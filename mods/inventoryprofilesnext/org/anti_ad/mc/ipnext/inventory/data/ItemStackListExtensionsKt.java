package org.anti_ad.mc.ipnext.inventory.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nItemStackListExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemStackListExtensions.kt\norg/anti_ad/mc/ipnext/inventory/data/ItemStackListExtensionsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,60:1\n1563#2:61\n1634#2,3:62\n827#2:65\n855#2,2:66\n1563#2:68\n1634#2,2:69\n1636#2:72\n1869#2,2:73\n1#3:71\n*S KotlinDebug\n*F\n+ 1 ItemStackListExtensions.kt\norg/anti_ad/mc/ipnext/inventory/data/ItemStackListExtensionsKt\n*L\n32#1:61\n32#1:62,3\n38#1:65\n38#1:66,2\n41#1:68\n41#1:69,2\n41#1:72\n51#1:73,2\n*E\n"
)
public final class ItemStackListExtensionsKt {
   @NotNull
   public static final List copyAsMutable(@NotNull List var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Iterable var4;
      Iterable var1 = var4 = var0;
      var0 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));

      for (ItemStack var2 : var1) {
         var2 = var2;
         ArrayList var3 = var0;
         var3.add(var2.copyAsMutable());
      }

      return var0;
   }

   @NotNull
   public static final List filterNotEmpty(@NotNull List var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Iterable var3 = var0;
      var var1 = new ArrayList();
      Iterator var4 = var3.iterator();

      while (var4.hasNext()) {
         Object var2;
         if (!ItemStackExtensionsKt.isEmpty((ItemStack)(var2 = var4.next()))) {
            var1.add(var2);
         }
      }

      return var1;
   }

   @NotNull
   public static final Set itemTypes(@NotNull List var0, boolean var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Iterable var5;
      Iterable var2 = var5 = filterNotEmpty(var0);
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));

      for (ItemStack var3 : var2) {
         var3 = var3;
         ArrayList var4 = var6;
         ItemType var9;
         (var9 = var3.getItemType()).setIgnoreDurability(var1);
         var4.add(var9);
      }

      return CollectionsKt.toSet(var6);
   }

   @NotNull
   public static final ItemBucket collect(@NotNull List var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      MutableItemBucket var1;
      (var1 = new MutableItemBucket()).addAll(var0);
      return var1;
   }

   @NotNull
   public static final ItemBucket processAndCollect(@NotNull List var0, @NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      MutableItemBucket var2;
      MutableItemBucket var3 = var2 = new MutableItemBucket();

      for (ItemStack var4 : var0) {
         var3.add((ItemStack)var1.invoke(var4));
      }

      return var2;
   }

   @NotNull
   public static final ItemStat stat(@NotNull List var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return ItemStat.Companion.invoke(var0);
   }
}
