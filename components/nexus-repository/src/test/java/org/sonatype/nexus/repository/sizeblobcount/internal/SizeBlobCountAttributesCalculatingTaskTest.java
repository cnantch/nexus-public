package org.sonatype.nexus.repository.sizeblobcount.internal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.sizeblobcount.SizeBlobCountAttributesFacet;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.repository.types.HostedType;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SizeBlobCountAttributesCalculatingTaskTest extends TestSupport{

    SizeBlobCountAttributesCalculatingTask sizeBlobCountAttributesCalculatingTask;


    @Mock
    Repository repository;

    @Mock
    HostedType hostedType;

    @Mock
    GroupType groupType;

    @Mock
    SizeBlobCountAttributesFacet sizeBlobCountAttributesFacet;



    @Before
    public void setUp() throws Exception {
        sizeBlobCountAttributesCalculatingTask = new SizeBlobCountAttributesCalculatingTask();
    }

    @Test
    public void verify_the_execution_of_the_calcul_of_size_and_blob_count() throws Exception {

        //Given
        when(repository.getName()).thenReturn("my-repo");
        when(repository.getType()).thenReturn(hostedType);
        when(repository.facet(SizeBlobCountAttributesFacet.class)).thenReturn(sizeBlobCountAttributesFacet);

        //When
        sizeBlobCountAttributesCalculatingTask.execute(repository);

        //Then
        verify(sizeBlobCountAttributesFacet, times(1)).calculateSizeBlobCount();
        assertThat(sizeBlobCountAttributesCalculatingTask.appliesTo(repository)).isTrue();
        assertThat(sizeBlobCountAttributesCalculatingTask.getMessage()).startsWith("Calculate the size and the blob count of the repository");
    }

    @Test
    public void verify_the_task_dont_apply_to_a_repository_type_different_of_hosted() throws Exception {

        //Given
        when(repository.getName()).thenReturn("my-repo");
        when(repository.getType()).thenReturn(groupType);
        when(repository.facet(SizeBlobCountAttributesFacet.class)).thenReturn(sizeBlobCountAttributesFacet);

        //When
        sizeBlobCountAttributesCalculatingTask.execute(repository);

        //Then
        assertThat(sizeBlobCountAttributesCalculatingTask.appliesTo(repository)).isFalse();
    }

}
