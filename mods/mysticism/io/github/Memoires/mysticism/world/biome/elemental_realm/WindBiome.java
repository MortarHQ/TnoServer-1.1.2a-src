package io.github.Memoires.mysticism.world.biome.elemental_realm;

import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import io.github.Memoires.mysticism.registry.world.MysticismPlacedFeatures;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeGenerationSettings.PlainBuilder;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class WindBiome {
   public static Biome create(BootstrapContext<?> context) {
      Builder mobSpawning = new Builder();
      mobSpawning.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)MonsterEntityTypes.FEATHERED_SERPENT.get(), 10, 1, 2));
      mobSpawning.addMobCharge((EntityType)MonsterEntityTypes.FEATHERED_SERPENT.get(), 2.0, 2.0);
      mobSpawning.addSpawn(MobCategory.MONSTER, new SpawnerData((EntityType)MysticismMonsterEntityTypes.MEMOIRES.get(), 1, 1, 1));
      mobSpawning.addMobCharge((EntityType)MysticismMonsterEntityTypes.MEMOIRES.get(), 4.0, 4.0);
      mobSpawning.addSpawn(MobCategory.CREATURE, new SpawnerData(EntityType.ALLAY, 5, 1, 2));
      mobSpawning.addSpawn(MobCategory.MONSTER, new SpawnerData(EntityType.VEX, 15, 1, 3));
      PlainBuilder generationSettings = new PlainBuilder()
         .addCarver(Carving.AIR, context.lookup(Registries.CONFIGURED_CARVER).getOrThrow(Carvers.CANYON))
         .addFeature(Decoration.LOCAL_MODIFICATIONS, context.lookup(Registries.PLACED_FEATURE).getOrThrow(MysticismPlacedFeatures.WIND_BLOB));
      BiomeSpecialEffects effects = new net.minecraft.world.level.biome.BiomeSpecialEffects.Builder()
         .fogColor(32768)
         .waterColor(32768)
         .waterFogColor(32768)
         .skyColor(0)
         .ambientLoopSound(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
         .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0))
         .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111))
         .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_NETHER_WASTES))
         .ambientParticle(new AmbientParticleSettings(ParticleTypes.SMALL_GUST, 0.001F))
         .build();
      return new BiomeBuilder()
         .hasPrecipitation(false)
         .generationSettings(generationSettings.build())
         .downfall(0.0F)
         .temperature(0.6F)
         .temperatureAdjustment(TemperatureModifier.NONE)
         .specialEffects(effects)
         .mobSpawnSettings(mobSpawning.build())
         .build();
   }
}
