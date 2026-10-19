package com.foxablazeultimate.ability.skill.ciel;

import com.foxablazeultimate.ability.skill.raphael.RaphaelSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class CielSkill extends RaphaelSkill {
   private static final int MODE_COUNT = 7;
   public static final int MODE_AUTHORITY_MANAGEMENT = 6;

   @Override
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/skill/ciel.png");
   }

   @Override
   public MutableComponent getColoredName() {
      MutableComponent name = super.getName();
      return name == null ? null : name.withStyle(ChatFormatting.RED);
   }

   @Override
   public int getModes(ManasSkillInstance instance) {
      return 7;
   }

   @Override
   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int safeMode = normalizeMode(mode);
      if (reverse) {
         return safeMode == 0 ? 6 : safeMode - 1;
      } else {
         return safeMode == 6 ? 0 : safeMode + 1;
      }
   }

   @Override
   public String getModeId(ManasSkillInstance instance, int mode) {
      int safeMode = normalizeMode(mode);
      return safeMode == 6 ? "ciel.authority_management" : super.getModeId(instance, safeMode);
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      int safeMode = normalizeMode(mode);
      if (safeMode != 6) {
         super.onPressed(instance, entity, keyNumber, safeMode);
      } else if (entity instanceof ServerPlayer player) {
         boolean emergency = CielEmergencyController.toggle(player, instance);
         String messageKey = emergency ? "foxablazeultimate.skill.ciel.assistance.emergency" : "foxablazeultimate.skill.ciel.assistance.normal";
         player.displayClientMessage(
            Component.translatable("foxablazeultimate.skill.ciel.assistance.switched", new Object[]{Component.translatable(messageKey)})
               .withStyle(ChatFormatting.GOLD),
            true
         );
      }
   }

   @Override
   protected boolean shouldPromptForCustomName(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   @Override
   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      if (entity instanceof Player player) {
         CielPassiveHandler.refreshAttributes(player);
      }
   }

   @Override
   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      if (entity instanceof Player player) {
         CielPassiveHandler.refreshAttributes(player);
      }
   }

   @Override
   public void onRespawn(ManasSkillInstance instance, ServerPlayer player, boolean conqueredEnd) {
      super.onRespawn(instance, player, conqueredEnd);
      CielPassiveHandler.refreshAttributes(player);
   }

   @Override
   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      if (entity instanceof Player player) {
         CielPassiveHandler.refreshAttributes(player);
      }
   }

   private static int normalizeMode(int mode) {
      return Math.floorMod(mode, 7);
   }
}
