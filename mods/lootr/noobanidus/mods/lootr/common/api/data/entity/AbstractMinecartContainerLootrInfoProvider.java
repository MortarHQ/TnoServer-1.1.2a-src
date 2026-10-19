package noobanidus.mods.lootr.common.api.data.entity;

import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record AbstractMinecartContainerLootrInfoProvider(AbstractMinecartContainer minecart, String cachedId) implements ILootrEntity {
   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return LootrBlockType.ENTITY;
   }

   @Override
   public ILootrType getInfoNewType() {
      return BuiltInLootrTypes.MINECART;
   }

   @NotNull
   @Override
   public Vec3 getInfoVec() {
      return this.minecart.position();
   }

   @NotNull
   @Override
   public UUID getInfoUUID() {
      return this.minecart.getUUID();
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
      return this.minecart.blockPosition();
   }

   @Override
   public ResourceKey<LootTable> getInfoLootTable() {
      return this.minecart.getLootTable();
   }

   @Nullable
   @Override
   public Component getInfoDisplayName() {
      return this.minecart.getName();
   }

   @NotNull
   @Override
   public ResourceKey<Level> getInfoDimension() {
      return this.minecart.level().dimension();
   }

   @Override
   public int getInfoContainerSize() {
      return this.minecart.getContainerSize();
   }

   @Nullable
   @Override
   public NonNullList<ItemStack> getInfoReferenceInventory() {
      return null;
   }

   @Override
   public boolean isInfoReferenceInventory() {
      return false;
   }

   @Override
   public long getInfoLootSeed() {
      return this.minecart.getLootTableSeed();
   }

   @Override
   public Level getInfoLevel() {
      return this.minecart.level();
   }

   @Override
   public Container getInfoContainer() {
      return this.minecart;
   }

   @Override
   public void markChanged() {
      this.minecart.setChanged();
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
