package org.sonatype.nexus.repository.sizeblobcount;

import org.sonatype.nexus.repository.Facet;

/**
 * @since 3.7.0
 */
@Facet.Exposed
public interface SizeBlobCountAttributesFacet extends Facet{

    /**
     * Size of a repository
     * @return
     */
    long size();

    /**
     * Number of blobs of a repository
     * @return
     */
    long blobCount();

    /**
     * Calculate the size and the blob count of a repository
     */
    void calculateSizeBlobCount();
}
