package org.anti_ad.mc.ipnext.item.rule.natives;

import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.HolderSet;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EnchantmentsOrder extends NativeRule implements SpecificEnchantmentOrder {
   public EnchantmentsOrder() {
      this.setComparator(new EnchantmentsOrder$1(this));
   }

   private final int compareNulls(Object var1, Object var2) {
      if (var1 == null) {
         return var2 == null ? 0 : -1;
      } else {
         return 1;
      }
   }

   public final int compareItems(@NotNull ItemType var1, @NotNull ItemType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      DataComponentType var3 = DataComponents.ENCHANTMENTS;
      DataComponentType var4 = DataComponents.STORED_ENCHANTMENTS;
      PatchedDataComponentMap var6 = var1.getTag();
      PatchedDataComponentMap var8 = var2.getTag();
      if (var6 != null && var8 != null) {
         ItemEnchantments var5 = (ItemEnchantments)var6.get(var3);
         ItemEnchantments var10 = (ItemEnchantments)var8.get(var3);
         ItemEnchantments var7 = (ItemEnchantments)var6.get(var4);
         ItemEnchantments var9 = (ItemEnchantments)var8.get(var4);
         if (var5 == null || var10 == null || this.getSize(var5) == 0 && this.getSize(var10) == 0) {
            if (var7 == null || var9 == null || this.getSize(var7) == 0 && this.getSize(var9) == 0) {
               int var11;
               return (var11 = this.compareNulls(var5, var10)) != 0 ? var11 : this.compareNulls(var7, var9);
            } else {
               return this.compareEnchantments(var7, var9);
            }
         } else {
            return this.compareEnchantments(var5, var10);
         }
      } else {
         return this.compareNulls(var6, var8);
      }
   }

   @Override
   public final int getSize(@NotNull ItemEnchantments var1) {
      return SpecificEnchantmentOrder$DefaultImpls.getSize(this, var1);
   }

   @Override
   public final int compareEnchantments(@NotNull ItemEnchantments var1, @NotNull ItemEnchantments var2) {
      return SpecificEnchantmentOrder$DefaultImpls.compareEnchantments(this, var1, var2);
   }

   @NotNull
   @Override
   public final HolderSet getTooltipOrderList(@Nullable Provider var1, @NotNull ResourceKey var2, @NotNull TagKey var3) {
      return SpecificEnchantmentOrder$DefaultImpls.getTooltipOrderList(this, var1, var2, var3);
   }
}
