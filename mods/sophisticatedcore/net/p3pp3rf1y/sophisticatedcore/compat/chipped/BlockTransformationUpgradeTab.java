package net.p3pp3rf1y.sophisticatedcore.compat.chipped;

import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;

public class BlockTransformationUpgradeTab extends UpgradeSettingsTab<BlockTransformationUpgradeContainer> {
   private final BlockTransformationRecipeControl recipeControl;

   public BlockTransformationUpgradeTab(
      BlockTransformationUpgradeContainer upgradeContainer,
      Position position,
      StorageScreenBase<?> screen,
      ButtonDefinition.Toggle<Boolean> shiftClickTargetButton,
      String upgradeName
   ) {
      super(
         upgradeContainer,
         position,
         screen,
         TranslationHelper.INSTANCE.translUpgrade(upgradeName),
         TranslationHelper.INSTANCE.translUpgradeTooltip(upgradeName)
      );
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 3, this.y + 24),
            shiftClickTargetButton,
            button -> this.getContainer().setShiftClickIntoStorage(!this.getContainer().shouldShiftClickIntoStorage()),
            this.getContainer()::shouldShiftClickIntoStorage
         )
      );
      this.recipeControl = new BlockTransformationRecipeControl(screen, upgradeContainer.getRecipeContainer(), new Position(this.x + 3, this.y + 24));
      this.addHideableChild(this.recipeControl);
   }

   @Override
   protected void moveSlotsToTab() {
      this.recipeControl.moveSlotsToView();
   }
}
