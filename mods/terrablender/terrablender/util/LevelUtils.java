package terrablender.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import terrablender.DimensionTypeTags;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.core.TerraBlender;
import terrablender.worldgen.IExtendedBiomeSource;
import terrablender.worldgen.IExtendedNoiseGeneratorSettings;
import terrablender.worldgen.IExtendedParameterList;
import terrablender.worldgen.IExtendedTheEndBiomeSource;

public class LevelUtils {
   public static void initializeOnServerStart(MinecraftServer server) {
      RegistryAccess registryAccess = server.registryAccess();
      Registry<LevelStem> levelStemRegistry = registryAccess.registryOrThrow(Registries.LEVEL_STEM);
      long seed = server.getWorldData().worldGenOptions().seed();

      for (Entry<ResourceKey<LevelStem>, LevelStem> entry : levelStemRegistry.entrySet()) {
         LevelStem stem = entry.getValue();
         initializeBiomes(registryAccess, stem.type(), entry.getKey(), stem.generator(), seed);
      }
   }

   public static boolean shouldApplyToChunkGenerator(ChunkGenerator chunkGenerator) {
      return chunkGenerator instanceof NoiseBasedChunkGenerator && shouldApplyToBiomeSource(chunkGenerator.getBiomeSource());
   }

   public static boolean shouldApplyToBiomeSource(BiomeSource biomeSource) {
      return biomeSource instanceof MultiNoiseBiomeSource;
   }

   public static RegionType getRegionTypeForDimension(Holder<DimensionType> dimensionType) {
      if (dimensionType.is(DimensionTypeTags.NETHER_REGIONS)) {
         return RegionType.NETHER;
      } else {
         return dimensionType.is(DimensionTypeTags.OVERWORLD_REGIONS) ? RegionType.OVERWORLD : null;
      }
   }

   public static void initializeBiomes(
      RegistryAccess registryAccess, Holder<DimensionType> dimensionType, ResourceKey<LevelStem> levelResourceKey, ChunkGenerator chunkGenerator, long seed
   ) {
      if (chunkGenerator instanceof NoiseBasedChunkGenerator noiseBasedChunkGenerator) {
         NoiseGeneratorSettings generatorSettings = (NoiseGeneratorSettings)noiseBasedChunkGenerator.generatorSettings().value();
         if (chunkGenerator.getBiomeSource() instanceof TheEndBiomeSource) {
            ((IExtendedTheEndBiomeSource)chunkGenerator.getBiomeSource()).initializeForTerraBlender(registryAccess, seed);
            ((IExtendedNoiseGeneratorSettings)generatorSettings).setRuleCategory(SurfaceRuleManager.RuleCategory.END);
         } else if (shouldApplyToBiomeSource(chunkGenerator.getBiomeSource())) {
            RegionType regionType = getRegionTypeForDimension(dimensionType);
            MultiNoiseBiomeSource biomeSource = (MultiNoiseBiomeSource)chunkGenerator.getBiomeSource();
            IExtendedBiomeSource biomeSourceEx = (IExtendedBiomeSource)biomeSource;
            if (regionType != null) {
               SurfaceRuleManager.RuleCategory ruleCategory = switch (regionType) {
                  case OVERWORLD -> SurfaceRuleManager.RuleCategory.OVERWORLD;
                  case NETHER -> SurfaceRuleManager.RuleCategory.NETHER;
                  default -> throw new IllegalArgumentException("Attempted to get surface rule category for unsupported region type " + regionType);
               };
               ((IExtendedNoiseGeneratorSettings)generatorSettings).setRuleCategory(ruleCategory);
               ParameterList parameters = biomeSource.parameters();
               IExtendedParameterList parametersEx = (IExtendedParameterList)parameters;
               parametersEx.initializeForTerraBlender(registryAccess, regionType, seed);
               Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
               Builder<Holder<Biome>> builder = ImmutableList.builder();
               Regions.get(regionType).forEach(region -> region.addBiomes(biomeRegistry, pair -> {
                  if (biomeRegistry.getHolder((ResourceKey)pair.getSecond()).isPresent()) {
                     builder.add(biomeRegistry.getHolderOrThrow((ResourceKey)pair.getSecond()));
                  }
               }));
               biomeSourceEx.appendDeferredBiomesList(builder.build());
               TerraBlender.LOGGER.info(String.format("Initialized TerraBlender biomes for level stem %s", levelResourceKey.location()));
            }
         }
      }
   }
}
