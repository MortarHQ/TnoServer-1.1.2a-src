package cn.leolezury.eternalstarlight.common.util;

@FunctionalInterface
public interface Easing {
   Easing IN_SINE = x -> (float)(1.0 - Math.cos(x * Math.PI / 2.0));
   Easing OUT_SINE = x -> (float)Math.sin(x * Math.PI / 2.0);
   Easing IN_OUT_SINE = x -> (float)(-(Math.cos(Math.PI * x) - 1.0) / 2.0);
   Easing IN_QUAD = x -> x * x;
   Easing OUT_QUAD = x -> 1.0F - (1.0F - x) * (1.0F - x);
   Easing IN_OUT_QUAD = x -> (float)(x < 0.5 ? 2.0F * x * x : 1.0 - Math.pow(-2.0F * x + 2.0F, 2.0) / 2.0);
   Easing IN_CUBIC = x -> x * x * x;
   Easing OUT_CUBIC = x -> (float)(1.0 - Math.pow(1.0F - x, 3.0));
   Easing IN_OUT_CUBIC = x -> (float)(x < 0.5 ? 4.0F * x * x * x : 1.0 - Math.pow(-2.0F * x + 2.0F, 3.0) / 2.0);
   Easing IN_QUART = x -> x * x * x * x;
   Easing OUT_QUART = x -> (float)(1.0 - Math.pow(1.0F - x, 4.0));
   Easing IN_OUT_QUART = x -> (float)(x < 0.5 ? 8.0F * x * x * x * x : 1.0 - Math.pow(-2.0F * x + 2.0F, 4.0) / 2.0);
   Easing IN_QUINT = x -> x * x * x * x * x;
   Easing OUT_QUINT = x -> (float)(1.0 - Math.pow(1.0F - x, 5.0));
   Easing IN_OUT_QUINT = x -> (float)(x < 0.5 ? 16.0F * x * x * x * x * x : 1.0 - Math.pow(-2.0F * x + 2.0F, 5.0) / 2.0);
   Easing IN_EXPO = x -> (float)(x == 0.0F ? 0.0 : Math.pow(2.0, 10.0F * x - 10.0F));
   Easing OUT_EXPO = x -> (float)(x == 1.0F ? 1.0 : 1.0 - Math.pow(2.0, -10.0F * x));
   Easing IN_OUT_EXPO = x -> (float)(
      x == 0.0F ? 0.0 : (x == 1.0F ? 1.0 : (x < 0.5 ? Math.pow(2.0, 20.0F * x - 10.0F) / 2.0 : (2.0 - Math.pow(2.0, -20.0F * x + 10.0F)) / 2.0))
   );
   Easing IN_CIRC = x -> (float)(1.0 - Math.sqrt(1.0 - Math.pow(x, 2.0)));
   Easing OUT_CIRC = x -> (float)Math.sqrt(1.0 - Math.pow(x - 1.0F, 2.0));
   Easing IN_OUT_CIRC = x -> (float)(
      x < 0.5 ? (1.0 - Math.sqrt(1.0 - Math.pow(2.0F * x, 2.0))) / 2.0 : (Math.sqrt(1.0 - Math.pow(-2.0F * x + 2.0F, 2.0)) + 1.0) / 2.0
   );
   Easing IN_BACK = x -> {
      float c1 = 1.70158F;
      float c3 = c1 + 1.0F;
      return c3 * x * x * x - c1 * x * x;
   };
   Easing OUT_BACK = x -> {
      float c1 = 1.70158F;
      float c3 = c1 + 1.0F;
      return (float)(1.0 + c3 * Math.pow(x - 1.0F, 3.0) + c1 * Math.pow(x - 1.0F, 2.0));
   };
   Easing IN_OUT_BACK = x -> {
      float c1 = 1.70158F;
      float c2 = c1 * 1.525F;
      return (float)(
         x < 0.5
            ? Math.pow(2.0F * x, 2.0) * ((c2 + 1.0F) * 2.0F * x - c2) / 2.0
            : (Math.pow(2.0F * x - 2.0F, 2.0) * ((c2 + 1.0F) * (x * 2.0F - 2.0F) + c2) + 2.0) / 2.0
      );
   };
   Easing IN_ELASTIC = x -> {
      float c4 = (float) (Math.PI * 2.0 / 3.0);
      return x == 0.0F ? 0.0F : (float)(x == 1.0F ? 1.0 : -Math.pow(2.0, 10.0F * x - 10.0F) * Math.sin((x * 10.0F - 10.75) * c4));
   };
   Easing OUT_ELASTIC = x -> {
      float c4 = (float) (Math.PI * 2.0 / 3.0);
      return x == 0.0F ? 0.0F : (float)(x == 1.0F ? 1.0 : Math.pow(2.0, -10.0F * x) * Math.sin((x * 10.0F - 0.75) * c4) + 1.0);
   };
   Easing IN_OUT_ELASTIC = x -> {
      float c5 = (float) (Math.PI * 4.0 / 9.0);
      double v = Math.sin((20.0F * x - 11.125) * c5);
      return x == 0.0F
         ? 0.0F
         : (float)(x == 1.0F ? 1.0 : (x < 0.5 ? -(Math.pow(2.0, 20.0F * x - 10.0F) * v) / 2.0 : Math.pow(2.0, -20.0F * x + 10.0F) * v / 2.0 + 1.0));
   };
   Easing OUT_BOUNCE = x -> {
      float n1 = 7.5625F;
      float d1 = 2.75F;
      if (x < 1.0F / d1) {
         return n1 * x * x;
      } else if (x < 2.0F / d1) {
         float var5;
         return n1 * (var5 = x - (float)(1.5 / d1)) * var5 + 0.75F;
      } else {
         float var3;
         float var4;
         return x < 2.5 / d1 ? n1 * (var3 = x - (float)(2.25 / d1)) * var3 + 0.9375F : n1 * (var4 = x - (float)(2.625 / d1)) * var4 + 0.984375F;
      }
   };
   Easing IN_BOUNCE = x -> 1.0F - OUT_BOUNCE.calculate(1.0F - x);
   Easing IN_OUT_BOUNCE = x -> x < 0.5 ? (1.0F - OUT_BOUNCE.calculate(1.0F - 2.0F * x)) / 2.0F : (1.0F + OUT_BOUNCE.calculate(2.0F * x - 1.0F)) / 2.0F;

   float calculate(float var1);

   default float interpolate(float f, float from, float to) {
      return from + this.calculate(f) * (to - from);
   }
}
