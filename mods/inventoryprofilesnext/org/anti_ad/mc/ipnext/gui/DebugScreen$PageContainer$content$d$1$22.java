package org.anti_ad.mc.ipnext.gui;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$22\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n358#2:224\n356#2:225\n106#2:226\n341#2:227\n106#2:228\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$22\n*L\n103#1:224\n103#1:225\n103#1:226\n103#1:227\n103#1:228\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$22 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$22(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "durability", "getDurability(Lorg/anti_ad/mc/ipnext/item/ItemType;)I", 1);
   }

   public final Object get() {
      ItemType var1;
      ItemType var2 = var1 = (ItemType)this.receiver;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var5 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var5;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      int var4 = var10000.getMaxDamage();
      var2 = var1;
      ItemStack var10001 = new ItemStack;
      ItemLike var10003 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10005 = var2.getTag();
      if (var10005 == null) {
         DataComponentMap var6 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var6;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      return var4 - var10001.getDamageValue();
   }
}
