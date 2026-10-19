package fzzyhmstrs.emi_loot.neoforge.util;

import fzzyhmstrs.emi_loot.util.IconEmiWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class IconEmiWidgetImpl extends IconEmiWidget {
   public IconEmiWidgetImpl(int x, int y, int keyIndex, Component text) {
      super(x, y, keyIndex, text);
   }

   public void render(GuiGraphics matrices, int mouseX, int mouseY, float delta) {
      this.renderInternal(matrices, mouseX, mouseY, delta);
   }
}
