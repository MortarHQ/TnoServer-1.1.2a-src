package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.function.Function;
import net.minecraft.util.Mth;

public class Easing {
   public static final Easing EASE_IN_OUT_CUBIC = new Easing(
      number -> (float)(number.floatValue() < 0.5 ? 4.0F * number * number * number : 1.0 - Math.pow(-2.0F * number + 2.0F, 3.0) / 2.0)
   );
   public static final Easing EASE_OUT_CUBIC = new Easing(number -> (float)(1.0 - Math.pow(1.0F - number, 3.0)));
   public static final Easing EASE_IN_OUT_QUINT = new Easing(
      number -> (float)(number.floatValue() < 0.5 ? 16.0 * Math.pow(number.floatValue(), 5.0) : 1.0 - Math.pow(-2.0F * number + 2.0F, 5.0) / 2.0)
   );
   public static final Easing EASE_IN_CUBIC_OUT_QUINT = new Easing(number -> {
      number = Mth.clamp(number, 0.0F, 1.0F);
      return number < 0.5F ? (float)(0.5 * Math.pow(number * 2.0F, 3.0)) : (float)(1.0 - 0.5 * (1.0 - (1.0 - Math.pow(1.0F - (number - 0.5F) * 2.0F, 5.0))));
   });
   private final Function<Float, Float> easingFunction;

   private Easing(Function<Float, Float> easingFunction) {
      this.easingFunction = easingFunction;
   }

   public float ease(float number) {
      return this.easingFunction.apply(number);
   }
}
