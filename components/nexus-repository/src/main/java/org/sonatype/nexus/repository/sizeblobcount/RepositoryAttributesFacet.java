package org.sonatype.nexus.repository.sizeblobcount;

import org.sonatype.nexus.repository.Facet;

@Facet.Exposed
public interface RepositoryAttributesFacet extends Facet{

    long size();

    long blobCount();
}
