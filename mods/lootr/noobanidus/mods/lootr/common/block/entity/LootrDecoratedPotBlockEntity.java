package noobanidus.mods.lootr.common.block.entity;

import com.google.auto.service.AutoService;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity.WobbleStyle;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ContainerSingleItem.BlockContainerSingleItem;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.ILootrWorldlyContainer;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.PlatformAPI;
import noobanidus.mods.lootr.common.api.PotDecorationsAdapter;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.SimpleLootrInstance;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LootrDecoratedPotBlockEntity
   extends BlockEntity
   implements RandomizableContainer,
   BlockContainerSingleItem,
   ILootrBlockEntity,
   ILootrWorldlyContainer {
   public long wobbleStartedAtTick;
   @Nullable
   public WobbleStyle lastWobbleStyle;
   @Nullable
   private PotDecorationsAdapter decorations;
   @Nullable
   protected ResourceKey<LootTable> lootTable;
   protected long lootTableSeed;
   private final SimpleLootrInstance lootrInstance = new SimpleLootrInstance(this::getVisualOpeners, 1);

   public LootrDecoratedPotBlockEntity(BlockPos blockPos, BlockState blockState) {
      super(LootrRegistry.getDecoratedPotBlockEntity(), blockPos, blockState);
      this.decorations = PotDecorationsAdapter.EMPTY;
   }

   protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
      super.saveAdditional(compoundTag, provider);
      this.trySaveLootTable(compoundTag);
      this.getDecorations().save(compoundTag);
      this.lootrInstance.saveAdditional(compoundTag, provider, this.level == null || this.level.isClientSide());
   }

   protected void loadAdditional(CompoundTag compoundTag, Provider provider) {
      super.loadAdditional(compoundTag, provider);
      this.decorations = this.getDecorations().load(compoundTag);
      this.tryLoadLootTable(compoundTag);
      this.lootrInstance.loadAdditional(compoundTag, provider);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider provider) {
      CompoundTag compoundTag = super.getUpdateTag(provider);
      this.saveAdditional(compoundTag, provider);
      this.lootrInstance.fillUpdateTag(compoundTag, provider, this.level != null && this.level.isClientSide());
      return compoundTag;
   }

   @Nullable
   private ItemStack openAndTakeItem(ServerPlayer player) {
      ILootrInventory playerInventory = LootrAPI.getInventory(this, player);
      if (playerInventory == null) {
         return null;
      }

      ItemStack itemToTake = playerInventory.getItem(0);
      boolean hasItemToTake = !itemToTake.isEmpty();
      boolean isFirstServerOpen = !this.hasServerOpened(player);
      boolean isFirstVisualOpen = !this.hasVisualOpened(player);
      if (!hasItemToTake && !isFirstServerOpen && !isFirstVisualOpen) {
         return null;
      }

      if (hasItemToTake) {
         playerInventory.setItem(0, ItemStack.EMPTY);
         playerInventory.setChanged();
      }

      if (isFirstServerOpen) {
         this.performTrigger(player);
         player.awardStat(LootrRegistry.getLootedStat());
         LootrRegistry.getStatTrigger().trigger(player);
      }

      boolean openersChanged = this.addOpener(player);
      if (openersChanged) {
         this.performOpen(player);
      }

      boolean isFirstGlobalOpen = !this.lootrInstance.hasBeenOpened();
      if (isFirstGlobalOpen) {
         this.lootrInstance.setHasBeenOpened();
      }

      if (openersChanged || isFirstGlobalOpen) {
         this.performUpdate(player);
      }

      if (LootrAPI.isCustomTrapped() && this.isInfoReferenceInventory()) {
         Block block = this.getBlockState().getBlock();
         this.level.updateNeighborsAt(this.getBlockPos(), block);
         this.level.updateNeighborsAt(this.getBlockPos().below(), block);
      }

      return itemToTake;
   }

   @Override
   public void performOpen(ServerPlayer player) {
      ILootrBlockEntity.super.performOpen(player);
      PlatformAPI.performPotBreak(this, player);
   }

   public boolean dropContent(ServerPlayer player) {
      if (this.level != null && this.level.getServer() != null) {
         ItemStack takenItem = this.openAndTakeItem(player);
         if (takenItem == null) {
            return false;
         }

         this.spawnPotContents(takenItem);
         PlatformAPI.performPotBreak(this, player);
         return true;
      } else {
         return false;
      }
   }

   private void spawnPotContents(ItemStack takenItem) {
      BlockPos spawnBlockPos = this.worldPosition.relative(Direction.UP, 1);
      double spawnX = spawnBlockPos.getX() + 0.5;
      double spawnY = spawnBlockPos.getY() + 0.5 + EntityType.ITEM.getHeight() / 2.0F;
      double spawnZ = spawnBlockPos.getZ() + 0.5;
      if (!takenItem.isEmpty()) {
         this.spawnItemEntity(takenItem.split(this.level.random.nextInt(21) + 10), spawnX, spawnY, spawnZ);
      }

      for (ItemStack decorationItem : this.getDecorations().ordered()) {
         this.spawnItemEntity(decorationItem.copy(), spawnX, spawnY, spawnZ);
      }
   }

   private void spawnItemEntity(ItemStack itemStack, double x, double y, double z) {
      ItemEntity itemEntity = new ItemEntity(this.level, x, y, z, itemStack);
      itemEntity.setDeltaMovement(Vec3.ZERO);
      this.level.addFreshEntity(itemEntity);
   }

   public Direction getDirection() {
      return (Direction)this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
   }

   public PotDecorationsAdapter getDecorations() {
      if (this.decorations == null) {
         this.decorations = PotDecorationsAdapter.EMPTY;
      }

      return this.decorations;
   }

   public void setFromItem(ItemStack itemStack) {
      this.applyComponentsFromItemStack(itemStack);
   }

   public ItemStack getPotAsItem() {
      ItemStack itemStack = LootrRegistry.getDecoratedPotItem().getDefaultInstance();
      itemStack.applyComponents(this.collectComponents());
      return itemStack;
   }

   @Nullable
   public ResourceKey<LootTable> getLootTable() {
      return this.lootTable;
   }

   public void setLootTable(@Nullable ResourceKey<LootTable> resourceKey) {
      this.lootTable = resourceKey;
   }

   public long getLootTableSeed() {
      return this.lootTableSeed;
   }

   public void setLootTableSeed(long l) {
      this.lootTableSeed = l;
   }

   protected void collectImplicitComponents(Builder builder) {
      super.collectImplicitComponents(builder);
      if (this.lootTable != null) {
         builder.set(DataComponents.CONTAINER_LOOT, new SeededContainerLoot(this.lootTable, this.lootTableSeed));
      }
   }

   protected void applyImplicitComponents(DataComponentInput dataComponentInput) {
      super.applyImplicitComponents(dataComponentInput);
      this.decorations = LootrAPI.getDecorationsAdapter(dataComponentInput);
      SeededContainerLoot loot = (SeededContainerLoot)dataComponentInput.get(DataComponents.CONTAINER_LOOT);
      if (loot != null && loot.lootTable() != null) {
         this.lootTable = loot.lootTable();
         this.lootTableSeed = loot.seed();
      }
   }

   public void removeComponentsFromTag(CompoundTag compoundTag) {
      super.removeComponentsFromTag(compoundTag);
      compoundTag.remove("LootTable");
      compoundTag.remove("LootTableSeed");
   }

   public ItemStack getTheItem() {
      return ItemStack.EMPTY;
   }

   public ItemStack splitTheItem(int i) {
      return ItemStack.EMPTY;
   }

   public void setTheItem(ItemStack itemStack) {
   }

   public BlockEntity getContainerBlockEntity() {
      return this;
   }

   public void wobble(WobbleStyle wobbleStyle) {
      if (this.level != null && !this.level.isClientSide()) {
         this.level.blockEvent(this.getBlockPos(), this.getBlockState().getBlock(), 1, wobbleStyle.ordinal());
      }
   }

   public boolean triggerEvent(int i, int j) {
      if (this.level != null && i == 1 && j >= 0 && j < WobbleStyle.values().length) {
         this.wobbleStartedAtTick = this.level.getGameTime();
         this.lastWobbleStyle = WobbleStyle.values()[j];
         return true;
      } else {
         return super.triggerEvent(i, j);
      }
   }

   @Nullable
   @Override
   public Set<UUID> getClientOpeners() {
      return this.lootrInstance.getClientOpeners();
   }

   @Override
   public boolean isClientOpened() {
      return this.lootrInstance.isClientOpened();
   }

   @Override
   public void setClientOpened(boolean opened) {
      this.lootrInstance.setClientOpened(opened);
   }

   @Override
   public void markChanged() {
      this.setChanged();
      this.markDataChanged();
   }

   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return LootrBlockType.CHEST;
   }

   @Override
   public ILootrType getInfoNewType() {
      return BuiltInLootrTypes.POT;
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.lootrInstance.getInfoUUID();
   }

   @Override
   public String getInfoKey() {
      return this.lootrInstance.getInfoKey();
   }

   @Override
   public boolean hasBeenOpened() {
      return this.lootrInstance.hasBeenOpened();
   }

   @Override
   public boolean isPhysicallyOpen() {
      return false;
   }

   @NotNull
   @Override
   public BlockPos getInfoPos() {
      return this.getBlockPos();
   }

   @Nullable
   @Override
   public Component getInfoDisplayName() {
      return null;
   }

   @NotNull
   @Override
   public ResourceKey<Level> getInfoDimension() {
      return this.level.dimension();
   }

   @Override
   public int getInfoContainerSize() {
      return this.lootrInstance.getInfoContainerSize();
   }

   @Nullable
   @Override
   public NonNullList<ItemStack> getInfoReferenceInventory() {
      return this.lootrInstance.getCustomInventory();
   }

   @Override
   public void setInfoReferenceInventory(NonNullList<ItemStack> reference) {
      this.lootrInstance.setCustomInventory(reference);
   }

   @Override
   public boolean isInfoReferenceInventory() {
      return this.isInfoReferenceInventoryInternal(this.lootrInstance.isCustomInventory());
   }

   @Nullable
   @Override
   public ResourceKey<LootTable> getInfoLootTable() {
      return this.lootTable;
   }

   @Override
   public long getInfoLootSeed() {
      return this.lootTableSeed;
   }

   @Override
   public Level getInfoLevel() {
      return this.level;
   }

   @Nullable
   @Override
   public IContainerTrigger getTrigger() {
      return LootrRegistry.getPotTrigger();
   }

   @Override
   public double getParticleYOffset() {
      return 1.3;
   }

   @Override
   public double[] getParticleXBounds() {
      return new double[]{0.4, 0.6};
   }

   @Override
   public double[] getParticleZBounds() {
      return new double[]{0.4, 0.6};
   }

   @AutoService(ILootrBlockEntityConverter.class)
   public static class DefaultBlockEntityConverter implements ILootrBlockEntityConverter<LootrDecoratedPotBlockEntity> {
      public ILootrBlockEntity apply(LootrDecoratedPotBlockEntity blockEntity) {
         return blockEntity;
      }

      @Override
      public BlockEntityType<?> getBlockEntityType() {
         return LootrRegistry.getDecoratedPotBlockEntity();
      }
   }
}
