package org.anti_ad.mc.ipnext.debug;

import java.util.Comparator;
import java.util.List;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1\n+ 2 BlocksWithScreensUtils.kt\norg/anti_ad/mc/ipnext/debug/BlocksWithScreensUtilsKt\n*L\n1#1,121:1\n44#2:122\n*E\n"
)
public final class BlocksWithScreensUtilsKt$generateCommands$$inlined$sortedByDescending$1 implements Comparator {
   @Override
   public final int compare(Object var1, Object var2) {
      Comparable var10000 = var2.size();
      var2 = (List)var1;
      Comparable var3 = var10000;
      return ComparisonsKt.compareValues(var3, var2.size());
   }
}
