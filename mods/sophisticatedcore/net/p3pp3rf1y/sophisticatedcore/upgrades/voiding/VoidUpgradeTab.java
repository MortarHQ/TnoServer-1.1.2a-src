package net.p3pp3rf1y.sophisticatedcore.upgrades.voiding;

import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

public class VoidUpgradeTab extends UpgradeSettingsTab<VoidUpgradeContainer> {
   private static final MutableComponent VOID_SLOT_OVERFLOW_TOOLTIP = Component.translatable(
      TranslationHelper.INSTANCE.translUpgradeButton("void_slot_overflow")
   );
   private static final MutableComponent VOID_SLOT_OVERFLOW_TOOLTIP_DETAIL = Component.translatable(
         TranslationHelper.INSTANCE.translUpgradeButton("void_slot_overflow.detail")
      )
      .withStyle(ChatFormatting.GRAY);
   private static final MutableComponent VOID_STORAGE_OVERFLOW_TOOLTIP = Component.translatable(
      TranslationHelper.INSTANCE.translUpgradeButton("void_storage_overflow")
   );
   private static final MutableComponent VOID_STORAGE_OVERFLOW_TOOLTIP_DETAIL = Component.translatable(
         TranslationHelper.INSTANCE.translUpgradeButton("void_storage_overflow.detail")
      )
      .withStyle(ChatFormatting.GRAY);
   private static final MutableComponent VOID_ALWAYS_DISABLED_TOOLTIP = Component.translatable(
         TranslationHelper.INSTANCE.translUpgradeButton("void_always_disabled")
      )
      .withStyle(ChatFormatting.RED);
   private static final ButtonDefinition.Toggle<VoidType> VOID_TYPE = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         VoidType.ALWAYS,
         GuiHelper.getButtonStateData(new UV(208, 16), TranslationHelper.INSTANCE.translUpgradeButton("void_always"), Dimension.SQUARE_16, new Position(1, 1)),
         VoidType.SLOT_OVERFLOW,
         GuiHelper.getButtonStateData(new UV(224, 16), Dimension.SQUARE_16, new Position(1, 1), VOID_SLOT_OVERFLOW_TOOLTIP, VOID_SLOT_OVERFLOW_TOOLTIP_DETAIL),
         VoidType.STORAGE_OVERFLOW,
         GuiHelper.getButtonStateData(
            new UV(112, 96), Dimension.SQUARE_16, new Position(1, 1), VOID_STORAGE_OVERFLOW_TOOLTIP, VOID_STORAGE_OVERFLOW_TOOLTIP_DETAIL
         )
      )
   );
   private static final ButtonDefinition.Toggle<VoidType> VOID_TYPE_OVERFLOW_ONLY = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         VoidType.SLOT_OVERFLOW,
         GuiHelper.getButtonStateData(
            new UV(224, 16),
            Dimension.SQUARE_16,
            new Position(1, 1),
            VOID_SLOT_OVERFLOW_TOOLTIP,
            VOID_SLOT_OVERFLOW_TOOLTIP_DETAIL,
            VOID_ALWAYS_DISABLED_TOOLTIP
         ),
         VoidType.STORAGE_OVERFLOW,
         GuiHelper.getButtonStateData(
            new UV(112, 96),
            Dimension.SQUARE_16,
            new Position(1, 1),
            VOID_STORAGE_OVERFLOW_TOOLTIP,
            VOID_STORAGE_OVERFLOW_TOOLTIP_DETAIL,
            VOID_ALWAYS_DISABLED_TOOLTIP
         )
      )
   );
   protected FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>> filterLogicControl;

   protected VoidUpgradeTab(VoidUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, Component tabLabel, Component closedTooltip) {
      super(upgradeContainer, position, screen, tabLabel, closedTooltip);
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 3, this.y + 24),
            ButtonDefinitions.WORK_IN_GUI,
            button -> this.getContainer().setShouldWorkdInGUI(!this.getContainer().shouldWorkInGUI()),
            this.getContainer()::shouldWorkInGUI
         )
      );
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 21, this.y + 24),
            this.getContainer().getUpgradeWrapper().isVoidAlwaysEnabled() ? VOID_TYPE : VOID_TYPE_OVERFLOW_ONLY,
            button -> this.getContainer().setVoidType(this.getContainer().getVoidType().next()),
            this.getContainer()::getVoidType
         )
      );
   }

   @Override
   protected void moveSlotsToTab() {
      this.filterLogicControl.moveSlotsToView();
   }

   public static class Advanced extends VoidUpgradeTab {
      public Advanced(VoidUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, int slotsPerRow) {
         super(
            upgradeContainer,
            position,
            screen,
            TranslationHelper.INSTANCE.translUpgrade("advanced_void"),
            TranslationHelper.INSTANCE.translUpgradeTooltip("advanced_void")
         );
         this.filterLogicControl = this.addHideableChild(
            new FilterLogicControl.Advanced(screen, new Position(this.x + 3, this.y + 44), this.getContainer().getFilterLogicContainer(), slotsPerRow)
         );
      }
   }

   public static class Basic extends VoidUpgradeTab {
      public Basic(VoidUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, int slotsPerRow) {
         super(upgradeContainer, position, screen, TranslationHelper.INSTANCE.translUpgrade("void"), TranslationHelper.INSTANCE.translUpgradeTooltip("void"));
         this.filterLogicControl = this.addHideableChild(
            new FilterLogicControl.Basic(screen, new Position(this.x + 3, this.y + 44), this.getContainer().getFilterLogicContainer(), slotsPerRow)
         );
      }
   }
}
