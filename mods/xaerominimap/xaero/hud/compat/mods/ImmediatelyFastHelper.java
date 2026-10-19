package xaero.hud.compat.mods;

import net.minecraft.client.gui.GuiGraphics;
import xaero.lib.client.graphics.util.ImmediateRenderUtil;

public class ImmediatelyFastHelper {
   public static void triggerBatchingBuffersFlush(GuiGraphics guiGraphics) {
      ImmediateRenderUtil.coloredRectangle(guiGraphics.pose().last().pose(), 0.0F, 0.0F, 0.0F, 0.0F, 0);
   }
}
