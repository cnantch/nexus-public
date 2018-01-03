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
 * Configuration for Yum hosted repodata level.
 *
 * @since 3.next
 */
Ext.define('NX.coreui.view.repository.facet.YumHostedFacet', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.nx-coreui-repository-yum-hosted-facet',
  requires: [
    'NX.I18n',
    'Ext.form.ComboBox'
  ],

  /**
   * @override
   */
  initComponent: function() {
    var me = this;

    me.items = [
      {
        xtype: 'fieldset',
        cls: 'nx-form-section',
        title: NX.I18n.get('Repository_Facet_YumHostedFacet_Title'),
        items: [
          {
            xtype: 'combo',
            name: 'attributes.yum.repodataDepth',
            fieldLabel: NX.I18n.get('Repository_Facet_YumHostedFacet_RepodataDepth_FieldLabel'),
            helpText: NX.I18n.get('Repository_Facet_YumHostedFacet_RepodataDepth_HelpText'),
            forceSelection: true,
            editable: false,
            allowBlank: false,
            store : [0, 1, 2, 3, 4, 5]
          }
        ]
      }
    ];

    me.callParent();
  }
});

