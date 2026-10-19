package com.github.L_Ender.cataclysm.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class EffectWetness extends MobEffect {
   private static final ResourceLocation WETNESS_SPEED = ResourceLocation.fromNamespaceAndPath("cataclysm", "wetness_speed");

   public EffectWetness() {
      super(MobEffectCategory.HARMFUL, 5928654);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, WETNESS_SPEED, -0.05, Operation.ADD_MULTIPLIED_TOTAL);
   }

   public boolean applyEffectTick(LivingEntity LivingEntityIn, int amplifier) {
      if (LivingEntityIn.isSensitiveToWater()) {
         LivingEntityIn.hurt(LivingEntityIn.damageSources().magic(), 1.0F);
      }

      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int p_295391_, int p_294280_) {
      return true;
   }
}
