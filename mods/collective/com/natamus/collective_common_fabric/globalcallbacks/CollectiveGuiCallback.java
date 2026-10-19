package com.natamus.collective_common_fabric.globalcallbacks;

import com.natamus.collective_common_fabric.implementations.event.Event;
import com.natamus.collective_common_fabric.implementations.event.EventFactory;
import net.minecraft.class_332;
import net.minecraft.class_9779;

public class CollectiveGuiCallback {
   public static final Event<CollectiveGuiCallback.On_Gui_Render> ON_GUI_RENDER = EventFactory.createArrayBacked(
      CollectiveGuiCallback.On_Gui_Render.class, callbacks -> (guiGraphics, deltaTracker) -> {
         for (CollectiveGuiCallback.On_Gui_Render callback : callbacks) {
            callback.onGuiRender(guiGraphics, deltaTracker);
         }
      }
   );

   private CollectiveGuiCallback() {
   }

   @FunctionalInterface
   public interface On_Gui_Render {
      void onGuiRender(class_332 var1, class_9779 var2);
   }
}
