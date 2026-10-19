package org.anti_ad.mc.ipnext.gui;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$7\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n129#2,5:224\n106#2:229\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$7\n*L\n88#1:224,5\n88#1:229\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$7 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$7(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "translatedName", "getTranslatedName(Lorg/anti_ad/mc/ipnext/item/ItemType;)Ljava/lang/String;", 1);
   }

   public final Object get() {
      ItemType var1 = (ItemType)this.receiver;
      I18n var10000 = I18n.INSTANCE;
      ItemStack var10001 = new ItemStack;
      ItemLike var10003 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10005 = var1.getTag();
      if (var10005 == null) {
         DataComponentMap var3 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var3;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      String var2 = var10001.getDescriptionId();
      Intrinsics.checkNotNullExpressionValue(var2, "");
      return var10000.translate(var2, new Object[0]);
   }
}
