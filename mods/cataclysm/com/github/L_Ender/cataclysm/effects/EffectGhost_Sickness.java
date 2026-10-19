package com.github.L_Ender.cataclysm.effects;

import java.util.Set;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

public class EffectGhost_Sickness extends MobEffect {
   public EffectGhost_Sickness() {
      super(MobEffectCategory.HARMFUL, 9722673);
   }

   public boolean applyEffectTick(LivingEntity LivingEntityIn, int amplifier) {
      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      int k = 50 >> amplifier;
      return k > 0 ? duration % k == 0 : true;
   }

   public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
   }
}
