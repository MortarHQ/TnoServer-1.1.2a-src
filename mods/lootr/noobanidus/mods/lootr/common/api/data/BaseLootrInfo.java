package noobanidus.mods.lootr.common.api.data;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.ILootrType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record BaseLootrInfo(
   @Deprecated @Nullable LootrBlockType blockType,
   @Deprecated @Nullable ILootrInfo.LootrInfoType infoType,
   @Nullable ILootrType type,
   UUID uuid,
   String cachedKey,
   BlockPos pos,
   @Nullable Component name,
   ResourceKey<Level> dimension,
   int containerSize,
   NonNullList<ItemStack> customInventory,
   ResourceKey<LootTable> table,
   long seed
) implements ILootrInfo {
   public static BaseLootrInfo copy(ILootrInfo info) {
      return new BaseLootrInfo(
         info.getInfoBlockType(),
         info.getInfoType(),
         info.getInfoNewType(),
         info.getInfoUUID(),
         info.getInfoKey(),
         info.getInfoPos(),
         info.getInfoDisplayName(),
         info.getInfoDimension(),
         info.getInfoContainerSize(),
         info.getInfoReferenceInventory(),
         info.getInfoLootTable(),
         info.getInfoLootSeed()
      );
   }

   @Deprecated
   @Nullable
   @Override
   public LootrBlockType getInfoBlockType() {
      return this.blockType();
   }

   @Deprecated
   @Nullable
   @Override
   public ILootrInfo.LootrInfoType getInfoType() {
      return this.infoType();
   }

   @Nullable
   @Override
   public ILootrType getInfoNewType() {
      return this.type();
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.uuid();
   }

   @Override
   public String getInfoKey() {
      return this.cachedKey();
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
      return this.pos();
   }

   @Override
   public Component getInfoDisplayName() {
      return (Component)(this.name == null ? Component.empty() : this.name);
   }

   @NotNull
   @Override
   public ResourceKey<Level> getInfoDimension() {
      return this.dimension();
   }

   @Override
   public int getInfoContainerSize() {
      return this.containerSize();
   }

   @Nullable
   @Override
   public NonNullList<ItemStack> getInfoReferenceInventory() {
      return this.customInventory();
   }

   @Override
   public boolean isInfoReferenceInventory() {
      return this.customInventory() != null && !this.customInventory().isEmpty();
   }

   @Nullable
   @Override
   public ResourceKey<LootTable> getInfoLootTable() {
      return this.table();
   }

   @Override
   public long getInfoLootSeed() {
      return this.seed();
   }

   @Override
   public Level getInfoLevel() {
      return this.getDefaultLevel();
   }
}
