package noobanidus.mods.lootr.common.block.entity;

import com.google.auto.service.AutoService;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity.AnimationStatus;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.SimpleLootrInstance;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LootrShulkerBlockEntity extends RandomizableContainerBlockEntity implements ILootrBlockEntity {
   protected final SimpleLootrInstance simpleLootrInstance = new SimpleLootrInstance(this::getVisualOpeners, 27);
   private int openCount;
   private AnimationStatus animationStatus = AnimationStatus.CLOSED;
   private float progress;
   private float progressOld;

   public LootrShulkerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
      super(LootrRegistry.getShulkerBlockEntity(), pWorldPosition, pBlockState);
   }

   @Override
   public void defaultTick(Level level, BlockPos pos, BlockState state) {
      ILootrBlockEntity.super.defaultTick(level, pos, state);
      this.updateAnimation(level, pos, state);
   }

   private void updateAnimation(Level pLevel, BlockPos pPos, BlockState pState) {
      this.progressOld = this.progress;
      switch (this.animationStatus) {
         case CLOSED:
            this.progress = 0.0F;
            break;
         case OPENING:
            this.progress += 0.1F;
            if (this.progressOld == 0.0F) {
               doNeighborUpdates(pLevel, pPos, pState);
            }

            if (this.progress >= 1.0F) {
               this.animationStatus = AnimationStatus.OPENED;
               this.progress = 1.0F;
               doNeighborUpdates(pLevel, pPos, pState);
            }

            this.moveCollidedEntities(pLevel, pPos, pState);
            break;
         case OPENED:
            this.progress = 1.0F;
            break;
         case CLOSING:
            this.progress -= 0.1F;
            if (this.progressOld == 1.0F) {
               doNeighborUpdates(pLevel, pPos, pState);
            }

            if (this.progress <= 0.0F) {
               this.animationStatus = AnimationStatus.CLOSED;
               this.progress = 0.0F;
               doNeighborUpdates(pLevel, pPos, pState);
            }
      }
   }

   public AnimationStatus getAnimationStatus() {
      return this.animationStatus;
   }

   public AABB getBoundingBox(BlockState pState) {
      return Shulker.getProgressAabb(1.0F, (Direction)pState.getValue(ShulkerBoxBlock.FACING), 0.5F * this.getProgress(1.0F));
   }

   private void moveCollidedEntities(Level pLevel, BlockPos pPos, BlockState pState) {
      if (pState.getBlock() instanceof ShulkerBoxBlock) {
         Direction direction = (Direction)pState.getValue(ShulkerBoxBlock.FACING);
         AABB aabb = Shulker.getProgressDeltaAabb(1.0F, direction, this.progressOld, this.progress).move(pPos);

         for (Entity entity : pLevel.getEntities(null, aabb)) {
            if (entity.getPistonPushReaction() != PushReaction.IGNORE) {
               entity.move(
                  MoverType.SHULKER_BOX,
                  new Vec3(
                     (aabb.getXsize() + 0.01) * direction.getStepX(),
                     (aabb.getYsize() + 0.01) * direction.getStepY(),
                     (aabb.getZsize() + 0.01) * direction.getStepZ()
                  )
               );
            }
         }
      }
   }

   public int getContainerSize() {
      return this.simpleLootrInstance.getInfoContainerSize();
   }

   public boolean triggerEvent(int pEvent, int pCount) {
      if (pEvent == 1) {
         this.openCount = pCount;
         if (pCount == 0) {
            this.animationStatus = AnimationStatus.CLOSING;
         }

         if (pCount == 1) {
            this.animationStatus = AnimationStatus.OPENING;
         }

         return true;
      } else {
         return super.triggerEvent(pEvent, pCount);
      }
   }

   public void startOpen(Player pPlayer) {
      if (!this.remove && !pPlayer.isSpectator()) {
         if (!this.simpleLootrInstance.hasBeenOpened()) {
            this.simpleLootrInstance.setHasBeenOpened();
            this.markChanged();
         }

         if (this.openCount < 0) {
            this.openCount = 0;
         }

         this.openCount++;
         this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
         if (this.openCount == 1) {
            this.level.gameEvent(pPlayer, GameEvent.CONTAINER_OPEN, this.worldPosition);
            this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
         }

         if (!this.level.isClientSide() && LootrAPI.isCustomTrapped() && this.isInfoReferenceInventory()) {
            Block block = this.getBlockState().getBlock();
            this.level.updateNeighborsAt(this.getBlockPos(), block);
            this.level.updateNeighborsAt(this.getBlockPos().below(), block);
         }
      }
   }

   public void stopOpen(Player pPlayer) {
      if (!this.remove && !pPlayer.isSpectator()) {
         this.openCount--;
         this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
         if (this.openCount <= 0) {
            this.level.gameEvent(pPlayer, GameEvent.CONTAINER_CLOSE, this.worldPosition);
            this.level
               .playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_CLOSE, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
         }
      }
   }

   protected Component getDefaultName() {
      return Component.translatable("container.shulkerBox");
   }

   protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
      return null;
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

   protected NonNullList<ItemStack> getItems() {
      return this.simpleLootrInstance.getEmptyItemList();
   }

   protected void setItems(NonNullList<ItemStack> pItems) {
   }

   public float getProgress(float pPartialTicks) {
      return Mth.lerp(pPartialTicks, this.progressOld, this.progress);
   }

   public boolean isClosed() {
      return this.animationStatus == AnimationStatus.CLOSED;
   }

   @Nullable
   @Override
   public Set<UUID> getClientOpeners() {
      return this.simpleLootrInstance.getClientOpeners();
   }

   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return LootrBlockType.SHULKER;
   }

   @Override
   public ILootrType getInfoNewType() {
      return BuiltInLootrTypes.SHULKER;
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
      return !this.isClosed();
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

   @Override
   public void markChanged() {
      this.setChanged();
      this.markDataChanged();
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

   @Override
   public int getPhysicalOpenerCount() {
      return this.openCount;
   }

   @Nullable
   @Override
   public IContainerTrigger getTrigger() {
      return LootrRegistry.getShulkerTrigger();
   }

   @Override
   public double getParticleYOffset() {
      return 1.1;
   }

   private static void doNeighborUpdates(Level pLevel, BlockPos pPos, BlockState pState) {
      pState.updateNeighbourShapes(pLevel, pPos, 3);
      pLevel.updateNeighborsAt(pPos, pState.getBlock());
   }

   @AutoService(ILootrBlockEntityConverter.class)
   public static class DefaultBlockEntityConverter implements ILootrBlockEntityConverter<LootrShulkerBlockEntity> {
      public ILootrBlockEntity apply(LootrShulkerBlockEntity blockEntity) {
         return blockEntity;
      }

      @Override
      public BlockEntityType<?> getBlockEntityType() {
         return LootrRegistry.getShulkerBlockEntity();
      }
   }
}
