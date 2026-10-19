package org.anti_ad.mc.ipnext.gui;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$3\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n115#2:224\n112#2:225\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$3\n*L\n84#1:224\n84#1:225\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$3 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$3(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "namespace", "getNamespace(Lorg/anti_ad/mc/ipnext/item/ItemType;)Ljava/lang/String;", 1);
   }

   public final Object get() {
      ItemType var1 = (ItemType)this.receiver;
      ResourceLocation var10000 = BuiltInRegistries.ITEM.getKey(var1.getItem());
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      String var2 = var10000.getNamespace();
      Intrinsics.checkNotNullExpressionValue(var2, "");
      return var2;
   }
}
