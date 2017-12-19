package org.sonatype.nexus.repository.sizeblobcount.internal;


import org.sonatype.nexus.logging.task.TaskLogging;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.sizeblobcount.RepositoryAttributesFacet;
import org.sonatype.nexus.repository.sizeblobcount.SizeBlobCount;
import org.sonatype.nexus.repository.types.HostedType;
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
public class RepositoryAttributesUpdatingTask  extends RepositoryTaskSupport
        implements Cancelable
{


    @Override
    public String getMessage() {
        return "Calculate the size and the blob count of the repository " + getRepositoryField();
    }


    @Override
    protected void execute(Repository repository) {
        RepositoryAttributesFacet sizeBlobCountFacet = repository.facet(RepositoryAttributesFacet.class);
        SizeBlobCount sizeBlobCount = sizeBlobCountFacet.calculateSizeBlobCount();
        if (sizeBlobCount != null) {
            sizeBlobCountFacet.setSize(sizeBlobCount.getSize());
            sizeBlobCountFacet.setBlobCount(sizeBlobCount.getBlobCount());
        }

    }

    @Override
    protected boolean appliesTo(Repository repository) {
        return repository.getType() instanceof HostedType;
    }
}
