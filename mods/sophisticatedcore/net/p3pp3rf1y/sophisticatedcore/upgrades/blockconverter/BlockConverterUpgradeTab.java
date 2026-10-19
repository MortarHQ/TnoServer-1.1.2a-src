package net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public abstract class BlockConverterUpgradeTab<R extends SingleItemRecipe, RC extends BlockConverterRecipeContainer<R, ?, RC, C>, C extends BlockConverterUpgradeContainer<R, ?, C, RC>>
   extends UpgradeSettingsTab<C> {
   private final BlockConverterRecipeControl<R, RC> recipeControl;

   public BlockConverterUpgradeTab(
      C upgradeContainer,
      Position position,
      StorageScreenBase<?> screen,
      Component tabLabel,
      Component closedTooltip,
      ButtonDefinition.Toggle<Boolean> shiftClickTargetButton
   ) {
      super(upgradeContainer, position, screen, tabLabel, closedTooltip);
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 3, this.y + 24),
            shiftClickTargetButton,
            button -> this.getContainer().setShiftClickIntoStorage(!this.getContainer().shouldShiftClickIntoStorage()),
            this.getContainer()::shouldShiftClickIntoStorage
         )
      );
      this.recipeControl = this.createRecipeControl(screen, upgradeContainer.getRecipeContainer(), new Position(this.x + 3, this.y + 24));
      this.addHideableChild(this.recipeControl);
   }

   protected abstract BlockConverterRecipeControl<R, RC> createRecipeControl(StorageScreenBase<?> var1, RC var2, Position var3);

   @Override
   protected void moveSlotsToTab() {
      this.recipeControl.moveSlotsToView();
   }
}
