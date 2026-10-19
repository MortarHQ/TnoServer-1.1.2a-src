package net.jadenxgamer.netherexp.util;

import java.awt.Color;

public class ColorHelper {
   public static ColorHelper.Builder adjustHSB(Color original) {
      return new ColorHelper.Builder(original);
   }

   public static class Builder {
      private final float[] hsb;
      private float hueMul = 1.0F;
      private float satMul = 1.0F;
      private float briMul = 1.0F;

      private Builder(Color color) {
         this.hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
      }

      public ColorHelper.Builder hue(float multiplier) {
         this.hueMul = multiplier;
         return this;
      }

      public ColorHelper.Builder saturation(float multiplier) {
         this.satMul = multiplier;
         return this;
      }

      public ColorHelper.Builder brightness(float multiplier) {
         this.briMul = multiplier;
         return this;
      }

      public Color build() {
         float h = clamp(this.hsb[0] * this.hueMul);
         float s = clamp(this.hsb[1] * this.satMul);
         float b = clamp(this.hsb[2] * this.briMul);
         return new Color(Color.HSBtoRGB(h, s, b));
      }

      private static float clamp(float val) {
         return Math.min(1.0F, Math.max(0.0F, val));
      }
   }
}
