package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class CollectiveLifecycleEvents {
   public static final Event<CollectiveLifecycleEvents.Minecraft_Loaded> MINECRAFT_LOADED = EventFactory.createArrayBacked(
      CollectiveLifecycleEvents.Minecraft_Loaded.class, callbacks -> isclient -> {
         for (CollectiveLifecycleEvents.Minecraft_Loaded callback : callbacks) {
            callback.onMinecraftLoad(isclient);
         }
      }
   );
   public static final Event<CollectiveLifecycleEvents.Default_Language_Loaded> DEFAULT_LANGUAGE_LOADED = EventFactory.createArrayBacked(
      CollectiveLifecycleEvents.Default_Language_Loaded.class, callbacks -> () -> {
         for (CollectiveLifecycleEvents.Default_Language_Loaded callback : callbacks) {
            callback.onLanguageLoad();
         }
      }
   );

   private CollectiveLifecycleEvents() {
   }

   public interface Default_Language_Loaded {
      void onLanguageLoad();
   }

   @FunctionalInterface
   public interface Minecraft_Loaded {
      void onMinecraftLoad(boolean var1);
   }
}
