package net.p3pp3rf1y.sophisticatedcore.upgrades.magnet;

import net.minecraft.ChatFormatting;
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
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterControl;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterType;

public class MagnetUpgradeTab extends UpgradeSettingsTab<MagnetUpgradeContainer> {
   private static final ButtonDefinition.Toggle<Boolean> PICKUP_ITEMS = ButtonDefinitions.createToggleButtonDefinition(
      ButtonDefinitions.getBooleanStateData(
         GuiHelper.getButtonStateData(new UV(128, 48), TranslationHelper.INSTANCE.translUpgradeButton("pickup_items"), Dimension.SQUARE_16, new Position(1, 1)),
         GuiHelper.getButtonStateData(
            new UV(144, 48), TranslationHelper.INSTANCE.translUpgradeButton("do_not_pickup_items"), Dimension.SQUARE_16, new Position(1, 1)
         )
      )
   );
   private static final ButtonDefinition.Toggle<Boolean> PICKUP_XP = ButtonDefinitions.createToggleButtonDefinition(
      ButtonDefinitions.getBooleanStateData(
         GuiHelper.getButtonStateData(
            new UV(96, 48),
            Dimension.SQUARE_16,
            new Position(1, 1),
            Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("pickup_xp")),
            Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("pickup_xp.detail"))
               .withStyle(ChatFormatting.DARK_GRAY)
               .withStyle(ChatFormatting.ITALIC)
         ),
         GuiHelper.getButtonStateData(
            new UV(112, 48), TranslationHelper.INSTANCE.translUpgradeButton("do_not_pickup_xp"), Dimension.SQUARE_16, new Position(1, 1)
         )
      )
   );
   protected ContentsFilterControl filterLogicControl;

   protected MagnetUpgradeTab(
      MagnetUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, Component tabLabel, Component closedTooltip
   ) {
      super(upgradeContainer, position, screen, tabLabel, closedTooltip);
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 3, this.y + 24),
            PICKUP_ITEMS,
            button -> this.getContainer().setPickupItems(!this.getContainer().shouldPickupItems()),
            () -> this.getContainer().shouldPickupItems()
         )
      );
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 21, this.y + 24),
            PICKUP_XP,
            button -> this.getContainer().setPickupXp(!this.getContainer().shouldPickupXp()),
            () -> this.getContainer().shouldPickupXp()
         )
      );
   }

   @Override
   protected void moveSlotsToTab() {
      this.filterLogicControl.moveSlotsToView();
   }

   public static class Advanced extends MagnetUpgradeTab {
      public Advanced(
         MagnetUpgradeContainer upgradeContainer,
         Position position,
         StorageScreenBase<?> screen,
         int slotsPerRow,
         ButtonDefinition.Toggle<ContentsFilterType> contentsFilterButton
      ) {
         super(
            upgradeContainer,
            position,
            screen,
            TranslationHelper.INSTANCE.translUpgrade("advanced_magnet"),
            TranslationHelper.INSTANCE.translUpgradeTooltip("advanced_magnet")
         );
         this.filterLogicControl = this.addHideableChild(
            new ContentsFilterControl.Advanced(
               screen, new Position(this.x + 3, this.y + 44), this.getContainer().getFilterLogicContainer(), slotsPerRow, contentsFilterButton
            )
         );
      }
   }

   public static class Basic extends MagnetUpgradeTab {
      public Basic(
         MagnetUpgradeContainer upgradeContainer,
         Position position,
         StorageScreenBase<?> screen,
         int slotsPerRow,
         ButtonDefinition.Toggle<ContentsFilterType> contentsFilterButton
      ) {
         super(
            upgradeContainer, position, screen, TranslationHelper.INSTANCE.translUpgrade("magnet"), TranslationHelper.INSTANCE.translUpgradeTooltip("magnet")
         );
         this.filterLogicControl = this.addHideableChild(
            new ContentsFilterControl.Basic(
               screen, new Position(this.x + 3, this.y + 44), this.getContainer().getFilterLogicContainer(), slotsPerRow, contentsFilterButton
            )
         );
      }
   }
}
