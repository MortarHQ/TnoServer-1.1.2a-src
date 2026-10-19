package com.github.wal_bos.moreskills.effect;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class AlbisDiamondDustEffect extends TensuraMobEffect {
   public AlbisDiamondDustEffect() {
      super(MobEffectCategory.HARMFUL, 12055807);
      this.addAttributeModifier(
         Attributes.MOVEMENT_SPEED,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "albis_diamond_dust_speed"),
         -0.08,
         Operation.ADD_MULTIPLIED_TOTAL
      );
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return false;
   }
}
