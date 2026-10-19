package com.blackgear.vanillabackport.common.worldgen.placements;

import com.blackgear.platform.common.worldgen.WorldGenRegistry;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.common.worldgen.features.TheGardenAwakensFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

public class TheGardenAwakensPlacements {
   public static final WorldGenRegistry<PlacedFeature> FEATURES = WorldGenRegistry.of(Registries.PLACED_FEATURE, "minecraft");
   public static final ResourceKey<PlacedFeature> PALE_OAK_CHECKED = FEATURES.create("pale_oak_checked");
   public static final ResourceKey<PlacedFeature> PALE_OAK_CREAKING_CHECKED = FEATURES.create("pale_oak_creaking_checked");
   public static final ResourceKey<PlacedFeature> FLOWER_PALE_GARDEN = FEATURES.create("flower_pale_garden");
   public static final ResourceKey<PlacedFeature> PALE_GARDEN_VEGETATION = FEATURES.create("pale_garden_vegetation");
   public static final ResourceKey<PlacedFeature> PALE_GARDEN_FLOWERS = FEATURES.create("pale_garden_flowers");
   public static final ResourceKey<PlacedFeature> PALE_MOSS_PATCH = FEATURES.create("pale_moss_patch");

   public static void bootstrap(BootstrapContext<PlacedFeature> context) {
      HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
      FEATURES.register(
         context,
         PALE_OAK_CHECKED,
         features.getOrThrow(TheGardenAwakensFeatures.PALE_OAK),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival(ModBlocks.PALE_OAK_SAPLING.get())}
      );
      FEATURES.register(
         context,
         PALE_OAK_CREAKING_CHECKED,
         features.getOrThrow(TheGardenAwakensFeatures.PALE_OAK_CREAKING),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival(ModBlocks.PALE_OAK_SAPLING.get())}
      );
      FEATURES.register(
         context,
         FLOWER_PALE_GARDEN,
         features.getOrThrow(TheGardenAwakensFeatures.FLOWER_PALE_GARDEN),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()}
      );
      FEATURES.register(
         context,
         PALE_GARDEN_VEGETATION,
         features.getOrThrow(TheGardenAwakensFeatures.PALE_GARDEN_VEGETATION),
         new PlacementModifier[]{
            CountPlacement.of(16),
            InSquarePlacement.spread(),
            SurfaceWaterDepthFilter.forMaxDepth(0),
            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
            BiomeFilter.biome()
         }
      );
      FEATURES.register(
         context,
         PALE_GARDEN_FLOWERS,
         features.getOrThrow(TheGardenAwakensFeatures.PALE_GARDEN_FLOWERS),
         new PlacementModifier[]{
            RarityFilter.onAverageOnceEvery(8),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING_NO_LEAVES),
            BiomeFilter.biome()
         }
      );
      FEATURES.register(
         context,
         PALE_MOSS_PATCH,
         features.getOrThrow(TheGardenAwakensFeatures.PALE_MOSS_PATCH),
         new PlacementModifier[]{
            CountPlacement.of(1), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING_NO_LEAVES), BiomeFilter.biome()
         }
      );
   }
}
