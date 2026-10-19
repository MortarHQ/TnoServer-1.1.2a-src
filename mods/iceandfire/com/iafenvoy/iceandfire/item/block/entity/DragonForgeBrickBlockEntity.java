package com.iafenvoy.iceandfire.item.block.entity;

import com.iafenvoy.iceandfire.registry.IafBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DragonForgeBrickBlockEntity extends BlockEntity implements WorldlyContainer {
   @Nullable
   private DragonForgeBlockEntity core = null;

   public DragonForgeBrickBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafBlockEntities.DRAGONFORGE_BRICK.get(), pos, state);
   }

   public static void tick(Level level, BlockPos position, BlockState state, DragonForgeBrickBlockEntity forgeInput) {
      forgeInput.core = forgeInput.getConnectedTileEntity(position);
   }

   private DragonForgeBlockEntity getConnectedTileEntity(BlockPos position) {
      assert this.level != null;

      for (Direction facing : Direction.values()) {
         if (this.level.getBlockEntity(position.relative(facing)) instanceof DragonForgeBlockEntity forge) {
            return forge;
         }
      }

      return null;
   }

   public int[] getSlotsForFace(Direction side) {
      return this.core == null ? new int[0] : this.core.getSlotsForFace(side);
   }

   public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
      return this.core != null && this.core.canPlaceItemThroughFace(slot, stack, dir);
   }

   public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
      return this.core != null && this.core.canTakeItemThroughFace(slot, stack, dir);
   }

   public int getContainerSize() {
      return this.core == null ? 0 : this.core.getContainerSize();
   }

   public boolean isEmpty() {
      return this.core == null || this.core.isEmpty();
   }

   public ItemStack getItem(int slot) {
      return this.core == null ? ItemStack.EMPTY : this.core.getItem(slot);
   }

   public ItemStack removeItem(int slot, int amount) {
      return this.core == null ? ItemStack.EMPTY : this.core.removeItem(slot, amount);
   }

   public ItemStack removeItemNoUpdate(int slot) {
      return this.core == null ? ItemStack.EMPTY : this.core.removeItemNoUpdate(slot);
   }

   public void setItem(int slot, ItemStack stack) {
      if (this.core != null) {
         this.core.setItem(slot, stack);
      }
   }

   public boolean stillValid(Player player) {
      return this.core != null && this.core.stillValid(player);
   }

   public void clearContent() {
      if (this.core != null) {
         this.core.clearContent();
      }
   }
}
