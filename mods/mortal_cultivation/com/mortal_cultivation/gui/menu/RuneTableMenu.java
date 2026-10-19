package com.mortal_cultivation.gui.menu;

import com.mortal_cultivation.block.entity.RuneTableBlockEntity;
import com.mortal_cultivation.init.MortalCultivationModMenus;
import com.mortal_cultivation.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public final class RuneTableMenu extends AbstractContainerMenu {
   private static final int TABLE_SLOT = 0;
   private static final int TABLE_SLOT_COUNT = 1;
   private static final int PLAYER_INVENTORY_START = 1;
   private static final int PLAYER_INVENTORY_END = 28;
   private static final int HOTBAR_START = 28;
   private static final int HOTBAR_END = 37;
   private final Level level;
   private final BlockPos tablePos;

   public RuneTableMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
      this(containerId, inventory, readTablePos(extraData));
   }

   public RuneTableMenu(int containerId, Inventory inventory, RuneTableBlockEntity table) {
      this(containerId, inventory, table.getBlockPos(), table.getItemHandler());
   }

   private RuneTableMenu(int containerId, Inventory inventory, BlockPos tablePos) {
      this(containerId, inventory, tablePos, new ItemStackHandler(1));
   }

   private RuneTableMenu(int containerId, Inventory inventory, BlockPos tablePos, IItemHandler handler) {
      super((MenuType)MortalCultivationModMenus.RUNE_TABLE.get(), containerId);
      this.level = inventory.player.level();
      this.tablePos = tablePos;
      this.addSlot(new SlotItemHandler(handler, 0, 80, 60) {
         public boolean mayPlace(ItemStack stack) {
            return RuneTableBlockEntity.isRuneOrb(stack);
         }

         public int getMaxStackSize(ItemStack stack) {
            return 1;
         }
      });

      for (int row = 0; row < 3; row++) {
         for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(inventory, column + (row + 1) * 9, 13 + column * 18, 128 + row * 18));
         }
      }

      for (int column = 0; column < 9; column++) {
         this.addSlot(new Slot(inventory, column, 13 + column * 18, 186));
      }
   }

   public boolean stillValid(Player player) {
      return this.level.getBlockState(this.tablePos).is((Block)BlockRegistry.RUNE_TABLE.get())
         && player.distanceToSqr(this.tablePos.getX() + 0.5, this.tablePos.getY() + 0.5, this.tablePos.getZ() + 0.5) <= 64.0;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      if (index >= 0 && index < this.slots.size()) {
         Slot slot = (Slot)this.slots.get(index);
         if (!slot.hasItem()) {
            return ItemStack.EMPTY;
         }

         ItemStack source = slot.getItem();
         ItemStack moved = source.copy();
         if (index == 0) {
            if (!this.moveItemStackTo(source, 1, 37, true)) {
               return ItemStack.EMPTY;
            }
         } else if (RuneTableBlockEntity.isRuneOrb(source)) {
            if (!this.moveItemStackTo(source, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 1 && index < 28) {
            if (!this.moveItemStackTo(source, 28, 37, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 28 && index < 37 && !this.moveItemStackTo(source, 1, 28, false)) {
            return ItemStack.EMPTY;
         }

         if (source.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (source.getCount() == moved.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(player, source);
         return moved;
      } else {
         return ItemStack.EMPTY;
      }
   }

   private static BlockPos readTablePos(FriendlyByteBuf extraData) {
      return extraData != null && extraData.readableBytes() >= 8 ? extraData.readBlockPos() : BlockPos.ZERO;
   }
}
