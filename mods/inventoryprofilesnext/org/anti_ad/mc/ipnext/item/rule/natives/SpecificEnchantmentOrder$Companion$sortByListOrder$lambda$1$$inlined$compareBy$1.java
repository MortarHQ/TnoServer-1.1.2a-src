package org.anti_ad.mc.ipnext.item.rule.natives;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Comparator;
import java.util.List;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 SpecificEnchantmentOrder.kt\norg/anti_ad/mc/ipnext/item/rule/natives/SpecificEnchantmentOrder$Companion\n*L\n1#1,102:1\n44#2:103\n*E\n"
)
public final class SpecificEnchantmentOrder$Companion$sortByListOrder$lambda$1$$inlined$compareBy$1 implements Comparator {
   public SpecificEnchantmentOrder$Companion$sortByListOrder$lambda$1$$inlined$compareBy$1(List var1) {
      this.$order$inlined = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      var1 = var1;
      Comparable var10000 = this.$order$inlined.indexOf(var1.getKey());
      var1 = (Entry)var2;
      Comparable var5 = var10000;
      return ComparisonsKt.compareValues(var5, this.$order$inlined.indexOf(var1.getKey()));
   }
}
