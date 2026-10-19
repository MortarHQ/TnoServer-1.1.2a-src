package net.p3pp3rf1y.sophisticatedbackpacks.common.gui;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackAccessLogger;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlockEntity;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.UUIDDeduplicator;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedbackpacks.network.BackpackContentsPayload;
import net.p3pp3rf1y.sophisticatedcore.common.gui.ISyncedContainer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SophisticatedMenuProvider;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeHandler;
import net.p3pp3rf1y.sophisticatedcore.util.NoopStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

public class BackpackContainer extends StorageContainerMenuBase<IBackpackWrapper> implements ISyncedContainer {
   private final BackpackContext backpackContext;

   public BackpackContainer(int windowId, Player player, BackpackContext backpackContext) {
      super(
         ModItems.BACKPACK_CONTAINER_TYPE.get(),
         windowId,
         player,
         backpackContext.getBackpackWrapper(player),
         backpackContext.getParentBackpackWrapper(player).orElse(NoopStorageWrapper.INSTANCE),
         backpackContext.getBackpackSlotIndex(),
         backpackContext.shouldLockBackpackSlot(player)
      );
      this.backpackContext = backpackContext;
      ((IBackpackWrapper)this.storageWrapper)
         .getContentsUuid()
         .ifPresent(
            backpackUuid -> {
               ItemStack backpack = ((IBackpackWrapper)this.storageWrapper).getBackpack();
               BackpackAccessLogger.logPlayerAccess(
                  player,
                  backpack.getItem(),
                  backpackUuid,
                  backpack.getHoverName().getString(),
                  ((IBackpackWrapper)this.storageWrapper).getMainColor(),
                  ((IBackpackWrapper)this.storageWrapper).getAccentColor(),
                  ((IBackpackWrapper)this.storageWrapper).getColumnsTaken()
               );
               if (!player.level().isClientSide()) {
                  UUIDDeduplicator.checkForDuplicateBackpacksAndRemoveTheirUUID(player, backpackUuid, ((IBackpackWrapper)this.storageWrapper).getBackpack());
               }
            }
         );
      if (backpackContext.shouldSaveAfterOpen()) {
         backpackContext.saveBackpackStack();
      }

      if (!player.level().isClientSide) {
         this.getBlockPosition()
            .flatMap(pos -> WorldHelper.getBlockEntity(player.level(), pos, BackpackBlockEntity.class))
            .ifPresent(backpackBlockEntity -> backpackBlockEntity.startOpen(player));
      }
   }

   public Optional<BlockPos> getBlockPosition() {
      BackpackContext.ContextType type = this.backpackContext.getType();
      return type != BackpackContext.ContextType.BLOCK_BACKPACK && type != BackpackContext.ContextType.BLOCK_SUB_BACKPACK
         ? Optional.empty()
         : Optional.of(this.backpackContext.getBackpackPosition(this.player));
   }

   public Optional<Entity> getEntity() {
      return this.backpackContext.getOwnerPlayer(this.player);
   }

   protected void sendStorageSettingsToClient() {
      if (!this.player.level().isClientSide) {
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

   protected StorageContainerMenuBase<IBackpackWrapper>.StorageUpgradeSlot instantiateUpgradeSlot(UpgradeHandler upgradeHandler, int slotIndex) {
      return new BackpackContainer.BackpackUpgradeSlot(upgradeHandler, slotIndex);
   }

   public boolean stillValid(Player player) {
      return this.backpackContext.canInteractWith(player);
   }

   public void removed(Player player) {
      if (!player.level().isClientSide) {
         this.getBlockPosition()
            .flatMap(pos -> WorldHelper.getBlockEntity(player.level(), pos, BackpackBlockEntity.class))
            .ifPresent(backpackBlockEntity -> backpackBlockEntity.stopOpen(player));
      }

      super.removed(player);
   }

   public static BackpackContainer fromBuffer(int windowId, Inventory playerInventory, FriendlyByteBuf buffer) {
      return new BackpackContainer(windowId, playerInventory.player, BackpackContext.fromBuffer(buffer, playerInventory.player.level()));
   }

   public BackpackContext getBackpackContext() {
      return this.backpackContext;
   }

   public void openSettings() {
      if (this.isClientSide()) {
         this.sendToServer(data -> data.putString("action", "openSettings"));
      } else {
         this.player
            .openMenu(
               new SophisticatedMenuProvider(
                  (w, p, pl) -> new BackpackSettingsContainerMenu(w, pl, this.backpackContext),
                  Component.translatable(SBPTranslationHelper.INSTANCE.translGui("settings.title")),
                  false
               ),
               this.backpackContext::toBuffer
            );
      }
   }

   protected boolean storageItemHasChanged() {
      return this.backpackContext.getBackpackWrapper(this.player) != this.storageWrapper;
   }

   public boolean detectSettingsChangeAndReload() {
      return ((IBackpackWrapper)this.storageWrapper).getContentsUuid().map(uuid -> {
         BackpackStorage storage = BackpackStorage.get();
         if (storage.removeUpdatedBackpackSettingsFlag(uuid)) {
            ((IBackpackWrapper)this.storageWrapper).getSettingsHandler().reloadFrom(storage.getOrCreateBackpackContents(uuid));
            this.refreshInventorySlotsIfNeeded();
            return true;
         } else {
            return false;
         }
      }).orElse(false);
   }

   protected boolean shouldSlotItemBeDroppedFromStorage(Slot slot) {
      return slot.getItem().getItem() instanceof BackpackItem && !((IBackpackWrapper)this.storageWrapper).getInventoryHandler().isItemValid(0, slot.getItem());
   }

   public class BackpackUpgradeSlot extends StorageContainerMenuBase<IBackpackWrapper>.StorageUpgradeSlot {
      public BackpackUpgradeSlot(UpgradeHandler upgradeHandler, int slotIndex) {
         super(BackpackContainer.this, upgradeHandler, slotIndex);
      }

      protected void onUpgradeChanged() {
         super.onUpgradeChanged();
         BackpackContainer.this.backpackContext.onUpgradeChanged(BackpackContainer.this.player);
      }
   }
}
