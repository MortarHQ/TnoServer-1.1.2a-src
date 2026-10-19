package xaero.map.gui;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import xaero.map.core.XaeroWorldMapCore;

public class CrosshairMessageOverlay implements Layer {
   public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      XaeroWorldMapCore.onRenderCrosshair(guiGraphics);
   }
}
