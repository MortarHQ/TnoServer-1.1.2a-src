package com.bobmowzie.mowziesmobs.server.potion;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class MowzieEffect extends MobEffect {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/gui/container/potions.png");

   public MowzieEffect(MobEffectCategory type, int liquidColor) {
      super(type, liquidColor);
   }

   public boolean shouldApplyEffectTickThisTick(int id, int amplifier) {
      return true;
   }
}
