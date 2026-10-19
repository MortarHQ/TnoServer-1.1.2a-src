package com.chronodawn.worldgen.features;

import com.chronodawn.compat.CompatResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class DarkTimeWoodTreeFeature {
   public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_TIME_WOOD_TREE = ResourceKey.create(
      Registries.CONFIGURED_FEATURE, CompatResourceLocation.create("chronodawn", "dark_time_wood_tree")
   );

   private DarkTimeWoodTreeFeature() {
      throw new UnsupportedOperationException("Utility class");
   }
}
