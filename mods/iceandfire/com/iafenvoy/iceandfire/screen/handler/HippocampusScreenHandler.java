package com.iafenvoy.iceandfire.screen.handler;

import com.iafenvoy.iceandfire.entity.HippocampusEntity;
import com.iafenvoy.iceandfire.registry.IafScreenHandlers;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class HippocampusScreenHandler extends AbstractContainerMenu {
   private final Container hippocampusInventory;
   private final HippocampusEntity hippocampus;

   public HippocampusScreenHandler(int i, Inventory playerInventory, FriendlyByteBuf buf) {
      this(i, new SimpleContainer(18), playerInventory, (HippocampusEntity)Minecraft.getInstance().level.getEntity(buf.readInt()));
   }

   public HippocampusScreenHandler(int id, Container hippoInventory, Inventory playerInventory, HippocampusEntity hippocampus) {
      super((MenuType)IafScreenHandlers.HIPPOCAMPUS_SCREEN.get(), id);
      this.hippocampusInventory = hippoInventory;
      this.hippocampus = hippocampus;
      Player player = playerInventory.player;
      this.hippocampusInventory.startOpen(player);
      this.addSlot(new Slot(this.hippocampusInventory, 0, 8, 18) {
         public boolean mayPlace(ItemStack stack) {
            return stack.getItem() == Items.SADDLE && !this.hasItem();
         }

         public boolean isActive() {
            return true;
         }
      });
      this.addSlot(new Slot(this.hippocampusInventory, 1, 8, 36) {
         public boolean mayPlace(ItemStack stack) {
            return stack.getItem() == Blocks.CHEST.asItem() && !this.hasItem();
         }

         public boolean isActive() {
            return true;
         }
      });
      this.addSlot(new Slot(this.hippocampusInventory, 2, 8, 52) {
         public boolean mayPlace(ItemStack stack) {
            return HippocampusEntity.getIntFromArmor(stack) != 0;
         }

         public int getMaxStackSize() {
            return 1;
         }

         public boolean isActive() {
            return true;
         }
      });
      if (this.hippocampus != null && this.hippocampus.isChested()) {
         for (int k = 0; k < 3; k++) {
            for (int l = 0; l < this.hippocampus.getInventoryColumns(); l++) {
               this.addSlot(new Slot(hippoInventory, 3 + l + k * this.hippocampus.getInventoryColumns(), 80 + l * 18, 18 + k * 18));
            }
         }
      }

      for (int i1 = 0; i1 < 3; i1++) {
         for (int k1 = 0; k1 < 9; k1++) {
            this.addSlot(new Slot(player.getInventory(), k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 - 18));
         }
      }

      for (int j1 = 0; j1 < 9; j1++) {
         this.addSlot(new Slot(player.getInventory(), j1, 8 + j1 * 18, 142));
      }
   }

   public ItemStack quickMoveStack(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(index);
      if (slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         int containerSize = this.hippocampusInventory.getContainerSize();
         if (index < containerSize) {
            if (!this.moveItemStackTo(itemstack1, containerSize, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(1).mayPlace(itemstack1) && !this.getSlot(1).hasItem()) {
            if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(2).mayPlace(itemstack1) && !this.getSlot(2).hasItem()) {
            if (!this.moveItemStackTo(itemstack1, 2, 3, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(0).mayPlace(itemstack1)) {
            if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (containerSize <= 3 || !this.moveItemStackTo(itemstack1, 3, containerSize, false)) {
            int j = containerSize + 27;
            int k = j + 9;
            if (index >= j && index < k) {
               if (!this.moveItemStackTo(itemstack1, containerSize, j, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index < j) {
               if (!this.moveItemStackTo(itemstack1, j, k, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
               return ItemStack.EMPTY;
            }

            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }
      }

      return itemstack;
   }

   public boolean stillValid(Player playerIn) {
      return !this.hippocampus.hasInventoryChanged(this.hippocampusInventory)
         && this.hippocampusInventory.stillValid(playerIn)
         && this.hippocampus.isAlive()
         && this.hippocampus.distanceTo(playerIn) < 8.0F;
   }

   public void removed(Player playerIn) {
      super.removed(playerIn);
      this.hippocampusInventory.stopOpen(playerIn);
   }

   public HippocampusEntity getHippocampus() {
      return this.hippocampus;
   }
}
