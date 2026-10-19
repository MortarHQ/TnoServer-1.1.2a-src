package com.foxablazeultimate.config;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CielConfig extends ManasConfig {
   public CielConfig.CielSettings Ciel = new CielConfig.CielSettings();

   public static CielConfig get() {
      return CielConfig.Holder.instance;
   }

   public static void setInstance(CielConfig config) {
      CielConfig.Holder.instance = config;
   }

   public String getFileName() {
      return "ciel";
   }

   public Path getConfigPath() {
      return Paths.get("config", "fox_ultimate", this.getFileName() + ".toml");
   }

   public void load() {
      Path path = this.getConfigPath();

      try {
         if (Files.exists(path) && Files.size(path) == 0L) {
            Files.delete(path);
            FoxAblazeUltimateMod.LOGGER.info("[FoxAblazeUltimate] Deleted empty config {}, rebuilding defaults.", path);
         }
      } catch (IOException exception) {
         FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] Config pre-check failed ({}): {}", path, exception.getMessage());
      }

      super.load();
   }

   public static CielConfig.CielSettings settings() {
      CielConfig config = get();
      return config != null && config.Ciel != null ? config.Ciel : CielConfig.Defaults.SETTINGS;
   }

   public static class CielSettings extends ManasSubConfig {
      @Comment("Require a permanent mastered Raphael before Ciel can awaken.")
      public boolean requireMasteredRaphael = true;
      @Comment("Require the player to be a True Demon Lord or True Hero before Ciel can awaken. The legacy field name is retained for existing configs.")
      public boolean requireTrueDemonLord = true;
      @Comment("Charybdis Cores that Raphael must successfully integrate before Ciel can awaken.")
      public int requiredCharybdisCores = 5;
      @Comment("Total item count that Raphael must successfully produce with Refine before Ciel can awaken.")
      public int requiredRefinedItemCount = 64;
      @Comment("Health percentage below which combat unlocks Manas naming.")
      public double awakeningHealthPercent = 25.0;
      @Comment("Ticks between server-side awakening and soul-bound restoration checks.")
      public int acquisitionCheckIntervalTicks = 10;
      @Comment("Ticks after dealing or receiving damage that still count as combat for awakening.")
      public int combatTimeoutTicks = 200;
      @Comment("Additional chant-speed attribute supplied by Ciel's Manas Core passive.")
      public double chantSpeedBonus = 10.0;
      @Comment("Additional analytical-appraisal attribute supplied by Ciel's Manas Core passive.")
      public double analysisLevelBonus = 10.0;
      @Comment("Additional skill-learning gain supplied by Ciel's Manas Core passive.")
      public double learningBonus = 30.0;
      @Comment("Additional skill-mastery gain supplied by Ciel's Manas Core passive.")
      public double masteryBonus = 30.0;
      @Comment("Future Vision attack-prediction success chance in percent.")
      public double futureVisionChance = 90.0;
      @Comment("Multiplier applied to skill cooldowns while Ciel is owned. Default 0.70 means 30% less.")
      public double cooldownMultiplier = 0.7;
      @Comment("Multiplier applied to skill magicule costs while Ciel is owned. Default 0.70 means 30% less.")
      public double magiculeCostMultiplier = 0.7;
      @Comment("Maximum-magicule percentage drained each second during Emergency Takeover.")
      public double takeoverDrainPercentPerSecond = 1.0;
      @Comment("Maximum hostile-target search range during Emergency Takeover.")
      public double takeoverTargetRange = 32.0;
      @Comment("Distance at which Emergency Takeover stops advancing and fights in place.")
      public double takeoverMeleeStopDistance = 2.75;
      @Comment("Distance at which Emergency Takeover starts sprinting toward its target.")
      public double takeoverSprintDistance = 8.0;
      @Comment("Make Emergency Takeover equip the highest-damage weapon found in the inventory before melee attacks.")
      public boolean takeoverUseStrongestWeapon = true;
      @Comment("Ticks between highest-damage weapon reevaluations while Emergency Takeover has a target.")
      public int takeoverWeaponRefreshTicks = 10;
      @Comment("Maximum horizontal aim change in degrees per tick during Emergency Takeover.")
      public double takeoverAimYawDegreesPerTick = 12.0;
      @Comment("Maximum vertical aim change in degrees per tick during Emergency Takeover.")
      public double takeoverAimPitchDegreesPerTick = 8.0;
      @Comment("Ticks between target searches when no valid target is currently locked.")
      public int takeoverTargetRefreshTicks = 5;
      @Comment("Enable bounded local obstacle avoidance during Emergency Takeover.")
      public boolean takeoverObstacleAvoidance = true;
      @Comment("Maximum distance in blocks checked by Emergency Takeover obstacle avoidance.")
      public double takeoverAvoidanceLookAhead = 4.0;
      @Comment("Preferred horizontal clearance around obstacles. Exact-width routes remain usable as a fallback.")
      public double takeoverAvoidanceClearance = 0.05;
      @Comment("Number of collision samples used for each obstacle-avoidance direction. Internally capped at 12.")
      public int takeoverAvoidanceProbeSamples = 5;
      @Comment("Ticks between full obstacle-avoidance direction recalculations.")
      public int takeoverAvoidanceRepathTicks = 4;
      @Comment("Minimum ticks to keep a selected detour direction before returning to a direct route.")
      public int takeoverAvoidanceHoldTicks = 12;
      @Comment("First lateral angle in degrees tested while avoiding an obstacle.")
      public double takeoverAvoidanceNarrowAngleDegrees = 35.0;
      @Comment("Second lateral angle in degrees tested while avoiding an obstacle.")
      public double takeoverAvoidanceWideAngleDegrees = 65.0;
      @Comment("Widest escape angle in degrees. Values above 90 allow backing away from a blocking entity.")
      public double takeoverAvoidanceEscapeAngleDegrees = 100.0;
      @Comment("Ticks without sufficient progress before Emergency Takeover reverses its detour side.")
      public int takeoverAvoidanceStuckCheckTicks = 10;
      @Comment("Minimum horizontal distance that counts as progress during a detour.")
      public double takeoverAvoidanceStuckMinimumDistance = 0.25;
      @Comment("Maximum height in blocks that Emergency Takeover may deliberately jump over.")
      public double takeoverMaximumAutoJumpHeight = 1.25;
      @Comment("Allow Emergency Takeover to place or mine blocks when trapped in a closed pit.")
      public boolean takeoverTerrainEscape = true;
      @Comment("Ticks without meaningful pursuit progress before terrain escape takes priority.")
      public int takeoverEscapeDetectionTicks = 30;
      @Comment("Horizontal route progress that resets Emergency Takeover's stuck detector.")
      public double takeoverEscapeMinimumProgress = 0.3;
      @Comment("Horizontal flood-search radius used to distinguish a closed pit from a local obstacle.")
      public int takeoverEscapeSearchRadius = 3;
      @Comment("Horizontal radius used to search for nearby stairs or other rising walkable paths.")
      public int takeoverEscapeRouteSearchRadius = 8;
      @Comment("Vertical blocks copied above and below the player for client-side takeover pathfinding.")
      public int takeoverClientPathVerticalRange = 6;
      @Comment("Maximum world cells copied per client tick while building an immutable pathfinding snapshot.")
      public int takeoverClientSnapshotBudget = 2048;
      @Comment("Maximum nodes expanded by one client-side takeover path calculation.")
      public int takeoverClientPathMaxVisitedNodes = 4096;
      @Comment("Maximum milliseconds spent in one background takeover path calculation.")
      public int takeoverClientPathTimeBudgetMillis = 10;
      @Comment("Normal ticks between client-side takeover route recalculations.")
      public int takeoverClientPathReplanTicks = 10;
      @Comment("Ticks between trapped-area probes while Emergency Takeover has no target.")
      public int takeoverClientIdleProbeTicks = 20;
      @Comment("Maximum downward drop included in an automatically followed route.")
      public double takeoverClientMaximumDropHeight = 3.0;
      @Comment("Maximum ticks spent following one discovered escape route before falling back to pillaring.")
      public int takeoverEscapeRouteTimeoutTicks = 80;
      @Comment("Maximum horizontal distance from the pillar center before Emergency Takeover may jump.")
      public double takeoverEscapePillarCenterTolerance = 0.17;
      @Comment("Maximum horizontal motion allowed before Emergency Takeover may begin a pillar jump.")
      public double takeoverEscapePillarMotionTolerance = 0.05;
      @Comment("Allow Emergency Takeover to consume tagged basic blocks and pillar upward.")
      public boolean takeoverEscapePlaceBlocks = true;
      @Comment("Allow Emergency Takeover to mine a staircase when no usable pillar block is available.")
      public boolean takeoverEscapeBreakBlocks = true;
      @Comment("Minimum ticks between automatic pillar block placement attempts.")
      public int takeoverEscapeActionIntervalTicks = 4;
      @Comment("Maximum ticks spent in one terrain-escape attempt.")
      public int takeoverEscapeMaximumTicks = 240;
      @Comment("Ticks before terrain escape may retry after an unsuccessful attempt.")
      public int takeoverEscapeRetryCooldownTicks = 100;
      @Comment("Hardest block that terrain escape may mine. Obsidian is 50 and is excluded by default.")
      public double takeoverEscapeMaximumBreakHardness = 10.0;
      @Comment("Multiplier applied to normal player/tool mining progress during terrain escape.")
      public double takeoverEscapeBreakSpeedMultiplier = 1.0;
      @Comment("Radius of the Emergency Takeover killing aura.")
      public double killAuraRadius = 4.5;
      @Comment("Ticks between killing-aura attacks.")
      public int killAuraIntervalTicks = 10;
      @Comment("Maximum targets struck by each killing-aura pulse.")
      public int killAuraMaxTargets = 4;
      @Comment("Ticks between attempts to use skills from the active Tensura preset.")
      public int autoCastIntervalTicks = 20;
      @Comment("Held duration supplied to automatically activated held skills.")
      public int autoCastHoldTicks = 10;
      @Comment("Maximum preset skills activated in one Emergency Takeover cycle.")
      public int autoCastMaxSkillsPerCycle = 1;
      @Comment("Require line of sight for Emergency Takeover targets.")
      public boolean autoCastRequireLineOfSight = true;
      @Comment("Allow Emergency Takeover to target hostile players.")
      public boolean autoCastTargetsPlayers = false;
   }

   private static final class Defaults {
      private static final CielConfig.CielSettings SETTINGS = new CielConfig.CielSettings();
   }

   private static final class Holder {
      private static CielConfig instance;
   }
}
