package net.p3pp3rf1y.sophisticatedcore.upgrades.compacting;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class CompactingUpgradeContainer extends UpgradeContainerBase<CompactingUpgradeWrapper, CompactingUpgradeContainer> {
   private static final String DATA_SHOULD_WORKD_IN_GUI = "shouldWorkdInGUI";
   private final FilterLogicContainer<FilterLogic> filterLogicContainer = new FilterLogicContainer<>(this.upgradeWrapper::getFilterLogic, this, this.slots::add);
   private static final String DATA_SHOULD_COMPACT_NON_UNCRAFTABLE = "shouldCompactNonUncraftable";

   public CompactingUpgradeContainer(
      Player player, int containerId, CompactingUpgradeWrapper wrapper, UpgradeContainerType<CompactingUpgradeWrapper, CompactingUpgradeContainer> type
   ) {
      super(player, containerId, wrapper, type);
   }

   public FilterLogicContainer<FilterLogic> getFilterLogicContainer() {
      return this.filterLogicContainer;
   }

   public void setCompactNonUncraftable(boolean shouldCompactNonUncraftable) {
      this.upgradeWrapper.setCompactNonUncraftable(shouldCompactNonUncraftable);
      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "shouldCompactNonUncraftable", shouldCompactNonUncraftable));
   }

   public boolean shouldCompactNonUncraftable() {
      return this.upgradeWrapper.shouldCompactNonUncraftable();
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("shouldCompactNonUncraftable")) {
         this.setCompactNonUncraftable(data.getBoolean("shouldCompactNonUncraftable"));
      } else if (data.contains("shouldWorkdInGUI")) {
         this.setShouldWorkdInGUI(data.getBoolean("shouldWorkdInGUI"));
      } else if (this.filterLogicContainer.handlePacket(data)) {
         this.upgradeWrapper.resetFullSlotInfo();
      }
   }

   public void setShouldWorkdInGUI(boolean shouldWorkdInGUI) {
      this.upgradeWrapper.setShouldWorkdInGUI(shouldWorkdInGUI);
      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "shouldWorkdInGUI", shouldWorkdInGUI));
   }

   public boolean shouldWorkInGUI() {
      return this.upgradeWrapper.shouldWorkInGUI();
   }
}
