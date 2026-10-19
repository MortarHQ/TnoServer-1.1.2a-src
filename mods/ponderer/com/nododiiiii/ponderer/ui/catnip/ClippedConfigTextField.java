package com.nododiiiii.ponderer.ui.catnip;

import net.createmod.catnip.config.ui.ConfigTextField;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class ClippedConfigTextField extends ConfigTextField {
   private static final int HINT_COLOR = -1868521312;

   public ClippedConfigTextField(Font font, int x, int y, int width, int height) {
      super(font, x, y, width, height);
   }

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      String savedHint = this.hint;
      this.hint = "";
      super.renderWidget(graphics, mouseX, mouseY, partialTicks);
      this.hint = savedHint;
      if (this.hint != null && !this.hint.isEmpty() && this.getValue().isEmpty()) {
         int maxWidth = Math.max(0, this.getWidth() - 10);
         String clipped = this.font.plainSubstrByWidth(this.hint, maxWidth);
         graphics.drawString(this.font, clipped, this.getX() + 5, this.getY() + (this.height - 8) / 2, -1868521312);
      }
   }
}
