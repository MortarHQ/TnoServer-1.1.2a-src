package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$thenBy$1\n+ 2 ScoreBasedDualDiffCalculatorInstance.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/SingleType\n*L\n1#1,145:1\n137#2:146\n*E\n"
)
public final class SingleType$special$$inlined$thenBy$1 implements Comparator {
   public SingleType$special$$inlined$thenBy$1(Comparator var1) {
      this.$this_thenBy = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      int var3;
      if ((var3 = this.$this_thenBy.compare(var1, (SingleType$Node)var2)) != 0) {
         return var3;
      }

      Comparable var10000 = var1.getUpperTotal();
      var1 = (SingleType$Node)var2;
      Comparable var5 = var10000;
      return ComparisonsKt.compareValues(var5, var1.getUpperTotal());
   }
}
