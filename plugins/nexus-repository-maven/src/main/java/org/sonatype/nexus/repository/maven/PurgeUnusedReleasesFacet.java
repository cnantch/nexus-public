package org.sonatype.nexus.repository.maven;

import org.sonatype.nexus.repository.Facet;

/**
 * Facet for purging unused Maven releases.
 *
 * @since 3.7
 */
@Facet.Exposed
public interface PurgeUnusedReleasesFacet extends Facet {


    /**
     * Purge the number of releases of a library in a repository
     * @param groupId - the group id of the library
     * @param artifactId - The artifact id of the library
     * @param option
     * @param numberOfReleasesToKeep - the number of releases to keep
     */
    void purgeUnusedReleases(String groupId, String artifactId, String option, int numberOfReleasesToKeep);

}
