package com.nododiiiii.ponderer.neoforge.sticksnapshot.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DeadUiScreen extends Screen {
   private final String sourceName;

   protected DeadUiScreen(Screen source) {
      super(Component.literal("Dead UI Mirror"));
      this.sourceName = source.getClass().getSimpleName();
   }

   protected void init() {
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
      int panelW = 196;
      int panelH = 166;
      int x = (this.width - panelW) / 2;
      int y = (this.height - panelH) / 2;
      guiGraphics.fill(x - 2, y - 2, x + panelW + 2, y + panelH + 2, -870375649);
      guiGraphics.fill(x, y, x + panelW, y + panelH, -869651926);
      guiGraphics.drawString(this.font, "[DEAD UI MIRROR]", x + 8, y + 8, -1584257, false);
      guiGraphics.drawString(this.font, "Source: " + this.sourceName, x + 8, y + 24, -1, false);
      guiGraphics.drawString(this.font, "No interaction / no server sync", x + 8, y + 40, -5592406, false);
      guiGraphics.drawString(this.font, "Press ESC to close", x + 8, y + 56, -5592406, false);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return true;
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      return true;
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scrollDeltaX, double scrollDeltaY) {
      return true;
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.onClose();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean charTyped(char codePoint, int modifiers) {
      return true;
   }

   public boolean isPauseScreen() {
      return false;
   }
}
