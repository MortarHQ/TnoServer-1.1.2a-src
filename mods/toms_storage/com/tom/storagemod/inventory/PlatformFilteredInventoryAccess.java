package com.tom.storagemod.inventory;

import com.tom.storagemod.inventory.filter.IFilter;
import com.tom.storagemod.util.Priority;
import java.util.Set;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

public class PlatformFilteredInventoryAccess implements IInventoryAccess, Priority.IPriority, IItemHandler {
   private final IInventoryAccess acc;
   private final IFilter filter;
   private final InventoryChangeTracker tracker;

   public PlatformFilteredInventoryAccess(IInventoryAccess acc, final IFilter filter) {
      this.acc = acc;
      this.filter = filter;
      this.tracker = new InventoryChangeTracker(acc.getPlatformHandler()) {
         @Override
         protected boolean checkFilter(StoredItemStack stack) {
            return filter.getItemPred().test(stack);
         }

         @Override
         protected int getCount(ItemStack is) {
            return filter.isKeepLast() ? is.getCount() - 1 : is.getCount();
         }

         @Override
         protected IItemHandler getSlotHandler(IItemHandler def) {
            return PlatformFilteredInventoryAccess.this;
         }

         @Override
         public ItemStack[] prepForOffThread(Level level) {
            filter.getItemPred().updateState();
            return super.prepForOffThread(level);
         }

         @Override
         public long getChangeTracker(Level level) {
            filter.getItemPred().updateState();
            return super.getChangeTracker(level);
         }
      };
   }

   @Override
   public IInventoryAccess.IInventoryChangeTracker tracker() {
      return this.tracker;
   }

   @Override
   public ItemStack pushStack(ItemStack stack) {
      return !this.test(stack) ? stack : IInventoryAccess.super.pushStack(stack);
   }

   @Override
   public ItemStack pullMatchingStack(ItemStack st, long max) {
      return !this.test(st) ? ItemStack.EMPTY : IInventoryAccess.super.pullMatchingStack(st, max);
   }

   @Override
   public int getFreeSlotCount() {
      return this.acc.getFreeSlotCount();
   }

   @Override
   public int getSlotCount() {
      return this.acc.getSlotCount();
   }

   public IItemHandler get() {
      return this;
   }

   @Override
   public Priority getPriority() {
      Priority pr = Priority.IPriority.get(this.acc);
      return pr.add(this.filter.getPriority());
   }

   public int getSlots() {
      return this.getP().getSlots();
   }

   public ItemStack getStackInSlot(int slot) {
      ItemStack is = this.getP().getStackInSlot(slot);
      if (!this.test(is)) {
         return ItemStack.EMPTY;
      }

      if (this.filter.isKeepLast()) {
         is = is.copy();
         is.shrink(1);
      }

      return is;
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      return !this.test(stack) ? stack : this.getP().insertItem(slot, stack, simulate);
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      ItemStack is = this.getP().getStackInSlot(slot);
      if (!this.test(is)) {
         return ItemStack.EMPTY;
      }

      if (this.filter.isKeepLast()) {
         amount = Math.min(amount, is.getCount() - 1);
      }

      return this.getP().extractItem(slot, amount, simulate);
   }

   public int getSlotLimit(int slot) {
      return this.getP().getSlotLimit(slot);
   }

   private IItemHandler getP() {
      return this.acc.getPlatformHandler();
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      return this.test(stack) && this.getP().isItemValid(slot, stack);
   }

   private boolean test(ItemStack stack) {
      return this.filter.getItemPred().test(new StoredItemStack(stack, stack.getCount()));
   }

   @Override
   public IInventoryAccess getRootHandler(Set<IProxy> dejaVu) {
      return this.acc.getRootHandler(dejaVu);
   }

   @Override
   public String toString() {
      return "Filtering: {" + this.acc + " by " + this.filter + "}";
   }

   public IFilter getFilter() {
      return this.filter;
   }

   public IInventoryAccess getActualInventory() {
      return this.acc;
   }
}
