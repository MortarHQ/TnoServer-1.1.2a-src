package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class JukeboxUpgradeContainer extends UpgradeContainerBase<JukeboxUpgradeWrapper, JukeboxUpgradeContainer> {
   private static final String ACTION_DATA = "action";

   public JukeboxUpgradeContainer(
      final Player player,
      int upgradeContainerId,
      final JukeboxUpgradeWrapper upgradeWrapper,
      UpgradeContainerType<JukeboxUpgradeWrapper, JukeboxUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);

      for (int slot = 0; slot < upgradeWrapper.getDiscInventory().getSlots(); slot++) {
         this.slots.add(new SlotItemHandler(upgradeWrapper.getDiscInventory(), slot, -100, -100) {
            public void setChanged() {
               super.setChanged();
               if (upgradeWrapper.isPlaying() && this.getSlotIndex() == upgradeWrapper.getDiscSlotActive()) {
                  upgradeWrapper.stop(player);
               }
            }
         });
      }
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("action")) {
         String actionName = data.getString("action");
         switch (actionName) {
            case "play":
               if (this.player.containerMenu instanceof StorageContainerMenuBase<?> storageContainerMenu) {
                  storageContainerMenu.getBlockPosition()
                     .ifPresentOrElse(
                        pos -> this.upgradeWrapper.play(this.player.level(), pos),
                        () -> this.upgradeWrapper.play(storageContainerMenu.getEntity().orElse(this.player))
                     );
               }
               break;
            case "stop":
               this.upgradeWrapper.stop(this.player);
               break;
            case "next":
               this.upgradeWrapper.next();
               break;
            case "previous":
               this.upgradeWrapper.previous();
         }
      }

      if (data.contains("shuffle")) {
         this.upgradeWrapper.setShuffleEnabled(data.getBoolean("shuffle"));
      }

      if (data.contains("repeat")) {
         NBTHelper.getEnumConstant(data, "repeat", RepeatMode::fromName).ifPresent(this.upgradeWrapper::setRepeatMode);
      }
   }

   public void play() {
      this.sendDataToServer(() -> NBTHelper.putString(new CompoundTag(), "action", "play"));
   }

   public void stop() {
      this.sendDataToServer(() -> NBTHelper.putString(new CompoundTag(), "action", "stop"));
   }

   public void next() {
      this.sendDataToServer(() -> NBTHelper.putString(new CompoundTag(), "action", "next"));
   }

   public void previous() {
      this.sendDataToServer(() -> NBTHelper.putString(new CompoundTag(), "action", "previous"));
   }

   public boolean isShuffleEnabled() {
      return this.upgradeWrapper.isShuffleEnabled();
   }

   public void toggleShuffle() {
      boolean newValue = !this.upgradeWrapper.isShuffleEnabled();
      this.upgradeWrapper.setShuffleEnabled(newValue);
      this.sendBooleanToServer("shuffle", newValue);
   }

   public RepeatMode getRepeatMode() {
      return this.upgradeWrapper.getRepeatMode();
   }

   public void toggleRepeat() {
      RepeatMode newValue = this.upgradeWrapper.getRepeatMode().next();
      this.upgradeWrapper.setRepeatMode(newValue);
      this.sendDataToServer(() -> NBTHelper.putEnumConstant(new CompoundTag(), "repeat", newValue));
   }

   public Optional<Slot> getDiscSlotActive() {
      int discSlotActive = this.upgradeWrapper.getDiscSlotActive();
      return discSlotActive > -1 ? Optional.of(this.slots.get(discSlotActive)) : Optional.empty();
   }

   public long getDiscFinishTime() {
      return this.upgradeWrapper.getDiscFinishTime();
   }
}
