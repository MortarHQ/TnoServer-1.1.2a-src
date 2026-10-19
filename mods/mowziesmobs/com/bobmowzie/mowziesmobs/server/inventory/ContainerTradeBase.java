package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class ContainerTradeBase extends AbstractContainerMenu {
   protected final MowzieEntity tradingMob;
   protected final Container inventory;
   protected final Player player;
   private int numCustomSlots;

   public ContainerTradeBase(MenuType<?> menuType, int id, MowzieEntity tradingMob, Container inventory, Inventory playerInv) {
      super(menuType, id);
      this.tradingMob = tradingMob;
      this.inventory = inventory;
      this.player = playerInv.player;
      this.addCustomSlots(playerInv);
      this.numCustomSlots = this.slots.size();

      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
         }
      }

      for (int col = 0; col < 9; col++) {
         this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
      }
   }

   protected void addCustomSlots(Inventory playerInv) {
   }

   public boolean stillValid(Player player) {
      return this.tradingMob != null && this.inventory.stillValid(player) && this.tradingMob.isAlive() && this.tradingMob.distanceTo(player) < 8.0F;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      ItemStack stack = ItemStack.EMPTY;
      int playerHotbarStart = this.numCustomSlots + 27;
      int playerInventoryEnd = this.numCustomSlots + 36;
      Slot slot = (Slot)this.slots.get(index);
      if (slot != null && slot.hasItem()) {
         ItemStack contained = slot.getItem();
         stack = contained.copy();
         if (index == this.numCustomSlots - 1) {
            if (!this.moveItemStackTo(contained, this.numCustomSlots, playerInventoryEnd, true)) {
               return ItemStack.EMPTY;
            }

            slot.onQuickCraft(contained, stack);
         } else if (index != 0) {
            if (index >= this.numCustomSlots && index < playerHotbarStart) {
               if (!this.moveItemStackTo(contained, playerHotbarStart, playerInventoryEnd, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= playerHotbarStart
               && index < playerInventoryEnd
               && !this.moveItemStackTo(contained, this.numCustomSlots, playerHotbarStart, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(contained, this.numCustomSlots, playerInventoryEnd, false)) {
            return ItemStack.EMPTY;
         }

         if (contained.getCount() == 0) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (contained.getCount() == stack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(player, contained);
      }

      return stack;
   }

   public void removed(Player player) {
      super.removed(player);
      this.returnItems();
   }

   public void returnItems() {
      if (!this.player.level().isClientSide) {
         if (!this.player.isAlive() || this.player instanceof ServerPlayer && ((ServerPlayer)this.player).hasDisconnected()) {
            ItemStack itemstack = this.inventory.removeItemNoUpdate(0);
            if (!itemstack.isEmpty()) {
               this.player.drop(itemstack, false);
            }

            itemstack = this.inventory.removeItemNoUpdate(1);
            if (!itemstack.isEmpty()) {
               this.player.drop(itemstack, false);
            }
         } else if (this.player instanceof ServerPlayer) {
            ItemStack stack = this.inventory.removeItemNoUpdate(0);
            if (stack != ItemStack.EMPTY) {
               this.player.getInventory().placeItemBackInInventory(stack);
            }
         }
      }
   }

   public MowzieEntity getTradingMob() {
      return this.tradingMob;
   }

   public Container getInventory() {
      return this.inventory;
   }
}
