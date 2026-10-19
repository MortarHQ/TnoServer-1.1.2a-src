package com.nododiiiii.ponderer.ui;

import net.createmod.catnip.config.ui.HintableTextFieldWidget;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class SoftHintTextFieldWidget extends HintableTextFieldWidget {
   private static final int SOFT_HINT_COLOR = -11513776;

   public SoftHintTextFieldWidget(Font font, int x, int y, int width, int height) {
      super(font, x, y, width, height);
   }

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      String savedHint = this.hint;
      this.hint = "";
      super.renderWidget(graphics, mouseX, mouseY, partialTicks);
      this.hint = savedHint;
      if (this.hint != null && !this.hint.isEmpty()) {
         if (this.getValue().isEmpty()) {
            int maxW = this.width - 10;
            String displayHint = this.hint;
            if (this.font.width(displayHint) > maxW) {
               displayHint = this.font.plainSubstrByWidth(displayHint, maxW - this.font.width("...")) + "...";
            }

            graphics.drawString(this.font, displayHint, this.getX() + 5, this.getY() + (this.height - 8) / 2, -11513776);
         }
      }
   }
}
