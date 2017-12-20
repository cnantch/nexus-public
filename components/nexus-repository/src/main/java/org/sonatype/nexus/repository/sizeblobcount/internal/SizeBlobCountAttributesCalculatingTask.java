package org.sonatype.nexus.repository.sizeblobcount.internal;


import org.sonatype.nexus.logging.task.TaskLogging;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.repository.sizeblobcount.SizeBlobCountAttributesFacet;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.scheduling.Cancelable;

import javax.inject.Named;

import static org.sonatype.nexus.logging.task.TaskLogType.NEXUS_LOG_ONLY;

/**
 * Task that calculates the size and the blob count of a repository
 *
 * @since 3.7.0
 */
@Named
@TaskLogging(NEXUS_LOG_ONLY)
public class SizeBlobCountAttributesCalculatingTask extends RepositoryTaskSupport
        implements Cancelable
{


    @Override
    public String getMessage() {
        return "Calculate the size and the blob count of the repository " + getRepositoryField();
    }


    @Override
    protected void execute(Repository repository) {
        repository.facet(SizeBlobCountAttributesFacet.class).calculateSizeBlobCount();

    }

    @Override
    protected boolean appliesTo(Repository repository) {
        return repository.getType() instanceof HostedType;
    }
}
