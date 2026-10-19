package net.p3pp3rf1y.sophisticatedcore.upgrades.magnet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterLogicContainer;

public class MagnetUpgradeContainer extends UpgradeContainerBase<MagnetUpgradeWrapper, MagnetUpgradeContainer> {
   private static final String DATA_PICKUP_ITEMS = "pickupItems";
   private static final String DATA_PICKUP_XP = "pickupXp";
   private final ContentsFilterLogicContainer filterLogicContainer = new ContentsFilterLogicContainer(
      () -> this.upgradeWrapper.getFilterLogic(), this, this.slots::add
   );

   public MagnetUpgradeContainer(
      Player player, int containerId, MagnetUpgradeWrapper wrapper, UpgradeContainerType<MagnetUpgradeWrapper, MagnetUpgradeContainer> type
   ) {
      super(player, containerId, wrapper, type);
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("pickupItems")) {
         this.setPickupItems(data.getBoolean("pickupItems"));
      } else if (data.contains("pickupXp")) {
         this.setPickupXp(data.getBoolean("pickupXp"));
      }

      this.filterLogicContainer.handlePacket(data);
   }

   public ContentsFilterLogicContainer getFilterLogicContainer() {
      return this.filterLogicContainer;
   }

   public void setPickupItems(boolean pickupItems) {
      this.upgradeWrapper.setPickupItems(pickupItems);
      this.sendBooleanToServer("pickupItems", pickupItems);
   }

   public boolean shouldPickupItems() {
      return this.upgradeWrapper.shouldPickupItems();
   }

   public void setPickupXp(boolean pickupXp) {
      this.upgradeWrapper.setPickupXp(pickupXp);
      this.sendBooleanToServer("pickupXp", pickupXp);
   }

   public boolean shouldPickupXp() {
      return this.upgradeWrapper.shouldPickupXp();
   }
}
