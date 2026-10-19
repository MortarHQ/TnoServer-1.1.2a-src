package com.chronodawn.worldgen.features;

import com.chronodawn.compat.CompatResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AncientTimeWoodTreeFeature {
   public static final ResourceKey<ConfiguredFeature<?, ?>> ANCIENT_TIME_WOOD_TREE = ResourceKey.create(
      Registries.CONFIGURED_FEATURE, CompatResourceLocation.create("chronodawn", "ancient_time_wood_tree")
   );

   private AncientTimeWoodTreeFeature() {
      throw new UnsupportedOperationException("Utility class");
   }
}
