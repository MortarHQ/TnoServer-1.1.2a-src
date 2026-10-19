package com.tom.storagemod.inventory;

import com.tom.storagemod.inventory.filter.ItemPredicate;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

public class InventoryChangeTracker
   implements IInventoryAccess.IInventoryChangeTracker,
   IInventoryAccess.IMultiThreadedTracker<ItemStack[], Long>,
   IInventoryAccess.IChangeNotifier {
   public static final InventoryChangeTracker NULL = new InventoryChangeTracker(null);
   private final WeakReference<IItemHandler> itemHandler;
   private long lastUpdate;
   private long lastChange;
   private StoredItemStack[] lastItems = new StoredItemStack[0];

   public InventoryChangeTracker(IItemHandler itemHandler) {
      this.itemHandler = new WeakReference<>(itemHandler);
   }

   @Override
   public long getChangeTracker(Level level) {
      IItemHandler h = this.itemHandler.get();
      if (h == null) {
         return 0L;
      }

      if (this.lastUpdate != level.getGameTime()) {
         int slots = h.getSlots();
         if (this.lastItems.length != slots) {
            this.lastItems = new StoredItemStack[slots];
         }

         boolean change = false;

         for (int i = 0; i < slots; i++) {
            ItemStack is = h.getStackInSlot(i);
            change |= this.updateChange(i, is);
         }

         if (change) {
            this.lastChange = System.nanoTime();
         }

         this.lastUpdate = level.getGameTime();
      }

      return this.lastChange;
   }

   protected boolean checkFilter(ItemStack stack) {
      return this.checkFilter(new StoredItemStack(stack, stack.getCount()));
   }

   protected boolean checkFilter(StoredItemStack stack) {
      return true;
   }

   protected int getCount(ItemStack is) {
      return is.getCount();
   }

   protected IItemHandler getSlotHandler(IItemHandler def) {
      return def;
   }

   private boolean updateChange(int i, ItemStack is) {
      if (!is.isEmpty() && this.checkFilter(is)) {
         int cnt = this.getCount(is);
         if (this.lastItems[i] == null || !ItemStack.isSameItemSameComponents(this.lastItems[i].getStack(), is)) {
            this.lastItems[i] = new StoredItemStack(is);
            this.lastItems[i].setCount(cnt);
            return true;
         }

         if (this.lastItems[i].getQuantity() != cnt) {
            this.lastItems[i].setCount(cnt);
            return true;
         }
      } else if (this.lastItems[i] != null) {
         this.lastItems[i] = null;
         return true;
      }

      return false;
   }

   @Override
   public Stream<StoredItemStack> streamWrappedStacks(boolean parallel) {
      IItemHandler h = this.itemHandler.get();
      return h == null ? Stream.empty() : Arrays.stream(this.lastItems).filter(e -> e != null);
   }

   @Override
   public long countItems(StoredItemStack filter) {
      IItemHandler h = this.itemHandler.get();
      if (h == null) {
         return 0L;
      }

      long c = 0L;

      for (int i = 0; i < this.lastItems.length; i++) {
         StoredItemStack is = this.lastItems[i];
         if (is != null && is.equalItem(filter)) {
            c += is.getQuantity();
         }
      }

      return c;
   }

   @Override
   public InventorySlot findSlot(ItemPredicate filter, boolean findEmpty) {
      IItemHandler h = this.itemHandler.get();
      if (h == null) {
         return null;
      }

      for (int i = 0; i < this.lastItems.length; i++) {
         StoredItemStack is = this.lastItems[i];
         if (is == null) {
            if (findEmpty) {
               return new InventorySlot(this.getSlotHandler(h), this, i);
            }
         } else if (filter.test(is)) {
            return new InventorySlot(this.getSlotHandler(h), this, i);
         }
      }

      return null;
   }

   public ItemStack[] prepForOffThread(Level level) {
      if (this.lastUpdate == level.getGameTime()) {
         return null;
      }

      IItemHandler h = this.itemHandler.get();
      if (h == null) {
         return null;
      }

      int slots = h.getSlots();
      if (this.lastItems.length != slots) {
         this.lastItems = new StoredItemStack[slots];
      }

      ItemStack[] items = new ItemStack[slots];

      for (int i = 0; i < slots; i++) {
         items[i] = h.getStackInSlot(i);
      }

      return items;
   }

   public Long processOffThread(ItemStack[] array) {
      int slots = array.length;
      boolean change = false;

      for (int i = 0; i < slots; i++) {
         ItemStack is = array[i];
         change |= this.updateChange(i, is);
      }

      return change ? System.nanoTime() : this.lastChange;
   }

   public long finishOffThreadProcess(Level level, Long ct) {
      if (ct == null) {
         return this.lastChange;
      }

      this.lastChange = ct;
      this.lastUpdate = level.getGameTime();
      return ct;
   }

   @Override
   public void onSlotChanged(InventorySlot slot) {
      if (this.lastItems.length > slot.getId() && this.updateChange(slot.getId(), slot.getStack())) {
         this.lastChange = System.nanoTime();
      }
   }

   @Override
   public InventorySlot findSlotDest(StoredItemStack forStack) {
      IItemHandler h = this.itemHandler.get();
      if (h == null) {
         return null;
      }

      if (!this.checkFilter(forStack)) {
         return null;
      }

      for (int i = 0; i < this.lastItems.length; i++) {
         StoredItemStack is = this.lastItems[i];
         if ((is != null || h.isItemValid(i, forStack.getStack())) && (is == null || is.equalItem(forStack))) {
            int rem = h.insertItem(i, forStack.getActualStack(), true).getCount();
            if (rem < forStack.getQuantity()) {
               return new InventorySlot(this.getSlotHandler(h), this, i);
            }
         }
      }

      return null;
   }

   @Override
   public InventorySlot findSlotAfter(InventorySlot slot, ItemPredicate filter, boolean findEmpty, boolean loop) {
      if (slot == null) {
         return this.findSlot(filter, findEmpty);
      }

      IItemHandler h = this.itemHandler.get();
      if (h == null) {
         return null;
      }

      if (h.getSlots() <= slot.getId() + 1) {
         return loop ? this.findSlot(filter, findEmpty) : null;
      }

      for (int i = slot.getId() + 1; i < this.lastItems.length; i++) {
         StoredItemStack is = this.lastItems[i];
         if (is == null) {
            if (findEmpty) {
               return new InventorySlot(this.getSlotHandler(h), this, i);
            }
         } else if (filter.test(is)) {
            return new InventorySlot(this.getSlotHandler(h), this, i);
         }
      }

      return null;
   }

   @Override
   public InventorySlot findSlotDestAfter(InventorySlot slot, StoredItemStack forStack, boolean loop) {
      if (slot == null) {
         return this.findSlotDest(forStack);
      }

      IItemHandler h = this.itemHandler.get();
      if (h == null) {
         return null;
      }

      if (!this.checkFilter(forStack)) {
         return null;
      }

      if (h.getSlots() <= slot.getId() + 1) {
         return loop ? this.findSlotDest(forStack) : null;
      }

      for (int i = slot.getId() + 1; i < this.lastItems.length; i++) {
         StoredItemStack is = this.lastItems[i];
         if ((is != null || h.isItemValid(i, forStack.getStack())) && (is == null || is.equalItem(forStack))) {
            int rem = h.insertItem(i, forStack.getActualStack(), true).getCount();
            if (rem < forStack.getQuantity()) {
               return new InventorySlot(this.getSlotHandler(h), this, i);
            }
         }
      }

      return null;
   }
}
