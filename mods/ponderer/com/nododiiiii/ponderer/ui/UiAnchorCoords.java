package com.nododiiiii.ponderer.ui;

public final class UiAnchorCoords {
   private UiAnchorCoords() {
   }

   public static double normalizeX(double mouseX, int width) {
      if (width <= 0) {
         return 0.0;
      }

      double centered = (mouseX / width - 0.5) * 2.0;
      return clamp(centered, -2.0, 2.0);
   }

   public static double normalizeY(double mouseY, int height) {
      if (height <= 0) {
         return 0.0;
      }

      double centered = (0.5 - mouseY / height) * 2.0;
      return clamp(centered, -2.0, 2.0);
   }

   public static double decodeToPixelX(double anchorX, int guiWidth) {
      if (guiWidth <= 0) {
         return 0.0;
      }

      double normalized = clamp(anchorX, -2.0, 2.0);
      return (normalized * 0.5 + 0.5) * guiWidth;
   }

   public static double decodeToPixelYTopLeft(double anchorY, int guiHeight) {
      if (guiHeight <= 0) {
         return 0.0;
      }

      double normalized = clamp(anchorY, -2.0, 2.0);
      return (0.5 - normalized * 0.5) * guiHeight;
   }

   public static double topLeftToTransformX(double xTopLeft, int guiWidth) {
      return xTopLeft - guiWidth * 0.5;
   }

   public static double topLeftToTransformY(double yTopLeft, int guiHeight) {
      return yTopLeft - guiHeight * 0.5;
   }

   private static double clamp(double v, double min, double max) {
      return Math.max(min, Math.min(max, v));
   }
}
