package xaero.common.effect;

import net.minecraft.world.effect.MobEffectCategory;

public class NoMinimapEffect extends MinimapStatusEffect {
   public NoMinimapEffect(MobEffectCategory type) {
      super(type, -16777216, "no_minimap");
   }
}
