package com.blackgear.platform.common.worldgen.placement;

import com.blackgear.platform.common.worldgen.placement.parameters.Depth;
import com.blackgear.platform.common.worldgen.placement.parameters.Weirdness;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

public class BiomePlacement {
   public static final List<Consumer<BiomePlacement.Event>> LISTENERS = Lists.newArrayList();

   public static void registerBiomePlacements(Consumer<BiomePlacement.Event> listener) {
      LISTENERS.add(listener);
   }

   public interface Event {
      void add(Pair<ParameterPoint, ResourceKey<Biome>> var1);

      default void addBiome(
         Parameter temperature,
         Parameter humidity,
         Parameter continentalness,
         Parameter erosion,
         Parameter depth,
         Parameter weirdness,
         float offset,
         ResourceKey<Biome> biome
      ) {
         this.add(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, (float)Climate.quantizeCoord(offset)), biome));
      }

      default void addSurfaceBiome(
         Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness, float offset, ResourceKey<Biome> biome
      ) {
         this.addBiome(temperature, humidity, continentalness, erosion, Depth.SURFACE.parameter(), weirdness, offset, biome);
         this.addBiome(temperature, humidity, continentalness, erosion, Depth.FLOOR.parameter(), weirdness, offset, biome);
      }

      default void addUndergroundBiome(
         Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness, float offset, ResourceKey<Biome> biome
      ) {
         this.addBiome(temperature, humidity, continentalness, erosion, Depth.UNDERGROUND.parameter(), weirdness, offset, biome);
      }

      default void addBottomBiome(
         Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness, float offset, ResourceKey<Biome> biome
      ) {
         this.addBiome(temperature, humidity, continentalness, erosion, Depth.FLOOR.parameter(), weirdness, offset, biome);
      }

      default void addSurfaceBiome(
         Placement placement, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, float offset, ResourceKey<Biome> biome
      ) {
         for (Weirdness weirdness : placement.getWeirdnesses()) {
            this.addSurfaceBiome(temperature, humidity, continentalness, erosion, weirdness.parameter(), offset, biome);
         }
      }
   }
}
