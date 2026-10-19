package com.nododiiiii.ponderer.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public final class UiAnchorViewport {
   private static final int DEFAULT_CONTAINER_WIDTH = 176;
   private static final int DEFAULT_CONTAINER_HEIGHT = 166;

   private UiAnchorViewport() {
   }

   public static UiAnchorViewport.Rect resolve(Minecraft mc) {
      return resolveForScreen(mc, getEmbeddedMirrorScreen());
   }

   public static UiAnchorViewport.Rect resolveForScreen(Minecraft mc, @Nullable Screen mirror) {
      int guiW = Math.max(1, mc.getWindow().getGuiScaledWidth());
      int guiH = Math.max(1, mc.getWindow().getGuiScaledHeight());
      if (mirror instanceof AbstractContainerScreen<?> container) {
         UiAnchorViewport.Rect rect = readContainerRect(container, guiW, guiH);
         if (rect != null && rect.isValid()) {
            return rect;
         }
      }

      return centeredContainerFallback(guiW, guiH);
   }

   public static UiAnchorViewport.Rect resolveJeiForScreen(Minecraft mc, @Nullable Screen mirror) {
      if (mirror instanceof AbstractContainerScreen<?> container) {
         InterfaceSlotOverlayRenderer.ContainerBounds bounds = InterfaceSlotOverlayRenderer.readContainerBounds(container);
         return new UiAnchorViewport.Rect(bounds.left(), bounds.top(), bounds.width(), bounds.height());
      } else {
         return resolveForScreen(mc, mirror);
      }
   }

   private static UiAnchorViewport.Rect centeredContainerFallback(int guiW, int guiH) {
      double width = Math.min(guiW, 176);
      double height = Math.min(guiH, 166);
      double left = Math.floor((guiW - width) * 0.5);
      double top = Math.floor((guiH - height) * 0.5);
      return new UiAnchorViewport.Rect(left, top, width, height);
   }

   @Nullable
   private static UiAnchorViewport.Rect readContainerRect(AbstractContainerScreen<?> container, int guiW, int guiH) {
      try {
         Field leftPos = AbstractContainerScreen.class.getDeclaredField("leftPos");
         Field topPos = AbstractContainerScreen.class.getDeclaredField("topPos");
         Field imageWidth = AbstractContainerScreen.class.getDeclaredField("imageWidth");
         Field imageHeight = AbstractContainerScreen.class.getDeclaredField("imageHeight");
         leftPos.setAccessible(true);
         topPos.setAccessible(true);
         imageWidth.setAccessible(true);
         imageHeight.setAccessible(true);
         double width = imageWidth.getInt(container);
         double height = imageHeight.getInt(container);
         if (!(width <= 1.0) && !(height <= 1.0)) {
            double left = leftPos.getInt(container);
            double top = topPos.getInt(container);
            if (left == 0.0 && top == 0.0 && (guiW > width || guiH > height)) {
               left = Math.floor((guiW - width) * 0.5);
               top = Math.floor((guiH - height) * 0.5);
            }

            return new UiAnchorViewport.Rect(left, top, width, height);
         } else {
            return null;
         }
      } catch (Throwable ignored) {
         return null;
      }
   }

   @Nullable
   public static Screen getEmbeddedMirrorScreen() {
      Screen mirror = queryEmbeddedMirror("com.nododiiiii.ponderer.forge.sticksnapshot.client.ClientInputHandler");
      return mirror != null ? mirror : queryEmbeddedMirror("com.nododiiiii.ponderer.neoforge.sticksnapshot.client.ClientInputHandler");
   }

   @Nullable
   private static Screen queryEmbeddedMirror(String className) {
      try {
         Class<?> clazz = Class.forName(className);
         Method getter = clazz.getMethod("getEmbeddedMirrorScreen");
         if (getter.invoke(null) instanceof Screen screen) {
            return screen;
         }
      } catch (Throwable var5) {
      }

      return null;
   }

   public record Rect(double left, double top, double width, double height) {
      public boolean isValid() {
         return this.width > 1.0 && this.height > 1.0;
      }
   }
}
