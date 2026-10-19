package it.hurts.octostudios.immersiveui.util;

public class Easing {
   public static float lerp(float start_value, float end_value, float pct) {
      return start_value + (end_value - start_value) * pct;
   }

   public static float flip(float x) {
      return 1.0F - x;
   }

   public static float easeIn(float t) {
      return t * t;
   }

   public static float easeOut(float t) {
      return flip(easeIn(flip(t)));
   }

   public static float easeInOut(float t) {
      return lerp(easeIn(t), easeOut(t), t);
   }

   public static float easeOutBounce(float x) {
      float n1 = 7.5625F;
      float d1 = 2.75F;
      if (x < 0.36363637F) {
         return 7.5625F * x * x;
      } else if (x < 0.72727275F) {
         float var5;
         return (float)(7.5625F * (var5 = x - 0.54545456F) * var5 + 0.75);
      } else {
         float var3;
         float var4;
         return x < 0.9090909090909091
            ? (float)(7.5625F * (var3 = x - 0.8181818F) * var3 + 0.9375)
            : (float)(7.5625F * (var4 = x - 0.95454544F) * var4 + 0.984375);
      }
   }

   public static float animate(Easing.Type type, float x) {
      switch (type) {
         case EASE_IN:
            return easeIn(x);
         case EASE_OUT:
            return easeOut(x);
         case EASE_IN_OUT:
            return easeInOut(x);
         case EASE_OUT_BOUNCE:
            return easeOutBounce(x);
         case LINEAR:
            return x;
         default:
            return x;
      }
   }

   public enum Type {
      EASE_IN,
      EASE_OUT,
      EASE_IN_OUT,
      EASE_OUT_BOUNCE,
      LINEAR;
   }
}
