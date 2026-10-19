package net.p3pp3rf1y.sophisticatedcore.compat.create;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;

public abstract class MountedStorageSettingsContainerMenuBase extends SettingsContainerMenu<IStorageWrapper> {
   private final int contraptionEntityId;
   private final BlockPos localPos;
   private CompoundTag lastSettingsNbt = null;

   public MountedStorageSettingsContainerMenuBase(
      MenuType<?> menuType, int windowId, Player player, IStorageWrapper storageWrapper, int contraptionEntityId, BlockPos localPos
   ) {
      super(menuType, windowId, player, storageWrapper);
      this.contraptionEntityId = contraptionEntityId;
      this.localPos = localPos;
   }

   @Override
   public void detectSettingsChangeAndReload() {
      if (this.player.level().isClientSide) {
         this.storageWrapper.getContentsUuid().ifPresent(uuid -> this.updateFromContents(uuid));
      }
   }

   protected abstract void updateFromContents(UUID var1);

   protected abstract CompoundTag getSettingsTag(CompoundTag var1);

   public int getContraptionEntityId() {
      return this.contraptionEntityId;
   }

   private void sendStorageSettingsToClient() {
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

   @Override
   public void broadcastChanges() {
      super.broadcastChanges();
      this.sendStorageSettingsToClient();
   }

   public BlockPos getLocalPos() {
      return this.localPos;
   }

   @Override
   public BlockPos getBlockPosition() {
      return this.localPos;
   }
}
