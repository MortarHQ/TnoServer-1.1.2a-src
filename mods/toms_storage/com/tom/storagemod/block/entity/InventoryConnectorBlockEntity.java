package com.tom.storagemod.block.entity;

import com.tom.storagemod.Config;
import com.tom.storagemod.Content;
import com.tom.storagemod.StorageTags;
import com.tom.storagemod.api.MultiblockInventoryAPI;
import com.tom.storagemod.block.IInventoryNode;
import com.tom.storagemod.inventory.BlockFilter;
import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.IInventoryConnectorReference;
import com.tom.storagemod.inventory.InventoryCableNetwork;
import com.tom.storagemod.inventory.MultiInventoryAccess;
import com.tom.storagemod.inventory.PlatformInventoryAccess;
import com.tom.storagemod.inventory.PlatformMultiInventoryAccess;
import com.tom.storagemod.platform.PlatformBlockEntity;
import com.tom.storagemod.util.BlockFace;
import com.tom.storagemod.util.InventoryConnectorConfigUtil;
import com.tom.storagemod.util.TickerUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class InventoryConnectorBlockEntity
   extends PlatformBlockEntity
   implements TickerUtil.TickableServer,
   IInventoryConnector,
   IInventoryAccess.IInventory,
   IInventoryConnectorReference {
   private MultiInventoryAccess handler = new PlatformMultiInventoryAccess();
   private Map<BlockFace, PlatformInventoryAccess.BlockInventoryAccess> invAccesses = new HashMap<>();
   private Set<IInventoryAccess> connectedInvs = new HashSet<>();
   private Set<IInventoryConnector> linkedConnectors = new HashSet<>();
   private Set<BlockFace> interfaces = new HashSet<>();

   public InventoryConnectorBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
      super(Content.connectorBE.get(), p_155229_, p_155230_);
   }

   @Override
   public void updateServer() {
      long time = this.level.getGameTime();
      if (time % 20L == Math.abs(this.worldPosition.hashCode()) % 20) {
         this.detectTouchingInventories();
         this.detectCableNetwork();
         this.handler.build(this, this.linkedConnectors);
      }
   }

   private void detectCableNetwork() {
      this.linkedConnectors.clear();

      for (BlockPos p : InventoryCableNetwork.getNetwork(this.level).getNetworkNodes(this.worldPosition)) {
         if (this.level.isLoaded(p)) {
            BlockEntity be = this.level.getBlockEntity(p);
            if (be != this && be instanceof IInventoryConnector te) {
               this.linkedConnectors.add(te);
            }
         }
      }
   }

   private void detectTouchingInventories() {
      BlockFilter connFilter = BlockFilter.getFilterAt(this.level, this.worldPosition);
      UnaryOperator<IInventoryAccess> wrapper = connFilter != null ? i -> connFilter.wrap(this.level, i) : UnaryOperator.identity();
      this.connectedInvs.clear();
      Map<BlockPos, Direction> connected = new HashMap<>();
      Set<BlockFilter> blockFilters = new HashSet<>();
      Set<BlockFace> interfaces = new HashSet<>();
      Stack<BlockPos> toCheck = new Stack<>();
      Set<BlockPos> checkedBlocks = new HashSet<>();
      toCheck.add(this.worldPosition);
      checkedBlocks.add(this.worldPosition);
      int maxRange = Config.get().invConnectorScanRange;
      maxRange *= maxRange;
      boolean onlyTrims = Config.get().onlyTrims;
      Consumer<BlockPos> mbCheck = opos -> {
         BlockFilter fx = BlockFilter.getFilterAt(this.level, opos);
         if (fx != null) {
            blockFilters.add(fx);
         }

         if (!onlyTrims) {
            toCheck.add(opos);
         }

         checkedBlocks.add(opos);
      };

      while (!toCheck.isEmpty()) {
         BlockPos cp = toCheck.pop();

         for (Direction d : Direction.values()) {
            BlockPos p = cp.relative(d);
            if (!checkedBlocks.contains(p) && p.distSqr(this.worldPosition) < maxRange) {
               checkedBlocks.add(p);
               BlockState state = this.level.getBlockState(p);
               if (state.is(StorageTags.TRIMS)) {
                  toCheck.add(p);
               } else if (state.getBlock() instanceof IInventoryNode) {
                  interfaces.add(new BlockFace(p, Direction.DOWN));
                  toCheck.add(p);
               } else if (InventoryConnectorConfigUtil.canConnect(state)
                  && PlatformInventoryAccess.BlockInventoryAccess.hasInventoryAt(this.level, p, state, d.getOpposite())) {
                  BlockFilter f = BlockFilter.getFilterAt(this.level, p);
                  if (f != null) {
                     blockFilters.add(f);
                  }

                  connected.put(p, d.getOpposite());
                  if (!onlyTrims) {
                     toCheck.add(p);
                  }

                  MultiblockInventoryAPI.EVENT.invoker().detectMultiblocks(this.level, p, state, mbCheck);
               }
            }
         }
      }

      blockFilters.forEach(fx -> fx.getConnectedBlocks().forEach(connected::remove));
      Map<BlockFace, PlatformInventoryAccess.BlockInventoryAccess> invA = new HashMap<>();
      connected.forEach((px, dx) -> {
         BlockFace s = new BlockFace(px, dx);
         PlatformInventoryAccess.BlockInventoryAccess acc = this.invAccesses.remove(s);
         if (acc == null) {
            acc = new PlatformInventoryAccess.BlockInventoryAccess();
            acc.onLoad(this.level, px, dx, this);
         }

         invA.put(s, acc);
         this.connectedInvs.add(wrapper.apply(acc));
      });
      blockFilters.forEach(fx -> {
         if (!fx.skip()) {
            BlockFace s = new BlockFace(fx.getMainPos(), fx.getSide());
            PlatformInventoryAccess.BlockInventoryAccess acc = this.invAccesses.remove(s);
            if (acc == null) {
               acc = new PlatformInventoryAccess.BlockInventoryAccess();
               acc.onLoad(this.level, fx.getMainPos(), fx.getSide(), this);
            }

            invA.put(s, acc);
            this.connectedInvs.add(wrapper.apply(fx.wrap(this.level, acc)));
         }
      });
      this.invAccesses.values().forEach(IInventoryAccess::markInvalid);
      this.invAccesses.clear();
      this.invAccesses = invA;
      if (!this.interfaces.equals(interfaces)) {
         InventoryCableNetwork net = InventoryCableNetwork.getNetwork(this.level);
         this.interfaces.forEach(net::markNodeInvalid);
         this.interfaces = interfaces;
         net.markNodeInvalid(this.worldPosition);
      }

      for (BlockFace blockFace : this.interfaces) {
         if (this.level.getBlockEntity(blockFace.pos()) instanceof InventoryInterfaceBlockEntity ii) {
            ii.setConnectorAccess(this);
         }
      }
   }

   public void setRemoved() {
      super.setRemoved();
      this.invAccesses.clear();
      this.handler.clear();
   }

   public InventoryConnectorBlockEntity.UsageInfo getUsage() {
      return new InventoryConnectorBlockEntity.UsageInfo(this.handler.getInventoryCount(), this.handler.getSlotCount(), this.handler.getFreeSlotCount());
   }

   @Override
   public IInventoryAccess getMergedHandler() {
      return this.handler;
   }

   public Set<IInventoryAccess> getConnectedInventories() {
      return this.connectedInvs;
   }

   public Set<BlockFace> getInterfaces() {
      return this.interfaces;
   }

   @Override
   public boolean hasConnectedInventories() {
      return !this.isRemoved();
   }

   @Override
   public IInventoryAccess getInventoryAccess() {
      return this.handler;
   }

   public List<BlockPos> getConnectedBlocks() {
      return this.invAccesses.keySet().stream().map(b -> b.pos()).toList();
   }

   @Override
   public Collection<IInventoryConnector> getConnectedConnectors() {
      return this.linkedConnectors;
   }

   @Override
   public IInventoryConnector getConnectorRef() {
      return this;
   }

   public record UsageInfo(int blocks, int all, int free) {
   }
}
