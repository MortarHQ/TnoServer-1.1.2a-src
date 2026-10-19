package org.anti_ad.mc.ipnext.item;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.level.ItemLike;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$thenBy$1\n+ 2 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n+ 3 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,145:1\n137#2:146\n106#3:147\n*S KotlinDebug\n*F\n+ 1 ComponentUtils.kt\norg/anti_ad/mc/ipnext/item/ComponentUtils\n*L\n137#1:147\n*E\n"
)
public final class ComponentUtils$special$$inlined$thenBy$2 implements Comparator {
   public ComponentUtils$special$$inlined$thenBy$2(Comparator var1) {
      this.$this_thenBy = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      int var3;
      if ((var3 = this.$this_thenBy.compare(var1, (ItemType)var2)) != 0) {
         return var3;
      }

      var1 = var1;
      net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var10 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var10;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      Comparable var7 = var10000.getCount();
      var1 = (ItemType)var2;
      Comparable var6 = var7;
      var10000 = new net.minecraft.world.item.ItemStack;
      var10002 = (ItemLike)var1.getItem();
      var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var12 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var12;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      return ComparisonsKt.compareValues(var6, var10000.getCount());
   }
}
