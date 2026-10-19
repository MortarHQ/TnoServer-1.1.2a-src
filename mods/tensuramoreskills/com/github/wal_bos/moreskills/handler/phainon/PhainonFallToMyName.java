package com.github.wal_bos.moreskills.handler.phainon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public final class PhainonFallToMyName {
   private PhainonFallToMyName() {
   }

   public static void tick(LivingEntity entity, ManasSkillInstance instance) {
      int shatter = instance.getOrCreateTag().getInt("PhainonShatterTicks");
      if (shatter > 0) {
         entity.setHealth(Math.max(entity.getHealth(), 1.0F));
         entity.invulnerableTime = 0;
         instance.getOrCreateTag().putInt("PhainonShatterTicks", --shatter);
         heatAura(entity);
         if (shatter <= 0) {
            execute(entity, instance);
         }
      }
   }

   public static void execute(LivingEntity entity, ManasSkillInstance instance) {
      if (instance.getOrCreateTag().getInt("PhainonShatterTicks") <= 0 && !instance.getOrCreateTag().getBoolean("PhainonFallUnlocked")) {
         entity.sendSystemMessage(Component.literal("Fall to my name is not active.").withStyle(ChatFormatting.RED));
      } else {
         DamageSource source = TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.MAGIC_GENERIC, entity);

         for (LivingEntity target : entity.level()
            .getEntitiesOfClass(
               LivingEntity.class, entity.getBoundingBox().inflate(24.0), targetx -> targetx != entity && targetx.isAlive() && !targetx.isAlliedTo(entity)
            )) {
            target.hurt(source, 500000.0F);
            target.setRemainingFireTicks(600);
         }

         instance.getOrCreateTag().putInt("PhainonShatterTicks", 0);
         instance.getOrCreateTag().putBoolean("PhainonFallUnlocked", false);
         PhainonDelivererActions.stop(entity, instance);
         entity.hurt(entity.damageSources().fellOutOfWorld(), Float.MAX_VALUE);
      }
   }

   private static void heatAura(LivingEntity entity) {
      DamageSource source = TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.MAGIC_GENERIC, entity);

      for (LivingEntity target : entity.level()
         .getEntitiesOfClass(
            LivingEntity.class, entity.getBoundingBox().inflate(8.0), targetx -> targetx != entity && targetx.isAlive() && !targetx.isAlliedTo(entity)
         )) {
         target.hurt(source, 10000.0F);
         target.setRemainingFireTicks(80);
      }
   }
}
