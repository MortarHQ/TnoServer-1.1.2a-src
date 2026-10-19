package org.anti_ad.mc.ipnext.inventory;

import java.util.Comparator;
import kotlin.collections.IndexedValue;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.math2d.Point;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/AreaTypesKt\n*L\n1#1,102:1\n315#2:103\n*E\n"
)
public final class AreaTypesKt$isRectangular$lambda$5$$inlined$sortedBy$1 implements Comparator {
   @Override
   public final int compare(Object var1, Object var2) {
      Comparable var10000 = ((Point)var1.getValue()).getX();
      var1 = (IndexedValue)var2;
      Comparable var4 = var10000;
      return ComparisonsKt.compareValues(var4, ((Point)var1.getValue()).getX());
   }
}
