package com.finderfeed.fdlib.util;

public class FDColor {
   public float r;
   public float g;
   public float b;
   public float a;

   public FDColor(float r, float g, float b, float a) {
      this.r = r;
      this.g = g;
      this.b = b;
      this.a = a;
   }

   public int encode() {
      int r = Math.clamp(Math.round(this.r * 255.0F), 0, 255);
      int g = Math.clamp(Math.round(this.g * 255.0F), 0, 255);
      int b = Math.clamp(Math.round(this.b * 255.0F), 0, 255);
      int a = Math.clamp(Math.round(this.a * 255.0F), 0, 255);
      return (a << 24) + (r << 16) + (g << 8) + b;
   }

   public static FDColor decode(int color) {
      return new FDColor((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, (color >> 24 & 0xFF) / 255.0F);
   }
}
