package com.iafenvoy.iceandfire.util;

import java.awt.Color;

public class Color4i {
   private static final float MUL = 255.0F;
   public final int r;
   public final int g;
   public final int b;
   public final int a;

   public Color4i(int r, int g, int b, int a) {
      this.r = r;
      this.g = g;
      this.b = b;
      this.a = a;
   }

   public Color4i(double r, double g, double b, double a) {
      this((int)(r * 255.0), (int)(g * 255.0), (int)(b * 255.0), (int)(a * 255.0));
   }

   public Color4i(int value) {
      this(value >> 24 & 0xFF, value >> 16 & 0xFF, value >> 8 & 0xFF, value & 0xFF);
   }

   public static Color4i copy(Color4i another) {
      return new Color4i(another.r, another.g, another.b, another.a);
   }

   public static Color4i copy(Color4i another, int alpha) {
      return new Color4i(another.r, another.g, another.b, alpha);
   }

   public static Color4i fromHSV(float h, float s, float v) {
      Color color = Color.getHSBColor(h, s, v);
      return new Color4i(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
   }

   public float getR() {
      return this.r / 255.0F;
   }

   public float getG() {
      return this.g / 255.0F;
   }

   public float getB() {
      return this.b / 255.0F;
   }

   public float getA() {
      return this.a / 255.0F;
   }

   public int getIntValue() {
      return this.a << 24 | this.r << 16 | this.g << 8 | this.b;
   }
}
