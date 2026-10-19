package com.github.wal_bos.moreskills.client.fx;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class PathsFlashOverlay {
   public static void render(GuiGraphics graphics, PathsClientState.FlashMode mode, float alpha) {
      int w = graphics.guiWidth();
      int h = graphics.guiHeight();
      int a = Mth.clamp((int)(alpha * 255.0F), 0, 255);
      int rgb = mode == PathsClientState.FlashMode.WHITE ? 16777215 : 0;
      int argb = a << 24 | rgb;
      graphics.fill(0, 0, w, h, argb);
   }
}
