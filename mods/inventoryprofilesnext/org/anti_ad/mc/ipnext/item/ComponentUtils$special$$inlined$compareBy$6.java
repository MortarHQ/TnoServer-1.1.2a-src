package org.anti_ad.mc.ipnext.item;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n+ 3 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,102:1\n136#2:103\n131#3:104\n112#3:105\n*S KotlinDebug\n*F\n+ 1 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n*L\n136#1:104\n136#1:105\n*E\n"
)
public final class ComponentUtils$special$$inlined$compareBy$6 implements Comparator {
   @Override
   public final int compare(Object var1, Object var2) {
      var1 = var1;
      ResourceLocation var10000 = BuiltInRegistries.ITEM.getKey(var1.getItem());
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      String var6 = var10000.toString();
      Intrinsics.checkNotNullExpressionValue(var6, "");
      Comparable var7 = var6;
      var1 = (ItemType)var2;
      Comparable var5 = var7;
      var10000 = BuiltInRegistries.ITEM.getKey(var1.getItem());
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      String var9 = var10000.toString();
      Intrinsics.checkNotNullExpressionValue(var9, "");
      return ComparisonsKt.compareValues(var5, var9);
   }
}
