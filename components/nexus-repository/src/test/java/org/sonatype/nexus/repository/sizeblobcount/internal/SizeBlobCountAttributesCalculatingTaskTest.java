package org.sonatype.nexus.repository.sizeblobcount.internal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.repository.sizeblobcount.SizeBlobCountAttributesFacet;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.scheduling.TaskConfiguration;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SizeBlobCountAttributesCalculatingTaskTest extends TestSupport{


    private static final String MY_REPO_NAME = "my-repo";

    @Mock
    private Repository repository;

    @Mock
    private HostedType hostedType;

    @Mock
    private GroupType groupType;

    @Mock
    private SizeBlobCountAttributesFacet sizeBlobCountAttributesFacet;


    private TaskConfiguration taskConfiguration;


    private SizeBlobCountAttributesCalculatingTask sizeBlobCountAttributesCalculatingTask;

    @Before
    public void setUp() {
        sizeBlobCountAttributesCalculatingTask = new SizeBlobCountAttributesCalculatingTask(hostedType);
        taskConfiguration = new TaskConfiguration();
        taskConfiguration.setId("test");
        taskConfiguration.setTypeId("test");
        taskConfiguration.setString(RepositoryTaskSupport.REPOSITORY_NAME_FIELD_ID, MY_REPO_NAME);

        sizeBlobCountAttributesCalculatingTask.configure(taskConfiguration);
    }

    @Test
    public void verify_the_execution_of_the_calcul_of_size_and_blob_count() {

        //Given
        when(repository.getName()).thenReturn(MY_REPO_NAME);
        when(repository.getType()).thenReturn(hostedType);
        when(repository.facet(SizeBlobCountAttributesFacet.class)).thenReturn(sizeBlobCountAttributesFacet);

        //When
        sizeBlobCountAttributesCalculatingTask.execute(repository);

        //Then
        verify(sizeBlobCountAttributesFacet, times(1)).calculateSizeBlobCount();
        assertThat(sizeBlobCountAttributesCalculatingTask.appliesTo(repository)).isTrue();
        assertThat(sizeBlobCountAttributesCalculatingTask.getMessage()).isEqualToIgnoringCase(SizeBlobCountAttributesCalculatingTask.PREFIX_MESSAGE + MY_REPO_NAME);
    }

    @Test
    public void verify_the_task_dont_apply_to_a_repository_type_different_of_hosted() {

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
