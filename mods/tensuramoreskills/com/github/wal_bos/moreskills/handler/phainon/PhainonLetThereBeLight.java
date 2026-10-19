package com.github.wal_bos.moreskills.handler.phainon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class PhainonLetThereBeLight {
   private PhainonLetThereBeLight() {
   }

   public static void execute(LivingEntity entity, ManasSkillInstance instance) {
      Vec3 look = entity.getLookAngle().normalize();
      entity.setDeltaMovement(look.scale(2.0).add(0.0, 0.15, 0.0));
      entity.hurtMarked = true;
      AABB box = entity.getBoundingBox().expandTowards(look.scale(9.0)).inflate(2.0);
      boolean hit = false;

      for (LivingEntity target : entity.level()
         .getEntitiesOfClass(LivingEntity.class, box, targetx -> targetx != entity && targetx.isAlive() && !targetx.isAlliedTo(entity))) {
         DamageSource source = TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.MAGIC_GENERIC, entity);
         target.hurt(source, 350.0F + PhainonProgress.get(instance).getCoreFlameCount() * 50.0F);
         hit = true;
      }

      if (hit) {
         PhainonProgress.get(instance).addBlaze(1);
      }
   }
}
