package com.teamresourceful.resourcefullib.client.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public abstract class ImageButton extends AbstractButton {
   protected int imageWidth = 256;
   protected int imageHeight = 256;

   public ImageButton(int x, int y, int width, int height) {
      super(x, y, width, height, CommonComponents.EMPTY);
   }

   public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      graphics.blit(
         this.getTexture(mouseX, mouseY),
         this.getX(),
         this.getY(),
         this.getU(mouseX, mouseY),
         this.getV(mouseX, mouseY),
         this.width,
         this.height,
         this.imageWidth,
         this.imageHeight
      );
   }

   public abstract ResourceLocation getTexture(int var1, int var2);

   public abstract int getU(int var1, int var2);

   public abstract int getV(int var1, int var2);

   protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
   }
}
