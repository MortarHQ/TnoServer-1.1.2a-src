package terrablender.worldgen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import terrablender.api.SurfaceRuleManager;

public class TBSurfaceRuleData {
   private static final RuleSource AIR = makeStateRule(Blocks.AIR);
   private static final RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
   private static final RuleSource WHITE_TERRACOTTA = makeStateRule(Blocks.WHITE_TERRACOTTA);
   private static final RuleSource ORANGE_TERRACOTTA = makeStateRule(Blocks.ORANGE_TERRACOTTA);
   private static final RuleSource TERRACOTTA = makeStateRule(Blocks.TERRACOTTA);
   private static final RuleSource RED_SAND = makeStateRule(Blocks.RED_SAND);
   private static final RuleSource RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
   private static final RuleSource STONE = makeStateRule(Blocks.STONE);
   private static final RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
   private static final RuleSource DIRT = makeStateRule(Blocks.DIRT);
   private static final RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
   private static final RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
   private static final RuleSource MYCELIUM = makeStateRule(Blocks.MYCELIUM);
   private static final RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
   private static final RuleSource CALCITE = makeStateRule(Blocks.CALCITE);
   private static final RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
   private static final RuleSource SAND = makeStateRule(Blocks.SAND);
   private static final RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
   private static final RuleSource PACKED_ICE = makeStateRule(Blocks.PACKED_ICE);
   private static final RuleSource SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
   private static final RuleSource MUD = makeStateRule(Blocks.MUD);
   private static final RuleSource POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);
   private static final RuleSource ICE = makeStateRule(Blocks.ICE);
   private static final RuleSource WATER = makeStateRule(Blocks.WATER);
   private static final RuleSource LAVA = makeStateRule(Blocks.LAVA);
   private static final RuleSource NETHERRACK = makeStateRule(Blocks.NETHERRACK);
   private static final RuleSource SOUL_SAND = makeStateRule(Blocks.SOUL_SAND);
   private static final RuleSource SOUL_SOIL = makeStateRule(Blocks.SOUL_SOIL);
   private static final RuleSource BASALT = makeStateRule(Blocks.BASALT);
   private static final RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
   private static final RuleSource WARPED_WART_BLOCK = makeStateRule(Blocks.WARPED_WART_BLOCK);
   private static final RuleSource WARPED_NYLIUM = makeStateRule(Blocks.WARPED_NYLIUM);
   private static final RuleSource NETHER_WART_BLOCK = makeStateRule(Blocks.NETHER_WART_BLOCK);
   private static final RuleSource CRIMSON_NYLIUM = makeStateRule(Blocks.CRIMSON_NYLIUM);
   private static final RuleSource ENDSTONE = makeStateRule(Blocks.END_STONE);

   private static RuleSource makeStateRule(Block block) {
      return SurfaceRules.state(block.defaultBlockState());
   }

   public static RuleSource overworld() {
      return overworldLike(true, false, true);
   }

   public static RuleSource overworldLike(boolean checkAbovePreliminarySurface, boolean bedrockRoof, boolean bedrockFloor) {
      ConditionSource isBlockAboveY97WithVariationAbove = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(97), 2);
      ConditionSource isBlockAboveY256 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(256), 0);
      ConditionSource isSurfaceAbove63WithVariationBelow = SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1);
      ConditionSource isSurfaceAboveY74WithVariationAbove = SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1);
      ConditionSource isBlockAboveY60 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0);
      ConditionSource isBlockAboveY62 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
      ConditionSource isBlockAboveY63 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
      ConditionSource isInOrAboveShallowWater = SurfaceRules.waterBlockCheck(-1, 0);
      ConditionSource isAboveWater = SurfaceRules.waterBlockCheck(0, 0);
      ConditionSource isInOrAboveDeepWaterWithVariationBelow = SurfaceRules.waterStartCheck(-6, -1);
      ConditionSource isHole = SurfaceRules.hole();
      ConditionSource isFrozenOcean = SurfaceRules.isBiome(new ResourceKey[]{Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN});
      ConditionSource isSteep = SurfaceRules.steep();
      RuleSource surfaceGrassOrDirtIfSubmerged = SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(isAboveWater, GRASS_BLOCK), DIRT});
      RuleSource sandOrSandstoneCeiling = SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SAND});
      RuleSource gravelOrStoneCeiling = SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE), GRAVEL});
      ConditionSource isSandyShoreOrOcean = SurfaceRules.isBiome(new ResourceKey[]{Biomes.WARM_OCEAN, Biomes.BEACH, Biomes.SNOWY_BEACH});
      ConditionSource isDesert = SurfaceRules.isBiome(new ResourceKey[]{Biomes.DESERT});
      RuleSource onAndUnderFloorSurfaceRules = SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.STONY_PEAKS}),
               SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.CALCITE, -0.0125, 0.0125), CALCITE), STONE})
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.STONY_SHORE}),
               SurfaceRules.sequence(
                  new RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.GRAVEL, -0.05, 0.05), gravelOrStoneCeiling), STONE}
               )
            ),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{Biomes.WINDSWEPT_HILLS}), SurfaceRules.ifTrue(surfaceNoiseAbove(1.0), STONE)),
            SurfaceRules.ifTrue(isSandyShoreOrOcean, sandOrSandstoneCeiling),
            SurfaceRules.ifTrue(isDesert, sandOrSandstoneCeiling),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{Biomes.DRIPSTONE_CAVES}), STONE)
         }
      );
      RuleSource powderedSnowSmallPatches = SurfaceRules.ifTrue(
         SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.45, 0.58), SurfaceRules.ifTrue(isAboveWater, POWDER_SNOW)
      );
      RuleSource powderedSnowLargePatches = SurfaceRules.ifTrue(
         SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35, 0.6), SurfaceRules.ifTrue(isAboveWater, POWDER_SNOW)
      );
      RuleSource underFloorSurfaceRules = SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.FROZEN_PEAKS}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(isSteep, PACKED_ICE),
                     SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PACKED_ICE, -0.5, 0.2), PACKED_ICE),
                     SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, -0.0625, 0.025), ICE),
                     SurfaceRules.ifTrue(isAboveWater, SNOW_BLOCK)
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.SNOWY_SLOPES}),
               SurfaceRules.sequence(
                  new RuleSource[]{SurfaceRules.ifTrue(isSteep, STONE), powderedSnowSmallPatches, SurfaceRules.ifTrue(isAboveWater, SNOW_BLOCK)}
               )
            ),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{Biomes.JAGGED_PEAKS}), STONE),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{Biomes.GROVE}), SurfaceRules.sequence(new RuleSource[]{powderedSnowSmallPatches, DIRT})),
            onAndUnderFloorSurfaceRules,
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{Biomes.WINDSWEPT_SAVANNA}), SurfaceRules.ifTrue(surfaceNoiseAbove(1.75), STONE)),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.WINDSWEPT_GRAVELLY_HILLS}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(surfaceNoiseAbove(2.0), gravelOrStoneCeiling),
                     SurfaceRules.ifTrue(surfaceNoiseAbove(1.0), STONE),
                     SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0), DIRT),
                     gravelOrStoneCeiling
                  }
               )
            ),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{Biomes.MANGROVE_SWAMP}), MUD),
            DIRT
         }
      );
      RuleSource shallowFloorSurfaceRules = SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.FROZEN_PEAKS}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(isSteep, PACKED_ICE),
                     SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PACKED_ICE, 0.0, 0.2), PACKED_ICE),
                     SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.025), ICE),
                     SurfaceRules.ifTrue(isAboveWater, SNOW_BLOCK)
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.SNOWY_SLOPES}),
               SurfaceRules.sequence(
                  new RuleSource[]{SurfaceRules.ifTrue(isSteep, STONE), powderedSnowLargePatches, SurfaceRules.ifTrue(isAboveWater, SNOW_BLOCK)}
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.JAGGED_PEAKS}),
               SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(isSteep, STONE), SurfaceRules.ifTrue(isAboveWater, SNOW_BLOCK)})
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.GROVE}),
               SurfaceRules.sequence(new RuleSource[]{powderedSnowLargePatches, SurfaceRules.ifTrue(isAboveWater, SNOW_BLOCK)})
            ),
            onAndUnderFloorSurfaceRules,
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.WINDSWEPT_SAVANNA}),
               SurfaceRules.sequence(
                  new RuleSource[]{SurfaceRules.ifTrue(surfaceNoiseAbove(1.75), STONE), SurfaceRules.ifTrue(surfaceNoiseAbove(-0.5), COARSE_DIRT)}
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.WINDSWEPT_GRAVELLY_HILLS}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(surfaceNoiseAbove(2.0), gravelOrStoneCeiling),
                     SurfaceRules.ifTrue(surfaceNoiseAbove(1.0), STONE),
                     SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0), surfaceGrassOrDirtIfSubmerged),
                     gravelOrStoneCeiling
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA}),
               SurfaceRules.sequence(
                  new RuleSource[]{SurfaceRules.ifTrue(surfaceNoiseAbove(1.75), COARSE_DIRT), SurfaceRules.ifTrue(surfaceNoiseAbove(-0.95), PODZOL)}
               )
            ),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{Biomes.ICE_SPIKES}), SurfaceRules.ifTrue(isAboveWater, SNOW_BLOCK)),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{Biomes.MANGROVE_SWAMP}), MUD),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{Biomes.MUSHROOM_FIELDS}), MYCELIUM),
            surfaceGrassOrDirtIfSubmerged
         }
      );
      ConditionSource isSuitableSurfaceNoiseLower = SurfaceRules.noiseCondition(Noises.SURFACE, -0.909, -0.5454);
      ConditionSource isSuitableSurfaceNoiseMid = SurfaceRules.noiseCondition(Noises.SURFACE, -0.1818, 0.1818);
      ConditionSource isSuitableSurfaceNoiseUpper = SurfaceRules.noiseCondition(Noises.SURFACE, 0.5454, 0.909);
      RuleSource surfaceRules = SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(
               SurfaceRules.ON_FLOOR,
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(new ResourceKey[]{Biomes.WOODED_BADLANDS}),
                        SurfaceRules.ifTrue(
                           isBlockAboveY97WithVariationAbove,
                           SurfaceRules.sequence(
                              new RuleSource[]{
                                 SurfaceRules.ifTrue(isSuitableSurfaceNoiseLower, COARSE_DIRT),
                                 SurfaceRules.ifTrue(isSuitableSurfaceNoiseMid, COARSE_DIRT),
                                 SurfaceRules.ifTrue(isSuitableSurfaceNoiseUpper, COARSE_DIRT),
                                 surfaceGrassOrDirtIfSubmerged
                              }
                           )
                        )
                     ),
                     SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(new ResourceKey[]{Biomes.SWAMP}),
                        SurfaceRules.ifTrue(
                           isBlockAboveY62,
                           SurfaceRules.ifTrue(SurfaceRules.not(isBlockAboveY63), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER))
                        )
                     ),
                     SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(new ResourceKey[]{Biomes.MANGROVE_SWAMP}),
                        SurfaceRules.ifTrue(
                           isBlockAboveY60,
                           SurfaceRules.ifTrue(SurfaceRules.not(isBlockAboveY63), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER))
                        )
                     )
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                           new RuleSource[]{
                              SurfaceRules.ifTrue(isBlockAboveY256, ORANGE_TERRACOTTA),
                              SurfaceRules.ifTrue(
                                 isSurfaceAboveY74WithVariationAbove,
                                 SurfaceRules.sequence(
                                    new RuleSource[]{
                                       SurfaceRules.ifTrue(isSuitableSurfaceNoiseLower, TERRACOTTA),
                                       SurfaceRules.ifTrue(isSuitableSurfaceNoiseMid, TERRACOTTA),
                                       SurfaceRules.ifTrue(isSuitableSurfaceNoiseUpper, TERRACOTTA),
                                       SurfaceRules.bandlands()
                                    }
                                 )
                              ),
                              SurfaceRules.ifTrue(
                                 isInOrAboveShallowWater,
                                 SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, RED_SANDSTONE), RED_SAND})
                              ),
                              SurfaceRules.ifTrue(SurfaceRules.not(isHole), ORANGE_TERRACOTTA),
                              SurfaceRules.ifTrue(isInOrAboveDeepWaterWithVariationBelow, WHITE_TERRACOTTA),
                              gravelOrStoneCeiling
                           }
                        )
                     ),
                     SurfaceRules.ifTrue(
                        isSurfaceAbove63WithVariationBelow,
                        SurfaceRules.sequence(
                           new RuleSource[]{
                              SurfaceRules.ifTrue(
                                 isBlockAboveY63, SurfaceRules.ifTrue(SurfaceRules.not(isSurfaceAboveY74WithVariationAbove), ORANGE_TERRACOTTA)
                              ),
                              SurfaceRules.bandlands()
                           }
                        )
                     ),
                     SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.ifTrue(isInOrAboveDeepWaterWithVariationBelow, WHITE_TERRACOTTA))
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.ON_FLOOR,
               SurfaceRules.ifTrue(
                  isInOrAboveShallowWater,
                  SurfaceRules.sequence(
                     new RuleSource[]{
                        SurfaceRules.ifTrue(
                           isFrozenOcean,
                           SurfaceRules.ifTrue(
                              isHole,
                              SurfaceRules.sequence(
                                 new RuleSource[]{SurfaceRules.ifTrue(isAboveWater, AIR), SurfaceRules.ifTrue(SurfaceRules.temperature(), ICE), WATER}
                              )
                           )
                        ),
                        shallowFloorSurfaceRules
                     }
                  )
               )
            ),
            SurfaceRules.ifTrue(
               isInOrAboveDeepWaterWithVariationBelow,
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(isFrozenOcean, SurfaceRules.ifTrue(isHole, WATER))),
                     SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, underFloorSurfaceRules),
                     SurfaceRules.ifTrue(isSandyShoreOrOcean, SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SANDSTONE)),
                     SurfaceRules.ifTrue(isDesert, SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SANDSTONE))
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.ON_FLOOR,
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS}), STONE),
                     SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(new ResourceKey[]{Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN}), sandOrSandstoneCeiling
                     ),
                     gravelOrStoneCeiling
                  }
               )
            )
         }
      );
      Builder<RuleSource> builder = ImmutableList.builder();
      builder.addAll(
         SurfaceRuleManager.getDefaultSurfaceRuleAdditionsForStage(SurfaceRuleManager.RuleCategory.OVERWORLD, SurfaceRuleManager.RuleStage.BEFORE_BEDROCK)
      );
      if (bedrockRoof) {
         builder.add(
            SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK)
         );
      }

      if (bedrockFloor) {
         builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK));
      }

      List<RuleSource> afterBedrockRules = SurfaceRuleManager.getDefaultSurfaceRuleAdditionsForStage(
         SurfaceRuleManager.RuleCategory.OVERWORLD, SurfaceRuleManager.RuleStage.AFTER_BEDROCK
      );
      if (!afterBedrockRules.isEmpty()) {
         Builder<RuleSource> newSurfaceRules = ImmutableList.builder();
         newSurfaceRules.addAll(afterBedrockRules);
         newSurfaceRules.add(surfaceRules);
         surfaceRules = SurfaceRules.sequence((RuleSource[])newSurfaceRules.build().toArray(RuleSource[]::new));
      }

      RuleSource surfaceRulesWithPreliminarySurfaceCheck = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), surfaceRules);
      builder.add(checkAbovePreliminarySurface ? surfaceRulesWithPreliminarySurfaceCheck : surfaceRules);
      builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), DEEPSLATE));
      return SurfaceRules.sequence((RuleSource[])builder.build().toArray(RuleSource[]::new));
   }

   public static RuleSource nether() {
      ConditionSource isAbove31 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(31), 0);
      ConditionSource surfacerules$conditionsource1 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(32), 0);
      ConditionSource yStart30 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0);
      ConditionSource isBelow35 = SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0));
      ConditionSource isTop5Blocks = SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0);
      ConditionSource isHole = SurfaceRules.hole();
      ConditionSource isSuitableSoulSandNoise = SurfaceRules.noiseCondition(Noises.SOUL_SAND_LAYER, -0.012);
      ConditionSource surfacerules$conditionsource7 = SurfaceRules.noiseCondition(Noises.GRAVEL_LAYER, -0.012);
      ConditionSource isSuitablePatchNoise = SurfaceRules.noiseCondition(Noises.PATCH, -0.012);
      ConditionSource isSuitableNetherrackNoise = SurfaceRules.noiseCondition(Noises.NETHERRACK, 0.54);
      ConditionSource surfacerules$conditionsource10 = SurfaceRules.noiseCondition(Noises.NETHER_WART, 1.17);
      ConditionSource isStateSelectorNoiseSuitable = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0);
      RuleSource gravelPatchRules = SurfaceRules.ifTrue(isSuitablePatchNoise, SurfaceRules.ifTrue(yStart30, SurfaceRules.ifTrue(isBelow35, GRAVEL)));
      RuleSource bedrockRules = SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
            SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK)
         }
      );
      RuleSource surfaceRules = SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(isTop5Blocks, NETHERRACK),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.BASALT_DELTAS}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, BASALT),
                     SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.sequence(new RuleSource[]{gravelPatchRules, SurfaceRules.ifTrue(isStateSelectorNoiseSuitable, BASALT), BLACKSTONE})
                     )
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.SOUL_SAND_VALLEY}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_CEILING,
                        SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(isStateSelectorNoiseSuitable, SOUL_SAND), SOUL_SOIL})
                     ),
                     SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.sequence(new RuleSource[]{gravelPatchRules, SurfaceRules.ifTrue(isStateSelectorNoiseSuitable, SOUL_SAND), SOUL_SOIL})
                     )
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.ON_FLOOR,
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(SurfaceRules.not(surfacerules$conditionsource1), SurfaceRules.ifTrue(isHole, LAVA)),
                     SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(new ResourceKey[]{Biomes.WARPED_FOREST}),
                        SurfaceRules.ifTrue(
                           SurfaceRules.not(isSuitableNetherrackNoise),
                           SurfaceRules.ifTrue(
                              isAbove31,
                              SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(surfacerules$conditionsource10, WARPED_WART_BLOCK), WARPED_NYLIUM})
                           )
                        )
                     ),
                     SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(new ResourceKey[]{Biomes.CRIMSON_FOREST}),
                        SurfaceRules.ifTrue(
                           SurfaceRules.not(isSuitableNetherrackNoise),
                           SurfaceRules.ifTrue(
                              isAbove31,
                              SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(surfacerules$conditionsource10, NETHER_WART_BLOCK), CRIMSON_NYLIUM})
                           )
                        )
                     )
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{Biomes.NETHER_WASTES}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.ifTrue(
                           isSuitableSoulSandNoise,
                           SurfaceRules.sequence(
                              new RuleSource[]{
                                 SurfaceRules.ifTrue(SurfaceRules.not(isHole), SurfaceRules.ifTrue(yStart30, SurfaceRules.ifTrue(isBelow35, SOUL_SAND))),
                                 NETHERRACK
                              }
                           )
                        )
                     ),
                     SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(
                           isAbove31,
                           SurfaceRules.ifTrue(
                              isBelow35,
                              SurfaceRules.ifTrue(
                                 surfacerules$conditionsource7,
                                 SurfaceRules.sequence(
                                    new RuleSource[]{
                                       SurfaceRules.ifTrue(surfacerules$conditionsource1, GRAVEL), SurfaceRules.ifTrue(SurfaceRules.not(isHole), GRAVEL)
                                    }
                                 )
                              )
                           )
                        )
                     )
                  }
               )
            ),
            NETHERRACK
         }
      );
      Builder<RuleSource> builder = ImmutableList.builder();
      builder.addAll(
         SurfaceRuleManager.getDefaultSurfaceRuleAdditionsForStage(SurfaceRuleManager.RuleCategory.NETHER, SurfaceRuleManager.RuleStage.BEFORE_BEDROCK)
      );
      builder.add(bedrockRules);
      builder.addAll(
         SurfaceRuleManager.getDefaultSurfaceRuleAdditionsForStage(SurfaceRuleManager.RuleCategory.NETHER, SurfaceRuleManager.RuleStage.AFTER_BEDROCK)
      );
      builder.add(surfaceRules);
      return SurfaceRules.sequence((RuleSource[])builder.build().toArray(RuleSource[]::new));
   }

   public static RuleSource end() {
      return ENDSTONE;
   }

   public static RuleSource air() {
      return AIR;
   }

   private static ConditionSource surfaceNoiseAbove(double value) {
      return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.25, Double.MAX_VALUE);
   }
}
