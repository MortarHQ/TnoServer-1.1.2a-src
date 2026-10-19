package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.toolswapper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class ToolSwapperUpgradeContainer extends UpgradeContainerBase<ToolSwapperUpgradeWrapper, ToolSwapperUpgradeContainer> {
   private static final String DATA_SHOULD_SWAP_WEAPON = "shouldSwapWeapon";
   private static final String DATA_TOOL_SWAP_MODE = "toolSwapMode";
   private final FilterLogicContainer<FilterLogic> filterLogicContainer;

   public ToolSwapperUpgradeContainer(
      Player player,
      int upgradeContainerId,
      ToolSwapperUpgradeWrapper upgradeWrapper,
      UpgradeContainerType<ToolSwapperUpgradeWrapper, ToolSwapperUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);
      this.filterLogicContainer = new FilterLogicContainer(upgradeWrapper::getFilterLogic, this, this.slots::add);
   }

   public void handlePacket(CompoundTag data) {
      if (data.contains("shouldSwapWeapon")) {
         this.setSwapWeapon(data.getBoolean("shouldSwapWeapon"));
      } else if (data.contains("toolSwapMode")) {
         this.setToolSwapMode(ToolSwapMode.fromName(data.getString("toolSwapMode")));
      } else {
         this.filterLogicContainer.handlePacket(data);
      }
   }

   public FilterLogicContainer<FilterLogic> getFilterLogicContainer() {
      return this.filterLogicContainer;
   }

   public void setSwapWeapon(boolean shouldSwapWeapon) {
      ((ToolSwapperUpgradeWrapper)this.upgradeWrapper).setSwapWeapon(shouldSwapWeapon);
      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "shouldSwapWeapon", shouldSwapWeapon));
   }

   public boolean shouldSwapWeapon() {
      return ((ToolSwapperUpgradeWrapper)this.upgradeWrapper).shouldSwapWeapon();
   }

   public void setToolSwapMode(ToolSwapMode toolSwapMode) {
      ((ToolSwapperUpgradeWrapper)this.upgradeWrapper).setToolSwapMode(toolSwapMode);
      this.sendDataToServer(() -> NBTHelper.putEnumConstant(new CompoundTag(), "toolSwapMode", toolSwapMode));
   }

   public ToolSwapMode getToolSwapMode() {
      return ((ToolSwapperUpgradeWrapper)this.upgradeWrapper).getToolSwapMode();
   }
}
