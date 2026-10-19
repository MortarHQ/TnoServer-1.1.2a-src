package xaero.common.events;

import net.minecraft.client.ToggleKeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import xaero.hud.HudSession;

public class ClientEventsListener {
   public void playerTickPost(HudSession hudSession) {
   }

   public void clientTickPost(HudSession hudSession) {
   }

   public boolean handleRenderStatusEffectOverlay(GuiGraphics guiGraphics) {
      return false;
   }

   public boolean handleForceToggleKeyMapping(ToggleKeyMapping keyMapping) {
      return false;
   }
}
