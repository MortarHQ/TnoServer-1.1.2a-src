package net.p3pp3rf1y.sophisticatedcore.client.gui;

import java.util.Map.Entry;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;

public class UpgradeSettingsTabControl extends SettingsTabControl<StorageScreenBase<?>, UpgradeSettingsTab<?>> {
   public UpgradeSettingsTabControl(Position position, StorageScreenBase<?> screen, String storageSettingsTabTooltip) {
      super(position);
      this.addChild(new StorageSettingsTab(new Position(this.x, this.getTopY()), screen, storageSettingsTabTooltip));

      for (Entry<Integer, UpgradeContainerBase<?, ?>> entry : ((StorageContainerMenuBase)screen.getMenu()).getUpgradeContainers().entrySet()) {
         this.addSettingsTab(
               () -> ((StorageContainerMenuBase)screen.getMenu()).setOpenTabId(entry.getKey()),
               () -> ((StorageContainerMenuBase)screen.getMenu()).removeOpenTabId(),
               UpgradeGuiManager.getTab(entry.getValue(), new Position(this.x, this.getTopY()), screen)
            )
            .onAfterInit();
      }
   }

   public boolean slotIsNotCoveredAt(Slot slot, double mouseX, double mouseY) {
      for (Tab tab : this.children) {
         if (tab instanceof UpgradeSettingsTab<?> upgradeSettingsTab && !upgradeSettingsTab.slotIsNotCoveredAt(slot, mouseX, mouseY)) {
            return false;
         }
      }

      return true;
   }

   public void tick() {
      this.children.forEach(Tab::tick);
   }
}
