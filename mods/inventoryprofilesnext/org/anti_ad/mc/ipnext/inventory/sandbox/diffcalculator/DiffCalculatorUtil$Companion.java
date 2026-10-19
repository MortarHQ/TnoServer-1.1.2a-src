package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

public final class DiffCalculatorUtil$Companion implements DiffCalculatorUtil {
   private DiffCalculatorUtil$Companion() {
   }

   @Override
   public final int calcRank(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.calcRank(this, var1, var2);
   }

   @Override
   public final int clickCountLowerBound(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountLowerBound(this, var1, var2);
   }

   @Override
   public final int clickCountUpperBound(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountUpperBound(this, var1, var2);
   }

   @Override
   public final int clickCountSingleSlotToLess(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.clickCountSingleSlotToLess(this, var1, var2);
   }

   @Override
   public final boolean canRight(int var1, int var2) {
      return DiffCalculatorUtil$DefaultImpls.canRight(this, var1, var2);
   }
}
