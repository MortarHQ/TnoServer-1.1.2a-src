package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import org.anti_ad.mc.ipnext.config.DiffCalculatorType;

// $VF: synthetic class
public final class GenericDiffCalculatorInstance$WhenMappings {
   static {
      int[] var0 = new int[DiffCalculatorType.values().length];

      try {
         var0[DiffCalculatorType.SIMPLE.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         var0[DiffCalculatorType.SCORE_BASED_SINGLE.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         var0[DiffCalculatorType.SCORE_BASED_DUAL.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
