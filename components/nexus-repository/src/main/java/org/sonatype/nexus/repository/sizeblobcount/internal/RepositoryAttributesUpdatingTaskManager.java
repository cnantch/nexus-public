package org.sonatype.nexus.repository.sizeblobcount.internal;

import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.scheduling.TaskConfiguration;
import org.sonatype.nexus.scheduling.TaskScheduler;
import org.sonatype.nexus.scheduling.schedule.Schedule;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.TASKS;
import static org.sonatype.nexus.repository.sizeblobcount.internal.RepositoryAttributesUpdatingTaskDescriptor.TYPE_ID;

/**
 * @since 3.7.0
 */
@Named
@ManagedLifecycle(phase = TASKS)
@Singleton
public class RepositoryAttributesUpdatingTaskManager
        extends StateGuardLifecycleSupport
{
    private final TaskScheduler taskScheduler;

    private final String repositoryAttributesUpdatingCron;

    @Inject
    public RepositoryAttributesUpdatingTaskManager(final TaskScheduler taskScheduler,
                                                   @Named("${nexus.repositoryAttributesUpdate.cron:-0 */30 * * * ?}") final String repositoryAttributesUpdatingCron)
    {
        this.taskScheduler = checkNotNull(taskScheduler);
        this.repositoryAttributesUpdatingCron = checkNotNull(repositoryAttributesUpdatingCron);
    }

    @Override
    protected void doStart() throws Exception {
        if (!taskScheduler.listsTasks().stream().anyMatch((info) -> TYPE_ID.equals(info.getConfiguration().getTypeId()))) {
            TaskConfiguration configuration = taskScheduler.createTaskConfigurationInstance(TYPE_ID);
            Schedule schedule = taskScheduler.getScheduleFactory().cron(new Date(), repositoryAttributesUpdatingCron);
            taskScheduler.scheduleTask(configuration, schedule);
        }
    }
}
