package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1308;
import net.minecraft.class_1917;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_3218;
import net.minecraft.class_3730;

public class CollectiveSpawnEvents {
   public static final Event<CollectiveSpawnEvents.Mob_Check_Spawn> MOB_CHECK_SPAWN = EventFactory.createArrayBacked(
      CollectiveSpawnEvents.Mob_Check_Spawn.class, callbacks -> (entity, world, spawnerPos, spawnReason) -> {
         for (CollectiveSpawnEvents.Mob_Check_Spawn callback : callbacks) {
            if (!callback.onMobCheckSpawn(entity, world, spawnerPos, spawnReason)) {
               return false;
            }
         }

         return true;
      }
   );
   public static final Event<CollectiveSpawnEvents.Mob_Special_Spawn> MOB_SPECIAL_SPAWN = EventFactory.createArrayBacked(
      CollectiveSpawnEvents.Mob_Special_Spawn.class, callbacks -> (entity, level, blockPos, spawner, spawnReason) -> {
         for (CollectiveSpawnEvents.Mob_Special_Spawn callback : callbacks) {
            if (!callback.onMobSpecialSpawn(entity, level, blockPos, spawner, spawnReason)) {
               return false;
            }
         }

         return true;
      }
   );

   private CollectiveSpawnEvents() {
   }

   @FunctionalInterface
   public interface Mob_Check_Spawn {
      boolean onMobCheckSpawn(class_1308 var1, class_3218 var2, class_2338 var3, class_3730 var4);
   }

   @FunctionalInterface
   public interface Mob_Special_Spawn {
      boolean onMobSpecialSpawn(class_1308 var1, class_1937 var2, class_2338 var3, class_1917 var4, class_3730 var5);
   }
}
