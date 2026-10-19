package com.tom.storagemod.inventory;

import java.util.Arrays;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.EmptyItemHandler;

public class PlatformMultiInventoryAccess extends MultiInventoryAccess implements IItemHandler {
   private int[] offsets = new int[0];
   private int invSize;
   private int offsetsSize;
   private boolean calling;

   public IItemHandler get() {
      return this;
   }

   private int findInventory(int slot) {
      int arrayIndex = Arrays.binarySearch(this.offsets, 0, this.offsetsSize, slot);
      if (arrayIndex < 0) {
         arrayIndex = -arrayIndex - 2;
      }

      return arrayIndex;
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      if (this.calling) {
         return false;
      }

      if (slot >= this.invSize) {
         return false;
      }

      this.calling = true;
      int arrayIndex = this.findInventory(slot);
      int invSlot = slot - this.offsets[arrayIndex];
      boolean r = this.getHandler(arrayIndex, invSlot).isItemValid(invSlot, stack);
      this.calling = false;
      return r;
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      if (this.calling) {
         return stack;
      }

      if (slot >= this.invSize) {
         return stack;
      }

      this.calling = true;
      int arrayIndex = this.findInventory(slot);
      int invSlot = slot - this.offsets[arrayIndex];
      ItemStack s = this.getHandler(arrayIndex, invSlot).insertItem(invSlot, stack, simulate);
      this.calling = false;
      return s;
   }

   public ItemStack getStackInSlot(int slot) {
      if (this.calling) {
         return ItemStack.EMPTY;
      }

      if (slot >= this.invSize) {
         return ItemStack.EMPTY;
      }

      this.calling = true;
      int arrayIndex = this.findInventory(slot);
      int invSlot = slot - this.offsets[arrayIndex];
      ItemStack s = this.getHandler(arrayIndex, invSlot).getStackInSlot(invSlot);
      this.calling = false;
      return s;
   }

   public int getSlots() {
      return this.invSize;
   }

   public int getSlotLimit(int slot) {
      if (this.calling) {
         return 0;
      }

      if (slot >= this.invSize) {
         return 0;
      }

      this.calling = true;
      int arrayIndex = this.findInventory(slot);
      int invSlot = slot - this.offsets[arrayIndex];
      int r = this.getHandler(arrayIndex, invSlot).getSlotLimit(invSlot);
      this.calling = false;
      return r;
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if (this.calling) {
         return ItemStack.EMPTY;
      }

      if (slot >= this.invSize) {
         return ItemStack.EMPTY;
      }

      this.calling = true;
      int arrayIndex = this.findInventory(slot);
      int invSlot = slot - this.offsets[arrayIndex];
      ItemStack s = this.getHandler(arrayIndex, invSlot).extractItem(invSlot, amount, simulate);
      this.calling = false;
      return s;
   }

   @Override
   public void clear() {
      super.clear();
      this.invSize = 0;
   }

   @Override
   public void refresh() {
      if (this.offsets.length != this.connected.size()) {
         this.offsets = new int[this.connected.size()];
      }

      this.invSize = 0;
      int hOff = 0;

      for (int i = 0; i < this.offsets.length; i++) {
         IItemHandler ih = this.getHandler(i, 0);
         int s = ih.getSlots();
         if (s == 0) {
            hOff++;
         } else {
            this.offsets[i - hOff] = this.invSize;
            this.invSize += s;
         }
      }

      this.offsetsSize = this.offsets.length - hOff;
   }

   private IItemHandler getHandler(int i, int invSlot) {
      IItemHandler h = this.connected.get(i).getPlatformHandler();
      if (h == null) {
         return EmptyItemHandler.INSTANCE;
      } else {
         return invSlot < h.getSlots() ? h : EmptyItemHandler.INSTANCE;
      }
   }
}
