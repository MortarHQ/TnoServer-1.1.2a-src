package com.natamus.collective_common_fabric.globalcallbacks;

import com.natamus.collective_common_fabric.implementations.event.Event;
import com.natamus.collective_common_fabric.implementations.event.EventFactory;

public class MainMenuLoadedCallback {
   public static final Event<MainMenuLoadedCallback.On_Main_Menu_Loaded> MAIN_MENU_LOADED = EventFactory.createArrayBacked(
      MainMenuLoadedCallback.On_Main_Menu_Loaded.class, callbacks -> () -> {
         for (MainMenuLoadedCallback.On_Main_Menu_Loaded callback : callbacks) {
            callback.onMainMenuLoaded();
         }
      }
   );

   private MainMenuLoadedCallback() {
   }

   @FunctionalInterface
   public interface On_Main_Menu_Loaded {
      void onMainMenuLoaded();
   }
}
