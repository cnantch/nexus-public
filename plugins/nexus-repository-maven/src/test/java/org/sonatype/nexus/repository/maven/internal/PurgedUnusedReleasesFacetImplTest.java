package org.sonatype.nexus.repository.maven.internal;

import com.google.common.base.Supplier;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.orient.entity.AttachedEntityMetadata;
import org.sonatype.nexus.orient.entity.EntityAdapter;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.maven.MavenFacet;
import org.sonatype.nexus.repository.storage.*;
import org.sonatype.nexus.transaction.UnitOfWork;

import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.sonatype.nexus.repository.maven.internal.PurgeUnusedReleasesFacetImpl.PAGINATION_LIMIT;
import static org.sonatype.nexus.repository.maven.internal.QueryPurgeReleasesBuilder.VERSION_OPTION;

public class PurgedUnusedReleasesFacetImplTest extends TestSupport{

    public static final String GROUP_ID = "org.edf";

    public static final String ARTIFACT_ID = "demoNexus";

    @Mock
    StorageTx storageTx;

    @Mock
    StorageFacet storageFacet;

    @Mock
    MavenFacet mavenFacet;

    @Mock
    Repository repository;

    @Mock
    Maven2Format maven2Format;

    @Mock
    private Configuration configuration;

    @Mock
    private Bucket bucket;

    @Mock
    private Component  firstComponent;
    @Mock
    private Component secondComponent;
    @Mock
    private Component thirdComponent;
    @Mock
    private Component fourthComponent;
    @Mock
    private Supplier<StorageTx> supplier;
    @Mock
    private ODatabaseDocumentTx oDatabaseDocumentTx;

