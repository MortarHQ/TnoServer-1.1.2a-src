package com.benji.netherman.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class ManipulationEffect extends MobEffect {
   public ManipulationEffect() {
      super(MobEffectCategory.HARMFUL, 10027008);
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      if (entity instanceof Player player && !player.level().isClientSide()) {
         AABB box = player.getBoundingBox().inflate(20.0);

         for (Piglin piglin : player.level().getEntitiesOfClass(Piglin.class, box)) {
            if (piglin.getTarget() != player) {
               piglin.setTarget(player);
            }
         }

         for (ZombifiedPiglin zp : player.level().getEntitiesOfClass(ZombifiedPiglin.class, box)) {
            if (zp.getTarget() != player) {
               zp.setTarget(player);
            }
         }
      }

      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return duration % 20 == 0;
   }
}
