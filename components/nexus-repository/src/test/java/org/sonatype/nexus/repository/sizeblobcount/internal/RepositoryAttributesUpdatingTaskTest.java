package org.sonatype.nexus.repository.sizeblobcount.internal;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.types.HostedType;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class RepositoryAttributesUpdatingTaskTest extends TestSupport{

    RepositoryAttributesUpdatingTask repositoryAttributesUpdatingTask;

    @Mock
    RepositoryManager repositoryManager;

    @Mock
    Repository repository;

    @Mock
    Repository anotherRepository;

    @Mock
    HostedType hostedType;



    @Before
    public void setUp() throws Exception {
        repositoryAttributesUpdatingTask = new RepositoryAttributesUpdatingTask(repositoryManager);
    }

    @Test
    public void check_name_of_the_task() throws Exception {
        Assertions.assertThat(repositoryAttributesUpdatingTask.getMessage()).isEqualToIgnoringCase("Update the size and the blob count of the repository");
    }

    @Test
    public void verify_the_execution_of_the_calcul_of_size_and_blob_count() throws Exception {

        //Given
        when(repository.getName()).thenReturn("my-repo");
        when(repository.getType()).thenReturn(hostedType);
        when(anotherRepository.getName()).thenReturn("my-another-repo");
        when(anotherRepository.getType()).thenReturn(hostedType);
        Iterable<Repository> repositories = Arrays.asList(repository, anotherRepository);

        when(repositoryManager.browse()).thenReturn(repositories);

        //When
        repositoryAttributesUpdatingTask.execute();

        //Then
        Mockito.verify(repositoryManager, times(1)).calculateSizeBlobCount();
    }
}
