package gardensofthedead.neoforge.datagen;

import gardensofthedead.registry.ModBiomes;
import gardensofthedead.registry.ModParticleTypes;
import gardensofthedead.registry.ModSoundEvents;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BiomeProvider {
   public static void create(BootstrapContext<Biome> context) {
      HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
      HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
      context.register(ModBiomes.SOULBLIGHT_FOREST, soulblightForest(features, carvers));
      context.register(ModBiomes.WHISTLING_WOODS, whistlingWoods(features, carvers));
   }

   protected static int calculateSkyColor(float color) {
      float $$1 = color / 3.0F;
      $$1 = Mth.clamp($$1, -1.0F, 1.0F);
      return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
   }

   public static Biome soulblightForest(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
      double energyBudget = 1.0;
      double charge = 0.8;
      MobSpawnSettings spawnSettings = new Builder()
         .addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.SKELETON, 3, 5, 5))
         .addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.ENDERMAN, 1, 4, 4))
         .addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 5, 2, 4))
         .addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.HOGLIN, 21, 3, 4))
         .addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.PIGLIN, 10, 3, 4))
         .addSpawn(MobCategory.CREATURE, new SpawnerData(EntityType.STRIDER, 60, 1, 2))
         .addMobCharge(EntityType.SKELETON, energyBudget, charge)
         .addMobCharge(EntityType.ENDERMAN, energyBudget, charge)
         .addMobCharge(EntityType.ZOMBIFIED_PIGLIN, energyBudget, charge)
         .addMobCharge(EntityType.HOGLIN, energyBudget, charge)
         .addMobCharge(EntityType.PIGLIN, energyBudget, charge)
         .addMobCharge(EntityType.STRIDER, energyBudget, charge)
         .build();
      net.minecraft.world.level.biome.BiomeGenerationSettings.Builder generationSettings = new net.minecraft.world.level.biome.BiomeGenerationSettings.Builder(
            features, carvers
         )
         .addCarver(Carving.AIR, Carvers.NETHER_CAVE)
         .addFeature(Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA)
         .addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN)
         .addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
         .addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE)
         .addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
         .addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
         .addFeature(Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
         .addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED)
         .addFeature(Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_SOUL_SAND)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.SOULBLIGHT_FUNGI)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.SOULBLIGHT_FOREST_VEGETATION)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.SHORT_STANDING_SOUL_SPORE_PATCH)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.LONG_STANDING_SOUL_SPORE_PATCH)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.SHORT_HANGING_SOUL_SPORE_PATCH)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.LONG_HANGING_SOUL_SPORE_PATCH);
      BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
      BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);
      return new BiomeBuilder()
         .hasPrecipitation(false)
         .temperature(2.0F)
         .downfall(0.0F)
         .specialEffects(
            new net.minecraft.world.level.biome.BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(5847585)
               .skyColor(calculateSkyColor(2.0F))
               .ambientParticle(new AmbientParticleSettings((ParticleOptions)ModParticleTypes.SOULBLIGHT_SPORE.get(), 0.01F))
               .ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
               .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0))
               .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111))
               .backgroundMusic(Musics.createGameMusic(BuiltInRegistries.SOUND_EVENT.wrapAsHolder((SoundEvent)ModSoundEvents.SOULBLIGHT_FOREST_MUSIC.get())))
               .build()
         )
         .mobSpawnSettings(spawnSettings)
         .generationSettings(generationSettings.build())
         .build();
   }

   public static Biome whistlingWoods(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
      MobSpawnSettings spawnSettings = new Builder()
         .addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 1, 2, 4))
         .addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.HOGLIN, 9, 3, 4))
         .addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.PIGLIN, 5, 3, 4))
         .addSpawn(MobCategory.CREATURE, new SpawnerData(EntityType.STRIDER, 60, 1, 2))
         .build();
      net.minecraft.world.level.biome.BiomeGenerationSettings.Builder generationSettings = new net.minecraft.world.level.biome.BiomeGenerationSettings.Builder(
            features, carvers
         )
         .addCarver(Carving.AIR, Carvers.NETHER_CAVE)
         .addFeature(Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA);
      BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
      generationSettings.addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN)
         .addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
         .addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
         .addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
         .addFeature(Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
         .addFeature(Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.NETHER_WART_BLOCK_PILE)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.DENSE_WEEPING_VINES)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.NOISY_CRIMSON_FUNGI)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.WHISTLECANE_COLUMN)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.TALL_BLISTERCROWN_PATCH)
         .addFeature(Decoration.VEGETAL_DECORATION, PlacedFeatureProvider.WHISTLING_WOODS_VEGETATION);
      BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);
      return new BiomeBuilder()
         .hasPrecipitation(false)
         .temperature(2.0F)
         .downfall(0.0F)
         .specialEffects(
            new net.minecraft.world.level.biome.BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(6164760)
               .skyColor(calculateSkyColor(2.0F))
               .ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.01F))
               .ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
               .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0))
               .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111))
               .backgroundMusic(Musics.createGameMusic(BuiltInRegistries.SOUND_EVENT.wrapAsHolder((SoundEvent)ModSoundEvents.WHISTLING_WOODS_MUSIC.get())))
               .build()
         )
         .mobSpawnSettings(spawnSettings)
         .generationSettings(generationSettings.build())
         .build();
   }
}
