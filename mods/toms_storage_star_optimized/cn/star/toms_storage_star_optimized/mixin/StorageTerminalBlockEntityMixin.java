package cn.star.toms_storage_star_optimized.mixin;

import cn.star.toms_storage_star_optimized.api.TerminalDirtyState;
import com.tom.storagemod.block.entity.StorageTerminalBlockEntity;
import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.NetworkInventory;
import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.inventory.TerminalItemStack;
import com.tom.storagemod.inventory.IInventoryAccess.IInventoryChangeTracker;
import com.tom.storagemod.util.BeaconLevelCalc;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StorageTerminalBlockEntity.class)
public abstract class StorageTerminalBlockEntityMixin implements TerminalDirtyState {
   @Shadow
   private NetworkInventory itemCache;
   @Shadow
   private Map<StoredItemStack, TerminalItemStack> items;
   @Shadow
   private boolean updateItems;
   @Shadow
   private int changeCount;
   @Shadow
   private int beaconLevel;
   @Shadow
   private long changeTracker;
   @Shadow
   private int slotCount;
   @Shadow
   private int freeCount;
   @Unique
   private static final int TSSO_REFRESH_INTERVAL = 10;
   @Unique
   private long tsso$lastScanTick = Long.MIN_VALUE;

   @Override
   public boolean tsso$hasPendingSync() {
      StorageTerminalBlockEntity self = (StorageTerminalBlockEntity)this;
      Level level = self.getLevel();
      if (level == null) {
         return this.updateItems;
      }

      long time = level.getGameTime();
      return this.updateItems || this.tsso$lastScanTick == Long.MIN_VALUE || time - this.tsso$lastScanTick >= 10L;
   }

   @Overwrite
   public Map<StoredItemStack, TerminalItemStack> getStacks() {
      return this.items;
   }

   @Overwrite
   public StoredItemStack pullStack(StoredItemStack stack, long max) {
      if (stack == null) {
         return null;
      }

      StorageTerminalBlockEntity self = (StorageTerminalBlockEntity)this;
      IInventoryAccess access = this.itemCache.getAccess(self.getLevel(), self.getBlockPos());
      ItemStack pulled = access.pullMatchingStack(stack.getStack(), max);
      if (pulled.isEmpty()) {
         return null;
      }

      this.updateItems = true;
      return new StoredItemStack(pulled);
   }

   @Overwrite
   public StoredItemStack pushStack(StoredItemStack stack) {
      if (stack == null) {
         return null;
      }

      StorageTerminalBlockEntity self = (StorageTerminalBlockEntity)this;
      IInventoryAccess access = this.itemCache.getAccess(self.getLevel(), self.getBlockPos());
      ItemStack remaining = access.pushStack(stack.getActualStack());
      this.updateItems = true;
      return remaining.isEmpty() ? null : new StoredItemStack(remaining);
   }

   @Overwrite
   public void updateServer() {
      StorageTerminalBlockEntity self = (StorageTerminalBlockEntity)this;
      Level level = self.getLevel();
      if (level != null) {
         long time = level.getGameTime();
         boolean scheduledRefresh = this.tsso$lastScanTick == Long.MIN_VALUE || time - this.tsso$lastScanTick >= 10L;
         if (this.updateItems || scheduledRefresh) {
            IInventoryAccess access = this.itemCache.getAccess(level, self.getBlockPos());
            IInventoryChangeTracker tracker = access.tracker();
            long currentTracker = tracker.getChangeTracker(level);
            if (scheduledRefresh || this.changeTracker != currentTracker) {
               this.changeTracker = currentTracker;
               this.items = new HashMap<>();
               tracker.streamWrappedStacks(false)
                  .<TerminalItemStack>map(TerminalItemStack::new)
                  .forEach(stack -> this.items.merge(stack, stack, TerminalItemStack::merge));
               this.items.replaceAll((key, value) -> new TerminalItemStack(value));
               this.slotCount = Mth.clamp(access.getSlotCount(), 0, 32767);
               this.freeCount = Mth.clamp(access.getFreeSlotCount(), 0, 32767);
               this.changeCount++;
            }

            this.updateItems = false;
            this.tsso$lastScanTick = time;
         }

         if (time % 40L == Math.abs(self.getBlockPos().hashCode()) % 40) {
            this.beaconLevel = BlockPos.betweenClosedStream(new AABB(self.getBlockPos()).inflate(8.0)).mapToInt(pos -> {
               if (level.isLoaded(pos)) {
                  BlockState state = level.getBlockState(pos);
                  if (state.is(Blocks.BEACON)) {
                     return BeaconLevelCalc.calcBeaconLevel(level, pos.getX(), pos.getY(), pos.getZ());
                  }
               }

               return -1;
            }).max().orElse(-1);
         }
      }
   }
}
