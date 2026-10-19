package me.decce.gnetum.gui.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IconButton extends Button {
   private ResourceLocation icon;
   private int iconXOffset;
   private int iconYOffset;
   private int iconWidth;
   private int iconHeight;

   public IconButton(int x, int y, int width, int height, Component message, OnPress onPress) {
      super(x, y, width, height, message, onPress, Button.DEFAULT_NARRATION);
   }

   public void setIcon(ResourceLocation icon, int xOffset, int yOffset, int width, int height) {
      this.icon = icon;
      this.iconXOffset = xOffset;
      this.iconYOffset = yOffset;
      this.iconWidth = width;
      this.iconHeight = height;
   }

   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
      guiGraphics.blit(
         this.icon,
         this.getX() + this.iconXOffset,
         this.getY() + this.iconYOffset,
         this.iconWidth,
         this.iconHeight,
         0.0F,
         0.0F,
         this.iconWidth,
         this.iconHeight,
         this.iconWidth,
         this.iconHeight
      );
   }
}
