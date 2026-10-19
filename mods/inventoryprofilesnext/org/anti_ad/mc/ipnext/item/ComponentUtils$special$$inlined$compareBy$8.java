package org.anti_ad.mc.ipnext.item;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.item.enchantment.ItemEnchantments;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n*L\n1#1,102:1\n157#2:103\n*E\n"
)
public final class ComponentUtils$special$$inlined$compareBy$8 implements Comparator {
   @Override
   public final int compare(Object var1, Object var2) {
      var1 = var1;
      Comparable var10000 = ComponentUtils.access$getScore(ComponentUtils.INSTANCE, var1);
      var1 = (ItemEnchantments)var2;
      Comparable var5 = var10000;
      return ComparisonsKt.compareValues(var5, ComponentUtils.access$getScore(ComponentUtils.INSTANCE, var1));
   }
}
