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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.sonatype.nexus.repository.FacetSupport.State.STARTED;
import static org.sonatype.nexus.repository.maven.internal.Attributes.*;
import static org.sonatype.nexus.repository.maven.internal.QueryPurgeReleasesBuilder.DATE_RELEASE_OPTION;
import static org.sonatype.nexus.repository.maven.internal.QueryPurgeReleasesBuilder.VERSION_OPTION;

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
                log.debug(MESSAGE_PURGE_NOT_EXECUTED);
            } else {
                log.debug("Number of releases to purge {} ", nbReleasesToPurge);
                process(groupId, artifactId, option, nbReleasesToPurge);
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
    private void process(String groupId, String artifactId, String option, long nbReleasesToPurge) {
        //First retrieve the last component of the releases which have been not purged
        String lastComponentVersion = null;
        Date lastReleaseDate = null;
        List<Component> components = retrieveReleases(groupId, artifactId, option, nbReleasesToPurge);
        if (VERSION_OPTION.equals(option)) {
            lastComponentVersion = getLastComponentVersion(components);
        } else if (DATE_RELEASE_OPTION.equals(option)) {
            lastReleaseDate = getLastComponentReleaseDate(components);
        }
        StorageTx tx = UnitOfWork.currentTx();
        //
        int n = 0;

        while (n < nbReleasesToPurge && !isCanceled()) {
            List<Component> filteredComponents = retrieveReleases(groupId, artifactId, option, PAGINATION_LIMIT, lastComponentVersion, lastReleaseDate, "desc");
            int totalComponents = filteredComponents.size();
            log.debug("{} components will be purged ", totalComponents);
            lastComponentVersion = getLastComponentVersion(filteredComponents);

            for (Component component : filteredComponents) {
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

    private List<Component> retrieveReleases(String groupId,
                                             String artifactId,
                                             String option,
                                             long pagination,
                                             String lastComponentVersion,
                                             Date lastReleaseDate,
                                             String order) {
        if (optionalFacet(StorageFacet.class).isPresent()) {
            StorageTx tx = UnitOfWork.currentTx();
            QueryPurgeReleasesBuilder queryPurgeReleasesBuilder = null;
            if (VERSION_OPTION.equals(option)) {
                queryPurgeReleasesBuilder = QueryPurgeReleasesBuilder.buildQueryForVersionOption(getRepository(),
                        tx, groupId, artifactId, lastComponentVersion, pagination, order);
            } else if (DATE_RELEASE_OPTION.equals(option)) {
                queryPurgeReleasesBuilder = QueryPurgeReleasesBuilder.buildQueryForReleaseDateOption(getRepository(),
                        tx, groupId, artifactId, lastReleaseDate, pagination, order);
            }

            log.debug("Query executed {} ", Objects.requireNonNull(queryPurgeReleasesBuilder).toString());

            Iterable<Component> components = tx.findComponents(queryPurgeReleasesBuilder.getWhereClause(),
                    queryPurgeReleasesBuilder.getQueryParams(), Collections.singletonList(getRepository()), queryPurgeReleasesBuilder.getQuerySuffix());
            return Lists.newArrayList(components);
        }
        return Collections.emptyList();
    }

    public List retrieveReleases(String groupId, String artifactId, String option, long pagination) {
        return retrieveReleases(groupId, artifactId, option, pagination, null, null, "asc");
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

    public Date getLastComponentReleaseDate(List<Component> components) {
        return components.get(components.size() - 1).lastUpdated().toDate();
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
