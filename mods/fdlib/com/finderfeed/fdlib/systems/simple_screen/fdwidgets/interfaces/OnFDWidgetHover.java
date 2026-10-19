package com.finderfeed.fdlib.systems.simple_screen.fdwidgets.interfaces;

import com.finderfeed.fdlib.systems.simple_screen.FDWidget;
import net.minecraft.client.gui.GuiGraphics;

@FunctionalInterface
public interface OnFDWidgetHover {
   void hoverOver(FDWidget var1, GuiGraphics var2, float var3, float var4, float var5);
}
