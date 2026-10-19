package com.foxablazeultimate.config;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SatanaelConfig extends ManasConfig {
   public SatanaelConfig.SatanaelSettings Satanael = new SatanaelConfig.SatanaelSettings();

   public static SatanaelConfig get() {
      return SatanaelConfig.Holder.instance;
   }

   public static void setInstance(SatanaelConfig config) {
      SatanaelConfig.Holder.instance = config;
   }

   public String getFileName() {
      return "satanael";
   }

   public Path getConfigPath() {
      return Paths.get("config", "fox_ultimate", this.getFileName() + ".toml");
   }

   public void load() {
      Path path = this.getConfigPath();
      boolean migrateLegacyWrathGain = false;

      try {
         if (Files.exists(path) && Files.size(path) == 0L) {
            Files.delete(path);
            FoxAblazeUltimateMod.LOGGER.info("[FoxAblazeUltimate] Deleted empty config {}, rebuilding defaults.", path);
         } else if (Files.exists(path)) {
            String compactConfig = Files.readString(path).replaceAll("\\s+", "");
            migrateLegacyWrathGain = compactConfig.contains("wrathGainPerDominatedEnemyPerSecond=1.0");
         }
      } catch (IOException exception) {
         FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] Config pre-check failed ({}): {}", path, exception.getMessage());
      }

      super.load();
      if (migrateLegacyWrathGain && Math.abs(this.Satanael.wrathGainPerDominatedEnemyPerSecond - 1.0) < 1.0E-7) {
         this.Satanael.wrathGainPerDominatedEnemyPerSecond = 0.1;
         this.save();
      }
   }

   private static final class Holder {
      static SatanaelConfig instance;
   }

   public static class SatanaelSettings extends ManasSubConfig {
      @Comment("Soul points required to evolve mastered Wrath into Satanael.")
      public int requiredSoulPoints = 25000;
      @Comment("Ticks between automatic Satanael acquisition checks.")
      public int acquisitionCheckIntervalTicks = 20;
      @Comment("Mastery granted when Satanael is acquired.")
      public int acquisitionGrantMastery = 0;
      @Comment("Chance for a Wrath Crystal to drop when the victim or killer has a Rampage effect.")
      public double wrathCrystalDropChance = 0.01;
      @Comment("Ticks between Magicule Breeder Reactor pulses. 50 ticks = 2.5 seconds.")
      public int breederPulseTicks = 50;
      @Comment("Fraction of maximum magicule restored by each reactor pulse.")
      public double breederMagiculeFraction = 0.02;
      @Comment("Chance to gain one Rampage level at or below natural maximum magicule.")
      public double breederRampageChance = 0.06;
      @Comment("Chance to gain one Rampage level above natural maximum magicule.")
      public double breederAboveMaxRampageChance = 0.1;
      @Comment("Extra reactor output multiplier contributed by each dominated enemy.")
      public double breederEfficiencyPerDominatedEnemy = 0.1;
      @Comment("Base Satanael Rampage duration in ticks. 900 ticks = 45 seconds.")
      public int rampageDurationTicks = 900;
      @Comment("Maximum Rampage duration after combat extensions. 1200 ticks = 60 seconds.")
      public int rampageMaximumDurationTicks = 1200;
      @Comment("Maximum Satanael Rampage level.")
      public int rampageMaximumLevel = 10;
      @Comment("Rampage duration added when attacking or being attacked.")
      public int rampageCombatExtensionTicks = 100;
      @Comment("Health ratio below which Rampage gain chance is multiplied by the low-health multiplier.")
      public double lowHealthThreshold = 0.5;
      @Comment("Health ratio below which Rampage gain chance is multiplied by the critical-health multiplier.")
      public double criticalHealthThreshold = 0.25;
      @Comment("Rampage gain chance multiplier below the low-health threshold.")
      public double lowHealthChanceMultiplier = 1.5;
      @Comment("Rampage gain chance multiplier below the critical-health threshold.")
      public double criticalHealthChanceMultiplier = 2.0;
      @Comment("Ticks without attacking or being attacked before combat ends. 200 ticks = 10 seconds.")
      public int combatTimeoutTicks = 200;
      @Comment("Fraction of maximum magicule restored each second while the player is in combat after being attacked.")
      public double combatMagiculeRecoveryFraction = 0.01;
      @Comment("Ticks between losing Rampage levels after leaving combat.")
      public int rampageDecayIntervalTicks = 100;
      @Comment("Armor added per Satanael Rampage level.")
      public double rampageArmorPerLevel = 20.0;
      @Comment("Attack damage added per Satanael Rampage level.")
      public double rampageAttackPerLevel = 100.0;
      @Comment("Attack speed added per Satanael Rampage level.")
      public double rampageAttackSpeedPerLevel = 0.6;
      @Comment("Movement speed added per Satanael Rampage level.")
      public double rampageMovementSpeedPerLevel = 0.2;
      @Comment("Swimming speed multiplier added per Satanael Rampage level.")
      public double rampageSwimSpeedPerLevel = 2.0;
      @Comment("Knockback resistance added per Satanael Rampage level.")
      public double rampageKnockbackResistancePerLevel = 0.5;
      @Comment("Magicule consumed once per second while holding Wrath Domination.")
      public double dominationMagiculeCost = 100.0;
      @Comment("Wrath Domination radius in blocks.")
      public double dominationRadius = 30.0;
      @Comment("Rampage and domination duration. 800 ticks = 40 seconds.")
      public int dominationDurationTicks = 800;
      @Comment("Hold ticks required for Wrath Domination to add one more Rampage level.")
      public int dominationLevelIntervalTicks = 200;
      @Comment("Ticks between Wrath Domination area scans and effect refreshes.")
      public int dominationApplyIntervalTicks = 10;
      @Comment("Ticks between domination AI target refreshes.")
      public int dominationRetargetIntervalTicks = 10;
      @Comment("Ticks between forced melee attacks when a mob's own AI rejects its dominated target.")
      public int dominationForcedAttackIntervalTicks = 20;
      @Comment("Navigation speed used to force dominated mobs toward each other.")
      public double dominationForcedMovementSpeed = 1.3;
      @Comment("Ticks between Wrath Domination sounds while holding the mode.")
      public int dominationSoundIntervalTicks = 20;
      @Comment("Volume of the Wrath Domination sound.")
      public double dominationSoundVolume = 10.0;
      @Comment("Pitch of the Wrath Domination sound.")
      public double dominationSoundPitch = 1.0;
      @Comment("Wrath percentage absorbed per dominated enemy each second.")
      public double wrathGainPerDominatedEnemyPerSecond = 0.1;
      @Comment("Wrath percentage lost each second while outside combat with no dominated enemies.")
      public double wrathDecayPerSecond = 1.0;
      @Comment("Wrath percentage required to activate Stampede.")
      public double stampedeRequiredWrath = 100.0;
      @Comment("Stampede duration. 600 ticks = 30 seconds.")
      public int stampedeDurationTicks = 600;
      @Comment("Stampede cooldown in Tensura skill-system seconds. Do not convert this value to ticks.")
      public int stampedeCooldownSeconds = 360;
      @Comment("Ticks between full-power reactor pulses during Stampede.")
      public int stampedeBreederPulseTicks = 10;
      @Comment("Base reactor output multiplier during Stampede.")
      public double stampedeBreederBaseMultiplier = 5.0;
      @Comment("Additional reactor output multiplier gained each second during Stampede.")
      public double stampedeBreederGrowthPerSecond = 0.1;
      @Comment("Attack damage gained each second during Stampede.")
      public double stampedeAttackPerSecond = 20.0;
      @Comment("Armor gained each second during Stampede.")
      public double stampedeArmorPerSecond = 5.0;
      @Comment("Movement speed multiplier gained each second during Stampede.")
      public double stampedeSpeedPerSecond = 0.02;
      @Comment("Magicule gain attribute added each second during Stampede.")
      public double stampedeMagiculeOutputPerSecond = 0.05;
      @Comment("Ticks between Stampede aura particle emissions.")
      public int stampedeParticleIntervalTicks = 2;
      @Comment("Particle count emitted during Stampede.")
      public int stampedeParticleCount = 5;
      @Comment("Stampede activation sound volume.")
      public double stampedeSoundVolume = 3.0;
      @Comment("Stampede activation sound pitch.")
      public double stampedeSoundPitch = 0.65;
      @Comment("Forced Weakness duration after Stampede. 2400 ticks = 2 minutes.")
      public int stampedeWeaknessDurationTicks = 2400;
      @Comment("Zero-based Satanael Weakness amplifier. 1 means Weakness II.")
      public int stampedeWeaknessAmplifier = 1;
      @Comment("Attack damage penalty enforced during post-Stampede weakness.")
      public double stampedeWeaknessAttackPenalty = -8.0;
      @Comment("Ticks between mastery point gains while holding Wrath Domination.")
      public int masteryPointIntervalTicks = 100;
   }
}
