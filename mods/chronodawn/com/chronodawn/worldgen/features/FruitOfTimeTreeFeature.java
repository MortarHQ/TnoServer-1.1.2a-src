package com.chronodawn.worldgen.features;

import com.chronodawn.compat.CompatResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class FruitOfTimeTreeFeature {
   public static final ResourceKey<ConfiguredFeature<?, ?>> FRUIT_OF_TIME_TREE = ResourceKey.create(
      Registries.CONFIGURED_FEATURE, CompatResourceLocation.create("chronodawn", "fruit_of_time_tree")
   );

   private FruitOfTimeTreeFeature() {
      throw new UnsupportedOperationException("Utility class");
   }
}
