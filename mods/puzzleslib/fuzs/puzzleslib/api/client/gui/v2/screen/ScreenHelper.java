package fuzs.puzzleslib.api.client.gui.v2.screen;

import net.minecraft.client.Minecraft;

public final class ScreenHelper {
   private ScreenHelper() {
   }

   public static int getMouseX() {
      Minecraft minecraft = Minecraft.getInstance();
      return (int)(minecraft.mouseHandler.xpos() * minecraft.getWindow().getGuiScaledWidth() / minecraft.getWindow().getScreenWidth());
   }

   public static int getMouseY() {
      Minecraft minecraft = Minecraft.getInstance();
      return (int)(minecraft.mouseHandler.ypos() * minecraft.getWindow().getGuiScaledHeight() / minecraft.getWindow().getScreenHeight());
   }

   public static boolean isHovering(int posX, int posY, int width, int height, double mouseX, double mouseY) {
      return mouseX >= posX && mouseX < posX + width && mouseY >= posY && mouseY < posY + height;
   }
}
