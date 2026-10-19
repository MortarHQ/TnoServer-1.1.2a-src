package net.pixeldreamstudios.mythika.registry;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.MythikaConfig;

public final class SpawnRegistry {
   private SpawnRegistry() {
   }

   public static void forEachSpawn(SpawnRegistry.SpawnConsumer consumer) {
      MythikaConfig config = Mythika.config;
      if (config.cyclopsSpawnWeight > 0) {
         consumer.accept(TagRegistry.CYCLOPS_SPAWN_BIOMES, MobCategory.MONSTER, EntityRegistry.CYCLOPS.get(), config.cyclopsSpawnWeight, 1, 1);
      }

      if (config.minotaurSpawnWeight > 0) {
         consumer.accept(TagRegistry.MINOTAUR_SPAWN_BIOMES, MobCategory.MONSTER, EntityRegistry.MINOTAUR.get(), config.minotaurSpawnWeight, 1, 1);
      }

      if (config.satyrSpawnWeight > 0) {
         consumer.accept(TagRegistry.SATYR_SPAWN_BIOMES, MobCategory.CREATURE, EntityRegistry.SATYR.get(), config.satyrSpawnWeight, 4, 6);
      }
   }

   @FunctionalInterface
   public interface SpawnConsumer {
      void accept(TagKey<Biome> var1, MobCategory var2, EntityType<?> var3, int var4, int var5, int var6);
   }
}
