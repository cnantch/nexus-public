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
package org.sonatype.nexus.repository.browse.api

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.browse.internal.api.RepositoryItemIDXO
import org.sonatype.nexus.repository.storage.Asset

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder

import static org.sonatype.nexus.common.entity.EntityHelper.id
import static org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer.ID
import static org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer.NAME
import static org.sonatype.nexus.repository.storage.Asset.CHECKSUM
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_ATTRIBUTES

/**
 * Asset transfer object for REST APIs.
 *
 * @since 3.3
 */
@CompileStatic
@Builder
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['id'])
class AssetXO
{
  String downloadUrl

  String path

  String id

  String repository

  String format

  Map checksum

  static AssetXO fromAsset(final Asset asset, final Repository repository) {
    String internalId = id(asset).getValue()

    Map checksum = asset.attributes().child(CHECKSUM).backing()

    return builder()
        .path(asset.name())
        .downloadUrl(repository.url + '/' + asset.name())
        .id(new RepositoryItemIDXO(repository.name, internalId).value)
        .repository(repository.name)
        .checksum(checksum)
        .format(repository.format.value)
        .build()
  }

  static AssetXO fromElasticSearchMap(final Map map, final Repository repository) {
    String internalId = (String) map.get(ID)

    Map checksum = (Map) map.get(P_ATTRIBUTES, [:])[CHECKSUM]

    return builder()
        .path((String) map.get(NAME))
        .downloadUrl(repository.url + '/' + (String) map.get(NAME))
        .id(new RepositoryItemIDXO(repository.name, internalId).value)
        .repository(repository.name)
        .checksum(checksum)
        .format(repository.format.value)
        .build()
  }
}
