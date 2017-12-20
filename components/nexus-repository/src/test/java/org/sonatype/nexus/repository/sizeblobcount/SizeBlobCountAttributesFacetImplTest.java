package org.sonatype.nexus.repository.sizeblobcount;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.tx.OTransaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.group.GroupFacet;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.storage.*;
import org.sonatype.nexus.repository.types.GroupType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.ImmutableList.copyOf;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.repository.sizeblobcount.SizeBlobCountAttributesFacetImpl.BLOB_COUNT_KEY;
import static org.sonatype.nexus.repository.sizeblobcount.SizeBlobCountAttributesFacetImpl.SIZE_BLOB_COUNT_KEY_ATTRIBUTES;
import static org.sonatype.nexus.repository.sizeblobcount.SizeBlobCountAttributesFacetImpl.SIZE_KEY;

public class SizeBlobCountAttributesFacetImplTest extends TestSupport{


    @Mock
    RepositoryManager repositoryManager;

    @Mock
    Configuration configuration;

    @Mock
    StorageFacet storageFacet;

    @Mock
    Bucket bucket;

    @Mock
    StorageTx storageTx;

    @Mock
    private OTransaction tx;

    @Mock
    BlobRef blobRef;

    @Mock
    Supplier<StorageTx> supplier;

    @Mock
    ODatabaseDocumentTx oDatabaseDocumentTx;

    @Mock
    GroupType groupType;

    private Repository initRepository() throws Exception {
        Repository repository = mock(Repository.class);

        repository.attach(storageFacet);

        when(repository.facet(StorageFacet.class)).thenReturn(storageFacet);
        when(repository.optionalFacet(StorageFacet.class)).thenReturn(Optional.of(storageFacet));
        when(storageFacet.txSupplier()).thenReturn(supplier);
        when(storageFacet.txSupplier().get()).thenReturn(storageTx);
        when(repository.getName()).thenReturn("MY-MAVEN-REPO");


        return repository;
    }

    private void initConfiguration(Repository repository) throws Exception {
        Map<String, Map<String, Object>> attributes = new HashMap<>();
        Map<String, Object> valueOfFirstKey = new HashMap<>();
        valueOfFirstKey.put("blobStoreName", "blobstoreTest");
        attributes.put("storage", valueOfFirstKey);
        when(configuration.getRepositoryName()).thenReturn("MY-MAVEN-REPO");
        when(configuration.getRecipeName()).thenReturn("mavenRecipeTest");
        when(configuration.getAttributes()).thenReturn(attributes);

        when(repository.getConfiguration()).thenReturn(configuration);
    }

    private Repository initRepositoryWithoutStorageFacet() throws Exception {
        Repository repository = mock(Repository.class);
        Map<String, Map<String, Object>> attributes = new HashMap<>();
        Map<String, Object> valueOfFirstKey = new HashMap<>();
        valueOfFirstKey.put("blobStoreName", "blobstoreTest");
        attributes.put("storage", valueOfFirstKey);
        when(configuration.getRepositoryName()).thenReturn("MY-MAVEN-REPO");
        when(configuration.getRecipeName()).thenReturn("mavenRecipeTest");
        when(configuration.getAttributes()).thenReturn(attributes);
        when(repository.getName()).thenReturn("MY-MAVEN-REPO");
        when(repository.optionalFacet(StorageFacet.class)).thenReturn(Optional.empty());
        when(repository.getConfiguration()).thenReturn(configuration);


        return repository;
    }

    private Repository initRepositoryWithAssets(Asset... assets) throws Exception {
        Repository repository = mock(Repository.class);
        Map<String, Map<String, Object>> attributes = new HashMap<>();
        Map<String, Object> valueOfFirstKey = new HashMap<>();
        valueOfFirstKey.put("blobStoreName", "blobstoreTest");
        attributes.put("storage", valueOfFirstKey);
        when(configuration.getRepositoryName()).thenReturn("MY-MAVEN-REPO");
        when(configuration.getRecipeName()).thenReturn("mavenRecipeTest");
        when(configuration.getAttributes()).thenReturn(attributes);

        when(repository.getName()).thenReturn("MY-MAVEN-REPO");
        when(repository.facet(StorageFacet.class)).thenReturn(storageFacet);
        when(repository.optionalFacet(StorageFacet.class)).thenReturn(Optional.of(storageFacet));
        when(storageFacet.txSupplier()).thenReturn(supplier);
        when(storageFacet.txSupplier().get()).thenReturn(storageTx);

        repository.init(configuration);
        repository.attach(storageFacet);

        when(storageTx.findBucket(repository)).thenReturn(bucket);
        when(storageTx.browseAssets(bucket)).thenReturn(Lists.newArrayList(assets));

        when(storageTx.countAssets(Matchers.any(Query.class), Matchers.any(Iterable.class))).thenReturn(2L);



        return repository;
    }

    private Repository groupRepository(final String name, final Repository... repositories) {
        Repository groupRepository = mock(Repository.class);
        when(groupRepository.getType()).thenReturn(groupType);
        when(groupRepository.getName()).thenReturn(name);
        when(repositoryManager.get(name)).thenReturn(groupRepository);
        GroupFacet groupFacet = mock(GroupFacet.class);
        when(groupRepository.facet(GroupFacet.class)).thenReturn(groupFacet);
        when(groupRepository.optionalFacet(GroupFacet.class)).thenReturn(Optional.of(groupFacet));
        when(groupRepository.facet(StorageFacet.class)).thenReturn(storageFacet);
        when(groupRepository.optionalFacet(StorageFacet.class)).thenReturn(Optional.of(storageFacet));

        when(groupFacet.members()).thenReturn(copyOf(repositories));
        return groupRepository;
    }

