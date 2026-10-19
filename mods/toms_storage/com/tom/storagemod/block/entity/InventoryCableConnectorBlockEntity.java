package com.tom.storagemod.block.entity;

import com.tom.storagemod.Config;
import com.tom.storagemod.Content;
import com.tom.storagemod.block.InventoryCableConnectorBlock;
import com.tom.storagemod.inventory.BlockFilter;
import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.IInventoryConnectorReference;
import com.tom.storagemod.inventory.IInventoryLink;
import com.tom.storagemod.inventory.InventoryCableNetwork;
import com.tom.storagemod.inventory.MultiInventoryAccess;
import com.tom.storagemod.inventory.PlatformInventoryAccess;
import com.tom.storagemod.inventory.PlatformMultiInventoryAccess;
import com.tom.storagemod.inventory.RemoteConnections;
import com.tom.storagemod.menu.InventoryLinkMenu;
import com.tom.storagemod.util.BeaconLevelCalc;
import com.tom.storagemod.util.TickerUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class InventoryCableConnectorBlockEntity
   extends PaintedBlockEntity
   implements MenuProvider,
   TickerUtil.TickableServer,
   IInventoryConnector,
   IInventoryLink {
   private static final String CHANNEL_TAG = "channel";
   private PlatformInventoryAccess.BlockInventoryAccess block = new PlatformInventoryAccess.BlockInventoryAccess();
   private MultiInventoryAccess mergedHandler = new PlatformMultiInventoryAccess();
   private Set<IInventoryConnector> linkedConnectors = new HashSet<>();
   private Collection<IInventoryAccess> filteredMerge = Collections.emptyList();
   private IInventoryAccess self = this.block;
   private UUID channel = null;
   private int beaconLevel = -1;

   public InventoryCableConnectorBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
      super(Content.cableConnectorBE.get(), p_155229_, p_155230_);
   }

   public void onLoad() {
      super.onLoad();
      if (!this.level.isClientSide) {
         BlockState state = this.level.getBlockState(this.worldPosition);
         Direction facing = (Direction)state.getValue(InventoryCableConnectorBlock.FACING);
         this.block.onLoad(this.level, this.worldPosition.relative(facing), facing.getOpposite(), this);
      }
   }

   public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
      return new InventoryLinkMenu(p_39954_, p_39955_, this);
   }

   @Override
   public void updateServer() {
      long time = this.level.getGameTime();
      if (time % 20L == Math.abs(this.worldPosition.hashCode()) % 20) {
         BlockState state = this.level.getBlockState(this.worldPosition);
         Direction facing = (Direction)state.getValue(InventoryCableConnectorBlock.FACING);
         BlockPos pos = this.worldPosition.relative(facing);
         BlockState st = this.level.getBlockState(pos);
         this.mergedHandler.clear();
         this.linkedConnectors.clear();
         this.beaconLevel = -1;
         this.detectCableNetwork();
         if (st.is(Blocks.BEACON)) {
            this.beaconLevel = BeaconLevelCalc.calcBeaconLevel(this.level, pos.getX(), pos.getY(), pos.getZ());
            this.self = this.mergedHandler;
            if (this.channel != null) {
               RemoteConnections.Channel chn = RemoteConnections.get(this.level).getChannel(this.channel);
               if (chn != null) {
                  chn.register((ServerLevel)this.level, this.worldPosition);
                  Set<IInventoryLink> links = chn.findOthers((ServerLevel)this.level, this.worldPosition, this.beaconLevel);
                  links.forEach(l -> this.linkedConnectors.add(l.getConnector()));
               }
            }

            this.mergedHandler.build(this, this.linkedConnectors);
         } else if (this.level.getBlockEntity(pos) instanceof IInventoryConnectorReference ref) {
            IInventoryConnector inv = ref.getConnectorRef();
            if (inv != null) {
               this.self = this.mergedHandler;
               this.mergedHandler.build(inv, Collections.emptyList());
               BlockFilter f = BlockFilter.getFilterAt(this.level, this.worldPosition);
               if (f != null) {
                  List<IInventoryAccess> invs = new ArrayList<>();

                  for (IInventoryAccess a : this.mergedHandler.getConnected()) {
                     invs.add(f.wrap(this.level, a));
                  }

                  this.filteredMerge = invs;
                  this.mergedHandler.build(this, Collections.emptyList());
               } else {
                  this.filteredMerge = this.mergedHandler.getConnected();
               }
            } else {
               this.self = PlatformInventoryAccess.EMPTY;
            }
         } else {
            BlockFilter f = BlockFilter.getFilterAt(this.level, this.worldPosition);
            if (f != null) {
               this.self = f.wrap(this.level, this.block);
            } else {
               this.self = this.block;
            }
         }
      }
   }

   private void detectCableNetwork() {
      for (BlockPos p : InventoryCableNetwork.getNetwork(this.level).getNetworkNodes(this.worldPosition)) {
         if (this.level.isLoaded(p)) {
            BlockEntity be = this.level.getBlockEntity(p);
            if (be != this && be instanceof IInventoryConnector te) {
               this.linkedConnectors.add(te);
            }
         }
      }
   }

   public Component getDisplayName() {
      return Component.translatable("menu.toms_storage.inventory_connector");
   }

   public boolean stillValid(Player player) {
      if (this.channel != null) {
         RemoteConnections.Channel chn = RemoteConnections.get(this.level).getChannel(this.channel);
         if (chn != null && !chn.publicChannel && !chn.owner.equals(player.getUUID())) {
            return false;
         }
      }

      return this.level.getBlockEntity(this.worldPosition) == this && this.beaconLevel >= 0
         ? !(player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) > 64.0)
         : false;
   }

   @Override
   public IInventoryAccess getMergedHandler() {
      return this.self;
   }

   @Override
   public Collection<IInventoryAccess> getConnectedInventories() {
      return this.beaconLevel >= 0 ? Collections.emptyList() : (this.self == this.mergedHandler ? this.filteredMerge : Collections.singleton(this.self));
   }

   @Override
   public boolean hasConnectedInventories() {
      return !this.isRemoved() && this.beaconLevel >= 0;
   }

   @Override
   public void loadAdditional(CompoundTag nbt, Provider provider) {
      super.loadAdditional(nbt, provider);
      if (nbt.contains("channel")) {
         this.channel = nbt.getUUID("channel");
      } else {
         this.channel = null;
      }
   }

   @Override
   public void saveAdditional(CompoundTag nbt, Provider provider) {
      super.saveAdditional(nbt, provider);
      if (this.channel != null) {
         nbt.putUUID("channel", this.channel);
      }
   }

   @Override
   public IInventoryConnector getConnector() {
      return this;
   }

   @Override
   public boolean isAccessibleFrom(ServerLevel world, BlockPos blockPos, int level) {
      int cLocalLvl = Config.get().invLinkBeaconLvl;
      int cSDLvl = Config.get().invLinkBeaconLvlSameDim;
      int cCDLvl = Config.get().invLinkBeaconLvlCrossDim;
      int lvl = Math.max(this.beaconLevel, level);
      int lvlM = Math.min(this.beaconLevel, level);
      if (cLocalLvl == -1) {
         return false;
      }

      if (cCDLvl == -1 || lvl < cCDLvl || lvlM <= 0 && cCDLvl != 0) {
         if (!this.level.dimension().equals(world.dimension())) {
            return false;
         } else if (cSDLvl == -1 || lvl < cSDLvl || lvlM <= 0 && cSDLvl != 0) {
            int range = Config.get().invLinkBeaconRange;
            range *= range;
            return blockPos.distSqr(this.worldPosition) < range;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   @Override
   public UUID getChannel() {
      return this.channel;
   }

   public void setChannel(UUID chn) {
      this.channel = chn;
      this.setChanged();
   }

   public int getBeaconLevel() {
      return this.beaconLevel;
   }

   @Override
   public Collection<IInventoryConnector> getConnectedConnectors() {
      return this.linkedConnectors;
   }

   public boolean hasBeacon() {
      BlockState state = this.level.getBlockState(this.worldPosition);
      Direction facing = (Direction)state.getValue(InventoryCableConnectorBlock.FACING);
      BlockPos pos = this.worldPosition.relative(facing);
      BlockState st = this.level.getBlockState(pos);
      return st.is(Blocks.BEACON);
   }
}
