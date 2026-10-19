package com.bobmowzie.mowziesmobs.server.potion;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectSunblock extends MowzieEffect {
   public EffectSunblock() {
      super(MobEffectCategory.BENEFICIAL, 16768834);
   }

   public boolean applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
      super.applyEffectTick(entityLivingBaseIn, amplifier);
      int k = 50 >> amplifier;
      if (k > 0 && entityLivingBaseIn.tickCount % k == 0 && entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
         entityLivingBaseIn.heal(1.0F);
      }

      return true;
   }
}
