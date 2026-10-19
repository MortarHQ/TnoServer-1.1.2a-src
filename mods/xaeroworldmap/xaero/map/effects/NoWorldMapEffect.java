package xaero.map.effects;

import net.minecraft.world.effect.MobEffectCategory;

public class NoWorldMapEffect extends WorldMapStatusEffect {
   protected NoWorldMapEffect(MobEffectCategory type) {
      super(type, -16777216, "no_world_map");
   }
}
