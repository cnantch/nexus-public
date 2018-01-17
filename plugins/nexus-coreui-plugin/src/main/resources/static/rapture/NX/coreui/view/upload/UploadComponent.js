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
 * Upload Component View
 *
 * @since 3.7
 */
Ext.define('NX.coreui.view.upload.UploadComponent', {
    extend: 'NX.view.AddPanel',
    alias: 'widget.nx-coreui-upload-component',
    requires: [
      'NX.I18n'
    ],

    /**
     * @override
     */
    initComponent: function() {
      var me = this;
      me.store = 'UploadDefinition';

      me.callParent();
    },

    loadRecord: function(uploadDefinition, repository) {
      var me = this;

      me.uploadDefinition = uploadDefinition;
      me.repository = repository;

      me.removeAll(true);
      me.add(
        {
          xtype: 'panel',
          ui: 'nx-inset',
          items: [{
            xtype: 'nx-settingsform',
            api: {
              submit: 'NX.direct.coreui_Upload.doUpload'
            },
            buttons: [{
              text: NX.I18n.get('FeatureGroups_Upload_Form_Upload_Button'),
              action: 'upload',
              formBind: true,
              ui: 'nx-primary'
            }, {
              text: NX.I18n.get('FeatureGroups_Upload_Form_Discard_Button'),
              action: 'cancel'
            }],
            items: [{
              xtype: 'fieldcontainer',
              width: 700,
              items: [{
                xtype: 'fieldset',
                cls: 'nx-form-section',
                itemId: 'nx-coreui-upload-component-assets',
                nextSuffix: 0,
                title: NX.I18n.get('FeatureGroups_Upload_Asset_Form_Title'),
                items: [
                  me.createHeader(),
                  me.createRow(),
                  {
                    xtype: 'button',
                    text: NX.I18n.get('FeatureGroups_Upload_Asset_Form_Add_Asset_Button'),
                    action: 'add_asset',
                    hidden: !me.uploadDefinition.get('multipleUpload')
                  }
                ]
              }, {
                xtype: 'fieldset',
                cls: 'nx-form-section',
                itemId: 'nx-coreui-upload-component-fields',
                layout: {
                  type: 'vbox',
                  align: 'stretch'
                },
                title: NX.I18n.get('FeatureGroups_Upload_Component_Form_Title'),
                items: me.uploadDefinition.get('componentFields').map(me.createComponentField, this),
                hidden: me.uploadDefinition.get('componentFields').length === 0
              }, {
                xtype: 'hidden',
                name: 'repositoryName',
                value: repository.get('name')
              }]
            }],
            dockedItems: [{
              xtype: 'panel',
              itemId: 'nx-coreui-upload-success-message',
              ui: 'nx-drilldown-message',
              cls: 'nx-drilldown-info',
              iconCls: NX.Icons.cls('tick', 'x16'),
              hidden: true,
              dock: 'top'
            }]
          }]
        }
      );

      if (me.repository.get('format') === 'maven2') {
        me.down('textfield[name=packaging]').setDisabled(true);
      }
    },

    addAssetRow: function() {
      var me = this,
          assetPanel = me.down('#nx-coreui-upload-component-assets');

      if (assetPanel) {
        var row = me.createRow(),
            fields = row.items,
            suffix = assetPanel.nextSuffix++;

        fields.forEach(function(field) {
          field.name += suffix;
        });

        assetPanel.insert(assetPanel.items.items.length - 1, row);
      }
    },

    createHeader: function() {
      var me = this,
          header = {
            xtype: 'container',
            layout: {
              type: 'hbox',
              align: 'stretch'
            },
            items: [
              {
                xtype: 'text',
                text: NX.I18n.get('FeatureGroups_Upload_Asset_Form_File_Label'),
                cls: 'nx-table-header-label',
                width: 305,
                height: 25
              }
            ]
          },
          assetFields = me.uploadDefinition.get('assetFields');

      assetFields.forEach(function(assetField) {
        header.items.push({
          xtype: 'text',
          text: assetField.displayName,
          cls: 'nx-table-header-label',
          width: 145,
          height: 25
        });
      });

      return header;
    },

    createRow: function() {
      var me = this,
          row = {
              xtype: 'panel',
              layout: 'column',
              cls: 'nx-repeated-row',
              items: [{
                  xtype: 'fileuploadfield',
                  cls: 'nx-float-left',
                  allowBlank: false,
                  submitValue: true,
                  clearOnSubmit: false,
                  buttonText: NX.I18n.get('FeatureGroups_Upload_Form_Browse_Button'),
                  buttonConfig: {
                      glyph: 'xf016@FontAwesome' /* fa-file-o */
                  },
                  name: 'file',
                  width: '150px',
                  listeners: {
                    change: function() {
                      me.fileChange.apply(me, arguments);
                    }
                  }
              }]
          };

      var assetFields = me.uploadDefinition.get('assetFields');

      assetFields.forEach(function(assetField) {
          row.items.push(me.createAssetField(assetField));
      });

      row.items.push({
        xtype: 'button',
        text: NX.I18n.get('FeatureGroups_Upload_Asset_Form_Remove_Button'),
        action: 'remove_upload_asset',
        hidden: true
      });

      return row;
    },

    createComponentField: function (field) {
      var me = this;
      return me.createField(field, false);
    },

    createAssetField: function(field) {
      var me = this;
      return me.createField(field, true, '100px', 'nx-float-left', me.validateUniqueAsset);
    },

    createField: function (field, hideLabel, width, cls, validator) {
      var widget = {
        allowBlank: field.optional,
        name: field.name,
        width: width,
        cls: cls,
        validator: validator,
        listeners: {
          change: function() {
            this.up('form').isValid();
          }
        }
      };

      if (field.type === 'STRING') {
        widget.xtype = 'textfield';
        widget.fieldLabel = hideLabel ? undefined : field.displayName;
        widget.helpText = field.helpText || undefined;
      }
      else if (field.type === 'BOOLEAN') {
        widget.xtype = 'checkbox';
        widget.boxLabel = field.displayName;
      }
      return widget;
    },

    fileChange: function(fileField, value) {
      var me = this,
          regexMap = me.uploadDefinition.get('regexMap'),
          filename, match, suffix;

      if (regexMap && value) {
        filename = value.substring(value.lastIndexOf(value.startsWith('/') ? '/' : '\\') + 1);
        match = filename.match(regexMap.regex);

        if (match) {
          suffix = (fileField.name.match("file(\\d*)") || [])[1] || '';
          regexMap.fieldList.forEach(function(field, index) {
            if (field) {
              var input = me.down('textfield[name=' + field + suffix + ']') || me.down('textfield[name=' + field + ']') ;
              if (input) {
                input.setValue(match[index + 1]);
              }
            }
          });
        }
      }
    },

    validateUniqueAsset: function() {
      var me = this,
          assetRow = me.up(),
          assetValue = {},
          assetRows = Ext.Array.difference(assetRow.up().query('panel'), [assetRow]);

      assetRow.query('field').forEach(function(field) {
        assetValue[field.name.replace(/[0-9]/g, '')] = field.value;
      });

      var duplicate = Ext.Array.findBy(assetRows, function(row) {
        var isDuplicate = true;
        Object.keys(assetValue).forEach(function(fieldName) {
          isDuplicate = isDuplicate &&
              (row.query('field[name^=' + fieldName + ']')[0].value === assetValue[fieldName]);
        });
        return isDuplicate;
      });

      return (duplicate === null) || NX.I18n.get('FeatureGroups_Upload_Asset_Form_Not_Unique_Error_Message');
    }

  });
