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
/*global Ext, NX*/

/**
 * Repository model.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.model.Repository', {
  extend: 'Ext.data.Model',
  idProperty: 'name',
  fields: [
    {name: 'name', type: 'string', sortType: 'asUCText'},
    {name: 'type', type: 'string', sortType: 'asUCText'},
    {name: 'format', type: 'string', sortType: 'asUCText'},
    {name: 'recipe', type: 'string', sortType: 'asUCText'},
    {name: 'online', type: 'boolean'},
    {name: 'status', type: 'auto' /*object*/},
    {name: 'attributes', type: 'auto' /*object*/},
    {name: 'url', type: 'string', sortType: 'asUCText'},
    {name: 'blobCount', type: 'int'},
    {name: 'size', type: 'int'}
  ]
});
