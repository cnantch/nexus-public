/**
 * Task type model.
 *
 * @since 3.7
 */
Ext.define('NX.coreui.model.TaskOptionPurge', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'name', type: 'string', sortType: 'asUCText'},
        {name: 'description', type: 'string', sortType: 'asUCText'}
    ]
});
