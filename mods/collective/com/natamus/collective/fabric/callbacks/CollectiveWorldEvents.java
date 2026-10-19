package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_3218;

public class CollectiveWorldEvents {
   public static final Event<CollectiveWorldEvents.World_Unload> WORLD_UNLOAD = EventFactory.createArrayBacked(
      CollectiveWorldEvents.World_Unload.class, callbacks -> serverLevel -> {
         for (CollectiveWorldEvents.World_Unload callback : callbacks) {
            callback.onWorldUnload(serverLevel);
         }
      }
   );

   private CollectiveWorldEvents() {
   }

   @FunctionalInterface
   public interface World_Unload {
      void onWorldUnload(class_3218 var1);
   }
}
