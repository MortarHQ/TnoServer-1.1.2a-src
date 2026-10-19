package org.anti_ad.mc.ipnext.gui;

import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$1\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n118#2:224\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$1\n*L\n82#1:224\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$1 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$1(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "itemClass", "getItemClass(Lorg/anti_ad/mc/ipnext/item/ItemType;)Ljava/lang/Class;", 1);
   }

   public final Object get() {
      return ((ItemType)this.receiver).getItem().getClass();
   }
}
