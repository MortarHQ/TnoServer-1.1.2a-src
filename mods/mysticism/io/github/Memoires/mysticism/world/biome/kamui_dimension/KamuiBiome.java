package io.github.Memoires.mysticism.world.biome.kamui_dimension;

import io.github.Memoires.mysticism.registry.world.MysticismPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeGenerationSettings.PlainBuilder;
import net.minecraft.world.level.biome.BiomeSpecialEffects.Builder;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class KamuiBiome {
   public static Biome create(BootstrapContext<?> context) {
      PlainBuilder generationSettings = new PlainBuilder()
         .addFeature(Decoration.SURFACE_STRUCTURES, context.lookup(Registries.PLACED_FEATURE).getOrThrow(MysticismPlacedFeatures.KAMUI_CUBE));
      BiomeSpecialEffects effects = new Builder().fogColor(462100).waterColor(0).waterFogColor(462100).skyColor(462100).build();
      return new BiomeBuilder()
         .hasPrecipitation(false)
         .generationSettings(generationSettings.build())
         .downfall(0.0F)
         .temperature(0.6F)
         .temperatureAdjustment(TemperatureModifier.NONE)
         .specialEffects(effects)
         .mobSpawnSettings(new net.minecraft.world.level.biome.MobSpawnSettings.Builder().build())
         .build();
   }
}
