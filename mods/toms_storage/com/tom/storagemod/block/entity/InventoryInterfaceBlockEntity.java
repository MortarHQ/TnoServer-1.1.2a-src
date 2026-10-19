package com.tom.storagemod.block.entity;

import com.tom.storagemod.Content;
import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.IInventoryConnectorReference;
import com.tom.storagemod.inventory.InventoryCableNetwork;
import com.tom.storagemod.inventory.PlatformInventoryAccess;
import com.tom.storagemod.platform.PlatformBlockEntity;
import com.tom.storagemod.util.TickerUtil;
import java.lang.ref.WeakReference;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class InventoryInterfaceBlockEntity
   extends PlatformBlockEntity
   implements TickerUtil.TickableServer,
   IInventoryAccess.IInventory,
   IInventoryConnectorReference {
   private WeakReference<IInventoryConnector> networkAccess;
   private WeakReference<IInventoryConnector> connectorAccess;
   private boolean hasConnector;

   public InventoryInterfaceBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
      super(Content.invInterfaceBE.get(), p_155229_, p_155230_);
   }

   @Override
   public void updateServer() {
      long time = this.level.getGameTime();
      if (time % 20L == Math.abs(this.worldPosition.hashCode()) % 20) {
         if (this.hasConnector) {
            this.hasConnector = false;
         } else {
            this.connectorAccess = null;
         }

         if (this.connectorAccess != null) {
            this.networkAccess = this.connectorAccess;
         } else {
            this.networkAccess = null;

            for (BlockPos p : InventoryCableNetwork.getNetwork(this.level).getNetworkNodes(this.worldPosition)) {
               if (this.level.isLoaded(p) && this.level.getBlockEntity(p) instanceof IInventoryConnector te && te.hasConnectedInventories()) {
                  this.networkAccess = new WeakReference<>(te);
                  break;
               }
            }
         }
      }
   }

   @Override
   public IInventoryAccess getInventoryAccess() {
      if (this.networkAccess != null) {
         IInventoryConnector net = this.networkAccess.get();
         if (net != null && net.hasConnectedInventories()) {
            return net.getMergedHandler();
         }
      }

      return PlatformInventoryAccess.EMPTY;
   }

   @Override
   public IInventoryConnector getConnectorRef() {
      if (this.networkAccess != null) {
         IInventoryConnector net = this.networkAccess.get();
         if (net != null && net.hasConnectedInventories()) {
            return net;
         }
      }

      return null;
   }

   public void setConnectorAccess(IInventoryConnector connectorAccess) {
      this.connectorAccess = new WeakReference<>(connectorAccess);
      this.hasConnector = true;
   }
}
