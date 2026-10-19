package com.foxablazeultimate.effect;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.world.effect.MobEffectCategory;

public class SatanaelCombatEffect extends TensuraMobEffect {
   public SatanaelCombatEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(147, 36, 35).getRGB());
   }
}
