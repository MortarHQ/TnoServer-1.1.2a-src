package com.tom.storagemod.inventory;

import java.util.Set;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

public class PlatformItemHandler implements IItemHandler, IProxy {
   private IInventoryAccess.IInventory access;

   private IItemHandler getP() {
      return this.access.getInventoryAccess().getPlatformHandler();
   }

   public PlatformItemHandler(IInventoryAccess.IInventory access) {
      this.access = access;
   }

   public int getSlots() {
      return this.getP().getSlots();
   }

   public ItemStack getStackInSlot(int slot) {
      return this.getP().getStackInSlot(slot);
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      return this.getP().insertItem(slot, stack, simulate);
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      return this.getP().extractItem(slot, amount, simulate);
   }

   public int getSlotLimit(int slot) {
      return this.getP().getSlotLimit(slot);
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      return this.getP().isItemValid(slot, stack);
   }

   @Override
   public IInventoryAccess getRootHandler(Set<IProxy> dejaVu) {
      return this.access.getInventoryAccess().getRootHandler(dejaVu);
   }
}
