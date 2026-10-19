package net.p3pp3rf1y.sophisticatedbackpacks.compat.sawmill;

import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeGuiManager;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.compat.sawmill.SawmillUpgradeContainer;
import net.p3pp3rf1y.sophisticatedcore.compat.sawmill.SawmillUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.compat.sawmill.SawmillUpgradeItem.Wrapper;

public class SawmillCompatClient {
   private SawmillCompatClient() {
   }

   public static void registerUpgradeTab(UpgradeContainerType<Wrapper, SawmillUpgradeContainer> containerType) {
      UpgradeGuiManager.registerTab(
         containerType,
         (upgradeContainer, position, screen) -> new SawmillUpgradeTab(upgradeContainer, position, screen, SBPButtonDefinitions.SHIFT_CLICK_TARGET)
      );
   }
}
