package net.p3pp3rf1y.sophisticatedcore.inventory;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ISlotTracker {
   void setShouldInsertIntoEmpty(BooleanSupplier var1);

   Set<ItemStackKey> getFullStacks();

   Set<ItemStackKey> getPartialStacks();

   Set<Item> getItems();

   void removeAndSetSlotIndexes(InventoryHandler var1, int var2, ItemStack var3);

   void clear();

   void refreshSlotIndexesFrom(InventoryHandler var1);

   ItemStack insertItemIntoHandler(
      InventoryHandler var1,
      BiFunction<ItemStack, Boolean, ItemStack> var2,
      ISlotTracker.IItemHandlerInserter var3,
      UnaryOperator<ItemStack> var4,
      UnaryOperator<ItemStack> var5,
      ItemStack var6,
      boolean var7
   );

   ItemStack insertItemIntoHandler(
      InventoryHandler var1,
      BiFunction<ItemStack, Boolean, ItemStack> var2,
      ISlotTracker.IItemHandlerInserter var3,
      UnaryOperator<ItemStack> var4,
      UnaryOperator<ItemStack> var5,
      int var6,
      ItemStack var7,
      boolean var8
   );

   void registerListeners(Consumer<ItemStackKey> var1, Consumer<ItemStackKey> var2, Runnable var3, Runnable var4);

   void unregisterStackKeyListeners();

   boolean hasEmptySlots();

   boolean hasExactStackMemorized(ItemStackKey var1);

   boolean hasItemMemorizedOrFiltered(Item var1);

   int getFirstMatchingSlot(ItemStackKey var1);

   ItemStack extractItemFromHandler(InventoryHandler var1, ISlotTracker.IItemHandlerExtractor var2, ItemStack var3, boolean var4);

   interface IItemHandlerExtractor {
      ItemStack extractItem(int var1, int var2, boolean var3);
   }

   interface IItemHandlerInserter {
      ItemStack insertItem(int var1, ItemStack var2, boolean var3);
   }

   class Noop implements ISlotTracker {
      @Override
      public void setShouldInsertIntoEmpty(BooleanSupplier shouldInsertIntoEmpty) {
      }

      @Override
      public Set<ItemStackKey> getFullStacks() {
         return Collections.emptySet();
      }

      @Override
      public Set<ItemStackKey> getPartialStacks() {
         return Collections.emptySet();
      }

      @Override
      public Set<Item> getItems() {
         return Collections.emptySet();
      }

      @Override
      public void removeAndSetSlotIndexes(InventoryHandler inventoryHandler, int slot, ItemStack stack) {
      }

      @Override
      public void clear() {
      }

      @Override
      public void refreshSlotIndexesFrom(InventoryHandler itemHandler) {
      }

      @Override
      public ItemStack insertItemIntoHandler(
         InventoryHandler itemHandler,
         BiFunction<ItemStack, Boolean, ItemStack> beforeInsertHandler,
         ISlotTracker.IItemHandlerInserter inserter,
         UnaryOperator<ItemStack> slotOverflowHandler,
         UnaryOperator<ItemStack> storageOverflowHandler,
         ItemStack stack,
         boolean simulate
      ) {
         return stack;
      }

      @Override
      public ItemStack extractItemFromHandler(
         InventoryHandler inventoryHandler, ISlotTracker.IItemHandlerExtractor extractItemInternal, ItemStack stack, boolean simulate
      ) {
         return ItemStack.EMPTY;
      }

      @Override
      public ItemStack insertItemIntoHandler(
         InventoryHandler itemHandler,
         BiFunction<ItemStack, Boolean, ItemStack> beforeInsertHandler,
         ISlotTracker.IItemHandlerInserter inserter,
         UnaryOperator<ItemStack> slotOverflowHandler,
         UnaryOperator<ItemStack> storageOverflowHandler,
         int slot,
         ItemStack stack,
         boolean simulate
      ) {
         return inserter.insertItem(slot, stack, simulate);
      }

      @Override
      public void registerListeners(
         Consumer<ItemStackKey> onAddStackKey, Consumer<ItemStackKey> onRemoveStackKey, Runnable onAddFirstEmptySlot, Runnable onRemoveLastEmptySlot
      ) {
      }

      @Override
      public void unregisterStackKeyListeners() {
      }

      @Override
      public boolean hasEmptySlots() {
         return false;
      }

      @Override
      public int getFirstMatchingSlot(ItemStackKey stackKey) {
         return -1;
      }

      @Override
      public boolean hasExactStackMemorized(ItemStackKey stackKey) {
         return false;
      }

      @Override
      public boolean hasItemMemorizedOrFiltered(Item item) {
         return false;
      }
   }
}
