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
package org.sonatype.nexus.repository.browse.internal;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.repository.browse.BrowseNodeConfiguration;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.BucketEntityAdapter;
import org.sonatype.nexus.repository.storage.ComponentDatabase;
import org.sonatype.nexus.scheduling.TaskConfiguration;
import org.sonatype.nexus.scheduling.TaskInfo;
import org.sonatype.nexus.scheduling.TaskRemovedException;
import org.sonatype.nexus.scheduling.TaskScheduler;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Streams.stream;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.TASKS;
import static org.sonatype.nexus.orient.entity.AttachedEntityHelper.id;
import static org.sonatype.nexus.orient.transaction.OrientTransactional.inTx;

/**
 * @since 3.6
 */
@Named
@ManagedLifecycle(phase = TASKS)
@Singleton
public class RebuildBrowseNodesManager
    extends StateGuardLifecycleSupport
{
  @VisibleForTesting
  static final String SELECT_ANY_ASSET_BY_BUCKET = "select @rid from asset where bucket = :bucket limit 1";

  @VisibleForTesting
  static final String SELECT_ANY_BROWSE_NODE_BY_BUCKET = "select @rid from browse_node where repository_name = :repositoryName limit 1";

  private final Provider<DatabaseInstance> componentDatabaseInstanceProvider;

  private final TaskScheduler taskScheduler;

  private final boolean automaticRebuildEnabled;

  private final BucketEntityAdapter bucketEntityAdapter;

  @Inject
  public RebuildBrowseNodesManager(@Named(ComponentDatabase.NAME)
                                   final Provider<DatabaseInstance> componentDatabaseInstanceProvider,
                                   final TaskScheduler taskScheduler,
                                   final BrowseNodeConfiguration configuration,
                                   final BucketEntityAdapter bucketEntityAdapter)
  {
    this.componentDatabaseInstanceProvider = checkNotNull(componentDatabaseInstanceProvider);
    this.taskScheduler = checkNotNull(taskScheduler);
    this.automaticRebuildEnabled = checkNotNull(configuration).isAutomaticRebuildEnabled();
    this.bucketEntityAdapter = bucketEntityAdapter;
  }

  @Override
  protected void doStart() { // NOSONAR
    if (!automaticRebuildEnabled) {
      return;
    }

    Stopwatch sw = Stopwatch.createStarted();
    try {
      Collection<Bucket> buckets = inTx(componentDatabaseInstanceProvider).call(db -> {
        return stream(bucketEntityAdapter.browse(db)).filter(bucket -> {
          boolean hasAssets = !execute(db, SELECT_ANY_ASSET_BY_BUCKET, singletonMap("bucket", id(bucket)))
              .isEmpty();
          boolean hasBrowseNodes = !execute(db, SELECT_ANY_BROWSE_NODE_BY_BUCKET,
              singletonMap("repositoryName", bucket.getRepositoryName()))
              .isEmpty();

          if (hasAssets ^ hasBrowseNodes) {
            log.debug("browse_node table will be rebuilt for bucket={}", id(bucket));
            return true;
          }
          else if (!hasAssets) {
            log.debug("browse_node table won't be populated as there are no assets for bucketId={}", id(bucket));
          }
          else {
            log.debug("browse_node table already populated for bucketId={}", id(bucket));
          }
          return false;
        }).collect(toList());
      });

      for (Bucket bucket : buckets) {
        if (!launchExistingTask(bucket.getRepositoryName())) {
          launchNewTask(bucket.getRepositoryName());
        }
      }
    }
    catch (Exception e) {
      log.error("Failed to determine if the browse nodes need to be rebuilt for any repositories", e);
    }
    log.debug("scheduling rebuild browse nodes tasks took {} ms", sw.elapsed(TimeUnit.MILLISECONDS));
  }

  private boolean launchExistingTask(final String repositoryName) throws TaskRemovedException {
    for (TaskInfo taskInfo : taskScheduler.listsTasks()) {
      if (isRebuildTask(repositoryName, taskInfo)) {
        if (!TaskInfo.State.RUNNING.equals(taskInfo.getCurrentState().getState())) {
          taskInfo.runNow();
        }
        return true;
      }
    }

    return false;
  }

  private void launchNewTask(final String repositoryName) {
    TaskConfiguration configuration = taskScheduler
        .createTaskConfigurationInstance(RebuildBrowseNodesTaskDescriptor.TYPE_ID);
    configuration.setString(RebuildBrowseNodesTaskDescriptor.REPOSITORY_NAME_FIELD_ID, repositoryName);
    configuration.setName("Rebuild repository browse tree - (" + repositoryName + ")");
    taskScheduler.submit(configuration);
  }

  private boolean isRebuildTask(final String repositoryName, final TaskInfo taskInfo) {
    return RebuildBrowseNodesTaskDescriptor.TYPE_ID.equals(taskInfo.getConfiguration().getTypeId()) && repositoryName
        .equals(taskInfo.getConfiguration().getString(RebuildBrowseNodesTaskDescriptor.REPOSITORY_NAME_FIELD_ID));
  }

  private List<ODocument> execute(final ODatabaseDocumentTx db, // NOSONAR
                                  final String query,
                                  final Map<String, Object> parameters)
  {
    return db.command(new OCommandSQL(query)).execute(parameters);
  }
}
