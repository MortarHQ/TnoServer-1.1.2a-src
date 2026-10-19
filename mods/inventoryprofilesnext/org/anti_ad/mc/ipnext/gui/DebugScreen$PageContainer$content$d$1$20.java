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
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$20\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n354#2:224\n106#2:225\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$20\n*L\n101#1:224\n101#1:225\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$20 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$20(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "isDamageable", "isDamageable(Lorg/anti_ad/mc/ipnext/item/ItemType;)Z", 1);
   }

   public final Object get() {
      ItemType var1 = (ItemType)this.receiver;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var2 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var2;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return var10000.isDamageableItem();
   }
}
