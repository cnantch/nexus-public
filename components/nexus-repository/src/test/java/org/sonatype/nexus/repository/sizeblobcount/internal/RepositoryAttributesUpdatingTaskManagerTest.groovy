package org.sonatype.nexus.repository.sizeblobcount.internal

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.scheduling.TaskConfiguration
import org.sonatype.nexus.scheduling.TaskInfo
import org.sonatype.nexus.scheduling.TaskScheduler
import org.sonatype.nexus.scheduling.schedule.Cron
import org.sonatype.nexus.scheduling.schedule.Schedule
import org.sonatype.nexus.scheduling.schedule.ScheduleFactory

import static org.mockito.Matchers.any
import static org.mockito.Matchers.eq
import static org.mockito.Mockito.*
import static org.sonatype.nexus.repository.sizeblobcount.internal.RepositoryAttributesUpdatingTaskDescriptor.TYPE_ID

class RepositoryAttributesUpdatingTaskManagerTest  extends TestSupport
{

    static final String CRON_EXPRESSION = '0 * * * * ?'

    @Mock
    private TaskScheduler taskScheduler

    @Mock
    private ScheduleFactory scheduleFactory

    @Mock
    private Cron cron

    @Mock
    private TaskInfo taskInfo

    private RepositoryAttributesUpdatingTaskManager repositoryAttributesUpdatingTaskManager;

    @Before
    public void setUp() throws Exception {
        repositoryAttributesUpdatingTaskManager = new RepositoryAttributesUpdatingTaskManager(taskScheduler,
        CRON_EXPRESSION)
    }

    @Test
    void 'will create a new updating attributes task if one does not exist on startup'() {
        TaskConfiguration taskConfiguration = new TaskConfiguration()
        taskConfiguration.setTypeId(TYPE_ID)

        when(taskScheduler.listsTasks()).thenReturn([])
        when(taskScheduler.getScheduleFactory()).thenReturn(scheduleFactory)
        when(taskScheduler.createTaskConfigurationInstance(TYPE_ID)).thenReturn(taskConfiguration)
        when(scheduleFactory.cron(any(Date), eq(CRON_EXPRESSION))).thenReturn(cron)

        repositoryAttributesUpdatingTaskManager.doStart()

        verify(taskScheduler).scheduleTask(taskConfiguration, cron)
    }

    @Test
    void 'will not create a duplicate updating attributes task if one exists on startup'() {
        TaskConfiguration taskConfiguration = new TaskConfiguration()
        taskConfiguration.setTypeId(TYPE_ID)

        when(taskInfo.getConfiguration()).thenReturn(taskConfiguration)
        when(taskScheduler.listsTasks()).thenReturn([taskInfo])

        repositoryAttributesUpdatingTaskManager.doStart()

        verify(taskScheduler, never()).scheduleTask(any(TaskConfiguration), any(Schedule))
    }


}
