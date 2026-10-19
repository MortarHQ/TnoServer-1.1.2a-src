package org.anti_ad.mc.ipnext.inventory.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;
import org.anti_ad.mc.ipnext.util.MutableBucket;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nItemBucket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemBucket.kt\norg/anti_ad/mc/ipnext/inventory/data/MutableItemBucket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,89:1\n1869#2,2:90\n1740#2,3:92\n1869#2,2:95\n1#3:97\n126#4:98\n153#4,3:99\n*S KotlinDebug\n*F\n+ 1 ItemBucket.kt\norg/anti_ad/mc/ipnext/inventory/data/MutableItemBucket\n*L\n57#1:90,2\n64#1:92,3\n71#1:95,2\n83#1:98\n83#1:99,3\n*E\n"
)
public final class MutableItemBucket extends MutableBucket implements ItemBucket {
   private MutableItemBucket(Map var1) {
      super(var1);
   }

   public MutableItemBucket() {
   }

   protected final boolean validateEmpty(@NotNull ItemType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return ItemTypeExtensionsKt.isEmpty(var1);
   }

   public final boolean add(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.add(var1.getItemType(), var1.getCount());
   }

   public final void addAll(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");

      for (ItemStack var2 : var1) {
         this.add(var2);
      }
   }

   @Override
   public final boolean contains(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.contains(var1.getItemType(), var1.getCount());
   }

   @Override
   public final boolean containsAll(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Iterable var3;
      if (!((var3 = var1) instanceof Collection) || !((Collection)var3).isEmpty()) {
         for (ItemStack var2 : var3) {
            if (!this.contains(var2)) {
               return false;
            }
         }
      }

      return true;
   }

   public final boolean remove(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.remove(var1.getItemType(), var1.getCount());
   }

   public final void removeAll(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");

      for (ItemStack var2 : var1) {
         this.remove(var2);
      }
   }

   @NotNull
   @Override
   public final ItemBucket minus(@NotNull ItemBucket var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      MutableItemBucket var2;
      (var2 = this.copyAsMutable()).removeAll(var1);
      return var2;
   }

   @NotNull
   @Override
   public final ItemBucket copy() {
      return this.copyAsMutable();
   }

   @NotNull
   @Override
   public final MutableItemBucket copyAsMutable() {
      return new MutableItemBucket(this.getAsMap());
   }

   @NotNull
   @Override
   public final String toString() {
      Map var1;
      Map var2 = var1 = this.getAsMap();
      var var5 = new ArrayList(var1.size());

      for (Entry var3 : var2.entrySet()) {
         ArrayList var4 = var5;
         var4.add(ItemStack.Companion.invoke((ItemType)var3.getKey(), ((Number)var3.getValue()).intValue()));
      }

      return var5.toString();
   }
}
