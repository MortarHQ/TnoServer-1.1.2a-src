package noobanidus.mods.lootr.common.block.entity;

import com.google.auto.service.AutoService;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.ILootrWorldlyContainer;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.SimpleLootrInstance;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LootrBarrelBlockEntity extends RandomizableContainerBlockEntity implements ILootrBlockEntity, ILootrWorldlyContainer {
   protected final SimpleLootrInstance simpleLootrInstance = new SimpleLootrInstance(this::getVisualOpeners, 27);
   private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
      protected void onOpen(Level level, BlockPos pos, BlockState state) {
         if (!LootrBarrelBlockEntity.this.hasBeenOpened()) {
            LootrBarrelBlockEntity.this.simpleLootrInstance.setHasBeenOpened();
            LootrBarrelBlockEntity.this.markChanged();
         }

         LootrBarrelBlockEntity.this.playSound(state, SoundEvents.BARREL_OPEN);
         LootrBarrelBlockEntity.this.updateBlockState(state, true);
         if (LootrAPI.isCustomTrapped() && LootrBarrelBlockEntity.this.isInfoReferenceInventory()) {
            Block block = state.getBlock();
            level.updateNeighborsAt(pos, block);
            level.updateNeighborsAt(pos.below(), block);
         }
      }

      protected void onClose(Level level, BlockPos pos, BlockState state) {
         LootrBarrelBlockEntity.this.playSound(state, SoundEvents.BARREL_CLOSE);
         LootrBarrelBlockEntity.this.updateBlockState(state, false);
      }

      protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int p_155069_, int p_155070_) {
      }

      protected boolean isOwnContainer(Player player) {
         return player.containerMenu instanceof ChestMenu chestMenu && chestMenu.getContainer() instanceof ILootrInventory data
            ? data.getInfo().getInfoUUID().equals(LootrBarrelBlockEntity.this.getInfoUUID())
            : false;
      }
   };

   public LootrBarrelBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
      super(LootrRegistry.getBarrelBlockEntity(), pWorldPosition, pBlockState);
   }

   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return LootrBlockType.BARREL;
   }

   @Override
   public ILootrType getInfoNewType() {
      return BuiltInLootrTypes.BARREL;
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.simpleLootrInstance.getInfoUUID();
   }

   @Override
   public boolean isPhysicallyOpen() {
      return this.getBlockState().hasProperty(BarrelBlock.OPEN) && (Boolean)this.getBlockState().getValue(BarrelBlock.OPEN);
   }

   protected NonNullList<ItemStack> getItems() {
      return this.simpleLootrInstance.getEmptyItemList();
   }

   protected void setItems(NonNullList<ItemStack> pItems) {
   }

   public void unpackLootTable(@Nullable Player player) {
   }

   public void loadAdditional(CompoundTag compound, Provider provider) {
      super.loadAdditional(compound, provider);
      this.tryLoadLootTable(compound);
      this.simpleLootrInstance.loadAdditional(compound, provider);
   }

   public void saveToItem(ItemStack itemstack, Provider provider) {
      this.simpleLootrInstance.setSavingToItem(true);
      super.saveToItem(itemstack, provider);
      this.simpleLootrInstance.setSavingToItem(false);
   }

   protected void saveAdditional(CompoundTag compound, Provider provider) {
      super.saveAdditional(compound, provider);
      this.trySaveLootTable(compound);
      this.simpleLootrInstance.saveAdditional(compound, provider, this.level != null && this.level.isClientSide());
   }

   protected Component getDefaultName() {
      return Component.translatable("container.barrel");
   }

   protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
      return null;
   }

   public int getContainerSize() {
      return this.simpleLootrInstance.getInfoContainerSize();
   }

   public void startOpen(Player pPlayer) {
      if (!this.remove && !pPlayer.isSpectator()) {
         this.openersCounter.incrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   public void stopOpen(Player pPlayer) {
      if (!this.remove && !pPlayer.isSpectator()) {
         this.openersCounter.decrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   public void recheckOpen() {
      if (!this.remove) {
         this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   protected void updateBlockState(BlockState pState, boolean pOpen) {
      this.level.setBlock(this.getBlockPos(), (BlockState)pState.setValue(BarrelBlock.OPEN, pOpen), 3);
   }

   protected void playSound(BlockState pState, SoundEvent pSound) {
      Vec3i vec3i = ((Direction)pState.getValue(BarrelBlock.FACING)).getNormal();
      double d0 = this.worldPosition.getX() + 0.5 + vec3i.getX() / 2.0;
      double d1 = this.worldPosition.getY() + 0.5 + vec3i.getY() / 2.0;
      double d2 = this.worldPosition.getZ() + 0.5 + vec3i.getZ() / 2.0;
      this.level.playSound(null, d0, d1, d2, pSound, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
   }

   @Override
   public void markChanged() {
      this.setChanged();
      this.markDataChanged();
   }

   @Override
   public boolean hasBeenOpened() {
      return this.simpleLootrInstance.hasBeenOpened();
   }

   @Nullable
   @Override
   public Set<UUID> getClientOpeners() {
      return this.simpleLootrInstance.getClientOpeners();
   }

   @Override
   public boolean isClientOpened() {
      return this.simpleLootrInstance.isClientOpened();
   }

   @Override
   public void setClientOpened(boolean opened) {
      this.simpleLootrInstance.setClientOpened(opened);
   }

   @NotNull
   public CompoundTag getUpdateTag(Provider provider) {
      CompoundTag result = super.getUpdateTag(provider);
      this.simpleLootrInstance.fillUpdateTag(result, provider, this.level != null && this.level.isClientSide());
      return result;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
   }

   @NotNull
   @Override
   public BlockPos getInfoPos() {
      return this.getBlockPos();
   }

   @Override
   public ResourceKey<LootTable> getInfoLootTable() {
      return this.getLootTable();
   }

   @Nullable
   @Override
   public Component getInfoDisplayName() {
      return this.getDisplayName();
   }

   @NotNull
   @Override
   public ResourceKey<Level> getInfoDimension() {
      return this.getLevel().dimension();
   }

   @Override
   public int getInfoContainerSize() {
      return this.getContainerSize();
   }

   @Override
   public long getInfoLootSeed() {
      return this.getLootTableSeed();
   }

   @Nullable
   @Override
   public NonNullList<ItemStack> getInfoReferenceInventory() {
      return this.simpleLootrInstance.getCustomInventory();
   }

   @Override
   public void setInfoReferenceInventory(NonNullList<ItemStack> reference) {
      this.simpleLootrInstance.setCustomInventory(reference);
   }

   @Override
   public boolean isInfoReferenceInventory() {
      return this.isInfoReferenceInventoryInternal(this.simpleLootrInstance.isCustomInventory());
   }

   @Override
   public Level getInfoLevel() {
      return this.getLevel();
   }

   @Nullable
   @Override
   public IContainerTrigger getTrigger() {
      return LootrRegistry.getBarrelTrigger();
   }

   @Override
   public String getInfoKey() {
      return this.simpleLootrInstance.getInfoKey();
   }

   @Override
   public int getPhysicalOpenerCount() {
      return this.openersCounter.getOpenerCount();
   }

   @Override
   public double getParticleYOffset() {
      return 1.1;
   }

   @AutoService(ILootrBlockEntityConverter.class)
   public static class DefaultBlockEntityConverter implements ILootrBlockEntityConverter<LootrBarrelBlockEntity> {
      public ILootrBlockEntity apply(LootrBarrelBlockEntity blockEntity) {
         return blockEntity;
      }

      @Override
      public BlockEntityType<?> getBlockEntityType() {
         return LootrRegistry.getBarrelBlockEntity();
      }
   }
}
