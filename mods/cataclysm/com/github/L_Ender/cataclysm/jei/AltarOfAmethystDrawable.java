package com.github.L_Ender.cataclysm.jei;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class AltarOfAmethystDrawable implements IDrawable {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/altar_of_amethyst_jei.png");

   public int getWidth() {
      return 125;
   }

   public int getHeight() {
      return 59;
   }

   public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
      int i = xOffset;
      int j = yOffset;
      guiGraphics.blit(TEXTURE, i, j, 0.0F, 0.0F, 125, 59, 256, 256);
   }
}
