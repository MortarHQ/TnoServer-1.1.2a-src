package org.anti_ad.mc.ipnext.gui;

import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$16\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n402#2:224\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$16\n*L\n97#1:224\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$16 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$16(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "hasPotionName", "getHasPotionName(Lorg/anti_ad/mc/ipnext/item/ItemType;)Z", 1);
   }

   public final Object get() {
      PatchedDataComponentMap var10000 = ((ItemType)this.receiver).getTag();
      return var10000 != null ? var10000.has(DataComponents.POTION_CONTENTS) : false;
   }
}
