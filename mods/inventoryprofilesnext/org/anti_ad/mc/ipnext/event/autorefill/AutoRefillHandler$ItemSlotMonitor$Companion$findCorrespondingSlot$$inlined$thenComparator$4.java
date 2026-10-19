package org.anti_ad.mc.ipnext.event.autorefill;

import java.util.Comparator;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.config.AutoRefillSettings;
import org.anti_ad.mc.ipnext.item.ItemStack;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$thenComparator$1\n+ 2 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor$Companion\n*L\n1#1,197:1\n698#2,5:198\n*E\n"
)
public final class AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$4 implements Comparator {
   public AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$4(Comparator var1) {
      this.$this_thenComparator = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      int var3;
      if ((var3 = this.$this_thenComparator.compare(var1, var2)) != 0) {
         return var3;
      }

      IndexedValue var4 = var2;
      var2 = (IndexedValue)var1;
      return AutoRefillSettings.INSTANCE.getAUTO_REFILL_PREFER_SMALLER_STACKS().getBooleanValue()
         ? ((ItemStack)var2.getValue()).getCount() - ((ItemStack)var4.getValue()).getCount()
         : ((ItemStack)var4.getValue()).getCount() - ((ItemStack)var2.getValue()).getCount();
   }
}
