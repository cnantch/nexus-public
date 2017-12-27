package org.sonatype.nexus.repository.sizeblobcount;

import org.sonatype.nexus.repository.Facet;

/**
 * Facet used for calculating the size and the blobcount of repositories
 * @since 3.7.0
 */
@Facet.Exposed
public interface SizeBlobCountAttributesFacet extends Facet{

    /**
     * Calculate the size and the blob count of a repository
     */
    void calculateSizeBlobCount();
}
