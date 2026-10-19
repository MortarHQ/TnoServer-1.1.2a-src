package net.regions_unexplored.world.surface;

import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.data.RUBiomes;
import net.regions_unexplored.registry.data.RUNoises;

public class RUSurfaceRuleBuilder {
   private static final RuleSource AIR = makeStateRule(Blocks.CAVE_AIR);
   private static final RuleSource LAVA = makeStateRule(Blocks.LAVA);
   private static final RuleSource NETHERRACK = makeStateRule(Blocks.NETHERRACK);
   private static final RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
   private static final RuleSource END_STONE = makeStateRule(Blocks.END_STONE);
   private static final RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
   private static final RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
   private static final RuleSource BRIMSPROUT_NYLIUM = makeStateRule(RUBlocks.BRIMSPROUT_NYLIUM.get());
   private static final RuleSource MYCOTOXIC_NYLIUM = makeStateRule(RUBlocks.MYCOTOXIC_NYLIUM.get());
   private static final RuleSource GLISTERING_NYLIUM = makeStateRule(RUBlocks.GLISTERING_NYLIUM.get());
   private static final RuleSource GLISTERING_WART = makeStateRule(RUBlocks.GLISTERING_WART.get());
   private static final RuleSource COBALT_NYLIUM = makeStateRule(RUBlocks.COBALT_NYLIUM.get());
   private static final RuleSource SOUL_SAND = makeStateRule(Blocks.SOUL_SAND);

   private static RuleSource makeStateRule(Block block) {
      return SurfaceRules.state(block.defaultBlockState());
   }

   public static RuleSource nether() {
      ConditionSource above31 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(31), 0);
      ConditionSource above32 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(32), 0);
      ConditionSource start30 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0);
      ConditionSource end35 = SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0));
      ConditionSource belowTop5 = SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0);
      ConditionSource hole = SurfaceRules.hole();
      ConditionSource soulSandLayerNoise = SurfaceRules.noiseCondition(Noises.SOUL_SAND_LAYER, -0.012);
      ConditionSource gravelLayerNoise = SurfaceRules.noiseCondition(Noises.GRAVEL_LAYER, -0.012);
      ConditionSource patchNoise = SurfaceRules.noiseCondition(Noises.PATCH, -0.012);
      ConditionSource netherrackNoise = SurfaceRules.noiseCondition(Noises.NETHERRACK, 0.54);
      ConditionSource wartNoise = SurfaceRules.noiseCondition(Noises.NETHER_WART, 1.17);
      ConditionSource stateSelectorNoise = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0);
      RuleSource gravelPatch = SurfaceRules.ifTrue(patchNoise, SurfaceRules.ifTrue(start30, SurfaceRules.ifTrue(end35, GRAVEL)));
      return SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
            SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK),
            SurfaceRules.ifTrue(belowTop5, NETHERRACK),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{RUBiomes.INFERNAL_HOLT}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_CEILING,
                        SurfaceRules.ifTrue(stateSelectorNoise, SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.UNDER_FLOOR), BLACKSTONE))
                     ),
                     SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.ifTrue(netherrackNoise, BLACKSTONE))
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.ON_FLOOR,
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(SurfaceRules.not(above32), SurfaceRules.ifTrue(hole, LAVA)),
                     SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(new ResourceKey[]{RUBiomes.BLACKSTONE_BASIN}),
                        SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(netherrackNoise, BLACKSTONE), SurfaceRules.ifTrue(above31, COBALT_NYLIUM)})
                     ),
                     SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(new ResourceKey[]{RUBiomes.GLISTERING_MEADOW}),
                        SurfaceRules.ifTrue(
                           SurfaceRules.not(SurfaceRules.noiseCondition(Noises.NETHERRACK, 0.45)),
                           SurfaceRules.ifTrue(
                              above31, SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(wartNoise, GLISTERING_WART), GLISTERING_NYLIUM})
                           )
                        )
                     ),
                     SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(new ResourceKey[]{RUBiomes.MYCOTOXIC_UNDERGROWTH}),
                        SurfaceRules.ifTrue(
                           SurfaceRules.not(netherrackNoise),
                           SurfaceRules.ifTrue(above31, SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(wartNoise, NETHERRACK), MYCOTOXIC_NYLIUM}))
                        )
                     ),
                     SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(new ResourceKey[]{RUBiomes.INFERNAL_HOLT}),
                        SurfaceRules.sequence(
                           new RuleSource[]{SurfaceRules.ifTrue(netherrackNoise, BLACKSTONE), SurfaceRules.ifTrue(above31, BRIMSPROUT_NYLIUM)}
                        )
                     )
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{RUBiomes.BLACKSTONE_BASIN}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, SurfaceRules.ifTrue(stateSelectorNoise, BLACKSTONE)),
                     SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, BLACKSTONE)
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.isBiome(new ResourceKey[]{RUBiomes.REMOVED_REDSTONE_ABYSS}),
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.ifTrue(
                           soulSandLayerNoise,
                           SurfaceRules.sequence(
                              new RuleSource[]{
                                 SurfaceRules.ifTrue(SurfaceRules.not(hole), SurfaceRules.ifTrue(start30, SurfaceRules.ifTrue(end35, SOUL_SAND))), NETHERRACK
                              }
                           )
                        )
                     ),
                     SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(
                           above31,
                           SurfaceRules.ifTrue(
                              end35,
                              SurfaceRules.ifTrue(
                                 gravelLayerNoise,
                                 SurfaceRules.sequence(
                                    new RuleSource[]{SurfaceRules.ifTrue(above32, GRAVEL), SurfaceRules.ifTrue(SurfaceRules.not(hole), GRAVEL)}
                                 )
                              )
                           )
                        )
                     )
                  }
               )
            )
         }
      );
   }

   private static RuleSource block(Supplier<Block> block) {
      return SurfaceRules.state(block.get().defaultBlockState());
   }

   public static RuleSource end() {
      return END_STONE;
   }

   public static RuleSource air() {
      return AIR;
   }

   private static ConditionSource noiseAbove(ResourceKey<NoiseParameters> noise, double min) {
      return SurfaceRules.noiseCondition(noise, min / 8.25, Double.MAX_VALUE);
   }

   private static ConditionSource noiseBetween(ResourceKey<NoiseParameters> noise, double min, double max) {
      return SurfaceRules.noiseCondition(noise, min / 8.25, max / 8.25);
   }

   private static ConditionSource surfaceNoiseAbove(double noise) {
      return SurfaceRules.noiseCondition(Noises.SURFACE, noise / 8.25, Double.MAX_VALUE);
   }

   private static ConditionSource shieldNoise(double min, double max) {
      return SurfaceRules.noiseCondition(RUNoises.SHIELD, min / 8.25, max / 8.25);
   }

   private static ConditionSource shieldNoise(double noise) {
      return SurfaceRules.noiseCondition(RUNoises.SHIELD, noise / 8.25, Double.MAX_VALUE);
   }
}
