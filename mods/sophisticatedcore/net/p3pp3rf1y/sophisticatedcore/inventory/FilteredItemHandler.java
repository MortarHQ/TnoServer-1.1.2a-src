package net.p3pp3rf1y.sophisticatedcore.inventory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public class FilteredItemHandler<T extends IItemHandler> implements IItemHandler, IItemHandlerSimpleInserter {
   protected final T inventoryHandler;
   protected final List<FilterLogic> inputFilters;
   protected final List<FilterLogic> outputFilters;

   public FilteredItemHandler(T inventoryHandler, List<FilterLogic> inputFilters, List<FilterLogic> outputFilters) {
      this.inventoryHandler = inventoryHandler;
      this.inputFilters = inputFilters;
      this.outputFilters = outputFilters;
   }

   public int getSlots() {
      return this.inventoryHandler.getSlots();
   }

   public ItemStack getStackInSlot(int slot) {
      return this.inventoryHandler.getStackInSlot(slot);
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      if (this.inputFilters.isEmpty()) {
         return this.inventoryHandler.insertItem(slot, stack, simulate);
      } else {
         return this.matchesFilters(stack, this.inputFilters) ? this.inventoryHandler.insertItem(slot, stack, simulate) : stack;
      }
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if (this.outputFilters.isEmpty()) {
         return this.inventoryHandler.extractItem(slot, amount, simulate);
      } else {
         return this.matchesFilters(this.getStackInSlot(slot), this.outputFilters)
            ? this.inventoryHandler.extractItem(slot, amount, simulate)
            : ItemStack.EMPTY;
      }
   }

   protected boolean matchesFilters(ItemStack stack, List<FilterLogic> filters) {
      boolean matchAll = this.shouldMatchAllFilters(filters);

      for (FilterLogic filter : filters) {
         if (matchAll && !filter.matchesFilter(stack)) {
            return false;
         }

         if (!matchAll && filter.matchesFilter(stack)) {
            return true;
         }
      }

      return matchAll;
   }

   private boolean shouldMatchAllFilters(List<FilterLogic> filters) {
      if (filters.size() < 2) {
         return false;
      }

      for (FilterLogic filter : filters) {
         if (!filter.isAllowList()) {
            return true;
         }
      }

      return false;
   }

   public int getSlotLimit(int slot) {
      return this.inventoryHandler.getSlotLimit(slot);
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      return this.matchesFilters(stack, this.inputFilters) ? this.inventoryHandler.isItemValid(slot, stack) : false;
   }

   @Override
   public ItemStack insertItem(ItemStack stack, boolean simulate) {
      if (!this.inputFilters.isEmpty() && !this.matchesFilters(stack, this.inputFilters)) {
         return stack;
      } else {
         return this.inventoryHandler instanceof IItemHandlerSimpleInserter simpleInserter
            ? simpleInserter.insertItem(stack, simulate)
            : InventoryHelper.insertIntoInventory(stack, this.inventoryHandler, simulate);
      }
   }

   public void setStackInSlot(int i, ItemStack itemStack) {
      if (this.inventoryHandler instanceof IItemHandlerSimpleInserter simpleInserter) {
         simpleInserter.setStackInSlot(i, itemStack);
      } else {
         throw new UnsupportedOperationException("setStackInSlot is not supported by the underlying inventory handler");
      }
   }

   public static class Modifiable extends FilteredItemHandler<ITrackedContentsItemHandler> implements ITrackedContentsItemHandler {
      public Modifiable(ITrackedContentsItemHandler inventoryHandler, List<FilterLogic> inputFilters, List<FilterLogic> outputFilters) {
         super(inventoryHandler, inputFilters, outputFilters);
      }

      @Override
      public void setStackInSlot(int slot, ItemStack stack) {
         this.inventoryHandler.setStackInSlot(slot, stack);
      }

      @Override
      public ItemStack insertItem(ItemStack stack, boolean simulate) {
         if (this.inputFilters.isEmpty()) {
            return this.inventoryHandler.insertItem(stack, simulate);
         } else {
            return this.matchesFilters(stack, this.inputFilters) ? this.inventoryHandler.insertItem(stack, simulate) : stack;
         }
      }

      @Override
      public Set<ItemStackKey> getTrackedStacks() {
         Set<ItemStackKey> ret = new HashSet<>();
         this.inventoryHandler.getTrackedStacks().forEach(ts -> {
            if (this.matchesFilters(ts.stack(), this.inputFilters)) {
               ret.add(ts);
            }
         });
         return ret;
      }

      @Override
      public void registerTrackingListeners(
         Consumer<ItemStackKey> onAddStackKey, Consumer<ItemStackKey> onRemoveStackKey, Runnable onAddFirstEmptySlot, Runnable onRemoveLastEmptySlot
      ) {
         this.inventoryHandler.registerTrackingListeners(isk -> {
            if (this.matchesFilters(isk.stack(), this.inputFilters)) {
               onAddStackKey.accept(isk);
            }
         }, isk -> {
            if (this.matchesFilters(isk.stack(), this.inputFilters)) {
               onRemoveStackKey.accept(isk);
            }
         }, onAddFirstEmptySlot, onRemoveLastEmptySlot);
      }

      @Override
      public void unregisterStackKeyListeners() {
         this.inventoryHandler.unregisterStackKeyListeners();
      }

      @Override
      public boolean hasEmptySlots() {
         return this.inventoryHandler.hasEmptySlots();
      }

      @Override
      public boolean isInsertBlocked() {
         return this.inventoryHandler.isInsertBlocked();
      }

      @Override
      public ItemStack extractItem(ItemStack stack, boolean simulate) {
         if (this.outputFilters.isEmpty()) {
            return this.inventoryHandler.extractItem(stack, simulate);
         } else {
            return this.matchesFilters(stack, this.outputFilters) ? this.inventoryHandler.extractItem(stack, simulate) : ItemStack.EMPTY;
         }
      }
   }
}
