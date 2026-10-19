package com.yungnickyoung.minecraft.yungscavebiomes.effect;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class BuffetedEffect extends MobEffect {
   public BuffetedEffect() {
      super(MobEffectCategory.HARMFUL, 10052662);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, YungsCaveBiomesCommon.id("effect.buffeted"), -0.5, Operation.ADD_MULTIPLIED_TOTAL);
   }
}
