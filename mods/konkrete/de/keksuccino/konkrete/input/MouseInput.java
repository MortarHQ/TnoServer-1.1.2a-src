package de.keksuccino.konkrete.input;

import de.keksuccino.konkrete.mixin.mixins.client.IMixinMouseHandler;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus.Internal;

public class MouseInput {
   private static boolean useRenderScale = false;
   private static float renderScale = 1.0F;
   @Internal
   public static boolean mouseHandler_screenLeftMouseDown = false;
   @Internal
   public static boolean mouseHandler_screenRightMouseDown = false;

   public static int getActiveMouseButton() {
      return ((IMixinMouseHandler)Minecraft.getInstance().mouseHandler).getActiveButtonKonkrete();
   }

   public static boolean isLeftMouseDown() {
      return mouseHandler_screenLeftMouseDown;
   }

   public static boolean isRightMouseDown() {
      return mouseHandler_screenRightMouseDown;
   }

   public static int getMouseX() {
      int x = (int)(
         Minecraft.getInstance().mouseHandler.xpos()
            * Minecraft.getInstance().getWindow().getGuiScaledWidth()
            / Minecraft.getInstance().getWindow().getScreenWidth()
      );
      return useRenderScale ? (int)(x / renderScale) : x;
   }

   public static int getMouseY() {
      int y = (int)(
         Minecraft.getInstance().mouseHandler.ypos()
            * Minecraft.getInstance().getWindow().getGuiScaledHeight()
            / Minecraft.getInstance().getWindow().getScreenHeight()
      );
      return useRenderScale ? (int)(y / renderScale) : y;
   }

   public static void setRenderScale(float scale) {
      renderScale = scale;
      useRenderScale = true;
   }

   public static void resetRenderScale() {
      useRenderScale = false;
   }

   @Deprecated
   public static void blockVanillaInput(String category) {
   }

   @Deprecated
   public static void unblockVanillaInput(String category) {
   }

   @Deprecated
   public static boolean isVanillaInputBlocked() {
      return false;
   }

   @Deprecated
   public static void ignoreBlockedVanillaInput(boolean ignore) {
   }
}
