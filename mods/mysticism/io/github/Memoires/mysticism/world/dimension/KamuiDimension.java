package io.github.Memoires.mysticism.world.dimension;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import io.github.Memoires.mysticism.registry.dimension.MysticismNoises;
import io.github.Memoires.mysticism.registry.world.MysticismBiomes;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class KamuiDimension {
   public static class GenerationNoises extends NoiseRouterData {
      private static final DensityFunction ZERO = DensityFunctions.zero();

      private static DensityFunction slide(DensityFunction func, int i, int i1, int i2, int i3, double v, int i4, int i5, double v1) {
         DensityFunction func1 = DensityFunctions.yClampedGradient(i + i1 - i2, i + i1 - i3, 1.0, 0.0);
         DensityFunction toReturn = DensityFunctions.lerp(func1, v, func);
         DensityFunction func2 = DensityFunctions.yClampedGradient(i + i4, i + i5, 0.0, 1.0);
         return DensityFunctions.lerp(func2, v1, toReturn);
      }

      private static DensityFunction slideEndLike(DensityFunction func, int i, int i1) {
         return slide(func, i, i1, 72, -184, -23.4375, 4, 32, -0.234375);
      }

      private static NoiseRouter kamuiDimension(HolderGetter<NoiseParameters> holderGetter) {
         DensityFunction uFunc1 = DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftA(holderGetter.getOrThrow(Noises.SHIFT))));
         DensityFunction uFunc2 = DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftB(holderGetter.getOrThrow(Noises.SHIFT))));
         DensityFunction temperature = DensityFunctions.flatCache(
            DensityFunctions.shiftedNoise2d(uFunc1, uFunc2, 0.25, holderGetter.getOrThrow(Noises.TEMPERATURE))
         );
         DensityFunction vegetation = DensityFunctions.flatCache(
            DensityFunctions.shiftedNoise2d(uFunc1, uFunc2, 0.25, holderGetter.getOrThrow(Noises.VEGETATION))
         );
         DensityFunction continentalness = DensityFunctions.flatCache(
            DensityFunctions.shiftedNoise2d(uFunc1, uFunc2, 0.25, holderGetter.getOrThrow(Noises.CONTINENTALNESS))
         );
         DensityFunction erosion = DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d(uFunc1, uFunc2, 0.25, holderGetter.getOrThrow(Noises.EROSION)));
         DensityFunction uFunc3 = DensityFunctions.add(
            DensityFunctions.constant(0.05), DensityFunctions.noise(holderGetter.getOrThrow(MysticismNoises.ELEMENTAL_REALM), 0.6, 1.0)
         );
         DensityFunction uFunc4 = DensityFunctions.add(DensityFunctions.constant(0.4), uFunc3);
         DensityFunction uFunc5 = DensityFunctions.mul(DensityFunctions.yClampedGradient(16, 128, 1.0, 0.0), uFunc4);
         DensityFunction uFunc6 = DensityFunctions.add(DensityFunctions.constant(-0.2), uFunc5);
         DensityFunction uFunc7 = DensityFunctions.add(DensityFunctions.constant(0.1), uFunc6);
         DensityFunction uFunc8 = DensityFunctions.mul(DensityFunctions.yClampedGradient(0, 48, 0.0, 1.0), uFunc7);
         DensityFunction uFunc9 = DensityFunctions.add(DensityFunctions.constant(-0.1), uFunc8);
         DensityFunction uFunc10 = DensityFunctions.add(DensityFunctions.constant(-0.05), uFunc9);
         DensityFunction finalDensity = DensityFunctions.interpolated(DensityFunctions.blendDensity(slideEndLike(uFunc10, 0, 128))).squeeze();
         return new NoiseRouter(ZERO, ZERO, ZERO, ZERO, temperature, vegetation, continentalness, erosion, ZERO, ZERO, ZERO, finalDensity, ZERO, ZERO, ZERO);
      }

      public static NoiseGeneratorSettings kamuiDimension(BootstrapContext<?> bootstrapContext) {
         return new NoiseGeneratorSettings(
            NoiseSettings.create(0, 128, 2, 1),
            Blocks.AIR.defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
            kamuiDimension(bootstrapContext.lookup(Registries.NOISE)),
            ElementalRealm.Rules.ELEMENTAL_REALM_DIMENSION,
            List.of(),
            0,
            false,
            false,
            false,
            false
         );
      }
   }

   public static class Rules {
      private static final RuleSource CYAN_TERRACOTTA = makeStateRule(Blocks.CYAN_TERRACOTTA);
      public static final RuleSource KAMUI_DIMENSION = kamuiDimension();

      private static RuleSource makeStateRule(Block pBlock) {
         return SurfaceRules.state(pBlock.defaultBlockState());
      }

      private static RuleSource kamuiDimension() {
         ConditionSource topLayer = SurfaceRules.ON_FLOOR;
         ConditionSource middleLayer = SurfaceRules.UNDER_FLOOR;
         ConditionSource bottomLayer = SurfaceRules.VERY_DEEP_UNDER_FLOOR;
         ConditionSource kamuiBiome = SurfaceRules.isBiome(new ResourceKey[]{MysticismBiomes.KAMUI_BIOME});
         RuleSource kamuiBiomeSurface = SurfaceRules.sequence(
            new RuleSource[]{
               SurfaceRules.ifTrue(topLayer, CYAN_TERRACOTTA),
               SurfaceRules.ifTrue(middleLayer, CYAN_TERRACOTTA),
               SurfaceRules.ifTrue(bottomLayer, CYAN_TERRACOTTA),
               CYAN_TERRACOTTA
            }
         );
         Builder<RuleSource> builder = ImmutableList.builder();
         builder.add(SurfaceRules.ifTrue(kamuiBiome, kamuiBiomeSurface));
         return SurfaceRules.sequence((RuleSource[])builder.build().toArray(RuleSource[]::new));
      }
   }
}
