package io.github.Memoires.mysticism.effect.debuff;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.world.effect.MobEffectCategory;

public class ConstantEnergyDebuffEffect extends TensuraMobEffect {
   public ConstantEnergyDebuffEffect() {
      super(MobEffectCategory.NEUTRAL, new Color(0, 56, 56).getRGB());
   }
}
