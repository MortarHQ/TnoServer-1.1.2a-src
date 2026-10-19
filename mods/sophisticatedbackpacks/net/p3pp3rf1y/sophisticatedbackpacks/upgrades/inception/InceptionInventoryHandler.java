package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import net.p3pp3rf1y.sophisticatedcore.inventory.IInsertBlockOverride;
import net.p3pp3rf1y.sophisticatedcore.inventory.IItemHandlerSimpleInserter;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;

public class InceptionInventoryHandler implements ITrackedContentsItemHandler {
   private IItemHandlerModifiable combinedInventories;
   private final ITrackedContentsItemHandler wrappedInventoryHandler;
   private final InventoryOrder inventoryOrder;
   private final SubBackpacksHandler subBackpacksHandler;
   private List<ITrackedContentsItemHandler> handlers;

   public InceptionInventoryHandler(ITrackedContentsItemHandler wrappedInventoryHandler, InventoryOrder inventoryOrder, SubBackpacksHandler subBackpacksHandler) {
      this.wrappedInventoryHandler = wrappedInventoryHandler;
      this.inventoryOrder = inventoryOrder;
      this.subBackpacksHandler = subBackpacksHandler;
      subBackpacksHandler.addRefreshListener(sbs -> this.refreshHandlerDelegate());
      this.refreshHandlerDelegate();
   }

   private void refreshHandlerDelegate() {
      this.handlers = new ArrayList<>();
      if (this.inventoryOrder == InventoryOrder.MAIN_FIRST) {
         this.handlers.add(this.wrappedInventoryHandler);
      }

      this.subBackpacksHandler.getSubBackpacks().forEach(sbp -> this.handlers.add(sbp.getInventoryForInputOutput()));
      if (this.inventoryOrder == InventoryOrder.INCEPTED_FIRST) {
         this.handlers.add(this.wrappedInventoryHandler);
      }

      this.combinedInventories = new CombinedInvWrapper(this.handlers.toArray(new IItemHandlerModifiable[0])) {
         public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            ItemStack remaining = stack;

            for (ITrackedContentsItemHandler handler : InceptionInventoryHandler.this.handlers) {
               remaining = handler.insertItem(remaining, simulate);
               if (remaining.isEmpty()) {
                  break;
               }
            }

            return remaining;
         }
      };
   }

   public void setStackInSlot(int slot, ItemStack stack) {
      this.combinedInventories.setStackInSlot(slot, stack);
   }

   public int getSlots() {
      return this.combinedInventories.getSlots();
   }

   public ItemStack getStackInSlot(int slot) {
      return this.combinedInventories.getStackInSlot(slot);
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      return this.combinedInventories.insertItem(slot, stack, simulate);
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      return this.combinedInventories.extractItem(slot, amount, simulate);
   }

   public int getSlotLimit(int slot) {
      return this.combinedInventories.getSlotLimit(slot);
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      return this.combinedInventories.isItemValid(slot, stack);
   }

   public ItemStack insertItem(ItemStack stack, boolean simulate) {
      ItemStack remainingStack = stack;

      for (IItemHandlerSimpleInserter handler : this.handlers) {
         remainingStack = handler.insertItem(remainingStack, simulate);
         if (remainingStack.isEmpty()) {
            break;
         }
      }

      return remainingStack;
   }

   public Set<ItemStackKey> getTrackedStacks() {
      Set<ItemStackKey> ret = new HashSet<>();
      this.handlers.forEach(h -> ret.addAll(h.getTrackedStacks()));
      return ret;
   }

   public void registerTrackingListeners(
      Consumer<ItemStackKey> onAddStackKey, Consumer<ItemStackKey> onRemoveStackKey, Runnable onAddFirstEmptySlot, Runnable onRemoveLastEmptySlot
   ) {
      this.handlers.forEach(h -> h.registerTrackingListeners(onAddStackKey, onRemoveStackKey, onAddFirstEmptySlot, onRemoveLastEmptySlot));
   }

   public void unregisterStackKeyListeners() {
      this.handlers.forEach(ITrackedContentsItemHandler::unregisterStackKeyListeners);
   }

   public boolean hasEmptySlots() {
      return this.handlers.stream().anyMatch(ITrackedContentsItemHandler::hasEmptySlots);
   }

   public boolean isInsertBlocked() {
      return this.handlers.stream().allMatch(IInsertBlockOverride::isInsertBlocked);
   }

   public ItemStack extractItem(ItemStack stack, boolean simulate) {
      ItemStack remaining = stack;

      for (ITrackedContentsItemHandler handler : this.handlers) {
         ItemStack extracted = handler.extractItem(remaining, simulate);
         if (extracted.getCount() > 0) {
            remaining = stack.copyWithCount(remaining.getCount() - extracted.getCount());
         }

         if (remaining.isEmpty()) {
            break;
         }
      }

      return stack.getCount() == remaining.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - remaining.getCount());
   }
}
