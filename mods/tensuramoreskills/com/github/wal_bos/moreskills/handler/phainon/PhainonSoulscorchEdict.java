package com.github.wal_bos.moreskills.handler.phainon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class PhainonSoulscorchEdict {
   private PhainonSoulscorchEdict() {
   }

   public static void start(LivingEntity entity, ManasSkillInstance instance) {
      instance.getOrCreateTag().putInt("PhainonSoulscorchGuard", 100);
      entity.sendSystemMessage(Component.literal("Calamity: Soulscorch Edict.").withStyle(ChatFormatting.GOLD));
   }

   public static void tick(LivingEntity entity, ManasSkillInstance instance) {
      int guard = instance.getOrCreateTag().getInt("PhainonSoulscorchGuard");
      if (guard > 0) {
         instance.getOrCreateTag().putInt("PhainonSoulscorchGuard", --guard);
         if (guard == 0) {
            sweep(entity, instance);
         }
      }
   }

   private static void sweep(LivingEntity entity, ManasSkillInstance instance) {
      Vec3 look = entity.getLookAngle().normalize();
      AABB box = entity.getBoundingBox().expandTowards(look.scale(8.0)).inflate(4.0, 2.0, 4.0);
      boolean hit = false;

      for (LivingEntity target : entity.level()
         .getEntitiesOfClass(LivingEntity.class, box, targetx -> targetx != entity && targetx.isAlive() && !targetx.isAlliedTo(entity))) {
         Vec3 dir = target.position().subtract(entity.position()).normalize();
         if (!(dir.dot(look) < 0.15)) {
            DamageSource source = TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.MAGIC_GENERIC, entity);
            target.hurt(source, 650.0F + PhainonProgress.get(instance).getCoreFlameCount() * 75.0F);
            target.setDeltaMovement(target.getDeltaMovement().add(dir.scale(1.5)).add(0.0, 0.4, 0.0));
            target.hurtMarked = true;
            hit = true;
         }
      }

      if (hit) {
         PhainonProgress.get(instance).addBlaze(2);
      }
   }
}
