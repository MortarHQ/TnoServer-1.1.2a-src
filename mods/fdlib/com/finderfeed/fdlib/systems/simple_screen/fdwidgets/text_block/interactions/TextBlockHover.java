package com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions;

import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.TextBlockWidget;
import net.minecraft.client.gui.GuiGraphics;

@FunctionalInterface
public interface TextBlockHover {
   void onHoverOver(TextBlockWidget var1, GuiGraphics var2, float var3, float var4);
}
