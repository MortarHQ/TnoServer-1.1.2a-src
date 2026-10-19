package com.mongoose.royalvariations.common.world;

import com.mongoose.royalvariations.common.entities.RVEntityType;
import com.mongoose.royalvariations.config.RVConfig;
import com.mongoose.royalvariations.init.RVTags;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public class RVLevelRegistry {
   public static void addBiomeSpawns(Holder<Biome> biome, Builder builder) {
      if (!biome.is(RVTags.Biomes.COMMON_BLACKLIST) && !biome.is(biomeResourceKey -> biomeResourceKey.registry().getNamespace().contains("alexscaves"))) {
         if (biome.is(RVTags.Biomes.ROYAL_ZOMBIE_SPAWN) && (Integer)RVConfig.RoyalZombieSpawnWeight.get() > 0) {
            builder.getMobSpawnSettings()
               .getSpawner(MobCategory.MONSTER)
               .add(
                  new SpawnerData(
                     (EntityType)RVEntityType.ROYAL_ZOMBIE.get(),
                     (Integer)RVConfig.RoyalZombieSpawnWeight.get(),
                     (Integer)RVConfig.RoyalZombieSpawnMinCount.get(),
                     (Integer)RVConfig.RoyalZombieSpawnMaxCount.get()
                  )
               );
         }

         if (biome.is(RVTags.Biomes.ROYAL_SKELETON_SPAWN) && (Integer)RVConfig.RoyalSkeletonSpawnWeight.get() > 0) {
            builder.getMobSpawnSettings()
               .getSpawner(MobCategory.MONSTER)
               .add(
                  new SpawnerData(
                     (EntityType)RVEntityType.ROYAL_SKELETON.get(),
                     (Integer)RVConfig.RoyalSkeletonSpawnWeight.get(),
                     (Integer)RVConfig.RoyalSkeletonSpawnMinCount.get(),
                     (Integer)RVConfig.RoyalSkeletonSpawnMaxCount.get()
                  )
               );
         }

         if (biome.is(RVTags.Biomes.ROYAL_CREEPER_SPAWN) && (Integer)RVConfig.RoyalCreeperSpawnWeight.get() > 0) {
            builder.getMobSpawnSettings()
               .getSpawner(MobCategory.MONSTER)
               .add(
                  new SpawnerData(
                     (EntityType)RVEntityType.ROYAL_CREEPER.get(),
                     (Integer)RVConfig.RoyalCreeperSpawnWeight.get(),
                     (Integer)RVConfig.RoyalCreeperSpawnMinCount.get(),
                     (Integer)RVConfig.RoyalCreeperSpawnMaxCount.get()
                  )
               );
         }

         if (biome.is(RVTags.Biomes.ROYAL_ENDERMAN_SPAWN) && (Integer)RVConfig.RoyalEndermanSpawnWeight.get() > 0) {
            builder.getMobSpawnSettings()
               .getSpawner(MobCategory.MONSTER)
               .add(
                  new SpawnerData(
                     (EntityType)RVEntityType.ROYAL_ENDERMAN.get(),
                     (Integer)RVConfig.RoyalEndermanSpawnWeight.get(),
                     (Integer)RVConfig.RoyalEndermanSpawnMinCount.get(),
                     (Integer)RVConfig.RoyalEndermanSpawnMaxCount.get()
                  )
               );
         }
      }
   }

   public static boolean startName(ResourceKey<Biome> biomeResourceKey, String string) {
      return biomeResourceKey.registry().getNamespace().startsWith(string);
   }

   public static boolean containsName(ResourceKey<Biome> biomeResourceKey, String string) {
      return biomeResourceKey.registry().getNamespace().contains(string);
   }
}