    private void initFirstRepository() throws Exception {
        when(configuration.getRecipeName()).thenReturn("maven2-hosted");
        when(repository.getName()).thenReturn("my-first-repo");
        when(repository.getConfiguration()).thenReturn(configuration);
        when(repository.getFormat()).thenReturn(maven2Format);
        repository.attach(storageFacet);
        when(repository.optionalFacet(StorageFacet.class)).thenReturn(Optional.of(storageFacet));
        when(repository.facet(StorageFacet.class)).thenReturn(storageFacet);

        when(storageFacet.txSupplier()).thenReturn(supplier);
        when(storageFacet.txSupplier().get()).thenReturn(storageTx);

        //Bucket of the repository
        when(storageTx.findBucket(repository)).thenReturn(bucket);
        ORecordId orID = new ORecordId(21, 1);
        EntityAdapter owner = mock(EntityAdapter.class);
        ODocument document = mock(ODocument.class);
        when(document.getIdentity()).thenReturn(orID);
        EntityMetadata entityMetadata = new AttachedEntityMetadata(owner, document);
        when(bucket.getEntityMetadata()).thenReturn(entityMetadata);

        //Initialization of the components
        when(firstComponent.group()).thenReturn(GROUP_ID);
        when(firstComponent.version()).thenReturn("1.0-20171221.092123");
        when(firstComponent.format()).thenReturn("maven2");
        when(firstComponent.name()).thenReturn("ARTIFACT_ID");
        when(firstComponent.lastUpdated()).thenReturn(new DateTime(2017, 12, 10, 23, 0, 0));
        Map<String, Object> firstAttributesMap = new HashMap<>();
        firstAttributesMap.put("baseVersion", "1.0-SNAPSHOT");
        firstAttributesMap.put("groupId", GROUP_ID);
        firstAttributesMap.put("artifactId", "ARTIFACT_ID");
        NestedAttributesMap firstAttributesComponent = new NestedAttributesMap("maven2", firstAttributesMap);
        when(firstComponent.attributes()).thenReturn(firstAttributesComponent);
        Asset firstassetOfFirstComponent = mock(Asset.class);
        when(firstassetOfFirstComponent.name()).thenReturn("org/edf/demoNexus/1.0-SNAPSHOT/demoNexus-1.0-20171221.092011-1.jar");
        Asset sndassetOfFirstComponent = mock(Asset.class);
        when(sndassetOfFirstComponent.name()).thenReturn("org/edf/demoNexus/1.0-SNAPSHOT/demoNexus-1.0-20171223.092011-1.jar");

        when(storageTx.browseAssets(firstComponent)).thenReturn(Arrays.asList(firstassetOfFirstComponent, sndassetOfFirstComponent));

        when(secondComponent.group()).thenReturn(GROUP_ID);
        when(secondComponent.version()).thenReturn("1.0");
        when(secondComponent.format()).thenReturn("maven2");
        when(secondComponent.name()).thenReturn("ARTIFACT_ID");
        when(secondComponent.lastUpdated()).thenReturn(new DateTime(2017, 12, 10, 23, 10, 0));
        Map<String, Object> sndAttributesMap = new HashMap<>();
        sndAttributesMap.put("baseVersion", "1.0");
        sndAttributesMap.put("groupId", GROUP_ID);
        sndAttributesMap.put("artifactId", "ARTIFACT_ID");
        NestedAttributesMap sndAttributesComponent = new NestedAttributesMap("maven2", sndAttributesMap);
        when(secondComponent.attributes()).thenReturn(sndAttributesComponent);

        Asset firstassetOfSndComponent = mock(Asset.class);
        when(firstassetOfSndComponent.name()).thenReturn("org/edf/demoNexus/1.0/demoNexus-1.1.jar");
        Asset sndassetOfSndComponent = mock(Asset.class);
        when(sndassetOfSndComponent.name()).thenReturn("org/edf/demoNexus/1.0/demoNexus-1.1.jar.md5");
        Asset thirdassetOfSndComponent = mock(Asset.class);
        when(thirdassetOfSndComponent.name()).thenReturn("org/edf/demoNexus/1.0/demoNexus-1.1.jar.sha1");

        when(storageTx.browseAssets(secondComponent)).thenReturn(Arrays.asList(firstassetOfSndComponent,
                sndassetOfSndComponent, thirdassetOfSndComponent));



        when(thirdComponent.group()).thenReturn(GROUP_ID);
        when(thirdComponent.version()).thenReturn("1.2");
        when(thirdComponent.format()).thenReturn("maven2");
        when(thirdComponent.name()).thenReturn("ARTIFACT_ID");
        when(thirdComponent.lastUpdated()).thenReturn(new DateTime(2017, 12, 11, 23, 10, 0));
        Map<String, Object> thirdAttributesMap = new HashMap<>();
        thirdAttributesMap.put("baseVersion", "1.2");
        thirdAttributesMap.put("groupId", GROUP_ID);
        thirdAttributesMap.put("artifactId", "ARTIFACT_ID");
        NestedAttributesMap thirdAttributesComponent = new NestedAttributesMap("maven2", thirdAttributesMap);
        when(thirdComponent.attributes()).thenReturn(thirdAttributesComponent);

        Asset firstassetOfThirdComponent = mock(Asset.class);
        when(firstassetOfThirdComponent.name()).thenReturn("org/edf/demoNexus/1.0/demoNexus-1.2.jar");
        Asset sndassetOfThirdComponent = mock(Asset.class);
        when(sndassetOfThirdComponent.name()).thenReturn("org/edf/demoNexus/1.0/demoNexus-1.2.pom");

        when(storageTx.browseAssets(thirdComponent)).thenReturn(Arrays.asList(firstassetOfThirdComponent,
                sndassetOfThirdComponent));

        when(fourthComponent.group()).thenReturn(GROUP_ID);
        when(fourthComponent.version()).thenReturn("1.3");
        when(fourthComponent.format()).thenReturn("maven2");
        when(fourthComponent.name()).thenReturn("ARTIFACT_ID");
        when(fourthComponent.lastUpdated()).thenReturn(new DateTime(2017, 12, 12, 23, 10, 0));
        Map<String, Object> fourthAttributesMap = new HashMap<>();
        fourthAttributesMap.put("baseVersion", "1.3");
        fourthAttributesMap.put("groupId", GROUP_ID);
        fourthAttributesMap.put("artifactId", "ARTIFACT_ID");
        NestedAttributesMap fourthAttributesComponent = new NestedAttributesMap("maven2", fourthAttributesMap);
        when(fourthComponent.formatAttributes()).thenReturn(fourthAttributesComponent);

        Asset firstassetOfFourthComponent = mock(Asset.class);
        when(firstassetOfFourthComponent.name()).thenReturn("org/edf/demoNexus/1.0/demoNexus-1.3.jar");

        when(storageTx.browseAssets(thirdComponent)).thenReturn(Arrays.asList(firstassetOfFourthComponent));


        when(storageTx.browseComponents(bucket)).thenReturn(Arrays.asList(firstComponent, secondComponent,
                thirdComponent,
                fourthComponent));


    }


    public Component mockComponent(String version, DateTime lastUpdatedDateTime, String baseVersion) {
        Component component = mock(Component.class);
        when(component.group()).thenReturn(GROUP_ID);
        when(component.version()).thenReturn(version);
        when(component.format()).thenReturn("maven2");
        when(component.name()).thenReturn(ARTIFACT_ID);
        when(component.lastUpdated()).thenReturn(lastUpdatedDateTime);
        Map<String, Object> firstAttributesMap = new HashMap<>();
        firstAttributesMap.put("baseVersion", baseVersion);
        firstAttributesMap.put("groupId", GROUP_ID);
        firstAttributesMap.put("artifactId", ARTIFACT_ID);
        NestedAttributesMap firstAttributesComponent = new NestedAttributesMap("maven2", firstAttributesMap);
        when(component.formatAttributes()).thenReturn(firstAttributesComponent);
        when(component.formatAttributes()).thenReturn(firstAttributesComponent);
        return component;
    }

    PurgeUnusedReleasesFacetImpl facet;

    @Before
    public void setUp() throws Exception {
        facet = new PurgeUnusedReleasesFacetImpl();

        facet.attach(repository);

        when(storageTx.getDb()).thenReturn(oDatabaseDocumentTx);

        UnitOfWork.beginBatch(storageTx);
    }

    @After
    public void tearDown() throws Exception {
        UnitOfWork.end();
    }

