package com.mortal_cultivation.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class MindscapeDimension {
   public static final ResourceKey<Level> MINDSCAPE_LEVEL = ResourceKey.create(
      Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "mindscape")
   );
   public static final ResourceKey<DimensionType> MINDSCAPE_TYPE = ResourceKey.create(
      Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "mindscape")
   );
}
