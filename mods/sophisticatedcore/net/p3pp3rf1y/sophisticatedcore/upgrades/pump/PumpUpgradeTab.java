package net.p3pp3rf1y.sophisticatedcore.upgrades.pump;

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

public class PumpUpgradeTab extends UpgradeSettingsTab<PumpUpgradeContainer> {
   private static final ButtonDefinition.Toggle<Boolean> IS_INPUT = ButtonDefinitions.createToggleButtonDefinition(
      ButtonDefinitions.getBooleanStateData(
         GuiHelper.getButtonStateData(new UV(144, 0), TranslationHelper.INSTANCE.translUpgradeButton("pump_input"), Dimension.SQUARE_16, new Position(1, 1)),
         GuiHelper.getButtonStateData(new UV(160, 0), TranslationHelper.INSTANCE.translUpgradeButton("pump_output"), Dimension.SQUARE_16, new Position(1, 1))
      )
   );
   private static final ButtonDefinition.Toggle<Boolean> INTERACT_WITH_FLUID_HANDLERS = ButtonDefinitions.createToggleButtonDefinition(
      ButtonDefinitions.getBooleanStateData(
         GuiHelper.getButtonStateData(
            new UV(0, 112), TranslationHelper.INSTANCE.translUpgradeButton("interact_with_tanks_and_pipes"), Dimension.SQUARE_16, new Position(1, 1)
         ),
         GuiHelper.getButtonStateData(
            new UV(16, 112), TranslationHelper.INSTANCE.translUpgradeButton("do_not_interact_with_tanks_and_pipes"), Dimension.SQUARE_16, new Position(1, 1)
         )
      )
   );
   private static final ButtonDefinition.Toggle<Boolean> INTERACT_WITH_WORLD = ButtonDefinitions.createToggleButtonDefinition(
      ButtonDefinitions.getBooleanStateData(
         GuiHelper.getButtonStateData(
            new UV(176, 0), TranslationHelper.INSTANCE.translUpgradeButton("interact_with_world"), Dimension.SQUARE_16, new Position(1, 1)
         ),
         GuiHelper.getButtonStateData(
            new UV(192, 0), TranslationHelper.INSTANCE.translUpgradeButton("do_not_interact_with_world"), Dimension.SQUARE_16, new Position(1, 1)
         )
      )
   );
   private static final ButtonDefinition.Toggle<Boolean> INTERACT_WITH_HAND = ButtonDefinitions.createToggleButtonDefinition(
      ButtonDefinitions.getBooleanStateData(
         GuiHelper.getButtonStateData(
            new UV(208, 0), TranslationHelper.INSTANCE.translUpgradeButton("interact_with_hand"), Dimension.SQUARE_16, new Position(1, 1)
         ),
         GuiHelper.getButtonStateData(
            new UV(224, 0), TranslationHelper.INSTANCE.translUpgradeButton("do_not_interact_with_hand"), Dimension.SQUARE_16, new Position(1, 1)
         )
      )
   );

   protected PumpUpgradeTab(PumpUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, Component tabLabel, Component closedTooltip) {
      super(upgradeContainer, position, screen, tabLabel, closedTooltip);
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 3, this.y + 24),
            IS_INPUT,
            button -> this.getContainer().setIsInput(!this.getContainer().isInput()),
            () -> this.getContainer().isInput()
         )
      );
   }

   @Override
   protected void moveSlotsToTab() {
   }

   public static class Advanced extends PumpUpgradeTab {
      private final FluidFilterControl fluidFilterControl;

      public FluidFilterControl getFluidFilterControl() {
         return this.fluidFilterControl;
      }

      public Advanced(PumpUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
         super(
            upgradeContainer,
            position,
            screen,
            TranslationHelper.INSTANCE.translUpgrade("advanced_pump"),
            TranslationHelper.INSTANCE.translUpgradeTooltip("advanced_pump")
         );
         this.addHideableChild(
            new ToggleButton<>(
               new Position(this.x + 21, this.y + 24),
               PumpUpgradeTab.INTERACT_WITH_FLUID_HANDLERS,
               button -> this.getContainer().setInteractWithFluidHandlers(!this.getContainer().shouldInteractWithFluidHandlers()),
               () -> this.getContainer().shouldInteractWithFluidHandlers()
            )
         );
         this.addHideableChild(
            new ToggleButton<>(
               new Position(this.x + 39, this.y + 24),
               PumpUpgradeTab.INTERACT_WITH_WORLD,
               button -> this.getContainer().setInteractWithWorld(!this.getContainer().shouldInteractWithWorld()),
               () -> this.getContainer().shouldInteractWithWorld()
            )
         );
         this.addHideableChild(
            new ToggleButton<>(
               new Position(this.x + 57, this.y + 24),
               PumpUpgradeTab.INTERACT_WITH_HAND,
               button -> this.getContainer().setInteractWithHand(!this.getContainer().shouldInteractWithHand()),
               () -> this.getContainer().shouldInteractWithHand()
            )
         );
         this.fluidFilterControl = new FluidFilterControl(new Position(this.x + 3, this.y + 44), this.getContainer().getFluidFilterContainer());
         this.addHideableChild(this.fluidFilterControl);
      }
   }

   public static class Basic extends PumpUpgradeTab {
      public Basic(PumpUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
         super(upgradeContainer, position, screen, TranslationHelper.INSTANCE.translUpgrade("pump"), TranslationHelper.INSTANCE.translUpgradeTooltip("pump"));
      }
   }
}
