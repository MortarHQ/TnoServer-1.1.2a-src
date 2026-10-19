package net.jadenxgamer.elysium_api.impl.mixin.compat;

import net.jadenxgamer.elysium_api.ElysiumAPI;
import net.jadenxgamer.elysium_api.impl.core.biome.MosaicBiomeSource;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terrablender.api.RegionType;
import terrablender.api.SurfaceRuleManager.RuleCategory;
import terrablender.util.LevelUtils;
import terrablender.worldgen.IExtendedNoiseGeneratorSettings;

@Pseudo
@Mixin(targets = "terrablender.util.LevelUtils")
public abstract class LevelUtilsMixin {
   @Inject(method = "initializeBiomes", at = @At("HEAD"))
   private static void elysium_api$initializeBiomesFix(
      RegistryAccess registryAccess,
      Holder<DimensionType> dimensionType,
      ResourceKey<LevelStem> levelResourceKey,
      ChunkGenerator chunkGenerator,
      long seed,
      CallbackInfo ci
   ) {
      if (chunkGenerator instanceof NoiseBasedChunkGenerator noiseBasedChunkGenerator) {
         NoiseGeneratorSettings generatorSettings = (NoiseGeneratorSettings)noiseBasedChunkGenerator.generatorSettings().value();
         if (chunkGenerator.getBiomeSource() instanceof MosaicBiomeSource) {
            RegionType regionType = LevelUtils.getRegionTypeForDimension(dimensionType);
            if (regionType != null) {
               RuleCategory ruleCategory = null;
               switch (regionType) {
                  case OVERWORLD:
                     ruleCategory = RuleCategory.OVERWORLD;
                     break;
                  case NETHER:
                     ruleCategory = RuleCategory.NETHER;
               }

               if (ruleCategory == null) {
                  return;
               }

               ((IExtendedNoiseGeneratorSettings)generatorSettings).setRuleCategory(ruleCategory);
               ElysiumAPI.LOGGER
                  .info("MosaicBiomeSource successfully patched surface rules from Terrablender for dimension: '{}'", dimensionType.getRegisteredName());
            }
         }
      }
   }
}
