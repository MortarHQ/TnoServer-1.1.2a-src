package com.natamus.collective_common_forge.services.helpers;

import net.minecraft.world.item.ItemStack;

public interface ToolFunctionsHelper {
   boolean isTool(ItemStack var1);

   boolean isSword(ItemStack var1);

   boolean isShield(ItemStack var1);

   boolean isPickaxe(ItemStack var1);

   boolean isAxe(ItemStack var1);

   boolean isShovel(ItemStack var1);

   boolean isHoe(ItemStack var1);

   boolean isShears(ItemStack var1);

   boolean isFlintAndSteel(ItemStack var1);
}
