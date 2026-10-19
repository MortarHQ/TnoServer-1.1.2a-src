package noobanidus.mods.lootr.common.block.entity;

import com.google.auto.service.AutoService;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.ILootrWorldlyContainer;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.LootrTags;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.SimpleLootrInstance;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootr.common.data.LootrInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LootrChestBlockEntity extends ChestBlockEntity implements ILootrBlockEntity, ILootrWorldlyContainer {
   protected final SimpleLootrInstance simpleLootrInstance = new SimpleLootrInstance(this::getVisualOpeners, 27);
   private final ChestLidController chestLidController = new ChestLidController();
   private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
      protected void onOpen(Level level, BlockPos pos, BlockState state) {
         if (!LootrChestBlockEntity.this.hasBeenOpened()) {
            LootrChestBlockEntity.this.simpleLootrInstance.setHasBeenOpened();
            LootrChestBlockEntity.this.markChanged();
         }

         LootrChestBlockEntity.playSound(level, pos, state, SoundEvents.CHEST_OPEN);
      }

      protected void onClose(Level level, BlockPos pos, BlockState state) {
         LootrChestBlockEntity.playSound(level, pos, state, SoundEvents.CHEST_CLOSE);
      }

      protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int p_155364_, int p_155365_) {
         LootrChestBlockEntity.this.signalOpenCount(level, pos, state, p_155364_, p_155365_);
      }

      protected boolean isOwnContainer(Player player) {
         return player.containerMenu instanceof ChestMenu menu && menu.getContainer() instanceof LootrInventory data
            ? LootrChestBlockEntity.this.getInfoUUID().equals(data.getInfo().getInfoUUID())
            : false;
      }
   };

   protected LootrChestBlockEntity(BlockEntityType<?> p_155327_, BlockPos p_155328_, BlockState p_155329_) {
      super(p_155327_, p_155328_, p_155329_);
   }

   public LootrChestBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
      this(LootrRegistry.getChestBlockEntity(), pWorldPosition, pBlockState);
   }

   @Override
   public void defaultTick(Level level, BlockPos pos, BlockState state) {
      ILootrBlockEntity.super.defaultTick(level, pos, state);
      this.chestLidController.tickLid();
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

   public boolean triggerEvent(int pId, int pType) {
      if (pId == 1) {
         this.chestLidController.shouldBeOpen(pType > 0);
         return true;
      } else {
         return super.triggerEvent(pId, pType);
      }
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

   public float getOpenNess(float pPartialTicks) {
      return this.chestLidController.getOpenness(pPartialTicks);
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

   public void unpackLootTable(@Nullable Player player) {
   }

   @Nullable
   @Override
   public Set<UUID> getClientOpeners() {
      return this.simpleLootrInstance.getClientOpeners();
   }

   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return LootrBlockType.CHEST;
   }

   @Override
   public ILootrType getInfoNewType() {
      return BuiltInLootrTypes.CHEST;
   }

   @Override
   public void markChanged() {
      this.setChanged();
      this.markDataChanged();
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.simpleLootrInstance.getInfoUUID();
   }

   @Override
   public String getInfoKey() {
      return this.simpleLootrInstance.getInfoKey();
   }

   @Override
   public boolean hasBeenOpened() {
      return this.simpleLootrInstance.hasBeenOpened();
   }

   @Override
   public boolean isPhysicallyOpen() {
      return this.getOpenNess(1.0F) > 0.0F;
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
      return this.simpleLootrInstance.getInfoContainerSize();
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

   @Override
   public int getPhysicalOpenerCount() {
      return this.openersCounter.getOpenerCount();
   }

   @Nullable
   @Override
   public IContainerTrigger getTrigger() {
      return LootrRegistry.getChestTrigger();
   }

   protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int p_155868_, int p_155869_) {
      super.signalOpenCount(level, pos, state, p_155868_, p_155869_);
      if (state.is(LootrTags.Blocks.TRAPPED_CHESTS) || LootrAPI.isCustomTrapped() && p_155868_ != p_155869_ && this.isInfoReferenceInventory()) {
         Block block = state.getBlock();
         level.updateNeighborsAt(pos, block);
         level.updateNeighborsAt(pos.below(), block);
      }
   }

   public static int getOpenCount(BlockGetter pLevel, BlockPos pPos) {
      BlockState blockstate = pLevel.getBlockState(pPos);
      return blockstate.hasBlockEntity() && pLevel.getBlockEntity(pPos) instanceof LootrChestBlockEntity chest ? chest.openersCounter.getOpenerCount() : 0;
   }

   protected static void playSound(Level pLevel, BlockPos pPos, BlockState pState, SoundEvent pSound) {
      double d0 = pPos.getX() + 0.5;
      double d1 = pPos.getY() + 0.5;
      double d2 = pPos.getZ() + 0.5;
      pLevel.playSound(null, d0, d1, d2, pSound, SoundSource.BLOCKS, 0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F);
   }

   @AutoService(ILootrBlockEntityConverter.class)
   public static class DefaultBlockEntityConverter implements ILootrBlockEntityConverter<LootrChestBlockEntity> {
      public ILootrBlockEntity apply(LootrChestBlockEntity blockEntity) {
         return blockEntity;
      }

      @Override
      public BlockEntityType<?> getBlockEntityType() {
         return LootrRegistry.getChestBlockEntity();
      }
   }
}
