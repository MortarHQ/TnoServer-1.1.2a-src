package com.tom.storagemod.inventory.filter;

import net.minecraft.world.item.ItemStack;

public interface ItemFilter extends ItemPredicate {
   ItemFilter TRUE = s -> true;

   default boolean configMatch(ItemStack stack) {
      return true;
   }
}
