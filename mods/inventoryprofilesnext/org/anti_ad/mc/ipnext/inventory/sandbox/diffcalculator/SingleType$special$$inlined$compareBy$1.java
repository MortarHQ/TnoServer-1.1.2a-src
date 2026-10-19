package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 ScoreBasedDualDiffCalculatorInstance.kt\norg/anti_ad/mc/ipnext/inventory/sandbox/diffcalculator/SingleType\n*L\n1#1,102:1\n136#2:103\n*E\n"
)
public final class SingleType$special$$inlined$compareBy$1 implements Comparator {
   @Override
   public final int compare(Object var1, Object var2) {
      Comparable var10000 = var1.getFScore();
      var1 = (SingleType$Node)var2;
      Comparable var4 = var10000;
      return ComparisonsKt.compareValues(var4, var1.getFScore());
   }
}
