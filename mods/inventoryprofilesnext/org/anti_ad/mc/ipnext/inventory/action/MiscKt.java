package org.anti_ad.mc.ipnext.inventory.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.random.Random.Default;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.inventory.data.ItemStackListExtensionsKt;
import org.anti_ad.mc.ipnext.inventory.data.ItemStat;
import org.anti_ad.mc.ipnext.inventory.data.ItemStat$GroupEntry;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nMisc.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Misc.kt\norg/anti_ad/mc/ipnext/inventory/action/MiscKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,200:1\n130#1,2:230\n132#1,2:233\n134#1,3:249\n138#1:253\n130#1,2:254\n132#1,2:257\n134#1,3:264\n138#1:268\n130#1,2:269\n132#1,2:272\n134#1,3:279\n138#1:283\n130#1,2:284\n132#1,2:287\n134#1,3:303\n138#1:307\n1374#2:201\n1460#2,2:202\n1563#2:204\n1634#2,3:205\n1462#2,3:208\n1563#2:212\n1634#2,3:213\n3550#2,7:216\n3550#2,7:223\n3550#2,5:244\n3556#2:252\n3550#2,5:259\n3556#2:267\n3550#2,5:274\n3556#2:282\n3550#2,5:298\n3556#2:306\n1563#2:308\n1634#2,3:309\n3581#2,10:312\n1#3:211\n1#3:232\n1#3:256\n1#3:271\n1#3:286\n98#4,9:235\n98#4,9:289\n*S KotlinDebug\n*F\n+ 1 Misc.kt\norg/anti_ad/mc/ipnext/inventory/action/MiscKt\n*L\n142#1:230,2\n142#1:233,2\n142#1:249,3\n142#1:253\n152#1:254,2\n152#1:257,2\n152#1:264,3\n152#1:268\n159#1:269,2\n159#1:272,2\n159#1:279,3\n159#1:283\n166#1:284,2\n166#1:287,2\n166#1:303,3\n166#1:307\n99#1:201\n99#1:202,2\n100#1:204\n100#1:205,3\n99#1:208,3\n114#1:212\n114#1:213,3\n122#1:216,7\n133#1:223,7\n142#1:244,5\n142#1:252\n152#1:259,5\n152#1:267\n159#1:274,5\n159#1:282\n166#1:298,5\n166#1:306\n177#1:308\n177#1:309,3\n177#1:312,10\n142#1:232\n152#1:256\n159#1:271\n166#1:286\n144#1:235,9\n168#1:289,9\n*E\n"
)
public final class MiscKt {
   @NotNull
   private static final Default random = Random.Default;

   @NotNull
   public static final List flatten(@NotNull List var0, @Nullable Integer var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Iterable var2 = var0;
      var0 = new ArrayList();
      Iterator var12 = var2.iterator();

      while (var12.hasNext()) {
         Pair var3;
         ItemType var4 = (ItemType)(var3 = (Pair)var12.next()).component1();
         Iterable var14;
         Iterable var5 = var14 = (Iterable & List)var3.component2();
         var var15 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var14, 10));

         for (Object var6 : var5) {
            int var20 = ((Number)var6).intValue();
            ArrayList var7 = var15;
            var7.add(ItemStack.Companion.invoke(var4, var20));
         }

