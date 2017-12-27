package org.sonatype.nexus.repository.sizeblobcount.internal;


import org.sonatype.nexus.logging.task.TaskLogging;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.repository.Type;
import org.sonatype.nexus.repository.sizeblobcount.SizeBlobCountAttributesFacet;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.scheduling.Cancelable;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkNotNull;
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

    public static final String PREFIX_MESSAGE = "Calculate the size and the blob count of the repository ";
    private final Type hostedType;

    @Inject
    public SizeBlobCountAttributesCalculatingTask(@Named(HostedType.NAME) final Type hostedType) {
        this.hostedType = checkNotNull(hostedType);
    }


    @Override
    public String getMessage() {
        return PREFIX_MESSAGE + getRepositoryField();
    }


    @Override
    protected void execute(Repository repository) {
        repository.facet(SizeBlobCountAttributesFacet.class).calculateSizeBlobCount();

    }

    @Override
    protected boolean appliesTo(Repository repository) {
        return hostedType.equals(repository.getType());
    }
}
