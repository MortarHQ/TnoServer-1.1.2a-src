package org.anti_ad.mc.ipnext.item.rule.natives;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.optionals.OptionalsKt;
import kotlin.ranges.RangesKt;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.HolderSet.Direct;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SpecificEnchantmentOrder$DefaultImpls {
   public static int getSize(@NotNull SpecificEnchantmentOrder var0, @NotNull ItemEnchantments var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.size();
   }

   public static int compareEnchantments(@NotNull SpecificEnchantmentOrder var0, @NotNull ItemEnchantments var1, @NotNull ItemEnchantments var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Provider var3 = TooltipContext.of((Level)Vanilla.INSTANCE.world()).registries();
      ResourceKey var10002 = Registries.ENCHANTMENT;
      Intrinsics.checkNotNullExpressionValue(Registries.ENCHANTMENT, "");
      TagKey var10003 = EnchantmentTags.TOOLTIP_ORDER;
      Intrinsics.checkNotNullExpressionValue(EnchantmentTags.TOOLTIP_ORDER, "");
      List var9 = CollectionsKt.toMutableList((Iterable)var0.getTooltipOrderList(var3, var10002, var10003));
      SpecificEnchantmentOrder$Companion var10000 = SpecificEnchantmentOrder.Companion;
      Set var10001 = var1.entrySet();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      List var10 = var10000.sortByListOrder(var10001, var9);
      var10000 = SpecificEnchantmentOrder.Companion;
      var10001 = var2.entrySet();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      List var11 = var10000.sortByListOrder(var10001, var9);
      int var12 = 0;

      for (int var4 = RangesKt.coerceAtMost(var10.size(), var11.size()); var12 < var4; var12++) {
         int var5 = var9.indexOf(((Entry)var10.get(var12)).getKey());
         int var6 = var9.indexOf(((Entry)var11.get(var12)).getKey());
         int var7 = ((Entry)var10.get(var12)).getIntValue();
         int var8 = ((Entry)var11.get(var12)).getIntValue();
         if (var5 != var6) {
            return var5 - var6;
         }

         if (var7 != var8) {
            return var8 - var7;
         }
      }

      return var11.size() - var10.size();
   }

   @NotNull
   public static HolderSet getTooltipOrderList(@NotNull SpecificEnchantmentOrder var0, @Nullable Provider var1, @NotNull ResourceKey var2, @NotNull TagKey var3) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      if (var1 != null) {
         RegistryLookup var10000 = var1.lookupOrThrow(var2);
         if (var10000 != null) {
            Optional var4 = var10000.get(var3);
            if (var4 != null) {
               Named var5 = (Named)OptionalsKt.getOrNull(var4);
               if (var5 != null) {
                  return (HolderSet)var5;
               }
            }
         }
      }

      Direct var6 = HolderSet.direct(new Holder[0]);
      Intrinsics.checkNotNullExpressionValue(var6, "");
      return (HolderSet)var6;
   }
}
