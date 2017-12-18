package org.sonatype.nexus.repository.sizeblobcount.internal;

import org.sonatype.nexus.formfields.FormField;
import org.sonatype.nexus.formfields.RepositoryCombobox;
import org.sonatype.nexus.repository.sizeblobcount.RepositoryAttributesFacet;
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
public class RepositoryAttributesUpdatingTaskDescriptor extends TaskDescriptorSupport
{
    public static final String TYPE_ID = "repository.attributes-update";

    @Inject
    public RepositoryAttributesUpdatingTaskDescriptor() {
        super(TYPE_ID,
                RepositoryAttributesUpdatingTask.class,
                "Update size and the blob count",
                VISIBLE,
                EXPOSED,
                new RepositoryCombobox(
                        REPOSITORY_NAME_FIELD_ID,
                        "Repository",
                        "Select the repository to purge unused snapshot versions from",
                        FormField.MANDATORY
                ).includingAnyOfFacets(RepositoryAttributesFacet.class).includeAnEntryForAllRepositories()
        );
       // super(TYPE_ID, RepositoryAttributesUpdatingTask.class, "Update size and the blob count", NOT_VISIBLE, NOT_EXPOSED);
    }
}
