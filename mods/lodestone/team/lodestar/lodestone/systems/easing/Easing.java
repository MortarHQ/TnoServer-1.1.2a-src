package team.lodestar.lodestone.systems.easing;

import com.mojang.serialization.Codec;
import java.util.HashMap;
import net.minecraft.util.Mth;

public abstract class Easing {
   public static final Codec<Easing> CODEC = Codec.STRING.xmap(Easing::valueOf, e -> e.name);
   public static final HashMap<String, Easing> EASINGS = new HashMap<>();
   public final String name;
   public static final Easing LINEAR = new Easing("linear") {
      @Override
      public float ease(float value, float min, float max, float time) {
         return max * value / time + min;
      }
   };
   public static final Easing QUAD_IN = new Easing("quadIn") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return max * (var5 = value / time) * var5 + min;
      }
   };
   public static final Easing QUAD_OUT = new Easing("quadOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return -max * (var5 = value / time) * (var5 - 2.0F) + min;
      }
   };
   public static final Easing QUAD_IN_OUT = new Easing("quadInOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return (var5 = value / (time / 2.0F)) < 1.0F ? max / 2.0F * var5 * var5 + min : -max / 2.0F * (--var5 * (var5 - 2.0F) - 1.0F) + min;
      }
   };
   public static final Easing CUBIC_IN = new Easing("cubicIn") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return max * (var5 = value / time) * var5 * var5 + min;
      }
   };
   public static final Easing CUBIC_OUT = new Easing("cubicOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return max * ((var5 = value / time - 1.0F) * var5 * var5 + 1.0F) + min;
      }
   };
   public static final Easing CUBIC_IN_OUT = new Easing("cubicInOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         float var6;
         return (var5 = value / (time / 2.0F)) < 1.0F ? max / 2.0F * var5 * var5 * var5 + min : max / 2.0F * ((var6 = var5 - 2.0F) * var6 * var6 + 2.0F) + min;
      }
   };
   public static final Easing QUARTIC_IN = new Easing("quarticIn") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return max * (var5 = value / time) * var5 * var5 * var5 + min;
      }
   };
   public static final Easing QUARTIC_OUT = new Easing("quarticOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return -max * ((var5 = value / time - 1.0F) * var5 * var5 * var5 - 1.0F) + min;
      }
   };
   public static final Easing QUARTIC_IN_OUT = new Easing("quarticInOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         float var6;
         return (var5 = value / (time / 2.0F)) < 1.0F
            ? max / 2.0F * var5 * var5 * var5 * var5 + min
            : -max / 2.0F * ((var6 = var5 - 2.0F) * var6 * var6 * var6 - 2.0F) + min;
      }
   };
   public static final Easing QUINTIC_IN = new Easing("quinticIn") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return max * (var5 = value / time) * var5 * var5 * var5 * var5 + min;
      }
   };
   public static final Easing QUINTIC_OUT = new Easing("quinticOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return max * ((var5 = value / time - 1.0F) * var5 * var5 * var5 * var5 + 1.0F) + min;
      }
   };
   public static final Easing QUINTIC_IN_OUT = new Easing("quinticInOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         float var6;
         return (var5 = value / (time / 2.0F)) < 1.0F
            ? max / 2.0F * var5 * var5 * var5 * var5 * var5 + min
            : max / 2.0F * ((var6 = var5 - 2.0F) * var6 * var6 * var6 * var6 + 2.0F) + min;
      }
   };
   public static final Easing SINE_IN = new Easing("sineIn") {
      @Override
      public float ease(float value, float min, float max, float time) {
         return -max * (float)Math.cos(value / time * (Math.PI / 2)) + max + min;
      }
   };
   public static final Easing SINE_OUT = new Easing("sineOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         return max * (float)Math.sin(value / time * (Math.PI / 2)) + min;
      }
   };
   public static final Easing SINE_IN_OUT = new Easing("sineInOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         return -max / 2.0F * ((float)Math.cos(Math.PI * value / time) - 1.0F) + min;
      }
   };
   public static final Easing EXPO_IN = new Easing("expoIn") {
      @Override
      public float ease(float value, float min, float max, float time) {
         return value == 0.0F ? min : max * (float)Math.pow(2.0, 10.0F * (value / time - 1.0F)) + min;
      }
   };
   public static final Easing EXPO_OUT = new Easing("expoOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         return value == time ? min + max : max * (-((float)Math.pow(2.0, -10.0F * value / time)) + 1.0F) + min;
      }
   };
   public static final Easing EXPO_IN_OUT = new Easing("expoInOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         if (value == 0.0F) {
            return min;
         }

         if (value == time) {
            return min + max;
         }

         float var5;
         return (var5 = value / (time / 2.0F)) < 1.0F
            ? max / 2.0F * (float)Math.pow(2.0, 10.0F * (var5 - 1.0F)) + min
            : max / 2.0F * (-((float)Math.pow(2.0, -10.0F * --var5)) + 2.0F) + min;
      }
   };
   public static final Easing CIRC_IN = new Easing("circIn") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return -max * ((float)Math.sqrt(1.0F - (var5 = value / time) * var5) - 1.0F) + min;
      }
   };
   public static final Easing CIRC_OUT = new Easing("circOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         return max * (float)Math.sqrt(1.0F - (var5 = value / time - 1.0F) * var5) + min;
      }
   };
   public static final Easing CIRC_IN_OUT = new Easing("circInOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         float var5;
         float var6;
         return (var5 = value / (time / 2.0F)) < 1.0F
            ? -max / 2.0F * ((float)Math.sqrt(1.0F - var5 * var5) - 1.0F) + min
            : max / 2.0F * ((float)Math.sqrt(1.0F - (var6 = var5 - 2.0F) * var6) + 1.0F) + min;
      }
   };
   public static final Easing.Elastic ELASTIC_IN = new Easing.ElasticIn();
   public static final Easing.Elastic ELASTIC_OUT = new Easing.ElasticOut();
   public static final Easing.Elastic ELASTIC_IN_OUT = new Easing.ElasticInOut();
   public static final Easing.Back BACK_IN = new Easing.BackIn();
   public static final Easing.Back BACK_OUT = new Easing.BackOut();
   public static final Easing.Back BACK_IN_OUT = new Easing.BackInOut();
   public static final Easing BOUNCE_IN = new Easing("bounceIn") {
      @Override
      public float ease(float value, float min, float max, float time) {
         return max - Easing.BOUNCE_OUT.ease(time - value, 0.0F, max, time) + min;
      }
   };
   public static final Easing BOUNCE_OUT = new Easing("bounceOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         if ((value = value / time) < 0.36363637F) {
            return max * (7.5625F * value * value) + min;
         } else if (value < 0.72727275F) {
            float var8;
            return max * (7.5625F * (var8 = value - 0.54545456F) * var8 + 0.75F) + min;
         } else {
            float var6;
            float var7;
            return value < 0.90909094F
               ? max * (7.5625F * (var6 = value - 0.8181818F) * var6 + 0.9375F) + min
               : max * (7.5625F * (var7 = value - 0.95454544F) * var7 + 0.984375F) + min;
         }
      }
   };
   public static final Easing BOUNCE_IN_OUT = new Easing("bounceInOut") {
      @Override
      public float ease(float value, float min, float max, float time) {
         return value < time / 2.0F
            ? Easing.BOUNCE_IN.ease(value * 2.0F, 0.0F, max, time) * 0.5F + min
            : Easing.BOUNCE_OUT.ease(value * 2.0F - time, 0.0F, max, time) * 0.5F + max * 0.5F + min;
      }
   };

   public Easing(String name) {
      this.name = name;
      EASINGS.put(name, this);
   }

   public static Easing valueOf(String name) {
      return EASINGS.get(name);
   }

   public abstract float ease(float var1, float var2, float var3, float var4);

   public float ease(double value, double min, double max, double time) {
      return this.ease((float)value, (float)min, (float)max, (float)time);
   }

   public float ease(float value, float min, float max) {
      return this.ease(value, min, max, 1.0F);
   }

   public float ease(double value, double min, double max) {
      return this.ease(value, min, max, 1.0);
   }

   public float clamped(float value, float min, float max, float time) {
      return this.ease(Mth.clamp(value, 0.0F, time), min, max, time);
   }

   public float clamped(double value, double min, double max, double time) {
      return this.clamped((float)value, (float)min, (float)max, (float)time);
   }

   public float clamped(float value, float min, float max) {
      return this.clamped(value, min, max, 1.0F);
   }

   public float clamped(double value, double min, double max) {
      return this.clamped(value, min, max, 1.0);
   }

   public abstract static class Back extends Easing {
      public static final float DEFAULT_OVERSHOOT = 1.70158F;
      private float overshoot;

      public Back(String name) {
         this(name, 1.70158F);
      }

      public Back(String name, float overshoot) {
         super(name);
         this.overshoot = overshoot;
      }

      public void setOvershoot(float overshoot) {
         this.overshoot = overshoot;
      }

      public float getOvershoot() {
         return this.overshoot;
      }
   }

   public static class BackIn extends Easing.Back {
      public BackIn() {
         super("backIn");
      }

      public BackIn(float overshoot) {
         super("backIn", overshoot);
      }

      @Override
      public float ease(float value, float min, float max, float time) {
         float s = this.getOvershoot();
         float var6;
         return max * (var6 = value / time) * var6 * ((s + 1.0F) * var6 - s) + min;
      }
   }

   public static class BackInOut extends Easing.Back {
      public BackInOut() {
         super("backInOut");
      }

      public BackInOut(float overshoot) {
         super("backInOut", overshoot);
      }

      @Override
      public float ease(float value, float min, float max, float time) {
         float s = this.getOvershoot();
         float var6;
         float var7;
         float var8;
         float var9;
         return (var6 = value / (time / 2.0F)) < 1.0F
            ? max / 2.0F * (var6 * var6 * (((var8 = (float)(s * 1.525)) + 1.0F) * var6 - var8)) + min
            : max / 2.0F * ((var7 = var6 - 2.0F) * var7 * (((var9 = (float)(s * 1.525)) + 1.0F) * var7 + var9) + 2.0F) + min;
      }
   }

   public static class BackOut extends Easing.Back {
      public BackOut() {
         super("backOut");
      }

      public BackOut(float overshoot) {
         super("backOut", overshoot);
      }

      @Override
      public float ease(float value, float min, float max, float time) {
         float s = this.getOvershoot();
         float var6;
         return max * ((var6 = value / time - 1.0F) * var6 * ((s + 1.0F) * var6 + s) + 1.0F) + min;
      }
   }

   public abstract static class Elastic extends Easing {
      private float amplitude;
      private float period;

      public Elastic(String name, float amplitude, float period) {
         super(name);
         this.amplitude = amplitude;
         this.period = period;
      }

      public Elastic(String name) {
         this(name, -1.0F, 0.0F);
      }

      public float getPeriod() {
         return this.period;
      }

      public void setPeriod(float period) {
         this.period = period;
      }

      public float getAmplitude() {
         return this.amplitude;
      }

      public void setAmplitude(float amplitude) {
         this.amplitude = amplitude;
      }
   }

   public static class ElasticIn extends Easing.Elastic {
      public ElasticIn(float amplitude, float period) {
         super("elasticIn", amplitude, period);
      }

      public ElasticIn() {
         super("elasticIn");
      }

      @Override
      public float ease(float value, float min, float max, float time) {
         float a = this.getAmplitude();
         float p = this.getPeriod();
         if (value == 0.0F) {
            return min;
         }

         if ((value = value / time) == 1.0F) {
            return min + max;
         }

         if (p == 0.0F) {
            p = time * 0.3F;
         }

         float s = 0.0F;
         if (a < Math.abs(max)) {
            a = max;
            s = p / 4.0F;
         } else {
            s = p / (float) (Math.PI * 2) * (float)Math.asin(max / a);
         }

         return -(a * (float)Math.pow(2.0, 10.0F * --value) * (float)Math.sin((value * time - s) * (Math.PI * 2) / p)) + min;
      }
   }

   public static class ElasticInOut extends Easing.Elastic {
      public ElasticInOut(float amplitude, float period) {
         super("elasticInOut", amplitude, period);
      }

      public ElasticInOut() {
         super("elasticInOut");
      }

      @Override
      public float ease(float value, float min, float max, float time) {
         float a = this.getAmplitude();
         float p = this.getPeriod();
         if (value == 0.0F) {
            return min;
         }

         if ((value = value / (time / 2.0F)) == 2.0F) {
            return min + max;
         }

         if (p == 0.0F) {
            p = time * 0.45000002F;
         }

         float s = 0.0F;
         if (a < Math.abs(max)) {
            a = max;
            s = p / 4.0F;
         } else {
            s = p / (float) (Math.PI * 2) * (float)Math.asin(max / a);
         }

         return value < 1.0F
            ? -0.5F * (a * (float)Math.pow(2.0, 10.0F * --value) * (float)Math.sin((value * time - s) * (Math.PI * 2) / p)) + min
            : a * (float)Math.pow(2.0, -10.0F * --value) * (float)Math.sin((value * time - s) * (Math.PI * 2) / p) * 0.5F + max + min;
      }
   }

   public static class ElasticOut extends Easing.Elastic {
      public ElasticOut(float amplitude, float period) {
         super("elasticOut", amplitude, period);
      }

      public ElasticOut() {
         super("elasticOut");
      }

      @Override
      public float ease(float value, float min, float max, float time) {
         float a = this.getAmplitude();
         float p = this.getPeriod();
         if (value == 0.0F) {
            return min;
         }

         if ((value = value / time) == 1.0F) {
            return min + max;
         }

         if (p == 0.0F) {
            p = time * 0.3F;
         }

         float s = 0.0F;
         if (a < Math.abs(max)) {
            a = max;
            s = p / 4.0F;
         } else {
            s = p / (float) (Math.PI * 2) * (float)Math.asin(max / a);
         }

         return a * (float)Math.pow(2.0, -10.0F * value) * (float)Math.sin((value * time - s) * (Math.PI * 2) / p) + max + min;
      }
   }
}
