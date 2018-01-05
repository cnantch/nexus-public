package org.sonatype.nexus.repository.maven.internal;

import java.util.Objects;

public class QueryResultForNumberOfReleases {

    private final String groupId;
    private final String artifactId;
    private final Long count;


    public QueryResultForNumberOfReleases(String groupId, String artifactId, Long count) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.count = count;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryResultForNumberOfReleases that = (QueryResultForNumberOfReleases) o;
        return count == that.count &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(artifactId, that.artifactId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(groupId, artifactId, count);
    }

    @Override
    public String toString() {
        return "QueryResultForNumberOfReleases{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", count=" + count +
                '}';
    }
}
