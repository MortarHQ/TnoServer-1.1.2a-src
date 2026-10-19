package net.p3pp3rf1y.sophisticatedcore.upgrades.pickup;

import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterControl;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilteredUpgradeContainer;

public class PickupUpgradeTab extends UpgradeSettingsTab<ContentsFilteredUpgradeContainer<PickupUpgradeWrapper>> {
   protected ContentsFilterControl filterLogicControl;

   protected PickupUpgradeTab(
      ContentsFilteredUpgradeContainer<PickupUpgradeWrapper> upgradeContainer,
      Position position,
      StorageScreenBase<?> screen,
      Component tabLabel,
      Component closedTooltip
   ) {
      super(upgradeContainer, position, screen, tabLabel, closedTooltip);
   }

   @Override
   protected void moveSlotsToTab() {
      this.filterLogicControl.moveSlotsToView();
   }

   public static class Advanced extends PickupUpgradeTab {
      public Advanced(
         ContentsFilteredUpgradeContainer<PickupUpgradeWrapper> upgradeContainer,
         Position position,
         StorageScreenBase<?> screen,
         int slotsPerRow,
         ButtonDefinition.Toggle<ContentsFilterType> contentsFilterButton
      ) {
         super(
            upgradeContainer,
            position,
            screen,
            TranslationHelper.INSTANCE.translUpgrade("advanced_pickup"),
            TranslationHelper.INSTANCE.translUpgradeTooltip("advanced_pickup")
         );
         this.filterLogicControl = this.addHideableChild(
            new ContentsFilterControl.Advanced(
               screen, new Position(this.x + 3, this.y + 24), this.getContainer().getFilterLogicContainer(), slotsPerRow, contentsFilterButton
            )
         );
      }
   }

   public static class Basic extends PickupUpgradeTab {
      public Basic(
         ContentsFilteredUpgradeContainer<PickupUpgradeWrapper> upgradeContainer,
         Position position,
         StorageScreenBase<?> screen,
         int slotsPerRow,
         ButtonDefinition.Toggle<ContentsFilterType> contentsFilterButton
      ) {
         super(
            upgradeContainer, position, screen, TranslationHelper.INSTANCE.translUpgrade("pickup"), TranslationHelper.INSTANCE.translUpgradeTooltip("pickup")
         );
         this.filterLogicControl = this.addHideableChild(
            new ContentsFilterControl.Basic(
               screen, new Position(this.x + 3, this.y + 24), this.getContainer().getFilterLogicContainer(), slotsPerRow, contentsFilterButton
            )
         );
      }
   }
}
