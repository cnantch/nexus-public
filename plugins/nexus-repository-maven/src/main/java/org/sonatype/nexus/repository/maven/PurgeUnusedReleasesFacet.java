package org.sonatype.nexus.repository.maven;

import org.sonatype.nexus.repository.Facet;

/**
 * Facet for purging unused Maven releases.
 *
 * @since 3.7
 */
@Facet.Exposed
public interface PurgeUnusedReleasesFacet {


    /**
     * Purge the number of releases of a library in a repository
     * @param repositoryName - The name of the repository where the library has been saved
     * @param groupId - the group id of the library
     * @param artifactId - The artifact id of the library
     * @param numberOfReleases - the number of releases to purge
     */
    void purgeUnusedReleases(String repositoryName, String groupId, String artifactId, int numberOfReleases);

}
