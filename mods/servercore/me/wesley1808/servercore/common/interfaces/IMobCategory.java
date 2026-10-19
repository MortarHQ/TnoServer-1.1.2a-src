package me.wesley1808.servercore.common.interfaces;

import me.wesley1808.servercore.common.config.Config;
import me.wesley1808.servercore.common.config.data.mob_spawning.MobSpawnEntry;
import net.minecraft.world.entity.MobCategory;

public interface IMobCategory {
   int servercore$getSpawnInterval();

   int servercore$getOriginalCapacity();

   void servercore$modifyCapacity(double var1);

   void servercore$modifySpawningConfig(int var1, int var2);

   static IMobCategory of(MobCategory category) {
      return (IMobCategory)category;
   }

   static int getSpawnInterval(MobCategory category) {
      return of(category).servercore$getSpawnInterval();
   }

   static int getOriginalCapacity(MobCategory category) {
      return of(category).servercore$getOriginalCapacity();
   }

   static void modifyCapacity(MobCategory category, double modifier) {
      of(category).servercore$modifyCapacity(modifier);
   }

   static void reload() {
      for (MobSpawnEntry entry : Config.get().mobSpawning().categories()) {
         modify(entry.category(), entry.capacity(), entry.spawnInterval());
      }
   }

   private static void modify(MobCategory category, int max, int interval) {
      of(category).servercore$modifySpawningConfig(max, interval);
   }
}
