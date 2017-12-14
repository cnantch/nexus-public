package org.sonatype.nexus.repository.sizeblobcount.internal;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @since 3.7.0
 */
@Named
@Singleton
public class RepositoryAttributesUpdatingTaskDescriptor extends TaskDescriptorSupport
{
    public static final String TYPE_ID = "repository.attributes-update";

    @Inject
    public RepositoryAttributesUpdatingTaskDescriptor() {
        super(TYPE_ID, RepositoryAttributesUpdatingTask.class, "Update size and the blob count", NOT_VISIBLE, NOT_EXPOSED);
    }
}
