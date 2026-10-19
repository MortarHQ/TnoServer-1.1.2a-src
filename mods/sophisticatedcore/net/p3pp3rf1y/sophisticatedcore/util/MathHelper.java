package net.p3pp3rf1y.sophisticatedcore.util;

public class MathHelper {
   private static final float EPSILON = 1.0E-6F;

   private MathHelper() {
   }

   public static int intMaxCappedAddition(int a, int b) {
      return Integer.MAX_VALUE - a < b ? Integer.MAX_VALUE : a + b;
   }

   public static int intMaxCappedMultiply(int a, int b) {
      return Integer.MAX_VALUE / a < b ? Integer.MAX_VALUE : a * b;
   }

   public static boolean epsilonEquals(float a, float b) {
      return Math.abs(a - b) < 1.0E-6F;
   }
}
