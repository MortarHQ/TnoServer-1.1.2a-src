package com.chronodawn.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EntropyEffect extends MobEffect {
   private static final int TICK_INTERVAL = 20;

   public EntropyEffect() {
      super(MobEffectCategory.HARMFUL, 13085050);
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return duration % 20 == 0;
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      if (entity.isDeadOrDying()) {
         return false;
      }

      DamageSource source = entity.damageSources().magic();
      entity.hurt(source, 1.0F);
      return true;
   }
}
