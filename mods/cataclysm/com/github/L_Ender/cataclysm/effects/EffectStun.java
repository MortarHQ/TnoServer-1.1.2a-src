package com.github.L_Ender.cataclysm.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class EffectStun extends MobEffect {
   private static final ResourceLocation STUN_SPEED = ResourceLocation.fromNamespaceAndPath("cataclysm", "stun_speed");

   public EffectStun() {
      super(MobEffectCategory.HARMFUL, 16747520);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, STUN_SPEED, -0.5, Operation.ADD_VALUE);
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return duration > 0;
   }
}
