package com.tom.storagemod.block.entity;

import com.tom.storagemod.Content;
import com.tom.storagemod.util.TickerUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class OpenCrateBlockEntity extends BlockEntity implements TickerUtil.TickableServer, Container {
   private List<ItemEntity> items = new ArrayList<>();

   public OpenCrateBlockEntity(BlockPos pos, BlockState state) {
      super(Content.openCrateBE.get(), pos, state);
   }

   @Override
   public void updateServer() {
      if (this.level.getGameTime() % 5L == Math.abs(this.worldPosition.hashCode()) % 5) {
         BlockState state = this.level.getBlockState(this.worldPosition);
         Direction f = (Direction)state.getValue(BlockStateProperties.FACING);
         BlockPos p = this.worldPosition.relative(f);
         this.items = this.level.getEntitiesOfClass(ItemEntity.class, new AABB(p));
      }
   }

   public int getContainerSize() {
      return this.items.size() + 1;
   }

   public boolean isEmpty() {
      return this.items.isEmpty();
   }

   public ItemStack getItem(int index) {
      if (this.items.size() > index) {
         ItemEntity ent = this.items.get(index);
         return ent.isAlive() ? ent.getItem() : ItemStack.EMPTY;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public ItemStack removeItem(int index, int count) {
      if (this.items.size() > index) {
         ItemEntity ent = this.items.get(index);
         if (ent.isAlive()) {
            ItemStack s = ent.getItem().split(count);
            if (ent.getItem().isEmpty()) {
               ent.remove(RemovalReason.KILLED);
            }

            return s;
         } else {
            return ItemStack.EMPTY;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   public ItemStack removeItemNoUpdate(int index) {
      if (this.items.size() > index) {
         ItemEntity ent = this.items.get(index);
         if (ent.isAlive()) {
            ItemStack s = ent.getItem();
            ent.remove(RemovalReason.KILLED);
            return s;
         } else {
            return ItemStack.EMPTY;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   public void setItem(int index, ItemStack stack) {
      BlockState state = this.level.getBlockState(this.worldPosition);
      Direction f = Direction.UP;
      if (state.getBlock() == Content.openCrate.get()) {
         f = (Direction)state.getValue(BlockStateProperties.FACING);
      }

      BlockPos p = this.worldPosition.relative(f);
      ItemEntity entityitem = new ItemEntity(this.level, p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5, stack);
      entityitem.setDefaultPickUpDelay();
      entityitem.setDeltaMovement(Vec3.ZERO);
      this.level.addFreshEntity(entityitem);
      this.items.add(entityitem);
   }

   public int getMaxStackSize() {
      return 64;
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public void startOpen(Player player) {
   }

   public void stopOpen(Player player) {
   }

   public boolean canPlaceItem(int index, ItemStack stack) {
      return index == this.items.size();
   }

   public void clearContent() {
   }
}
