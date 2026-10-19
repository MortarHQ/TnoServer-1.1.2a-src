package noobanidus.mods.lootr.common.api.data;

import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.IClientOpeners;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.PlayerContext;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.blockentity.RandomizableContainerBlockEntityLootrInfoProvider;
import noobanidus.mods.lootr.common.api.data.entity.AbstractMinecartContainerLootrInfoProvider;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import org.jetbrains.annotations.Nullable;

public interface ILootrInfoProvider extends ILootrInfo, IClientOpeners {
   static ILootrInfoProvider of(BlockPos pos, Level level) {
      if (level.isClientSide()) {
         return null;
      } else {
         BlockEntity blockEntity = level.getBlockEntity(pos);
         ILootrBlockEntity var5 = LootrAPI.resolveBlockEntity(blockEntity);
         if (var5 instanceof ILootrInfoProvider) {
            return var5;
         } else {
            return blockEntity instanceof ILootrInfoProvider provider ? provider : null;
         }
      }
   }

   static ILootrInfoProvider of(RandomizableContainerBlockEntity blockEntity, UUID id) {
      ILootrBlockEntity var4 = LootrAPI.resolveBlockEntity(blockEntity);
      if (var4 instanceof ILootrInfoProvider) {
         return var4;
      } else {
         return blockEntity instanceof ILootrInfoProvider provider
            ? provider
            : new RandomizableContainerBlockEntityLootrInfoProvider(blockEntity, id, ILootrInfo.generateInfoKey(id), null);
      }
   }

   static ILootrInfoProvider of(RandomizableContainerBlockEntity blockEntity, UUID id, NonNullList<ItemStack> customInventory) {
      ILootrBlockEntity var5 = LootrAPI.resolveBlockEntity(blockEntity);
      if (var5 instanceof ILootrInfoProvider) {
         return var5;
      } else {
         return blockEntity instanceof ILootrInfoProvider provider
            ? provider
            : new RandomizableContainerBlockEntityLootrInfoProvider(blockEntity, id, ILootrInfo.generateInfoKey(id), customInventory);
      }
   }

   static ILootrInfoProvider of(AbstractMinecartContainer minecart) {
      return minecart instanceof ILootrInfoProvider provider
         ? provider
         : new AbstractMinecartContainerLootrInfoProvider(minecart, ILootrInfo.generateInfoKey(minecart.getUUID()));
   }

   @Deprecated
   static ILootrInfoProvider of(
      UUID id,
      BlockPos pos,
      int containerSize,
      ResourceKey<LootTable> lootTable,
      long lootSeed,
      Component displayName,
      ResourceKey<Level> dimension,
      NonNullList<ItemStack> customInventory,
      @Deprecated ILootrInfo.LootrInfoType type,
      @Deprecated LootrBlockType blockType
   ) {
      return new CustomLootrInfoProvider(
         id, ILootrInfo.generateInfoKey(id), pos, containerSize, lootTable, lootSeed, displayName, dimension, customInventory, type, blockType, null
      );
   }

   static ILootrInfoProvider of(
      UUID id,
      BlockPos pos,
      int containerSize,
      ResourceKey<LootTable> lootTable,
      long lootSeed,
      Component displayName,
      ResourceKey<Level> dimension,
      NonNullList<ItemStack> customInventory,
      ILootrType type
   ) {
      return new CustomLootrInfoProvider(
         id, ILootrInfo.generateInfoKey(id), pos, containerSize, lootTable, lootSeed, displayName, dimension, customInventory, null, null, type
      );
   }

   default int getPhysicalOpenerCount() {
      return -1;
   }

   @Override
   default Set<UUID> getVisualOpeners() {
      ILootrSavedData data = LootrAPI.getData(this);
      return data != null ? data.getVisualOpeners() : null;
   }

   @Override
   default Set<UUID> getActualOpeners() {
      ILootrSavedData data = LootrAPI.getData(this);
      return data != null ? data.getActualOpeners() : null;
   }

   default boolean hasLootAvailable(ServerPlayer player) {
      ILootrInventory inventory = LootrAPI.getInventory(this, player);
      if (inventory == null) {
         return false;
      }

      for (int i = 0; i < inventory.getContainerSize(); i++) {
         if (!inventory.getItem(i).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   default IContainerTrigger getTrigger() {
      return null;
   }

   default void performTrigger(ServerPlayer player) {
      IContainerTrigger trigger = this.getTrigger();
      if (trigger != null && !this.hasServerOpened(player)) {
         trigger.trigger(player, this.getInfoUUID());
      }
   }

   default void performOpen(ServerPlayer player) {
   }

   default void performOpen() {
   }

   default void performClose(ServerPlayer player) {
   }

   default void performClose() {
   }

   default void performDecay() {
   }

   default void performRefresh() {
      ILootrSavedData data = LootrAPI.getData(this);
      if (data != null) {
         data.refresh();
         data.clearOpeners();
         NewTickingData.getRefreshData().clearTicking(LootrAPI.getServer(), this.getInfoUUID());
         this.markChanged();
      }
   }

   default void performUpdate(ServerPlayer player) {
   }

   default void performUpdate() {
   }

   @Override
   default void markDataChanged() {
      ILootrSavedData data = LootrAPI.getData(this);
      if (data != null) {
         data.markChanged();
      }
   }

   default boolean shouldDisplayParticles(PlayerContext context) {
      return !this.hasClientOpened(context);
   }

   default Vec3 getParticleCenter() {
      BlockPos pos = this.getInfoPos();
      return new Vec3(pos.getX(), pos.getY(), pos.getZ());
   }

   default double getParticleYOffset() {
      return 0.95;
   }

   default double[] getParticleXBounds() {
      return new double[]{0.25, 0.75};
   }

   default double[] getParticleZBounds() {
      return new double[]{0.25, 0.75};
   }

   default int getParticleColor() {
      return 16438858;
   }

   default int getParticleColor(PlayerContext context) {
      return this.getParticleColor();
   }
}
