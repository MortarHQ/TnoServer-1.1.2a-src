package com.tom.storagemod.inventory;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

public class InventorySlot {
   private final IItemHandler handler;
   private final IInventoryAccess.IChangeNotifier notifier;
   private final int id;

   public InventorySlot(IItemHandler h, IInventoryAccess.IChangeNotifier notifier, int i) {
      this.handler = h;
      this.notifier = notifier;
      this.id = i;
   }

   public ItemStack getStack() {
      return this.handler.getStackInSlot(this.id);
   }

   public ItemStack extract(int amount) {
      ItemStack is = this.handler.extractItem(this.id, amount, false);
      if (!is.isEmpty()) {
         this.notifyChange();
      }

      return is;
   }

   public ItemStack insert(ItemStack stack) {
      int c = stack.getCount();
      ItemStack is = this.handler.insertItem(this.id, stack, false);
      if (c != is.getCount()) {
         this.notifyChange();
      }

      return is;
   }

   public boolean transferTo(int amount, InventorySlot to) {
      ItemStack is = this.handler.extractItem(this.id, amount, true);
      if (is.isEmpty()) {
         return false;
      }

      int ex = is.getCount();
      is = to.handler.insertItem(to.id, is, true);
      if (is.isEmpty()) {
         is = this.handler.extractItem(this.id, amount, false);
         to.handler.insertItem(to.id, is, false);
         this.notifyChange();
         to.notifyChange();
         return true;
      }

      if (is.getCount() < ex) {
         int ins = ex - is.getCount();
         is = this.handler.extractItem(this.id, ins, true);
         is = to.handler.insertItem(to.id, is, true);
         if (is.isEmpty()) {
            is = this.handler.extractItem(this.id, ins, false);
            to.handler.insertItem(to.id, is, false);
            this.notifyChange();
            to.notifyChange();
            return true;
         }
      }

      return false;
   }

   private void notifyChange() {
      this.notifier.onSlotChanged(this);
   }

   protected IItemHandler getHandler() {
      return this.handler;
   }

   protected int getId() {
      return this.id;
   }
}