    private Asset mockAsset(String name, long size) {
        return mockAsset(name, size, true);
    }

    private Asset mockAsset(String name, long size, boolean hasBlob) {
        Asset asset = mock(Asset.class);
        when(asset.name()).thenReturn(name);
        when(asset.size()).thenReturn(size);
        if (hasBlob) {
            when(asset.blobRef()).thenReturn(blobRef);
        }
        return asset;
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void should_return_0_for_an_empty_repository() throws Exception {
        //Given
        /**
         * Un repository vide
         */
        Repository repository = initRepository();
        initConfiguration(repository);

        //When
        /**
         * Appel de la méthode de comptage de la taille et du blob count
         */
        SizeBlobCountAttributesFacet sizeBlobCountAttributesFacet = new SizeBlobCountAttributesFacetImpl();
        sizeBlobCountAttributesFacet.attach(repository);
        sizeBlobCountAttributesFacet.calculateSizeBlobCount();

        //Then
        /**
         * Return 0 for size and 0 for blobcount
         */
        assertThat(sizeBlobCountAttributesFacet.blobCount()).isEqualTo(0);
        assertThat(sizeBlobCountAttributesFacet.size()).isEqualTo(0);
    }

    @Test
    public void should_return_0_for_a_repository_with_no_storage_facet() throws Exception {
        //Given
        /**
         * Un repository vide
         */
        Repository repository = initRepositoryWithoutStorageFacet();

        //When
        /**
         * Appel de la méthode de comptage de la taille et du blob count
         */
        SizeBlobCountAttributesFacet sizeBlobCountAttributesFacet = new SizeBlobCountAttributesFacetImpl();
        sizeBlobCountAttributesFacet.attach(repository);
        sizeBlobCountAttributesFacet.calculateSizeBlobCount();
        //Then
        /**
         * Return 0 for size and 0 for blobcount
         */
        assertThat(sizeBlobCountAttributesFacet.blobCount()).isEqualTo(0);
        assertThat(sizeBlobCountAttributesFacet.size()).isEqualTo(0);
    }

    @Test
    public void should_return_the_size_and_the_blob_count_of_the_assets_for_a_repository_which_contains_just_those_assets() throws Exception {

        //Given
        /**
         * Un repository avec des assets
         */
        Asset asset1 = mockAsset("org.edf.test:1.0", 1500);
        Asset asset2 = mockAsset("org.edf.openam:1.0", 2500);
        Repository repository = initRepositoryWithAssets(asset1, asset2);
        initConfiguration(repository);
        Map<String, Object> backing = new HashMap<>();
        backing.put(SIZE_KEY, 4000L);
        backing.put(BLOB_COUNT_KEY, 2);
        configuration.getAttributes().put(SIZE_BLOB_COUNT_KEY_ATTRIBUTES, backing);

        //When
        /**
         * Appel de la méthode de comptage de la taille et du blob count
         */
        SizeBlobCountAttributesFacet sizeBlobCountAttributesFacet = new SizeBlobCountAttributesFacetImpl();
        sizeBlobCountAttributesFacet.attach(repository);
        sizeBlobCountAttributesFacet.calculateSizeBlobCount();

        //Then
        /**
         * Return the size and the blob count of the two assets
         */
        assertThat(sizeBlobCountAttributesFacet.blobCount()).isEqualTo(2);
        assertThat(sizeBlobCountAttributesFacet.size()).isEqualTo(4000);
    }

    @Test
    public void should_return_the_repository_attributes_of_a_group_repository() throws Exception {
        //Given
        Asset asset1 = mockAsset("org.edf.test:1.0", 1500);
        Asset asset2 = mockAsset("org.edf.openam:1.0", 2500);
        Repository repository = initRepositoryWithAssets(asset1, asset2);
        Asset asset3 = mockAsset("org.edf.test:2.0", 15000);
        Asset asset4 = mockAsset("org.edf.openam:2.0", 2500);
        Repository repository2 = initRepositoryWithAssets(asset3, asset4);
        Repository groupRepository = groupRepository("MY-REMO-MAVEN-GROUP", repository, repository2);
        Bucket groupBucket = mock(Bucket.class);
        when(storageTx.findBucket(groupRepository)).thenReturn(groupBucket);
        Map<String, Object> backing = new HashMap<>();
        backing.put(SIZE_KEY, 0L);
        backing.put(BLOB_COUNT_KEY, 0);
        NestedAttributesMap attributesMap = new NestedAttributesMap(SIZE_BLOB_COUNT_KEY_ATTRIBUTES, backing);
        when(groupBucket.attributes()).thenReturn(attributesMap);
        initConfiguration(groupRepository);


        //When
        /**
         * Appel de la méthode de comptage de la taille et du blob count
         */
        SizeBlobCountAttributesFacet sizeBlobCountAttributesFacet = new SizeBlobCountAttributesFacetImpl();
        sizeBlobCountAttributesFacet.attach(groupRepository);
        sizeBlobCountAttributesFacet.calculateSizeBlobCount();

        //Then
        /**
         * Return the size and the blob count of the group repository
         */
        assertThat(sizeBlobCountAttributesFacet.size()).isEqualTo(0);
        assertThat(sizeBlobCountAttributesFacet.blobCount()).isEqualTo(0);
    }
}
