package org.anti_ad.mc.ipnext.event.autorefill;

import java.util.Comparator;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemType;

@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$thenComparator$1\n+ 2 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor$Companion\n+ 3 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,197:1\n670#2,3:198\n356#3:201\n106#3:202\n*S KotlinDebug\n*F\n+ 1 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor$Companion\n*L\n672#1:201\n672#1:202\n*E\n"
)
public final class AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$1 implements Comparator {
   public AutoRefillHandler$ItemSlotMonitor$Companion$findCorrespondingSlot$$inlined$thenComparator$1(Comparator var1) {
      this.$this_thenComparator = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      int var3;
      if ((var3 = this.$this_thenComparator.compare(var1, var2)) != 0) {
         return var3;
      }

      IndexedValue var4 = (IndexedValue)var2;
      ItemType var7 = ((ItemStack)((IndexedValue)var1).getValue()).getItemType();
      ItemType var5 = ((ItemStack)var4.getValue()).getItemType();
      net.minecraft.world.item.ItemStack var8 = new net.minecraft.world.item.ItemStack;
      ItemLike var10002 = (ItemLike)var5.getItem();
      PatchedDataComponentMap var10004 = var5.getTag();
      if (var10004 == null) {
         DataComponentMap var10 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var10;
      }

      var8./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      int var9 = var8.getMaxDamage();
      var5 = var7;
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var5.getItem();
      PatchedDataComponentMap var10005 = var5.getTag();
      if (var10005 == null) {
         DataComponentMap var11 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var11;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      return var9 - var10001.getMaxDamage();
   }
}
