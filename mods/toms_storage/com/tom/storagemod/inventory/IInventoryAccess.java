package com.tom.storagemod.inventory;

import com.tom.storagemod.inventory.filter.ItemPredicate;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IInventoryAccess extends IChangeTrackerAccess, IProxy {
   default ItemStack pullMatchingStack(ItemStack st, long max) {
      InventorySlot slot = null;
      IInventoryAccess.IInventoryChangeTracker tr = this.tracker();
      StoredItemStack s = new StoredItemStack(st, 1L);
      ItemPredicate ip = s::equalItem;
      ItemStack ext = null;
      long ex = 0L;

      while (ex < max && (slot = tr.findSlotAfter(slot, ip, false, false)) != null) {
         ItemStack e = slot.extract((int)(max - ex));
         if (ext == null) {
            ext = e;
         } else {
            ext.grow(e.getCount());
         }

         ex += e.getCount();
      }

      return ext != null ? ext : ItemStack.EMPTY;
   }

   default ItemStack pushStack(ItemStack stack) {
      IInventoryAccess.IInventoryChangeTracker tr = this.tracker();
      StoredItemStack s = new StoredItemStack(stack);
      InventorySlot slot = null;

      while (!stack.isEmpty() && (slot = tr.findSlotDestAfter(slot, s, false)) != null) {
         stack = slot.insert(stack);
         s.setCount(stack.getCount());
      }

      return stack;
   }

   int getFreeSlotCount();

   int getSlotCount();

   @Deprecated
   Object get();

   @Override
   IInventoryAccess getRootHandler(Set<IProxy> var1);

   default <T> T getPlatformHandler() {
      T t = (T)this.get();
      return (T)(t != null ? t : PlatformInventoryAccess.EMPTY.get());
   }

   default void markInvalid() {
   }

   interface IChangeNotifier {
      void onSlotChanged(InventorySlot var1);
   }

   interface IInventory {
      IInventoryAccess getInventoryAccess();
   }

   interface IInventoryChangeTracker {
      long getChangeTracker(Level var1);

      Stream<StoredItemStack> streamWrappedStacks(boolean var1);

      long countItems(StoredItemStack var1);

      InventorySlot findSlot(ItemPredicate var1, boolean var2);

      InventorySlot findSlotAfter(InventorySlot var1, ItemPredicate var2, boolean var3, boolean var4);

      InventorySlot findSlotDest(StoredItemStack var1);

      InventorySlot findSlotDestAfter(InventorySlot var1, StoredItemStack var2, boolean var3);

      class Delegate implements IInventoryAccess.IInventoryChangeTracker, IInventoryAccess.IMultiThreadedTracker<Object, Object> {
         private IInventoryAccess.IInventoryChangeTracker delegate;

         @Override
         public Stream<StoredItemStack> streamWrappedStacks(boolean parallel) {
            return this.delegate.streamWrappedStacks(parallel);
         }

         @Override
         public long countItems(StoredItemStack filter) {
            return this.delegate.countItems(filter);
         }

         @Override
         public long getChangeTracker(Level level) {
            return this.delegate.getChangeTracker(level);
         }

         public void setDelegate(IInventoryAccess.IInventoryChangeTracker delegate) {
            this.delegate = delegate;
         }

         @Override
         public InventorySlot findSlot(ItemPredicate filter, boolean findEmpty) {
            return this.delegate.findSlot(filter, findEmpty);
         }

         @Override
         public Object prepForOffThread(Level level) {
            return this.delegate instanceof IInventoryAccess.IMultiThreadedTracker t ? t.prepForOffThread(level) : null;
         }

         @Override
         public Object processOffThread(Object array) {
            return ((IInventoryAccess.IMultiThreadedTracker)this.delegate).processOffThread(array);
         }

         @Override
         public long finishOffThreadProcess(Level level, Object ct) {
            return ((IInventoryAccess.IMultiThreadedTracker)this.delegate).finishOffThreadProcess(level, ct);
         }

         @Override
         public InventorySlot findSlotDest(StoredItemStack forStack) {
            return this.delegate.findSlotDest(forStack);
         }

         @Override
         public InventorySlot findSlotAfter(InventorySlot slot, ItemPredicate filter, boolean findEmpty, boolean loop) {
            return this.delegate.findSlotAfter(slot, filter, findEmpty, loop);
         }

         @Override
         public InventorySlot findSlotDestAfter(InventorySlot slot, StoredItemStack forStack, boolean loop) {
            return this.delegate.findSlotDestAfter(slot, forStack, loop);
         }
      }
   }

   interface IMultiThreadedTracker<A, B> {
      A prepForOffThread(Level var1);

      B processOffThread(A var1);

      long finishOffThreadProcess(Level var1, B var2);
   }
}
