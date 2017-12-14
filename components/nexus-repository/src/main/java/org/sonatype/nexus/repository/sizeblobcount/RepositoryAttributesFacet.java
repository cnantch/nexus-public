package org.sonatype.nexus.repository.sizeblobcount;

import org.sonatype.nexus.repository.Facet;

/**
 * @since 3.7.0
 */
@Facet.Exposed
public interface RepositoryAttributesFacet extends Facet{
    long size();

    long blobCount();

    void setSize(long size);

    void setBlobCount(long size);

}
