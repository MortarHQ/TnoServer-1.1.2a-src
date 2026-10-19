package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import org.jetbrains.annotations.NotNull;

public final class DiffCalculatorUtil$DefaultImpls {
   public static int calcRank(@NotNull DiffCalculatorUtil var0, int var1, int var2) {
      if (var1 == var2) {
         return 0;
      } else if (var1 + 1 == var2) {
         return 10;
      } else if (var2 == 0) {
         return 11;
      } else if (var1 / 2 == var2) {
         return 12;
      } else if (var1 < var2) {
         return 20;
      } else if (var2 == 1) {
         return 30;
      } else if (var1 / 2 + 1 == var2) {
         return 31;
      } else if (var1 / 4 == var2) {
         return 32;
      } else if (var1 > var2) {
         return 40;
      } else {
         throw new AssertionError("unreachable");
      }
   }

   public static int clickCountLowerBound(@NotNull DiffCalculatorUtil var0, int var1, int var2) {
      switch (var0.calcRank(var1, var2) / 10) {
         case 0:
            return 0;
         case 1:
            return 1;
         case 2:
            return 1;
         case 3:
            return 2;
         case 4:
            return 2;
         default:
            throw new AssertionError("unreachable");
      }
   }

   public static int clickCountUpperBound(@NotNull DiffCalculatorUtil var0, int var1, int var2) {
      switch (var0.calcRank(var1, var2) / 10) {
         case 0:
            return 0;
         case 1:
            return 1;
         case 2:
            return var2 - var1;
         case 3:
            return 2;
         case 4:
            return clickCountUpperBoundNGreaterThanG(var0, var1, var2);
         default:
            throw new AssertionError("unreachable");
      }
   }

   private static int clickCountUpperBoundNGreaterThanG(DiffCalculatorUtil var0, int var1, int var2) {
      return 1 + Math.min(var0.clickCountUpperBound(0, var2), var0.clickCountUpperBound(var1 / 2, var2));
   }

   public static int clickCountSingleSlotToLess(@NotNull DiffCalculatorUtil var0, int var1, int var2) {
      if (var1 < var2) {
         throw new IllegalStateException("from < to".toString());
      }

      if (var1 == var2) {
         return 0;
      }

      int var3 = var1 / 2;
      return 1 + (var3 <= var2 ? var2 - var3 : var2);
   }

   public static boolean canRight(@NotNull DiffCalculatorUtil var0, int var1, int var2) {
      if (var1 < var2) {
         throw new IllegalStateException("from < to".toString());
      } else {
         return var1 / 2 <= var2;
      }
   }
}
