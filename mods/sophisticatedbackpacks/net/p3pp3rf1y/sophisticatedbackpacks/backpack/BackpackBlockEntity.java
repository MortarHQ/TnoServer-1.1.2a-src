package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.EmptyFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.EmptyEnergyStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContainer;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModBlocks;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.controller.ControllerBlockEntityBase;
import net.p3pp3rf1y.sophisticatedcore.controller.IControllableStorage;
import net.p3pp3rf1y.sophisticatedcore.inventory.CachedFailedInsertInventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.renderdata.TankPosition;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

public class BackpackBlockEntity extends BlockEntity implements IControllableStorage {
   public static final String BACKPACK_DATA_TAG = "backpackData";
   @Nullable
   private BlockPos controllerPos = null;
   private IBackpackWrapper backpackWrapper = IBackpackWrapper.Noop.INSTANCE;
   private boolean updateBlockRender = true;
   private boolean chunkBeingUnloaded = false;
   @Nullable
   private IItemHandler externalItemHandler;
   @Nullable
   private IFluidHandler externalFluidHandler;
   @Nullable
   private IEnergyStorage externalEnergyStorage;
   private boolean triedUnpackingLoot = false;
   private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
      protected void onOpen(Level level, BlockPos pos, BlockState state) {
         BackpackBlockEntity.this.setOpenBlockState(state, true);
      }

      protected void onClose(Level level, BlockPos pos, BlockState state) {
         BackpackBlockEntity.this.setOpenBlockState(state, false);
      }

      protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int count, int openCount) {
      }

      protected boolean isOwnContainer(Player player) {
         return player.containerMenu instanceof BackpackContainer backpackContainer
            && backpackContainer.getBlockPosition().<Boolean>map(BackpackBlockEntity.this.worldPosition::equals).orElse(false);
      }
   };

   public BackpackBlockEntity(BlockPos pos, BlockState state) {
      super(ModBlocks.BACKPACK_TILE_TYPE.get(), pos, state);
   }

   public void setBackpack(ItemStack backpack) {
      this.backpackWrapper = BackpackWrapper.fromStack(backpack);
      this.backpackWrapper.setContentsChangeHandler(() -> {
         this.setChanged();
         WorldHelper.notifyBlockUpdate(this);
      });
      this.backpackWrapper.setInventorySlotChangeHandler(this::setChanged);
      this.backpackWrapper.setUpgradeCachesInvalidatedHandler(this::invalidateHandlers);
      this.backpackWrapper.getRenderInfo().setRenderUpdateChangeListener(renderInfo -> {
         this.updateBlockRender = true;
         WorldHelper.notifyBlockUpdate(this);
      });
   }

   public void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.setBackpackFromNbt(tag);
      this.loadControllerPos(tag);
      if (this.level != null && !this.level.isClientSide()) {
         this.removeControllerPos();
         this.tryToAddToController();
      }

      WorldHelper.notifyBlockUpdate(this);
   }

   public void onLoad() {
      super.onLoad();
      this.registerWithControllerOnLoad();
   }

   private void setBackpackFromNbt(CompoundTag nbt) {
      RegistryHelper.getRegistryAccess()
         .ifPresent(registryAccess -> this.setBackpack(ItemStack.parseOptional(registryAccess, nbt.getCompound("backpackData"))));
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      this.writeBackpack(tag, registries);
      this.saveControllerPos(tag);
   }

   private void writeBackpack(CompoundTag ret, Provider registries) {
      ItemStack backpackCopy = this.backpackWrapper.getBackpack().copy();
      ret.put("backpackData", backpackCopy.save(registries));
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag ret = super.getUpdateTag(registries);
      this.writeBackpack(ret, registries);
      this.saveControllerPos(ret);
      ret.putBoolean("updateBlockRender", this.updateBlockRender);
      this.updateBlockRender = false;
      return ret;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, Provider registries) {
      CompoundTag tag = pkt.getTag();
      if (tag != null) {
         this.setBackpackFromNbt(tag);
         if (tag.getBoolean("updateBlockRender")) {
            WorldHelper.notifyBlockUpdate(this);
         }
      }
   }

   public IBackpackWrapper getBackpackWrapper() {
      return this.backpackWrapper;
   }

   private void invalidateHandlers() {
      this.invalidateCapabilities();
      this.externalItemHandler = null;
      this.externalFluidHandler = null;
      this.externalEnergyStorage = null;
      this.onInventoryInputOutputHandlerRefresh();
   }

   private boolean isBlockConnectionDisallowed(@Nullable Direction direction) {
      return direction != null
         && this.level != null
         && Config.SERVER.noConnectionBlocks.isBlockConnectionDisallowed(this.level.getBlockState(this.getBlockPos().relative(direction)).getBlock());
   }

   @Nullable
   public IItemHandler getExternalItemHandler(@Nullable Direction direction) {
      if (this.isBlockConnectionDisallowed(direction)) {
         return null;
      }

      if (this.externalItemHandler == null) {
         this.externalItemHandler = new CachedFailedInsertInventoryHandler(() -> {
            IBackpackWrapper backpackWrapper = this.getBackpackWrapper();
            if (!this.triedUnpackingLoot && this.level != null && !this.level.isClientSide()) {
               backpackWrapper.fillWithLootAndExtraItems(this.level, this.getBlockPos());
               this.triedUnpackingLoot = true;
            }

            return backpackWrapper.getInventoryForInputOutput();
         }, () -> this.level != null ? this.level.getGameTime() : 0L);
      }

      return this.externalItemHandler;
   }

   @Nullable
   public IFluidHandler getExternalFluidHandler(@Nullable Direction direction) {
      if (this.isBlockConnectionDisallowed(direction)) {
         return null;
      }

      if (this.externalFluidHandler == null) {
         this.externalFluidHandler = this.getBackpackWrapper().getFluidHandler().map(IFluidHandler.class::cast).orElse(EmptyFluidHandler.INSTANCE);
      }

      return this.externalFluidHandler;
   }

   @Nullable
   public IEnergyStorage getExternalEnergyStorage(@Nullable Direction direction) {
      if (this.isBlockConnectionDisallowed(direction)) {
         return null;
      }

      if (this.externalEnergyStorage == null) {
         this.externalEnergyStorage = this.getBackpackWrapper().getEnergyStorage().map(IEnergyStorage.class::cast).orElse(EmptyEnergyStorage.INSTANCE);
      }

      return this.externalEnergyStorage;
   }

   public void refreshRenderState() {
      BlockState state = this.getBlockState();
      state = (BlockState)state.setValue(BackpackBlock.LEFT_TANK, false);
      state = (BlockState)state.setValue(BackpackBlock.RIGHT_TANK, false);
      RenderInfo renderInfo = this.backpackWrapper.getRenderInfo();

      for (TankPosition pos : renderInfo.getTankRenderInfos().keySet()) {
         if (pos == TankPosition.LEFT) {
            state = (BlockState)state.setValue(BackpackBlock.LEFT_TANK, true);
         } else if (pos == TankPosition.RIGHT) {
            state = (BlockState)state.setValue(BackpackBlock.RIGHT_TANK, true);
         }
      }

      state = (BlockState)state.setValue(BackpackBlock.BATTERY, renderInfo.getBatteryRenderInfo().isPresent());
      Level l = Objects.requireNonNull(this.level);
      l.setBlockAndUpdate(this.worldPosition, state);
      l.updateNeighborsAt(this.worldPosition, state.getBlock());
      WorldHelper.notifyBlockUpdate(this);
   }

   private void setOpenBlockState(BlockState state, boolean open) {
      if (this.level != null && state.hasProperty(BackpackBlock.OPEN) && (Boolean)state.getValue(BackpackBlock.OPEN) != open) {
         this.level.setBlock(this.worldPosition, (BlockState)state.setValue(BackpackBlock.OPEN, open), 3);
      }
   }

   public void startOpen(Player player) {
      if (this.level != null && !this.level.isClientSide && !this.remove && !player.isSpectator()) {
         this.openersCounter.incrementOpeners(player, this.level, this.getBlockPos(), this.getBlockState());
      }
   }

   public void stopOpen(Player player) {
      if (this.level != null && !this.level.isClientSide && !this.remove && !player.isSpectator()) {
         this.openersCounter.decrementOpeners(player, this.level, this.getBlockPos(), this.getBlockState());
      }
   }

   public void recheckOpen() {
      if (!this.remove && this.level != null) {
         this.openersCounter.recheckOpeners(this.level, this.getBlockPos(), this.getBlockState());
      }
   }

   public static void serverTick(Level level, BlockPos blockPos, BackpackBlockEntity backpackBlockEntity) {
      if (!level.isClientSide) {
         backpackBlockEntity.backpackWrapper
            .getUpgradeHandler()
            .getWrappersThatImplement(ITickableUpgrade.class)
            .forEach(upgrade -> upgrade.tick(null, level, blockPos));
      }
   }

   public IStorageWrapper getStorageWrapper() {
      return this.backpackWrapper;
   }

   public void setControllerPos(BlockPos controllerPos) {
      this.controllerPos = controllerPos;
      this.setChanged();
   }

   public Optional<BlockPos> getControllerPos() {
      return Optional.ofNullable(this.controllerPos);
   }

   public void removeControllerPos() {
      this.controllerPos = null;
   }

   public BlockPos getStorageBlockPos() {
      return this.getBlockPos();
   }

   public Level getStorageBlockLevel() {
      return Objects.requireNonNull(this.getLevel());
   }

   public boolean canConnectStorages() {
      return false;
   }

   public void unregisterController() {
      super.unregisterController();
      this.backpackWrapper.unregisterOnSlotsChangeListener();
      this.backpackWrapper.unregisterOnInventoryHandlerRefreshListener();
   }

   public void registerController(ControllerBlockEntityBase controllerBlockEntity) {
      super.registerController(controllerBlockEntity);
      if (this.level != null && !this.level.isClientSide) {
         this.backpackWrapper.registerOnSlotsChangeListener(this::changeSlots);
         this.backpackWrapper.registerOnInventoryHandlerRefreshListener(this::registerInventoryStackListeners);
      }
   }

   public void onChunkUnloaded() {
      super.onChunkUnloaded();
      this.chunkBeingUnloaded = true;
   }

   public void setRemoved() {
      if (!this.chunkBeingUnloaded && this.level != null) {
         this.removeFromController();
      }

      super.setRemoved();
   }
}
