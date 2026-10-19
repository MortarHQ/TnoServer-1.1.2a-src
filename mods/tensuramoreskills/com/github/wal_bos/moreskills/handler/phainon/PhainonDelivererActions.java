package com.github.wal_bos.moreskills.handler.phainon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Abilities;

public final class PhainonDelivererActions {
   public static final ResourceLocation DELIVERER_HEALTH = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "phainon_deliverer_health");
   public static final ResourceLocation DELIVERER_REACH = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "phainon_deliverer_reach");
   public static final ResourceLocation DELIVERER_SPEED = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "phainon_deliverer_speed");

   private PhainonDelivererActions() {
   }

   public static boolean isActive(ManasSkillInstance instance) {
      return instance.getOrCreateTag().getInt("PhainonDelivererTicks") > 0;
   }

   public static void start(LivingEntity entity, ManasSkillInstance instance) {
      stop(entity, instance);
      instance.getOrCreateTag().putInt("PhainonDelivererTicks", 6000);
      instance.getOrCreateTag().putInt("PhainonBlaze", 0);
      instance.getOrCreateTag().putInt("PhainonBlazeHitCooldown", 0);
      instance.getOrCreateTag().putInt("PhainonSoulscorchGuard", 0);
      instance.getOrCreateTag().putInt("PhainonShatterTicks", 0);
      instance.getOrCreateTag().putBoolean("PhainonFallUnlocked", false);
      AttributeHelper.addPermanentAttribute(entity, Attributes.MAX_HEALTH, DELIVERER_HEALTH, 4.0, Operation.ADD_MULTIPLIED_TOTAL);
      AttributeHelper.addPermanentAttribute(entity, Attributes.ENTITY_INTERACTION_RANGE, DELIVERER_REACH, 3.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, Attributes.BLOCK_INTERACTION_RANGE, DELIVERER_REACH, 3.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, Attributes.FLYING_SPEED, DELIVERER_SPEED, 0.5, Operation.ADD_VALUE);
      entity.setHealth(entity.getMaxHealth());
      entity.sendSystemMessage(Component.literal("He Who Bears the World must Burn.").withStyle(ChatFormatting.GOLD));
   }

   public static void tick(LivingEntity entity, ManasSkillInstance instance) {
      if (isActive(instance)) {
         if (instance.getOrCreateTag().getInt("PhainonShatterTicks") > 0) {
            keepFlight(entity, instance);
         } else {
            int ticks = instance.getOrCreateTag().getInt("PhainonDelivererTicks") - 1;
            instance.getOrCreateTag().putInt("PhainonDelivererTicks", ticks);
            if (ticks <= 0) {
               stop(entity, instance);
            } else {
               if (instance.getOrCreateTag().getInt("PhainonBlazeHitCooldown") > 0) {
                  instance.getOrCreateTag().putInt("PhainonBlazeHitCooldown", instance.getOrCreateTag().getInt("PhainonBlazeHitCooldown") - 1);
               }

               keepFlight(entity, instance);
            }
         }
      }
   }

   public static void stop(LivingEntity entity, ManasSkillInstance instance) {
      instance.getOrCreateTag().putInt("PhainonDelivererTicks", 0);
      instance.getOrCreateTag().putInt("PhainonBlaze", 0);
      instance.getOrCreateTag().putInt("PhainonBlazeHitCooldown", 0);
      instance.getOrCreateTag().putInt("PhainonSoulscorchGuard", 0);
      AttributeHelper.removeAttribute(entity, Attributes.MAX_HEALTH, DELIVERER_HEALTH);
      AttributeHelper.removeAttribute(entity, Attributes.ENTITY_INTERACTION_RANGE, DELIVERER_REACH);
      AttributeHelper.removeAttribute(entity, Attributes.BLOCK_INTERACTION_RANGE, DELIVERER_REACH);
      AttributeHelper.removeAttribute(entity, Attributes.FLYING_SPEED, DELIVERER_SPEED);
      if (entity instanceof ServerPlayer player && instance.getOrCreateTag().getBoolean("PhainonForcedFlight")) {
         Abilities abilities = player.getAbilities();
         if (!player.isCreative() && !player.isSpectator()) {
            abilities.mayfly = false;
            abilities.flying = false;
         }

         instance.getOrCreateTag().putBoolean("PhainonForcedFlight", false);
         player.onUpdateAbilities();
      }

      if (entity.isAlive() && entity.getHealth() > entity.getMaxHealth()) {
         entity.setHealth(entity.getMaxHealth());
      }
   }

   private static void keepFlight(LivingEntity entity, ManasSkillInstance instance) {
      if (entity instanceof ServerPlayer player) {
         Abilities abilities = player.getAbilities();
         if (!abilities.mayfly) {
            instance.getOrCreateTag().putBoolean("PhainonForcedFlight", true);
            abilities.mayfly = true;
            player.onUpdateAbilities();
         }
      }
   }
}
