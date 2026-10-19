package twilightforest.init;

import java.util.List;
import java.util.OptionalLong;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.dimension.DimensionType.MonsterSettings;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.DensityFunctions.HolderHolder;
import twilightforest.TFRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.init.custom.BiomeLayerStack;
import twilightforest.world.components.biomesources.TFBiomeProvider;
import twilightforest.world.components.layer.BiomeDensitySource;
import twilightforest.world.registration.surface_rules.TFSurfaceRules;

public class TFDimensionData {
   @Deprecated
   public static final int SEALEVEL = 0;
   public static final ResourceKey<DimensionType> TWILIGHT_DIM_TYPE = ResourceKey.create(
      Registries.DIMENSION_TYPE, TwilightForestMod.prefix("twilight_forest_type")
   );
   public static final ResourceKey<NoiseGeneratorSettings> TWILIGHT_NOISE_GEN = ResourceKey.create(
      Registries.NOISE_SETTINGS, TwilightForestMod.prefix("twilight_noise_gen")
   );
   public static final ResourceKey<NoiseGeneratorSettings> SKYLIGHT_NOISE_GEN = ResourceKey.create(
      Registries.NOISE_SETTINGS, TwilightForestMod.prefix("skylight_noise_gen")
   );
   public static final ResourceKey<LevelStem> TWILIGHT_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, TFDimension.DIMENSION);

   private static DimensionType twilightDimType() {
      return new DimensionType(
         OptionalLong.of(13000L),
         true,
         false,
         false,
         true,
         0.125,
         true,
         false,
         -32,
         288,
         288,
         BlockTags.INFINIBURN_OVERWORLD,
         TFDimension.DIMENSION_RENDERER,
         0.01F,
         new MonsterSettings(false, false, UniformInt.of(0, 7), 7)
      );
   }

   public static void bootstrapType(BootstrapContext<DimensionType> context) {
      context.register(TWILIGHT_DIM_TYPE, twilightDimType());
   }

   public static NoiseGeneratorSettings makeNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context, boolean skylight) {
      HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
      DensityFunction finalDensity = new HolderHolder(
         densityFunctions.getOrThrow(skylight ? TFDensityFunctions.SKYLIGHT_TERRAIN : TFDensityFunctions.FORESTED_TERRAIN)
      );
      NoiseSettings tfNoise = NoiseSettings.create(-32, 256, 2, 2);
      return new NoiseGeneratorSettings(
         tfNoise,
         Blocks.STONE.defaultBlockState(),
         skylight ? Blocks.AIR.defaultBlockState() : Blocks.WATER.defaultBlockState(),
         new NoiseRouter(
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            finalDensity,
            finalDensity,
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero()
         ),
         TFSurfaceRules.tfSurface(),
         List.of(),
         0,
         false,
         false,
         false,
         false
      );
   }

   public static void bootstrapNoise(BootstrapContext<NoiseGeneratorSettings> context) {
      context.register(TWILIGHT_NOISE_GEN, makeNoiseSettings(context, false));
      context.register(SKYLIGHT_NOISE_GEN, makeNoiseSettings(context, true));
   }

   public static void bootstrapStem(BootstrapContext<LevelStem> context) {
      HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
      HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);
      HolderGetter<BiomeDensitySource> biomeDataRegistry = context.lookup(TFRegistries.Keys.BIOME_TERRAIN_DATA);
      NoiseBasedChunkGenerator twilightChunkGenerator = new NoiseBasedChunkGenerator(
         new TFBiomeProvider(biomeDataRegistry.getOrThrow(BiomeLayerStack.BIOME_GRID)), noiseGenSettings.getOrThrow(TWILIGHT_NOISE_GEN)
      );
      LevelStem stem = new LevelStem(dimTypes.getOrThrow(TWILIGHT_DIM_TYPE), twilightChunkGenerator);
      context.register(TWILIGHT_LEVEL_STEM, stem);
   }
}
