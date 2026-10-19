package noobanidus.mods.lootr.common.api.data.blockentity;

import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.ILootrSavedData;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record RandomizableContainerBlockEntityLootrInfoProvider(
   @NotNull RandomizableContainerBlockEntity blockEntity, UUID id, String cachedId, NonNullList<ItemStack> customInventory
) implements ILootrBlockEntity {
   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return switch (this.blockEntity) {
         case BarrelBlockEntity ignored -> LootrBlockType.BARREL;
         case TrappedChestBlockEntity ignored -> LootrBlockType.TRAPPED_CHEST;
         case ShulkerBoxBlockEntity ignored -> LootrBlockType.SHULKER;
         default -> LootrBlockType.CHEST;
      };
   }

   @Override
   public ILootrType getInfoNewType() {
      return BuiltInLootrTypes.SIMPLE;
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.id();
   }

   @Override
   public String getInfoKey() {
      return this.cachedId();
   }

   @Override
   public boolean hasBeenOpened() {
      return false;
   }

   @Override
   public boolean isPhysicallyOpen() {
      return false;
   }

   @NotNull
   @Override
   public BlockPos getInfoPos() {
      return this.blockEntity.getBlockPos();
   }

   @Override
   public ResourceKey<LootTable> getInfoLootTable() {
      return this.blockEntity.getLootTable();
   }

   @Override
   public Component getInfoDisplayName() {
      return this.blockEntity.getDisplayName();
   }

   @NotNull
   @Override
   public ResourceKey<Level> getInfoDimension() {
      return this.blockEntity.getLevel().dimension();
   }

   @Override
   public int getInfoContainerSize() {
      return this.blockEntity.getContainerSize();
   }

   @Override
   public long getInfoLootSeed() {
      return this.blockEntity.getLootTableSeed();
   }

   @Nullable
   @Override
   public NonNullList<ItemStack> getInfoReferenceInventory() {
      return this.customInventory();
   }

   @Override
   public boolean isInfoReferenceInventory() {
      return false;
   }

   @Override
   public Level getInfoLevel() {
      return this.blockEntity.getLevel();
   }

   @Override
   public Container getInfoContainer() {
      return this.blockEntity;
   }

   @Override
   public void markChanged() {
      this.blockEntity.setChanged();
   }

   @Override
   public void markDataChanged() {
      ILootrSavedData data = LootrAPI.getData(this);
      if (data != null) {
         data.markChanged();
      }
   }

   @Nullable
   @Override
   public Set<UUID> getClientOpeners() {
      return null;
   }

   @Override
   public boolean isClientOpened() {
      return false;
   }

   @Override
   public void setClientOpened(boolean opened) {
   }
}
