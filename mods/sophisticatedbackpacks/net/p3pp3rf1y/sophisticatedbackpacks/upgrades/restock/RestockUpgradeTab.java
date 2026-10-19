package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.restock;

import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition.Toggle;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterControl;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilteredUpgradeContainer;

public abstract class RestockUpgradeTab extends UpgradeSettingsTab<ContentsFilteredUpgradeContainer<RestockUpgradeWrapper>> {
   protected ContentsFilterControl filterLogicControl;

   protected RestockUpgradeTab(
      ContentsFilteredUpgradeContainer<RestockUpgradeWrapper> upgradeContainer,
      Position position,
      StorageScreenBase<?> screen,
      Component tabLabel,
      Component closedTooltip
   ) {
      super(upgradeContainer, position, screen, tabLabel, closedTooltip);
   }

   protected void moveSlotsToTab() {
      this.filterLogicControl.moveSlotsToView();
   }

   public static class Advanced extends RestockUpgradeTab {
      public Advanced(
         ContentsFilteredUpgradeContainer<RestockUpgradeWrapper> upgradeContainer,
         Position position,
         StorageScreenBase<?> screen,
         Toggle<ContentsFilterType> contentsFilterButton
      ) {
         super(
            upgradeContainer,
            position,
            screen,
            SBPTranslationHelper.INSTANCE.translUpgrade("advanced_restock", new Object[0]),
            SBPTranslationHelper.INSTANCE.translUpgradeTooltip("advanced_restock")
         );
         this.filterLogicControl = (ContentsFilterControl)this.addHideableChild(
            new net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterControl.Advanced(
               screen,
               new Position(this.x + 3, this.y + 24),
               ((ContentsFilteredUpgradeContainer)this.getContainer()).getFilterLogicContainer(),
               (Integer)Config.SERVER.advancedRestockUpgrade.slotsInRow.get(),
               contentsFilterButton
            )
         );
      }
   }

   public static class Basic extends RestockUpgradeTab {
      public Basic(
         ContentsFilteredUpgradeContainer<RestockUpgradeWrapper> upgradeContainer,
         Position position,
         StorageScreenBase<?> screen,
         Toggle<ContentsFilterType> contentsFilterButton
      ) {
         super(
            upgradeContainer,
            position,
            screen,
            SBPTranslationHelper.INSTANCE.translUpgrade("restock", new Object[0]),
            SBPTranslationHelper.INSTANCE.translUpgradeTooltip("restock")
         );
         this.filterLogicControl = (ContentsFilterControl)this.addHideableChild(
            new net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterControl.Basic(
               screen,
               new Position(this.x + 3, this.y + 24),
               ((ContentsFilteredUpgradeContainer)this.getContainer()).getFilterLogicContainer(),
               (Integer)Config.SERVER.restockUpgrade.slotsInRow.get(),
               contentsFilterButton
            )
         );
      }
   }
}
