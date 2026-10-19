package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import org.jetbrains.annotations.NotNull;

public interface DiffCalculatorUtil {
   @NotNull
   DiffCalculatorUtil$Companion Companion = DiffCalculatorUtil$Companion.$$INSTANCE;

   int calcRank(int var1, int var2);

   int clickCountLowerBound(int var1, int var2);

   int clickCountUpperBound(int var1, int var2);

   int clickCountSingleSlotToLess(int var1, int var2);

   boolean canRight(int var1, int var2);
}
