package com.natamus.collective_common_neoforge.globalcallbacks;

import com.natamus.collective_common_neoforge.implementations.event.Event;
import com.natamus.collective_common_neoforge.implementations.event.EventFactory;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

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
      void onGuiRender(GuiGraphics var1, DeltaTracker var2);
   }
}
