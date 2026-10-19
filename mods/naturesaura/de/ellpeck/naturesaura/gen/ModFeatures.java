package de.ellpeck.naturesaura.gen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public final class ModFeatures {
   public static Feature<TreeConfiguration> ANCIENT_TREE;
   public static Feature<NoneFeatureConfiguration> NETHER_WART_MUSHROOM;
   public static Feature<NoneFeatureConfiguration> AURA_BLOOM;
   public static Feature<NoneFeatureConfiguration> AURA_CACTUS;
   public static Feature<NoneFeatureConfiguration> WARPED_AURA_MUSHROOM;
   public static Feature<NoneFeatureConfiguration> CRIMSON_AURA_MUSHROOM;
   public static Feature<NoneFeatureConfiguration> AURA_MUSHROOM;

   public static final class Configured {
      public static final ResourceKey<ConfiguredFeature<?, ?>> ANCIENT_TREE = ResourceKey.create(
         Registries.CONFIGURED_FEATURE, ResourceLocation.parse("naturesaura:ancient_tree")
      );
      public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_WART_MUSHROOM = ResourceKey.create(
         Registries.CONFIGURED_FEATURE, ResourceLocation.parse("naturesaura:nether_wart_mushroom")
      );
      public static final ResourceKey<ConfiguredFeature<?, ?>> AURA_BLOOM = ResourceKey.create(
         Registries.CONFIGURED_FEATURE, ResourceLocation.parse("naturesaura:aura_bloom")
      );
      public static final ResourceKey<ConfiguredFeature<?, ?>> AURA_CACTUS = ResourceKey.create(
         Registries.CONFIGURED_FEATURE, ResourceLocation.parse("naturesaura:aura_cactus")
      );
      public static final ResourceKey<ConfiguredFeature<?, ?>> WARPED_AURA_MUSHROOM = ResourceKey.create(
         Registries.CONFIGURED_FEATURE, ResourceLocation.parse("naturesaura:warped_aura_mushroom")
      );
      public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_AURA_MUSHROOM = ResourceKey.create(
         Registries.CONFIGURED_FEATURE, ResourceLocation.parse("naturesaura:crimson_aura_mushroom")
      );
      public static final ResourceKey<ConfiguredFeature<?, ?>> AURA_MUSHROOM = ResourceKey.create(
         Registries.CONFIGURED_FEATURE, ResourceLocation.parse("naturesaura:aura_mushroom")
      );

      public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
         FeatureUtils.register(context, AURA_BLOOM, ModFeatures.AURA_BLOOM, NoneFeatureConfiguration.INSTANCE);
         FeatureUtils.register(context, AURA_CACTUS, ModFeatures.AURA_CACTUS, NoneFeatureConfiguration.INSTANCE);
         FeatureUtils.register(context, WARPED_AURA_MUSHROOM, ModFeatures.WARPED_AURA_MUSHROOM, NoneFeatureConfiguration.INSTANCE);
         FeatureUtils.register(context, CRIMSON_AURA_MUSHROOM, ModFeatures.CRIMSON_AURA_MUSHROOM, NoneFeatureConfiguration.INSTANCE);
         FeatureUtils.register(context, AURA_MUSHROOM, ModFeatures.AURA_MUSHROOM, NoneFeatureConfiguration.INSTANCE);
         FeatureUtils.register(context, ANCIENT_TREE, ModFeatures.ANCIENT_TREE, new TreeConfigurationBuilder(null, null, null, null, null).build());
         FeatureUtils.register(context, NETHER_WART_MUSHROOM, ModFeatures.NETHER_WART_MUSHROOM, NoneFeatureConfiguration.INSTANCE);
      }
   }

   public static final class Placed {
      public static final ResourceKey<PlacedFeature> AURA_BLOOM = ResourceKey.create(
         Registries.PLACED_FEATURE, ResourceLocation.parse("naturesaura:aura_bloom")
      );
      public static final ResourceKey<PlacedFeature> AURA_CACTUS = ResourceKey.create(
         Registries.PLACED_FEATURE, ResourceLocation.parse("naturesaura:aura_cactus")
      );
      public static final ResourceKey<PlacedFeature> WARPED_AURA_MUSHROOM = ResourceKey.create(
         Registries.PLACED_FEATURE, ResourceLocation.parse("naturesaura:warped_aura_mushroom")
      );
      public static final ResourceKey<PlacedFeature> CRIMSON_AURA_MUSHROOM = ResourceKey.create(
         Registries.PLACED_FEATURE, ResourceLocation.parse("naturesaura:crimson_aura_mushroom")
      );
      public static final ResourceKey<PlacedFeature> AURA_MUSHROOM = ResourceKey.create(
         Registries.PLACED_FEATURE, ResourceLocation.parse("naturesaura:aura_mushroom")
      );

      public static void bootstrap(BootstrapContext<PlacedFeature> context) {
         HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
         PlacementUtils.register(context, AURA_BLOOM, holdergetter.getOrThrow(ModFeatures.Configured.AURA_BLOOM), new PlacementModifier[0]);
         PlacementUtils.register(context, AURA_CACTUS, holdergetter.getOrThrow(ModFeatures.Configured.AURA_CACTUS), new PlacementModifier[0]);
         PlacementUtils.register(context, WARPED_AURA_MUSHROOM, holdergetter.getOrThrow(ModFeatures.Configured.WARPED_AURA_MUSHROOM), new PlacementModifier[0]);
         PlacementUtils.register(
            context, CRIMSON_AURA_MUSHROOM, holdergetter.getOrThrow(ModFeatures.Configured.CRIMSON_AURA_MUSHROOM), new PlacementModifier[0]
         );
         PlacementUtils.register(context, AURA_MUSHROOM, holdergetter.getOrThrow(ModFeatures.Configured.AURA_MUSHROOM), new PlacementModifier[0]);
      }
   }
}
