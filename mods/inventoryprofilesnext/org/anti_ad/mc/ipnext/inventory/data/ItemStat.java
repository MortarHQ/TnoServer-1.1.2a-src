package org.anti_ad.mc.ipnext.inventory.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nItemStat.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemStat.kt\norg/anti_ad/mc/ipnext/inventory/data/ItemStat\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Grouping.kt\nkotlin/collections/GroupingKt__GroupingKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,77:1\n827#2:78\n855#2,2:79\n1550#2:81\n1563#2:91\n1634#2,3:92\n112#3:82\n53#3:83\n80#3,6:84\n1#4:90\n*S KotlinDebug\n*F\n+ 1 ItemStat.kt\norg/anti_ad/mc/ipnext/inventory/data/ItemStat\n*L\n31#1:78\n31#1:79,2\n33#1:81\n54#1:91\n54#1:92,3\n34#1:82\n34#1:83\n34#1:84,6\n*E\n"
)
public final class ItemStat {
   @NotNull
   public static final ItemStat$Companion Companion = new ItemStat$Companion(null);
   @NotNull
   private final List items;
   @NotNull
   private final Map itemGroups;

   public ItemStat(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      Iterable var11 = var1;
      ItemStat var10 = this;
      var var2 = new ArrayList();
      Iterator var3 = var11.iterator();

      while (var3.hasNext()) {
         Object var12;
         if (!ItemStackExtensionsKt.isEmpty((ItemStack)((IndexedValue)(var12 = var3.next())).getValue())) {
            var2.add(var12);
         }
      }

      var10.items = var2;
      var11 = this.items;
      Grouping var14 = new ItemStat$special$$inlined$groupingBy$1(var11);
      var10 = this;
      Map var19 = new LinkedHashMap();
      Iterator var4 = var14.sourceIterator();

      while (var4.hasNext()) {
         ItemType var15 = (ItemType)var4.next();
         IndexedValue var5 = (IndexedValue)var14.keyOf(var15);
         int var6 = (int)var19.get(var5);
         boolean var16 = var6 == null && !var19.containsKey(var5);
         Object var20 = var15;
         var6 = var6;
         Object var7 = var5;
         Object var8 = var5;
         Map var9 = var19;
         Object var26;
         if (var16) {
            var15 = (ItemType)var7;
            var26 = new ItemStat$GroupEntry(var15);
         } else {
            var26 = var6;
         }

         var5 = (IndexedValue)var20;
         ItemStat$GroupEntry var18 = (ItemStat$GroupEntry)var26;
         var6 = var5.component1();
         ItemStack var22 = (ItemStack)var5.component2();
         var18.add(var6, var22);
         var9.put(var8, var18);
      }

      var10.itemGroups = var19;
   }

   @NotNull
   public final Map getItemGroups() {
      return this.itemGroups;
   }

   public final int getTotalItemCount() {
      Iterable var1 = this.itemGroups.values();
      int var2 = 0;

      for (Object var3 : var1) {
         ItemStat$GroupEntry var5 = (ItemStat$GroupEntry)var3;
         int var7 = var2;
         var2 = var5.getItemCount();
         var2 = var7 + var2;
      }

      return var2;
   }

   public final int getTotalSlotCount() {
      Iterable var1 = this.itemGroups.values();
      int var2 = 0;

      for (Object var3 : var1) {
         ItemStat$GroupEntry var5 = (ItemStat$GroupEntry)var3;
         int var7 = var2;
         var2 = var5.getSlotCount();
         var2 = var7 + var2;
      }

      return var2;
   }

   public final int getTotalMinSlotCount() {
      Iterable var1 = this.itemGroups.values();
      int var2 = 0;

      for (Object var3 : var1) {
         ItemStat$GroupEntry var5 = (ItemStat$GroupEntry)var3;
         int var7 = var2;
         var2 = var5.getMinSlotCount();
         var2 = var7 + var2;
      }

      return var2;
   }

   public final int getTotalMaxSlotCount() {
      return this.getTotalItemCount();
   }

   @NotNull
   public final Set getItemTypes() {
      return this.itemGroups.keySet();
   }

   @NotNull
   public final List getGroupEntries() {
      Iterable var1;
      Iterable var2 = var1 = this.getItemTypes();
      var1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (Object var3 : var2) {
         ItemType var7 = (ItemType)var3;
         ArrayList var4 = var1;
         var4.add((ItemStat$GroupEntry)MapsKt.getValue(this.itemGroups, var7));
      }

      return var1;
   }
}
