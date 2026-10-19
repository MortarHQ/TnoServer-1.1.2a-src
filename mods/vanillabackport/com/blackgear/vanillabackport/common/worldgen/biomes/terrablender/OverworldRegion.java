package com.blackgear.vanillabackport.common.worldgen.biomes.terrablender;

import com.blackgear.vanillabackport.common.registries.ModBiomes;
import com.blackgear.vanillabackport.core.VanillaBackport;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;
import terrablender.api.ParameterUtils.Continentalness;
import terrablender.api.ParameterUtils.Depth;
import terrablender.api.ParameterUtils.Erosion;
import terrablender.api.ParameterUtils.Humidity;
import terrablender.api.ParameterUtils.ParameterPointListBuilder;
import terrablender.api.ParameterUtils.Temperature;
import terrablender.api.ParameterUtils.Weirdness;

public class OverworldRegion extends Region {
   public OverworldRegion(ResourceLocation name, RegionType type, int weight) {
      super(name, type, weight);
   }

   public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
      VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();
      if ((Boolean)VanillaBackport.COMMON_CONFIG.hasPaleGarden.get()) {
         addPaleGarden(builder);
      }

      addSulfurCaves(builder);
      builder.build().forEach(mapper);
   }

   private static void addPaleGarden(VanillaParameterOverlayBuilder builder) {
      addPaleGarden(
         builder, OverworldRegion.Placement.PEAK_VARIANT, Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND), Erosion.EROSION_2
      );
      addPaleGarden(builder, OverworldRegion.Placement.PEAK_VARIANT, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_3);
      addPaleGarden(
         builder, OverworldRegion.Placement.HIGH_SLICE_VARIANT, Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND), Erosion.EROSION_2
      );
      addPaleGarden(builder, OverworldRegion.Placement.HIGH_SLICE_VARIANT, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_3);
      addPaleGarden(builder, OverworldRegion.Placement.MID_SLICE_VARIANT, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_1);
      addPaleGarden(builder, OverworldRegion.Placement.MID_SLICE_VARIANT, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_2);
   }

   private static void addSulfurCaves(VanillaParameterOverlayBuilder builder) {
      new ParameterPointListBuilder()
         .temperature(new Temperature[]{Temperature.FULL_RANGE})
         .humidity(new Humidity[]{Humidity.FULL_RANGE})
         .continentalness(new Parameter[]{Continentalness.span(Continentalness.COAST, Continentalness.INLAND)})
         .erosion(new Parameter[]{Erosion.span(Erosion.EROSION_5, Erosion.EROSION_6)})
         .depth(new Depth[]{Depth.UNDERGROUND})
         .weirdness(new Parameter[]{Parameter.span(-1.1F, -0.85F)})
         .offset(new Long[]{0L})
         .build()
         .forEach(point -> builder.add(point, ModBiomes.SULFUR_CAVES));
   }

   private static void addPaleGarden(VanillaParameterOverlayBuilder builder, OverworldRegion.Placement placement, Parameter continentalness, Erosion erosion) {
      for (Weirdness weirdness : placement.getWeirdnesses()) {
         new ParameterPointListBuilder()
            .temperature(new Parameter[]{Temperature.NEUTRAL.parameter()})
            .humidity(new Parameter[]{Humidity.HUMID.parameter()})
            .continentalness(new Parameter[]{continentalness})
            .erosion(new Parameter[]{erosion.parameter()})
            .depth(new Parameter[]{Depth.SURFACE.parameter()})
            .weirdness(new Parameter[]{weirdness.parameter()})
            .offset(new Long[]{0L})
            .build()
            .forEach(point -> builder.add(point, ModBiomes.PALE_GARDEN));
      }
   }

   public enum Placement {
      VALLEY(Weirdness.VALLEY),
      LOW_SLICE(Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_VARIANT_ASCENDING),
      LOW_SLICE_NORMAL(Weirdness.LOW_SLICE_NORMAL_DESCENDING),
      LOW_SLICE_VARIANT(Weirdness.LOW_SLICE_VARIANT_ASCENDING),
      MID_SLICE(
         Weirdness.MID_SLICE_NORMAL_ASCENDING,
         Weirdness.MID_SLICE_VARIANT_ASCENDING,
         Weirdness.MID_SLICE_NORMAL_DESCENDING,
         Weirdness.MID_SLICE_VARIANT_DESCENDING
      ),
      MID_SLICE_NORMAL(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING),
      MID_SLICE_VARIANT(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING),
      HIGH_SLICE(
         Weirdness.HIGH_SLICE_NORMAL_ASCENDING,
         Weirdness.HIGH_SLICE_VARIANT_ASCENDING,
         Weirdness.HIGH_SLICE_NORMAL_DESCENDING,
         Weirdness.HIGH_SLICE_VARIANT_DESCENDING
      ),
      HIGH_SLICE_NORMAL(Weirdness.HIGH_SLICE_NORMAL_ASCENDING, Weirdness.HIGH_SLICE_NORMAL_DESCENDING),
      HIGH_SLICE_VARIANT(Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.HIGH_SLICE_VARIANT_DESCENDING),
      PEAK(Weirdness.PEAK_NORMAL, Weirdness.PEAK_VARIANT),
      PEAK_NORMAL(Weirdness.PEAK_NORMAL),
      PEAK_VARIANT(Weirdness.PEAK_VARIANT);

      private final Weirdness[] weirdnesses;

      Placement(Weirdness... weirdnesses) {
         this.weirdnesses = weirdnesses;
      }

      public Weirdness[] getWeirdnesses() {
         return this.weirdnesses;
      }
   }
}
