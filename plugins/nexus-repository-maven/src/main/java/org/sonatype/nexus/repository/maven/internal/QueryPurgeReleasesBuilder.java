package org.sonatype.nexus.repository.maven.internal;

import com.orientechnologies.orient.core.id.ORID;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.StorageTx;

import java.util.HashMap;
import java.util.Map;

import static org.sonatype.nexus.orient.entity.AttachedEntityHelper.id;

public class QueryPurgeReleasesBuilder {

    public static final String VERSION_OPTION = "version";
    public static final String DATE_RELEASE_OPTION = "dateRelease";
    private final Map<String, Object> queryParams;
    private final String whereClause;
    private final String querySuffix;


    private QueryPurgeReleasesBuilder(Map<String, Object> queryParams, String whereClause, String querySuffix) {
        this.queryParams = queryParams;
        this.whereClause = whereClause;
        this.querySuffix = querySuffix;
    }


    public static QueryPurgeReleasesBuilder buildQuery(Repository repository, StorageTx tx, String groupId, String artifactId, String option, ORID lastComponentId, Integer pagination, Boolean isCount) {
        Map<String, Object> queryParameters = new HashMap<>();
        ORID bucketId = id(tx.findBucket(repository));
        queryParameters.put("bucketId", bucketId);
        queryParameters.put("groupId", groupId);
        queryParameters.put("artifactId", artifactId);
        queryParameters.put("criteriaSnapshot", "%SNAPSHOT%");
        String whereClause = " ";
        if (lastComponentId != null) {
            queryParameters.put("lastComponentId", lastComponentId);
            whereClause += " @rid > :lastComponentId and ";
        }
        StringBuilder querySuffixBuilder = new StringBuilder("");
        if (!isCount) {
            if (VERSION_OPTION.equals(option)) {
                querySuffixBuilder.append("order by attributes.maven2.baseVersion desc");
            } else if (DATE_RELEASE_OPTION.equals(option)) {
                querySuffixBuilder.append("order by last_updated desc");
            }
        }
        querySuffixBuilder.append(" limit ");
        querySuffixBuilder.append(pagination);

        whereClause += "bucket = :bucketId and attributes.maven2.groupId = :groupId " +
                " attributes.maven2.artifactId = :artifactId and not(attributes.maven2.baseVersion.toUpperCase() like :criteriaSnapshot)";

        return new QueryPurgeReleasesBuilder(queryParameters, whereClause, isCount ? null : querySuffixBuilder.toString());
    }

    public static QueryPurgeReleasesBuilder buildQueryForCount(Repository repository, StorageTx tx, String groupId, String artifactId) {
        return buildQuery(repository, tx,
                groupId,
                artifactId,
                null,
                null,
                null,
                true);
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

    @Override
    public String toString() {
        return "QueryPurgeReleasesBuilder{" +
                "queryParams=" + queryParams +
                ", whereClause='" + whereClause + '\'' +
                ", querySuffix='" + querySuffix + '\'' +
                '}';
    }
}
