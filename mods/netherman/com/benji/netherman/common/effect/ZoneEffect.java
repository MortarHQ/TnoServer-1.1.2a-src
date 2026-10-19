package com.benji.netherman.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ZoneEffect extends MobEffect {
   public ZoneEffect(int color) {
      super(MobEffectCategory.NEUTRAL, color);
   }
}
