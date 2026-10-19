package org.anti_ad.mc.ipnext.item;

import java.util.Comparator;
import kotlin.jvm.internal.SourceDebugExtension;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$thenComparator$1\n+ 2 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n*L\n1#1,197:1\n138#2:198\n*E\n"
)
public final class ComponentUtils$special$$inlined$thenComparator$1 implements Comparator {
   public ComponentUtils$special$$inlined$thenComparator$1(Comparator var1) {
      this.$this_thenComparator = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      int var3;
      if ((var3 = this.$this_thenComparator.compare(var1, var2)) != 0) {
         return var3;
      }

      ItemType var4 = var2;
      var2 = (ItemType)var1;
      return ComponentUtils.INSTANCE.compareComponents(var2, var4);
   }
}
