package xaero.map.effects;

import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class EffectsRegister {
   public void registerEffects(Function<WorldMapStatusEffect, Holder<MobEffect>> registry) {
      Effects.init();
      Effects.NO_WORLD_MAP = registry.apply(Effects.NO_WORLD_MAP_UNHELD);
      Effects.NO_WORLD_MAP_HARMFUL = registry.apply(Effects.NO_WORLD_MAP_HARMFUL_UNHELD);
      Effects.NO_CAVE_MAPS = registry.apply(Effects.NO_CAVE_MAPS_UNHELD);
      Effects.NO_CAVE_MAPS_HARMFUL = registry.apply(Effects.NO_CAVE_MAPS_HARMFUL_UNHELD);
   }
}
