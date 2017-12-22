package org.sonatype.nexus.repository.maven.internal;

import com.orientechnologies.orient.core.id.ORID;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.Type;
import org.sonatype.nexus.repository.maven.MavenFacet;
import org.sonatype.nexus.repository.maven.PurgeUnusedReleasesFacet;
import org.sonatype.nexus.repository.maven.internal.hosted.metadata.MetadataRebuilder;
import org.sonatype.nexus.repository.maven.internal.hosted.metadata.MetadataUtils;
import org.sonatype.nexus.repository.storage.*;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.transaction.UnitOfWork;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.orient.entity.AttachedEntityHelper.*;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_ARTIFACT_ID;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_BASE_VERSION;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_GROUP_ID;

@Named
public class PurgeUnusedReleasesFacetImpl extends FacetSupport
        implements PurgeUnusedReleasesFacet {


    public static final String MESSAGE_PURGE_NOT_EXECUTED = "TODO message to tell the purge cannot be done";
    public static final int PAGINATION_LIMIT = 5;
    private final ComponentEntityAdapter componentEntityAdapter;
    private final MetadataRebuilder metadataRebuilder;
    private final Type hostedType;
    private ORID lastComponentRecordId;


    @Inject
    PurgeUnusedReleasesFacetImpl(final ComponentEntityAdapter componentEntityAdapter,
                                 final MetadataRebuilder metadataRebuilder,
                                 @Named(HostedType.NAME) final Type hostedType
    )
    {
        this.componentEntityAdapter = checkNotNull(componentEntityAdapter);
        this.metadataRebuilder = checkNotNull(metadataRebuilder);
        this.hostedType = checkNotNull(hostedType);
    }


    @Override
    public void purgeUnusedReleases(String groupId, String artifactId, String option, int numberOfReleasesToKeep) {
        long nbComponents = countTotalReleases(groupId, artifactId);
        long nbReleasesToPurge = nbComponents - numberOfReleasesToKeep;
        if (nbReleasesToPurge <= 0) {
            log.debug(MESSAGE_PURGE_NOT_EXECUTED);
        } else {
            processAsHosted(groupId, artifactId, option, numberOfReleasesToKeep, nbReleasesToPurge);
        }

    }

    /**
     * Processing the purge
     * @param groupId - Group Id of the component
     * @param artifactId - Artifact Id of the component
     * @param option - Option used to order by for purge
     * @param numberOfReleasesToKeep
     * @param nbReleasesToPurge
     */
    public void processAsHosted(String groupId, String artifactId, String option, int numberOfReleasesToKeep, long nbReleasesToPurge) {
        //First retrieve the last component of the releases which have been not purged
        lastComponentRecordId = getLastComponentRecordId(retrieveReleases(groupId, artifactId, option, numberOfReleasesToKeep));
        StorageTx tx = UnitOfWork.currentTx();
        //
        int limit = PAGINATION_LIMIT;
        int n = 0;

        while (n < nbReleasesToPurge) {
            List<Component> components = retrieveReleases(groupId, artifactId, option, limit, lastComponentRecordId);
            int totalComponents = components.size();
            log.debug("{} components will be purged ", totalComponents);
            for (Component component : components) {
                deleteComponent(component);
            }
            lastComponentRecordId = getLastComponentRecordId(components);

            tx.commit();
            tx.begin();

            n += totalComponents;
        }
    }


    public List retrieveReleases(String groupId, String artifactId, String option, int pagination) {
        return retrieveReleases(groupId, artifactId, option, pagination, null);
    }

    /**
     * Get the releases component where the recordId is lower than the last Component id
     * @param groupId
     * @param artifactId
     * @param option
     * @param pagination
     * @param lastComponentId
     * @return
     */
    public List<Component> retrieveReleases(String groupId, String artifactId, String option, int pagination, ORID lastComponentId) {
        if (optionalFacet(StorageFacet.class).isPresent()) {
            StorageTx tx = UnitOfWork.currentTx();

            QueryPurgeReleasesBuilder queryPurgeReleasesBuilder = QueryPurgeReleasesBuilder.buildQuery(getRepository(), tx, groupId, artifactId, option, lastComponentId, pagination, false);

            log.debug("Query executed {} ", queryPurgeReleasesBuilder.toString());

            Iterable<Component> components = tx.findComponents(queryPurgeReleasesBuilder.getWhereClause(),
                    queryPurgeReleasesBuilder.getQueryParams(), Arrays.asList(getRepository()), queryPurgeReleasesBuilder.getQuerySuffix());
            return (List) components;
        }
        return Collections.emptyList();
    }

    /**
     * Number of releases for a given groupId/ artifactId
     * @param groupId
     * @param artifactId
     * @return
     */
    public long countTotalReleases(String groupId, String artifactId) {
        if (optionalFacet(StorageFacet.class).isPresent()) {
            StorageTx tx = UnitOfWork.currentTx();
            QueryPurgeReleasesBuilder queryPurgeReleasesBuilder = QueryPurgeReleasesBuilder.buildQueryForCount(getRepository(),
                    tx,
                    groupId,
                    artifactId);
            Long nbComponents = tx.countComponents(queryPurgeReleasesBuilder.getWhereClause(),
                    queryPurgeReleasesBuilder.getQueryParams(),
                    Arrays.asList(getRepository()), queryPurgeReleasesBuilder.getQuerySuffix());
            return nbComponents;
        }
        return 0L;
    }



    private String deleteComponent(final Component component) {
        log.debug("Deleting unused released component {}", component);
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
        return groupId;
    }

    public ORID getLastComponentRecordId(List<Component> components) {
        return id(components.get(components.size() - 1));
    }

}
