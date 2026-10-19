package org.anti_ad.mc.ipnext.gui;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$13\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,223:1\n344#2:224\n106#2:225\n345#2:228\n346#2:230\n1803#3,2:226\n1805#3:229\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$13\n*L\n94#1:224\n94#1:225\n94#1:228\n94#1:230\n94#1:226,2\n94#1:229\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$13 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$13(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "(enchantmentsScore)", "get(enchantmentsScore)(Lorg/anti_ad/mc/ipnext/item/ItemType;)D", 1);
   }

   public final Object get() {
      ItemType var1 = (ItemType)this.receiver;
      ItemStack var10000 = new ItemStack;
      ItemLike var10002 = (ItemLike)var1.getItem();
      PatchedDataComponentMap var10004 = var1.getTag();
      if (var10004 == null) {
         DataComponentMap var13 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10004 = (PatchedDataComponentMap)var13;
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
      Set var12 = EnchantmentHelper.getEnchantmentsForCrafting(var10000).entrySet();
      Intrinsics.checkNotNullExpressionValue(var12, "");
      Iterable var8 = var12;
      double var4 = 0.0;

      for (Object var2 : var8) {
         Entry var10 = (Entry)var2;
         double var6 = var4;
         Intrinsics.checkNotNull(var10);
         Holder var3 = (Holder)((java.util.Map.Entry)var10).getKey();
         Integer var11 = (Integer)((java.util.Map.Entry)var10).getValue();
         var4 = var6 + (var3.is(EnchantmentTags.CURSE) ? -0.001 : (double)var11.intValue() / ((Enchantment)var3.value()).getMaxLevel());
      }

      return var4;
   }
}
