package org.sonatype.nexus.repository.maven.internal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.maven.internal.hosted.metadata.MetadataRebuilder;
import org.sonatype.nexus.repository.storage.ComponentEntityAdapter;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.repository.types.HostedType;

import static org.mockito.Mockito.*;

public class PurgedUnusedReleasesFacetImplTest {

    @Mock
    ComponentEntityAdapter componentEntityAdapter;

    @Mock
    MetadataRebuilder metadataRebuilder;

    @Mock
    GroupType groupType;

    @Mock
    HostedType hostedType;


    PurgeUnusedReleasesFacetImpl facet;

    @Before
    public void setUp() throws Exception {
        facet = new PurgeUnusedReleasesFacetImpl(componentEntityAdapter,
                metadataRebuilder, hostedType);
    }


    @Test
    public void TODO() throws Exception {
        Repository repository = mock(Repository.class);
        when(repository.getType()).thenReturn(groupType);

        //When

        //Then
    }
}
