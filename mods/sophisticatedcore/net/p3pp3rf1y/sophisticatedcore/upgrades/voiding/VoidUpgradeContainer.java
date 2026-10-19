package net.p3pp3rf1y.sophisticatedcore.upgrades.voiding;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class VoidUpgradeContainer extends UpgradeContainerBase<VoidUpgradeWrapper, VoidUpgradeContainer> {
   private static final String DATA_SHOULD_WORKD_IN_GUI = "shouldWorkdInGUI";
   private static final String DATA_VOID_TYPE = "voidType";
   private final FilterLogicContainer<FilterLogic> filterLogicContainer = new FilterLogicContainer<>(this.upgradeWrapper::getFilterLogic, this, this.slots::add);

   public VoidUpgradeContainer(Player player, int containerId, VoidUpgradeWrapper wrapper, UpgradeContainerType<VoidUpgradeWrapper, VoidUpgradeContainer> type) {
      super(player, containerId, wrapper, type);
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("shouldWorkdInGUI")) {
         this.setShouldWorkdInGUI(data.getBoolean("shouldWorkdInGUI"));
      } else if (data.contains("voidType")) {
         this.setVoidType(VoidType.fromName(data.getString("voidType")));
      }

      this.filterLogicContainer.handlePacket(data);
   }

   public FilterLogicContainer<FilterLogic> getFilterLogicContainer() {
      return this.filterLogicContainer;
   }

   public void setShouldWorkdInGUI(boolean shouldWorkdInGUI) {
      this.upgradeWrapper.setShouldWorkdInGUI(shouldWorkdInGUI);
      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "shouldWorkdInGUI", shouldWorkdInGUI));
   }

   public void setVoidType(VoidType voidType) {
      this.upgradeWrapper.setVoidType(voidType);
      this.sendDataToServer(() -> NBTHelper.putEnumConstant(new CompoundTag(), "voidType", voidType));
   }

   public boolean shouldWorkInGUI() {
      return this.upgradeWrapper.shouldWorkInGUI();
   }

   public VoidType getVoidType() {
      return this.upgradeWrapper.getVoidType();
   }
}
