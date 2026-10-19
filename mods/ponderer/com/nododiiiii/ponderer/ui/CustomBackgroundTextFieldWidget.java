package com.nododiiiii.ponderer.ui;

import net.createmod.catnip.config.ui.HintableTextFieldWidget;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class CustomBackgroundTextFieldWidget extends HintableTextFieldWidget {
   private int customBackgroundColor = -1;

   public CustomBackgroundTextFieldWidget(Font font, int x, int y, int width, int height) {
      super(font, x, y, width, height);
   }

   public void setBackgroundColor(int color) {
      this.customBackgroundColor = color;
   }

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      if (this.customBackgroundColor != -1) {
         graphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, this.customBackgroundColor);
         graphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + 1, -10066330);
         graphics.fill(this.getX(), this.getY() + this.height - 1, this.getX() + this.width, this.getY() + this.height, -10066330);
         graphics.fill(this.getX(), this.getY(), this.getX() + 1, this.getY() + this.height, -10066330);
         graphics.fill(this.getX() + this.width - 1, this.getY(), this.getX() + this.width, this.getY() + this.height, -10066330);
         this.renderText(graphics);
      } else {
         super.renderWidget(graphics, mouseX, mouseY, partialTicks);
      }
   }

   private void renderText(GuiGraphics graphics) {
      String text = this.getValue();
      int color = -1;
      int maxW = this.width - 10;
      String displayText = text;
      if (this.font.width(displayText) > maxW) {
         int ellipsisWidth = this.font.width("...");
         int availableWidth = maxW - ellipsisWidth;
         if (availableWidth <= 0) {
            displayText = "...";
         } else {
            StringBuilder sb = new StringBuilder();
            int currentWidth = 0;

            for (int i = text.length() - 1; i >= 0; i--) {
               char c = text.charAt(i);
               String charStr = String.valueOf(c);
               int charWidth = this.font.width(charStr);
               if (currentWidth + charWidth > availableWidth) {
                  break;
               }

               sb.insert(0, c);
               currentWidth += charWidth;
            }

            if (sb.length() > 0) {
               displayText = "..." + sb.toString();
            } else {
               displayText = "...";
            }
         }
      }

      graphics.drawString(this.font, displayText, this.getX() + 5, this.getY() + (this.height - 8) / 2, color);
   }
}
