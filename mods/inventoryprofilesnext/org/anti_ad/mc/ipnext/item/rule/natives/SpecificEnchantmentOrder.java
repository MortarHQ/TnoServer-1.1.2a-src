package org.anti_ad.mc.ipnext.item.rule.natives;

import net.minecraft.core.HolderSet;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SpecificEnchantmentOrder {
   int getSize(@NotNull ItemEnchantments var1);

   int compareEnchantments(@NotNull ItemEnchantments var1, @NotNull ItemEnchantments var2);

   @NotNull
   HolderSet getTooltipOrderList(@Nullable Provider var1, @NotNull ResourceKey var2, @NotNull TagKey var3);
}
