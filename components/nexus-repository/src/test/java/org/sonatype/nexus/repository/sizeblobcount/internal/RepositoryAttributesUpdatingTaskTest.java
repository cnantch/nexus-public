package org.sonatype.nexus.repository.sizeblobcount.internal;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.sizeblobcount.RepositoryAttributesFacet;
import org.sonatype.nexus.repository.sizeblobcount.SizeBlobCount;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.repository.types.HostedType;

import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class RepositoryAttributesUpdatingTaskTest extends TestSupport{

    RepositoryAttributesUpdatingTask repositoryAttributesUpdatingTask;


    @Mock
    Repository repository;

    @Mock
    HostedType hostedType;

    @Mock
    Repository groupRepository;

    @Mock
    GroupType groupType;

    @Mock
    RepositoryAttributesFacet repositoryAttributesFacet;



    @Before
    public void setUp() throws Exception {
        repositoryAttributesUpdatingTask = new RepositoryAttributesUpdatingTask();
    }

    @Test
    public void verify_the_execution_of_the_calcul_of_size_and_blob_count() throws Exception {

        //Given
        when(repository.getName()).thenReturn("my-repo");
        when(repository.getType()).thenReturn(hostedType);
        when(repository.facet(RepositoryAttributesFacet.class)).thenReturn(repositoryAttributesFacet);

        //When
        repositoryAttributesUpdatingTask.execute(repository);

        //Then
        Mockito.verify(repositoryAttributesFacet, times(1)).calculateSizeBlobCount();
        Assertions.assertThat(repositoryAttributesUpdatingTask.getMessage()).startsWith("Calculate the size and the blob count of the repository");
    }

    @Test
    public void verify_the_execution_of_set_size_and_set_blob_count() throws Exception {
        when(repositoryAttributesFacet.calculateSizeBlobCount()).thenReturn(new SizeBlobCount(1000l, 3L));

        when(repository.getName()).thenReturn("my-repo");
        when(repository.getType()).thenReturn(hostedType);
        when(repository.facet(RepositoryAttributesFacet.class)).thenReturn(repositoryAttributesFacet);

        //When
        repositoryAttributesUpdatingTask.execute(repository);

        //Then
        Mockito.verify(repositoryAttributesFacet, times(1)).setSize(1000l);
        Mockito.verify(repositoryAttributesFacet, times(1)).setBlobCount(3l);
    }

    @Test
    public void verify_the_type_of_repository_which_been_used_on_this_task() throws Exception {

        when(repository.getName()).thenReturn("my-repo");
        when(repository.getType()).thenReturn(hostedType);
        when(repository.facet(RepositoryAttributesFacet.class)).thenReturn(repositoryAttributesFacet);

        when(groupRepository.getName()).thenReturn("my-repo-group");
        when(groupRepository.getType()).thenReturn(groupType);

        //When
        repositoryAttributesUpdatingTask.execute(repository);

        //Then
        Assertions.assertThat(repositoryAttributesUpdatingTask.appliesTo(repository)).isTrue();
        Assertions.assertThat(repositoryAttributesUpdatingTask.appliesTo(groupRepository)).isFalse();
    }
}
