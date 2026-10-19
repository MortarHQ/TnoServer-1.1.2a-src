package com.finderfeed.fdlib.systems.simple_screen.fdwidgets.interfaces;

import com.finderfeed.fdlib.systems.simple_screen.FDWidget;

@FunctionalInterface
public interface OnFDWidgetClick {
   boolean click(FDWidget var1, float var2, float var3, int var4);
}
