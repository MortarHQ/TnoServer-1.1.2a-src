package net.p3pp3rf1y.sophisticatedbackpacks.client.gui;

import java.util.Map;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition.Toggle;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterType;

public class SBPButtonDefinitions {
   public static final Toggle<ContentsFilterType> BACKPACK_CONTENTS_FILTER_TYPE = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         ContentsFilterType.ALLOW,
         GuiHelper.getButtonStateData(new UV(0, 0), SBPTranslationHelper.INSTANCE.translUpgradeButton("allow"), Dimension.SQUARE_16, new Position(1, 1)),
         ContentsFilterType.BLOCK,
         GuiHelper.getButtonStateData(new UV(16, 0), SBPTranslationHelper.INSTANCE.translUpgradeButton("block"), Dimension.SQUARE_16, new Position(1, 1)),
         ContentsFilterType.STORAGE,
         GuiHelper.getButtonStateData(
            new UV(80, 16), SBPTranslationHelper.INSTANCE.translUpgradeButton("match_backpack_contents"), Dimension.SQUARE_16, new Position(1, 1)
         )
      )
   );
   public static final Toggle<Boolean> SHIFT_CLICK_TARGET = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         true,
         GuiHelper.getButtonStateData(
            new UV(32, 48),
            Dimension.SQUARE_16,
            new Position(1, 1),
            SBPTranslationHelper.INSTANCE.getTranslatedLines(SBPTranslationHelper.INSTANCE.translUpgradeButton("shift_click_into_backpack"), null)
         ),
         false,
         GuiHelper.getButtonStateData(
            new UV(48, 48),
            Dimension.SQUARE_16,
            new Position(1, 1),
            SBPTranslationHelper.INSTANCE.getTranslatedLines(SBPTranslationHelper.INSTANCE.translUpgradeButton("shift_click_into_inventory"))
         )
      )
   );
   public static final Toggle<Boolean> REFILL_CRAFTING_GRID = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         true,
         GuiHelper.getButtonStateData(
            new UV(32, 112),
            Dimension.SQUARE_16,
            new Position(1, 1),
            SBPTranslationHelper.INSTANCE.getTranslatedLines(SBPTranslationHelper.INSTANCE.translUpgradeButton("refill_crafting_grid"))
         ),
         false,
         GuiHelper.getButtonStateData(
            new UV(48, 112),
            Dimension.SQUARE_16,
            new Position(1, 1),
            SBPTranslationHelper.INSTANCE.getTranslatedLines(SBPTranslationHelper.INSTANCE.translUpgradeButton("do_not_refill_crafting_grid"))
         )
      )
   );

   private SBPButtonDefinitions() {
   }
}
