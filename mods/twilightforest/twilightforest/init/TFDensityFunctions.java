package twilightforest.init;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.DensityFunctions.HolderHolder;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import twilightforest.TFRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.init.custom.BiomeLayerStack;
import twilightforest.world.components.chunkgenerators.AbsoluteDifferenceFunction;
import twilightforest.world.components.chunkgenerators.FocusedDensityFunction;
import twilightforest.world.components.chunkgenerators.HollowHillFunction;
import twilightforest.world.components.chunkgenerators.NoiseDensityRouter;
import twilightforest.world.components.chunkgenerators.SqrtDensityFunction;
import twilightforest.world.components.chunkgenerators.TerrainDensityRouter;
import twilightforest.world.components.layer.BiomeDensitySource;

public class TFDensityFunctions {
   public static final DeferredRegister<MapCodec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES = DeferredRegister.create(
      Registries.DENSITY_FUNCTION_TYPE, "twilightforest"
   );
   public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<TerrainDensityRouter>> BIOME_DRIVEN_TERRAIN = register(
      "biome_driven_terrain", TerrainDensityRouter.CODEC
   );
   public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<NoiseDensityRouter>> BIOME_DRIVEN_NOISE = register(
      "biome_driven_noise", NoiseDensityRouter.CODEC
   );
   public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<FocusedDensityFunction>> FOCUSED = register(
      "focused", FocusedDensityFunction.CODEC
   );
   public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<HollowHillFunction>> HOLLOW_HILL = register(
      "hollow_hill", HollowHillFunction.CODEC
   );
   public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<AbsoluteDifferenceFunction.Min>> COORD_MIN = register(
      "coord_min", AbsoluteDifferenceFunction.Min.CODEC
   );
   public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<AbsoluteDifferenceFunction.Max>> COORD_MAX = register(
      "coord_max", AbsoluteDifferenceFunction.Max.CODEC
   );
   public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<SqrtDensityFunction>> SQRT = register("sqrt", SqrtDensityFunction.CODEC);
   public static final ResourceKey<DensityFunction> BIOME_TERRAIN_RAW = ResourceKey.create(
      Registries.DENSITY_FUNCTION, TwilightForestMod.prefix("raw_biome_terrain")
   );
   public static final ResourceKey<DensityFunction> BIOME_NOISE_RAW = ResourceKey.create(
      Registries.DENSITY_FUNCTION, TwilightForestMod.prefix("raw_biome_noise")
   );
   public static final ResourceKey<DensityFunction> FORESTED_TERRAIN = ResourceKey.create(
      Registries.DENSITY_FUNCTION, TwilightForestMod.prefix("forested_terrain")
   );
   public static final ResourceKey<DensityFunction> SKYLIGHT_TERRAIN = ResourceKey.create(
      Registries.DENSITY_FUNCTION, TwilightForestMod.prefix("skylight_terrain")
   );

   private static <T extends DensityFunction> DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<T>> register(String name, MapCodec<T> keyCodec) {
      return DENSITY_FUNCTION_TYPES.register(name, () -> keyCodec);
   }

   public static void bootstrap(BootstrapContext<DensityFunction> context) {
      Reference<BiomeDensitySource> biomeGrid = context.lookup(TFRegistries.Keys.BIOME_TERRAIN_DATA).getOrThrow(BiomeLayerStack.BIOME_GRID);
      DensityFunction referencedBiomeDensity = makeBiomeDensityRaw(context, biomeGrid);
      DensityFunction ambientTerrainNoise = makeAmbientNoise2D(context);
      DensityFunction referencedNoiseDensity = makeStreamDensityRaw(context, biomeGrid);
      makeForestedTerrain(context, referencedBiomeDensity, ambientTerrainNoise, referencedNoiseDensity);
      makeSkylightTerrain(context, referencedBiomeDensity, ambientTerrainNoise);
   }

