package org.anti_ad.mc.ipnext.gui;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$11\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n339#2:224\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$11\n*L\n92#1:224\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$11 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$11(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "rawId", "getRawId(Lorg/anti_ad/mc/ipnext/item/ItemType;)I", 1);
   }

   public final Object get() {
      ItemType var1 = (ItemType)this.receiver;
      DefaultedRegistry var10000 = BuiltInRegistries.ITEM;
      Intrinsics.checkNotNullExpressionValue(BuiltInRegistries.ITEM, "");
      return VanillaAccessorsKt._getRawId_/* $VF was: (getRawId) */(var10000, var1.getItem());
   }
}
