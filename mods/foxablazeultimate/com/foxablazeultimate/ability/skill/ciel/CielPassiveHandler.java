package com.foxablazeultimate.ability.skill.ciel;

import com.foxablazeultimate.ability.skill.yogsothoth.YogTimeStopManager;
import com.foxablazeultimate.config.CielConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillUpdateCooldownEvent;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public final class CielPassiveHandler {
   private static final ResourceLocation CHANT_SPEED = id("chant_speed");
   private static final ResourceLocation ANALYSIS_LEVEL = id("analysis_level");
   private static final ResourceLocation LEARNING_GAIN = id("learning_gain");
   private static final ResourceLocation MASTERY_GAIN = id("mastery_gain");
   private static final ResourceLocation MELEE_PREDICTION = id("melee_prediction");
   private static final ResourceLocation PROJECTILE_PREDICTION = id("projectile_prediction");
   private static final ResourceLocation RAPHAEL_MELEE_PREDICTION = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "raphael/melee_dodge");
   private static final ResourceLocation RAPHAEL_PROJECTILE_PREDICTION = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "raphael/projectile_dodge");
   private static final String COOLDOWN_FRACTION_PREFIX = "CielCooldownFraction_";
   private static boolean initialized;

   private CielPassiveHandler() {
   }

   public static synchronized void init() {
      if (!initialized) {
         initialized = true;
         SkillEvents.SKILL_UPDATE_COOLDOWN.register((SkillUpdateCooldownEvent)(instance, owner, mode, current, updated) -> {
            if (!hasPermanentCiel(owner)) {
               return EventResult.pass();
            }

            double multiplier = Mth.clamp(CielConfig.settings().cooldownMultiplier, 0.0, 1.0);
            if (multiplier >= 1.0 || current <= 0) {
               return EventResult.pass();
            }

            if (multiplier <= 0.0) {
               updated.set(0);
               return EventResult.pass();
            }

            String key = "CielCooldownFraction_" + Math.max(0, mode);
            double accumulator = instance.getOrCreateTag().getDouble(key) + (1.0 / multiplier - 1.0);
            int additionalSeconds = (int)Math.floor(accumulator);
            instance.getOrCreateTag().putDouble(key, accumulator - additionalSeconds);
            if (additionalSeconds > 0) {
               updated.set(Math.max(0, (Integer)updated.get() - additionalSeconds));
            }

            return EventResult.pass();
         });
         SkillEvents.ACTIVATE_SKILL
            .register(
               (SkillActivationEvent)(changeable, owner, keyNumber, mode) -> {
                  if (owner instanceof Player player && CielState.isEmergencyTakeover(player) && !CielEmergencyController.isAutomatedAction()) {
                     ManasSkillInstance instance = (ManasSkillInstance)changeable.get();
                     if (instance != null && instance.getSkill() == FoxAblazeUltimateSkills.CIEL.get() && mode == 6) {
                        return EventResult.pass();
                     } else {
                        return instance != null
                              && instance.getSkill() != null
                              && YogTimeStopManager.YOG_SOTHOTH_ID.equals(instance.getSkill().getRegistryName())
                              && mode == 8
                           ? EventResult.pass()
                           : EventResult.interruptFalse();
                     }
                  } else {
                     return EventResult.pass();
                  }
               }
            );
      }
   }

   public static void refreshAttributes(Player player) {
      if (!hasPermanentCiel(player)) {
         removeAttributes(player);
      } else {
         CielConfig.CielSettings config = CielConfig.settings();
         replace(player.getAttribute(TensuraAttributes.CHANT_SPEED), CHANT_SPEED, Mth.clamp(config.chantSpeedBonus, 0.0, 1024.0));
         replace(player.getAttribute(TensuraAttributes.ANALYSIS_LEVEL), ANALYSIS_LEVEL, Mth.clamp(config.analysisLevelBonus, 0.0, 1024.0));
         replace(player.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN), LEARNING_GAIN, Mth.clamp(config.learningBonus, 0.0, 1024.0));
         replace(player.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN), MASTERY_GAIN, Mth.clamp(config.masteryBonus, 0.0, 1024.0));
         remove(player.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE), RAPHAEL_MELEE_PREDICTION);
         remove(player.getAttribute(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE), RAPHAEL_PROJECTILE_PREDICTION);
         double prediction = Mth.clamp(config.futureVisionChance, 0.0, 100.0);
         replaceToMinimum(player.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE), MELEE_PREDICTION, prediction);
         replaceToMinimum(player.getAttribute(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE), PROJECTILE_PREDICTION, prediction);
      }
   }

   public static double optimizeMagiculeCost(LivingEntity owner, double originalCost) {
      if (hasPermanentCiel(owner) && !(originalCost <= 0.0)) {
         double multiplier = Mth.clamp(CielConfig.settings().magiculeCostMultiplier, 0.0, 1.0);
         return originalCost * multiplier;
      } else {
         return originalCost;
      }
   }

   public static boolean hasPermanentCiel(LivingEntity entity) {
      return entity instanceof Player player
         && CielState.isAwakened(player)
         && SkillAPI.getSkillsFrom(player)
            .getSkill((ManasSkill)FoxAblazeUltimateSkills.CIEL.get())
            .filter(instance -> !instance.isTemporarySkill() && !instance.isSubInstance())
            .isPresent();
   }

   private static void removeAttributes(Player player) {
      remove(player.getAttribute(TensuraAttributes.CHANT_SPEED), CHANT_SPEED);
      remove(player.getAttribute(TensuraAttributes.ANALYSIS_LEVEL), ANALYSIS_LEVEL);
      remove(player.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN), LEARNING_GAIN);
      remove(player.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN), MASTERY_GAIN);
      remove(player.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE), MELEE_PREDICTION);
      remove(player.getAttribute(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE), PROJECTILE_PREDICTION);
   }

   private static void replace(AttributeInstance attribute, ResourceLocation id, double value) {
      if (attribute != null) {
         attribute.addOrReplacePermanentModifier(new AttributeModifier(id, value, Operation.ADD_VALUE));
      }
   }

   private static void replaceToMinimum(AttributeInstance attribute, ResourceLocation id, double minimum) {
      if (attribute != null) {
         attribute.removeModifier(id);
         double addition = Math.max(0.0, minimum - attribute.getValue());
         if (addition > 0.0) {
            attribute.addOrReplacePermanentModifier(new AttributeModifier(id, addition, Operation.ADD_VALUE));
         }
      }
   }

   private static void remove(AttributeInstance attribute, ResourceLocation id) {
      if (attribute != null) {
         attribute.removeModifier(id);
      }
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "ciel_" + path);
   }
}
