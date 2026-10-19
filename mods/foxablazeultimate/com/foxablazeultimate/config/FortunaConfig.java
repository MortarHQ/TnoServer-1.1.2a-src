package com.foxablazeultimate.config;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FortunaConfig extends ManasConfig {
   public FortunaConfig.FortunaSettings Fortuna = new FortunaConfig.FortunaSettings();

   public static FortunaConfig get() {
      return FortunaConfig.Holder.instance;
   }

   public static void setInstance(FortunaConfig config) {
      FortunaConfig.Holder.instance = config;
   }

   public String getFileName() {
      return "fortuna";
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

   public static class FortunaSettings extends ManasSubConfig {
      @Comment("Current magicules consumed when Mathematician and Usurper evolve into Fortuna.")
      public double acquisitionMagiculeCost = 500000.0;
      @Comment("Ticks between automatic Fortuna evolution checks.")
      public int acquisitionCheckIntervalTicks = 20;
      @Comment("Mastery granted when Fortuna is acquired.")
      public int acquisitionGrantMastery = 0;
      @Comment("Melee and projectile dodge chance supplied by Future Prediction. Values use percent units.")
      public double futurePredictionDodgeChance = 70.0;
      @Comment("Movement speed added by Fortuna's Thought Acceleration. Vanilla Thought Acceleration mastered is 0.02.")
      public double thoughtMovementSpeed = 0.04;
      @Comment("Attack speed added by Fortuna's Thought Acceleration. Vanilla Thought Acceleration mastered is 0.4.")
      public double thoughtAttackSpeed = 0.8;
      @Comment("Chant speed added by Fortuna's Thought Acceleration. Vanilla Thought Acceleration is 2.0.")
      public double thoughtChantSpeed = 4.0;
      @Comment("Dodge invulnerability added by Fortuna's Thought Acceleration.")
      public double thoughtDodgeInvulnerability = 2.0;
      @Comment("Presence Sense level supplied by Universal Perception.")
      public double universalPresenceSense = 3.0;
      @Comment("Presence and sound sensing radius supplied by Universal Perception.")
      public double universalPresenceRadius = 20.0;
      @Comment("Auditory-sense marker strength used by Tensura's Universal Perception renderer.")
      public double universalSoundSense = 0.1;
      @Comment("Heat sensing radius supplied by Universal Perception.")
      public double universalHeatRadius = 30.0;
      @Comment("Magicules consumed once per second while Fortuna's Universal Perception passive is enabled.")
      public double universalPerceptionMagiculeCostPerSecond = 25.0;
      @Comment("Maximum Spacetime Operation range in blocks.")
      public double teleportRange = 40.0;
      @Comment("Distance behind a targeted entity used by Spacetime Operation.")
      public double teleportBehindDistance = 1.5;
      @Comment("Spacetime Operation cooldown in Tensura skill-system seconds.")
      public int teleportCooldownSeconds = 5;
      @Comment("Magicules consumed once per second while Multidimensional Barrier is active.")
      public double barrierMagiculeCostPerSecond = 2000.0;
      @Comment("Multidimensional Barrier cooldown after it closes, in Tensura skill-system seconds.")
      public int barrierCooldownSeconds = 60;
      @Comment("Initial reduction against the first hit of each damage type. 0.10 means 10 percent.")
      public double barrierInitialReduction = 0.1;
      @Comment("Extra reduction learned at each adaptation step. 0.01 means 1 percent.")
      public double barrierReductionPerStep = 0.01;
      @Comment("Hits of the same damage type required for each adaptation step after its first hit.")
      public int barrierHitsPerStep = 3;
      @Comment("Maximum reduction learned for one damage type. 0.80 means 80 percent.")
      public double barrierMaximumReduction = 0.8;
      @Comment("Ticks between passive mastery gains while Fortuna is toggled on.")
      public int passiveMasteryIntervalTicks = 100;
      @Comment("Multiplier applied to spatial damage while Fortuna is toggled. Spatial Domination takes precedence.")
      public double spatialDamageMultiplier = 6.0;
      @Comment("Magicules consumed by each equipped Fortuna Warp Shot projectile.")
      public double warpShotMagiculeCost = 30.0;
      @Comment("Warp Shot attribute strength while Fortuna is equipped. Set to 0 to disable the passive.")
      public double warpShotStrength = 0.5;
   }

   private static final class Holder {
      private static FortunaConfig instance;
   }
}
