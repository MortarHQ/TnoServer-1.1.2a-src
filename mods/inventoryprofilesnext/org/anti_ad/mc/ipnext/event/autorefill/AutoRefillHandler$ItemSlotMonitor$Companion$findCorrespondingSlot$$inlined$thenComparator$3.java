package org.anti_ad.mc.ipnext.event.autorefill;

import java.util.Comparator;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.rule.file.RuleFileRegister;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$thenComparator$1\n+ 2 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor$Companion\n*L\n1#1,197:1\n694#2,3:198\n*E\n"
)
public final class AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$3 implements Comparator {
   public AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$3(Comparator var1) {
      this.$this_thenComparator = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      int var3;
      if ((var3 = this.$this_thenComparator.compare(var1, var2)) != 0) {
         return var3;
      }

      IndexedValue var4 = (IndexedValue)var2;
      ItemType var6 = ((ItemStack)((IndexedValue)var1).getValue()).getItemType();
      ItemType var5 = ((ItemStack)var4.getValue()).getItemType();
      return RuleFileRegister.INSTANCE.getCustomRuleOrEmpty("auto_refill_best").compare(var6, var5);
   }
}
