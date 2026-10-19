package org.anti_ad.mc.ipnext.item;

import java.util.Comparator;
import java.util.List;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$thenBy$1\n+ 2 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n*L\n1#1,145:1\n101#2:146\n*E\n"
)
public final class ComponentUtils$special$$inlined$thenBy$1 implements Comparator {
   public ComponentUtils$special$$inlined$thenBy$1(Comparator var1) {
      this.$this_thenBy = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      int var3;
      if ((var3 = this.$this_thenBy.compare(var1, (Component)var2)) != 0) {
         return var3;
      }

      List var10000 = var1.getSiblings();
      Comparable var6 = var10000 != null ? var10000.size() : 0;
      var1 = (Component)var2;
      Comparable var5 = var6;
      var10000 = var1.getSiblings();
      return ComparisonsKt.compareValues(var5, var10000 != null ? var10000.size() : 0);
   }
}
