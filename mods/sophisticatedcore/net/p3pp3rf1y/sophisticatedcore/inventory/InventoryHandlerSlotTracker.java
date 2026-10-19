package net.p3pp3rf1y.sophisticatedcore.inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.util.SlotValueMap;
import org.apache.logging.log4j.util.Supplier;

public class InventoryHandlerSlotTracker implements ISlotTracker {
   private final Map<ItemStackKey, Set<Integer>> fullStackSlots = new HashMap<>();
   private final Map<Integer, ItemStackKey> fullSlotStacks = new HashMap<>();
   private final Map<ItemStackKey, Set<Integer>> partiallyFilledStackSlots = new HashMap<>();
   private final Map<Integer, ItemStackKey> partiallyFilledSlotStacks = new HashMap<>();
   private final Map<Item, Set<ItemStackKey>> itemStackKeys = new HashMap<>();
   private final Set<Integer> emptySlots = new TreeSet<>();
   private final MemorySettingsCategory memorySettings;
   private final SlotValueMap<Item> filterItemSlots;
   private Consumer<ItemStackKey> onAddStackKey = sk -> {};
   private Consumer<ItemStackKey> onRemoveStackKey = sk -> {};
   private Runnable onAddFirstEmptySlot = () -> {};
   private Runnable onRemoveLastEmptySlot = () -> {};
   private BooleanSupplier shouldInsertIntoEmpty = () -> true;

   public InventoryHandlerSlotTracker(MemorySettingsCategory memorySettings, SlotValueMap<Item> filterItemSlots) {
      this.memorySettings = memorySettings;
      this.filterItemSlots = filterItemSlots;
   }

   @Override
   public void setShouldInsertIntoEmpty(BooleanSupplier shouldInsertIntoEmpty) {
      this.shouldInsertIntoEmpty = shouldInsertIntoEmpty;
   }

   public void addPartiallyFilled(int slot, ItemStack stack) {
      ItemStackKey stackKey = ItemStackKey.of(stack);
      this.partiallyFilledStackSlots.computeIfAbsent(stackKey, k -> {
         if (!this.fullStackSlots.containsKey(k)) {
            this.onAddStackKey.accept(k);
         }

         return new TreeSet<>();
      }).add(slot);
      this.partiallyFilledSlotStacks.put(slot, stackKey);
      this.itemStackKeys.computeIfAbsent(stack.getItem(), i -> new HashSet<>()).add(stackKey);
   }

   @Override
   public Set<ItemStackKey> getFullStacks() {
      return this.fullStackSlots.keySet();
   }

   @Override
   public Set<ItemStackKey> getPartialStacks() {
      return this.partiallyFilledStackSlots.keySet();
   }

   @Override
   public boolean hasExactStackMemorized(ItemStackKey stackKey) {
      return this.memorySettings.matchesStackKey(stackKey);
   }

   @Override
   public boolean hasItemMemorizedOrFiltered(Item item) {
      return this.memorySettings.matchesItem(item) || this.filterItemSlots.containsValue(item);
   }

   @Override
   public Set<Item> getItems() {
      return this.itemStackKeys.keySet();
   }

   public void addFull(int slot, ItemStack stack) {
      ItemStackKey stackKey = ItemStackKey.of(stack);
      this.fullStackSlots.computeIfAbsent(stackKey, k -> {
         if (!this.partiallyFilledStackSlots.containsKey(k)) {
            this.onAddStackKey.accept(k);
         }

         return new TreeSet<>();
      }).add(slot);
      this.fullSlotStacks.put(slot, stackKey);
      this.itemStackKeys.computeIfAbsent(stack.getItem(), i -> new HashSet<>()).add(stackKey);
   }

   public void removePartiallyFilled(int slot) {
      if (this.partiallyFilledSlotStacks.containsKey(slot)) {
         ItemStackKey stackKey = this.partiallyFilledSlotStacks.remove(slot);
         Set<Integer> partialSlots = this.partiallyFilledStackSlots.get(stackKey);
         if (partialSlots == null) {
            SophisticatedCore.LOGGER
               .error("Unstable ItemStack detected in slot tracking: {}", new Supplier[]{() -> stackKey != null ? stackKey.stack().toString() : "null"});
         } else {
            partialSlots.remove(slot);
         }

         if (partialSlots == null || partialSlots.isEmpty()) {
            this.partiallyFilledStackSlots.remove(stackKey);
            if (!this.fullStackSlots.containsKey(stackKey)) {
               this.onStackKeyRemoved(stackKey);
            }
         }
      }
   }

