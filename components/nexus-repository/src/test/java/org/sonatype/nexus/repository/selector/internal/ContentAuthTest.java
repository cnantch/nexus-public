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
package org.sonatype.nexus.repository.selector.internal;

import java.util.Collections;
import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;

import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link ContentAuth}.
 */
public class ContentAuthTest
    extends TestSupport
{
  private static final String REPOSITORY_NAME = "repository-name";

  private static final String PATH = "path";

  private static final String FORMAT = "format";

  @Mock
  ContentAuthHelper contentAuthHelper;

  @Mock
  ODocument assetDocument;

  @Mock
  ODocument componentDocument;

  @Mock
  ODocument bucketDocument;

  @Mock
  OCommandRequest commandRequest;

  @Mock
  ODatabaseDocumentInternal database;

  ContentAuth underTest;

  @Before
  public void setup() {
    when(bucketDocument.getRecord()).thenReturn(bucketDocument);
    when(bucketDocument.field("repository_name", String.class)).thenReturn(REPOSITORY_NAME);
    when(bucketDocument.getIdentity()).thenReturn(mock(ORID.class));

    when(assetDocument.getClassName()).thenReturn("asset");
    when(assetDocument.getRecord()).thenReturn(assetDocument);
    when(assetDocument.field("bucket", OIdentifiable.class)).thenReturn(bucketDocument);
    when(assetDocument.field("name", String.class)).thenReturn(PATH);
    when(assetDocument.field("format", String.class)).thenReturn(FORMAT);

    when(componentDocument.getClassName()).thenReturn("component");
    when(componentDocument.getRecord()).thenReturn(componentDocument);
    when(componentDocument.field("bucket", OIdentifiable.class)).thenReturn(bucketDocument);
    when(componentDocument.getDatabase()).thenReturn(database);
    when(componentDocument.getIdentity()).thenReturn(mock(ORID.class));

    when(commandRequest.execute(any(Map.class))).thenReturn(Collections.singletonList(assetDocument));
    when(database.command(any(OCommandRequest.class))).thenReturn(commandRequest);

    underTest = new ContentAuth(contentAuthHelper);
  }

  @Test
  public void testAssetPermitted() {
    when(contentAuthHelper.checkAssetPermissions(assetDocument, new String[]{REPOSITORY_NAME})).thenReturn(true);
    assertThat(underTest.execute(underTest, null, null, new Object[] { assetDocument, REPOSITORY_NAME }, null), is(true));
    verify(contentAuthHelper, times(1)).checkAssetPermissions(assetDocument, new String[]{REPOSITORY_NAME});
  }

  @Test
  public void testAssetNotPermitted() {
    when(contentAuthHelper.checkAssetPermissions(assetDocument, new String[]{REPOSITORY_NAME})).thenReturn(false);
    assertThat(underTest.execute(underTest, null, null, new Object[] { assetDocument, REPOSITORY_NAME }, null), is(false));
    verify(contentAuthHelper, times(1)).checkAssetPermissions(assetDocument, new String[]{REPOSITORY_NAME});
  }

  @Test
  public void testComponentPermitted() {
    when(contentAuthHelper.checkAssetPermissions(assetDocument, new String[]{REPOSITORY_NAME})).thenReturn(true);
    assertThat(underTest.execute(underTest, null, null, new Object[] { componentDocument, REPOSITORY_NAME }, null), is(true));
    verify(contentAuthHelper, times(1)).checkAssetPermissions(assetDocument, new String[]{REPOSITORY_NAME});
  }

  @Test
  public void testComponentNotPermitted() {
    when(contentAuthHelper.checkAssetPermissions(assetDocument, new String[]{REPOSITORY_NAME})).thenReturn(false);
    assertThat(underTest.execute(underTest, null, null, new Object[] { componentDocument, REPOSITORY_NAME }, null), is(false));
    verify(contentAuthHelper, times(1)).checkAssetPermissions(assetDocument, new String[]{REPOSITORY_NAME});
  }

  @Test
  public void testComponentPermitted_withGroupRepo() {
    when(contentAuthHelper.checkAssetPermissions(assetDocument, new String[]{"group_repo"})).thenReturn(true);
    assertThat(underTest.execute(underTest, null, null, new Object[] { componentDocument, "group_repo" }, null), is(true));
    verify(contentAuthHelper).checkAssetPermissions(assetDocument, new String[]{"group_repo"});
    verify(contentAuthHelper, never()).checkAssetPermissions(assetDocument, new String[]{REPOSITORY_NAME});
  }

  @Test
  public void testComponentNotPermitted_withGroupRepo() {
    assertThat(underTest.execute(underTest, null, null, new Object[] { componentDocument, "group_repo" }, null), is(false));
    verify(contentAuthHelper).checkAssetPermissions(assetDocument, new String[]{"group_repo"});
    verify(contentAuthHelper, never()).checkAssetPermissions(assetDocument, new String[]{REPOSITORY_NAME});
  }

  @Test
  public void testAssetPermitted_jexlOnly() {
    when(contentAuthHelper.checkAssetPermissionsJexlOnly(assetDocument, new String[] { REPOSITORY_NAME })).thenReturn(
        true);
    assertThat(underTest.execute(underTest, null, null, new Object[] { assetDocument, REPOSITORY_NAME, true }, null),
        is(true));
    verify(contentAuthHelper, times(1)).checkAssetPermissionsJexlOnly(assetDocument, new String[] { REPOSITORY_NAME });
  }

  @Test
  public void testAssetNotPermitted_jexlOnly() {
    when(contentAuthHelper.checkAssetPermissionsJexlOnly(assetDocument, new String[] { REPOSITORY_NAME })).thenReturn(
        false);
    assertThat(underTest.execute(underTest, null, null, new Object[] { assetDocument, REPOSITORY_NAME, true }, null),
        is(false));
    verify(contentAuthHelper, times(1)).checkAssetPermissionsJexlOnly(assetDocument, new String[] { REPOSITORY_NAME });
  }

  @Test
  public void testComponentPermitted_jexlOnly() {
    when(contentAuthHelper.checkAssetPermissionsJexlOnly(assetDocument, new String[] { REPOSITORY_NAME })).thenReturn(
        true);
    assertThat(
        underTest.execute(underTest, null, null, new Object[] { componentDocument, REPOSITORY_NAME, true }, null),
        is(true));
    verify(contentAuthHelper, times(1)).checkAssetPermissionsJexlOnly(assetDocument, new String[] { REPOSITORY_NAME });
  }

  @Test
  public void testComponentNotPermitted_jexlOnly() {
    when(contentAuthHelper.checkAssetPermissionsJexlOnly(assetDocument, new String[] { REPOSITORY_NAME })).thenReturn(
        false);
    assertThat(
        underTest.execute(underTest, null, null, new Object[] { componentDocument, REPOSITORY_NAME, true }, null),
        is(false));
    verify(contentAuthHelper, times(1)).checkAssetPermissionsJexlOnly(assetDocument, new String[] { REPOSITORY_NAME });
  }

  @Test
  public void testComponentPermitted_withGroupRepo_jexlOnly() {
    when(contentAuthHelper.checkAssetPermissionsJexlOnly(assetDocument, new String[] { "group_repo" }))
        .thenReturn(true);
    assertThat(underTest.execute(underTest, null, null, new Object[] { componentDocument, "group_repo", true }, null),
        is(true));
    verify(contentAuthHelper).checkAssetPermissionsJexlOnly(assetDocument, new String[] { "group_repo" });
    verify(contentAuthHelper, never()).checkAssetPermissionsJexlOnly(assetDocument, new String[] { REPOSITORY_NAME });
  }

  @Test
  public void testComponentNotPermitted_withGroupRepo_jexlOnly() {
    assertThat(underTest.execute(underTest, null, null, new Object[] { componentDocument, "group_repo", true }, null),
        is(false));
    verify(contentAuthHelper).checkAssetPermissionsJexlOnly(assetDocument, new String[] { "group_repo" });
    verify(contentAuthHelper, never()).checkAssetPermissionsJexlOnly(assetDocument, new String[] { REPOSITORY_NAME });
  }
}
