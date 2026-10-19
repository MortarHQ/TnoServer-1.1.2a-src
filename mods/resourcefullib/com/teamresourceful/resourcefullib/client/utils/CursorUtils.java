package com.teamresourceful.resourcefullib.client.utils;

import com.teamresourceful.resourcefullib.client.screens.CursorScreen;
import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public final class CursorUtils {
   private static final long DEFAULT_CURSOR = GLFW.glfwCreateStandardCursor(221185);
   private static final long POINTING_CURSOR = GLFW.glfwCreateStandardCursor(221188);
   private static final long DISABLED_CURSOR = GLFW.glfwCreateStandardCursor(221194);
   private static final long TEXT_CURSOR = GLFW.glfwCreateStandardCursor(221186);
   private static final long CROSSHAIR_CURSOR = GLFW.glfwCreateStandardCursor(221187);
   private static final long RESIZE_EW_CURSOR = GLFW.glfwCreateStandardCursor(221189);
   private static final long RESIZE_NS_CURSOR = GLFW.glfwCreateStandardCursor(221190);
   private static final long RESIZE_NWSE_CURSOR = GLFW.glfwCreateStandardCursor(221191);
   private static final long RESIZE_NESW_CURSOR = GLFW.glfwCreateStandardCursor(221192);
   private static final long RESIZE_ALL_CURSOR = GLFW.glfwCreateStandardCursor(221193);

   private CursorUtils() throws UtilityClassException {
      throw new UtilityClassException();
   }

   public static void setDefault() {
      GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), DEFAULT_CURSOR);
   }

   public static void setPointing() {
      GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), POINTING_CURSOR);
   }

   public static void setDisabled() {
      GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), DISABLED_CURSOR);
   }

   public static void setText() {
      GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), TEXT_CURSOR);
   }

   public static void setCrosshair() {
      GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), CROSSHAIR_CURSOR);
   }

   public static void setResizeEastWest() {
      GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), RESIZE_EW_CURSOR);
   }

   public static void setResizeNorthSouth() {
      GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), RESIZE_NS_CURSOR);
   }

   public static void setResizeNorthWestSouthEast() {
      GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), RESIZE_NWSE_CURSOR);
   }

   public static void setResizeNorthEastSouthWest() {
      GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), RESIZE_NESW_CURSOR);
   }

   public static void setResizeAll() {
      GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), RESIZE_ALL_CURSOR);
   }

   public static void setCursor(boolean state, CursorScreen.Cursor cursor) {
      if (Minecraft.getInstance().screen instanceof CursorScreen cursorScreen && state) {
         cursorScreen.setCursor(cursor);
      }
   }
}
