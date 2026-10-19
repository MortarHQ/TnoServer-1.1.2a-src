package com.tom.storagemod.block.entity;

import com.tom.storagemod.block.AbstractInventoryHopperBlock;
import com.tom.storagemod.inventory.NetworkInventory;
import com.tom.storagemod.platform.PlatformBlockEntity;
import com.tom.storagemod.util.IValidInfo;
import com.tom.storagemod.util.TickerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractInventoryHopperBlockEntity extends PlatformBlockEntity implements TickerUtil.TickableServer, IValidInfo {
   protected NetworkInventory topCache = new NetworkInventory();
   protected NetworkInventory bottomCache = new NetworkInventory();

   public AbstractInventoryHopperBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public void onLoad() {
      super.onLoad();
      if (!this.level.isClientSide) {
         BlockState state = this.level.getBlockState(this.worldPosition);
         Direction facing = (Direction)state.getValue(AbstractInventoryHopperBlock.FACING);
         this.bottomCache.onLoad(this.level, this.worldPosition.relative(facing), facing.getOpposite(), this);
         this.topCache.onLoad(this.level, this.worldPosition.relative(facing.getOpposite()), facing, this);
      }
   }

   public boolean isEnabled() {
      return (Boolean)this.getBlockState().getValue(AbstractInventoryHopperBlock.ENABLED);
   }
}
