package org.sonatype.nexus.repository.sizeblobcount.internal;

import org.sonatype.nexus.formfields.FormField;
import org.sonatype.nexus.formfields.RepositoryCombobox;
import org.sonatype.nexus.repository.sizeblobcount.SizeBlobCountAttributesFacet;
import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import static org.sonatype.nexus.repository.RepositoryTaskSupport.REPOSITORY_NAME_FIELD_ID;

/**
 * @since 3.7.0
 */
@Named
@Singleton
public class SizeBlobCountAttributesCalculatingTaskDescriptor extends TaskDescriptorSupport
{
    public static final String TYPE_ID = "repository.calculate-size-blobcount";

    @Inject
    public SizeBlobCountAttributesCalculatingTaskDescriptor() {
        super(TYPE_ID,
                SizeBlobCountAttributesCalculatingTask.class,
                " Calculate the size and the blob count of a repository",
                VISIBLE,
                EXPOSED,
                new RepositoryCombobox(
                        REPOSITORY_NAME_FIELD_ID,
                        "Repository",
                        "Select the repository which you will calculate the size and the blob count",
                        FormField.MANDATORY
                ).includingAnyOfFacets(SizeBlobCountAttributesFacet.class).includeAnEntryForAllRepositories()
        );
    }
}
