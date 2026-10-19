package com.sbjeiindex.jei;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public class OffsetItemHandlerModifiable implements IItemHandlerModifiable {
   private final IItemHandlerModifiable delegate;
   private final int offset;

   public OffsetItemHandlerModifiable(IItemHandlerModifiable delegate, int offset) {
      this.delegate = delegate;
      this.offset = offset;
   }

   private int toDelegateIndex(int slot) {
      return slot - this.offset;
   }

   public int getSlots() {
      return this.offset + this.delegate.getSlots();
   }

   public ItemStack getStackInSlot(int slot) {
      int i = this.toDelegateIndex(slot);
      return i >= 0 && i < this.delegate.getSlots() ? this.delegate.getStackInSlot(i) : ItemStack.EMPTY;
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      int i = this.toDelegateIndex(slot);
      return i >= 0 && i < this.delegate.getSlots() ? this.delegate.insertItem(i, stack, simulate) : stack;
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      int i = this.toDelegateIndex(slot);
      return i >= 0 && i < this.delegate.getSlots() ? this.delegate.extractItem(i, amount, simulate) : ItemStack.EMPTY;
   }

   public int getSlotLimit(int slot) {
      int i = this.toDelegateIndex(slot);
      return i >= 0 && i < this.delegate.getSlots() ? this.delegate.getSlotLimit(i) : 0;
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      int i = this.toDelegateIndex(slot);
      return i >= 0 && i < this.delegate.getSlots() ? this.delegate.isItemValid(i, stack) : false;
   }

   public void setStackInSlot(int slot, ItemStack stack) {
      int i = this.toDelegateIndex(slot);
      if (i >= 0 && i < this.delegate.getSlots()) {
         this.delegate.setStackInSlot(i, stack);
      }
   }
}