   private void removeFull(int slot) {
      if (this.fullSlotStacks.containsKey(slot)) {
         ItemStackKey stackKey = this.fullSlotStacks.remove(slot);
         Set<Integer> fullSlots = this.fullStackSlots.get(stackKey);
         if (fullSlots == null) {
            SophisticatedCore.LOGGER
               .error("Unstable ItemStack detected in slot tracking: {}", new Supplier[]{() -> stackKey != null ? stackKey.stack().toString() : "null"});
         } else {
            fullSlots.remove(slot);
         }

         if (fullSlots == null || fullSlots.isEmpty()) {
            this.fullStackSlots.remove(stackKey);
            if (!this.partiallyFilledStackSlots.containsKey(stackKey)) {
               this.onStackKeyRemoved(stackKey);
            }
         }
      }
   }

   private void onStackKeyRemoved(ItemStackKey stackKey) {
      this.itemStackKeys.computeIfPresent(stackKey.getStack().getItem(), (i, stackKeys) -> {
         stackKeys.remove(stackKey);
         return stackKeys;
      });
      if (this.itemStackKeys.containsKey(stackKey.getStack().getItem()) && this.itemStackKeys.get(stackKey.getStack().getItem()).isEmpty()) {
         this.itemStackKeys.remove(stackKey.getStack().getItem());
      }

      this.onRemoveStackKey.accept(stackKey);
   }

   @Override
   public void removeAndSetSlotIndexes(InventoryHandler inventoryHandler, int slot, ItemStack stack) {
      if (stack.isEmpty()) {
         this.removePartiallyFilled(slot);
         this.removeFull(slot);
         if (inventoryHandler.isSlotAccessible(slot)) {
            this.addEmptySlot(slot);
         }
      } else {
         if (this.emptySlots.contains(slot)) {
            this.removeEmpty(slot);
         }

         if (this.isPartiallyFilled(inventoryHandler, slot, stack)) {
            this.setPartiallyFilled(slot, stack);
         } else {
            this.setFull(slot, stack);
         }
      }
   }

   private void setFull(int slot, ItemStack stack) {
      boolean containsSlot = this.fullSlotStacks.containsKey(slot);
      if (!containsSlot || this.fullSlotStacks.get(slot).hashCodeNotEquals(stack)) {
         if (containsSlot) {
            this.removeFull(slot);
         }

         this.addFull(slot, stack);
      }

      if (this.partiallyFilledSlotStacks.containsKey(slot)) {
         this.removePartiallyFilled(slot);
      }
   }

   private void setPartiallyFilled(int slot, ItemStack stack) {
      boolean containsSlot = this.partiallyFilledSlotStacks.containsKey(slot);
      if (!containsSlot || this.partiallyFilledSlotStacks.get(slot).hashCodeNotEquals(stack)) {
         if (containsSlot) {
            this.removePartiallyFilled(slot);
         }

         this.addPartiallyFilled(slot, stack);
      }

      if (this.fullSlotStacks.containsKey(slot)) {
         this.removeFull(slot);
      }
   }

   private void removeEmpty(int slot) {
      this.emptySlots.remove(slot);
      if (this.emptySlots.isEmpty()) {
         this.onRemoveLastEmptySlot.run();
      }
   }

   private void set(InventoryHandler inventoryHandler, int slot, ItemStack stack) {
      if (stack.isEmpty()) {
         if (inventoryHandler.isSlotAccessible(slot)) {
            this.addEmptySlot(slot);
         }
      } else if (this.isPartiallyFilled(inventoryHandler, slot, stack)) {
         this.addPartiallyFilled(slot, stack);
      } else {
         this.addFull(slot, stack);
      }
   }

   private void addEmptySlot(int slot) {
      this.emptySlots.add(slot);
      if (this.emptySlots.size() == 1) {
         this.onAddFirstEmptySlot.run();
      }
   }

   @Override
   public void clear() {
      this.partiallyFilledStackSlots.clear();
      this.partiallyFilledSlotStacks.clear();
   }

