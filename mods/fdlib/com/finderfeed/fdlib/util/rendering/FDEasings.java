package com.finderfeed.fdlib.util.rendering;

public class FDEasings {
   public static float gaussian(float p) {
      return (float)Math.exp(-(p * p));
   }

   public static float one(float p) {
      return 1.0F;
   }

   public static float zero(float p) {
      return 0.0F;
   }

   public static float linear(float p) {
      return p;
   }

   public static float reversedLinear(float p) {
      return -p + 1.0F;
   }

   public static float squareHill(float p) {
      return 1.0F - 4.0F * (float)Math.pow(p - 0.5, 2.0);
   }

   public static float quadroHill(float p) {
      return 1.0F - 16.0F * (float)Math.pow(p - 0.5, 4.0);
   }

   public static float easeInOut(float p) {
      return p <= 0.5 ? 2.0F * p * p : -2.0F * (p - 1.0F) * (p - 1.0F) + 1.0F;
   }

   public static float reversedEaseInOut(float p) {
      return easeInOut(1.0F - p);
   }

   public static float easeIn(float p) {
      return p * p;
   }

   public static float easeOut(float p) {
      return 1.0F - (p - 1.0F) * (p - 1.0F);
   }

   public static float reversedEaseOut(float p) {
      p = 1.0F - p;
      return 1.0F - (p - 1.0F) * (p - 1.0F);
   }

   public static float easeOutBack(float x) {
      float c1 = 1.70158F;
      float c3 = c1 + 1.0F;
      return 1.0F + c3 * (float)Math.pow(x - 1.0F, 3.0) + c1 * (float)Math.pow(x - 1.0F, 2.0);
   }

   public static float easeInOutBack(float x) {
      float c1 = 1.70158F;
      float c2 = c1 * 1.525F;
      return x < 0.5
         ? (float)Math.pow(2.0F * x, 2.0) * ((c2 + 1.0F) * 2.0F * x - c2) / 2.0F
         : ((float)Math.pow(2.0F * x - 2.0F, 2.0) * ((c2 + 1.0F) * (x * 2.0F - 2.0F) + c2) + 2.0F) / 2.0F;
   }

   public static float easeOutBounce(float x) {
      float n1 = 7.5625F;
      float d1 = 2.75F;
      if (x < 1.0F / d1) {
         return n1 * x * x;
      } else if (x < 2.0F / d1) {
         float var5;
         return n1 * (var5 = x - 1.5F / d1) * var5 + 0.75F;
      } else {
         float var3;
         float var4;
         return x < 2.5 / d1 ? n1 * (var3 = x - 2.25F / d1) * var3 + 0.9375F : n1 * (var4 = x - 2.625F / d1) * var4 + 0.984375F;
      }
   }
}
