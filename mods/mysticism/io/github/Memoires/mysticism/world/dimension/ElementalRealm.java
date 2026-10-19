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

public class ElementalRealm {
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

      private static NoiseRouter elementalRealm(HolderGetter<NoiseParameters> holderGetter) {
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

      public static NoiseGeneratorSettings elementalRealm(BootstrapContext<?> bootstrapContext) {
         return new NoiseGeneratorSettings(
            NoiseSettings.create(0, 128, 2, 1),
            Blocks.BUDDING_AMETHYST.defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
            elementalRealm(bootstrapContext.lookup(Registries.NOISE)),
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
      private static final RuleSource CLAY = makeStateRule(Blocks.CLAY);
      private static final RuleSource SAND = makeStateRule(Blocks.SAND);
      private static final RuleSource DIRT = makeStateRule(Blocks.DIRT);
      private static final RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
      private static final RuleSource MAGMA_BLOCK = makeStateRule(Blocks.MAGMA_BLOCK);
      private static final RuleSource BASALT = makeStateRule(Blocks.BASALT);
      private static final RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
      private static final RuleSource END_STONE = makeStateRule(Blocks.END_STONE);
      private static final RuleSource MOSS = makeStateRule(Blocks.MOSS_BLOCK);
      private static final RuleSource STONE = makeStateRule(Blocks.STONE);
      private static final RuleSource MOSSY_STONE = makeStateRule(Blocks.MOSSY_COBBLESTONE);
      private static final RuleSource RED_SAND = makeStateRule(Blocks.RED_SAND);
      private static final RuleSource RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
      private static final RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
      private static final RuleSource BLACK_CONCRETE = makeStateRule(Blocks.BLACK_CONCRETE);
      private static final RuleSource BLACK_TERRACOTTA = makeStateRule(Blocks.BLACK_TERRACOTTA);
      private static final RuleSource WHITE_CONCRETE = makeStateRule(Blocks.WHITE_CONCRETE);
      private static final RuleSource QUARTZ_BLOCK = makeStateRule(Blocks.QUARTZ_BLOCK);
      private static final RuleSource WHITE_TERRACOTTA = makeStateRule(Blocks.WHITE_TERRACOTTA);
      public static final RuleSource ELEMENTAL_REALM_DIMENSION = elementalRealm();

      private static RuleSource makeStateRule(Block pBlock) {
         return SurfaceRules.state(pBlock.defaultBlockState());
      }

      private static RuleSource elementalRealm() {
         ConditionSource onCeilingLayer = SurfaceRules.ON_CEILING;
         ConditionSource underCeilingLayer = SurfaceRules.UNDER_CEILING;
         ConditionSource topLayer = SurfaceRules.ON_FLOOR;
         ConditionSource middleLayer = SurfaceRules.UNDER_FLOOR;
         ConditionSource preBottomLayer = SurfaceRules.DEEP_UNDER_FLOOR;
         ConditionSource bottomLayer = SurfaceRules.VERY_DEEP_UNDER_FLOOR;
         ConditionSource fireBiome = SurfaceRules.isBiome(new ResourceKey[]{MysticismBiomes.FIRE_BIOME});
         RuleSource fireBiomeSurface = SurfaceRules.sequence(
            new RuleSource[]{
               SurfaceRules.ifTrue(topLayer, BLACKSTONE), SurfaceRules.ifTrue(middleLayer, BASALT), SurfaceRules.ifTrue(bottomLayer, MAGMA_BLOCK), BASALT
            }
         );
         ConditionSource waterBiome = SurfaceRules.isBiome(new ResourceKey[]{MysticismBiomes.WATER_BIOME});
         RuleSource waterBiomeSurface = SurfaceRules.sequence(
            new RuleSource[]{SurfaceRules.ifTrue(topLayer, SAND), SurfaceRules.ifTrue(middleLayer, COARSE_DIRT), SurfaceRules.ifTrue(bottomLayer, DIRT), CLAY}
         );
         ConditionSource earthBiome = SurfaceRules.isBiome(new ResourceKey[]{MysticismBiomes.EARTH_BIOME});
         RuleSource earthBiomeSurface = SurfaceRules.sequence(
            new RuleSource[]{
               SurfaceRules.ifTrue(topLayer, RED_SAND),
               SurfaceRules.ifTrue(middleLayer, RED_SANDSTONE),
               SurfaceRules.ifTrue(bottomLayer, SANDSTONE),
               RED_SANDSTONE
            }
         );
         ConditionSource windBiome = SurfaceRules.isBiome(new ResourceKey[]{MysticismBiomes.WIND_BIOME});
         RuleSource windBiomeSurface = SurfaceRules.sequence(
            new RuleSource[]{SurfaceRules.ifTrue(topLayer, MOSS), SurfaceRules.ifTrue(middleLayer, MOSSY_STONE), SurfaceRules.ifTrue(bottomLayer, STONE), MOSS}
         );
         ConditionSource spaceBiome = SurfaceRules.isBiome(new ResourceKey[]{MysticismBiomes.SPACE_BIOME});
         RuleSource spaceBiomeSurface = SurfaceRules.sequence(
            new RuleSource[]{
               SurfaceRules.ifTrue(topLayer, END_STONE), SurfaceRules.ifTrue(middleLayer, END_STONE), SurfaceRules.ifTrue(bottomLayer, END_STONE), END_STONE
            }
         );
         ConditionSource lightBiome = SurfaceRules.isBiome(new ResourceKey[]{MysticismBiomes.LIGHT_BIOME});
         RuleSource lightBiomeSurface = SurfaceRules.sequence(
            new RuleSource[]{
               SurfaceRules.ifTrue(topLayer, WHITE_CONCRETE),
               SurfaceRules.ifTrue(middleLayer, QUARTZ_BLOCK),
               SurfaceRules.ifTrue(bottomLayer, WHITE_TERRACOTTA),
               WHITE_CONCRETE
            }
         );
         ConditionSource darknessBiome = SurfaceRules.isBiome(new ResourceKey[]{MysticismBiomes.DARKNESS_BIOME});
         RuleSource darknessBiomeSurface = SurfaceRules.sequence(
            new RuleSource[]{
               SurfaceRules.ifTrue(topLayer, BLACK_CONCRETE),
               SurfaceRules.ifTrue(middleLayer, BLACKSTONE),
               SurfaceRules.ifTrue(bottomLayer, BLACK_TERRACOTTA),
               BLACK_CONCRETE
            }
         );
         Builder<RuleSource> builder = ImmutableList.builder();
         builder.add(SurfaceRules.ifTrue(fireBiome, fireBiomeSurface));
         builder.add(SurfaceRules.ifTrue(waterBiome, waterBiomeSurface));
         builder.add(SurfaceRules.ifTrue(earthBiome, earthBiomeSurface));
         builder.add(SurfaceRules.ifTrue(windBiome, windBiomeSurface));
         builder.add(SurfaceRules.ifTrue(spaceBiome, spaceBiomeSurface));
         builder.add(SurfaceRules.ifTrue(lightBiome, lightBiomeSurface));
         builder.add(SurfaceRules.ifTrue(darknessBiome, darknessBiomeSurface));
         return SurfaceRules.sequence((RuleSource[])builder.build().toArray(RuleSource[]::new));
      }
   }
}
