package org.anti_ad.mc.ipnext.gui;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$5\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n125#2:224\n123#2:225\n106#2:226\n127#2:227\n106#2:228\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$5\n*L\n86#1:224\n86#1:225\n86#1:226\n86#1:227\n86#1:228\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$5 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$5(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "customName", "getCustomName(Lorg/anti_ad/mc/ipnext/item/ItemType;)Ljava/lang/String;", 1);
   }

   public final Object get() {
      ItemType var1;
      ItemType var2 = var1 = (ItemType)this.receiver;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10004 = var2.getTag();
      if (var10004 == null) {
         DataComponentMap var7 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var7;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      if (var10000.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
         var2 = var1;
         var10000 = new ItemStack;
         var10002 = (ItemLike)var2.getItem();
         var10004 = var2.getTag();
         if (var10004 == null) {
            DataComponentMap var9 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var9;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         String var5 = var10000.getDisplayName().getString();
         Intrinsics.checkNotNullExpressionValue(var5, "");
         return var5;
      } else {
         return "";
      }
   }
}
