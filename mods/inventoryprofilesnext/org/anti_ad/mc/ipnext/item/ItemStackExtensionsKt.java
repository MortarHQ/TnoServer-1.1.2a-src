package org.anti_ad.mc.ipnext.item;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.Log;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nItemStackExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemStackExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemStackExtensionsKt\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 3 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 ItemStack.kt\norg/anti_ad/mc/ipnext/item/ItemStack\n*L\n1#1,118:1\n88#2,4:119\n88#2,4:125\n98#2,9:129\n98#2,9:140\n98#2,9:149\n98#2,9:158\n88#2,4:167\n88#2,4:171\n98#2,9:177\n98#2,9:187\n130#3:123\n130#3:138\n1#4:124\n1#4:139\n1#4:175\n34#5:176\n34#5:186\n*S KotlinDebug\n*F\n+ 1 ItemStackExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemStackExtensionsKt\n*L\n34#1:119,4\n39#1:125,4\n44#1:129,9\n45#1:140,9\n54#1:149,9\n58#1:158,9\n67#1:167,4\n71#1:171,4\n93#1:177,9\n101#1:187,9\n38#1:123\n44#1:138\n38#1:124\n44#1:139\n93#1:176\n101#1:186\n*E\n"
)
public final class ItemStackExtensionsKt {
   @NotNull
   public static final ItemStack getEMPTY(@NotNull ItemStack$Companion var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemStack$Companion var10000 = ItemStack.Companion;
      Item var10003 = Items.AIR;
      Intrinsics.checkNotNullExpressionValue(Items.AIR, "");
      return var10000.invoke(
         new ItemType(
            var10003,
            new PatchedDataComponentMap(Items.AIR.components()),
            DataComponentPatch.EMPTY,
            ItemTypeExtensionsKt$EMPTY$1.INSTANCE,
            false,
            false,
            null,
            112,
            null
         ),
         0
      );
   }

   public static final boolean isEmpty(@NotNull ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      boolean var1;
      boolean var2 = var1 = ItemTypeExtensionsKt.isEmpty(var0.getItemType()) || var0.getCount() <= 0;
      if (var1) {
         ItemType var10000 = var0.getItemType();
         Item var10003 = Items.AIR;
         Intrinsics.checkNotNullExpressionValue(Items.AIR, "");
         if (!Intrinsics.areEqual(
               var10000,
               new ItemType(
                  var10003,
                  new PatchedDataComponentMap(Items.AIR.components()),
                  DataComponentPatch.EMPTY,
                  ItemTypeExtensionsKt$EMPTY$1.INSTANCE,
                  false,
                  false,
                  null,
                  112,
                  null
               )
            )
            || var0.getCount() != 0) {
            Log.INSTANCE.warn("Informal item stack " + var0);
         }
      }

      return var2;
   }

