package com.foxablazeultimate.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.foxablazeultimate.FoxAblazeUltimateMod;
import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HellSniperConfig extends ManasConfig {
   public HellSniperConfig.HellSniperSettings HellSniper = new HellSniperConfig.HellSniperSettings();

   public static HellSniperConfig get() {
      return HellSniperConfig.Holder.instance;
   }

   public static void setInstance(HellSniperConfig config) {
      HellSniperConfig.Holder.instance = config;
   }

   public static HellSniperConfig.HellSniperSettings settings() {
      HellSniperConfig config = get();
      return config == null ? new HellSniperConfig.HellSniperSettings() : config.HellSniper;
   }

   public String getFileName() {
      return "hell_sniper";
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
      CommentedConfig section = (CommentedConfig)this.getConfig().get("HellSniper");
      boolean placementMigrated = section != null && section.remove("projectileLaunchOffset") != null;
      if (section != null && section.remove("projectileLaunchGap") != null) {
         placementMigrated = true;
      }

      if (placementMigrated) {
         this.getConfig().save();
      }
   }

   public static class HellSniperSettings extends ManasSubConfig {
      @Comment(
         "Minimum current magicule and permanent maximum-magicule capacity required to awaken Hell Sniper. The acquisition consumes the configured permanent capacity cost afterward."
      )
      public double acquisitionRequiredMaximumMagicule = 3000000.0;
      @Comment("Permanent maximum-magicule capacity consumed when Hell Sniper awakens. The default reduces a 3,000,000 capacity to 2,000,000.")
      public double acquisitionMaximumMagiculeCost = 1000000.0;
      @Comment("Ticks between automatic Hell Sniper acquisition checks.")
      public int acquisitionCheckIntervalTicks = 20;
      @Comment("Mastery granted when Hell Sniper is acquired. Sniper is retained and is not consumed.")
      public int acquisitionGrantMastery = 0;
      @Comment("Maximum distance in blocks at which Sniping can acquire and retain a target.")
      public double targetingRange = 100.0;
      @Comment("Magicules consumed when a valid target is locked and the cast begins.")
      public double magiculeCost = 5000.0;
      @Comment("Magicules consumed once when Infinite Sniping starts. Repeated shots do not consume additional magicules.")
      public double infiniteMagiculeCost = 50000.0;
      @Comment(
         "Cooldown in Tensura skill-system seconds, counted from the moment the cast starts. Earlier magic circles keep running independently, so this alone decides how often Sniping can be re-fired."
      )
      public int cooldownSeconds = 3;
      @Comment(
         "How many real seconds the Sniping activation button must be held before the needle can be released. The progress uses Tensura's native casting display."
      )
      public int snipingChargeSeconds = 2;
      @Comment(
         "How many real seconds the Infinite Sniping activation button must be held before the formation can be released. This mode is available after mastery."
      )
      public int infiniteChargeSeconds = 10;
      @Comment(
         "Cooldown for Infinite Sniping in Tensura skill-system seconds. 600 seconds is ten minutes; this value is intentionally stored in seconds, not ticks."
      )
      public int infiniteCooldownSeconds = 600;
      @Comment("Number of fixed magic circles summoned by Infinite Sniping. Values are clamped to 3-32 at runtime.")
      public int infiniteCircleCount = 8;
      @Comment(
         "Requested radius in blocks of the Infinite Sniping circle formation. The runtime automatically enlarges this radius when necessary to prevent the rendered magic circles from overlapping."
      )
      public double infiniteRingRadius = 7.0;
      @Comment("Minimum empty distance in blocks between adjacent Infinite Sniping circles.")
      public double infiniteMinimumCircleSpacing = 1.0;
      @Comment("Radius in blocks around the fixed formation centre used to acquire targets.")
      public double infiniteTargetingRange = 100.0;
      @Comment("Duration in seconds for which Infinite Sniping attacks after its firing warm-up has completed.")
      public int infiniteDurationSeconds = 60;
      @Comment("Ticks between Infinite Sniping volleys. One volley fires at most one beam from each circle and rotates through every eligible target.")
      public int infiniteShotIntervalTicks = 20;
      @Comment(
         "Ticks between the starts of consecutive Infinite Sniping circle appearance animations. A value of 2 lets the next circle begin just before the previous one finishes its 2.5-tick supplied appearance animation."
      )
      public int infiniteAppearanceStaggerTicks = 2;
      @Comment("Direct magic-bullet damage dealt before the impact explosion.")
      public double damage = 500.0;
      @Comment("Direct magic-bullet damage dealt after Hell Sniper is mastered.")
      public double damageMastered = 800.0;
      @Comment("Vanilla explosion power at the impact point. TNT uses power 4.")
      public float explosionPower = 4.0F;
      @Comment("Whether the impact explosion destroys terrain.")
      public boolean explosionDestroysTerrain = true;
      @Comment("Speed in blocks per tick of the homing needle released from the magic circle.")
      public double projectileSpeed = 2.0;
      @Comment("Exact distance in blocks from the caster's eye position to the centre of the fixed Sniping magic circle.")
      public double projectileLaunchDistance = 1.0;
      @Comment("Duration in ticks of the smooth magic-circle appearance phase.")
      public int appearanceDurationTicks = 3;
      @Comment("Duration in ticks of the rotating middle-circle aiming phase.")
      public int aimingDurationTicks = 80;
      @Comment(
         "Ticks spent in the firing animation before the ray projectile is released. The animation's two secondary circles finish expanding around tick 13, so values above that let the firing pose play out before the shot leaves."
      )
      public int firingWarmupTicks = 30;
      @Comment(
         "Maximum vertical adjustment, in degrees, used while a magic circle tracks a target. The default limits the smooth adjustment to 35 degrees up or down."
      )
      public double verticalAimLimitDegrees = 35.0;
      @Comment("Maximum vertical aim change per server tick, in degrees. Smaller values make the circle turn more gradually.")
      public double verticalAimStepDegrees = 4.0;
      @Comment(
         "Half-angle, in degrees, of the cone an Infinite Sniping circle may fire into. Each formation circle keeps the outward bearing it spawned with, so this stops it firing backwards through its own face. Together with the vertical limit it defines what a circle can reach; 180 restores firing in every direction."
      )
      public double infiniteFiringConeDegrees = 60.0;
      @Comment("Ticks the firing pose may remain after impact when the target survives.")
      public int postFireDurationTicks = 6;
   }

   private static final class Holder {
      private static HellSniperConfig instance;
   }
}
