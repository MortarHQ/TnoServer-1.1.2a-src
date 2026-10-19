package team.creative.creativecore.common.util.math;

import net.minecraft.util.Mth;

public class Maths {
   public static final float EPSILON = 0.001F;
   public static final float EPSILON_UP = 999.99994F;
   public static final double EPSILON_DOUBLE = 0.001;
   public static final double EPSILON_UP_DOUBLE = 1000.0;
   public static final float EPSILON_PRECISE = 1.0E-5F;
   public static final float EPSILON_PRECISE_UP = 100000.0F;
   public static final double EPSILON_PRECISE_DOUBLE = 1.0E-5;
   public static final double EPSILON_PRECISE_UP_DOUBLE = 99999.99999999999;

   public static boolean smallerThanAndEquals(double a, double b) {
      return a < b || equals(a, b);
   }

   public static boolean greaterThanAndEquals(double a, double b) {
      return a > b || equals(a, b);
   }

   public static boolean equals(double a, double b) {
      return a == b || Math.abs(a - b) < 0.001;
   }

   public static boolean equals(double a, double b, double epsilon) {
      return a == b || Math.abs(a - b) < epsilon;
   }

   public static boolean equals(float a, float b) {
      return a == b || Math.abs(a - b) < 0.001F;
   }

   public static boolean equals(float a, float b, float epsilon) {
      return a == b || Math.abs(a - b) < epsilon;
   }

   public static long tickToMs(int ticks) {
      return ticks * 50L;
   }

   public static int msToTick(long ms) {
      return (int)(ms / 50L);
   }

   public static double round(double value) {
      return Mth.floor(99999.99999999999 * value + 0.5) * 1.0E-5;
   }

   public static float round(float value) {
      return Mth.floor(100000.0F * value + 0.5F) * 1.0E-5F;
   }

   public static double safeDivide(double v1, double v2) {
      return v1 != 0.0 && v2 != 0.0 ? v1 / v2 : 0.0;
   }

   public static long safePercent(long v1, long v2) {
      return v1 != 0L && v2 != 0L ? v1 % v2 : 0L;
   }

   public static float safeRound(double value) {
      return value != 0.0 ? (float)Math.round(value * 100.0) / 100.0F : 0.0F;
   }

   public static int min(int a, int b, int c) {
      return a <= b ? (a <= c ? a : c) : (b <= c ? b : c);
   }

   public static float min(float a, float b, float c) {
      return a <= b ? (a <= c ? a : c) : (b <= c ? b : c);
   }

   public static double min(double a, double b, double c) {
      return a <= b ? (a <= c ? a : c) : (b <= c ? b : c);
   }

   public static long min(long a, long b, long c) {
      return a <= b ? (a <= c ? a : c) : (b <= c ? b : c);
   }

   public static int max(int a, int b, int c) {
      return a >= b ? (a >= c ? a : c) : (b >= c ? b : c);
   }

   public static float max(float a, float b, float c) {
      return a >= b ? (a >= c ? a : c) : (b >= c ? b : c);
   }

   public static double max(double a, double b, double c) {
      return a >= b ? (a >= c ? a : c) : (b >= c ? b : c);
   }

   public static long max(long a, long b, long c) {
      return a >= b ? (a >= c ? a : c) : (b >= c ? b : c);
   }
}