    @Test
    public void should_return_the_number_of_components_for_a_given_groupId_artifactId_in_a_specific_repository() throws Exception {
        //Given
        initFirstRepository();
        when(storageTx.countComponents(Matchers.any(String.class),
                Matchers.any(Map.class),
                Matchers.any(Iterable.class),
                Matchers.anyString()
        )).thenReturn(3L);

        //When
        long nbReleases = facet.countTotalReleases(GROUP_ID, ARTIFACT_ID);

        //Then
        assertThat(nbReleases).isEqualTo(3);
    }

    @Test
    public void should_return_the_last_record_id_of_the_component_in_a_component_list() throws Exception {
        String lastComponentVersion;
        initFirstRepository();
        List<Component> components = Arrays.asList(secondComponent, thirdComponent, fourthComponent);
        when(storageTx.findComponents(Matchers.any(String.class),
                Matchers.any(Map.class),
                Matchers.any(Iterable.class),
                Matchers.anyString()
        )).thenReturn(components);
        when(fourthComponent.version()).thenReturn("2.4");

        //When
        lastComponentVersion = facet.getLastComponentVersion(components);

        //Then
        assertThat(lastComponentVersion).isEqualTo("2.4");
    }



    @Test
    public void should_return_the_releases_component_for_the_given_groupId_artifactId() throws Exception {

        //Given
        initFirstRepository();
        when(storageTx.findComponents(Matchers.any(String.class),
                Matchers.any(Map.class),
                Matchers.any(Iterable.class),
                Matchers.anyString()
        )).thenReturn(Arrays.asList(secondComponent, thirdComponent, fourthComponent));

        //When

        List<Component> components = facet.retrieveReleases(GROUP_ID, ARTIFACT_ID, "version", 10);

        //Then
        assertThat(components.size()).isEqualTo(3);
        assertThat(components).contains(secondComponent, thirdComponent, fourthComponent);
    }

    @Test
    public void test_the_purge_of_unused_releases_when_the_number_of_releases_to_keep_is_lower_than_the_total_components() throws Exception {
        //Given
        initFirstRepository();
        when(repository.facet(MavenFacet.class)).thenReturn(mavenFacet);


        Component fifthComponent = mockComponent("1.0.5", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT");
        ORecordId orID = new ORecordId(23, 1);
        EntityAdapter owner = mock(EntityAdapter.class);
        ODocument document = mock(ODocument.class);
        when(document.getIdentity()).thenReturn(orID);
        EntityMetadata entityMetadata = new AttachedEntityMetadata(owner, document);
        when(fifthComponent.getEntityMetadata()).thenReturn(entityMetadata);

        List<Component> components = Arrays.asList(mockComponent("1.0.1", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.0.2", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.0.3", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.0.4", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                fifthComponent,
                mockComponent("1.0.6", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.0.7", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.0.8", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.0.9", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.1.0", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.1.1", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.1.2", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.1.3", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.1.4", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                mockComponent("1.1.5", new DateTime(2017, 12, 10, 23, 0, 0), "1.0-SNAPSHOT"),
                fourthComponent);
        when(storageTx.findComponents(Matchers.any(String.class),
                Matchers.any(Map.class),
                Matchers.any(Iterable.class),
                Matchers.eq("order by attributes.maven2.baseVersion asc limit " + PAGINATION_LIMIT)
        )).thenReturn(components);
        when(storageTx.findComponents(Matchers.any(String.class),
                Matchers.any(Map.class),
                Matchers.any(Iterable.class),
                Matchers.eq("order by attributes.maven2.baseVersion desc limit " + PAGINATION_LIMIT)
        )).thenReturn(components.subList(0,PAGINATION_LIMIT));
        orID = new ORecordId(22, 1);
        when(document.getIdentity()).thenReturn(orID);
        when(fourthComponent.getEntityMetadata()).thenReturn(entityMetadata);
        when(storageTx.countComponents(Matchers.any(String.class),
                Matchers.any(Map.class),
                Matchers.any(Iterable.class),
                Matchers.anyString()

        )).thenReturn(16L);


        //When
        facet.purgeUnusedReleases(GROUP_ID, ARTIFACT_ID, VERSION_OPTION, 6);


        //Then
        verify(storageTx, times(2)).findComponents(Matchers.any(String.class),
                Matchers.any(Map.class),
                Matchers.any(Iterable.class),
                Matchers.anyString());
    }

    @Test
    public void test_the_purge_of_unused_releases_when_the_number_of_releases_to_keep_is_higher_than_the_total_components() throws Exception {
        //Given
        initFirstRepository();

        when(storageTx.countComponents(Matchers.any(String.class),
                Matchers.any(Map.class),
                Matchers.any(Iterable.class),
                Matchers.anyString()

        )).thenReturn(16L);

        //When
        facet.purgeUnusedReleases(GROUP_ID, ARTIFACT_ID, VERSION_OPTION, 16);


        //Then
        verify(storageTx, times(0)).findComponents(Matchers.any(String.class),
                Matchers.any(Map.class),
                Matchers.any(Iterable.class),
                Matchers.anyString());
    }
}
