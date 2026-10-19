package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1296;
import net.minecraft.class_1429;
import net.minecraft.class_3218;

public class CollectiveAnimalEvents {
   public static final Event<CollectiveAnimalEvents.On_Baby_Spawn> PRE_BABY_SPAWN = EventFactory.createArrayBacked(
      CollectiveAnimalEvents.On_Baby_Spawn.class, callbacks -> (world, parentA, parentB, offspring) -> {
         for (CollectiveAnimalEvents.On_Baby_Spawn callback : callbacks) {
            if (!callback.onBabySpawn(world, parentA, parentB, offspring)) {
               return false;
            }
         }

         return true;
      }
   );

   private CollectiveAnimalEvents() {
   }

   @FunctionalInterface
   public interface On_Baby_Spawn {
      boolean onBabySpawn(class_3218 var1, class_1429 var2, class_1429 var3, class_1296 var4);
   }
}
