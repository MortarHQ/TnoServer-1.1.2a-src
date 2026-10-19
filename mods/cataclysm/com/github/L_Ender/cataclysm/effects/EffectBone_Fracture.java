package com.github.L_Ender.cataclysm.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class EffectBone_Fracture extends MobEffect {
   private static final ResourceLocation BONE_FRACTURE_SPEED = ResourceLocation.fromNamespaceAndPath("cataclysm", "bone_fracture_speed");
   private static final ResourceLocation BONE_FRACTURE_ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath("cataclysm", "bone_fracture_attack_speed");

   public EffectBone_Fracture() {
      super(MobEffectCategory.HARMFUL, 11381677);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, BONE_FRACTURE_SPEED, -0.02, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, BONE_FRACTURE_ATTACK_SPEED, -0.15, Operation.ADD_VALUE);
   }

   public boolean applyEffectTick(LivingEntity LivingEntityIn, int amplifier) {
      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return duration > 0;
   }
}
