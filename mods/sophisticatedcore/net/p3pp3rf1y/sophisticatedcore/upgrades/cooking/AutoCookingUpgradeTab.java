package net.p3pp3rf1y.sophisticatedcore.upgrades.cooking;

import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicControl;

public abstract class AutoCookingUpgradeTab<R extends AbstractCookingRecipe, W extends AutoCookingUpgradeWrapper<W, ?, R>>
   extends UpgradeSettingsTab<AutoCookingUpgradeContainer<R, W>> {
   private final FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>> inputFilterLogicControl;
   private final FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>> fuelFilterLogicControl;
   private final CookingLogicControl<R> cookingLogicControl;

   protected AutoCookingUpgradeTab(
      AutoCookingUpgradeContainer<R, W> upgradeContainer,
      Position position,
      StorageScreenBase<?> screen,
      String tabLabel,
      String closedTooltip,
      int inputFilterSlotsPerRow,
      int fuelFilterSlotsPerRow
   ) {
      super(
         upgradeContainer, position, screen, TranslationHelper.INSTANCE.translUpgrade(tabLabel), TranslationHelper.INSTANCE.translUpgradeTooltip(closedTooltip)
      );
      this.inputFilterLogicControl = this.addHideableChild(
         new FilterLogicControl.Advanced(
            screen, new Position(this.x + 3, this.y + 24), this.getContainer().getInputFilterLogicContainer(), inputFilterSlotsPerRow
         )
      );
      this.cookingLogicControl = this.addHideableChild(
         new CookingLogicControl<>(new Position(this.x + 3, this.y + 84), this.getContainer().getCookingLogicContainer())
      );
      this.fuelFilterLogicControl = this.addHideableChild(
         new FilterLogicControl<>(screen, new Position(this.x + 3, this.y + 142), this.getContainer().getFuelFilterLogicContainer(), fuelFilterSlotsPerRow)
      );
   }

   @Override
   protected void moveSlotsToTab() {
      this.inputFilterLogicControl.moveSlotsToView();
      this.cookingLogicControl.moveSlotsToView(this.screen.getGuiLeft(), this.screen.getGuiTop());
      this.fuelFilterLogicControl.moveSlotsToView();
   }

   public static class AutoBlastingUpgradeTab extends AutoCookingUpgradeTab<BlastingRecipe, AutoCookingUpgradeWrapper.AutoBlastingUpgradeWrapper> {
      public AutoBlastingUpgradeTab(
         AutoCookingUpgradeContainer<BlastingRecipe, AutoCookingUpgradeWrapper.AutoBlastingUpgradeWrapper> upgradeContainer,
         Position position,
         StorageScreenBase<?> screen,
         int inputFilterSlotsPerRow,
         int fuelFilterSlotsPerRow
      ) {
         super(upgradeContainer, position, screen, "auto_blasting", "auto_blasting", inputFilterSlotsPerRow, fuelFilterSlotsPerRow);
      }
   }

   public static class AutoSmeltingUpgradeTab extends AutoCookingUpgradeTab<SmeltingRecipe, AutoCookingUpgradeWrapper.AutoSmeltingUpgradeWrapper> {
      public AutoSmeltingUpgradeTab(
         AutoCookingUpgradeContainer<SmeltingRecipe, AutoCookingUpgradeWrapper.AutoSmeltingUpgradeWrapper> upgradeContainer,
         Position position,
         StorageScreenBase<?> screen,
         int inputFilterSlotsPerRow,
         int fuelFilterSlotsPerRow
      ) {
         super(upgradeContainer, position, screen, "auto_smelting", "auto_smelting", inputFilterSlotsPerRow, fuelFilterSlotsPerRow);
      }
   }

   public static class AutoSmokingUpgradeTab extends AutoCookingUpgradeTab<SmokingRecipe, AutoCookingUpgradeWrapper.AutoSmokingUpgradeWrapper> {
      public AutoSmokingUpgradeTab(
         AutoCookingUpgradeContainer<SmokingRecipe, AutoCookingUpgradeWrapper.AutoSmokingUpgradeWrapper> upgradeContainer,
         Position position,
         StorageScreenBase<?> screen,
         int inputFilterSlotsPerRow,
         int fuelFilterSlotsPerRow
      ) {
         super(upgradeContainer, position, screen, "auto_smoking", "auto_smoking", inputFilterSlotsPerRow, fuelFilterSlotsPerRow);
      }
   }
}
