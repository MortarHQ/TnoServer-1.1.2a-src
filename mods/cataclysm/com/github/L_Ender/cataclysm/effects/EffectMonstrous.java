package com.github.L_Ender.cataclysm.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class EffectMonstrous extends MobEffect {
   private static final ResourceLocation MONSTROUS_KNOCKBACK = ResourceLocation.fromNamespaceAndPath("cataclysm", "monstrous_knockback");
   private static final ResourceLocation MONSTROUS_ARMOR = ResourceLocation.fromNamespaceAndPath("cataclysm", "monstrous_armor");
   private static final ResourceLocation MONSTROUS_ARMOR_TOUGHNESS = ResourceLocation.fromNamespaceAndPath("cataclysm", "monstrous_armor_toughness");

   public EffectMonstrous() {
      super(MobEffectCategory.BENEFICIAL, 8803127);
      this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, MONSTROUS_KNOCKBACK, 0.5, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ARMOR, MONSTROUS_ARMOR, 3.0, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, MONSTROUS_ARMOR_TOUGHNESS, 2.0, Operation.ADD_VALUE);
   }

   public boolean applyEffectTick(LivingEntity LivingEntityIn, int amplifier) {
      if (LivingEntityIn.getHealth() < LivingEntityIn.getMaxHealth() * 1.0F / 2.0F) {
         LivingEntityIn.heal(1.0F);
      }

      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      int k = 50 >> amplifier;
      return k > 0 ? duration % k == 0 : true;
   }
}
