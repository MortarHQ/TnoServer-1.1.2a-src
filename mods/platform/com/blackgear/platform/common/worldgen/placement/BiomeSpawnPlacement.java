package com.blackgear.platform.common.worldgen.placement;

import com.blackgear.platform.common.worldgen.placement.parameters.Depth;
import com.blackgear.platform.common.worldgen.placement.parameters.Weirdness;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

public class BiomeSpawnPlacement {
   public static final List<Pair<ParameterPoint, ResourceKey<Biome>>> BIOME_ENTRIES = Lists.newArrayList();

   public static void add(Pair<ParameterPoint, ResourceKey<Biome>> mapper) {
      BIOME_ENTRIES.add(mapper);
   }

   public static void addBiome(
      Parameter temperature,
      Parameter humidity,
      Parameter continentalness,
      Parameter erosion,
      Parameter depth,
      Parameter weirdness,
      float offset,
      ResourceKey<Biome> key
   ) {
      add(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, offset), key));
   }

   public static void addSurfaceBiome(
      Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness, float offset, ResourceKey<Biome> key
   ) {
      addBiome(temperature, humidity, continentalness, erosion, Depth.SURFACE.parameter(), weirdness, offset, key);
      addBiome(temperature, humidity, continentalness, erosion, Depth.FLOOR.parameter(), weirdness, offset, key);
   }

   public static void addUndergroundBiome(
      Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness, float offset, ResourceKey<Biome> key
   ) {
      addBiome(temperature, humidity, continentalness, erosion, Depth.UNDERGROUND.parameter(), weirdness, offset, key);
   }

   public static void addBottomBiome(
      Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness, float offset, ResourceKey<Biome> key
   ) {
      addBiome(temperature, humidity, continentalness, erosion, Depth.FLOOR.parameter(), weirdness, offset, key);
   }

   public static void addSurfaceBiome(
      Placement placement, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, float offset, ResourceKey<Biome> key
   ) {
      for (Weirdness weirdness : placement.getWeirdnesses()) {
         addSurfaceBiome(temperature, humidity, continentalness, erosion, weirdness.parameter(), offset, key);
      }
   }
}
