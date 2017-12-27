package org.sonatype.nexus.repository.maven.tasks;

import com.google.common.base.Strings;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.repository.Type;
import org.sonatype.nexus.repository.maven.PurgeUnusedReleasesFacet;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.scheduling.Cancelable;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class PurgeMavenUnusedReleasesTask  extends RepositoryTaskSupport
        implements Cancelable
{

    public static final String NUMBER_RELEASES_TO_KEEP = "numberOfReleasesToKeep";

    public static final String GROUP_ID = "groupId";

    public static final String ARTIFACT_ID = "artifactId";

    public static final String PURGE_UNUSED_MAVEN_RELEASES_MESSAGE = "Purge unused Maven releases versions of %s.%s of this repository %s";
    public static final String OPTION_FOR_PURGE_ID = "optionForPurge";

    private final Type hostedType;

    private final Format maven2Format;

    @Inject
    public PurgeMavenUnusedReleasesTask(
                                         @Named(HostedType.NAME) final Type hostedType,
                                         @Named(Maven2Format.NAME) final Format maven2Format)
    {
        this.hostedType = checkNotNull(hostedType);
        this.maven2Format = checkNotNull(maven2Format);
    }

    @Override
    protected void execute(final Repository repository) {
        String groupId = getConfiguration().getString(GROUP_ID);
        String artifactId = getConfiguration().getString(ARTIFACT_ID);
       // String option = !Strings.isNullOrEmpty(getConfiguration().getString(OPTION_FOR_PURGE_ID)) ? getConfiguration().getString(OPTION_FOR_PURGE_ID) : "version";
        String option = "version";
        log.info("Option used {} ", option);
        int numberOfReleasesToKeep = getConfiguration().getInteger(NUMBER_RELEASES_TO_KEEP, 1);
        repository.facet(PurgeUnusedReleasesFacet.class).purgeUnusedReleases(groupId,
                artifactId, option, numberOfReleasesToKeep);

    }

    @Override
    protected boolean appliesTo(final Repository repository) {
        return maven2Format.equals(repository.getFormat())
                && hostedType.equals(repository.getType());
    }

    @Override
    public String getMessage() {
        String groupId = getConfiguration().getString(GROUP_ID);
        String artifactId = getConfiguration().getString(ARTIFACT_ID);
        return String.format(PURGE_UNUSED_MAVEN_RELEASES_MESSAGE,
                groupId,
                artifactId,
                getRepositoryField()) ;
    }
}
