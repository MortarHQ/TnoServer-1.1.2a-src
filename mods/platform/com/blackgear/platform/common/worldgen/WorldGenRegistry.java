package com.blackgear.platform.common.worldgen;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class WorldGenRegistry<T> {
   protected final String modId;
   protected final ResourceKey<? extends Registry<T>> registry;

   private WorldGenRegistry(ResourceKey<? extends Registry<T>> registry, String modId) {
      this.registry = registry;
      this.modId = modId;
   }

   public static <T> WorldGenRegistry<T> of(ResourceKey<? extends Registry<T>> registry, String modId) {
      return new WorldGenRegistry<>(registry, modId);
   }

   public ResourceKey<T> create(String name) {
      return ResourceKey.create(this.registry, ResourceLocation.fromNamespaceAndPath(this.modId, name));
   }

   public void register(BootstrapContext<T> context, ResourceKey<T> key, T entry) {
      context.register(key, entry);
   }

   public <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
      BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration
   ) {
      context.register(key, new ConfiguredFeature(feature, configuration));
   }

   public void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, Feature<NoneFeatureConfiguration> feature) {
      this.register(context, key, feature, FeatureConfiguration.NONE);
   }

   public void register(
      BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placements
   ) {
      context.register(key, new PlacedFeature(feature, List.copyOf(placements)));
   }

   public void register(
      BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier... placements
   ) {
      this.register(context, key, feature, List.of(placements));
   }

   public void register(
      BootstrapContext<NoiseParameters> context, ResourceKey<NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes
   ) {
      context.register(key, new NoiseParameters(firstOctave, firstAmplitude, amplitudes));
   }

   public void register() {
   }
}