   public static final boolean isFull(@NotNull ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      int var10000 = var0.getCount();
      ItemType var2 = var0.getItemType();
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10005 = var2.getTag();
      if (var10005 == null) {
         DataComponentMap var8 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var8;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      boolean var1;
      boolean var4 = var1 = var10000 >= var10001.getMaxStackSize();
      if (var1) {
         var10000 = var0.getCount();
         ItemType var3 = var0.getItemType();
         var10001 = new net.minecraft.world.item.ItemStack;
         var10003 = (ItemLike)var3.getItem();
         var10005 = var3.getTag();
         if (var10005 == null) {
            DataComponentMap var10 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var10;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         if (var10000 != var10001.getMaxStackSize()) {
            Log.INSTANCE.warn("Informal item stack " + var0);
         }
      }

      return var4;
   }

   public static final boolean isNotFull(@NotNull ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return !isEmpty(var0) && !isFull(var0);
   }

   public static final int getRoom(@NotNull ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemType var1 = var0.getItemType();
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var3 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var3;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      int var2;
      if ((var2 = var10000.getMaxStackSize() - var0.getCount()) < 0) {
         Log.INSTANCE.warn("Informal item stack " + var0);
      }

      return var2;
   }

   public static final boolean stackableWith(@NotNull ItemStack var0, @NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var0.getItemType();
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var3 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var3;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.getMaxStackSize() > 1 && Intrinsics.areEqual(var0.getItemType(), var1.getItemType()) || isEmpty(var0) || isEmpty(var1);
   }

   @NotNull
   public static final net.minecraft.world.item.ItemStack getVanillaStack(@NotNull ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return ItemTypeExtensionsKt.vanillaStackWithCount(var0.getItemType(), var0.getCount());
   }

   @NotNull
   public static final MutableItemStack empty(@NotNull MutableItemStack$Companion var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Item var10004 = Items.AIR;
      Intrinsics.checkNotNullExpressionValue(Items.AIR, "");
      return new MutableItemStack(
         new ItemType(
            var10004,
            new PatchedDataComponentMap(Items.AIR.components()),
            DataComponentPatch.EMPTY,
            ItemTypeExtensionsKt$EMPTY$1.INSTANCE,
            false,
            false,
            null,
            112,
            null
         ),
         0,
         null,
         4,
         null
      );
   }

   public static final void setEmpty(@NotNull MutableItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Item var10003 = Items.AIR;
      Intrinsics.checkNotNullExpressionValue(Items.AIR, "");
      var0.setItemType(
         new ItemType(
            var10003,
            new PatchedDataComponentMap(Items.AIR.components()),
            DataComponentPatch.EMPTY,
            ItemTypeExtensionsKt$EMPTY$1.INSTANCE,
            false,
            false,
            null,
            112,
            null
         )
      );
      var0.setCount(0);
   }

   private static final void normalize(MutableItemStack var0) {
      if (ItemTypeExtensionsKt.isEmpty(var0.getItemType()) || var0.getCount() <= 0) {
         setEmpty(var0);
      }
   }

   public static final void swapWith(@NotNull MutableItemStack var0, @NotNull MutableItemStack var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var2 = var1.getItemType();
      MutableItemStack var3 = var0;
      var1.setItemType(var0.getItemType());
      var3.setItemType(var2);
      int var4 = var1.getCount();
      var3 = var0;
      var1.setCount(var0.getCount());
      var3.setCount(var4);
   }

   public static final void transferTo(@NotNull MutableItemStack var0, @NotNull MutableItemStack var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      transferNTo(var0, var1, var0.getCount());
   }

   public static final void transferOneTo(@NotNull MutableItemStack var0, @NotNull MutableItemStack var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      transferNTo(var0, var1, 1);
   }

   public static final void transferNTo(@NotNull MutableItemStack var0, @NotNull MutableItemStack var1, int var2) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      if (stackableWith(var0, var1)) {
         ItemStack var3;
         int var10000 = (var3 = var0).getCount();
         ItemType var5 = var3.getItemType();
         net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
         ItemLike var10003 = (ItemLike)var5.getItem();
         PatchedDataComponentMap var10005 = var5.getTag();
         if (var10005 == null) {
            DataComponentMap var12 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var12;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         if (var10000 <= var10001.getMaxStackSize() || isEmpty(var1)) {
            if (!isEmpty(var0)) {
               if (isEmpty(var1)) {
                  var1.setItemType(var0.getItemType());
                  var1.setCount(0);
               }

               net.minecraft.world.item.ItemStack var6 = getVanillaStack(var1);
               Log.INSTANCE.trace("another is " + var6);
               var10000 = (var3 = var0).getCount();
               ItemType var8 = var3.getItemType();
               var10001 = new net.minecraft.world.item.ItemStack;
               var10003 = (ItemLike)var8.getItem();
               var10005 = var8.getTag();
               if (var10005 == null) {
                  DataComponentMap var14 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var10005 = (PatchedDataComponentMap)var14;
               }

               var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
               if ((
                     var2 = var10000 > var10001.getMaxStackSize()
                        ? var0.getCount()
                        : RangesKt.coerceAtLeast(RangesKt.coerceAtMost(var2, Math.min(var0.getCount(), getRoom(var1))), 0)
                  )
                  != 0) {
                  var0.setCount(var0.getCount() - var2);
                  var1.setCount(var1.getCount() + var2);
                  normalize(var0);
                  normalize(var1);
               }
            }
         }
      }
   }

   public static final void splitHalfTo(@NotNull MutableItemStack var0, @NotNull MutableItemStack var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      transferNTo(var0, var1, var0.getCount() - var0.getCount() / 2);
   }
}
