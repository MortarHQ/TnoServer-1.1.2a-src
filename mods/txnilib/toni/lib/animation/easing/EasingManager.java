package toni.lib.animation.easing;

import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import java.util.List;
import java.util.function.Function;

public class EasingManager {
   static Double2DoubleFunction quart = poly(4.0);
   static Double2DoubleFunction quint = poly(5.0);
   public static Function<EasingFunctionArgs, Double2DoubleFunction> getEasingFunction = Memoizer.memoize(EasingManager::getEasingFuncImpl);

   public static double ease(double number, EasingType easingType, List<Double> easingArgs) {
      Double firstArg = easingArgs != null && easingArgs.size() >= 1 ? easingArgs.get(0) : null;
      return (Double)getEasingFunction.apply(new EasingFunctionArgs(easingType, firstArg)).apply(number);
   }

   public static double ease(double number, EasingType easingType) {
      return (Double)getEasingFunction.apply(new EasingFunctionArgs(easingType, null)).apply(number);
   }

   public static double ease(double number, EasingType easingType, double arg) {
      return (Double)getEasingFunction.apply(new EasingFunctionArgs(easingType, arg)).apply(number);
   }

   static Double2DoubleFunction getEasingFuncImpl(EasingFunctionArgs args) {
      return switch (args.easingType()) {
         case Step -> in(step(args.arg0()));
         case EaseInSine -> in(EasingManager::sin);
         case EaseOutSine -> out(EasingManager::sin);
         case EaseInOutSine -> inOut(EasingManager::sin);
         case EaseInQuad -> in(EasingManager::quad);
         case EaseOutQuad -> out(EasingManager::quad);
         case EaseInOutQuad -> inOut(EasingManager::quad);
         case EaseInCubic -> in(EasingManager::cubic);
         case EaseOutCubic -> out(EasingManager::cubic);
         case EaseInOutCubic -> inOut(EasingManager::cubic);
         case EaseInExpo -> in(EasingManager::exp);
         case EaseOutExpo -> out(EasingManager::exp);
         case EaseInOutExpo -> inOut(EasingManager::exp);
         case EaseInCirc -> in(EasingManager::circle);
         case EaseOutCirc -> out(EasingManager::circle);
         case EaseInOutCirc -> inOut(EasingManager::circle);
         case EaseInQuart -> in(quart);
         case EaseOutQuart -> out(quart);
         case EaseInOutQuart -> inOut(quart);
         case EaseInQuint -> in(quint);
         case EaseOutQuint -> out(quint);
         case EaseInOutQuint -> inOut(quint);
         case EaseInBack -> in(back(args.arg0()));
         case EaseOutBack -> out(back(args.arg0()));
         case EaseInOutBack -> inOut(back(args.arg0()));
         case EaseInElastic -> in(elastic(args.arg0()));
         case EaseOutElastic -> out(elastic(args.arg0()));
         case EaseInOutElastic -> inOut(elastic(args.arg0()));
         case EaseInBounce -> in(bounce(args.arg0()));
         case EaseOutBounce -> out(bounce(args.arg0()));
         case EaseInOutBounce -> inOut(bounce(args.arg0()));
         default -> in(EasingManager::linear);
      };
   }

   static Double2DoubleFunction in(Double2DoubleFunction easing) {
      return easing;
   }

   static Double2DoubleFunction out(Double2DoubleFunction easing) {
      return t -> 1.0 - (Double)easing.apply(1.0 - t);
   }

   static Double2DoubleFunction inOut(Double2DoubleFunction easing) {
      return t -> t < 0.5 ? (Double)easing.apply(t * 2.0) / 2.0 : 1.0 - (Double)easing.apply((1.0 - t) * 2.0) / 2.0;
   }

   static Double2DoubleFunction step0() {
      return n -> n > 0.0 ? 1.0 : 0.0;
   }

   static Double2DoubleFunction step1() {
      return n -> n >= 1.0 ? 1.0 : 0.0;
   }

   static double linear(double t) {
      return t;
   }

   static double quad(double t) {
      return t * t;
   }

   static double cubic(double t) {
      return t * t * t;
   }

   static Double2DoubleFunction poly(double n) {
      return t -> Math.pow(t, n);
   }

   static double sin(double t) {
      return 1.0 - Math.cos((float)(t * Math.PI / 2.0));
   }

   static double circle(double t) {
      return 1.0 - Math.sqrt(1.0 - t * t);
   }

   static double exp(double t) {
      return Math.pow(2.0, 10.0 * (t - 1.0));
   }

   static Double2DoubleFunction elastic(Double bounciness) {
      double p = (bounciness == null ? 1.0 : bounciness) * Math.PI;
      return t -> 1.0 - Math.pow(Math.cos((float)(t * Math.PI / 2.0)), 3.0) * Math.cos((float)(t * p));
   }

   static Double2DoubleFunction back(Double s) {
      double p = s == null ? 1.70158 : s * 1.70158;
      return t -> t * t * ((p + 1.0) * t - p);
   }

   public static Double2DoubleFunction bounce(Double s) {
      double k = s == null ? 0.5 : s;
      Double2DoubleFunction q = x -> 7.5625 * x * x;
      Double2DoubleFunction w = x -> 30.25 * k * Math.pow(x - 0.5454545454545454, 2.0) + 1.0 - k;
      Double2DoubleFunction r = x -> 121.0 * k * k * Math.pow(x - 0.8181818181818182, 2.0) + 1.0 - k * k;
      Double2DoubleFunction t = x -> 484.0 * k * k * k * Math.pow(x - 0.9545454545454546, 2.0) + 1.0 - k * k * k;
      return x -> min((Double)q.apply(x), (Double)w.apply(x), (Double)r.apply(x), (Double)t.apply(x));
   }

   static Double2DoubleFunction step(Double stepArg) {
      int steps = stepArg != null ? stepArg.intValue() : 2;
      double[] intervals = stepRange(steps);
      return t -> intervals[findIntervalBorderIndex(t, intervals, false)];
   }

   static double min(double a, double b, double c, double d) {
      return Math.min(Math.min(a, b), Math.min(c, d));
   }

   static int findIntervalBorderIndex(double point, double[] intervals, boolean useRightBorder) {
      if (point < intervals[0]) {
         return 0;
      }

      if (point > intervals[intervals.length - 1]) {
         return intervals.length - 1;
      }

      int indexOfNumberToCompare = 0;
      int leftBorderIndex = 0;
      int rightBorderIndex = intervals.length - 1;

      while (rightBorderIndex - leftBorderIndex != 1) {
         indexOfNumberToCompare = leftBorderIndex + (rightBorderIndex - leftBorderIndex) / 2;
         if (point >= intervals[indexOfNumberToCompare]) {
            leftBorderIndex = indexOfNumberToCompare;
         } else {
            rightBorderIndex = indexOfNumberToCompare;
         }
      }

      return useRightBorder ? rightBorderIndex : leftBorderIndex;
   }

   static double[] stepRange(int steps) {
      double stop = 1.0;
      if (steps < 2) {
         throw new IllegalArgumentException("steps must be > 2, got:" + steps);
      }

      double stepLength = 1.0 / steps;
      double[] stepArray = new double[steps];

      for (int i = 0; i < steps; i++) {
         stepArray[i] = i * stepLength;
      }

      return stepArray;
   }
}
