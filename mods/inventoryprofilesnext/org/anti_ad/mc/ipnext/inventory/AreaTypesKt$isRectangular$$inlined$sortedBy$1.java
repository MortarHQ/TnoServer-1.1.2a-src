package org.anti_ad.mc.ipnext.inventory;

import java.util.Comparator;
import kotlin.Pair;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/AreaTypesKt\n*L\n1#1,102:1\n315#2:103\n*E\n"
)
public final class AreaTypesKt$isRectangular$$inlined$sortedBy$1 implements Comparator {
   @Override
   public final int compare(Object var1, Object var2) {
      Comparable var10000 = ((Number)var1.component1()).intValue();
      var1 = (Pair)var2;
      Comparable var4 = var10000;
      return ComparisonsKt.compareValues(var4, ((Number)var1.component1()).intValue());
   }
}
