/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.upgrade.internal;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.VersionComparator;
import org.sonatype.nexus.common.upgrade.Checkpoint;
import org.sonatype.nexus.common.upgrade.Checkpoints;
import org.sonatype.nexus.common.upgrade.Upgrade;
import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.upgrade.UpgradeService;
import org.sonatype.nexus.upgrade.plan.DependencyResolver;
import org.sonatype.nexus.upgrade.plan.DependencySource;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Manages {@link Upgrade}s and {@link Checkpoint}s for the {@link UpgradeService}.
 * 
 * @since 3.1
 */
public class UpgradeManager
    extends ComponentSupport
{
  private final List<Checkpoint> managedCheckpoints;

  private final List<Upgrade> managedUpgrades;

  private final boolean warnOnMissingDependencies;

  @Inject
  public UpgradeManager(final List<Checkpoint> managedCheckpoints, final List<Upgrade> managedUpgrades,
                        @Named("${nexus.upgrade.warnOnMissingDependencies:-false}")
                        final boolean warnOnMissingDependencies)
  {
    this.managedCheckpoints = checkNotNull(managedCheckpoints);
    this.managedUpgrades = verifyUniquenessOf(checkNotNull(managedUpgrades));
    this.warnOnMissingDependencies = warnOnMissingDependencies;
  }

  /**
   * Enforce the assertion that that every tuple of (model, from version, to version) is unique amongst all the upgrades
   *
   * @return the same list as was passed in
   */
  private static List<Upgrade> verifyUniquenessOf(final List<Upgrade> managedUpgrades) {
    Map<Upgrades, List<Upgrade>> duplicates = managedUpgrades.stream()
        .collect(groupingBy(UpgradeManager::upgrades)).entrySet().stream()
        .filter(e -> e.getValue().size() > 1)
        .collect(toMap(Entry::getKey, Entry::getValue));

    if (!duplicates.isEmpty()) {
      throw new IllegalStateException("Duplicate upgrade steps found! " +
          duplicates.entrySet().stream()
              .map(e -> String.format("\"Upgrade of model: %s from: %s to: %s duplicated by classes: %s\"",
                  e.getKey().model(), e.getKey().from(), e.getKey().to(),
                  e.getValue().stream().map(u -> u.getClass().getCanonicalName()).collect(joining(","))))
              .collect(joining(","))
      );
    }

    return managedUpgrades;
  }

  public Set<String> getLocalModels() {
    return getModels(true);
  }

  public Set<String> getClusteredModels() {
    return getModels(false);
  }

  private Set<String> getModels(boolean local) {
    return managedCheckpoints.stream()
        .map(UpgradeManager::checkpoints)
        .filter(checkpoints -> checkpoints.local() == local)
        .map(checkpoints -> checkpoints.model())
        .collect(toCollection(HashSet::new));
  }

  /**
   * Returns ordered list of upgrades that should be applied to the current installation.
   * 
   * @param modelVersions The models and versions currently installed
   * @return ordered list of upgrades that should be applied
   */
  public List<Upgrade> plan(final Map<String, String> modelVersions) {
    return order(modelVersions, managedUpgrades.stream().filter(upgrade -> applies(modelVersions, upgrade)));
  }

  /**
   * Returns list of checkpoints that should be taken before applying the scheduled upgrades.
   * 
   * @param upgrades The scheduled upgrades
   * @return list of checkpoints that should be taken
   */
  public List<Checkpoint> prepare(final List<Upgrade> upgrades) {
    Set<String> models = upgrades.stream()
        .map(upgrade -> upgrades(upgrade).model())
        .collect(toCollection(HashSet::new));

    List<Checkpoint> checkpoints = managedCheckpoints.stream()
        .filter(checkpoint -> models.remove(checkpoints(checkpoint).model()))
        .collect(toList());

    checkArgument(models.isEmpty(), "Checkpoint(s) missing for %s", models);

    return checkpoints;
  }

  /**
   * Orders the given upgrades so any dependent upgrades appear earlier on in the sequence.
   */
  private List<Upgrade> order(final Map<String, String> modelVersions, final Stream<Upgrade> upgrades) {
    DependencyResolver<DependencySource<UpgradePoint>> resolver = new DependencyResolver<>();
    resolver.setWarnOnMissingDependencies(warnOnMissingDependencies);

    resolver.add(ImmutableList.of(new InitialStep(modelVersions)));
    resolver.add(upgrades.map(UpgradeStep::new).sorted(UpgradeManager::byVersion).collect(toList()));

    return resolver.resolve()
        .getOrdered().stream()
        .map(UpgradeStep::unwrap)
        .filter(Objects::nonNull)
        .collect(toList());
  }

  /**
   * @return {@code true} if the upgrade applies on top of the current installation
   */
  private static boolean applies(final Map<String, String> modelVersions, final Upgrade upgrade) {
    Upgrades upgrades = upgrades(upgrade);

    String current = modelVersions.getOrDefault(upgrades.model(), "1.0");

    // sanity check the upgrade increases the version
    checkArgument(VersionComparator.INSTANCE.compare(upgrades.to(), upgrades.from()) > 0,
        "%s upgrade version '%s' is not after '%s'",
        upgrade.getClass(), upgrades.to(), upgrades.from());

    // does the upgrade go past the current version?
    return VersionComparator.INSTANCE.compare(upgrades.to(), current) > 0;
  }

  /**
   * @return Metadata about this {@link Checkpoint}
   */
  static Checkpoints checkpoints(final Checkpoint checkpoint) {
    Checkpoints checkpoints = checkpoint.getClass().getAnnotation(Checkpoints.class);
    checkArgument(checkpoints != null, "%s is not annotated with @Checkpoints", checkpoint.getClass());
    return checkpoints;
  }

  /**
   * @return Metadata about this {@link Upgrade}
   */
  static Upgrades upgrades(final Upgrade upgrade) {
    Upgrades upgrades = upgrade.getClass().getAnnotation(Upgrades.class);
    checkArgument(upgrades != null, "%s is not annotated with @Upgrades", upgrade.getClass());
    return upgrades;
  }

  /**
   * Partial ordering for independent/unrelated {@link UpgradeStep}s (earliest first).
   */
  static int byVersion(final UpgradeStep lhs, final UpgradeStep rhs) {
    return VersionComparator.INSTANCE.compare(lhs.getVersion(), rhs.getVersion());
  }

  /**
   * Partial ordering for independent/unrelated {@link Upgrades} (earliest first).
   */
  static int byVersion(final Upgrades lhs, final Upgrades rhs) {
    return VersionComparator.INSTANCE.compare(lhs.to(), rhs.to());
  }

  /**
   * Returns a map of model names to the most recent known version of
   * that model.
   *
   * @since 3.4
   */
  public Map<String, String> latestKnownModelVersions() {
    return managedUpgrades.stream().map(upgrade -> upgrade.getClass().getAnnotation(Upgrades.class))
        .collect(
            groupingBy(
                Upgrades::model,
                collectingAndThen(
                    maxBy(UpgradeManager::byVersion),
                    step -> step.get().to())));
  }
}
