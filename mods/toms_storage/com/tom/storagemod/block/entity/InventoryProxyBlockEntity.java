package com.tom.storagemod.block.entity;

import com.tom.storagemod.Content;
import com.tom.storagemod.block.AbstractInventoryHopperBlock;
import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.PlatformInventoryAccess;
import com.tom.storagemod.inventory.PlatformProxyInventoryAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class InventoryProxyBlockEntity extends PaintedBlockEntity implements IInventoryAccess.IInventory {
   private PlatformInventoryAccess.BlockInventoryAccess block = new PlatformInventoryAccess.BlockInventoryAccess() {
      @Override
      protected void onInvalid() {
         InventoryProxyBlockEntity.this.markCapsInvalid();
      }
   };
   private PlatformProxyInventoryAccess proxy = new PlatformProxyInventoryAccess(this.block);

   public InventoryProxyBlockEntity(BlockPos pos, BlockState state) {
      super(Content.invProxyBE.get(), pos, state);
   }

   public void onLoad() {
      super.onLoad();
      if (!this.level.isClientSide) {
         BlockState state = this.level.getBlockState(this.worldPosition);
         Direction facing = (Direction)state.getValue(AbstractInventoryHopperBlock.FACING);
         this.block.onLoad(this.level, this.worldPosition.relative(facing), facing.getOpposite(), this);
      }
   }

   @Override
   public IInventoryAccess getInventoryAccess() {
      return this.proxy;
   }
}
