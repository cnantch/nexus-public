package org.sonatype.nexus.repository.maven.internal;

import com.orientechnologies.orient.core.id.ORID;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.StorageTx;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.sonatype.nexus.orient.entity.AttachedEntityHelper.id;

/**
 * Class used for build orient db sql request for purge unused releases
 */
public class QueryPurgeReleasesBuilder {

    static final String VERSION_OPTION = "version";
    static final String DATE_RELEASE_OPTION = "dateRelease";

    private  Map<String, Object> queryParams;
    private  String whereClause;
    private  String querySuffix;


    private QueryPurgeReleasesBuilder(Map<String, Object> queryParams, String whereClause, String querySuffix) {
        this.queryParams = queryParams;
        this.whereClause = whereClause;
        this.querySuffix = querySuffix;
    }

    /**
     * Build a query with thoses parameters
     * @param repository - Repository selected for the purge
     * @param tx - Storage Tx
     * @param groupId - Group Id of the release
     * @param artifactId - Artifact Id of the release
     * @param pagination - Pagination limit
     * @param isCount - Boolean which defines if is a count request
     * @param orderBy - The criteria for the order
     * @param sort - The criteria for the sort
     * @return the query builded
     */
    private static QueryPurgeReleasesBuilder buildQuery(Repository repository,
                                                        StorageTx tx,
                                                        String groupId,
                                                        String artifactId,
                                                        Long pagination,
                                                        Boolean isCount,
                                                        String orderBy,
                                                        String sort) {
        Map<String, Object> queryParameters = new HashMap<>();
        ORID bucketId = id(Objects.requireNonNull(tx.findBucket(repository)));
        queryParameters.put("bucketId", bucketId);
        queryParameters.put("groupId", groupId);
        queryParameters.put("artifactId", artifactId);
        queryParameters.put("criteriaSnapshot", "%SNAPSHOT%");
        String whereClause = "bucket = :bucketId and attributes.maven2.groupId = :groupId and " +
                " attributes.maven2.artifactId = :artifactId and not(attributes.maven2.baseVersion.toUpperCase() like :criteriaSnapshot)";
        StringBuilder querySuffixBuilder = new StringBuilder("");
        if (!isCount) {
            querySuffixBuilder.append(orderBy);
            querySuffixBuilder.append(sort);
            querySuffixBuilder.append(" limit ");
            querySuffixBuilder.append(pagination);
        }



        return new QueryPurgeReleasesBuilder(queryParameters, whereClause, isCount ? null : querySuffixBuilder.toString());
    }

    public static QueryPurgeReleasesBuilder buildQueryForVersionOption(Repository repository,
                                                                       StorageTx tx,
                                                                       String groupId,
                                                                       String artifactId,
                                                                       String lastComponentVersion,
                                                                       Long pagination,
                                                                       String sort) {
        QueryPurgeReleasesBuilder buildedQuery = buildQuery(repository,
                tx,
                groupId,
                artifactId,
                pagination, false,
                "order by attributes.maven2.baseVersion ",
                sort);
        if (lastComponentVersion != null) {
            buildedQuery.addFilterInQueryBuilder("lastComponentVersion", lastComponentVersion,
                    " and version <= : lastComponentVersion");
        }
        return  buildedQuery;
    }

    public static QueryPurgeReleasesBuilder buildQueryForReleaseDateOption(Repository repository,
                                                                           StorageTx tx,
                                                                           String groupId,
                                                                           String artifactId,
                                                                           Date lastReleaseDate,
                                                                           Long pagination,
                                                                           String sort) {
        QueryPurgeReleasesBuilder buildedQuery = buildQuery(repository,
                tx, groupId, artifactId, pagination, false, "order by last_updated ", sort);
        if (lastReleaseDate != null) {
            buildedQuery.addFilterInQueryBuilder("lastReleaseDate", lastReleaseDate, " and last_updated <= :lastReleaseDate");
        }
        return  buildedQuery;
    }

    public static QueryPurgeReleasesBuilder buildQueryForCount(Repository repository, StorageTx tx, String groupId, String artifactId) {
        return buildQuery(repository, tx, groupId, artifactId, null, true, null, null);
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    public String getQuerySuffix() {
        return querySuffix;
    }

    public String getWhereClause() {
        return whereClause;
    }

    private void addFilterInQueryBuilder(String filteredItem, Object filteredData, String suffixWhereClause) {
        queryParams.put(filteredItem, filteredData);
        whereClause += suffixWhereClause;
    }

    @Override
    public String toString() {
        return "QueryPurgeReleasesBuilder{" +
                "queryParams=" + queryParams +
                ", whereClause='" + whereClause + '\'' +
                ", querySuffix='" + querySuffix + '\'' +
                '}';
    }
}
