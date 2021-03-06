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
package org.sonatype.nexus.repository.group;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.group.GroupHandler.DispatchedRepositories;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class GroupHandlerTest
    extends TestSupport
{
  private static final String REPOSITORY_1 = "repository1";

  private static final String REPOSITORY_2 = "repository2";

  private static final String REPOSITORY_3 = "repository3";

  @Mock
  private Repository repository1;

  @Mock
  private Repository repository2;

  @Mock
  private Repository repository3;

  private DispatchedRepositories underTest;

  @Before
  public void setUp() throws Exception {
    underTest= new DispatchedRepositories();
    when(repository1.toString()).thenReturn(REPOSITORY_1);
    when(repository1.getName()).thenReturn(REPOSITORY_1);
    when(repository2.toString()).thenReturn(REPOSITORY_2);
    when(repository2.getName()).thenReturn(REPOSITORY_2);
    when(repository3.toString()).thenReturn(REPOSITORY_3);
    when(repository3.getName()).thenReturn(REPOSITORY_3);
  }

  @Test
  public void checkDispatchedRepositoryInsertionWillPreserveOrder() {
    underTest.add(repository1);
    underTest.add(repository2);
    underTest.add(repository3);

    assertThat(underTest.toString(),
        containsString(String.format("[%s, %s, %s]", REPOSITORY_1, REPOSITORY_2, REPOSITORY_3)));
  }

  @Test
  public void checkDispatchedRepositoryInsertionWillPreserveOrderWhenAlternateSequence() {
    underTest.add(repository3);
    underTest.add(repository1);
    underTest.add(repository2);

    assertThat(underTest.toString(),
        containsString(String.format("[%s, %s, %s]", REPOSITORY_3, REPOSITORY_1, REPOSITORY_2)));
  }
}
