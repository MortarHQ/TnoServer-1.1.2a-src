package com.blamejared.crafttweaker.platform.helper.inventory;

import java.util.OptionalInt;
import net.minecraft.world.item.ItemStack;

public interface IInventoryWrapper {
   int getContainerSize();

   ItemStack getItem(int var1);

   boolean canFitInSlot(int var1, ItemStack var2);

   default OptionalInt getSlotFor(ItemStack stack) {
      if (stack.isEmpty()) {
         return OptionalInt.empty();
      }

      int firstEmptySlot = -1;

      for (int i = 0; i < this.getContainerSize(); i++) {
         if (this.getItem(i).isEmpty()) {
            if (firstEmptySlot == -1) {
               firstEmptySlot = i;
            }
         } else if (this.canFitInSlot(i, stack)) {
            return OptionalInt.of(i);
         }
      }

      return OptionalInt.empty();
   }

   ItemStack insertItem(int var1, ItemStack var2, boolean var3);
}
