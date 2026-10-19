package com.mortal_cultivation.client.gui;

import java.util.function.IntSupplier;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class SpiritRootTextRenderer {
   private static final float MIN_SCALE = 0.8F;
   private static final float SCROLL_PIXELS_PER_SECOND = 14.0F;
   private static final long END_PAUSE_MILLIS = 1000L;
   private String previousText;
   private int previousTextWidth;
   private int previousMaxWidth;
   private long scrollStartedAt;

   public void reset() {
      this.previousText = null;
   }

   public int draw(Screen screen, GuiGraphics graphics, Font font, Component text, Component tooltip, int left, int top, int maxWidth, int mouseX, int mouseY) {
      return this.draw(screen, graphics, font, text, tooltip, left, top, maxWidth, mouseX, mouseY, () -> graphics.drawString(font, text, 0, 0, 16777215, false));
   }

   public int draw(
      Screen screen,
      GuiGraphics graphics,
      Font font,
      Component text,
      Component tooltip,
      int left,
      int top,
      int maxWidth,
      int mouseX,
      int mouseY,
      IntSupplier drawText
   ) {
      int textWidth = font.width(text);
      if (maxWidth > 0 && textWidth > 0) {
         long now = Util.getMillis();
         String contents = text.getString();
         if (!contents.equals(this.previousText) || textWidth != this.previousTextWidth || maxWidth != this.previousMaxWidth) {
            this.previousText = contents;
            this.previousTextWidth = textWidth;
            this.previousMaxWidth = maxWidth;
            this.scrollStartedAt = now;
         }

         float fittingScale = Math.min(1.0F, (float)maxWidth / textWidth);
         float scale = Math.max(0.8F, fittingScale);
         float scaledWidth = textWidth * scale;
         float overflow = fittingScale < 0.8F ? scaledWidth - maxWidth : 0.0F;
         float offset = scrollOffset(overflow, now - this.scrollStartedAt);
         float y = top + (9.0F - 9.0F * scale) / 2.0F;
         graphics.enableScissor(left, top, left + maxWidth, top + 9);
         graphics.pose().pushPose();

         try {
            graphics.pose().translate(left - offset, y, 0.0F);
            graphics.pose().scale(scale, scale, 1.0F);
            drawText.getAsInt();
         } finally {
            graphics.pose().popPose();
            graphics.disableScissor();
         }

         int visibleWidth = Math.min(maxWidth, (int)Math.ceil(scaledWidth));
         if ((scale < 1.0F || !text.equals(tooltip)) && mouseX >= left && mouseX < left + visibleWidth && mouseY >= top && mouseY < top + 9) {
            screen.setTooltipForNextRenderPass(tooltip);
         }

         return left + visibleWidth;
      } else {
         this.reset();
         return left;
      }
   }

   private static float scrollOffset(float overflow, long elapsedMillis) {
      if (overflow <= 0.0F) {
         return 0.0F;
      }

      double travelMillis = overflow * 1000.0 / 14.0;
      double phase = elapsedMillis % (2.0 * (1000.0 + travelMillis));
      if (phase < 1000.0) {
         return 0.0F;
      }

      phase -= 1000.0;
      if (phase < travelMillis) {
         return (float)(overflow * phase / travelMillis);
      }

      phase -= travelMillis;
      return phase < 1000.0 ? overflow : (float)(overflow * (1.0 - (phase - 1000.0) / travelMillis));
   }
}
