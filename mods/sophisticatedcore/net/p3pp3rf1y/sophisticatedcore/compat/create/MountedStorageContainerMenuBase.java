package net.p3pp3rf1y.sophisticatedcore.compat.create;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SophisticatedMenuProvider;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.util.NoopStorageWrapper;

public abstract class MountedStorageContainerMenuBase extends StorageContainerMenuBase<IStorageWrapper> {
   protected final WeakReference<AbstractContraptionEntity> contraptionEntity;
   protected final BlockPos localPos;
   @Nullable
   private CompoundTag lastSettingsNbt = null;
   protected final MountedStorageBase mountedStorage;

   public MountedStorageContainerMenuBase(
      MenuType<?> menuType,
      int containerId,
      Player player,
      IStorageWrapper parentStorageWrapper,
      int storageItemSlotIndex,
      boolean shouldLockStorageItemSlot,
      int contraptionEntityId,
      BlockPos localPos
   ) {
      this(
         menuType,
         containerId,
         player,
         getWrapper(player.level(), contraptionEntityId, localPos),
         parentStorageWrapper,
         storageItemSlotIndex,
         shouldLockStorageItemSlot,
         contraptionEntityId,
         localPos
      );
   }

   public MountedStorageContainerMenuBase(
      MenuType<?> menuType,
      int containerId,
      Player player,
      IStorageWrapper wrapper,
      IStorageWrapper parentStorageWrapper,
      int storageItemSlotIndex,
      boolean shouldLockStorageItemSlot,
      int contraptionEntityId,
      BlockPos localPos
   ) {
      super(menuType, containerId, player, wrapper, parentStorageWrapper, storageItemSlotIndex, shouldLockStorageItemSlot);
      if (player.level().getEntity(contraptionEntityId) instanceof AbstractContraptionEntity cEntity) {
         this.contraptionEntity = new WeakReference<>(cEntity);
         this.localPos = localPos;
         MountedStorageBase itemStorage = ContraptionHelper.getMountedStorage(cEntity, localPos);
         if (itemStorage == null) {
            throw new IllegalArgumentException("Incorrect storage type at " + localPos + " expected to find MountedStorageBase");
         }

         this.mountedStorage = itemStorage;
      } else {
         throw new IllegalArgumentException("Incorrect entity with id " + contraptionEntityId + " expected to find AbstractContraptionEntity");
      }
   }

   protected Optional<MountedStorageBase> getMountedStorage() {
      AbstractContraptionEntity c = this.contraptionEntity.get();
      return c == null ? Optional.empty() : Optional.ofNullable(ContraptionHelper.getMountedStorage(c, this.localPos));
   }

   private static IStorageWrapper getWrapper(Level level, int contraptionEntityId, BlockPos localPos) {
      if (level.getEntity(contraptionEntityId) instanceof AbstractContraptionEntity contraptionEntity) {
         MountedStorageBase itemStorage = ContraptionHelper.getMountedStorage(contraptionEntity, localPos);
         return itemStorage == null ? NoopStorageWrapper.INSTANCE : itemStorage.getStorageWrapper();
      } else {
         return NoopStorageWrapper.INSTANCE;
      }
   }

   public Optional<AbstractContraptionEntity> getContraptionEntity() {
      return Optional.ofNullable(this.contraptionEntity.get());
   }

   public BlockPos getLocalPos() {
      return this.localPos;
   }

   @Override
   public void removed(Player player) {
      super.removed(player);
      this.getContraptionEntity().ifPresent(c -> {
         Vec3 localPosVec = Vec3.atCenterOf(this.localPos);
         Vec3 newPos = c.toGlobalVector(localPosVec, 0.0F);
         MountedStorageBase mountedStorage = ContraptionHelper.getMountedStorage(c, this.localPos);
         if (mountedStorage != null) {
            mountedStorage.onClose(player, newPos);
         }
      });
   }

   @Override
   public Optional<BlockPos> getBlockPosition() {
      return Optional.empty();
   }

   @Override
   public Optional<Entity> getEntity() {
      return this.getContraptionEntity().map(c -> (Entity)c);
   }

   @Override
   public void openSettings() {
      if (this.isClientSide()) {
         this.sendToServer(data -> data.putString("action", "openSettings"));
      } else {
         this.getContraptionEntity()
            .ifPresent(
               c -> this.player
                  .openMenu(
                     new SophisticatedMenuProvider(
                        (w, p, pl) -> this.instantiateSettingsContainerMenu(w, pl, c.getId(), this.localPos),
                        Component.translatable(this.getSettingsTitleKey()),
                        false
                     ),
                     this::writeSettingsContainerMenuExtraData
                  )
            );
      }
   }

   protected abstract void writeSettingsContainerMenuExtraData(FriendlyByteBuf var1);

   protected abstract String getSettingsTitleKey();

   protected abstract MountedStorageSettingsContainerMenuBase instantiateSettingsContainerMenu(int var1, Player var2, int var3, BlockPos var4);

   @Override
   protected boolean storageItemHasChanged() {
      return false;
   }

   @Override
   public boolean detectSettingsChangeAndReload() {
      return this.player.level().isClientSide ? this.storageWrapper.getContentsUuid().map(uuid -> {
         MountedStorageData storage = MountedStorageData.get(uuid);
         if (storage.removeUpdatedStorageSettingsFlag(uuid)) {
            CompoundTag contents = storage.getContents();
            this.storageWrapper.getSettingsHandler().reloadFrom(this.getSettingsTag(contents));
            return true;
         } else {
            return false;
         }
      }).orElse(false) : false;
   }

   protected abstract CompoundTag getSettingsTag(CompoundTag var1);

   public boolean stillValid(Player player) {
      return this.getContraptionEntity().map(c -> {
         Vec3 localPosVec = Vec3.atCenterOf(this.localPos);
         Vec3 currentPos = c.toGlobalVector(localPosVec, 0.0F);
         return c.isAlive() && player.distanceToSqr(currentPos) < 64.0;
      }).orElse(false);
   }

   @Override
   protected void sendStorageSettingsToClient() {
      if (!this.player.level().isClientSide) {
         if (this.lastSettingsNbt == null || !this.lastSettingsNbt.equals(this.storageWrapper.getSettingsHandler().getNbt())) {
            this.lastSettingsNbt = this.storageWrapper.getSettingsHandler().getNbt().copy();
            this.storageWrapper.getContentsUuid().ifPresent(uuid -> {
               CompoundTag settingsContents = new CompoundTag();
               CompoundTag settingsNbt = this.storageWrapper.getSettingsHandler().getNbt();
               if (!settingsNbt.isEmpty()) {
                  settingsContents.put("settings", settingsNbt);
                  if (this.player instanceof ServerPlayer serverPlayer) {
                     PacketDistributor.sendToPlayer(serverPlayer, this.instantiateSettingsPayload(uuid, settingsContents), new CustomPacketPayload[0]);
                  }
               }
            });
         }
      }
   }

   protected abstract CustomPacketPayload instantiateSettingsPayload(UUID var1, CompoundTag var2);
}
