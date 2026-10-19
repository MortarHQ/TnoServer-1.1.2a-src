package com.tom.storagemod.inventory;

import com.tom.storagemod.block.entity.IInventoryConnector;
import com.tom.storagemod.util.IValidInfo;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public class NetworkInventory {
   private PlatformInventoryAccess.BlockInventoryAccess block = new PlatformInventoryAccess.BlockInventoryAccess();
   private WeakReference<IInventoryConnector> networkAccess;
   private long lastUpdate;

   public void onLoad(Level level, BlockPos worldPosition, Direction side, IValidInfo v) {
      this.block.onLoad(level, worldPosition, side, v);
   }

   public void scanNetwork(Level level, BlockPos worldPosition) {
      this.networkAccess = null;

      for (BlockPos p : InventoryCableNetwork.getNetwork(level).getNetworkNodes(worldPosition)) {
         if (level.isLoaded(p) && level.getBlockEntity(p) instanceof IInventoryConnector te && te.hasConnectedInventories()) {
            this.networkAccess = new WeakReference<>(te);
            break;
         }
      }
   }

   public IInventoryAccess getAccess(Level level, BlockPos worldPosition) {
      if (this.block.exists()) {
         if (this.block.get() instanceof PlatformItemHandler h) {
            Set<IProxy> dejaVu = Collections.newSetFromMap(new IdentityHashMap<>());
            return h.getRootHandler(dejaVu);
         } else {
            return this.block;
         }
      } else {
         if (level.getGameTime() - this.lastUpdate > 10L) {
            this.scanNetwork(level, worldPosition);
            this.lastUpdate = level.getGameTime();
         }

         if (this.networkAccess != null) {
            IInventoryConnector net = this.networkAccess.get();
            if (net != null && net.hasConnectedInventories()) {
               return net.getMergedHandler();
            }
         }

         return this.block;
      }
   }

   public boolean isNetwork() {
      return !this.block.exists() && this.networkAccess != null;
   }

   public boolean isValid() {
      return this.block.exists() || this.networkAccess != null;
   }
}
