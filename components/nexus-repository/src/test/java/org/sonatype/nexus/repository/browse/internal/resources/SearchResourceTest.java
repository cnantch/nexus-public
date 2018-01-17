/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.repository.browse.internal.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.browse.BrowseResult;
import org.sonatype.nexus.repository.browse.SearchResourceExtension;
import org.sonatype.nexus.repository.browse.api.AssetXO;
import org.sonatype.nexus.repository.browse.api.ComponentXO;
import org.sonatype.nexus.repository.browse.api.ComponentXOFactory;
import org.sonatype.nexus.repository.search.SearchService;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.rest.Page;

import com.google.common.collect.ImmutableSet;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.jboss.resteasy.spi.ResteasyUriInfo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.stubbing.Answer;

import static com.google.common.collect.ImmutableMap.of;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.repository.browse.internal.resources.ResourcesTestUtils.createAsset;
import static org.sonatype.nexus.repository.browse.internal.resources.ResourcesTestUtils.createComponent;

public class SearchResourceTest
    extends RepositoryResourceTestSupport
{
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  SearchService searchService;

  @Mock
  SearchResponse searchResponse;

  @Mock
  SearchHits searchHits;

  @Mock
  SearchHit searchHitMaven;

  @Mock
  SearchHit searchHitNpm;

  @Captor
  ArgumentCaptor<QueryBuilder> queryBuilderArgumentCaptor;

  @Mock
  Repository repository;

  @Mock
  BrowseResult<Asset> browseResult;

  @Spy
  SearchResourceExtension searchResourceExtension = new TestSearchResourceExtension();

  SearchResource underTest;

  @Before
  public void setup() {
    configureMockedRepository(repository, "test-repo", "http://localhost:8081/test");
    setupResponse();

    underTest = new SearchResource(searchUtils, browseService, searchService, new TokenEncoder(),
        new ComponentXOFactory(emptySet()), ImmutableSet.of(searchResourceExtension));
  }

  private void setupResponse() {
    when(searchResponse.getHits()).thenReturn(searchHits);

    List assets = newArrayList(
        createAsset("antlr.jar", "maven2", "first-sha1", of("extension", "jar", "classifier", "foo")),
        createAsset("antlr.pom", "maven2", "second-sha1", of("extension", "pom"))
    );
    Map<String, Object> component = createComponent("foo", "test-repo", "format", "test", "1.0", assets);

    when(searchHitMaven.sourceAsMap()).thenReturn(component);
    when(searchHitMaven.getSource()).thenReturn(component);
    when(searchHitMaven.getId()).thenReturn("id1");

    when(browseService.browseComponentAssets(eq(repository), anyString())).thenReturn(browseResult);
    Asset mockedAsset = getMockedAsset("first", "one");
    Asset mockedAsset1 = getMockedAsset("second", "two");

    when(browseResult.getResults()).thenReturn(asList(mockedAsset,
        mockedAsset1));

    List assets2 = newArrayList(
        createAsset("bar.one", "npm", "third-sha1", of("extension", "one")),
        createAsset("bar.two", "npm", "fourth-sha1", of("extension", "two")),
        createAsset("bar.three", "npm", "fifth-sha1", of("extension", "three"))
    );
    Map<String, Object> component2 = createComponent("bar", "test-repo", "npm", "group2", "2.0", assets2);
    when(searchHitNpm.sourceAsMap()).thenReturn(component2);
    when(searchHitNpm.getSource()).thenReturn(component2);
    when(searchHitNpm.getId()).thenReturn("id2");

    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitMaven, searchHitNpm});
  }

  @Test
  public void testSearch() {
    //the expected query
    QueryBuilder expected = boolQuery()
        .filter(termQuery("format", "maven2"));

    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);

    Page<ComponentXO> componentPage = underTest.search(null, uriInfo("?format=maven2"));

    List<ComponentXO> items = componentPage.getItems();

    assertThat(items, hasSize(2));

    ComponentXO componentXO = items.stream().filter(item -> item.getName().equals("foo")).findFirst().get();
    assertThat(componentXO.getGroup(), is("test"));
    assertThat(componentXO.getVersion(), is("1.0"));

    ComponentXO componentXO1 = items.stream().filter(item -> item.getName().equals("bar")).findFirst().get();
    assertThat(componentXO1.getGroup(), is("group2"));
    assertThat(componentXO1.getVersion(), is("2.0"));
    assertThat(componentXO1.getAssets().get(0).getChecksum().get("sha1"), is("87acec17cd9dcd20a716cc2cf67417b71c8a7016"));

    assertThat(queryBuilderArgumentCaptor.getValue().toString(), is(expected.toString()));
    verify(searchResourceExtension, times(2)).updateComponentXO(any(ComponentXO.class), any(SearchHit.class));
  }

  @Test
  public void testSearchWithChecksum() {
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitNpm});

    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
            .thenReturn(searchResponse);

    Page<AssetXO> assets = underTest.searchAssets(null, uriInfo("?format=npm"));

    List<AssetXO> items = assets.getItems();

    assertThat(items, hasSize(3));

    AssetXO assetXO = items.stream().filter(item -> item.getPath().equals("bar.one")).findFirst().get();
    assertThat(assetXO.getChecksum().get("sha1"), is("third-sha1"));
  }

  @Test
  public void testSearch_Using_Long_And_Short_AssetParamNames() {
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitMaven, searchHitNpm});

    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);

    Page<AssetXO> assets_longName = underTest.searchAssets(null, uriInfo("?assets.attributes.maven2.extension=jar"));
    List<AssetXO> items_longName = assets_longName.getItems();
    assertThat(items_longName, hasSize(1));

    Page<AssetXO> assets_shortName = underTest.searchAssets(null, uriInfo("?maven.extension=jar"));
    List<AssetXO> items_shortName = assets_shortName.getItems();
    assertThat(items_shortName, hasSize(1));
  }

  @Test
  public void testSearch_When_Multiple_Aliases_For_An_AssetAttribute() {
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitMaven, searchHitNpm});

    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);

    Page<AssetXO> assets_shortName = underTest.searchAssets(null, uriInfo("?maven.extension=jar"));
    List<AssetXO> items_shortName = assets_shortName.getItems();
    assertThat(items_shortName, hasSize(1));

    //Search using alternate alias mapped to the same attribute as maven.extension
    Page<AssetXO> assets_alternateName = underTest.searchAssets(null, uriInfo("?mvn.extension=jar"));
    List<AssetXO> items_alternateName = assets_alternateName.getItems();
    assertThat(items_alternateName, hasSize(1));
  }

  @Test
  public void testSearch_With_UnMapped_Long_AssetAttribute() {
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitMaven});

    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);

    //Positive case, 'classifier' is unmapped
    Page<AssetXO> assets_validAttribute = underTest.searchAssets(null, uriInfo("?assets.attributes.maven2.classifier=foo"));
    List<AssetXO> items_assets_validAttribute  = assets_validAttribute.getItems();
    assertThat(items_assets_validAttribute , hasSize(1));

    //Negative case
    Page<AssetXO> assets_inValidAttribute = underTest.searchAssets(null, uriInfo("?assets.attributes.maven3.classifier=foo"));
    List<AssetXO> items_inValidAttribute = assets_inValidAttribute.getItems();
    assertThat(items_inValidAttribute, hasSize(0));
  }

  @Test
  public void testSearchAndDownload_NoAssetParams_WillReturnAll() {
    // mock Elastic is only returning npm
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitNpm});

    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);

    Page<AssetXO> assets = underTest.searchAssets(null, uriInfo("?format=npm"));

    List<AssetXO> items = assets.getItems();

    assertThat(items, hasSize(3));

    AssetXO assetXO = items.stream().filter(item -> item.getPath().equals("bar.one")).findFirst().get();
    assertThat(assetXO.getRepository(), is("test-repo"));
    assertThat(assetXO.getDownloadUrl(), is("http://localhost:8081/test/bar.one"));

    AssetXO assetXO2 = items.stream().filter(item -> item.getPath().equals("bar.three")).findFirst().get();
    assertThat(assetXO2.getRepository(), is("test-repo"));
    assertThat(assetXO2.getDownloadUrl(), is("http://localhost:8081/test/bar.three"));

    //the expected query
    QueryBuilder expected = boolQuery()
        .filter(termQuery("format", "npm"));
    assertThat(queryBuilderArgumentCaptor.getValue().toString(), is(expected.toString()));
  }

  @Test
  public void testSearchAndDownload_SpecificAssetParam_WillReturnOne() {
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitNpm});

    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);

    Page<AssetXO> assets = underTest.searchAssets(null, uriInfo("?format=npm&sha1=fifth-sha1"));

    List<AssetXO> items = assets.getItems();

    assertThat(items, hasSize(1));

    AssetXO assetXO = items.get(0);
    assertThat(assetXO.getPath(), equalTo("bar.three"));
    assertThat(assetXO.getRepository(), is("test-repo"));
    assertThat(assetXO.getDownloadUrl(), is("http://localhost:8081/test/bar.three"));

    //the expected query
    QueryBuilder expected = boolQuery()
        .filter(termQuery("assets.attributes.checksum.sha1", "fifth-sha1"))
        .filter(termQuery("format", "npm"));
    assertThat(queryBuilderArgumentCaptor.getValue().toString(), is(expected.toString()));
  }

  @Test
  public void testSearchAndDownload_SpecificAssetParam_NotFound() {
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitNpm});
    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);

    Page<AssetXO> assets = underTest.searchAssets(null, uriInfo("?format=npm&sha1=notfound"));

    List<AssetXO> items = assets.getItems();

    assertThat(items, hasSize(0));
  }

  @Test
  public void testSearchAndDownload_SpecificAssetParam_NotFound_404_HTTP_RESPONSE() {
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitNpm});
    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);
    try {
      underTest.searchAndDownloadAssets(uriInfo("?format=npm&sha1=notfound"));
    }
    catch (WebApplicationException webEx) {
      assertThat(webEx.getResponse().getStatus(), equalTo(404));
    }
  }

  @Test
  public void testSearchAndDownload_SpecificAssetParam_NotFound_400_HTTP_RESPONSE() {
    // mock Elastic is only returning npm
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitNpm});
    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);
    try {
      underTest.searchAndDownloadAssets(uriInfo("?format=npm"));
    }
    catch (WebApplicationException webEx) {
      assertThat(webEx.getResponse().getStatus(), equalTo(400));
    }
  }

  @Test
  public void testSearchAndDownload_SpecificAssetParam_AssetFound_302_REDIRECT() {
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitNpm});
    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);
    Response response = underTest.searchAndDownloadAssets(uriInfo("?format=npm&sha1=fifth-sha1"));
    assertThat(response.getStatus(), equalTo(302));
    assertThat(response.getHeaderString("Location"), is("http://localhost:8081/test/bar.three"));
  }

  @Test
  public void testSearchAndDownload_WithLongAssetParam_AssetFound_302_REDIRECT() {
    when(searchHits.hits()).thenReturn(new SearchHit[]{searchHitMaven});
    when(searchService.search(queryBuilderArgumentCaptor.capture(), eq(emptyList()), eq(0), eq(50)))
        .thenReturn(searchResponse);
    Response response = underTest.searchAndDownloadAssets(uriInfo("?assets.attributes.maven2.extension=jar"));
    assertThat(response.getStatus(), equalTo(302));
    assertThat(response.getHeaderString("Location"), is("http://localhost:8081/test/antlr.jar"));
  }

  @Test
  public void testBuildQuery() {
    //the expected query
    QueryBuilder expected = boolQuery()
        .must(queryStringQuery("someKindOfStringQuery"))
        .filter(termQuery("format", "maven2"))
        .filter(termQuery("arbitrary.param", "random"));

    String uri = "?format=maven2" +
        "&arbitrary.param=random" +
        "&sha256=" + //this one should be ignored because it is empty
        "&q=someKindOfStringQuery";
    QueryBuilder actual = searchUtils.buildQuery(uriInfo(uri));

    assertThat(actual.toString(), is(expected.toString()));
  }

  @Test
  public void testGetAssetParams() {
    MultivaluedMap<String, String> result = underTest.getAssetParams(uriInfo("?sha1=thisisthesha1&name=antlr"));
    assertThat(result.size(), equalTo(1));
    assertThat(result, hasKey("sha1"));

    // put every single search param into the pam
    StringBuilder sb = new StringBuilder();
    Set<String> allKeys = searchUtils.getSearchParameters().keySet();
    allKeys.forEach(s -> sb.append(s).append("=valueDoesNotMatter&"));

    // asert only assert params remain
    result = underTest.getAssetParams(uriInfo("?" + sb.toString()));
    assertThat(result.size(), equalTo(searchUtils.getAssetSearchParameters().size()));
    assertThat(result.keySet(), equalTo(searchUtils.getAssetSearchParameters().keySet()));
  }

  @Test
  public void testGetAssetParams_ForLongAssetParamsEntries() {
    //Positive case for long asset search param entries
    MultivaluedMap<String, String> longNameResult = underTest.getAssetParams(
        uriInfo("?assets.attributes.maven2.extension=jar"));
    assertThat(longNameResult.size(), equalTo(1));
    assertThat(longNameResult, hasKey("assets.attributes.maven2.extension"));

    //Verify negative case
    MultivaluedMap<String, String> negativeCaseResult = underTest.getAssetParams(
        uriInfo("?attributes.not.asset.jar"));
    assertThat(negativeCaseResult.size(), equalTo(0));
  }

  @Test
  public void testGetAssetParams_ForParamsNotInSearchMappings() {
    //Verify a query string can contain asset attributes not in the search mappings
    MultivaluedMap<String, String> shortNameResult = underTest.getAssetParams(
        uriInfo("?assets.attributes.maven2.classifier=jar"));
    assertThat(shortNameResult.size(), equalTo(1));
  }

  @Test
  public void testFilterAsset() {
    // no asset params will return all assets
    assertTrue(underTest.filterAsset(
        createAsset("antlr.jar", "maven2", "first-sha1", of("extension", "jar")),
        new MultivaluedHashMap<>()));

    //checking for consistency in handling short and long parameter names
    assertTrue(underTest.filterAsset(
        createAsset("antlr.jar", "maven2", "first-sha1", of("extension", "jar")),
        uriInfo("?assets.attributes.maven2.extension=jar").getQueryParameters()));

    assertTrue(underTest.filterAsset(
        createAsset("antlr.jar", "maven2", "first-sha1", of("extension", "jar")),
        uriInfo("?maven.extension=jar").getQueryParameters()));

    // regular positive case
    assertTrue(underTest.filterAsset(
        createAsset("antlr.jar", "maven2", "first-sha1", of("extension", "jar")),
        uriInfo("?sha1=first-sha1").getQueryParameters()));

    // regular negative case
    assertFalse(underTest.filterAsset(
        createAsset("antlr.jar", "maven2", "first-sha1", of("extension", "jar")),
        uriInfo("?sha1=another-sha1").getQueryParameters()));
  }

  private UriInfo uriInfo(final String uri) {
    return new ResteasyUriInfo(URI.create(uri));
  }

  private class TestSearchResourceExtension
      implements SearchResourceExtension
  {
    @Override
    public ComponentXO updateComponentXO(final ComponentXO componentXO, final SearchHit hit) {
      return componentXO;
    }
  }
}
