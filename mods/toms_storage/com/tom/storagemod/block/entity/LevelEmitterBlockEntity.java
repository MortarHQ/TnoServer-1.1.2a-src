package com.tom.storagemod.block.entity;

import com.tom.storagemod.Content;
import com.tom.storagemod.block.LevelEmitterBlock;
import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.NetworkInventory;
import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.menu.LevelEmitterMenu;
import com.tom.storagemod.platform.Platform;
import com.tom.storagemod.platform.PlatformBlockEntity;
import com.tom.storagemod.util.TickerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class LevelEmitterBlockEntity extends PlatformBlockEntity implements TickerUtil.TickableServer, MenuProvider {
   private StoredItemStack filter;
   private int count = 1;
   private NetworkInventory topCache = new NetworkInventory();
   private boolean lessThan;
   private long changeTracker;

   public LevelEmitterBlockEntity(BlockPos pos, BlockState state) {
      super(Content.levelEmitterBE.get(), pos, state);
   }

   @Override
   public void updateServer() {
      if (this.level.getGameTime() % 10L == Math.abs(this.worldPosition.hashCode()) % 10) {
         BlockState state = this.level.getBlockState(this.worldPosition);
         boolean p = (Boolean)state.getValue(LevelEmitterBlock.POWERED);
         boolean currState = false;
         if (this.filter != null) {
            IInventoryAccess.IInventoryChangeTracker tr = this.topCache.getAccess(this.level, this.worldPosition).tracker();
            long ct = tr.getChangeTracker(this.level);
            if (ct == this.changeTracker) {
               return;
            }

            this.changeTracker = ct;
            long counter = tr.countItems(this.getFilter());
            if (this.lessThan) {
               currState = counter < this.count;
            } else {
               currState = counter > this.count;
            }
         } else {
            currState = false;
         }

         if (currState != p) {
            this.level.setBlock(this.worldPosition, (BlockState)state.setValue(LevelEmitterBlock.POWERED, currState), 3);
            Direction direction = (Direction)state.getValue(LevelEmitterBlock.FACING);
            BlockPos blockpos = this.worldPosition.relative(direction);
            if (Platform.notifyBlocks(this.level, this.worldPosition, direction)) {
               this.level.neighborChanged(blockpos, state.getBlock(), this.worldPosition);
               this.level.updateNeighborsAtExceptFromFacing(blockpos, state.getBlock(), direction.getOpposite());
            }
         }
      }
   }

   public void saveAdditional(CompoundTag compound, Provider provider) {
      super.saveAdditional(compound, provider);
      StoredItemStack f = this.getFilter();
      if (f != null) {
         ItemStack is = f.getStack();
         if (!is.isEmpty()) {
            compound.put("Filter", is.save(provider, new CompoundTag()));
         }
      }

      compound.putInt("Count", this.count);
      compound.putBoolean("lessThan", this.lessThan);
   }

   public void loadAdditional(CompoundTag nbtIn, Provider provider) {
      super.loadAdditional(nbtIn, provider);
      this.setFilter(ItemStack.parseOptional(provider, nbtIn.getCompound("Filter")));
      this.count = nbtIn.getInt("Count");
      this.lessThan = nbtIn.getBoolean("lessThan");
   }

   public void setFilter(ItemStack filter) {
      this.filter = new StoredItemStack(filter);
      this.changeTracker = 0L;
   }

   public StoredItemStack getFilter() {
      return this.filter;
   }

   public void setCount(int count) {
      this.count = count;
      this.changeTracker = 0L;
   }

   public int getCount() {
      return this.count;
   }

   public void setLessThan(boolean lessThan) {
      this.lessThan = lessThan;
      this.changeTracker = 0L;
   }

   public boolean isLessThan() {
      return this.lessThan;
   }

   public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
      return new LevelEmitterMenu(p_createMenu_1_, p_createMenu_2_, this);
   }

   public Component getDisplayName() {
      return Component.translatable("menu.toms_storage.level_emitter");
   }

   public boolean stillValid(Player p_59619_) {
      return this.level.getBlockEntity(this.worldPosition) != this
         ? false
         : !(p_59619_.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) > 64.0);
   }

   public void onLoad() {
      super.onLoad();
      if (!this.level.isClientSide) {
         BlockState state = this.level.getBlockState(this.worldPosition);
         Direction facing = (Direction)state.getValue(BlockStateProperties.FACING);
         this.topCache.onLoad(this.level, this.worldPosition.relative(facing.getOpposite()), facing, this);
      }
   }
}
