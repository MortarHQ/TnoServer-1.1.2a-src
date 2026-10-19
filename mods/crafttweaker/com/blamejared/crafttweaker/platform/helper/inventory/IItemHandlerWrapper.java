package com.blamejared.crafttweaker.platform.helper.inventory;

import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

public class IItemHandlerWrapper implements IInventoryWrapper {
   private final IItemHandler handler;

   public IItemHandlerWrapper(IItemHandler handler) {
      this.handler = handler;
   }

   @Override
   public int getContainerSize() {
      return this.handler.getSlots();
   }

   @Nonnull
   @Override
   public ItemStack getItem(int slot) {
      return this.handler.getStackInSlot(slot);
   }

   @Override
   public boolean canFitInSlot(int slot, ItemStack stack) {
      ItemStack item = this.getItem(slot);
      if (item.isEmpty()) {
         return true;
      } else if (ItemStack.isSameItemSameComponents(item, stack)) {
         int maxStackSize = Math.min(this.handler.getSlotLimit(slot), item.getMaxStackSize());
         return item.getCount() + stack.getCount() < maxStackSize;
      } else {
         return false;
      }
   }

   @Override
   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      return slot >= 0 && slot < this.handler.getSlots() ? this.handler.insertItem(slot, stack, simulate) : stack;
   }
}
