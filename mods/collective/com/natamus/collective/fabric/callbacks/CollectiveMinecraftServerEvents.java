package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_3218;
import net.minecraft.class_5268;

public final class CollectiveMinecraftServerEvents {
   public static final Event<CollectiveMinecraftServerEvents.Set_Spawn> WORLD_SET_SPAWN = EventFactory.createArrayBacked(
      CollectiveMinecraftServerEvents.Set_Spawn.class, callbacks -> (serverLevel, serverLevelData) -> {
         for (CollectiveMinecraftServerEvents.Set_Spawn callback : callbacks) {
            callback.onSetSpawn(serverLevel, serverLevelData);
         }
      }
   );

   private CollectiveMinecraftServerEvents() {
   }

   @FunctionalInterface
   public interface Set_Spawn {
      void onSetSpawn(class_3218 var1, class_5268 var2);
   }
}