   @Override
   public void refreshSlotIndexesFrom(InventoryHandler itemHandler) {
      this.fullStackSlots.keySet().forEach(sk -> this.onRemoveStackKey.accept(sk));
      this.fullStackSlots.clear();
      this.fullSlotStacks.clear();
      this.partiallyFilledStackSlots.keySet().forEach(sk -> this.onRemoveStackKey.accept(sk));
      this.partiallyFilledStackSlots.clear();
      this.partiallyFilledSlotStacks.clear();
      this.itemStackKeys.clear();
      this.emptySlots.clear();
      this.onRemoveLastEmptySlot.run();

      for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
         ItemStack stack = itemHandler.getStackInSlot(slot);
         this.set(itemHandler, slot, stack);
      }
   }

   private boolean isPartiallyFilled(InventoryHandler itemHandler, int slot, ItemStack stack) {
      return stack.getCount() < itemHandler.getStackLimit(slot, stack);
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
      if (this.emptySlots.isEmpty() && !this.itemStackKeys.containsKey(stack.getItem())) {
         return stack;
      }

      ItemStackKey stackKey = ItemStackKey.of(stack);
      ItemStack remainingStack = beforeInsertHandler.apply(stack, simulate);
      remainingStack = this.handleOverflow(slotOverflowHandler, storageOverflowHandler, stackKey, remainingStack);
      if (remainingStack.isEmpty()) {
         return remainingStack;
      }

      remainingStack = this.insertIntoSlotsThatMatchStack(inserter, remainingStack, simulate, stackKey);
      if (!remainingStack.isEmpty()) {
         remainingStack = this.insertIntoEmptySlots(inserter, remainingStack, simulate);
      }

      if (!remainingStack.isEmpty()) {
         remainingStack = this.handleOverflow(slotOverflowHandler, storageOverflowHandler, stackKey, remainingStack);
      }

      return remainingStack;
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
      if (!simulate) {
         return this.insertItemIntoHandler(itemHandler, beforeInsertHandler, inserter, slotOverflowHandler, storageOverflowHandler, stack, simulate);
      }

      ItemStack remainingStack = beforeInsertHandler.apply(stack, simulate);
      if (remainingStack.isEmpty()) {
         return remainingStack;
      }

      if (this.fullSlotStacks.containsKey(slot)) {
         return this.handleOverflow(slotOverflowHandler, storageOverflowHandler, ItemStackKey.of(stack), remainingStack);
      }

      if (this.emptySlots.contains(slot)) {
         if (this.memorySettings.isSlotSelected(slot) && this.memorySettings.matchesFilter(slot, stack)) {
            return inserter.insertItem(slot, remainingStack, true);
         }

         if (this.filterItemSlots.getSlots(stack.getItem()).contains(slot)) {
            return inserter.insertItem(slot, remainingStack, true);
         }

         if (this.shouldInsertIntoEmpty.getAsBoolean()) {
            if ((!this.memorySettings.isSlotSelected(slot) || this.memorySettings.matchesFilter(slot, stack))
               && !this.filterItemSlots.containsSlotAndDoesNotMatch(slot, stack.getItem())) {
               return inserter.insertItem(slot, remainingStack, true);
            }

            ItemStackKey stackKey = ItemStackKey.of(stack);
            return this.handleOverflow(slotOverflowHandler, storageOverflowHandler, stackKey, remainingStack, this.hasOneFullStackOfItem(stackKey));
         }
      } else if (this.partiallyFilledSlotStacks.containsKey(slot)) {
         if (this.partiallyFilledSlotStacks.get(slot).hashCode() == ItemStackKey.of(stack).hashCode()) {
            remainingStack = inserter.insertItem(slot, remainingStack, true);
         }

         if (remainingStack.isEmpty()) {
            return remainingStack;
         }

         boolean insertedSomething = remainingStack.getCount() != stack.getCount();
         ItemStackKey stackKey = ItemStackKey.of(remainingStack);
         return this.handleOverflow(
            slotOverflowHandler, storageOverflowHandler, stackKey, remainingStack, insertedSomething || this.hasOneFullStackOfItem(stackKey)
         );
      }

      return remainingStack;
   }

   @Override
   public ItemStack extractItemFromHandler(
      InventoryHandler inventoryHandler, ISlotTracker.IItemHandlerExtractor extractItemInternal, ItemStack stack, boolean simulate
   ) {
      if (this.partiallyFilledSlotStacks.isEmpty() && this.fullSlotStacks.isEmpty()) {
         return ItemStack.EMPTY;
      }

      ItemStackKey stackKey = ItemStackKey.of(stack);
      int extracted = 0;
      Set<Integer> slots = this.partiallyFilledStackSlots.get(stackKey);
      if (slots != null && !slots.isEmpty()) {
         int originalSize = slots.size();
         int i = 0;

         while (this.partiallyFilledStackSlots.get(stackKey) != null && !this.partiallyFilledStackSlots.get(stackKey).isEmpty() && i++ < originalSize) {
            int matchingSlot = this.partiallyFilledStackSlots.get(stackKey).iterator().next();
            extracted += extractItemInternal.extractItem(matchingSlot, stack.getCount() - extracted, simulate).getCount();
            if (extracted >= stack.getCount()) {
               break;
            }
         }
      }

      slots = this.fullStackSlots.get(stackKey);
      if (slots != null && !slots.isEmpty() && stack.getCount() > extracted) {
         int originalSize = slots.size();
         int i = 0;

         while (this.fullStackSlots.get(stackKey) != null && !this.fullStackSlots.get(stackKey).isEmpty() && i++ < originalSize) {
            int matchingSlot = this.fullStackSlots.get(stackKey).iterator().next();
            extracted += extractItemInternal.extractItem(matchingSlot, stack.getCount() - extracted, simulate).getCount();
            if (extracted >= stack.getCount()) {
               break;
            }
         }
      }

      return extracted > 0 ? stack.copyWithCount(extracted) : ItemStack.EMPTY;
   }

   @Override
   public void registerListeners(
      Consumer<ItemStackKey> onAddStackKey, Consumer<ItemStackKey> onRemoveStackKey, Runnable onAddFirstEmptySlot, Runnable onRemoveLastEmptySlot
   ) {
      this.onAddStackKey = onAddStackKey;
      this.onRemoveStackKey = onRemoveStackKey;
      this.onAddFirstEmptySlot = onAddFirstEmptySlot;
      this.onRemoveLastEmptySlot = onRemoveLastEmptySlot;
   }

   @Override
   public void unregisterStackKeyListeners() {
      this.onAddStackKey = sk -> {};
      this.onRemoveStackKey = sk -> {};
   }

   @Override
   public boolean hasEmptySlots() {
      return this.shouldInsertIntoEmpty.getAsBoolean() && !this.emptySlots.isEmpty();
   }

   private ItemStack handleOverflow(
      UnaryOperator<ItemStack> slotOverflowHandler, UnaryOperator<ItemStack> storageOverflowHandler, ItemStackKey stackKey, ItemStack remainingStack
   ) {
      boolean hasOneFullStackOfItem = this.hasOneFullStackOfItem(stackKey);
      return this.handleOverflow(slotOverflowHandler, storageOverflowHandler, stackKey, remainingStack, hasOneFullStackOfItem);
   }

   private boolean hasOneFullStackOfItem(ItemStackKey stackKey) {
      return this.fullStackSlots.containsKey(stackKey) && !this.fullStackSlots.get(stackKey).isEmpty();
   }

   private ItemStack handleOverflow(
      UnaryOperator<ItemStack> slotOverflowHandler,
      UnaryOperator<ItemStack> storageOverflowHandler,
      ItemStackKey stackKey,
      ItemStack remainingStack,
      boolean hasOneFullSlotOfItem
   ) {
      if (hasOneFullSlotOfItem) {
         if (this.emptySlots.isEmpty() && !this.partiallyFilledStackSlots.containsKey(stackKey)) {
            remainingStack = storageOverflowHandler.apply(remainingStack);
            if (remainingStack.isEmpty()) {
               return remainingStack;
            }
         }

         remainingStack = slotOverflowHandler.apply(remainingStack);
      }

      return remainingStack;
   }

   private ItemStack insertIntoSlotsThatMatchStack(ISlotTracker.IItemHandlerInserter inserter, ItemStack stack, boolean simulate, ItemStackKey stackKey) {
      ItemStack remainingStack = stack;
      Set<Integer> slots = this.partiallyFilledStackSlots.get(stackKey);
      if (slots != null && !slots.isEmpty()) {
         int sizeBefore = slots.size();
         int i = 0;

         while (this.partiallyFilledStackSlots.get(stackKey) != null && !this.partiallyFilledStackSlots.get(stackKey).isEmpty() && i++ < sizeBefore) {
            int matchingSlot = this.partiallyFilledStackSlots.get(stackKey).iterator().next();
            remainingStack = inserter.insertItem(matchingSlot, remainingStack, simulate);
            if (remainingStack.isEmpty()) {
               break;
            }
         }

         return remainingStack;
      } else {
         return remainingStack;
      }
   }

   private ItemStack insertIntoEmptySlots(ISlotTracker.IItemHandlerInserter inserter, ItemStack stack, boolean simulate) {
      ItemStack remainingStack = stack;
      remainingStack = this.insertIntoEmptyMemorySlots(inserter, simulate, remainingStack);
      remainingStack = this.insertIntoEmptyFilterSlots(inserter, simulate, remainingStack);
      if (this.shouldInsertIntoEmpty.getAsBoolean() && !remainingStack.isEmpty()) {
         int sizeBefore = this.emptySlots.size();
         int i = 0;

         while (!this.emptySlots.isEmpty() && i++ < sizeBefore) {
            Iterator<Integer> it = this.emptySlots.iterator();

            int slot;
            for (slot = it.next(); this.memorySettings.isSlotSelected(slot); slot = it.next()) {
               if (!it.hasNext()) {
                  return remainingStack;
               }
            }

            while (this.filterItemSlots.containsSlot(slot)) {
               if (!it.hasNext()) {
                  return remainingStack;
               }

               slot = it.next();
            }

            remainingStack = inserter.insertItem(slot, remainingStack, simulate);
            if (remainingStack.isEmpty()) {
               break;
            }
         }
      }

      return remainingStack;
   }

   private ItemStack insertIntoEmptyFilterSlots(ISlotTracker.IItemHandlerInserter inserter, boolean simulate, ItemStack remainingStack) {
      Item item = remainingStack.getItem();
      if (this.filterItemSlots.containsValue(item)) {
         for (int filterSlot : this.filterItemSlots.getSlots(item)) {
            if (this.emptySlots.contains(filterSlot)) {
               remainingStack = inserter.insertItem(filterSlot, remainingStack, simulate);
               if (remainingStack.isEmpty()) {
                  break;
               }
            }
         }
      }

      return remainingStack;
   }

   private ItemStack insertIntoEmptyMemorySlots(ISlotTracker.IItemHandlerInserter inserter, boolean simulate, ItemStack stack) {
      ItemStack remainingStack = stack;
      Map<Item, Set<Integer>> memoryFilterItemSlots = this.memorySettings.getFilterItemSlots();
      Item item = remainingStack.getItem();
      if (memoryFilterItemSlots.containsKey(item)) {
         for (int memorySlot : memoryFilterItemSlots.get(item)) {
            if (this.emptySlots.contains(memorySlot)) {
               remainingStack = inserter.insertItem(memorySlot, remainingStack, simulate);
               if (remainingStack.isEmpty()) {
                  break;
               }
            }
         }
      }

      Map<Integer, Set<Integer>> memoryFilterStackSlots = this.memorySettings.getFilterStackSlots();
      if (!memoryFilterStackSlots.isEmpty()) {
         int stackHash = ItemStack.hashItemAndComponents(remainingStack);
         if (memoryFilterStackSlots.containsKey(stackHash)) {
            for (int memorySlot : memoryFilterStackSlots.get(stackHash)) {
               if (this.emptySlots.contains(memorySlot)) {
                  remainingStack = inserter.insertItem(memorySlot, remainingStack, simulate);
                  if (remainingStack.isEmpty()) {
                     break;
                  }
               }
            }
         }
      }

      return remainingStack;
   }

   @Override
   public int getFirstMatchingSlot(ItemStackKey stackKey) {
      Set<Integer> slots = this.partiallyFilledStackSlots.get(stackKey);
      if (slots != null && !slots.isEmpty()) {
         return slots.iterator().next();
      }

      slots = this.fullStackSlots.get(stackKey);
      return slots != null && !slots.isEmpty() ? slots.iterator().next() : -1;
   }
}
