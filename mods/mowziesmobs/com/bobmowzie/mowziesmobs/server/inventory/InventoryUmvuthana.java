package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.Trade;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class InventoryUmvuthana implements Container {
   private final EntityUmvuthanaMinion umvuthana;
   private final List<ItemStack> slots = NonNullList.withSize(2, ItemStack.EMPTY);
   private Trade trade;

   public InventoryUmvuthana(EntityUmvuthanaMinion umvuthana) {
      this.umvuthana = umvuthana;
   }

   public int getContainerSize() {
      return this.slots.size();
   }

   public ItemStack getItem(int index) {
      return this.slots.get(index);
   }

   public ItemStack removeItem(int index, int count) {
      if (index == 1 && this.slots.get(index) != ItemStack.EMPTY) {
         return ContainerHelper.removeItem(this.slots, index, this.slots.get(index).getCount());
      }

      ItemStack stack = ContainerHelper.removeItem(this.slots, index, count);
      if (stack != ItemStack.EMPTY && this.doUpdateForSlotChange(index)) {
         this.reset();
      }

      return stack;
   }

   public ItemStack removeItemNoUpdate(int index) {
      return ContainerHelper.takeItem(this.slots, index);
   }

   public void setItem(int index, ItemStack stack) {
      this.slots.set(index, stack);
      if (stack != ItemStack.EMPTY && stack.getCount() > this.getMaxStackSize()) {
         stack.setCount(this.getMaxStackSize());
      }

      if (this.doUpdateForSlotChange(index)) {
         this.reset();
      }
   }

   private boolean doUpdateForSlotChange(int slot) {
      return slot == 0;
   }

   public int getMaxStackSize() {
      return 64;
   }

   public void setChanged() {
      this.reset();
   }

   public boolean stillValid(Player player) {
      return this.umvuthana.getCustomer() == player;
   }

   public void startOpen(Player player) {
   }

   public void stopOpen(Player player) {
   }

   public boolean canPlaceItem(int index, ItemStack stack) {
      return true;
   }

   public void clearContent() {
      this.slots.clear();
   }

   public void reset() {
      this.trade = null;
      ItemStack input = this.slots.get(0);
      if (input == ItemStack.EMPTY) {
         this.setItem(1, ItemStack.EMPTY);
      } else if (this.umvuthana.isOfferingTrade()) {
         Trade trade = this.umvuthana.getOfferingTrade();
         ItemStack tradeInput = trade.getInput();
         if (areItemsEqual(input, tradeInput) && input.getCount() >= tradeInput.getCount()) {
            this.trade = trade;
            this.setItem(1, trade.getOutput());
         } else {
            this.setItem(1, ItemStack.EMPTY);
         }
      }
   }

   public boolean isEmpty() {
      for (ItemStack stack : this.slots) {
         if (!stack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   private static boolean areItemsEqual(ItemStack s1, ItemStack s2) {
      return ItemStack.isSameItemSameComponents(s1, s2);
   }
}
