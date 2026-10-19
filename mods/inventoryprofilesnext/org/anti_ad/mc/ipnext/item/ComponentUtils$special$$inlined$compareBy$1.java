package org.anti_ad.mc.ipnext.item;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n*L\n1#1,102:1\n100#2:103\n*E\n"
)
public final class ComponentUtils$special$$inlined$compareBy$1 implements Comparator {
   @Override
   public final int compare(Object var1, Object var2) {
      Comparable var10000 = var1.getContents().toString();
      var1 = (Component)var2;
      Comparable var4 = var10000;
      return ComparisonsKt.compareValues(var4, var1.getContents().toString());
   }
}
