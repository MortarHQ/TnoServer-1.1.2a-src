package com.tom.storagemod.inventory;

import com.tom.storagemod.block.entity.BlockFilterAttachment;
import com.tom.storagemod.platform.Platform;
import com.tom.storagemod.util.IValidInfo;
import com.tom.storagemod.util.WorldStates;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.EmptyItemHandler;

public interface PlatformInventoryAccess extends IInventoryAccess {
   PlatformInventoryAccess EMPTY = new PlatformInventoryAccess() {
      @Override
      public IInventoryAccess.IInventoryChangeTracker tracker() {
         return InventoryChangeTracker.NULL;
      }

      @Override
      public IItemHandler get() {
         return EmptyItemHandler.INSTANCE;
      }

      @Override
      public ItemStack pullMatchingStack(ItemStack st, long max) {
         return ItemStack.EMPTY;
      }

      @Override
      public ItemStack pushStack(ItemStack stack) {
         return stack;
      }

      @Override
      public int getFreeSlotCount() {
         return 0;
      }

      @Override
      public IInventoryAccess getRootHandler(Set<IProxy> dejaVu) {
         return this;
      }
   };

   IItemHandler get();

   @Override
   default int getFreeSlotCount() {
      IItemHandler itemHandler = this.get();
      if (itemHandler == null) {
         return 0;
      }

      int empty = 0;

      for (int i = 0; i < itemHandler.getSlots(); i++) {
         if (itemHandler.getStackInSlot(i).isEmpty()) {
            empty++;
         }
      }

      return empty;
   }

   @Override
   default int getSlotCount() {
      IItemHandler itemHandler = this.get();
      return itemHandler == null ? 0 : itemHandler.getSlots();
   }

   static BlockFilter getBlockFilterAt(Level level, BlockPos p, boolean make) {
      BlockEntity be = level.getBlockEntity(p);
      return be != null && (make || be.hasData(Platform.BLOCK_FILTER)) ? ((BlockFilterAttachment)be.getData(Platform.BLOCK_FILTER)).getFilter() : null;
   }

   static void removeBlockFilterAt(Level level, BlockPos pos) {
      BlockEntity be = level.getBlockEntity(pos);
      if (be != null && be.hasData(Platform.BLOCK_FILTER)) {
         BlockFilterAttachment f = (BlockFilterAttachment)be.getData(Platform.BLOCK_FILTER);
         f.getFilter().dropContents(level, pos);
         be.removeData(Platform.BLOCK_FILTER);
      }
   }

   class BlockInventoryAccess implements PlatformInventoryAccess {
      private boolean valid;
      private BlockCapabilityCache<IItemHandler, Direction> itemCache;
      private IInventoryAccess.IInventoryChangeTracker.Delegate tracker = new IInventoryAccess.IInventoryChangeTracker.Delegate();

      public void onLoad(Level level, BlockPos worldPosition, Direction side, IValidInfo isValid) {
         this.valid = true;
         this.itemCache = BlockCapabilityCache.create(
            ItemHandler.BLOCK, (ServerLevel)level, worldPosition, side, () -> this.valid && isValid.isObjectValid(), this::onInvalid
         );
      }

      protected void onInvalid() {
      }

      @Override
      public IInventoryAccess.IInventoryChangeTracker tracker() {
         IItemHandler itemHandler = this.get();
         if (itemHandler != null) {
            this.tracker.setDelegate(WorldStates.getTracker(itemHandler));
         } else {
            this.tracker.setDelegate(InventoryChangeTracker.NULL);
         }

         return this.tracker;
      }

      @Override
      public IItemHandler get() {
         return this.itemCache != null && this.valid ? (IItemHandler)this.itemCache.getCapability() : null;
      }

      public static boolean hasInventoryAt(Level level, BlockPos pos, BlockState state, Direction direction) {
         return level.getCapability(ItemHandler.BLOCK, pos, state, null, direction) != null;
      }

      @Override
      public void markInvalid() {
         this.valid = false;
      }

      public boolean exists() {
         return this.valid && this.itemCache.getCapability() != null;
      }

      @Override
      public IInventoryAccess getRootHandler(Set<IProxy> dejaVu) {
         if (this.get() instanceof IProxy p) {
            return dejaVu.add(p) ? p.getRootHandler(dejaVu) : this;
         } else {
            return this;
         }
      }

      @Override
      public String toString() {
         return "BlockInventoryAccess at " + this.itemCache.pos();
      }
   }
}
