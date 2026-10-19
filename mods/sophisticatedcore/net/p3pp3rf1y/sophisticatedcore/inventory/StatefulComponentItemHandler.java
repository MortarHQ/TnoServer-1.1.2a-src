package net.p3pp3rf1y.sophisticatedcore.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public class StatefulComponentItemHandler implements IItemHandlerModifiable, ISlotChangeListener {
   protected NonNullList<ItemStack> stacks;
   protected final MutableDataComponentHolder parent;
   protected final DataComponentType<ItemContainerContents> component;
   protected final int size;

   public StatefulComponentItemHandler(MutableDataComponentHolder parent, DataComponentType<ItemContainerContents> component, int size) {
      this.parent = parent;
      this.component = component;
      this.size = size;
      Preconditions.checkArgument(size <= 256, "The max size of ItemContainerContents is 256 slots.");
      this.fillStacks();
   }

   private void fillStacks() {
      ItemContainerContents contents = this.getContents();
      this.stacks = NonNullList.withSize(this.size, ItemStack.EMPTY);
      contents.copyInto(this.stacks);
   }

   public int getSlots() {
      return this.size;
   }

   public ItemStack getStackInSlot(int slot) {
      this.validateSlotIndex(slot);
      return (ItemStack)this.stacks.get(slot);
   }

   public void setStackInSlot(int slot, ItemStack stack) {
      this.validateSlotIndex(slot);
      if (!this.isItemValid(slot, stack)) {
         throw new RuntimeException("Invalid stack " + stack + " for slot " + slot + ")");
      }

      ItemStack existing = (ItemStack)this.stacks.get(slot);
      if (!ItemStack.matches(stack, existing)) {
         this.updateContents(stack, slot);
      }
   }

   public ItemStack insertItem(int slot, ItemStack toInsert, boolean simulate) {
      this.validateSlotIndex(slot);
      if (toInsert.isEmpty()) {
         return ItemStack.EMPTY;
      }

      if (!this.isItemValid(slot, toInsert)) {
         return toInsert;
      }

      ItemStack existing = (ItemStack)this.stacks.get(slot);
      int insertLimit = Math.min(this.getSlotLimit(slot), toInsert.getMaxStackSize());
      if (!existing.isEmpty()) {
         if (!ItemStack.isSameItemSameComponents(toInsert, existing)) {
            return toInsert;
         }

         insertLimit -= existing.getCount();
      }

      if (insertLimit <= 0) {
         return toInsert;
      }

      int inserted = Math.min(insertLimit, toInsert.getCount());
      if (!simulate) {
         this.updateContents(toInsert.copyWithCount(existing.getCount() + inserted), slot);
      }

      return toInsert.copyWithCount(toInsert.getCount() - inserted);
   }

   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      this.validateSlotIndex(slot);
      if (amount == 0) {
         return ItemStack.EMPTY;
      }

      ItemStack existing = (ItemStack)this.stacks.get(slot);
      if (existing.isEmpty()) {
         return ItemStack.EMPTY;
      }

      int toExtract = Math.min(amount, existing.getMaxStackSize());
      if (!simulate) {
         this.updateContents(existing.copyWithCount(existing.getCount() - toExtract), slot);
      }

      return existing.copyWithCount(toExtract);
   }

   public int getSlotLimit(int slot) {
      return 99;
   }

   public boolean isItemValid(int slot, ItemStack stack) {
      return stack.getItem().canFitInsideContainerItems();
   }

   protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack newStack) {
   }

   protected ItemContainerContents getContents() {
      return (ItemContainerContents)this.parent.getOrDefault(this.component, ItemContainerContents.EMPTY);
   }

   protected void updateContents(ItemStack stack, int slot) {
      this.validateSlotIndex(slot);
      ItemStack oldStack = (ItemStack)this.stacks.get(slot);
      this.stacks.set(slot, stack);
      this.parent.set(this.component, ItemContainerContents.fromItems(this.stacks));
      this.onContentsChanged(slot, oldStack, stack);
   }

   protected final void validateSlotIndex(int slot) {
      if (slot < 0 || slot >= this.getSlots()) {
         throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.getSlots() + ")");
      }
   }

   @Override
   public void onSlotChanged(int slot) {
      this.updateContents(this.getStackInSlot(slot), slot);
   }
}