   @NotNull
   private static DensityFunction makeBiomeDensityRaw(BootstrapContext<DensityFunction> context, Reference<BiomeDensitySource> biomeGrid) {
      DensityFunction rawBiomeDensityReferenced = new TerrainDensityRouter(
         biomeGrid, -31.0, 64.0, 1.0, DensityFunctions.constant(8.0), DensityFunctions.constant(-1.25)
      );
      return new HolderHolder(context.register(BIOME_TERRAIN_RAW, rawBiomeDensityReferenced));
   }

   @NotNull
   private static DensityFunction makeAmbientNoise2D(BootstrapContext<DensityFunction> context) {
      HolderGetter<NoiseParameters> noiseLookup = context.lookup(Registries.NOISE);
      Reference<NoiseParameters> surfaceParams = noiseLookup.getOrThrow(Noises.SURFACE);
      Reference<NoiseParameters> ridgeParams = noiseLookup.getOrThrow(Noises.RIDGE);
      DensityFunction noiseInterpolator = mulAddHalf(DensityFunctions.noise(surfaceParams, 1.0, 0.0));
      DensityFunction wideNoise = mulAddHalf(DensityFunctions.noise(ridgeParams, 1.0, 0.0));
      DensityFunction thinNoise = mulAddHalf(DensityFunctions.noise(ridgeParams, 4.0, 0.0));
      DensityFunction jitteredNoise = DensityFunctions.lerp(noiseInterpolator.clamp(0.0, 1.0), wideNoise, thinNoise);
      return DensityFunctions.flatCache(jitteredNoise);
   }

   @NotNull
   private static DensityFunction makeStreamDensityRaw(BootstrapContext<DensityFunction> context, Reference<BiomeDensitySource> biomeGrid) {
      DensityFunction rawStreamDensityReferenced = new NoiseDensityRouter(biomeGrid, -31.0, 64.0, 1.0);
      return new HolderHolder(context.register(BIOME_NOISE_RAW, rawStreamDensityReferenced));
   }

   @NotNull
   private static DensityFunction mulAddHalf(DensityFunction input) {
      return DensityFunctions.add(DensityFunctions.constant(0.5), DensityFunctions.mul(DensityFunctions.constant(0.5), input));
   }

   private static void makeForestedTerrain(
      BootstrapContext<DensityFunction> context, DensityFunction rawBiomeDensity, DensityFunction ambientTerrainNoise, DensityFunction rawNoiseDensity
   ) {
      DensityFunction biomedLandscape = DensityFunctions.mul(
         DensityFunctions.constant(0.16666667F), DensityFunctions.add(rawBiomeDensity, DensityFunctions.yClampedGradient(-31, 256, 31.0, -256.0))
      );
      DensityFunction finalDensity = DensityFunctions.add(
         biomedLandscape,
         DensityFunctions.mul(rawNoiseDensity, DensityFunctions.interpolated(DensityFunctions.max(DensityFunctions.zero(), ambientTerrainNoise)))
      );
      context.register(FORESTED_TERRAIN, finalDensity.clamp(-0.1, 0.5));
   }

   private static void makeSkylightTerrain(BootstrapContext<DensityFunction> context, DensityFunction rawBiomeDensity, DensityFunction ambientTerrainNoise) {
      DensityFunction skyIslandNoise = DensityFunctions.add(
         DensityFunctions.constant(-0.5),
         DensityFunctions.mul(DensityFunctions.add(DensityFunctions.constant(-0.5), ambientTerrainNoise), DensityFunctions.constant(5.0))
      );
      DensityFunction biomeDensity = DensityFunctions.mul(
         DensityFunctions.constant(-0.25),
         DensityFunctions.mul(DensityFunctions.add(rawBiomeDensity, DensityFunctions.yClampedGradient(-31, 256, 31.0, -256.0)), DensityFunctions.constant(-1.0))
            .halfNegative()
            .abs()
      );
      DensityFunction finalDensity = DensityFunctions.add(new SqrtDensityFunction(DensityFunctions.interpolated(skyIslandNoise).clamp(0.0, 2.0)), biomeDensity);
      context.register(SKYLIGHT_TERRAIN, finalDensity.clamp(-0.1, 0.5));
   }
}
