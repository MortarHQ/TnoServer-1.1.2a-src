package com.tom.storagemod.menu;

import com.mojang.datafixers.util.Pair;
import com.tom.storagemod.Content;
import com.tom.storagemod.util.LimitedContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilingCabinetMenu extends AbstractContainerMenu {
   public static final ResourceLocation LOCKED_SLOT = ResourceLocation.tryBuild("toms_storage", "item/locked_slot");
   private final Container containerParent;
   private final LimitedContainer container;
   private final int containerRows;

   public FilingCabinetMenu(int wid, Inventory pinv) {
      this(wid, pinv, new SimpleContainer(512));
   }

   public FilingCabinetMenu(int wid, Inventory pinv, Container inv) {
      super(Content.filingCabinetMenu.get(), wid);
      this.containerParent = inv;
      this.container = new LimitedContainer(inv, 45);
      this.containerRows = 5;
      int i = (this.containerRows - 4) * 18;

      for (int j = 0; j < this.containerRows; j++) {
         for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(this.container, k + j * 9, 8 + k * 18, 18 + j * 18) {
               public int getMaxStackSize() {
                  return 1;
               }

               public int getMaxStackSize(ItemStack p_40238_) {
                  return 1;
               }

               public boolean mayPlace(ItemStack stack) {
                  if (!this.isValid()) {
                     return false;
                  }

                  if (stack.getMaxStackSize() != 1) {
                     return false;
                  }

                  for (int ix = 0; ix < this.container.getContainerSize(); ix++) {
                     ItemStack is = this.container.getItem(ix);
                     if (!is.isEmpty()) {
                        return is.getItem() == stack.getItem();
                     }
                  }

                  return true;
               }

               public boolean mayPickup(Player p_40228_) {
                  return this.isValid();
               }

               public ItemStack getItem() {
                  return !this.isValid() ? ItemStack.EMPTY : super.getItem();
               }

               public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                  return !this.isValid() ? Pair.of(InventoryMenu.BLOCK_ATLAS, FilingCabinetMenu.LOCKED_SLOT) : null;
               }

               private boolean isValid() {
                  return this.container.getContainerSize() > this.getContainerSlot();
               }
            });
         }
      }

      for (int l = 0; l < 3; l++) {
         for (int j1 = 0; j1 < 9; j1++) {
            this.addSlot(new Slot(pinv, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
         }
      }

      for (int i1 = 0; i1 < 9; i1++) {
         this.addSlot(new Slot(pinv, i1, 8 + i1 * 18, 161 + i));
      }
   }

   public ItemStack quickMoveStack(Player p_39253_, int p_39254_) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(p_39254_);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (p_39254_ < this.containerRows * 9) {
            if (!this.moveItemStackTo(itemstack1, this.containerRows * 9, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * 9, false)) {
            if (!this.containerParent.canPlaceItem(0, itemstack1)) {
               return ItemStack.EMPTY;
            }

            boolean inserted = false;

            for (int i = 0; i < this.containerParent.getContainerSize(); i++) {
               if (this.containerParent.getItem(i).isEmpty()) {
                  this.containerParent.setItem(i, itemstack);
                  itemstack1.setCount(0);
                  this.containerParent.setChanged();
                  inserted = true;
                  break;
               }
            }

            if (!inserted) {
               return ItemStack.EMPTY;
            }
         }

         if (itemstack1.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }
      }

      return itemstack;
   }

   public boolean stillValid(Player p_38874_) {
      return this.container.stillValid(p_38874_);
   }

   public int getRowCount() {
      return this.containerRows;
   }

   public boolean clickMenuButton(Player p_38875_, int row) {
      this.setRow(row);
      return true;
   }

   public void setRow(int row) {
      this.container.setStartOffset(row * 9);
   }

   public int getContainerSize() {
      return this.containerParent.getContainerSize();
   }
}
