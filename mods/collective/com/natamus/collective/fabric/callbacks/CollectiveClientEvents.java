package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_638;

public class CollectiveClientEvents {
   public static final Event<CollectiveClientEvents.Client_World_Load> CLIENT_WORLD_LOAD = EventFactory.createArrayBacked(
      CollectiveClientEvents.Client_World_Load.class, callbacks -> clientLevel -> {
         for (CollectiveClientEvents.Client_World_Load callback : callbacks) {
            callback.onClientWorldLoad(clientLevel);
         }
      }
   );

   private CollectiveClientEvents() {
   }

   @FunctionalInterface
   public interface Client_World_Load {
      void onClientWorldLoad(class_638 var1);
   }
}
