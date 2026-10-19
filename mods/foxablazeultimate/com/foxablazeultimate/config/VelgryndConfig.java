package com.foxablazeultimate.config;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VelgryndConfig extends ManasConfig {
   public VelgryndConfig.VelgryndSettings Velgrynd = new VelgryndConfig.VelgryndSettings();

   public static VelgryndConfig get() {
      return VelgryndConfig.Holder.instance;
   }

   public static void setInstance(VelgryndConfig cfg) {
      VelgryndConfig.Holder.instance = cfg;
   }

   public static VelgryndConfig.VelgryndSettings settings() {
      VelgryndConfig config = get();
      return config == null ? new VelgryndConfig.VelgryndSettings() : config.Velgrynd;
   }

   public String getFileName() {
      return "velgrynd";
   }

   public Path getConfigPath() {
      return Paths.get("config", "fox_ultimate", this.getFileName() + ".toml");
   }

   public void load() {
      Path p = this.getConfigPath();

      try {
         if (Files.exists(p) && Files.size(p) == 0L) {
            Files.delete(p);
            FoxAblazeUltimateMod.LOGGER.info("[FoxAblazeUltimate] Deleted empty config {}, rebuilding defaults.", p);
         }
      } catch (IOException e) {
         FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] Config pre-check failed ({}): {}", p, e.getMessage());
      }

      super.load();
   }

   private static final class Holder {
      static VelgryndConfig instance;
   }

   public static class VelgryndSettings extends ManasSubConfig {
      @Comment("Enable automatic Velgrynd evolution when all configured requirements are met.")
      public boolean acquisitionEnabled = true;
      @Comment("Required permanent skill IDs. All listed skills are required; temporary and sub-instance copies never count.")
      public List<String> acquisitionRequiredSkills = new ArrayList<>(
         List.of("tensura:flame_domination", "tensura:flame_breath", "tensura:thought_acceleration", "tensura:spatial_manipulation")
      );
      @Comment("Require every prerequisite skill to be mastered. False requires permanent ownership only.")
      public boolean acquisitionRequiresMastery = true;
      @Comment("Remove all configured prerequisite skills after successful evolution.")
      public boolean acquisitionConsumeRequiredSkills = true;
      @Comment("Permanent maximum-magicule capacity consumed on evolution, not recoverable current magicules. Temporary capacity bonuses do not count.")
      public double acquisitionMaximumMagiculeCost = 3000000.0;
      @Comment("Ticks between automatic evolution checks. 20 ticks = 1 second; minimum 1 tick.")
      public int acquisitionCheckIntervalTicks = 20;
      @Comment("Initial mastery of the acquired Velgrynd skill, clamped to its maximum mastery.")
      public int acquisitionGrantMastery = 0;
      @Comment("Magicule cost for summoning an avatar with Scorching Dragon Liberation.")
      public double magiculeCostLiberation = 50000.0;
      @Comment("Scorching Dragon Liberation cooldown in seconds.")
      public int liberationCooldown = 60;
      @Comment("Maximum number of avatars. Summoning beyond this replaces the oldest avatar.")
      public int maxAvatars = 5;
      @Comment("Maximum distance in blocks to target your own avatar when switching control.")
      public double avatarTargetRange = 50.0;
      @Comment("Magicule cost for Scorching Excitation.")
      public double magiculeCostExcitation = 10000.0;
      @Comment("Scorching Excitation duration in seconds.")
      public int excitationDurationSeconds = 300;
      @Comment("Scorching Excitation cooldown in seconds.")
      public int excitationCooldown = 300;
      @Comment("Scorching Excitation movement speed bonus. 0.5 means +50%.")
      public double excitationMovementSpeedBonus = 0.5;
      @Comment("Scorching Excitation attack speed bonus. 0.5 means +50%.")
      public double excitationAttackSpeedBonus = 0.5;
      @Comment("Scorching Excitation chant (casting) speed bonus added to Tensura's chant speed attribute.")
      public double excitationChantSpeedBonus = 1.0;
      @Comment("Scorching Excitation flame and heat damage bonus. 0.5 means +50%.")
      public double excitationHeatDamageBonus = 0.5;
      @Comment("Magicule cost for Dimension Jump.")
      public double magiculeCostDimensionJump = 4000.0;
      @Comment("Dimension Jump cooldown in seconds.")
      public int dimensionJumpCooldown = 30;
      @Comment("Dimension Jump teleport range in blocks.")
      public double dimensionJumpRange = 64.0;
      @Comment("Magicule cost for Scorching Dragon Hegemony: Accelerated Excitation.")
      public double magiculeCostAcceleration = 20000.0;
      @Comment("Scorching Dragon Hegemony: Accelerated Excitation cooldown in seconds.")
      public int accelerationCooldown = 600;
      @Comment("Hold time in ticks before the weapon is charged. 100 ticks = 5 seconds.")
      public int accelerationChargeTicks = 100;
      @Comment("Flame damage dealt by a charged weapon strike.")
      public double accelerationDamage = 300.0;
      @Comment("Passive flame damage boost. 0.5 means +50% flame damage.")
      public double flameDamageBoost = 0.5;
      @Comment("Passive reduction for incoming flame attacks. 0.8 means 80% less damage.")
      public double flameDamageReduction = 0.8;
      @Comment("Seconds of burning applied to targets by the owner's melee attacks.")
      public int attackBurnSeconds = 5;
      @Comment("Passive reduction for incoming physical attacks. 0.3 means 30% less damage.")
      public double physicalDamageReduction = 0.3;
      @Comment("Passive reduction for incoming elemental attacks other than flame. 0.5 means 50% less damage.")
      public double elementalDamageReduction = 0.5;
   }
}
