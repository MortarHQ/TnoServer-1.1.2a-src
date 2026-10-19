package com.foxablazeultimate.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

public final class AzathothConfig extends ManasConfig {
   public AzathothConfig.Settings Azathoth = new AzathothConfig.Settings();

   public static void setInstance(AzathothConfig value) {
      AzathothConfig.Holder.instance = value;
   }

   public static AzathothConfig.Settings settings() {
      return AzathothConfig.Holder.instance == null ? new AzathothConfig.Settings() : AzathothConfig.Holder.instance.Azathoth;
   }

   public String getFileName() {
      return "azathoth";
   }

   public Path getConfigPath() {
      return Path.of("config", "fox_ultimate", "azathoth.toml");
   }

   public void load() {
      Path path = this.getConfigPath();

      try {
         if (Files.isRegularFile(path)) {
            String contents = Files.readString(path);
            String repaired = contents.replaceFirst("\\A\\s*instance[ \\t]*=[ \\t]*(?:\\r?\\n|\\z)", "");
            if (!repaired.equals(contents)) {
               Path backup = path.resolveSibling(path.getFileName() + ".pre-singleton-fix.bak");

               for (int suffix = 1; Files.exists(backup); suffix++) {
                  backup = path.resolveSibling(path.getFileName() + ".pre-singleton-fix." + suffix + ".bak");
               }

               Files.copy(path, backup);
               Files.writeString(path, repaired);
               LoggerFactory.getLogger(AzathothConfig.class).info("Repaired interrupted Azathoth config save; original preserved at {}", backup);
            }
         }
      } catch (IOException exception) {
         throw new IllegalStateException("Could not recover Azathoth config: " + path, exception);
      }

      super.load();
      boolean changed = false;
      if (this.Azathoth.acquisitionRequiredSkills.contains("foxablazeultimate:beelzebub")) {
         this.Azathoth.acquisitionRequiredSkills = new ArrayList<>(this.Azathoth.acquisitionRequiredSkills);
         this.Azathoth.acquisitionRequiredSkills.replaceAll(id -> "foxablazeultimate:beelzebub".equals(id) ? "foxablazeultimate:beelzebuth" : id);
         changed = true;
      }

      if (this.Azathoth.acquisitionCostRevision < 2) {
         if (this.Azathoth.acquisitionCostRevision < 1 && Double.compare(this.Azathoth.acquisitionMagiculeCost, 5.0E7) == 0
            || Double.compare(this.Azathoth.acquisitionMagiculeCost, 5000000.0) == 0) {
            this.Azathoth.acquisitionMagiculeCost = 500000.0;
         }

         this.Azathoth.acquisitionCostRevision = 2;
         changed = true;
      }

      if (changed) {
         this.save();
      }
   }

   private static final class Holder {
      private static AzathothConfig instance;
   }

