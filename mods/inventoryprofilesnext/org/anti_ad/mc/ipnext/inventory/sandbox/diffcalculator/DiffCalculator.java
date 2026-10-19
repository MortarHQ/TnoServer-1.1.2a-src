package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.annotation.MayThrow;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandbox;
import org.jetbrains.annotations.NotNull;

public final class DiffCalculator {
   @NotNull
   public static final DiffCalculator INSTANCE = new DiffCalculator();

   private DiffCalculator() {
   }

   @MayThrow
   public final void apply(@NotNull ContainerSandbox var1, @NotNull ItemTracker var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      long var3 = System.nanoTime();

      try {
         new GenericDiffCalculatorInstance(var1, var2).run();
      } finally {
         long var6 = System.nanoTime() - var3;
         Log.INSTANCE.debug("diff brain costs " + var6 / 1000000.0 + " ms");
      }
   }
}
