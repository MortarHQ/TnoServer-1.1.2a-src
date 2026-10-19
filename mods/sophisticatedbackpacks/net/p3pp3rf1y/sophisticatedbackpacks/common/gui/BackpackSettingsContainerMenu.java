package net.p3pp3rf1y.sophisticatedbackpacks.common.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedbackpacks.network.BackpackContentsPayload;
import net.p3pp3rf1y.sophisticatedbackpacks.settings.BackpackMainSettingsContainer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;

public class BackpackSettingsContainerMenu extends SettingsContainerMenu<IBackpackWrapper> implements IContextAwareContainer {
   private final BackpackContext backpackContext;
   private CompoundTag lastSettingsNbt = null;

   protected BackpackSettingsContainerMenu(int windowId, Player player, BackpackContext backpackContext) {
      super(ModItems.SETTINGS_CONTAINER_TYPE.get(), windowId, player, backpackContext.getBackpackWrapper(player));
      this.backpackContext = backpackContext;
   }

   public static BackpackSettingsContainerMenu fromBuffer(int windowId, Inventory playerInventory, FriendlyByteBuf buffer) {
      return new BackpackSettingsContainerMenu(windowId, playerInventory.player, BackpackContext.fromBuffer(buffer, playerInventory.player.level()));
   }

   public void detectSettingsChangeAndReload() {
      if (this.player.level().isClientSide) {
         ((IBackpackWrapper)this.storageWrapper).getContentsUuid().ifPresent(uuid -> {
            BackpackStorage storage = BackpackStorage.get();
            if (storage.removeUpdatedBackpackSettingsFlag(uuid)) {
               ((IBackpackWrapper)this.storageWrapper).getSettingsHandler().reloadFrom(storage.getOrCreateBackpackContents(uuid));
            }
         });
      }
   }

   public void broadcastChanges() {
      super.broadcastChanges();
      this.sendBackpackSettingsToClient();
   }

   private void sendBackpackSettingsToClient() {
      if (!this.player.level().isClientSide) {
         if (this.lastSettingsNbt == null || !this.lastSettingsNbt.equals(((IBackpackWrapper)this.storageWrapper).getSettingsHandler().getNbt())) {
            this.lastSettingsNbt = ((IBackpackWrapper)this.storageWrapper).getSettingsHandler().getNbt().copy();
            ((IBackpackWrapper)this.storageWrapper).getContentsUuid().ifPresent(uuid -> {
               CompoundTag settingsContents = new CompoundTag();
               CompoundTag settingsNbt = ((IBackpackWrapper)this.storageWrapper).getSettingsHandler().getNbt();
               if (!settingsNbt.isEmpty()) {
                  settingsContents.put("settings", settingsNbt);
                  if (this.player instanceof ServerPlayer serverPlayer) {
                     PacketDistributor.sendToPlayer(serverPlayer, new BackpackContentsPayload(uuid, settingsContents), new CustomPacketPayload[0]);
                  }
               }
            });
         }
      }
   }

   @Override
   public BackpackContext getBackpackContext() {
      return this.backpackContext;
   }

   static {
      SettingsContainerMenu.addFactory("backpackGlobal", BackpackMainSettingsContainer::new);
   }
}
