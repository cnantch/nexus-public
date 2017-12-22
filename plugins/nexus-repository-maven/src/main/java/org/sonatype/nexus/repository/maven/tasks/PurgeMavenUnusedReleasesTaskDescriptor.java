package org.sonatype.nexus.repository.maven.tasks;

import org.sonatype.nexus.formfields.FormField;
import org.sonatype.nexus.formfields.NumberTextFormField;
import org.sonatype.nexus.formfields.RepositoryCombobox;
import org.sonatype.nexus.formfields.StringTextFormField;
import org.sonatype.nexus.repository.maven.PurgeUnusedReleasesFacet;
import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

import javax.inject.Named;
import javax.inject.Singleton;

import static org.sonatype.nexus.repository.RepositoryTaskSupport.REPOSITORY_NAME_FIELD_ID;
import static org.sonatype.nexus.repository.maven.tasks.PurgeMavenUnusedReleasesTask.*;

/**
 * Task descriptor for {@link PurgeMavenUnusedReleasesTask}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class PurgeMavenUnusedReleasesTaskDescriptor  extends TaskDescriptorSupport {

    public static final String TASK_NAME = "Purge unused Maven releases";

    public static final String TYPE_ID = "repository.maven.purge-unused-releases";

    public static final Number LAST_USED_INIT_VALUE = 1;

    public static final Number LAST_USED_MIN_VALUE = 1;

    public PurgeMavenUnusedReleasesTaskDescriptor() {
        super(TYPE_ID,
                PurgeMavenUnusedReleasesTask.class,
                TASK_NAME,
                VISIBLE,
                EXPOSED,
                new RepositoryCombobox(
                        REPOSITORY_NAME_FIELD_ID,
                        "Repository",
                        "Select the repository to purge unused releases versions from",
                        FormField.MANDATORY
                ).includingAnyOfFacets(PurgeUnusedReleasesFacet.class).includeAnEntryForAllRepositories(),
                new StringTextFormField(GROUP_ID, "Group Id of the release",
                        "Enter the groupId of the release you want to purge",
                        true),
                new StringTextFormField(ARTIFACT_ID, "Artifact Id of the release",
                        "Enter the artifactId of the release you want to purge",
                        true),
                new NumberTextFormField(
                        NUMBER_RELEASES_TO_KEEP,
                        "Last used in days",
                        "Number of releases to keep",
                        FormField.MANDATORY
                ).withInitialValue(LAST_USED_INIT_VALUE).withMinimumValue(LAST_USED_MIN_VALUE)
        );
    }
}
