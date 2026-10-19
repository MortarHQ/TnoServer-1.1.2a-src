package xaero.map.effects;

import net.minecraft.world.effect.MobEffectCategory;

public class NoCaveMapsEffect extends WorldMapStatusEffect {
   protected NoCaveMapsEffect(MobEffectCategory type) {
      super(type, -16777216, "no_cave_maps");
   }
}
