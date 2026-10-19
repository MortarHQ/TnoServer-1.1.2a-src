package com.blackgear.vanillabackport.common.worldgen.placements;

import com.blackgear.platform.common.worldgen.WorldGenRegistry;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.common.worldgen.features.SpringToLifeFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class SpringToLifePlacements {
   public static final WorldGenRegistry<PlacedFeature> FEATURES = WorldGenRegistry.of(Registries.PLACED_FEATURE, "minecraft");
   public static final ResourceKey<PlacedFeature> PATCH_BUSH = FEATURES.create("patch_bush");
   public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH_NEAR_WATER = FEATURES.create("patch_firefly_bush_near_water");
   public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP = FEATURES.create("patch_firefly_bush_near_water_swamp");
   public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH_SWAMP = FEATURES.create("patch_firefly_bush_swamp");
   public static final ResourceKey<PlacedFeature> WILDFLOWERS_BIRCH_FOREST = FEATURES.create("wildflowers_birch_forest");
   public static final ResourceKey<PlacedFeature> WILDFLOWERS_MEADOW = FEATURES.create("wildflowers_meadow");
   public static final ResourceKey<PlacedFeature> PATCH_DRY_GRASS_BADLANDS = FEATURES.create("patch_dry_grass_badlands");
   public static final ResourceKey<PlacedFeature> PATCH_DRY_GRASS_DESERT = FEATURES.create("patch_dry_grass_desert");
   public static final ResourceKey<PlacedFeature> PATCH_LEAF_LITTER = FEATURES.create("patch_leaf_litter");
   public static final ResourceKey<PlacedFeature> LEAF_LITTER = FEATURES.create("leaf_litter");
   public static final ResourceKey<PlacedFeature> CACTUS_FLOWER = FEATURES.create("cactus_flower");
   public static final ResourceKey<PlacedFeature> FALLEN_OAK_TREE = FEATURES.create("fallen_oak_tree");
   public static final ResourceKey<PlacedFeature> FALLEN_BIRCH_TREE = FEATURES.create("fallen_birch_tree");
   public static final ResourceKey<PlacedFeature> FALLEN_SUPER_BIRCH_TREE = FEATURES.create("fallen_super_birch_tree");
   public static final ResourceKey<PlacedFeature> FALLEN_JUNGLE_TREE = FEATURES.create("fallen_jungle_tree");
   public static final ResourceKey<PlacedFeature> FALLEN_SPRUCE_TREE = FEATURES.create("fallen_spruce_tree");
   public static final ResourceKey<PlacedFeature> PLACED_FALLEN_OAK_TREE = FEATURES.create("placed_fallen_oak_tree");
   public static final ResourceKey<PlacedFeature> PLACED_RARE_FALLEN_BIRCH_TREE = FEATURES.create("placed_rare_fallen_birch_tree");
   public static final ResourceKey<PlacedFeature> PLACED_FALLEN_BIRCH_TREE = FEATURES.create("placed_fallen_birch_tree");
   public static final ResourceKey<PlacedFeature> PLACED_FALLEN_SUPER_BIRCH_TREE = FEATURES.create("placed_fallen_super_birch_tree");
   public static final ResourceKey<PlacedFeature> PLACED_FALLEN_JUNGLE_TREE = FEATURES.create("placed_fallen_jungle_tree");
   public static final ResourceKey<PlacedFeature> PLACED_FALLEN_SPRUCE_TREE = FEATURES.create("placed_fallen_spruce_tree");
   public static final ResourceKey<PlacedFeature> PLACED_RARE_FALLEN_SPRUCE_TREE = FEATURES.create("placed_rare_fallen_spruce_tree");

   public static void bootstrap(BootstrapContext<PlacedFeature> context) {
      HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
      Holder<ConfiguredFeature<?, ?>> patch = features.getOrThrow(SpringToLifeFeatures.PATCH_FIREFLY_BUSH);
      FEATURES.register(
         context,
         PATCH_BUSH,
         features.getOrThrow(SpringToLifeFeatures.PATCH_BUSH),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()}
      );
      FEATURES.register(
         context,
         PATCH_FIREFLY_BUSH_NEAR_WATER,
         patch,
         new PlacementModifier[]{
            CountPlacement.of(2),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING_NO_LEAVES),
            BiomeFilter.biome(),
            nearWaterPredicate(ModBlocks.FIREFLY_BUSH.get())
         }
      );
      FEATURES.register(
         context,
         PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP,
         patch,
         new PlacementModifier[]{
            CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), nearWaterPredicate(ModBlocks.FIREFLY_BUSH.get())
         }
      );
      FEATURES.register(
         context,
         PATCH_FIREFLY_BUSH_SWAMP,
         patch,
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()}
      );
      FEATURES.register(
         context,
         WILDFLOWERS_BIRCH_FOREST,
         features.getOrThrow(SpringToLifeFeatures.WILDFLOWERS_BIRCH_FOREST),
         new PlacementModifier[]{
            CountPlacement.of(3), RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
         }
      );
      FEATURES.register(
         context,
         WILDFLOWERS_MEADOW,
         features.getOrThrow(SpringToLifeFeatures.WILDFLOWERS_MEADOW),
         new PlacementModifier[]{NoiseThresholdCountPlacement.of(-0.8, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()}
      );
      FEATURES.register(
         context,
         PATCH_DRY_GRASS_BADLANDS,
         features.getOrThrow(SpringToLifeFeatures.PATCH_DRY_GRASS),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()}
      );
      FEATURES.register(
         context,
         PATCH_DRY_GRASS_DESERT,
         features.getOrThrow(SpringToLifeFeatures.PATCH_DRY_GRASS),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()}
      );
      FEATURES.register(
         context, PATCH_LEAF_LITTER, features.getOrThrow(SpringToLifeFeatures.PATCH_LEAF_LITTER), VegetationPlacements.worldSurfaceSquaredWithCount(2)
      );
      FEATURES.register(
         context,
         FALLEN_OAK_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_OAK_TREE),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)}
      );
      FEATURES.register(
         context,
         FALLEN_BIRCH_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_BIRCH_TREE),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)}
      );
      FEATURES.register(
         context,
         FALLEN_SUPER_BIRCH_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_SUPER_BIRCH_TREE),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)}
      );
      FEATURES.register(
         context,
         FALLEN_SPRUCE_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_SPRUCE_TREE),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)}
      );
      FEATURES.register(
         context,
         FALLEN_JUNGLE_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_JUNGLE_TREE),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival(Blocks.JUNGLE_SAPLING)}
      );
      FEATURES.register(
         context,
         LEAF_LITTER,
         features.getOrThrow(SpringToLifeFeatures.LEAF_LITTER),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(16, 0.1F, 1))
      );
      FEATURES.register(
         context,
         CACTUS_FLOWER,
         features.getOrThrow(SpringToLifeFeatures.CACTUS_FLOWER),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(16, 0.1F, 1))
      );
      FEATURES.register(
         context,
         PLACED_FALLEN_OAK_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_OAK_TREE),
         VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(80), Blocks.OAK_SAPLING)
      );
      FEATURES.register(
         context,
         PLACED_RARE_FALLEN_BIRCH_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_BIRCH_TREE),
         VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(400), Blocks.BIRCH_SAPLING)
      );
      FEATURES.register(
         context,
         PLACED_FALLEN_BIRCH_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_BIRCH_TREE),
         VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(80), Blocks.BIRCH_SAPLING)
      );
      FEATURES.register(
         context,
         PLACED_FALLEN_SUPER_BIRCH_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_SUPER_BIRCH_TREE),
         VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(160), Blocks.BIRCH_SAPLING)
      );
      FEATURES.register(
         context,
         PLACED_FALLEN_JUNGLE_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_JUNGLE_TREE),
         VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(80), Blocks.JUNGLE_SAPLING)
      );
      FEATURES.register(
         context,
         PLACED_FALLEN_SPRUCE_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_SPRUCE_TREE),
         VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(80), Blocks.SPRUCE_SAPLING)
      );
      FEATURES.register(
         context,
         PLACED_RARE_FALLEN_SPRUCE_TREE,
         features.getOrThrow(SpringToLifeFeatures.FALLEN_SPRUCE_TREE),
         VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(120), Blocks.SPRUCE_SAPLING)
      );
   }

   public static BlockPredicateFilter nearWaterPredicate(Block block) {
      return BlockPredicateFilter.forPredicate(
         BlockPredicate.allOf(
            new BlockPredicate[]{
               BlockPredicate.ONLY_IN_AIR_PREDICATE,
               BlockPredicate.wouldSurvive(block.defaultBlockState(), BlockPos.ZERO),
               BlockPredicate.anyOf(
                  new BlockPredicate[]{
                     BlockPredicate.matchesFluids(new BlockPos(1, -1, 0), new Fluid[]{Fluids.WATER, Fluids.FLOWING_WATER}),
                     BlockPredicate.matchesFluids(new BlockPos(-1, -1, 0), new Fluid[]{Fluids.WATER, Fluids.FLOWING_WATER}),
                     BlockPredicate.matchesFluids(new BlockPos(0, -1, 1), new Fluid[]{Fluids.WATER, Fluids.FLOWING_WATER}),
                     BlockPredicate.matchesFluids(new BlockPos(0, -1, -1), new Fluid[]{Fluids.WATER, Fluids.FLOWING_WATER})
                  }
               )
            }
         )
      );
   }
}
