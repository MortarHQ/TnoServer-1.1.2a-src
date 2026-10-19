package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.toolswapper;

import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition.Toggle;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicControl;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicControl.Advanced;

public class ToolSwapperUpgradeTab extends UpgradeSettingsTab<ToolSwapperUpgradeContainer> {
   public static final Toggle<Boolean> SWAP_WEAPON = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         false,
         GuiHelper.getButtonStateData(
            new UV(48, 64),
            Dimension.SQUARE_16,
            new Position(1, 1),
            new Component[]{
               Component.translatable(SBPTranslationHelper.INSTANCE.translUpgradeButton("do_not_swap_weapon")),
               Component.translatable(SBPTranslationHelper.INSTANCE.translUpgradeButton("do_not_swap_weapon.detail")).withStyle(ChatFormatting.GRAY)
            }
         ),
         true,
         GuiHelper.getButtonStateData(
            new UV(32, 64),
            Dimension.SQUARE_16,
            new Position(1, 1),
            new Component[]{
               Component.translatable(SBPTranslationHelper.INSTANCE.translUpgradeButton("swap_weapon")),
               Component.translatable(SBPTranslationHelper.INSTANCE.translUpgradeButton("swap_weapon.detail")).withStyle(ChatFormatting.GRAY)
            }
         )
      )
   );
   public static final Toggle<ToolSwapMode> SWAP_TOOLS = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         ToolSwapMode.NO_SWAP,
         GuiHelper.getButtonStateData(
            new UV(96, 64),
            Dimension.SQUARE_16,
            new Position(1, 1),
            new Component[]{
               Component.translatable(SBPTranslationHelper.INSTANCE.translUpgradeButton("do_not_swap_tools")),
               Component.translatable(SBPTranslationHelper.INSTANCE.translUpgradeButton("do_not_swap_tools.detail")).withStyle(ChatFormatting.GRAY)
            }
         ),
         ToolSwapMode.ONLY_TOOLS,
         GuiHelper.getButtonStateData(
            new UV(80, 64),
            Dimension.SQUARE_16,
            new Position(1, 1),
            new Component[]{
               Component.translatable(SBPTranslationHelper.INSTANCE.translUpgradeButton("only_swap_for_tools")),
               Component.translatable(SBPTranslationHelper.INSTANCE.translUpgradeButton("only_swap_for_tools.detail")).withStyle(ChatFormatting.GRAY)
            }
         ),
         ToolSwapMode.ANY,
         GuiHelper.getButtonStateData(
            new UV(64, 64),
            Dimension.SQUARE_16,
            new Position(1, 1),
            new Component[]{
               Component.translatable(SBPTranslationHelper.INSTANCE.translUpgradeButton("swap_tools")),
               Component.translatable(SBPTranslationHelper.INSTANCE.translUpgradeButton("swap_tools.detail")).withStyle(ChatFormatting.GRAY)
            }
         )
      )
   );
   protected final FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>> filterLogicControl;

   public ToolSwapperUpgradeTab(ToolSwapperUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
      super(
         upgradeContainer,
         position,
         screen,
         SBPTranslationHelper.INSTANCE.translUpgrade("advanced_tool_swapper", new Object[0]),
         SBPTranslationHelper.INSTANCE.translUpgradeTooltip("advanced_tool_swapper")
      );
      this.addHideableChild(
         new ToggleButton(
            new Position(this.x + 3, this.y + 24),
            SWAP_WEAPON,
            button -> ((ToolSwapperUpgradeContainer)this.getContainer()).setSwapWeapon(!((ToolSwapperUpgradeContainer)this.getContainer()).shouldSwapWeapon()),
            ((ToolSwapperUpgradeContainer)this.getContainer())::shouldSwapWeapon
         )
      );
      this.addHideableChild(
         new ToggleButton(
            new Position(this.x + 21, this.y + 24),
            SWAP_TOOLS,
            button -> ((ToolSwapperUpgradeContainer)this.getContainer())
               .setToolSwapMode(((ToolSwapperUpgradeContainer)this.getContainer()).getToolSwapMode().next()),
            ((ToolSwapperUpgradeContainer)this.getContainer())::getToolSwapMode
         )
      );
      this.filterLogicControl = (FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>>)this.addHideableChild(
         new Advanced(screen, new Position(this.x + 3, this.y + 44), ((ToolSwapperUpgradeContainer)this.getContainer()).getFilterLogicContainer(), 4)
      );
   }

   protected void moveSlotsToTab() {
      this.filterLogicControl.moveSlotsToView();
   }
}
