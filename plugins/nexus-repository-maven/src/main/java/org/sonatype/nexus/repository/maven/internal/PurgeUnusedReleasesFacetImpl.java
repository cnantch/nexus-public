package org.sonatype.nexus.repository.maven.internal;

import com.google.common.collect.Lists;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.common.stateguard.Guarded;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.maven.MavenFacet;
import org.sonatype.nexus.repository.maven.PurgeUnusedReleasesFacet;
import org.sonatype.nexus.repository.maven.internal.hosted.metadata.MetadataUtils;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.transaction.TransactionalDeleteBlob;
import org.sonatype.nexus.repository.transaction.TransactionalStoreMetadata;
import org.sonatype.nexus.scheduling.CancelableHelper;
import org.sonatype.nexus.scheduling.TaskInterruptedException;
import org.sonatype.nexus.transaction.UnitOfWork;

import javax.inject.Named;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.sonatype.nexus.repository.FacetSupport.State.STARTED;
import static org.sonatype.nexus.repository.maven.internal.Attributes.*;

@Named
public class PurgeUnusedReleasesFacetImpl extends FacetSupport
        implements PurgeUnusedReleasesFacet {


    private static final String MESSAGE_PURGE_NOT_EXECUTED = "TODO message to tell the purge cannot be done";

    public static final int PAGINATION_LIMIT = 10;


    @Override
    @Guarded(by = STARTED)
    public void purgeUnusedReleases(String groupId, String artifactId, String option, int numberOfReleasesToKeep) {
        TransactionalStoreMetadata.operation.withDb(facet(StorageFacet.class).txSupplier()).call(() -> {
            long nbComponents = countTotalReleases(groupId, artifactId);
            long nbReleasesToPurge = nbComponents - numberOfReleasesToKeep;
            if (nbReleasesToPurge <= 0) {
                log.info(MESSAGE_PURGE_NOT_EXECUTED);
            } else {
                log.info("Number of releases to purge {} ", nbReleasesToPurge);
                processAsHosted(groupId, artifactId, option, nbReleasesToPurge);
            }
            return null;
        });
    }

    /**
     * Processing the purge
     * @param groupId - Group Id of the component
     * @param artifactId - Artifact Id of the component
     * @param option - Option used to order by for purge
     */
    private void processAsHosted(String groupId, String artifactId, String option, long nbReleasesToPurge) {
        //First retrieve the last component of the releases which have been not purged
        String lastComponentVersion = getLastComponentVersion(retrieveReleases(groupId, artifactId, option, nbReleasesToPurge));
        StorageTx tx = UnitOfWork.currentTx();
        //
        int n = 0;

        while (n < nbReleasesToPurge && !isCanceled()) {
            List<Component> components = retrieveReleases(groupId, artifactId, option, PAGINATION_LIMIT, lastComponentVersion, "desc");
            int totalComponents = components.size();
            log.info("{} components will be purged ", totalComponents);
            lastComponentVersion = getLastComponentVersion(components);

            for (Component component : components) {
                if (isCanceled()) {
                    break;
                }
                deleteComponent(component);
            }

            tx.commit();
            tx.begin();

            n += totalComponents;
        }
    }

    private List<Component> retrieveReleases(String groupId, String artifactId, String option, long pagination, String lastComponentVersion, String order) {
        if (optionalFacet(StorageFacet.class).isPresent()) {
            StorageTx tx = UnitOfWork.currentTx();

            QueryPurgeReleasesBuilder queryPurgeReleasesBuilder = QueryPurgeReleasesBuilder.buildQuery(getRepository(), tx, groupId, artifactId, option, lastComponentVersion, pagination, false, order);

            log.info("Query executed {} ", queryPurgeReleasesBuilder.toString());

            Iterable<Component> components = tx.findComponents(queryPurgeReleasesBuilder.getWhereClause(),
                    queryPurgeReleasesBuilder.getQueryParams(), Collections.singletonList(getRepository()), queryPurgeReleasesBuilder.getQuerySuffix());
            return Lists.newArrayList(components);
        }
        return Collections.emptyList();
    }

    public List retrieveReleases(String groupId, String artifactId, String option, long pagination) {
        return retrieveReleases(groupId, artifactId, option, pagination, null, "asc");
    }

    public long countTotalReleases(String groupId, String artifactId) {
        long nbComponents = 0L;
        if (optionalFacet(StorageFacet.class).isPresent()) {
            StorageTx tx = UnitOfWork.currentTx();
            QueryPurgeReleasesBuilder queryPurgeReleasesBuilder = QueryPurgeReleasesBuilder.buildQueryForCount(getRepository(),
                    tx,
                    groupId,
                    artifactId);
            nbComponents = tx.countComponents(queryPurgeReleasesBuilder.getWhereClause(),
                    queryPurgeReleasesBuilder.getQueryParams(),
                    Collections.singletonList(getRepository()), queryPurgeReleasesBuilder.getQuerySuffix());
        }
        log.info("Total number of releases components for {} {} : {} ", groupId, artifactId, nbComponents);
        return nbComponents;
    }


    @TransactionalDeleteBlob
    private void deleteComponent(final Component component) {
        log.info("Deleting unused released component {}", component);
        MavenFacet facet = facet(MavenFacet.class);
        final StorageTx tx = UnitOfWork.currentTx();
        tx.deleteComponent(component);

        NestedAttributesMap attributes = component.formatAttributes();
        String groupId = attributes.get(P_GROUP_ID, String.class);
        String artifactId = attributes.get(P_ARTIFACT_ID, String.class);
        String baseVersion = attributes.get(P_BASE_VERSION, String.class);

        try {
            // We have to delete all metadata through GAV levels and rebuild in the next step, as the MetadataRebuilder
            // isn't meant to remove metadata that has been orphaned by the deletion of a component
            MavenFacetUtils.deleteWithHashes(facet, MetadataUtils.metadataPath(groupId, artifactId, baseVersion));
            MavenFacetUtils.deleteWithHashes(facet, MetadataUtils.metadataPath(groupId, artifactId, null));
            MavenFacetUtils.deleteWithHashes(facet, MetadataUtils.metadataPath(groupId, null, null));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLastComponentVersion(List<Component> components) {
        return components.get(components.size() - 1).version();
    }

    private boolean isCanceled() {
        try {
            CancelableHelper.checkCancellation();
            return false;
        } catch (TaskInterruptedException e) {
            log.warn("Purge unused Maven releases job is canceled");
            return true;
        }
    }

}