   public static final class Settings extends ManasSubConfig {
      @Comment("Enable automatic Azathoth acquisition when all requirements are met. Prerequisite skills are always retained.")
      public boolean acquisitionEnabled = true;
      @Comment(
         "Required permanent skill IDs. Every listed skill is required; temporary and sub-instance copies do not count. Invalid or empty lists disable acquisition."
      )
      public List<String> acquisitionRequiredSkills = new ArrayList<>(
         List.of("foxablazeultimate:raphael", "foxablazeultimate:beelzebuth", "foxablazeultimate:veldora", "foxablazeultimate:velgrynd")
      );
      @Comment("Require every prerequisite skill to be mastered. False requires permanent ownership only.")
      public boolean acquisitionRequiresMastery = true;
      @Comment(
         "Current magicules consumed once on successful acquisition. Does not reduce permanent maximum magicules and is not reduced by skill-use discounts."
      )
      public double acquisitionMagiculeCost = 500000.0;
      @Comment("Internal migration version for acquisition-cost defaults (50M, then 5M, now 500K). Keep this value when editing the cost.")
      public int acquisitionCostRevision = 0;
      @Comment("Ticks between automatic acquisition checks. 20 ticks = 1 second; minimum 1 tick.")
      public int acquisitionCheckIntervalTicks = 20;
      @Comment("Initial mastery of the acquired Azathoth skill, clamped to its maximum mastery.")
      public int acquisitionGrantMastery = 0;
      @Comment("Soul Gluttony magicule cost per attempt.")
      public double soulMagiculeCost = 5000.0;
      @Comment("Soul Gluttony cooldown in seconds.")
      public int soulCooldownSeconds = 10;
      @Comment("Soul Gluttony aiming distance in blocks.")
      public double soulRange = 20.0;
      @Comment("Soul Gluttony area submode radius in blocks. Uses the same cost, hold time, cooldown and strong-target health requirement as individual mode.")
      public double soulAreaRadius = 40.0;
      @Comment("Ticks continuously aiming at the same target before Soul Gluttony attempts to consume it.")
      public int soulHoldTicks = 20;
      @Comment("Targets above this maximum health must first be weakened.")
      public double soulStrongTargetHealth = 100.0;
      @Comment("Maximum remaining health fraction for strong targets. 0.1 = 10%.")
      public double soulStrongTargetHealthFraction = 0.1;
      @Comment("Fraction of Tensura's eligible EP reward converted to permanent EP. Respects no-plunder and summon exclusions.")
      public double soulEpGainFraction = 0.1;
      @Comment("Fraction of the victim's current magicules recovered on successful consumption.")
      public double soulMagiculeGainFraction = 1.0;
      @Comment("Recoverable magicules per point of the victim's maximum health.")
      public double soulBodyMagiculesPerHealth = 10.0;
      @Comment("Barrier and resistance penetration after mastery. Tensura Anti-Skill requires at least 3.")
      public double masteredPenetration = 3.0;
      @Comment("Void Collapse magicule cost per charge, paid once.")
      public double voidMagiculeCost = 50000.0;
      @Comment(
         "Maximum Void Collapse cooldown in seconds at full charge. Shorter charges scale proportionally, rounded up to whole seconds. Begins when energy is stored."
      )
      public int voidCooldownSeconds = 1200;
      @Comment("Maximum charge time in seconds.")
      public int voidMaxChargeSeconds = 60;
      @Comment("Elemental damage at full charge. Partial charges scale linearly.")
      public double voidFullChargeDamage = 1000.0;
      @Comment("Additional fraction of main-hand attack damage when discharging a charged weapon.")
      public double voidWeaponDamageFraction = 0.1;
      @Comment("Ordinary and mastered independent storage page counts; 27 slots per page.")
      public int storagePages = 20;
      public int storageMasteredPages = 30;
      public int storageMaxStackSize = 999;
      @Comment("Spatial sight teleport range in blocks.")
      public double teleportRange = 64.0;
      public double teleportMagiculeCost = 4000.0;
      public int teleportCooldownSeconds = 30;
      @Comment("Warp Portal cost per block, using Tensura's native portal menu and travel mechanics.")
      public double portalMagiculeCostPerBlock = 50.0;
      public int portalChargeTicks = 20;
      public int portalCooldownSeconds = 30;
      @Comment("Dragon Liberation magicule cost and cooldown (seconds).")
      public double dragonMagiculeCost = 50000.0;
      public int dragonCooldownSeconds = 60;
      @Comment("Maximum simultaneously active dragon avatars per owner.")
      public int maxDragonAvatars = 3;
      public int dragonDurationSeconds = 300;
      public double dragonAttackRange = 24.0;
      public double dragonElementalDamage = 80.0;
      public int dragonAttackIntervalTicks = 40;
      public double scorchingAttackSpeedBonus = 1.0;
      public double scorchingDamageMultiplier = 1.5;
      public double stormMovementBonus = 0.5;
      public double stormAreaRadius = 5.0;
      public double frostArmorBonus = 30.0;
      public double frostDamageReduction = 0.5;
      public int frostSlowSeconds = 5;
      public int frostSlowAmplifier = 2;
      @Comment("Multidimensional Barrier damage reduction fractions. Uses the highest applicable reduction, without multiplying overlapping categories.")
      public double physicalDamageReduction = 0.7;
      public double elementalDamageReduction = 0.7;
      public double projectileDamageReduction = 0.8;
      public double ultimateDamageReduction = 0.8;
      @Comment("With Azathoth's passive enabled, a left-click attack instantly destroys a Tensura barrier entity.")
      public boolean breakBarriersOnAttack = true;
   }
}
