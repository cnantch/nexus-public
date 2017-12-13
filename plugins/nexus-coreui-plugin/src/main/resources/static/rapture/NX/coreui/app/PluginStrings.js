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
 * CoreUi plugin strings.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.app.PluginStrings', {
  '@aggregate_priority': 90,

  singleton: true,
  requires: [
    'NX.I18n'
  ],

  /**
   * String keys.
   *
   * Keys follow the following naming convention:
   *
   * Class_Name>_[Component_or_Attribute]: string
   *
   * @type {Object}
   */
  keys: {
    // Browse -> Browse
    Browse_Assets_Title_Feature: 'Assets',
    Browse_Assets_Description_Feature: 'Browse assets',
    Browse_Components_Title_Feature: 'Components',
    Browse_Components_Description_Feature: 'Browse components and assets',
    Browse_BrowseComponentList_Name_Column: 'Name',
    Browse_BrowseComponentList_Group_Column: 'Group',
    Browse_BrowseComponentList_Version_Column: 'Version',
    Browse_BrowseComponentList_EmptyText_View: 'No components found in repository',
    Browse_BrowseComponentList_EmptyText_Filter: 'No components matched "$filter"',
    Browse_BrowseAssetList_Name_Column: 'Name',
    Browse_BrowseAssetList_EmptyText_View: 'No assets found in repository',
    Browse_BrowseAssetList_EmptyText_Filter: 'No assets matched "$filter"',
    Assets_Info_Repository: 'Repository',
    Assets_Info_Format: 'Format',
    Assets_Info_Group: 'Component Group',
    Assets_Info_Name: 'Component Name',
    Assets_Info_Version: 'Component Version',
    Assets_Info_Path: 'Path',
    Assets_Info_ContentType: 'Content type',
    Assets_Info_FileSize: 'File size',
    Assets_Info_Last_Downloaded: 'Last downloaded',
    Assets_Info_Locally_Cached: 'Locally cached',
    Assets_Info_BlobRef: 'Blob reference',
    Assets_Info_Blob_Created: 'Blob created',
    Assets_Info_Blob_Updated: 'Blob updated',
    Assets_Info_ContainingRepositoryName: 'Containing repo',
    Assets_Info_Downloaded_Count: 'Last 30 days',
    Assets_Info_Downloaded_Unit: 'downloads',
    Assets_Info_UploadedBy: 'Uploader',
    Assets_Info_UploadedIp: 'Uploader\'s IP Address',
    AssetInfo_Delete_Button: 'Delete asset',
    AssetInfo_Delete_Title: 'Confirm deletion?',
    AssetInfo_Delete_Success: 'Asset deleted: {0}',
    Component_Asset_Tree_Title_Feature: 'Tree',
    Component_Asset_Tree_Description_Feature: 'View tree layout of components and assets',
    Component_Asset_Tree_EmptyText_View: 'No component/assets found in repository',
    Component_Asset_Tree_Expand_Failure: 'Unable to show requested tree entry',
    Component_Asset_Tree_Filtered_EmptyText_View: 'All components have been filtered out, try using <a href="#browse/search">search</a> instead?',
    Component_Asset_Tree_Results_Warning: 'There may be additional results, try filtering the results or searching if you cannot find what you\'re looking for.',
    Component_Asset_Tree_Html_View: 'HTML View',

    ComponentDetails_Delete_Button: 'Delete component',
    ComponentDetails_Analyze_Button: 'Analyze application',
    ComponentDetails_Delete_Body: 'This will delete all asset(s) associated with the component: {0}',
    ComponentDetails_Delete_Title: 'Confirm deletion?',
    ComponentDetails_Delete_Success: 'Component deleted: {0}',
    ComponentDetails_Analyze_Success: 'Analysis in process. Email will be sent when report is ready.',
    ComponentDetails_Loading_Mask: 'Loading...',

    AnalyzeApplicationWindow_Title: 'Analyze Application',
    AnalyzeApplicationWindow_Form_Asset_FieldLabel: 'Application asset',
    AnalyzeApplicationWindow_Form_Asset_HelpText: 'Select the asset that contains the application',
    AnalyzeApplicationWindow_Form_Asset_EmptyText: 'Select an asset',
    AnalyzeApplicationWindow_Form_Email_FieldLabel: 'Email address',
    AnalyzeApplicationWindow_Form_Email_HelpText: 'The address where the summary report will be sent',
    AnalyzeApplicationWindow_Form_Password_FieldLabel: 'Report password',
    AnalyzeApplicationWindow_Form_Password_HelpText: 'A password to gain access to the detailed report',
    AnalyzeApplicationWindow_Form_ProprietaryPackages_FieldLabel: 'Proprietary packages',
    AnalyzeApplicationWindow_Form_ProprietaryPackages_HelpText: 'A comma separated list of proprietary packages',
    AnalyzeApplicationWindow_Form_Label_FieldLabel: 'Report label',
    AnalyzeApplicationWindow_Form_Label_HelpText: 'The name the report will be given',
    AnalyzeApplicationWindow_Analyze_Button: 'Analyze',
    AnalyzeApplicationWindow_Cancel_Button: 'Cancel',
    AnalyzeApplicationWindow_Form_Html: '<p>Application analysis performs a deep inspection of this application, ' +
    'identifying potential risks.  More information is available ' +
    '<a href="http://links.sonatype.com/products/insight/ac/home" target="_blank" class="x-link">here</a>.</p>',
    AnalyzeApplicationWindow_Loading_Mask: 'Loading',
    AnalyzeApplicationWindow_No_Assets_Error_Title: 'Component has no application assets',
    AnalyzeApplicationWindow_No_Assets_Error_Message: 'The component you are analyzing has no application assets, please select another component for analysis.',

    HealthCheckInfo_Most_Popular_Version_Label: 'Most popular version',
    HealthCheckInfo_Age_Label: 'Age',
    HealthCheckInfo_Popularity_Label: 'Popularity',
    HealthCheckInfo_Loading_Text: 'Loading...',
    HealthCheckInfo_Disabled_Tooltip: 'The age and popularity data is only available once Repository Health Check (RHC) has been enabled.',
    HealthCheckInfo_Error_Tooltip: 'Error retrieving component data',
    HealthCheckInfo_Quota_Tooltip: 'The query limit for age and popularity data has been reached. Contact Sonatype support to extend current quota limits.',
    HealthCheckInfo_Unavailable_Tooltip: 'No data available for this component',

    // Browse -> Search
    Search_Text: 'Search',
    Search_Description: 'Search for components by attribute',
    Search_SaveSearchFilter_Title: 'Save search filter',
    Search_SaveSearchFilter_Name_FieldLabel: 'Filter name',
    Search_SaveSearchFilter_Description_FieldLabel: 'Filter description',
    Search_Results_Limit_Message: 'Only showing the first {0} of {1} results',
    SearchCriteria_Keyword_FieldLabel: 'Keyword',
    SearchCriteria_RepositoryName_FieldLabel: 'Repository Name',
    SearchCriteria_Name_FieldLabel: 'Name',
    SearchCriteria_Format_FieldLabel: 'Format',
    SearchCriteria_Group_FieldLabel: 'Group',
    SearchCriteria_Checksum_Group: 'Checksum',
    SearchDocker_Group: 'Docker Repositories',
    SearchMaven_Group: 'Maven Repositories',
    SearchNpm_Group: 'npm Repositories',
    SearchNuget_Group: 'NuGet Repositories',
    SearchPyPi_Group: 'PyPI Repositories',
    SearchRubygems_Group: 'RubyGems Repositories',
    SearchGitLfs_Group: 'Git LFS Repositories',
    SearchYum_Group: 'Yum Repositories',
    SearchCriteria_MD5_FieldLabel: 'MD5',
    SearchCriteria_SHA1_FieldLabel: 'SHA-1',
    SearchCriteria_SHA256_FieldLabel: 'SHA-256',
    SearchCriteria_SHA2_FieldLabel: 'SHA-512',
    SearchCriteria_Version_FieldLabel: 'Version',
    Search_TextSearchCriteria_Filter_EmptyText: 'Any',
    SearchDocker_Image_Name_FieldLabel: 'Image Name',
    SearchDocker_Image_Tag_FieldLabel: 'Image Tag',
    SearchDocker_LayerId_FieldLabel: 'Layer Id',
    SearchDocker_ContentDigest_FieldLabel: 'Content Digest',
    SearchMaven_ArtifactID_FieldLabel: 'Artifact Id',
    SearchMaven_BaseVersion_FieldLabel: 'Base Version',
    SearchMaven_Extension_FieldLabel: 'Extension',
    SearchMaven_GroupID_FieldLabel: 'Group Id',
    SearchMaven_Classifier_FieldLabel: 'Classifier',
    SearchMaven_Version_FieldLabel: 'Version',
    SearchNpm_Scope_FieldLabel: 'Scope',
    SearchNpm_Name_FieldLabel: 'Name',
    SearchNpm_Version_FieldLabel: 'Version',
    SearchNpm_Author_FieldLabel: 'Author',
    SearchNpm_Description_FieldLabel: 'Description',
    SearchNpm_Keywords_FieldLabel: 'Keywords',
    SearchNpm_License_FieldLabel: 'License',
    SearchNuget_ID_FieldLabel: 'ID',
    SearchNuget_Tags_FieldLabel: 'Tags',
    SearchPyPi_Classifiers_FieldLabel: 'Classifiers',
    SearchPyPi_Description_FieldLabel: 'Description',
    SearchPyPi_Keywords_FieldLabel: 'PyPI Keywords',
    SearchPyPi_Summary_FieldLabel: 'Summary',
    SearchRubygems_Name_FieldLabel: 'Name',
    SearchRubygems_Version_FieldLabel: 'Version',
    SearchRubygems_Platform_FieldLabel: 'Platform',
    SearchRubygems_Summary_FieldLabel: 'Summary',
    SearchRubygems_Description_FieldLabel: 'Description',
    SearchRubygems_Licenses_FieldLabel: 'Licenses',
    SearchRubygems_Homepage_FieldLabel: 'Homepage',
    SearchYum_Architecture_FieldLabel: 'Architecture',
    SearchYum_Name_FieldLabel: 'Package Name',
    Search_More_Text: 'More criteria',
    Search_SearchResultList_Format_Header: 'Format',
    Search_SearchResultList_Group_Header: 'Group',
    Search_SearchResultList_Name_Header: 'Name',
    Search_SearchResultList_Repository_Header: 'Repository',
    Search_SearchResultList_Version_Header: 'Version',
    Search_SearchResultList_EmptyText: 'No components matched the filter criteria',
    Search_Assets_Group: 'Group',
    Search_Assets_Name: 'Name',
    Search_Assets_Format: 'Format',
    Search_Assets_Repository: 'Repository',
    Search_Assets_Version: 'Version',
    SearchResultAssetList_Name_Header: 'Name',
    Component_AssetInfo_Info_Title: 'Summary',
    Component_AssetInfo_Attributes_Title: 'Attributes',
    Component_AssetInfo_HealthCheck_Title: 'Component IQ',

    // Browse -> Search -> Bower
    SearchBower_Text: 'Bower',
    SearchBower_Description: 'Search for components in Bower repositories',

    // Browse -> Search -> Docker
    SearchDocker_Text: 'Docker',
    SearchDocker_Description: 'Search for components in Docker repositories',

    // Browse -> Search -> Raw
    SearchRaw_Text: 'Raw',
    SearchRaw_Description: 'Search for components in Raw repositories',

    // Browse -> Search -> Git LFS
    SearchGitLfs_Text: 'Git LFS',
    SearchGitLfs_Description: 'Search for components in Git LFS repositories',

    // Browse -> Search -> npm
    SearchNpm_Text: 'npm',
    SearchNpm_Description: 'Search for components in npm repositories',

    // Browse -> Search -> Nuget
    SearchNuget_Text: 'NuGet',
    SearchNuget_Description: 'Search for components in NuGet repositories',

    // Browse -> Search -> PyPI
    SearchPyPi_Text: 'PyPI',
    SearchPyPi_Description: 'Search for components in PyPI repositories',

    // Browse -> Search -> Rubygems
    SearchRubygems_Text: 'RubyGems',
    SearchRubygems_Description: 'Search for components in RubyGems repositories',

    // Browse -> Search -> Custom
    Search_Custom_Text: 'Custom',
    Search_Custom_Description: 'Search for components by custom criteria',

    // Browse -> Search -> Maven
    SearchMaven_Text: 'Maven',
    SearchMaven_Description: 'Search for components by Maven coordinates',

    // Browse -> Search -> Yum
    SearchYum_Text: 'Yum',
    SearchYum_Description: 'Search for components in Yum repositories',

    // Browse -> Browse
    FeatureGroups_Browse_Text: 'Browse',
    FeatureGroups_Browse_Description: 'Browse assets and components',

    // Browse -> Upload
    FeatureGroups_Upload_Text: 'Upload',
    FeatureGroups_Upload_Description: 'Upload content to the repository',

    // Admin -> Repository
    FeatureGroups_Repository_Text: 'Repository',
    FeatureGroups_Repository_Description: 'Repository administration',

    // Admin -> Repository -> Repositories
    Repositories_Text: 'Repositories',
    Repositories_Description: 'Manage repositories',
    Repositories_Delete_Mask: 'Deleting repository',
    Repositories_Create_Title: 'Create Repository: {0}',
    Repositories_SelectRecipe_Title: 'Select Recipe',
    Repository_RepositoryAdd_Create_Success: 'Repository created: ',
    Repository_RepositoryAdd_Create_Error: 'You do not have permission to create repositories',
    Repository_RepositorySettingsForm_Update_Success: 'Repository updated: ',
    Repository_RepositorySettingsForm_Update_Error: 'You do not have permission to update repositories',
    Repository_RepositoryList_New_Button: 'Create repository',
    Repository_RepositoryList_Name_Header: 'Name',
    Repository_RepositoryList_Type_Header: 'Type',
    Repository_RepositoryList_Format_Header: 'Format',
    Repository_RepositoryList_Status_Header: 'Status',
    Repository_RepositoryList_URL_Header: 'URL',
    Repository_RepositoryList_Filter_EmptyText: 'No repositories matched "$filter"',
    Repository_RepositoryList_EmptyText: 'No repositories defined',
    Repository_RepositoryFeature_Delete_Button: 'Delete repository',
    Repository_RepositoryFeature_RebuildIndex_Button: 'Rebuild index',
    Repository_RepositoryFeature_HealthCheckDisable_Button: 'Disable HealthCheck',
    Repository_RepositoryFeature_HealthCheckEnable_Button: 'Enable HealthCheck',
    Repository_RepositoryFeature_InvalidateCache_Button: 'Invalidate cache',
    Repository_RepositorySettings_Title: 'Settings',
    Repository_Facet_BowerProxyFacet_Title: 'Bower',
    Repository_Facet_BowerProxyFacet_RewritePackageUrls_FieldLabel: 'Enable rewrite of package URLs',
    Repository_Facet_BowerProxyFacet_RewritePackageUrls_HelpText: 'Force Bower to retrieve packages through the proxy repository',
    Repository_Facet_DockerHostedFacet_V1_Title: 'Docker Registry API Support',
    Repository_Facet_DockerHostedFacet_V1_Enabled: 'Enable Docker V1 API',
    Repository_Facet_DockerHostedFacet_V1_Enabled_Help: 'Allow clients to use the V1 API to interact with this Repository',
    Repository_Facet_DockerConnectorFacet_Title: 'Repository Connectors',
    Repository_Facet_DockerConnectorFacet_Help: '<em>Connectors allow Docker clients to connect directly ' +
    'to hosted registries, but are not always required. Consult our <a href="http://links.sonatype.com/products/nexus/docker-ssl-connector/docs" target="_blank">documentation</a>' +
    ' for which connector is appropriate for your use case.<em/>',
    Repository_Facet_DockerConnectorFacet_HttpPort_FieldLabel: 'HTTP',
    Repository_Facet_DockerConnectorFacet_HttpPort_HelpText: 'Create an HTTP connector at specified port. Normally used if the server is behind a secure proxy.',
    Repository_Facet_DockerConnectorFacet_HttpsPort_FieldLabel: 'HTTPS',
    Repository_Facet_DockerConnectorFacet_HttpsPort_HelpText: 'Create an HTTPS connector at specified port. Normally used if the server is configured for https.',
    Repository_Facet_DockerProxyFacet_IndexType_FieldLabel: 'Docker Index',
    Repository_Facet_DockerProxyFacet_IndexTypeRegistry_BoxLabel: 'Use proxy registry (specified above)',
    Repository_Facet_DockerProxyFacet_IndexTypeHub_BoxLabel: 'Use Docker Hub',
    Repository_Facet_DockerProxyFacet_IndexTypeCustom_BoxLabel: 'Custom index',
    Repository_Facet_DockerProxyFacet_IndexUrl_HelpText: 'Location of Docker index',
    Repository_Facet_DockerProxyFacet_BasicAuth_FieldLabel: 'Force basic authentication',
    Repository_Facet_DockerProxyFacet_BasicAuth_BoxLabel: 'Disable to allow anonymous pull (Note: also requires Docker Bearer Token Realm to be activated)',
    Repository_Facet_GroupFacet_Title: 'Group',
    Repository_Facet_HttpClientFacet_Title: 'HTTP',
    Repository_Facet_Maven2Facet_Title: 'Maven 2',
    Repository_Facet_NegativeCacheFacet_Title: 'Negative Cache',
    Repository_Facet_NugetProxyFacet_Title: 'NuGet',
    Repository_Facet_ProxyFacet_Title: 'Proxy',
    Repository_Facet_StorageFacet_Title: 'Storage',
    Repository_Facet_StorageFacetHosted_Title: 'Hosted',
    Repository_Facet_ProxyFacet_Autoblock_FieldLabel: 'Auto blocking enabled',
    Repository_Facet_ProxyFacet_Autoblock_HelpText: 'Auto-block outbound connections on the repository if remote peer is detected as unreachable/unresponsive',
    Repository_Facet_ProxyFacet_Blocked_FieldLabel: 'Blocked',
    Repository_Facet_ProxyFacet_Blocked_HelpText: 'Block outbound connections on the repository',
    Repository_RepositorySettingsForm_Name_FieldLabel: 'Name',
    Repository_RepositorySettingsForm_Name_HelpText: 'A unique identifier for this repository',
    Repository_RepositorySettingsForm_URL_FieldLabel: 'URL',
    Repository_RepositorySettingsForm_URL_HelpText: 'The URL used to access this repository',
    Repository_Facet_GroupFacet_Members_FieldLabel: 'Member repositories',
    Repository_Facet_GroupFacet_Members_HelpText: 'Select and order the repositories that are part of this group',
    Repository_Facet_GroupFacet_Members_FromTitle: 'Available',
    Repository_Facet_GroupFacet_Members_ToTitle: 'Members',
    Repository_Facet_StorageFacetHosted_Deployment_FieldLabel: 'Deployment policy',
    Repository_Facet_StorageFacetHosted_Deployment_HelpText: 'Controls if deployments of and updates to artifacts are allowed',
    Repository_Facet_StorageFacetHosted_Deployment_EmptyText: 'Select a policy',
    Repository_Facet_StorageFacetHosted_Deployment_AllowItem: 'Allow redeploy',
    Repository_Facet_StorageFacetHosted_Deployment_DisableItem: 'Disable redeploy',
    Repository_Facet_StorageFacetHosted_Deployment_ReadOnlyItem: 'Read-only',
    Repository_Facet_ProxyFacet_Remote_FieldLabel: 'Remote storage',
    Repository_Facet_ProxyFacet_Remote_HelpText: 'Location of the remote repository being proxied',
    Repository_Facet_ProxyFacet_Remote_EmptyText: 'Enter a URL',
    Ssl_SslUseTrustStore_BoxLabel: 'Use the Nexus truststore',
    Ssl_SslUseTrustStore_Certificate_Button: 'View certificate',
    Ssl_SslUseTrustStore_Certificate_HelpText: 'Use certificates stored in the Nexus truststore to connect to external systems',
    Maven2Facet_VersionPolicy_FieldLabel: 'Version policy',
    Maven2Facet_VersionPolicy_HelpText: 'What type of artifacts does this repository store?',
    Maven2Facet_VersionPolicy_EmptyText: 'Select a policy',
    Maven2Facet_VersionPolicy_MixedItem: 'Mixed',
    Maven2Facet_VersionPolicy_ReleaseItem: 'Release',
    Maven2Facet_VersionPolicy_SnapshotItem: 'Snapshot',
    Repository_Facet_Maven2Facet_LayoutPolicy_FieldLabel: 'Layout policy',
    Repository_Facet_Maven2Facet_LayoutPolicy_HelpText: 'Validate that all paths are maven artifact or metadata paths',
    Repository_Facet_Maven2Facet_LayoutPolicy_EmptyText: 'Select a policy',
    Repository_Facet_Maven2Facet_LayoutPolicy_StrictItem: 'Strict',
    Repository_Facet_Maven2Facet_LayoutPolicy_PermissiveItem: 'Permissive',
    Repository_RepositorySettingsForm_Format_FieldLabel: 'Format',
    Repository_RepositorySettingsForm_Format_HelpText: 'The format of the repository (i.e. maven2, docker, raw, nuget...)',
    Repository_RepositorySettingsForm_Type_FieldLabel: 'Type',
    Repository_RepositorySettingsForm_Type_HelpText: 'The type of repository (i.e. group, hosted, or proxy)',
    Repository_RepositorySettingsForm_Online_FieldLabel: 'Online',
    Repository_RepositorySettingsForm_Online_HelpText: 'If checked, the repository accepts incoming requests',
    Repository_Facet_ProxyFacet_ArtifactAge_FieldLabel: 'Maximum component age',
    Repository_Facet_ProxyFacet_MetadataAge_FieldLabel: 'Maximum metadata age',
    Repository_Facet_ProxyFacet_ArtifactAge_HelpText: 'How long (in minutes) to cache artifacts before rechecking the remote repository. Release repositories should use -1.',
    Repository_Facet_ProxyFacet_MetadataAge_HelpText: 'How long (in minutes) to cache metadata before rechecking the remote repository.',
    Repository_Facet_HttpClientFacet_ConnectionRetries_FieldLabel: 'Connection retries',
    Repository_Facet_HttpClientFacet_ConnectionRetries_HelpText: 'Total retries if the initial connection attempt suffers a timeout',
    Repository_Facet_HttpClientFacet_ConnectionTimeout_FieldLabel: 'Connection timeout',
    Repository_Facet_HttpClientFacet_ConnectionTimeout_HelpText: 'Seconds to wait for activity before stopping and retrying the connection. Leave blank to use the globally defined HTTP timeout.',
    Repository_Facet_HttpClientFacet_EnableCircularRedirects_FieldLabel: 'Enable circular redirects',
    Repository_Facet_HttpClientFacet_EnableCircularRedirects_HelpText: 'Enable redirects to the same location (may be required by some servers)',
    Repository_Facet_HttpClientFacet_EnableCookies_FieldLabel: 'Enable cookies',
    Repository_Facet_HttpClientFacet_EnableCookies_HelpText: 'Allow cookies to be stored and used',
    Repository_Facet_StorageFacet_BlobStore_FieldLabel: 'Blob store',
    Repository_Facet_StorageFacet_BlobStore_HelpText: 'Blob store used to store asset contents',
    Repository_Facet_StorageFacet_BlobStore_EmptyText: 'Select a blob store',
    Repository_Facet_StorageFacet_ContentTypeValidation_FieldLabel: 'Strict Content Type Validation',
    Repository_Facet_StorageFacet_ContentTypeValidation_HelpText: 'Validate that all content uploaded to this repository is of a MIME type appropriate for the repository format',
    Repository_Facet_NegativeCacheFacet_Enabled_FieldLabel: 'Not found cache enabled',
    Repository_Facet_NegativeCacheFacet_Enabled_HelpText: 'Cache responses for content not present in the proxied repository',
    Repository_Facet_NegativeCacheFacet_TTL_FieldLabel: 'Not found cache TTL',
    Repository_Facet_NegativeCacheFacet_TTL_HelpText: 'How long to cache the fact that a file was not found in the repository (in minutes)',
    Repository_Facet_NugetProxyFacet_ItemMaxAge_FieldLabel: 'Metadata query cache age',
    Repository_Facet_NugetProxyFacet_ItemMaxAge_HelpText: 'How long to cache query results from the proxied repository (in seconds)',
    Repository_Facet_HttpClientFacet_AuthenticationType_FieldLabel: 'Authentication type',
    Repository_Facet_HttpClientFacet_AuthenticationType_Username: 'Username',
    Repository_Facet_HttpClientFacet_AuthenticationType_NTLM: 'Windows NTLM',
    Repository_Facet_HttpClientFacet_Authentication_Title: 'Authentication',
    Repository_Facet_HttpClientFacet_HTTP_Title: 'HTTP request settings',
    Repository_RepositoryList_Size_Header: 'Size',
    Repository_RepositoryList_BlobCount_Header: 'Blob count',
    Repository_RepositorySettingsForm_Title: 'Size and the Blob Count',
    Repository_RepositorySettingsForm_Size_FieldLabel:'Size',
    Repository_RepositorySettingsForm_Size_HelpText:'The size of the current repository',
    Repository_RepositorySettingsForm_BlobCount_FieldLabel:'Blob count',
    Repository_RepositorySettingsForm_BlobCount_HelpText:'The blob count of the current repository',

    HealthCheckRepositoryColumn_Header: 'Health check',
    HealthCheckRepositoryColumn_Analyzing: 'Analyzing&hellip;',
    HealthCheckRepositoryColumn_Analyzing_Tooltip: '<span><h2>The Analysis is Under Way</h2>' +
    'The contents of your repository are being analyzed. This process should only take a few minutes.<br><br>' +
    'When the analysis is complete and this page has been refreshed, we will show you the top 5 most vulnerable ' +
    'components in the repository, the number of downloads over the last month, and a year-over-year overview.</span>',
    HealthCheckRepositoryColumn_View_Permission_Error: '<span><h2>Insufficient Permissions to View Summary Report</h2>' +
    'To view healthcheck summary report for a repository your user account must have the necessary permissions.</span>',
    HealthCheckRepositoryColumn_Analyze: 'Analyze',
    HealthCheckRepositoryColumn_Analyze_Tooltip: '<span><h2>Repository Health Check Analysis</h2>Click this button to request a Repository Health Check (RHC) ' +
    'by IQ Server.  The process is non-invasive and non-disruptive. IQ Server ' +
    'will return actionable quality and security information about the open source components in the repository.' +
    '<br><br><a href="http://links.sonatype.com/products/clm/rhc/home" ' +
    'target="_blank">How the IQ Server Repository Health Check can help you make better software faster</a></span>',
    HealthCheckRepositoryColumn_Analyze_Dialog_Title: 'Analyze Repository',
    HealthCheckRepositoryColumn_Analyze_Dialog_Msg: 'Do you want to analyze the repository {0} and others for security vulnerabilities and license issues?',
    HealthCheckRepositoryColumn_Analyze_Dialog_Ok_Text: 'Yes, all repositories',
    HealthCheckRepositoryColumn_Analyze_Dialog_Yes_Text: 'Yes, only this repository',
    HealthCheckRepositoryColumn_Analyze_Permission_Error: '<span><h2>Insufficient Permissions to Analyze a Repository</h2>' +
    'To analyze a repository your user account must have permissions to start analysis.</span>',
    HealthCheckRepositoryColumn_Loading: 'Loading&hellip;',
    HealthCheckRepositoryColumn_CollectingTrendData: 'Insufficient trend data',
    HealthCheckRepositoryColumn_DownloadsDisabled: 'Download trends disabled',
    HealthCheckRepositoryColumn_Unavailable_Tooltip: '<span><h2>Repository Health Check Unavailable</h2>A Repository Health Check (RHC) ' +
    'cannot be performed on this repository, because it is an unsupported type or out of service.<br><br>' +
    '<a href="http://links.sonatype.com/products/clm/rhc/home" ' +
    'target="_blank">How the IQ Server Repository Health Check can help you make better software faster</a></span>',

    HealthCheckSummary_Help: '<a href="http://links.sonatype.com/products/nexus/rhc/manual-remediation-with-rhc" target="_blank">What should I do with this report?</a>',

    // Admin -> Repository -> Blob Stores
    Blobstores_Text: 'Blob Stores',
    Blobstores_Description: 'Manage blob stores',
    Blobstores_Delete_Mask: 'Deleting blob store',
    Blobstores_Create_Title: 'Create blob store',
    Blobstore_BlobstoreAdd_Create_Success: 'Blob store created: ',
    Blobstore_BlobstoreAdd_Create_Error: 'You do not have permission to create blob stores',
    Blobstore_BlobstoreSettingsForm_Update_Success: 'Blob store updated: ',
    Blobstore_BlobstoreSettingsForm_Update_Error: 'Update is not supported for blob stores',
    Blobstore_BlobstoreList_New_Button: 'Create blob store',
    Blobstore_BlobstoreList_Name_Header: 'Name',
    Blobstore_BlobstoreList_Type_Header: 'Type',
    Blobstore_BlobstoreList_BlobCount_Header: 'Blob count',
    Blobstore_BlobstoreList_TotalSize_Header: 'Total size',
    Blobstore_BlobstoreList_AvailableSpace_Header: 'Available space',
    Blobstore_BlobstoreList_Filter_EmptyText: 'No blob stores matched "$filter"',
    Blobstore_BlobstoreList_EmptyText: 'No blob stores defined',
    Blobstore_BlobstoreFeature_Delete_Button: 'Delete blob store',
    Blobstore_BlobstoreFeature_Delete_Disabled_Message: 'This blob store is in use by {0} and cannot be deleted',
    Blobstore_BlobstoreSettings_Title: 'Settings',
    Blobstore_BlobstoreAdd_Type_FieldLabel: 'Type',
    Blobstore_BlobstoreAdd_Type_EmptyText: 'Select a type',
    Blobstore_BlobstoreSettingsForm_Name_FieldLabel: 'Name',
    Blobstore_BlobstoreSettingsForm_Path_FieldLabel: 'Path',

    // Admin -> Repository -> Selectors
    Selectors_Text: 'Content Selectors',
    Selectors_Description: 'Manage content selectors',
    Selectors_Create_Title: 'Create Selector',
    Selector_SelectorAdd_Create_Error: 'You do not have permission to create selectors',
    Selector_SelectorAdd_Create_Success: 'Selector created: {0}',
    Selector_SelectorSettingsForm_Update_Error: 'You do not have permission to update selectors',
    Selector_SelectorSettingsForm_Update_Success: 'Selector updated: {0}',
    Selector_SelectorList_New_Button: 'Create selector',
    Selector_SelectorList_Name_Header: 'Name',
    Selector_SelectorList_Type_Header: 'Type',
    Selector_SelectorList_Description_Header: 'Description',
    Selector_SelectorList_EmptyText: 'No selectors defined',
    Selector_SelectorList_Filter_EmptyText: 'No selectors matched "$filter"',
    Selector_SelectorFeature_Delete_Button: 'Delete selector',
    Selectors_Delete_Message: 'Selector deleted: {0}',
    Selector_SelectorFeature_Settings_Title: 'Settings',
    Selector_SelectorSettingsForm_Name_FieldLabel: 'Name',
    Selector_SelectorSettingsForm_Type_FieldLabel: 'Type',
    Selector_SelectorSettingsForm_Type_Jexl: 'JEXL',
    Selector_SelectorSettingsForm_Type_Sonatype: 'CSEL',
    Selector_SelectorSettingsForm_Description_FieldLabel: 'Description',
    Selector_SelectorSettingsForm_Expression_FieldLabel: 'Search expression',
    Selector_SelectorSettingsForm_Expression_HelpText: 'Use query to identify repositories, components or assets',
    Selector_SelectorSettingsForm_Expression_Examples: '<div style="font-size: 11px"><br/>' +
    '<h4>Example Content Selector Expressions:</h4>' +
    '<p>Select all "raw" format content<br/><i>format == "raw"</i></p>' +
    '<p>Select all "maven2" content with a groupId that starts with "org.sonatype.nexus"<br/><i>format == "maven2" and coordinate.groupId =^ "org.sonatype.nexus"</i></p>' +
    '<br/>' +
    '<p>See the <a href="http://links.sonatype.com/products/nexus/selectors/docs" target="_blank">Nexus documentation</a> for more details</p>' +
    '</div>',
    Selector_SelectorSettingsForm_Expression_Examples_jexl: '<div style="font-size: 11px"><br/>' +
    '<h4>Example <a href="http://links.sonatype.com/products/nexus/jexl" target="_blank">JEXL</a> queries:</h4>' +
    '<p>Select all "raw" format content<br/><i>format == "raw"</i></p>' +
    '<p>Select all "maven2" content with a groupId that starts with "org.sonatype.nexus"<br/><i>format == "maven2" and coordinate.groupId =^ "org.sonatype.nexus"</i></p>' +
    '<br/>' +
    '<p>See the <a href="http://links.sonatype.com/products/nexus/selectors/docs" target="_blank">Nexus documentation</a> for more details</p>' +
    '</div>',
    Selector_SelectorSettingsForm_SelectorID_Title: 'Selector ID',
    Selector_SelectorSettingsForm_Specification_Title: 'Specification',
    Selector_SelectorSettingsForm_Preview_Button: 'Preview results',

    // Admin -> Repository -> Selectors -> Preview Window
    SelectorPreviewWindow_Title: 'Preview results',
    SelectorPreviewWindow_expression_FieldLabel: 'Expression',
    SelectorPreviewWindow_expression_jexl: 'JEXL',
    SelectorPreviewWindow_expression_csel: 'CSEL',
    SelectorPreviewWindow_type_FieldLabel: 'Type',
    SelectorPreviewWindow_repository_FieldLabel: 'Preview Repository',
    SelectorPreviewWindow_repository_HelpText: 'Select a repository to evaluate the content selector and see the content that would be available.',
    SelectorPreviewWindow_repository_EmptyText: 'Select a repository...',
    SelectorPreviewWindow_EmptyText_View: 'No assets in repository matched the expression',
    SelectorPreviewWindow_EmptyText_Filter: 'No assets matched "$filter"',
    SelectorPreviewWindow_Name_Column: 'Name',
    SelectorPreviewWindow_Preview_Button: 'Preview',

    // Admin -> Security
    FeatureGroups_Security_Title: 'Security',
    FeatureGroups_Security_Description: 'Security administration',

    // Admin -> Security -> Privileges
    Privileges_Text: 'Privileges',
    Privileges_Description: 'Manage privileges',
    Privileges_Update_Mask: 'Updating privilege',
    Privileges_Update_Success: 'Privilege updated: {0}',
    Privileges_Create_Success: 'Privilege created: {0}',
    Privileges_Delete_Success: 'Privilege deleted: {0}',
    Privileges_Select_Title: 'Select Privilege Type',
    Privilege_PrivilegeList_New_Button: 'Create privilege',
    Privilege_PrivilegeList_Name_Header: 'Name',
    Privilege_PrivilegeList_Description_Header: 'Description',
    Privilege_PrivilegeList_Type_Header: 'Type',
    Privilege_PrivilegeList_Permission_Header: 'Permission',
    Privilege_PrivilegeList_EmptyText: 'No privileges defined',
    Privilege_PrivilegeList_Filter_EmptyText: 'No privileges matched "$filter"',
    Privilege_PrivilegeFeature_Details_Tab: 'Summary',
    Privilege_PrivilegeFeature_Delete_Button: 'Delete privilege',
    Privilege_PrivilegeFeature_Settings_Title: 'Settings',
    Privilege_PrivilegeSelectType_Type_Header: 'Type',
    Privilege_PrivilegeAdd_Create_Error: 'You do not have permission to create privileges',
    Privilege_PrivilegeSettingsForm_Update_Success: 'Privilege updated: {0}',
    Privilege_PrivilegeSettingsForm_Update_Error: 'You do not have permission to update privileges or privilege is read only',
    Privilege_PrivilegeSettingsForm_Description_FieldLabel: 'Description',
    Privilege_PrivilegeSettingsForm_Name_FieldLabel: 'Name',
    Privileges_Summary_ID: 'ID',
    Privileges_Summary_Type: 'Type',
    Privileges_Summary_Name: 'Name',
    Privileges_Summary_Description: 'Description',
    Privileges_Summary_Permission: 'Permission',
    Privileges_Summary_Property: 'Property-{0}',
    Privileges_Create_Title: 'Create {0} Privilege',

    // Admin -> Security -> Roles
    Roles_Text: 'Roles',
    Roles_Description: 'Manage roles',
    Roles_Create_Title: 'Create Role',
    Role_RoleAdd_Create_Error: 'You do not have permission to create roles',
    Role_RoleAdd_Create_Success: 'Role created: ',
    Role_RoleSettingsForm_Update_Error: 'You do not have permission to update roles or role is readonly',
    Role_RoleSettingsForm_Update_Success: 'Role updated: ',
    Role_RoleList_New_Button: 'Create role',
    Role_RoleList_New_NexusRoleItem: 'Nexus role',
    Roles_New_ExternalRoleItem: 'External role mapping',
    Role_RoleList_Name_Header: 'Name',
    Role_RoleList_Source_Header: 'Source',
    Role_RoleList_Description_Header: 'Description',
    Role_RoleList_EmptyText: 'No roles defined',
    Role_RoleList_Filter_EmptyText: 'No roles matched "$filter"',
    Role_RoleFeature_Delete_Button: 'Delete role',
    Roles_Delete_Message: 'Role deleted: {0}',
    Role_RoleFeature_Settings_Title: 'Settings',
    Role_RoleSettingsForm_RoleID_FieldLabel: 'Role ID',
    Role_RoleSettingsForm_MappedRole_FieldLabel: 'Mapped Role',
    Role_RoleSettingsForm_MappedRole_EmptyText: 'Select a role',
    Role_RoleSettingsForm_Name_FieldLabel: 'Role name',
    Role_RoleSettingsForm_Description_FieldLabel: 'Role description',
    Role_RoleSettingsForm_Privileges_FieldLabel: 'Privileges',
    Role_RoleSettingsForm_Privileges_FromTitle: 'Available',
    Role_RoleSettingsForm_Privileges_ToTitle: 'Given',
    Role_RoleSettingsForm_Roles_FieldLabel: 'Roles',
    Role_RoleSettingsForm_Roles_FromTitle: 'Available',
    Role_RoleSettingsForm_Roles_ToTitle: 'Contained',

    // Admin -> Security -> Users
    User_Text: 'Users',
    User_Description: 'Manage users',
    User_UserSettingsForm_Update_Error: 'You do not have permission to update users or is an external user',
    User_UserSettingsForm_Update_Success: 'User updated: ',
    User_UserSettingsForm_UpdateRoles_Success: 'User role mappings updated: {0}',
    User_UserSettingsExternalForm_Remove_Error: 'Cannot remove role',
    Users_Create_Title: 'Create User',
    User_UserAdd_Password_FieldLabel: 'Password',
    User_UserAdd_PasswordConfirm_FieldLabel: 'Confirm password',
    User_UserChangePassword_NoMatch_Error: 'Passwords do not match',
    User_UserAdd_Create_Error: 'You do not have permission to create users',
    User_UserAdd_Create_Success: 'User created: ',
    User_UserChangePassword_Title: 'Change Password',
    User_UserChangePassword_Password_FieldLabel: 'New password',
    User_UserChangePassword_PasswordConfirm_FieldLabel: 'Confirm password',
    User_UserChangePassword_Submit_Button: 'Change password',
    User_UserChangePassword_Cancel_Button: '@Button_Cancel',
    User_UserChangePassword_NoPermission_Error: 'You do not have permission to change your password',
    User_UserList_New_Button: 'Create local user',
    User_UserList_Source_Label: 'Source:',
    User_UserList_Default_Button: 'Default',
    User_UserList_Filter_EmptyText: 'Filter by user ID',
    User_UserList_ID_Header: 'User ID',
    User_UserList_Realm_Header: 'Realm',
    User_UserList_FirstName_Header: 'First name',
    User_UserList_LastName_Header: 'Last name',
    User_UserList_Email_Header: 'Email',
    User_UserList_Status_Header: 'Status',
    User_UserList_EmptyText: 'No users defined',
    User_UserFeature_Delete_Button: 'Delete user',
    Users_Delete_Success: 'User deleted: {0}',
    User_UserFeature_More_Button: 'More',
    User_UserFeature_ChangePasswordItem: 'Change password',
    Users_Change_Success: 'Password changed',
    User_UserFeature_Settings_Title: 'Settings',
    User_UserSettingsForm_ID_FieldLabel: 'ID',
    User_UserSettingsForm_ID_HelpText: 'This will be used as the username',
    User_UserSettingsForm_FirstName_FieldLabel: 'First name',
    User_UserSettingsForm_LastName_FieldLabel: 'Last name',
    User_UserSettingsForm_Email_FieldLabel: 'Email',
    User_UserSettingsForm_Email_HelpText: 'Used for notifications',
    User_UserSettingsForm_Status_FieldLabel: 'Status',
    User_UserSettingsForm_Status_EmptyText: 'Select status',
    User_UserSettingsForm_Status_ActiveItem: 'Active',
    User_UserSettingsForm_Status_DisabledItem: 'Disabled',
    User_UserSettingsExternalForm_Roles_FieldLabel: 'Roles',
    User_UserSettingsExternalForm_Roles_FromTitle: 'Available',
    User_UserSettingsExternalForm_Roles_ToTitle: 'Granted',
    User_UserSettingsExternalForm_ExternalRoles_FieldLabel: 'External roles',
    User_UserSettingsExternalForm_ExternalRoles_HelpText: 'External roles should be managed at their source, and cannot be managed here.',

    // Admin -> Security -> Anonymous
    AnonymousSettings_Text: 'Anonymous',
    AnonymousSettings_Description: 'Browse server contents without authenticating',
    Security_AnonymousSettings_Update_Error: 'You do not have permission to configure the anonymous user',
    Security_AnonymousSettings_Update_Success: 'Anonymous security settings $action',
    Security_AnonymousSettings_Allow_BoxLabel: 'Allow anonymous users to access the server',
    Security_AnonymousSettings_Username_FieldLabel: 'Username',
    Security_AnonymousSettings_Realm_FieldLabel: 'Realm',

    // Admin -> Security -> LDAP
    LdapServers_Text: 'LDAP',
    LdapServers_Description: 'Manage LDAP server configuration',
    LdapServers_Update_Mask: 'Updating LDAP connection',
    LdapServers_Update_Success: 'LDAP server updated: {0}',
    Ldap_LdapServerConnectionForm_Update_Error: 'You do not have permission to update LDAP servers',
    LdapServers_Create_Mask: 'Creating LDAP connection',
    LdapServers_CreateConnection_Title: 'Create LDAP Connection',
    LdapServers_CreateUsersAndGroups_Title: 'Choose Users and Groups',
    LdapServers_Create_Success: 'LDAP server created: {0}',
    Ldap_LdapServerConnectionAdd_Create_Error: 'You do not have permission to create LDAP servers',
    LdapServers_Delete_Success: 'LDAP server deleted: {0}',
    Ldap_LdapServerChangeOrder_Title: 'Change LDAP servers ordering',
    LdapServers_ChangeOrder_Success: 'LDAP server order changed',
    Ldap_LdapServerUserAndGroupLoginCredentials_Title: 'Login Credentials',
    Ldap_LdapServerUserAndGroupLoginCredentials_Text: 'You have requested an operation which requires validation of your credentials.',
    Ldap_LdapServerUserAndGroupLoginCredentials_Input_Text: '<div>Enter your LDAP server credentials</div>',
    Ldap_LdapServerUserAndGroupLoginCredentials_Username_FieldLabel: 'LDAP server username',
    Ldap_LdapServerUserAndGroupLoginCredentials_Password_FieldLabel: 'LDAP server password',
    Ldap_LdapServerUserAndGroupLoginCredentials_Submit_Button: 'Test connection',
    Ldap_LdapServerUserAndGroupLoginCredentials_Cancel_Button: '@Button_Cancel',
    Ldap_LdapServerUserAndGroupMappingTestResults_Title: 'User Mapping Test Results',
    Ldap_LdapServerUserAndGroupMappingTestResults_ID_Header: 'User ID',
    Ldap_LdapServerUserAndGroupMappingTestResults_Name_Header: 'Name',
    Ldap_LdapServerUserAndGroupMappingTestResults_Email_Header: 'Email',
    Ldap_LdapServerUserAndGroupMappingTestResults_Roles_Header: 'Roles',
    Ldap_LdapServerList_New_Button: 'Create connection',
    Ldap_LdapServerList_ChangeOrder_Button: 'Change order',
    Ldap_LdapServerList_ClearCache_Button: 'Clear cache',
    Ldap_LdapServerList_Order_Header: 'Order',
    Ldap_LdapServerList_Name_Header: 'Name',
    Ldap_LdapServerList_URL_Header: 'URL',
    Ldap_LdapServerList_Filter_EmptyText: 'No LDAP servers matched "$filter"',
    Ldap_LdapServerList_EmptyText: 'No LDAP servers defined',
    Ldap_LdapServerFeature_Delete_Button: 'Delete connection',
    Ldap_LdapServerFeature_Connection_Title: 'Connection',
    Ldap_LdapServerFeature_UserAndGroup_Title: 'User and group',
    LdapServers_ClearCache_Success: 'LDAP cache has been cleared',
    LdapServers_VerifyConnection_Mask: 'Checking connection to {0}',
    LdapServers_VerifyConnection_Success: 'Connection to LDAP server verified: {0}',
    LdapServers_VerifyMapping_Mask: 'Checking user mapping on {0}',
    LdapServers_VerifyMapping_Success: 'LDAP server user mapping verified: {0}',
    LdapServers_VerifyLogin_Mask: 'Checking login on {0}',
    LdapServers_VerifyLogin_Success: 'LDAP login completed successfully on: {0}',
    LdapServersConnectionFieldSet_Address_Text: 'LDAP server address:',
    LdapServersConnectionFieldSet_Address_HelpText: 'The LDAP server usually listens on port 389 (ldap://) or port 636 (ldaps://)',
    LdapServersConnectionFieldSet_Name_FieldLabel: 'Name',
    LdapServersConnectionFieldSet_Protocol_EmptyText: 'Protocol',
    LdapServersConnectionFieldSet_Protocol_PlainItem: 'ldap',
    LdapServersConnectionFieldSet_Protocol_SecureItem: 'ldaps',
    LdapServersConnectionFieldSet_Host_EmptyText: 'Hostname',
    LdapServersConnectionFieldSet_Port_EmptyText: 'Port',
    LdapServersConnectionFieldSet_Base_FieldLabel: 'Search base',
    LdapServersConnectionFieldSet_Base_HelpText: 'LDAP location to be added to the connection URL (e.g. "dc=sonatype,dc=com")',
    LdapServersConnectionFieldSet_AuthMethod_FieldLabel: 'Authentication method',
    LdapServersConnectionFieldSet_AuthMethod_EmptyText: 'Select an authentication method',
    LdapServersConnectionFieldSet_AuthMethod_SimpleItem: 'Simple Authentication',
    LdapServersConnectionFieldSet_AuthMethod_AnonymousItem: 'Anonymous Authentication',
    LdapServersConnectionFieldSet_AuthMethod_DigestItem: 'DIGEST-MD5',
    LdapServersConnectionFieldSet_AuthMethod_CramItem: 'CRAM-MD5',
    LdapServersConnectionFieldSet_SaslRealm_FieldLabel: 'SASL realm',
    LdapServersConnectionFieldSet_SaslRealm_HelpText: 'The SASL realm to bind to (e.g. mydomain.com)',
    LdapServersConnectionFieldSet_Username_FieldLabel: 'Username or DN',
    LdapServersConnectionFieldSet_Username_HelpText: 'This must be a fully qualified username if simple authentication is used',
    LdapServersConnectionFieldSet_Password_FieldLabel: 'Password',
    LdapServersConnectionFieldSet_Password_HelpText: 'The password to bind with.',
    LdapServersConnectionFieldSet_Rules_Text: 'Connection rules',
    LdapServersConnectionFieldSet_Rules_HelpText: 'Set timeout parameters and max connection attempts to avoid being blacklisted',
    LdapServersConnectionFieldSet_Rules_Text1: 'Wait ',
    LdapServersConnectionFieldSet_Rules_Text2: ' seconds before timeout. Retry after ',
    LdapServersConnectionFieldSet_Rules_Text3: ' seconds, max of ',
    LdapServersConnectionFieldSet_Rules_Text4: ' failed attempts.',
    Ldap_LdapServerConnectionForm_VerifyConnection_Button: 'Verify connection',
    Ldap_LdapServerUserAndGroupFieldSet_Template_FieldLabel: 'Configuration template',
    Ldap_LdapServerUserAndGroupFieldSet_Template_EmptyText: 'Select a template',
    Ldap_LdapServerUserAndGroupFieldSet_BaseDN_FieldLabel: 'Base DN',
    Ldap_LdapServerUserAndGroupFieldSet_BaseDN_HelpText: 'The base location in LDAP that users are found. This is relative to the search base (e.g. ou=people).',
    Ldap_LdapServerUserAndGroupFieldSet_UserSubtree_FieldLabel: 'User subtree',
    Ldap_LdapServerUserAndGroupFieldSet_UserSubtree_HelpText: 'Are users located in structures below the user base DN?',
    Ldap_LdapServerUserAndGroupFieldSet_ObjectClass_FieldLabel: 'Object class',
    Ldap_LdapServerUserAndGroupFieldSet_ObjectClass_HelpText: 'LDAP class for user objects (e.g. inetOrgPerson)',
    Ldap_LdapServerUserAndGroupFieldSet_UserFilter_FieldLabel: 'User filter',
    Ldap_LdapServerUserAndGroupFieldSet_UserFilter_HelpText: 'LDAP search filter to limit user search (e.g. "attribute=foo" or "(l(mail=*@domain.com)(uid=dom*))")',
    Ldap_LdapServerUserAndGroupFieldSet_UserID_FieldLabel: 'User ID attribute',
    Ldap_LdapServerUserAndGroupFieldSet_RealName_FieldLabel: 'Real name attribute',
    Ldap_LdapServerUserAndGroupFieldSet_Email_FieldLabel: 'Email attribute',
    Ldap_LdapServerUserAndGroupFieldSet_Password_FieldLabel: 'Password attribute',
    Ldap_LdapServerUserAndGroupFieldSet_Password_HelpText: 'If this field is blank the user will be authenticated against a bind with the LDAP server.',
    Ldap_LdapServerUserAndGroupFieldSet_GroupMap_FieldLabel: 'Map LDAP groups as roles',
    Ldap_LdapServerUserAndGroupFieldSet_GroupType_FieldLabel: 'Group type',
    Ldap_LdapServerUserAndGroupFieldSet_GroupType_EmptyText: 'Select a group type',
    Ldap_LdapServerUserAndGroupFieldSet_GroupType_DynamicItem: 'Dynamic Groups',
    Ldap_LdapServerUserAndGroupFieldSet_GroupType_StaticItem: 'Static Groups',
    Ldap_LdapServerUserAndGroupFieldSet_GroupBaseDN_FieldLabel: 'Group base DN',
    Ldap_LdapServerUserAndGroupFieldSet_GroupBaseDN_HelpText: 'The base location in the LDAP that groups are found. This is relative to the search base (e.g. ou=Group).',
    Ldap_LdapServerUserAndGroupFieldSet_GroupSubtree_FieldLabel: 'Group subtree',
    Ldap_LdapServerUserAndGroupFieldSet_GroupSubtree_HelpText: 'Are groups located in structures below the group base DN.',
    Ldap_LdapServerUserAndGroupFieldSet_GroupObject_FieldLabel: 'Group object class',
    Ldap_LdapServerUserAndGroupFieldSet_GroupObject_HelpText: 'LDAP class for group objects (e.g. posixGroup)',
    Ldap_LdapServerUserAndGroupFieldSet_GroupID_FieldLabel: 'Group ID attribute',
    Ldap_LdapServerUserAndGroupFieldSet_GroupMember_FieldLabel: 'Group member attribute',
    Ldap_LdapServerUserAndGroupFieldSet_GroupMember_HelpText: 'LDAP attribute containing the usernames for the group.',
    Ldap_LdapServerUserAndGroupFieldSet_GroupMemberFormat_FieldLabel: 'Group member format',
    Ldap_LdapServerUserAndGroupFieldSet_GroupMemberFormat_HelpText: 'The format of user ID stored in the group member attribute (e.g. "uid=${username},ou=people,o=sonatype")',
    Ldap_LdapServerUserAndGroupFieldSet_GroupMemberOf_FieldLabel: 'Group member of attribute',
    Ldap_LdapServerUserAndGroupFieldSet_GroupMemberOf_HelpText: 'Set this to the attribute used to store the attribute which holds groups DN in the user object',
    Ldap_LdapServerUserAndGroupForm_VerifyGroupMapping_Button: 'Verify user mapping',
    Ldap_LdapServerUserAndGroupForm_VerifyLogin_Button: 'Verify login',

    // Admin -> Security -> Realms
    RealmSettings_Text: 'Realms',
    RealmSettings_Description: 'Manage the active security realms and their order',
    Security_RealmSettings_Update_Error: 'You do not have permission to configure realms',
    Security_RealmSettings_Update_Success: 'Security realms settings $action',
    Security_RealmSettings_Available_FromTitle: 'Available',
    Security_RealmSettings_Available_ToTitle: 'Active',

    // Admin -> Security -> SSL Certificates
    SslCertificates_Text: 'SSL Certificates',
    SslCertificates_Description: 'Manage trusted SSL certificates for use with the Nexus truststore',
    SslCertificates_Paste_Title: 'Paste Certificate as PEM',
    Ssl_SslCertificateAddFromPem_Cancel_Button: '@Button_Cancel',
    SslCertificates_Load_Title: 'Load Certificate from Server',
    Ssl_SslCertificateAddFromServer_Load_FieldLabel: 'Please enter a hostname, hostname:port or a URL to fetch a SSL certificate from',
    SslTrustStore_Load_Mask: 'Loading certificate&hellip;',
    Ssl_SslCertificateAddFromServer_Cancel_Button: '@Button_Cancel',
    SslCertificates_Load_Success: 'SSL Certificate created: {0}',
    Ssl_SslCertificateList_New_Button: 'Load certificate',
    Ssl_SslCertificateList_Load_Button: 'Load from server',
    Ssl_SslCertificateList_Paste_Button: 'Paste PEM',
    Ssl_SslCertificateList_Name_Header: 'Name',
    Ssl_SslCertificateList_IssuedTo_Header: 'Issued to',
    Ssl_SslCertificateList_IssuedBy_Header: 'Issued by',
    Ssl_SslCertificateList_Fingerprint_Header: 'Fingerprint',
    Ssl_SslCertificateList_EmptyText: 'No SSL certificates',
    Ssl_SslCertificateList_Filter_EmptyText: 'No SSL certificates matched "$filter"',
    Ssl_SslCertificateDetailsWindow_Title: 'Certificate Details',
    SslCertificates_Remove_Button: 'Remove certificate from truststore',
    SslCertificates_Add_Button: 'Add certificate to truststore',
    Ssl_SslCertificateFeature_Delete_Button: 'Delete certificate',
    SslCertificates_Delete_Success: 'SSL Certificate deleted: {0}',
    Ssl_SslCertificateDetailsWindow_Cancel_Button: '@Button_Cancel',
    Ssl_SslCertificateDetailsForm_Subject_Title: 'Subject',
    Ssl_SslCertificateDetailsForm_SubjectCommonName_FieldLabel: 'Common name',
    Ssl_SslCertificateDetailsForm_SubjectOrganization_FieldLabel: 'Organization',
    Ssl_SslCertificateDetailsForm_SubjectUnit_FieldLabel: 'Unit',
    Ssl_SslCertificateDetailsForm_Issuer_Title: 'Issuer',
    Ssl_SslCertificateDetailsForm_IssuerName_FieldLabel: 'Common name',
    Ssl_SslCertificateDetailsForm_IssuerOrganization_FieldLabel: 'Organization',
    Ssl_SslCertificateDetailsForm_IssuerUnit_FieldLabel: 'Unit',
    Ssl_SslCertificateDetailsForm_Certificate_Title: 'Certificate',
    Ssl_SslCertificateDetailsForm_CertificateIssuedOn_FieldLabel: 'Issued on',
    Ssl_SslCertificateDetailsForm_CertificateValidUntil_FieldLabel: 'Valid until',
    Ssl_SslCertificateDetailsForm_CertificateFingerprint_FieldLabel: 'Fingerprint',

    // Admin -> Support
    FeatureGroups_Support_Text: 'Support',
    FeatureGroups_Support_Description: 'Support tools',

    // Admin -> Support -> Analytics
    AnalyticsSettings_Text: 'Analytics',
    AnalyticsSettings_Description: 'Manage analytics configuration',
    Analytics_EventsZipCreated_FileType: 'Events ZIP',
    Analytics_AnalyticsSettings_Update_Error: 'You do not have permission to configure analytics',
    Analytics_AnalyticsSettings_Update_Success: 'Analytics settings $action',
    Analytics_AnalyticsSettings_HelpText: '<p>The analytics feature collects non-sensitive information about how your organization is using Nexus. It is useful to you from a compatibility perspective, since it gathers answers to questions such as what features are most important, where are users having difficulty and what integrations/APIs are actively in use. This data is available to you and allows you to understand your usage of Nexus better. Provided to Sonatype it enables us to tailor the ongoing development of the product.</p><b>Event Collection</b><p>The collected information is limited to the use of the Nexus user interface and the Nexus REST API -- i.e. the primary interaction points between your environment and Nexus. Only the user interface navigation flows and REST endpoints being called are recorded. None of the request specific data (e.g. credentials or otherwise sensitive information) is ever captured.</p><p>Event collection and submission are controlled separately.  When collection is enabled, a summary of the data collected is shown on the <code>Events</code> tab.</p><b>Event Submission</b><p>Analytics event data can be submitted either automatically or manually.<br/><code>Export</code> generates a ZIP file that can be inspected prior to any information being sent to the Nexus analytics service.<br/><code>Submit</code> generates a ZIP file and then immediately uploads it to the Nexus analytics service.</p>',
    Analytics_AnalyticsSettings_Collection_BoxLabel: 'Collect analytics events',
    Analytics_AnalyticsSettings_Submission_BoxLabel: 'Enable anonymized analytics submission to Sonatype',

    // Admin -> Support -> Analytics -> Events
    AnalyticsEvents_Text: 'Events',
    AnalyticsEvents_Description: 'View recorded analytics events',
    Analytics_AnalyticsEventList_Filter_EmptyText: 'No analytics events matched "$filter"',
    Analytics_AnalyticsEventList_Clear_Button: 'Clear',
    AnalyticsEvents_Clear_Title: 'Clear Events',
    AnalyticsEvents_Clear_Body: 'Clear analytics event data?',
    AnalyticsEvents_Clear_Mask: 'Clearing event data',
    AnalyticsEvents_Clear_Success: 'Event data has been cleared',
    Analytics_AnalyticsEventsList_Export_Button: 'Export',
    AnalyticsEvents_Export_Title: 'Export Events',
    AnalyticsEvents_Export_Body: '<p>Export and download analytics event data?</p><p>No data will be sent to Sonatype.</p>',
    AnalyticsEvents_Export_Mask: 'Exporting event data',
    AnalyticsEvents_Authenticate_Title: 'Downloading analytics events requires validation of your credentials',
    Analytics_AnalyticsEventList_Submit_Button: 'Submit',
    AnalyticsEvents_Submit_Title: 'Submit Events',
    AnalyticsEvents_Submit_Body: '<p>Submit analytics event data to Sonatype?</p><p>All identifying data will be anonymized.</p>',
    AnalyticsEvents_Submit_HelpText: 'Submitting analytics event data to Sonatype requires validation of your credentials',
    AnalyticsEvents_Submit_Success: 'Event data submission was successful',
    Analytics_AnalyticsEventList_Type_Header: 'Event type',
    Analytics_AnalyticsEventList_Timestamp_Header: 'Timestamp',
    Analytics_AnalyticsEventList_Timestamp_Tooltip: 'Event timestamp in milliseconds',
    Analytics_AnalyticsEventList_Sequence_Header: 'Sequence',
    Analytics_AnalyticsEventList_Duration_Header: 'Duration',
    Analytics_AnalyticsEventList_Duration_Tooltip: 'Event duration in nanoseconds',
    Analytics_AnalyticsEventList_User_Header: 'User',
    Analytics_AnalyticsEventList_Attributes_Header: 'Attributes',

    // Admin -> Support -> Logging
    Loggers_Text: 'Logging',
    Loggers_Description: 'Control logging verbosity levels',
    Loggers_Create_Title: 'Create logger',
    Logging_LoggerAdd_Name_FieldLabel: 'Logger name',
    Logging_LoggerAdd_Level_FieldLabel: 'Logging level',
    Loggers_Write_Success: 'Logger {0}d: {1}',
    Logging_LoggerList_New_Button: 'Create logger',
    Loggers_Update_Title: 'Confirm update?',
    Loggers_HelpText: 'Logger "{0}" is already configured. Would you like to update its level to "{1}"?',
    Logging_LoggerList_Delete_Button: 'Delete logger',
    Loggers_Delete_Title: 'Confirm deletion?',
    Loggers_Delete_Success: 'Logger deleted: {0}',
    Loggers_Reset_Title: 'Confirm reset?',
    Loggers_Reset_HelpText: 'Reset loggers to their default levels',
    Logging_LoggerList_Reset_Button: 'Reset to default levels',
    Loggers_Reset_Success: 'Loggers have been reset',
    Logging_LoggerList_Name_Header: 'Name',
    Logging_LoggerList_Level_Header: 'Level',
    Logging_LoggerList_Level_TraceItem: 'TRACE',
    Logging_LoggerList_Level_DebugItem: 'DEBUG',
    Logging_LoggerList_Level_InfoItem: 'INFO',
    Logging_LoggerList_Level_WarnItem: 'WARN',
    Logging_LoggerList_Level_ErrorItem: 'ERROR',
    Logging_LoggerList_Level_OffItem: 'OFF',
    Logging_LoggerList_Level_DefaultItem: 'DEFAULT',
    Logging_LoggerList_EmptyText: 'No loggers defined',
    Logging_LoggerList_Filter_EmptyText: 'No loggers matched "$filter"',

    // Admin -> Support -> Logging -> Log Viewer
    Log_Text: 'Log Viewer',
    Log_Description: 'View the current log contents',
    Logging_LogMark_Title: 'Mark Log',
    Logging_LogMark_Error: 'You do not have permission to mark the log',
    Logging_LogMark_FieldLabel: 'Log message',
    Logging_LogMark_HelpText: 'Insert this text into the log file as a reference point',
    Logging_LogMark_Success: 'Log has been marked',
    Log_LogMark_Mask: 'Marking Log',
    Logging_LogViewer_Download_Button: 'Download',
    Logging_LogViewer_Mark_Button: 'Create mark',
    Logging_LogViewer_Refresh_Text: 'Refresh interval:',
    Logging_LogViewer_Refresh_ManualItem: 'Manual',
    Logging_LogViewer_Refresh_20SecondsItem: 'Every 20 seconds',
    Logging_LogViewer_Refresh_MinuteItem: 'Every minute',
    Logging_LogViewer_Refresh_2MinutesItem: 'Every 2 minutes',
    Logging_LogViewer_Refresh_5MinutesItem: 'Every 5 minutes',
    Logging_LogViewer_Last25KBItem: 'Last 25KB',
    Logging_LogViewer_Last50KBItem: 'Last 50KB',
    Logging_LogViewer_Last100KBItem: 'Last 100KB',
    Log_Loading_Mask: 'Loading&hellip;',
    Log_Load_Failure: 'Failed to retrieve log due to "{0}".',
    Logging_LogViewer_EmptyText: 'Refresh to display log',

    // Admin -> Support -> Metrics
    Metrics_Text: 'Metrics',
    Metrics_Description: 'Provides server metrics',
    Metrics_Load_Mask: 'Loading&hellip;',
    Metrics_Refresh_Warning: 'Failed to refresh metrics data',
    Support_Metrics_Download_Button: 'Download',
    Metrics_Download_Tooltip: 'Download metrics data',
    Support_Metrics_Dump_Button: 'Thread dump',
    Support_Metrics_Dump_Tooltip: 'Download thread dump',
    Support_Metrics_MemoryUsage_Title: 'Memory usage',
    Support_Metrics_MemoryDistribution_Title: 'Memory distribution',
    Support_Metrics_Heap_Title: 'Heap',
    Metrics_Heap_NonHeapItem: 'Non-heap',
    Metrics_Heap_Available: 'Available',
    Support_Metrics_ThreadStates_Title: 'Thread states',
    Metrics_ThreadStates_New: 'New',
    Metrics_ThreadStates_Terminated: 'Terminated',
    Metrics_ThreadStates_Blocked: 'Blocked',
    Metrics_ThreadStates_Runnable: 'Runnable',
    Metrics_ThreadStates_TimedWaiting: 'Timed waiting',
    Metrics_ThreadStates_Waiting: 'Waiting',
    Support_Metrics_Dispatches_Title: 'Active Web Requests',
    Support_Metrics_ResponseCode_Title: 'Web Response Codes',
    Support_Metrics_Requests_Title: 'Web Requests',


    // Admin -> Support -> Support Request
    SupportRequest_Text: 'Support Request',
    SupportRequest_Description: 'Submit a support request to Sonatype',
    Support_SupportRequest_HelpText: '<p>Please include a complete description of your problem and steps to allow us to reproduce the problem (if available).</p><p>Attaching a <a href="#admin/support/supportzip">support ZIP</a> to your request will help our engineers give you a faster response.</p>',
    Support_SupportRequest_Submit_Button: 'Submit request',

    // Admin -> Support -> Support ZIP
    SupportZip_Title: 'Support ZIP',
    SupportZip_Description: 'Creates a ZIP file containing useful support information about your server',
    SupportZip_HelpText: '<p>No information will be sent to Sonatype when creating the support ZIP file.</p>' +
      '<p>Support ZIP creation may take a few minutes to complete.</p>',
    Support_SupportZip_Contents_FieldLabel: 'Contents',
    Support_SupportZip_Report_BoxLabel: 'System information report',
    Support_SupportZip_Dump_BoxLabel: 'JVM thread-dump',
    Support_SupportZip_Configuration_BoxLabel: 'Configuration files',
    Support_SupportZip_Security_BoxLabel: 'Security configuration files',
    Support_SupportZip_LogFiles_BoxLabel: 'Log files',
    Support_SupportZip_TaskLogFiles_BoxLabel: 'Task log files',
    Support_SupportZip_Metrics_BoxLabel: 'System and component metrics',
    Support_SupportZip_JMX_BoxLabel: 'JMX information',
    Support_SupportZip_Options_FieldLabel: 'Options',
    Support_SupportZip_Included_BoxLabel: 'Limit files in the ZIP archive to 30 MB apiece',
    Support_SupportZip_Max_BoxLabel: 'Limit the ZIP archive to 20 MB',
    Support_SupportZip_Create_Button: 'Create support ZIP',
    Support_SupportZip_Creating_Message: '<div align="center">Creating support ZIP <br/>(may take a few minutes)</div>',
    Support_SupportZipCreated_FileType_Text: 'Support ZIP',
    Support_SupportZipCreated_Truncated_Text: 'Contents have been truncated due to exceeded size limits.',
    Support_SupportZip_Create_Success: 'Support ZIP created',
    Support_FileCreated_Name_FieldLabel: 'Name',
    Support_FileCreated_Size_FieldLabel: 'Size',
    Support_FileCreated_Path_FieldLabel: 'Path',
    Support_FileCreated_Download_Button: 'Download',
    Support_FileCreated_Cancel_Button: '@Button_Cancel',
    SupportZip_Authenticate_Text: 'Downloading support ZIP requires validation of your credentials.',
    SupportZip_Permission_Error: 'You do not have permission to create a support ZIP',

    // Admin -> Support -> System Information
    SysInfo_Title: 'System Information',
    SysInfo_Description: 'Shows system information',
    SysInfo_Load_Mask: 'Loading&hellip;',
    Support_SysInfo_Download_Button: 'Download',

    // Admin -> System
    FeatureGroups_System_Text: 'System',
    FeatureGroups_System_Description: 'System administration',

    // Admin -> System -> API
    Api_Text: 'API',
    Api_Description: 'Learn how to interact with Nexus Repository Manager programmatically',

    // Admin -> System -> Capabilities
    Capabilities_Text: 'Capabilities',
    Capabilities_Description: 'Manage capabilities',
    Capabilities_Update_Mask: 'Updating capability',
    Capabilities_Enable_Mask: 'Enabling capability',
    Capabilities_Disable_Mask: 'Disabling capability',
    Capabilities_Update_Error: 'You do not have permission to update capabilities',
    Capability_CapabilityAdd_Create_Error: 'You do not have permission to update capabilities',
    Capabilities_Update_Success: 'Capability updated: {0}',
    Capability_CapabilitySettingsForm_Update_Error: 'You do not have permission to create capabilities',
    Capabilities_Create_Title: 'Create {0} Capability',
    Capabilities_Create_Success: 'Capability created: {0}',
    Capabilities_Delete_Success: 'Capability deleted: {0}',
    Capability_CapabilityList_New_Button: 'Create capability',
    Capability_CapabilityList_Type_Header: 'Type',
    Capability_CapabilityList_Description_Header: 'Description',
    Capability_CapabilityList_Notes_Header: 'Notes',
    Capability_CapabilityList_EmptyText: 'No capabilities defined',
    Capability_CapabilityList_Filter_EmptyText: 'No capability matched criteria "$filter"',
    Capability_CapabilityFeature_Delete_Button: 'Delete',
    Capability_CapabilityFeature_Enable_Button: 'Enable',
    Capability_CapabilityFeature_Disable_Button: 'Disable',
    Capability_CapabilitySummary_Title: 'Summary',
    Capability_CapabilitySettings_Title: 'Settings',
    Capability_CapabilitySettingsForm_Enabled_FieldLabel: 'Enable this capability',
    Capability_CapabilitySummary_Status_Title: 'Status',
    Capability_CapabilitySummary_About_Title: 'About',
    Capability_CapabilitySummary_Notes_Title: 'Notes',
    Capabilities_Enable_Text: 'Capability enabled: {0}',
    Capabilities_Disable_Text: 'Capability disabled: {0}',
    Capabilities_Select_Title: 'Select Capability Type',
    Capability_CapabilitySelectType_Description_Header: 'Description',
    Capability_CapabilitySelectType_Type_Header: 'Type',
    Capabilities_TypeName_Text: 'Type',
    Capabilities_Description_Text: 'Description',
    Capabilities_State_Text: 'State',
    Capability_CapabilitySummary_Notes_HelpText: 'Optional notes about configured capability',
    Capability_CapabilityStatus_EmptyText: 'This capability does not provide any status',

    // Admin -> System -> Email Server
    SmtpSettings_Text: 'Email Server',
    SmtpSettings_Description: 'Manage email server configuration',
    System_SmtpSettings_Update_Error: 'You do not have permission to configure email server',
    System_SmtpSettings_Update_Success: 'Email server configuration $action',
    System_SmtpSettings_Enabled_FieldLabel: 'Enabled',
    System_SmtpSettings_Host_FieldLabel: 'Host',
    System_SmtpSettings_Port_FieldLabel: 'Port',
    System_SmtpSettings_Username_FieldLabel: 'Username',
    System_SmtpSettings_Password_FieldLabel: 'Password',
    System_SmtpSettings_FromAddress_FieldLabel: 'From address',
    System_SmtpSettings_SubjectPrefix_FieldLabel: 'Subject prefix',
    System_SmtpSettings_SslTlsSection_FieldLabel: 'SSL/TLS options',
    System_SmtpSettings_StartTlsEnabled_FieldLabel: 'Enable STARTTLS support for insecure connections',
    System_SmtpSettings_StartTlsRequired_FieldLabel: 'Require STARTTLS support',
    System_SmtpSettings_SslOnConnectEnabled_FieldLabel: 'Enable SSL/TLS encryption upon connection',
    System_SmtpSettings_SslCheckServerIdentityEnabled_FieldLabel: 'Enable server identity check',

    System_SmtpSettings_VerifyServer_Button: 'Verify email server',
    System_VerifySmtpConnection_VerifyServer_Title: 'Verify Email Server',
    System_VerifySmtpConnection_HelpText: 'Where do you want to send the test email?',
    SmtpSettings_Verify_Mask: 'Checking email server {0}',
    SmtpSettings_Verify_Success: 'Email server verification email sent successfully',

    // Admin -> System -> HTTP
    HttpSettings_Text: 'HTTP',
    HttpSettings_Description: 'Manage outbound HTTP/HTTPS configuration',
    System_HttpSettings_Update_Error: 'You do not have permission to configure HTTP',
    System_HttpSettings_Update_Success: 'HTTP system settings $action',
    System_HttpSettings_Proxy_Title: 'HTTP proxy',
    System_HttpSettings_ProxyHost_FieldLabel: 'HTTP proxy host',
    System_HttpSettings_ProxyHost_HelpText: 'No http:// required (e.g. "proxy-host" or "192.168.1.101")',
    System_HttpSettings_ProxyPort_FieldLabel: 'HTTP proxy port',
    System_HttpSettings_Authentication_Title: 'Authentication',
    System_HttpSettings_ExcludeHosts_FieldLabel: 'Hosts to exclude from HTTP/HTTPS proxy',
    System_HttpSettings_ExcludeHosts_HelpText: 'Accepts Java "http.nonProxyHosts" wildcard patterns (one per line, no \'|\' hostname delimiters)',
    System_HttpSettings_HttpsProxy_Title: 'HTTPS proxy',
    System_HttpSettings_HttpsProxyHost_FieldLabel: 'HTTPS proxy host',
    System_HttpSettings_HttpsProxyHost_HelpText: 'No https:// required (e.g. "proxy-host" or "192.168.1.101")',
    System_HttpSettings_HttpsProxyPort_FieldLabel: 'HTTPS proxy port',
    System_HttpSettings_HttpsProxyAuthentication_Title: 'Authentication',

    // Admin -> System -> Bundles
    Bundles_Text: 'Bundles',
    Bundles_Description: 'View OSGI bundles',
    System_BundleList_Filter_EmptyText: 'No bundles matched "$filter"',
    System_BundleList_ID_Header: 'ID',
    System_BundleList_Name_Header: 'Name',
    System_BundleList_SymbolicName_Header: 'Symbolic Name',
    System_BundleList_Version_Header: 'Version',
    System_BundleList_State_Header: 'State',
    System_BundleList_Location_Header: 'Location',
    System_BundleList_Level_Header: 'Level',
    System_BundleList_Fragment_Header: 'Fragment',
    System_Bundles_Details_Tab: 'Summary',
    Bundles_ID_Info: 'ID',
    Bundles_Name_Info: 'Name',
    Bundles_SymbolicName_Info: 'Symbolic Name',
    Bundles_Version_Info: 'Version',
    Bundles_State_Info: 'State',
    Bundles_Location_Info: 'Location',
    Bundles_StartLevel_Info: 'Start Level',
    Bundles_Fragment_Info: 'Fragment',
    Bundles_Fragments_Info: 'Fragments',
    Bundles_FragmentHosts_Info: 'Fragment Hosts',
    Bundles_LastModified_Info: 'Last Modified',
    Bundles_Summary_Info: '{0}',

    // Admin -> System -> Nodes
    Nodes_Toggling_read_only_mode: 'Toggling read-only mode',
    Nodes_Disable_read_only_mode: 'Disable read-only mode',
    Nodes_Disable_read_only_mode_dialog: 'Disable read-only mode?',
    Nodes_Enable_read_only_mode: 'Enable read-only mode',
    Nodes_Enable_read_only_mode_dialog: 'Enable read-only mode?',
    Nodes_Read_only_mode_warning: 'Nexus Repository is in read-only mode',
    Nodes_force_release_dialog: 'Forcibly disable read-only mode?',
    Nodes_force_release: 'Force disable read-only mode',
    Nodes_Quorum_lost_warning: 'Not enough Nexus Repository Manager nodes in the cluster are reachable so quorum cannot be achieved; database is read only. <a href="#admin/system/nodes/clusterreset">Troubleshoot</a>',
    Nodes_OSS_Message: 'You are running a single-node instance of Nexus Repository Manager.',
    Nodes_enable_read_only_mode_dialog_description: 'Are you sure you want to reject additions of new' +
    ' components and changes to configuration?',
    Nodes_disable_read_only_mode_dialog_description: 'Are you sure you want to stop rejecting additions of new' +
    ' components and changes to configuration?',
    Nodes_force_release_warning: 'Warning: read-only mode has been enabled by system tasks. Releasing read-only mode before those tasks are complete may cause them to fail and/or cause data loss.',
    Nodes_force_release_confirmation: 'Are you sure you want to forcibly release read-only mode?',
    Nodes_NodeSettings_Title: 'Edit Node',
    Nodes_NodeSettingsForm_Update_Error: 'You do not have permission to update nodes',
    Nodes_NodeSettingsForm_Update_Success: 'Node updated, node is now named: ',
    Nodes_NodeSettingsForm_ID_FieldLabel: 'Node ID',
    Nodes_NodeSettingsForm_ID_HelpText: 'System-generated node identity',
    Nodes_NodeSettingsForm_Local_FieldLabel: 'Local',
    Nodes_NodeSettingsForm_Local_HelpText: 'Whether the current UI session is connected to the listed node',
    Nodes_NodeSettingsForm_SocketAddress_FieldLabel: 'Socket Address',
    Nodes_NodeSettingsForm_SocketAddress_HelpText: 'The IP address and port number used by the listed node to communicate with the cluster',
    Nodes_NodeSettingsForm_FriendlyName_FieldLabel: 'Node Name',
    Nodes_NodeSettingsForm_FriendlyName_HelpText: 'Custom alias for this node',

    // Admin -> System -> Tasks
    Tasks_Text: 'Tasks',
    Tasks_Description: 'Manage scheduled tasks',
    Tasks_Select_Title: 'Select a Type',
    Task_TaskSelectType_Filter_EmptyText: 'No types matched "$filter"',
    Task_TaskSelectType_Name_Header: 'Type',
    Tasks_Update_Mask: 'Updating task',
    Tasks_Run_Mask: 'Running task',
    Tasks_Stop_Mask: 'Stopping task',
    Task_TaskAdd_Create_Error: 'You do not have permission to create tasks',
    Tasks_Create_Title: 'Create {0} Task',
    Tasks_Create_Success: 'Task created: {0}',
    Task_TaskList_New_Button: 'Create task',
    Task_TaskList_Name_Header: 'Name',
    Task_TaskList_Type_Header: 'Type',
    Task_TaskList_Status_Header: 'Status',
    Task_TaskList_Schedule_Header: 'Schedule',
    Task_TaskList_NextRun_Header: 'Next run',
    Task_TaskList_LastRun_Header: 'Last run',
    Task_TaskList_LastResult_Header: 'Last result',
    Task_TaskList_EmptyState: 'No scheduled tasks defined',
    Task_TaskList_Filter_EmptyState: 'No scheduled tasks matched "$filter"',
    Task_TaskFeature_Delete_Button: 'Delete task',
    Tasks_Delete_Success: 'Task deleted: {0}',
    Task_TaskFeature_Run_Button: 'Run',
    Tasks_RunConfirm_Title: 'Confirm?',
    Tasks_RunConfirm_HelpText: 'Run {0} task?',
    Tasks_Run_Success: 'Task started: {0}',
    Task_TaskFeature_Stop_Button: 'Stop',
    Tasks_StopConfirm_Title: 'Confirm?',
    Tasks_StopConfirm_HelpText: 'Stop {0} task?',
    Tasks_Stop_Success: 'Task stopped: {0}',
    TaskFeature_Summary_Title: 'Summary',
    TaskFeature_Summary_Status_Section_Title: 'Run Status',
    TaskFeature_Status_Node_Column: 'Node ID',
    TaskFeature_Status_Status_Column: 'Status',
    TaskFeature_Status_LastResult_Column: 'Last result',
    Tasks_Settings_Title: 'Settings',
    Tasks_ID_Info: 'ID',
    Tasks_Name_Info: 'Name',
    Tasks_Type_Info: 'Type',
    Tasks_Status_Info: 'Status',
    Tasks_NextRun_Info: 'Next run',
    Tasks_LastRun_Info: 'Last run',
    Tasks_LastResult_Info: 'Last result',
    Task_TaskSettingsForm_Update_Error: 'You do not have permission to update tasks or task is readonly',
    Tasks_Update_Success: 'Task updated: {0}',
    Task_TaskSettingsForm_Enabled_FieldLabel: 'Task enabled',
    Task_TaskSettingsForm_Enabled_HelpText: 'This flag determines if the task is currently active.  To disable this task for a period of time, de-select this checkbox.',
    Task_TaskSettingsForm_Name_FieldLabel: 'Task name',
    Task_TaskSettingsForm_Name_HelpText: 'A name for the scheduled task',
    Task_TaskSettingsForm_Email_FieldLabel: 'Notification email',
    Task_TaskSettingsForm_Email_HelpText: 'The email address where an email will be sent in case that task execution fails',
    Task_TaskScheduleFieldSet_Recurrence_FieldLabel: 'Task frequency',
    Task_TaskScheduleFieldSet_Recurrence_HelpText: 'The frequency this task will run. Manual - this task can only be run manually. Once - run the task once at the specified date/time. Daily - run the task every day at the specified time. Weekly - run the task every week on the specified day at the specified time. Monthly - run the task every month on the specified day(s) and time. Advanced - run the task using the supplied cron string',
    Task_TaskScheduleFieldSet_Recurrence_EmptyText: 'Select a frequency',
    Task_TaskScheduleFieldSet_Recurrence_ManualItem: 'Manual',
    Task_TaskScheduleFieldSet_Recurrence_OnceItem: 'Once',
    Task_TaskScheduleFieldSet_Recurrence_HourlyItem: 'Hourly',
    Task_TaskScheduleFieldSet_Recurrence_DailyItem: 'Daily',
    Task_TaskScheduleFieldSet_Recurrence_WeeklyItem: 'Weekly',
    Task_TaskScheduleFieldSet_Recurrence_MonthlyItem: 'Monthly',
    Task_TaskScheduleFieldSet_Recurrence_AdvancedItem: 'Advanced (provide a CRON expression)',
    Task_TaskScheduleDaily_StartDate_FieldLabel: 'Start date',
    Task_TaskScheduleHourly_EndDate_FieldLabel: 'Start time',
    Task_TaskScheduleDaily_Recurring_FieldLabel: 'Time to run this task',
    Task_TaskScheduleMonthly_Days_FieldLabel: 'Days to run this task',
    Task_TaskScheduleMonthly_Days_BlankText: 'At least one day should be selected',
    Task_TaskScheduleAdvanced_Cron_FieldLabel: 'CRON expression',
    Task_TaskScheduleAdvanced_Cron_EmptyText: '* * * * * * *',
    Task_TaskScheduleAdvanced_Cron_HelpText: 'A cron expression that will control the running of the task.',
    Task_TaskScheduleAdvanced_Cron_AfterBodyEl: '<div style="font-size: 11px"><p>From left to right the fields and accepted values are:</p>' +
    '<table>' +
    '<thead><tr><th>Field Name</th><th>Allowed Values</th></tr></thead>' +
    '<tbody>' +
    '<tr><td>Seconds</td><td>0-59</td></tr>' +
    '<tr><td>Minutes</td><td>0-59</td></tr>' +
    '<tr><td>Hours</td><td>0-23</td></tr>' +
    '<tr><td>Day of month</td><td>1-31</td></tr>' +
    '<tr><td>Month</td><td>1-12 or JAN-DEC</td></tr>' +
    '<tr><td>Day of week</td><td>1-7 or SUN-SAT</td></tr>' +
    '<tr><td>Year(optional)</td><td>empty, 1970-2099</td></tr>' +
    '</tbody>' +
    '</table>' +
    '<br/>'+
    '<p>Special tokens include: * (all acceptable values), ? (no specific value), - (ranges, e.g. 10-12)</p>' +
    '</div> '
    ,
    Task_TaskScheduleManual_HelpText: 'Without recurrence, this service can only be run manually.',

    // Authentication section
    System_AuthenticationSettings_Username_FieldLabel: 'Username',
    System_AuthenticationSettings_Password_FieldLabel: 'Password',
    System_AuthenticationSettings_WindowsNtlmHostname_FieldLabel: 'Windows NTLM hostname',
    System_AuthenticationSettings_WindowsNtlmDomain_FieldLabel: 'Windows NTLM domain',

    // HTTP Request section
    System_HttpRequestSettings_UserAgentCustomization_FieldLabel: 'User-agent customization',
    System_HttpRequestSettings_UserAgentCustomization_HelpText: 'Custom fragment to append to "User-Agent" header in HTTP requests.',
    System_HttpRequestSettings_Timeout_FieldLabel: 'Connection/Socket timeout',
    System_HttpRequestSettings_Timeout_HelpText: 'Seconds to wait for activity before stopping and retrying the connection.',
    System_HttpRequestSettings_Attempts_FieldLabel: 'Connection/Socket retry attempts',
    System_HttpRequestSettings_Attempts_HelpText: 'Total retries if the initial connection attempt suffers a timeout',

    // User -> Account
    Users_Text: 'Account',
    Users_Description: 'Manage your account',
    User_UserAccount_Update_Success: 'User account settings $action',
    User_UserAccount_Update_Error: 'External users cannot be updated',
    User_UserAccount_ID_FieldLabel: 'ID',
    User_UserAccount_ID_HelpText: 'This is used as your username.',
    User_UserAccount_First_FieldLabel: 'First name',
    User_UserAccount_Last_FieldLabel: 'Last Name',
    User_UserAccount_Email_FieldLabel: 'Email',
    User_UserAccount_Password_Button: 'Change password',

    // User -> NuGet Api Key
    NuGetApiKey_Text:'NuGet API Key',
    NuGetApiKey_Description: 'Configure credentials for NuGet repositories',
    Nuget_NuGetApiKeyDetails_Html: 'Your NuGet API Key enables pushing packages using NuGet.exe. ' +
    '<span style="font-weight: bold;">Keep this key secret!</span>',
    Nuget_NuGetApiKeyDetails_ApiKey_Text: 'Your NuGet API Key is:',
    Nuget_NuGetApiKeyDetails_Register_Text: 'You can register this key for a given repository with the following command:',
    Nuget_NuGetApiKeyDetails_Register_Value: 'nuget setapikey {0} -source {1}',
    Nuget_NuGetApiKeyDetails_AutoClose_Html: 'This window will automatically close after one minute.',
    Nuget_NuGetApiKeyDetails_AutoClose_Message: 'Automatically closing NuGet API Key details due to timeout',
    Nuget_NuGetApiKey_Instructions_Text: 'A new API Key will be created the first time it is accessed. Resetting your API Key will invalidate the current key.',
    Nuget_NuGetApiKey_Access_Button: 'Access API Key',
    Nuget_NuGetApiKey_Access_HelpText: 'Accessing NuGet API Key requires validation of your credentials.',
    Nuget_NuGetApiKey_Reset_Button: 'Reset API Key',
    Nuget_NuGetApiKey_Reset_HelpText: 'Resetting NuGet API Key requires validation of your credentials.',

    // Admin -> System -> Licensing
    Licensing_Text: 'Licensing',
    Licensing_Description: 'A valid license is required for PRO features. Manage it here.',
    Licensing_LicensingDetails_Company_FieldLabel: 'Company',
    Licensing_LicensingDetails_Name_FieldLabel: 'Name',
    Licensing_LicensingDetails_Email_FieldLabel: 'Email',
    Licensing_LicensingDetails_EffectiveDate_FieldLabel: 'Effective date',
    Licensing_LicensingDetails_ExpirationDate_FieldLabel: 'Expiration date',
    Licensing_LicensingDetails_Type_FieldLabel: 'License type',
    Licensing_LicensingDetails_LicensedUsers_FieldLabel: 'Number of licensed users',
    Licensing_LicensingDetails_Connections_FieldLabel: 'Number of unique IP addresses that have connected in the last 7 days',
    Licensing_LicensingDetails_Fingerprint_FieldLabel: 'Fingerprint',
    Licensing_LicensingDetails_InstallLicense_Title: 'Install license',
    Licensing_LicensingDetails_InstallLicense_Html: '<p>Installing a new license requires restarting the server to take effect</p>',
    Licensing_LicensingDetails_LicenseSelect_Button: 'Select license&hellip;',
    Licensing_LicensingDetails_LicenseInstall_Button: 'Install license',
    Licensing_LicenseAgreement_Title: 'Nexus Repository Manager License Agreement',
    Licensing_LicenseAgreement_Yes_Button: 'I agree',
    Licensing_LicenseAgreement_No_Button: 'I do not agree',
    Licensing_LicenseAgreement_Download_Button: 'Download a copy of the license.',
    Licensing_Install_Success: 'License installed. Restart is only required if you are enabling new PRO features.',
    Licensing_Authentication_Validation: '{0} a license requires validation of your credentials.',

    // Admin -> System -> Licensing -> Recent Connections
    LicenseUsers_Title: 'Recent Connections',
    LicenseUsers_Description: 'Reports active users in the last 7 days',
    Licensing_LicenseUserList_Download_Button: 'Download',
    Licensing_LicenseUserList_IP_Header: 'IP',
    Licensing_LicenseUserList_Date_Header: 'Date',
    Licensing_LicenseUserList_User_Header: 'User',
    Licensing_LicenseUserList_Agent_Header: 'User agent',
    Licensing_LicenseUserList_EmptyText: 'No active users in the last 7 days.'
  },

  /**
   * String bundles.
   *
   * @type {Object}
   */
  bundles: {
    'NX.coreui.migration.Controller': {
      Feature_Text: 'Upgrade',
      Feature_Description: 'Upgrade configuration and content from Nexus Repository Manager 2',

      Activate_Mask: 'Loading',

      Configure_Mask: 'Configuring',
      Configure_Message: 'Upgrade configured',

      Cancel_Confirm_Title: 'Cancel Upgrade',
      Cancel_Confirm_Text: 'Do you want to cancel upgrade?',
      Cancel_Mask: 'Canceling',
      Cancel_Message: 'Upgrade canceled',

      IncompleteCancel_Title: 'Configuration Incomplete',
      IncompleteCancel_Text: 'Upgrade has been partially configured and needs to be reset to continue.',
      IncompleteCancel_Mask: 'Resetting',

      PlanStepDetail_Mask: 'Fetching details'
    },

    'NX.coreui.migration.AgentScreen': {
      Title: 'Agent Connection',
      Description: "<p>Configure the connection to remote server's upgrade-agent.<br/>" +
      'The remote server must have an upgrade-agent configured and enabled.</p>',
      Endpoint_FieldLabel: 'URL',
      Endpoint_HelpText: "The base URL of the remote server",
      Token_FieldLabel: 'Access Token',
      Token_HelpText: "The access token from the remote server's upgrade-agent settings"
    },

    'NX.coreui.migration.AgentStep': {
      Connect_Mask: 'Connecting',
      Connect_Message: 'Connected'
    },

    'NX.coreui.migration.ContentScreen': {
      Title: 'Content',
      Description: '<p>What content from Nexus Repository Manager 2 would you like to transfer?</p>',
      Repositories_FieldLabel: 'Repository configuration and content',
      Configuration_FieldLabel: 'Server configuration'
    },

    'NX.coreui.migration.OverviewScreen': {
      Title: 'Overview',
      Description: '<p>This wizard will help you upgrade from Nexus Repository Manager 2.</p>' +
      '<p>Many aspects of a server can be upgraded <strong>automatically</strong>:' +
      '<ul>' +
      '<li>Configuration: security (users, roles and privileges) and other applicable system settings </li>' +
      '<li>Repositories in supported formats: maven2, nuget, npm, rubygems, site</li>' +
      '</ul>' +
      '</p>' +
      '<p>Some aspects are <strong>incompatible</strong> and can not be automatically upgraded:' +
      '<ul>' +
      '<li>Unsupported repository formats: yum, p2, obr</li>' +
      '<li>Scheduled tasks</li>' +
      '<li>Capabilities</li>' +
      '</ul>' +
      '</p>' +
      '<p>Upgrade is incremental. We recommend upgrading one or two repositories first to ensure that the process works, then repeat the process and upgrade the rest. Take note of the following:' +
      '<ul>' +
      '<li>Repository upgrade could take <strong>considerable time</strong>.</li>' +
      '<li>Until upgrade has successfully completed, it is not recommended to make any configuration changes in Nexus Repository Manager 3, as the configuration is volatile during this time.</li>' +
      '<li>Also during the upgrade, all of the repositories in Nexus Repository Manager 3 will be offline.</li>' +
      '<li>Server configuration can be transferred multiple times, each time it will completely replace existing configurations.</li>' +
      '</ul>' +
      '</p>'
    },

    'NX.coreui.migration.PhaseFinishScreen': {
      Title: 'Finishing',
      Description: '<p>Upgrade is finishing.</p>',
      Abort_Button: 'Abort',
      Done_Button: 'Done'
    },

    'NX.coreui.migration.RepositoryDefaultsScreen': {
      $extend: 'NX.coreui.migration.RepositoryCustomizeWindow',

      Title: 'Repository Defaults',
      Description: '<p>Configure the default settings used for repository upgrade.<br/>' +
      'Per-repository settings may be customized when selecting repositories to upgrade.</p>',
      IngestMethod_HelpText: 'Choose how the repository content should be transferred. The method you choose may not be supported by all repositories.'
    },

    'NX.coreui.migration.RepositoryCustomizeWindow': {
      Title: 'Customize {0}',

      BlobStore_FieldLabel: 'Destination',
      BlobStore_HelpText: 'Choose where the repository content should be stored',
      BlobStore_EmptyText: 'Choose a blob store',

      IngestMethod_FieldLabel: 'Method',
      IngestMethod_HelpText: 'Choose how the repository content should be transferred',
      IngestMethod_EmptyText: 'Choose a repository content transfer method',
      IngestMethod_Link: 'Hard link (fastest)',
      IngestMethod_Copy: 'Filesystem copy (slow)',
      IngestMethod_Download: 'Download (slowest)'
    },

    'NX.coreui.migration.PlanStepDetailWindow': {
      Title: '{0}',
      EmptyLog: 'No progress',
      Timestamp_Column: 'Timestamp',
      Message_Column: 'Message'
    },

    'NX.coreui.migration.PreviewScreen': {
      Title: 'Preview',
      Description: '<p>Here is a preview of the upgrade configuration.</p>',
      Name_Column: 'Name',
      State_Column: 'State',
      Begin_Button: 'Begin'
    },

    'NX.coreui.migration.PreviewStep': {
      Begin_Confirm_Title: 'Begin Upgrade',
      Begin_Confirm_Text: 'Do you want to begin upgrade?',
      Begin_Mask: 'Upgrade beginning',
      Begin_Message: 'Upgrade begun'
    },

    'NX.coreui.migration.ProgressScreenSupport': {
      Name_Column: 'Name',
      Status_Column: 'Status',
      State_Column: 'State',
      Complete_Column: 'Complete'
    },

    'NX.coreui.migration.ProgressStepSupport': {
      Loading_Mask: 'Loading'
    },

    'NX.coreui.migration.RepositoriesScreen': {
      Title: 'Repositories',
      Description: '<p>Select the repositories to be upgraded.<br/>' +
      'Customize advanced configuration of the upgrade per-repository as needed.</p>',
      Repository_Column: 'Repository',
      Type_Column: 'Type',
      Format_Column: 'Format',
      Supported_Column: 'Supported',
      Status_Column: 'Status',
      Destination_Column: 'Destination',
      Method_Column: 'Method',
      Action_Tooltip: 'Customize repository options'
    },

    'NX.coreui.migration.RepositoriesStep': {
      $extend: 'NX.coreui.migration.ProgressStepSupport'
    },

    'NX.coreui.migration.RepositoryDefaultsStep': {
      $extend: 'NX.coreui.migration.ProgressStepSupport'
    },

    'NX.coreui.migration.PhasePrepareScreen': {
      Title: 'Preparing',
      Description: '<p>Preparing for upgrade.</p>',
      Abort_Button: 'Abort',
      Continue_Button: 'Continue'
    },

    'NX.coreui.migration.PhasePrepareStep': {
      $extend: 'NX.coreui.migration.ProgressStepSupport',

      Abort_Confirm_Title: 'Abort Upgrade',
      Abort_Confirm_Text: 'Do you want to abort upgrade?',
      Abort_Mask: 'Upgrade aborting',
      Abort_Message: 'Upgrade aborted',

      Continue_Confirm_Title: 'Continue Upgrade',
      Continue_Confirm_Text: 'Do you want to continue upgrade?',
      Continue_Mask: 'Upgrade continuing',
      Continue_Message: 'Upgrade continuing'
    },

    'NX.coreui.migration.PhaseSyncScreen': {
      Title: 'Synchronizing',
      Description: '<p>Upgrade is synchronizing changes.</p>',
      Abort_Button: 'Abort',
      Continue_Button: 'Continue',
      Continue_Button_Pending: '<i class="fa fa-spinner fa-spin fa-fw"></i> Continue'
    },

    'NX.coreui.migration.PhaseSyncStep': {
      $extend: 'NX.coreui.migration.ProgressStepSupport',

      Abort_Confirm_Title: 'Abort Upgrade',
      Abort_Confirm_Text: 'Do you want to abort upgrade?',
      Abort_Mask: 'Upgrade aborting',
      Abort_Message: 'Upgrade aborted',

      Stop_Waiting_Confirm_Title: 'Stop waiting for changes',
      Stop_Waiting_Confirm_Text: 'Any future changes to repositories will not be synchronized. Proceed?',
      Stop_Waiting_Confirm_Mask: 'Finalizing changes',
      Stop_Waiting_Confirm_Message: 'Changes finalized',

      Finish_Mask: 'Upgrade finishing',
      Finish_Message: 'Upgrade finishing'
    },

    'NX.coreui.migration.PhaseFinishStep': {
      $extend: 'NX.coreui.migration.ProgressStepSupport',

      Abort_Confirm_Title: 'Abort Upgrade',
      Abort_Confirm_Text: 'Do you want to abort upgrade?',
      Abort_Mask: 'Upgrade aborting',
      Abort_Message: 'Upgrade aborted',

      Done_Mask: 'Confirming',
      Done_Message: 'Upgrade done'
    },

    'NX.coreui.audit.AuditController': {
      Text: 'Audit',
      Description: 'System audit information',
      Clear_Title: 'Clear Audit Data',
      Clear_Body: 'Clear audit data?',
      Clear_Mask: 'Clearing audit data',
      Clear_Success: 'Audit data cleared'
    },

    'NX.coreui.audit.AuditList': {
      EmptyText: 'No audit data',
      Filter_EmptyText: 'No audit data matching "$filter"',
      Domain: 'Domain',
      Type: 'Type',
      Context: 'Context',
      Timestamp: 'Timestamp',
      NodeId: 'Node ID',
      Initiator: 'Initiator',
      Attribute: 'Attribute: {0}',
      Clear_Button: 'Clear'
    },

    'NX.coreui.controller.FileDescriptorWarnings': {
      File_Descriptor_Warning: '<a href="http://links.sonatype.com/products/nexus/system-reqs#filehandles" target="_blank">System Requirement: max file descriptors [{0}] likely too low, increase to at least [{1}].</a>'
    }
  }
}, function(self) {
  NX.I18n.register(self);
});
