package net.p3pp3rf1y.sophisticatedcore.upgrades.compacting;

import java.util.Map;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicControl;

public abstract class CompactingUpgradeTab extends UpgradeSettingsTab<CompactingUpgradeContainer> {
   public static final ButtonDefinition.Toggle<Boolean> COMPACT_UNCRAFTABLE = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         false,
         GuiHelper.getButtonStateData(
            new UV(80, 48), TranslationHelper.INSTANCE.translUpgradeButton("compact_only_uncraftable"), Dimension.SQUARE_16, new Position(1, 1)
         ),
         true,
         GuiHelper.getButtonStateData(
            new UV(80, 32), TranslationHelper.INSTANCE.translUpgradeButton("compact_anything"), Dimension.SQUARE_16, new Position(1, 1)
         )
      )
   );
   protected FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>> filterLogicControl;

   protected CompactingUpgradeTab(
      CompactingUpgradeContainer container, Position position, StorageScreenBase<?> screen, Component tabLabel, Component closedTooltip
   ) {
      super(container, position, screen, tabLabel, closedTooltip);
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 3, this.y + 24),
            COMPACT_UNCRAFTABLE,
            button -> this.getContainer().setCompactNonUncraftable(!this.getContainer().shouldCompactNonUncraftable()),
            this.getContainer()::shouldCompactNonUncraftable
         )
      );
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 21, this.y + 24),
            ButtonDefinitions.WORK_IN_GUI,
            button -> this.getContainer().setShouldWorkdInGUI(!this.getContainer().shouldWorkInGUI()),
            this.getContainer()::shouldWorkInGUI
         )
      );
   }

   @Override
   protected void moveSlotsToTab() {
      this.filterLogicControl.moveSlotsToView();
   }

   public static class Advanced extends CompactingUpgradeTab {
      public Advanced(CompactingUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, int slotsPerRow) {
         super(
            upgradeContainer,
            position,
            screen,
            TranslationHelper.INSTANCE.translUpgrade("advanced_compacting"),
            TranslationHelper.INSTANCE.translUpgradeTooltip("advanced_compacting")
         );
         this.filterLogicControl = this.addHideableChild(
            new FilterLogicControl.Advanced(screen, new Position(this.x + 3, this.y + 44), this.getContainer().getFilterLogicContainer(), slotsPerRow)
         );
      }
   }

   public static class Basic extends CompactingUpgradeTab {
      public Basic(CompactingUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, int slotsPerRow) {
         super(
            upgradeContainer,
            position,
            screen,
            TranslationHelper.INSTANCE.translUpgrade("compacting"),
            TranslationHelper.INSTANCE.translUpgradeTooltip("compacting")
         );
         this.filterLogicControl = this.addHideableChild(
            new FilterLogicControl.Basic(screen, new Position(this.x + 3, this.y + 44), this.getContainer().getFilterLogicContainer(), slotsPerRow)
         );
      }
   }
}
