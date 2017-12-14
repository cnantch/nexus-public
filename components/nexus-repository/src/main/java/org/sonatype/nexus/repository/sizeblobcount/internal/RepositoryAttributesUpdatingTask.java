package org.sonatype.nexus.repository.sizeblobcount.internal;


import org.sonatype.nexus.logging.task.TaskLogging;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.scheduling.Cancelable;
import org.sonatype.nexus.scheduling.TaskSupport;

import javax.inject.Inject;
import javax.inject.Named;

import static org.sonatype.nexus.logging.task.TaskLogType.NEXUS_LOG_ONLY;

/**
 * Background task (hidden from users) that updates the size and the blob count of a repository
 *
 * @since 3.7.0
 */
@Named
@TaskLogging(NEXUS_LOG_ONLY)
public class RepositoryAttributesUpdatingTask  extends TaskSupport
        implements Cancelable
{


    private RepositoryManager repositoryManager;

    @Inject
    public RepositoryAttributesUpdatingTask(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    @Override
    public String getMessage() {
        return "Update the size and the blob count of the repository";
    }

    @Override
    protected Object execute() throws Exception {
        repositoryManager.calculateSizeBlobCount();
        return null;
    }
}