         var var16 = var15;
         CollectionsKt.addAll(var0, var16);
      }

      var0 = var0;
      if (var1 == null) {
         return var0;
      }

      Collection var10000 = var0;
      int var10 = var1 - var0.size();
      Collection var17 = var10000;
      ArrayList var11 = new ArrayList(var10);

      for (int var13 = 0; var13 < var10; var13++) {
         ArrayList var18 = var11;
         var18.add(ItemStackExtensionsKt.getEMPTY(ItemStack.Companion));
      }

      return CollectionsKt.plus(var17, var11);
   }

   @NotNull
   public static final List spreadSlot(@NotNull List var0, int var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemStat var2 = ItemStackListExtensionsKt.stat(var0);
      var1 = RangesKt.coerceIn(var0.size() - var1, var2.getTotalMinSlotCount(), var2.getTotalMaxSlotCount());
      List var3;
      Iterable var4;
      List var5 = var4 = var3 = var2.getGroupEntries();
      var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));

      for (Object var16 : var5) {
         ItemStat$GroupEntry var7 = (ItemStat$GroupEntry)var16;
         ArrayList var8 = var4;
         var8.add(new RandFixedSum$Constraint(var7.getMinSlotCount(), var7.getItemCount()));
      }

      var4 = var4;
      var4 = RandFixedSum.INSTANCE.randfixedsum(var4, var1);
      Iterator var10 = (var5 = var3).iterator();
      Iterator var19 = var4.iterator();
      var5 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var5, 10), CollectionsKt.collectionSizeOrDefault(var4, 10)));

      while (var10.hasNext() && var19.hasNext()) {
         Object var10001 = var10.next();
         int var20 = ((Number)var19.next()).intValue();
         ItemStat$GroupEntry var12 = (ItemStat$GroupEntry)var10001;
         ArrayList var21 = var5;
         var21.add(distributeMonotonic(var12.getItemCount(), var20));
      }

      List var11 = var5;
      return flatten(CollectionsKt.zip(var2.getItemTypes(), var11), var0.size());
   }

   private static final List writeEachItemType(List var0, Function1 var1) {
      ItemStat var2 = ItemStackListExtensionsKt.stat(var0);
      int var3 = var0.size();
      ArrayList var4 = new ArrayList(var3);

      for (int var5 = 0; var5 < var3; var5++) {
         ArrayList var9 = var4;
         var9.add(ItemStackExtensionsKt.getEMPTY(ItemStack.Companion));
      }

      var0 = var4;

      for (ItemStat$GroupEntry var14 : var2.getGroupEntries()) {
         Iterable var15 = (Iterable)var1.invoke(var14);
         Iterable var11 = var14.getSlotIndices();
         Iterator var6 = var15.iterator();
         Iterator var7 = var11.iterator();
         ArrayList var12 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var15, 10), CollectionsKt.collectionSizeOrDefault(var11, 10)));

         while (var6.hasNext() && var7.hasNext()) {
            Object var10001 = var6.next();
            int var16 = ((Number)var7.next()).intValue();
            int var8 = ((Number)var10001).intValue();
            ArrayList var17 = var12;
            var0.set(var16, ItemStack.Companion.invoke(var14.getItemType(), var8));
            var17.add(Unit.INSTANCE);
         }
      }

      return var0;
   }

   @NotNull
   public static final List spreadItemCount(@NotNull List var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemStat var1 = ItemStackListExtensionsKt.stat(var0);
      int var9 = var0.size();
      ArrayList var2 = new ArrayList(var9);

      for (int var3 = 0; var3 < var9; var3++) {
         ArrayList var4 = var2;
         var4.add(ItemStackExtensionsKt.getEMPTY(ItemStack.Companion));
      }

      List var6 = var2;
      Iterator var10 = var1.getGroupEntries().iterator();

      while (var10.hasNext()) {
         ItemStat$GroupEntry var14;
         ItemStat$GroupEntry var11 = var14 = (ItemStat$GroupEntry)var10.next();
         RandFixedSum$Constraint var10000 = new RandFixedSum$Constraint;
         ItemType var19 = var11.getItemType();
         net.minecraft.world.item.ItemStack var10003 = new net.minecraft.world.item.ItemStack;
         ItemLike var10005 = (ItemLike)var19.getItem();
         PatchedDataComponentMap var10007 = var19.getTag();
         if (var10007 == null) {
            DataComponentMap var25 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10007 = (PatchedDataComponentMap)var25;
         }

         var10003./* $VF: Unable to resugar constructor */<init>(var10005, 1, var10007);
         var10000./* $VF: Unable to resugar constructor */<init>(1, var10003.getMaxStackSize());
         RandFixedSum$Constraint var7 = var10000;
         int var15 = var11.getSlotCount();
         ArrayList var20 = new ArrayList(var15);

         for (int var5 = 0; var5 < var15; var5++) {
            ArrayList var8 = var20;
            var8.add(var7);
         }

         List var16 = var20;
         Iterable var17 = RandFixedSum.INSTANCE.randfixedsum(var16, var11.getItemCount());
         Iterable var12 = var14.getSlotIndices();
         Iterator var22 = var17.iterator();
         Iterator var23 = var12.iterator();
         ArrayList var13 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var17, 10), CollectionsKt.collectionSizeOrDefault(var12, 10)));

         while (var22.hasNext() && var23.hasNext()) {
            Object var10001 = var22.next();
            var15 = ((Number)var23.next()).intValue();
            int var24 = ((Number)var10001).intValue();
            ArrayList var21 = var13;
            var6.set(var15, ItemStack.Companion.invoke(var14.getItemType(), var24));
            var21.add(Unit.INSTANCE);
         }
      }

      return var6;
   }

   @NotNull
   public static final List distribute(@NotNull List var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemStat var1 = ItemStackListExtensionsKt.stat(var0);
      int var9 = var0.size();
      ArrayList var2 = new ArrayList(var9);

      for (int var3 = 0; var3 < var9; var3++) {
         ArrayList var4 = var2;
         var4.add(ItemStackExtensionsKt.getEMPTY(ItemStack.Companion));
      }

      List var6 = var2;
      Iterator var10 = var1.getGroupEntries().iterator();

      while (var10.hasNext()) {
         ItemStat$GroupEntry var11;
         ItemStat$GroupEntry var14;
         Iterable var15 = distribute((var11 = var14 = (ItemStat$GroupEntry)var10.next()).getItemCount(), var11.getSlotCount());
         Iterable var12 = var14.getSlotIndices();
         Iterator var5 = var15.iterator();
         Iterator var7 = var12.iterator();
         ArrayList var13 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var15, 10), CollectionsKt.collectionSizeOrDefault(var12, 10)));

         while (var5.hasNext() && var7.hasNext()) {
            Object var10001 = var5.next();
            int var16 = ((Number)var7.next()).intValue();
            int var8 = ((Number)var10001).intValue();
            ArrayList var17 = var13;
            var6.set(var16, ItemStack.Companion.invoke(var14.getItemType(), var8));
            var17.add(Unit.INSTANCE);
         }
      }

      return var6;
   }

   @NotNull
   public static final List distributeMonotonic(@NotNull List var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemStat var1 = ItemStackListExtensionsKt.stat(var0);
      int var9 = var0.size();
      ArrayList var2 = new ArrayList(var9);

      for (int var3 = 0; var3 < var9; var3++) {
         ArrayList var4 = var2;
         var4.add(ItemStackExtensionsKt.getEMPTY(ItemStack.Companion));
      }

      List var6 = var2;
      Iterator var10 = var1.getGroupEntries().iterator();

      while (var10.hasNext()) {
         ItemStat$GroupEntry var11;
         ItemStat$GroupEntry var14;
         Iterable var15 = distributeMonotonic((var11 = var14 = (ItemStat$GroupEntry)var10.next()).getItemCount(), var11.getSlotCount());
         Iterable var12 = var14.getSlotIndices();
         Iterator var5 = var15.iterator();
         Iterator var7 = var12.iterator();
         ArrayList var13 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var15, 10), CollectionsKt.collectionSizeOrDefault(var12, 10)));

         while (var5.hasNext() && var7.hasNext()) {
            Object var10001 = var5.next();
            int var16 = ((Number)var7.next()).intValue();
            int var8 = ((Number)var10001).intValue();
            ArrayList var17 = var13;
            var6.set(var16, ItemStack.Companion.invoke(var14.getItemType(), var8));
            var17.add(Unit.INSTANCE);
         }
      }

      return var6;
   }

   @NotNull
   public static final List fillOne(@NotNull List var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemStat var1 = ItemStackListExtensionsKt.stat(var0);
      int var9 = var0.size();
      ArrayList var2 = new ArrayList(var9);

      for (int var3 = 0; var3 < var9; var3++) {
         ArrayList var4 = var2;
         var4.add(ItemStackExtensionsKt.getEMPTY(ItemStack.Companion));
      }

      List var6 = var2;
      Iterator var10 = var1.getGroupEntries().iterator();

      while (var10.hasNext()) {
         ItemStat$GroupEntry var11;
         ItemStat$GroupEntry var14;
         int var10000 = (var11 = var14 = (ItemStat$GroupEntry)var10.next()).getItemCount();
         ItemType var15 = var11.getItemType();
         net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
         ItemLike var10003 = (ItemLike)var15.getItem();
         PatchedDataComponentMap var10005 = var15.getTag();
         if (var10005 == null) {
            DataComponentMap var20 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var20;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         Iterable var16 = fillOne(var10000, var10001.getMaxStackSize(), var11.getSlotCount());
         Iterable var12 = var14.getSlotIndices();
         Iterator var5 = var16.iterator();
         Iterator var7 = var12.iterator();
         ArrayList var13 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var16, 10), CollectionsKt.collectionSizeOrDefault(var12, 10)));

         while (var5.hasNext() && var7.hasNext()) {
            Object var19 = var5.next();
            int var17 = ((Number)var7.next()).intValue();
            int var8 = ((Number)var19).intValue();
            ArrayList var18 = var13;
            var6.set(var17, ItemStack.Companion.invoke(var14.getItemType(), var8));
            var18.add(Unit.INSTANCE);
         }
      }

      return var6;
   }

   @NotNull
   public static final List distribute(int var0, int var1) {
      Iterable var2;
      Iterable var3 = var2 = (Iterable)(new IntRange(0, var1));
      var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));
      Iterator var4 = var3.iterator();

      while (var4.hasNext()) {
         int var5 = ((IntIterator)var4).nextInt();
         int var6 = var5;
         ArrayList var7 = var2;
         var7.add(var0 * var6 / var1);
      }

      Iterator var11;
      if (!(var11 = var2.iterator()).hasNext()) {
         return CollectionsKt.emptyList();
      }

      var2 = new ArrayList();
      Object var8 = var11.next();

      while (var11.hasNext()) {
         Object var12 = var11.next();
         int var13 = ((Number)var12).intValue();
         int var14 = ((Number)var8).intValue();
         List var15 = var2;
         var15.add(var13 - var14);
         var8 = var12;
      }

      return var2;
   }

   @NotNull
   public static final List distributeMonotonic(int var0, int var1) {
      int var2 = var0 % var1;
      ArrayList var3 = new ArrayList(var2);

      for (int var4 = 0; var4 < var2; var4++) {
         ArrayList var5 = var3;
         var5.add(var0 / var1 + 1);
      }

      var var10000 = var3;
      var2 = var1 - var0 % var1;
      Collection var10 = var10000;
      var3 = new ArrayList(var2);

      for (int var9 = 0; var9 < var2; var9++) {
         ArrayList var6 = var3;
         var6.add(var0 / var1);
      }

      return CollectionsKt.plus(var10, var3);
   }

   @NotNull
   public static final List fillOne(int var0, int var1, int var2) {
      if (var2 * var1 == var0) {
         ArrayList var11 = new ArrayList(var2);

         for (int var13 = 0; var13 < var2; var13++) {
            ArrayList var16 = var11;
            var16.add(var1);
         }

         return var11;
      } else {
         int var4 = (var0 - var2) / (var1 - 1);
         List var3 = new ArrayList(var4);

         for (int var5 = 0; var5 < var4; var5++) {
            ArrayList var6 = var3;
            var6.add(var1);
         }

         var3 = var3;
         List var12 = CollectionsKt.listOf((var0 - var2) % (var1 - 1) + 1);
         Collection var10000 = CollectionsKt.plus(var3, var12);
         int var10 = var2 - var3.size() - var12.size();
         Collection var15 = var10000;
         ArrayList var14 = new ArrayList(var10);

         for (int var7 = 0; var7 < var10; var7++) {
            ArrayList var8 = var14;
            var8.add(1);
         }

         return CollectionsKt.plus(var15, var14);
      }
   }

   @NotNull
   public static final List pack(int var0, int var1) {
      int var2 = var0 / var1;
      ArrayList var3 = new ArrayList(var2);

      for (int var4 = 0; var4 < var2; var4++) {
         ArrayList var5 = var3;
         var5.add(var1);
      }

      List var6 = var3;
      return var0 % var1 == 0 ? var6 : CollectionsKt.plus(var6, CollectionsKt.listOf(var0 % var1));
   }
}
