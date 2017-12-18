package org.sonatype.nexus.repository.maven.internal;

import com.orientechnologies.orient.core.id.ORID;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.Type;
import org.sonatype.nexus.repository.maven.PurgeUnusedReleasesFacet;
import org.sonatype.nexus.repository.maven.internal.hosted.metadata.MetadataRebuilder;
import org.sonatype.nexus.repository.storage.ComponentEntityAdapter;
import org.sonatype.nexus.repository.types.HostedType;

import javax.inject.Named;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class PurgeUnusedReleasesFacetImpl extends FacetSupport
        implements PurgeUnusedReleasesFacet {


    private final ComponentEntityAdapter componentEntityAdapter;
    private final MetadataRebuilder metadataRebuilder;
    private final Type hostedType;
    private ORID lastComponent;

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
    public void purgeUnusedReleases(String repositoryName, String groupId, String artifactId, int numberOfReleases) {

    }

}
