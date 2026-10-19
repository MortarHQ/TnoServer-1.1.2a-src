package com.chronodawn.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ChronoAegisEffect extends MobEffect {
   public ChronoAegisEffect() {
      super(MobEffectCategory.BENEFICIAL, 4286945);
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return false;
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      return true;
   }
}
