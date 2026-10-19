package noobanidus.mods.lootr.common.api.data;

import java.util.Set;
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

public record CustomLootrInfoProvider(
   UUID id,
   String cachedKey,
   BlockPos pos,
   int containerSize,
   ResourceKey<LootTable> lootTable,
   long lootSeed,
   Component displayName,
   ResourceKey<Level> dimension,
   NonNullList<ItemStack> customInventory,
   @Deprecated @Nullable ILootrInfo.LootrInfoType type,
   @Deprecated @Nullable LootrBlockType blockType,
   ILootrType newType
) implements ILootrInfoProvider {
   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return this.blockType();
   }

   @Deprecated
   @Override
   public ILootrInfo.LootrInfoType getInfoType() {
      return this.type();
   }

   @Override
   public ILootrType getInfoNewType() {
      return this.newType();
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.id();
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
   public ResourceKey<LootTable> getInfoLootTable() {
      return this.lootTable();
   }

   @Nullable
   @Override
   public Component getInfoDisplayName() {
      return this.displayName();
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

   @Override
   public long getInfoLootSeed() {
      return this.lootSeed();
   }

   @Override
   public Level getInfoLevel() {
      return this.getDefaultLevel();
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

   @Override
   public void markChanged() {
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
