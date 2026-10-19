package org.anti_ad.mc.ipnext.inventory.data;

import java.util.Iterator;
import kotlin.collections.Grouping;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemStack;

@SourceDebugExtension(
   "SMAP\n_Collections.kt\nKotlin\n*S Kotlin\n*F\n+ 1 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt$groupingBy$1\n+ 2 ItemStat.kt\norg/anti_ad/mc/ipnext/inventory/data/ItemStat\n*L\n1#1,1552:1\n33#2:1553\n*E\n"
)
public final class ItemStat$special$$inlined$groupingBy$1 implements Grouping {
   public ItemStat$special$$inlined$groupingBy$1(Iterable var1) {
      this.$this_groupingBy = var1;
   }

   public final Iterator sourceIterator() {
      return this.$this_groupingBy.iterator();
   }

   public final Object keyOf(Object var1) {
      return ((ItemStack)((IndexedValue)var1).getValue()).getItemType();
   }
}
