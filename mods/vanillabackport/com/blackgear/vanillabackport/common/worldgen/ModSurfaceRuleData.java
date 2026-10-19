package com.blackgear.vanillabackport.common.worldgen;

import com.blackgear.vanillabackport.common.registries.ModBiomes;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.common.registries.ModNoises;
import com.blackgear.vanillabackport.common.worldgen.surface.SpatialNoiseThresholdConditionSource;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class ModSurfaceRuleData {
   private static final RuleSource CINNABAR = makeStateRule(ModBlocks.CINNABAR.get());
   private static final RuleSource SULFUR = makeStateRule(ModBlocks.SULFUR.get());

   public static RuleSource makeRules() {
      return SurfaceRules.ifTrue(
         SurfaceRules.not(SurfaceRules.abovePreliminarySurface()),
         SurfaceRules.ifTrue(
            SurfaceRules.isBiome(new ResourceKey[]{ModBiomes.SULFUR_CAVES}),
            SurfaceRules.sequence(
               new RuleSource[]{
                  SurfaceRules.ifTrue(noiseCondition3d(ModNoises.SULFUR_CAVE_GRADIENT, -0.4F, -0.1F), CINNABAR),
                  SurfaceRules.ifTrue(noiseCondition3d(ModNoises.SULFUR_CAVE_GRADIENT, 0.0, 0.4F), SULFUR),
                  SurfaceRules.ifTrue(noiseCondition3d(ModNoises.SULFUR_CAVE_GRADIENT, 0.4F), CINNABAR)
               }
            )
         )
      );
   }

   public static ConditionSource noiseCondition3d(ResourceKey<NoiseParameters> noise, double minRange, double maxRange) {
      return new SpatialNoiseThresholdConditionSource(noise, minRange, maxRange);
   }

   public static ConditionSource noiseCondition3d(ResourceKey<NoiseParameters> noise, double minRange) {
      return noiseCondition3d(noise, minRange, Double.MAX_VALUE);
   }

   private static RuleSource makeStateRule(Block block) {
      return SurfaceRules.state(block.defaultBlockState());
   }
}
