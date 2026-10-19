package net.p3pp3rf1y.sophisticatedcore.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public class StorageGuiHelper {
   private StorageGuiHelper() {
   }

   public static void renderStorageBackground(Position position, GuiGraphics guiGraphics, ResourceLocation textureName, int xSize, int slotsHeight) {
      int x = position.x();
      int y = position.y();
      int slotsTopBottomHeight = Math.min(slotsHeight / 2, 150);
      int yOffset = 0;
      guiGraphics.blit(textureName, x, y, 0.0F, 0.0F, xSize, 17 + slotsTopBottomHeight, 256, 256);
      if (slotsHeight / 2 > 150) {
         int middleHeight = (slotsHeight / 2 - 150) * 2;
         guiGraphics.blit(textureName, x, y + 17 + slotsTopBottomHeight, 0.0F, 17.0F, xSize, middleHeight, 256, 256);
         yOffset = middleHeight;
      }

      int playerInventoryHeight = 97;
      guiGraphics.blit(
         textureName,
         x,
         y + yOffset + 17 + slotsTopBottomHeight,
         0.0F,
         256.0F - (playerInventoryHeight + slotsTopBottomHeight),
         xSize,
         playerInventoryHeight + slotsTopBottomHeight,
         256,
         256
      );
   }
}
