package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class InceptionUpgradeContainer extends UpgradeContainerBase<InceptionUpgradeWrapper, InceptionUpgradeContainer> {
   private static final String DATA_INVENTORY_ORDER = "inventoryOrder";

   public InceptionUpgradeContainer(
      Player player,
      int upgradeContainerId,
      InceptionUpgradeWrapper upgradeWrapper,
      UpgradeContainerType<InceptionUpgradeWrapper, InceptionUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);
   }

   public void handlePacket(CompoundTag data) {
      if (data.contains("inventoryOrder")) {
         this.setInventoryOrder(InventoryOrder.fromName(data.getString("inventoryOrder")));
      }
   }

   public InventoryOrder getInventoryOrder() {
      return ((InceptionUpgradeWrapper)this.upgradeWrapper).getInventoryOrder();
   }

   public void setInventoryOrder(InventoryOrder inventoryOrder) {
      ((InceptionUpgradeWrapper)this.upgradeWrapper).setInventoryOrder(inventoryOrder);
      this.sendDataToServer(() -> NBTHelper.putEnumConstant(new CompoundTag(), "inventoryOrder", inventoryOrder));
   }
}
