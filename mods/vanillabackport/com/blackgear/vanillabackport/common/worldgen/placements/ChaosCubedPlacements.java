package com.blackgear.vanillabackport.common.worldgen.placements;

import com.blackgear.platform.common.worldgen.WorldGenRegistry;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.common.worldgen.features.ChaosCubedFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;

public class ChaosCubedPlacements {
   public static final WorldGenRegistry<PlacedFeature> FEATURES = WorldGenRegistry.of(Registries.PLACED_FEATURE, "minecraft");
   public static final ResourceKey<PlacedFeature> SULFUR_POOL = FEATURES.create("sulfur_pool");
   public static final ResourceKey<PlacedFeature> ROOTED_SULFUR_SPRING = FEATURES.create("rooted_sulfur_spring");
   public static final ResourceKey<PlacedFeature> SULFUR_SPIKE_CLUSTER = FEATURES.create("sulfur_spike_cluster");
   public static final ResourceKey<PlacedFeature> SULFUR_SPIKE = FEATURES.create("sulfur_spike");

   public static void bootstrap(BootstrapContext<PlacedFeature> context) {
      HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
      FEATURES.register(
         context,
         SULFUR_POOL,
         features.getOrThrow(ChaosCubedFeatures.SULFUR_POOL),
         new PlacementModifier[]{
            CountPlacement.of(256),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            BlockPredicateFilter.forPredicate(BlockPredicate.solid()),
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new Block[]{ModBlocks.SULFUR.get()})),
            BiomeFilter.biome()
         }
      );
      FEATURES.register(
         context,
         ROOTED_SULFUR_SPRING,
         features.getOrThrow(ChaosCubedFeatures.ROOTED_SULFUR_SPRING),
         new PlacementModifier[]{
            CountPlacement.of(UniformInt.of(1, 2)),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BiomeFilter.biome()
         }
      );
      FEATURES.register(
         context,
         SULFUR_SPIKE_CLUSTER,
         features.getOrThrow(ChaosCubedFeatures.SULFUR_SPIKE_CLUSTER),
         new PlacementModifier[]{
            CountPlacement.of(UniformInt.of(48, 96)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
         }
      );
      FEATURES.register(
         context,
         SULFUR_SPIKE,
         features.getOrThrow(ChaosCubedFeatures.SULFUR_SPIKE),
         new PlacementModifier[]{
            CountPlacement.of(UniformInt.of(192, 256)),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            CountPlacement.of(UniformInt.of(1, 5)),
            RandomOffsetPlacement.of(ClampedNormalInt.of(0.0F, 3.0F, -10, 10), ClampedNormalInt.of(0.0F, 0.6F, -2, 2)),
            BiomeFilter.biome()
         }
      );
   }
}
