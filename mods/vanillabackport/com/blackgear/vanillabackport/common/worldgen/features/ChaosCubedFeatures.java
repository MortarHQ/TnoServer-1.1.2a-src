package com.blackgear.vanillabackport.common.worldgen.features;

import com.blackgear.platform.common.worldgen.WorldGenRegistry;
import com.blackgear.vanillabackport.common.level.blocks.PotentSulfurBlock;
import com.blackgear.vanillabackport.common.level.blocks.states.PotentSulfurState;
import com.blackgear.vanillabackport.common.level.features.SpeleothemClusterConfiguration;
import com.blackgear.vanillabackport.common.level.features.SpeleothemConfiguration;
import com.blackgear.vanillabackport.common.level.features.TemplateFeatureConfiguration;
import com.blackgear.vanillabackport.common.level.features.WeightedRandomFeatureConfiguration;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.common.registries.ModFeatures;
import com.blackgear.vanillabackport.core.data.tags.ModBlockTags;
import com.blackgear.vanillabackport.core.util.valueproviders.TrapezoidInt;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ChaosCubedFeatures {
   public static final WorldGenRegistry<ConfiguredFeature<?, ?>> FEATURES = WorldGenRegistry.of(Registries.CONFIGURED_FEATURE, "minecraft");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_POOL = FEATURES.create("sulfur_pool");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_SPRING = FEATURES.create("sulfur_spring");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ROOTED_SULFUR_SPRING = FEATURES.create("rooted_sulfur_spring");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_SPIKE_CLUSTER = FEATURES.create("sulfur_spike_cluster");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_SPIKE = FEATURES.create("sulfur_spike");

   public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
      HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
      HolderGetter<PlacedFeature> placements = context.lookup(Registries.PLACED_FEATURE);
      FEATURES.register(
         context,
         SULFUR_POOL,
         ModFeatures.SEQUENCE.get(),
         new SimpleRandomFeatureConfiguration(
            HolderSet.direct(
               new Holder[]{
                  PlacementUtils.inlinePlaced(
                     Feature.LAKE,
                     new Configuration(
                        BlockStateProvider.simple(Blocks.WATER.defaultBlockState()), BlockStateProvider.simple(ModBlocks.SULFUR.get().defaultBlockState())
                     ),
                     new PlacementModifier[0]
                  ),
                  PlacementUtils.inlinePlaced(
                     Feature.SIMPLE_BLOCK,
                     new SimpleBlockConfiguration(
                        BlockStateProvider.simple(
                           (BlockState)ModBlocks.POTENT_SULFUR.get().defaultBlockState().setValue(PotentSulfurBlock.STATE, PotentSulfurState.WET)
                        )
                     ),
                     new PlacementModifier[]{
                        EnvironmentScanPlacement.scanningFor(
                           Direction.DOWN,
                           BlockPredicate.allOf(BlockPredicate.solid(), BlockPredicate.matchesFluids(Direction.UP.getNormal(), new Fluid[]{Fluids.WATER})),
                           4
                        )
                     }
                  )
               }
            )
         )
      );
      BiFunction<Integer, Integer, Holder<PlacedFeature>> tuffCover = (count, spread) -> PlacementUtils.inlinePlaced(
         Feature.SIMPLE_BLOCK,
         new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.TUFF)),
         new PlacementModifier[]{
            CountPlacement.of(count),
            RandomOffsetPlacement.of(TrapezoidInt.triangle(spread), TrapezoidInt.triangle(3)),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), 4),
            BlockPredicateFilter.forPredicate(BlockPredicate.solid())
         }
      );
      Function<SimpleWeightedRandomList<TemplateFeatureConfiguration.TemplateEntry>, Holder<PlacedFeature>> sulfurSprings = entries -> PlacementUtils.inlinePlaced(
         ModFeatures.TEMPLATE.get(), new TemplateFeatureConfiguration(entries), new PlacementModifier[]{RandomOffsetPlacement.vertical(ConstantInt.of(-7))}
      );
      FEATURES.register(
         context,
         SULFUR_SPRING,
         ModFeatures.WEIGHTED_RANDOM_SELECTOR.get(),
         new WeightedRandomFeatureConfiguration(
            SimpleWeightedRandomList.builder()
               .add(
                  PlacementUtils.inlinePlaced(
                     ModFeatures.SEQUENCE.get(),
                     new SimpleRandomFeatureConfiguration(
                        HolderSet.direct(
                           new Holder[]{
                              tuffCover.apply(64, 7),
                              sulfurSprings.apply(
                                 SimpleWeightedRandomList.builder()
                                    .add(
                                       TemplateFeatureConfiguration.TemplateEntry.of(ResourceLocation.withDefaultNamespace("spring/sulfur_spring_small_1")), 1
                                    )
                                    .add(
                                       TemplateFeatureConfiguration.TemplateEntry.of(ResourceLocation.withDefaultNamespace("spring/sulfur_spring_small_2")), 1
                                    )
                                    .add(
                                       TemplateFeatureConfiguration.TemplateEntry.of(ResourceLocation.withDefaultNamespace("spring/sulfur_spring_small_3")), 1
                                    )
                                    .add(
                                       TemplateFeatureConfiguration.TemplateEntry.of(ResourceLocation.withDefaultNamespace("spring/sulfur_spring_small_4")), 1
                                    )
                                    .build()
                              )
                           }
                        )
                     ),
                     new PlacementModifier[0]
                  ),
                  200
               )
               .add(
                  PlacementUtils.inlinePlaced(
                     ModFeatures.SEQUENCE.get(),
                     new SimpleRandomFeatureConfiguration(
                        HolderSet.direct(
                           new Holder[]{
                              tuffCover.apply(80, 8),
                              sulfurSprings.apply(
                                 SimpleWeightedRandomList.builder()
                                    .add(
                                       TemplateFeatureConfiguration.TemplateEntry.of(ResourceLocation.withDefaultNamespace("spring/sulfur_spring_medium_1")), 1
                                    )
                                    .add(
                                       TemplateFeatureConfiguration.TemplateEntry.of(ResourceLocation.withDefaultNamespace("spring/sulfur_spring_medium_2")), 1
                                    )
                                    .add(
                                       TemplateFeatureConfiguration.TemplateEntry.of(ResourceLocation.withDefaultNamespace("spring/sulfur_spring_medium_3")), 1
                                    )
                                    .build()
                              )
                           }
                        )
                     ),
                     new PlacementModifier[0]
                  ),
                  90
               )
               .add(
                  PlacementUtils.inlinePlaced(
                     ModFeatures.SEQUENCE.get(),
                     new SimpleRandomFeatureConfiguration(
                        HolderSet.direct(
                           new Holder[]{
                              tuffCover.apply(96, 9),
                              sulfurSprings.apply(
                                 SimpleWeightedRandomList.builder()
                                    .add(
                                       TemplateFeatureConfiguration.TemplateEntry.of(ResourceLocation.withDefaultNamespace("spring/sulfur_spring_large_1")), 1
                                    )
                                    .add(
                                       TemplateFeatureConfiguration.TemplateEntry.of(ResourceLocation.withDefaultNamespace("spring/sulfur_spring_large_2")), 1
                                    )
                                    .build()
                              )
                           }
                        )
                     ),
                     new PlacementModifier[0]
                  ),
                  20
               )
               .add(
                  PlacementUtils.inlinePlaced(
                     ModFeatures.SEQUENCE.get(),
                     new SimpleRandomFeatureConfiguration(
                        HolderSet.direct(
                           new Holder[]{
                              tuffCover.apply(128, 10),
                              sulfurSprings.apply(
                                 SimpleWeightedRandomList.single(
                                    TemplateFeatureConfiguration.TemplateEntry.of(ResourceLocation.withDefaultNamespace("spring/sulfur_spring_extra_large_1"))
                                 )
                              )
                           }
                        )
                     ),
                     new PlacementModifier[0]
                  ),
                  5
               )
               .build()
         )
      );
      FEATURES.register(
         context,
         ROOTED_SULFUR_SPRING,
         Feature.ROOT_SYSTEM,
         new RootSystemConfiguration(
            PlacementUtils.inlinePlaced(features.getOrThrow(SULFUR_SPRING), new PlacementModifier[0]),
            5,
            3,
            BlockTags.AZALEA_ROOT_REPLACEABLE,
            BlockStateProvider.simple(ModBlocks.SULFUR.get()),
            20,
            184,
            1,
            1,
            BlockStateProvider.simple(ModBlocks.SULFUR.get()),
            1,
            1,
            BlockPredicate.ONLY_IN_AIR_PREDICATE
         )
      );
      FEATURES.register(
         context,
         SULFUR_SPIKE_CLUSTER,
         ModFeatures.SPELEOTHEM_CLUSTER.get(),
         new SpeleothemClusterConfiguration(
            ModBlocks.SULFUR.get().defaultBlockState(),
            ModBlocks.SULFUR_SPIKE.get().defaultBlockState(),
            ModBlockTags.SULFUR_SPIKE_REPLACEABLE,
            12,
            UniformInt.of(1, 4),
            UniformInt.of(2, 8),
            1,
            3,
            UniformInt.of(2, 4),
            UniformFloat.of(0.3F, 0.7F),
            ConstantFloat.ZERO,
            0.1F,
            3,
            8
         )
      );
      FEATURES.register(
         context,
         SULFUR_SPIKE,
         Feature.SIMPLE_RANDOM_SELECTOR,
         new SimpleRandomFeatureConfiguration(
            HolderSet.direct(
               new Holder[]{
                  PlacementUtils.inlinePlaced(
                     ModFeatures.SPELEOTHEM.get(),
                     new SpeleothemConfiguration(
                        ModBlocks.SULFUR.get().defaultBlockState(),
                        ModBlocks.SULFUR_SPIKE.get().defaultBlockState(),
                        ModBlockTags.SULFUR_SPIKE_REPLACEABLE,
                        0.2F,
                        0.7F,
                        0.5F,
                        0.5F
                     ),
                     new PlacementModifier[]{
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1))
                     }
                  ),
                  PlacementUtils.inlinePlaced(
                     ModFeatures.SPELEOTHEM.get(),
                     new SpeleothemConfiguration(
                        ModBlocks.SULFUR.get().defaultBlockState(),
                        ModBlocks.SULFUR_SPIKE.get().defaultBlockState(),
                        ModBlockTags.SULFUR_SPIKE_REPLACEABLE,
                        0.2F,
                        0.7F,
                        0.5F,
                        0.5F
                     ),
                     new PlacementModifier[]{
                        EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(-1))
                     }
                  )
               }
            )
         )
      );
   }
}
