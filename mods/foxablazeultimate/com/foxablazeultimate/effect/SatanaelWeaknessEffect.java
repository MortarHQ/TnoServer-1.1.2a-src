package com.foxablazeultimate.effect;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.world.effect.MobEffectCategory;

public class SatanaelWeaknessEffect extends TensuraMobEffect {
   public SatanaelWeaknessEffect() {
      super(MobEffectCategory.HARMFUL, new Color(72, 77, 72).getRGB());
   }
}
