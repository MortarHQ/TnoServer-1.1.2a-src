package com.tom.storagemod.inventory;

import java.util.Set;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

public class PlatformProxyInventoryAccess implements PlatformInventoryAccess, IItemHandler {
   private boolean calling;
   private IInventoryAccess access;

   public PlatformProxyInventoryAccess(IInventoryAccess access) {
      this.access = access;
   }

   private IItemHandler getP() {
      return this.access.getPlatformHandler();
   }

   @Override
   public IItemHandler get() {
      return this;
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      if (this.calling) {
         return false;
      }

      this.calling = true;
      boolean v = this.getP().isItemValid(slot, stack);
      this.calling = false;
      return v;
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      if (this.calling) {
         return stack;
      }

      this.calling = true;
      ItemStack is = this.getP().insertItem(slot, stack, simulate);
      this.calling = false;
      return is;
   }

   public ItemStack getStackInSlot(int slot) {
      if (this.calling) {
         return ItemStack.EMPTY;
      }

      this.calling = true;
      ItemStack is = this.getP().getStackInSlot(slot);
      this.calling = false;
      return is;
   }

   public int getSlots() {
      if (this.calling) {
         return 0;
      }

      this.calling = true;
      int s = this.getP().getSlots();
      this.calling = false;
      return s;
   }

   public int getSlotLimit(int slot) {
      if (this.calling) {
         return 0;
      }

      this.calling = true;
      int s = this.getP().getSlotLimit(slot);
      this.calling = false;
      return s;
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if (this.calling) {
         return ItemStack.EMPTY;
      }

      this.calling = true;
      ItemStack is = this.getP().extractItem(slot, amount, simulate);
      this.calling = false;
      return is;
   }

   @Override
   public IInventoryAccess getRootHandler(Set<IProxy> dejaVu) {
      return dejaVu.add(this.access) ? this.access.getRootHandler(dejaVu) : this;
   }

   @Override
   public IInventoryAccess.IInventoryChangeTracker tracker() {
      if (this.calling) {
         return InventoryChangeTracker.NULL;
      }

      this.calling = true;
      IInventoryAccess.IInventoryChangeTracker c = this.access.tracker();
      this.calling = false;
      return c;
   }
}
