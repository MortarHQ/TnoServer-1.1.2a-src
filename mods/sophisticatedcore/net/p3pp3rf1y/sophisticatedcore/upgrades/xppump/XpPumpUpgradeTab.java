package net.p3pp3rf1y.sophisticatedcore.upgrades.xppump;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.WidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;

public class XpPumpUpgradeTab extends UpgradeSettingsTab<XpPumpUpgradeContainer> {
   private static final ButtonDefinition.Toggle<AutomationDirection> DIRECTION = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         AutomationDirection.INPUT,
         GuiHelper.getButtonStateData(new UV(144, 0), TranslationHelper.INSTANCE.translUpgradeButton("xp_pump_input"), Dimension.SQUARE_16, new Position(1, 1)),
         AutomationDirection.OUTPUT,
         GuiHelper.getButtonStateData(
            new UV(128, 16), TranslationHelper.INSTANCE.translUpgradeButton("xp_pump_output"), Dimension.SQUARE_16, new Position(1, 1)
         ),
         AutomationDirection.KEEP,
         GuiHelper.getButtonStateData(new UV(96, 96), TranslationHelper.INSTANCE.translUpgradeButton("xp_pump_keep"), Dimension.SQUARE_16, new Position(1, 1)),
         AutomationDirection.OFF,
         GuiHelper.getButtonStateData(new UV(240, 0), TranslationHelper.INSTANCE.translUpgradeButton("xp_pump_off"), Dimension.SQUARE_16, new Position(1, 1))
      )
   );
   private static final ButtonDefinition.Toggle<Boolean> MEND_ITEMS = ButtonDefinitions.createToggleButtonDefinition(
      Map.of(
         true,
         GuiHelper.getButtonStateData(new UV(144, 32), TranslationHelper.INSTANCE.translUpgradeButton("mend_items"), Dimension.SQUARE_16, new Position(1, 1)),
         false,
         GuiHelper.getButtonStateData(
            new UV(160, 32), TranslationHelper.INSTANCE.translUpgradeButton("do_not_mend_items"), Dimension.SQUARE_16, new Position(1, 1)
         )
      )
   );
   private static final TextureBlitData STORE_ALL_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(192, 16), Dimension.SQUARE_16
   );
   public static final ButtonDefinition STORE_ALL = new ButtonDefinition(
      Dimension.SQUARE_18,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      STORE_ALL_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("store_all_experience"))
   );
   private static final TextureBlitData TAKE_ALL_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(144, 16), Dimension.SQUARE_16
   );
   public static final ButtonDefinition TAKE_ALL = new ButtonDefinition(
      Dimension.SQUARE_18,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      TAKE_ALL_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("take_all_experience"))
   );
   private static final TextureBlitData TAKE_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(160, 16), Dimension.SQUARE_16
   );
   public static final ButtonDefinition TAKE = new ButtonDefinition(
      Dimension.SQUARE_18, GuiHelper.DEFAULT_BUTTON_BACKGROUND, GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND, TAKE_FOREGROUND
   );
   private static final TextureBlitData STORE_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(176, 16), Dimension.SQUARE_16
   );
   public static final ButtonDefinition STORE = new ButtonDefinition(
      Dimension.SQUARE_18, GuiHelper.DEFAULT_BUTTON_BACKGROUND, GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND, STORE_FOREGROUND
   );
   private final Button takeButton;
   private final Button storeButton;

   public XpPumpUpgradeTab(final XpPumpUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen, boolean isMendingTurnedOn) {
      super(upgradeContainer, position, screen, TranslationHelper.INSTANCE.translUpgrade("xp_pump"), TranslationHelper.INSTANCE.translUpgradeTooltip("xp_pump"));
      int currentYOffset = 24;
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 3, this.y + currentYOffset),
            DIRECTION,
            button -> this.getContainer().setDirection(this.getContainer().getDirection().next()),
            () -> this.getContainer().getDirection()
         )
      );
      this.addHideableChild(
         new XpPumpUpgradeTab.LevelSelector(
            new Position(this.x + 21, this.y + currentYOffset),
            () -> String.valueOf(upgradeContainer.getLevel()),
            delta -> upgradeContainer.setLevel(upgradeContainer.getLevel() + (delta > 0.0 ? 1 : -1))
         )
      );
      currentYOffset += 20;
      if (isMendingTurnedOn) {
         this.addHideableChild(
            new ToggleButton<>(
               new Position(this.x + 3, this.y + currentYOffset),
               MEND_ITEMS,
               button -> upgradeContainer.setMendItems(!upgradeContainer.shouldMendItems()),
               upgradeContainer::shouldMendItems
            )
         );
         currentYOffset += 20;
      }

      this.addHideableChild(new Button(new Position(this.x + 3, this.y + currentYOffset), TAKE_ALL, button -> upgradeContainer.takeAllExperience()));
      this.takeButton = new Button(new Position(this.x + 21, this.y + currentYOffset), TAKE, button -> upgradeContainer.takeLevels()) {
         public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
            upgradeContainer.setLevelsToTake(upgradeContainer.getLevelsToTake() + (scrollY > 0.0 ? 1 : -1));
            XpPumpUpgradeTab.this.setTakeTooltip();
            return true;
         }
      };
      this.setTakeTooltip();
      this.addHideableChild(this.takeButton);
      this.storeButton = new Button(new Position(this.x + 39, this.y + currentYOffset), STORE, button -> upgradeContainer.storeLevels()) {
         public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
            upgradeContainer.setLevelsToStore(upgradeContainer.getLevelsToStore() + (scrollY > 0.0 ? 1 : -1));
            XpPumpUpgradeTab.this.setStoreTooltip();
            return true;
         }
      };
      this.setStoreTooltip();
      this.addHideableChild(this.storeButton);
      this.addHideableChild(new Button(new Position(this.x + 57, this.y + currentYOffset), STORE_ALL, button -> upgradeContainer.storeAllExperience()));
   }

   private void setStoreTooltip() {
      this.storeButton
         .setTooltip(
            List.of(
               Component.translatable(
                  TranslationHelper.INSTANCE.translUpgradeButton("store_levels"),
                  new Object[]{Component.literal(String.valueOf(this.getContainer().getLevelsToStore())).withStyle(ChatFormatting.RED)}
               ),
               Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("store_levels.controls"))
                  .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY})
            )
         );
   }

   private void setTakeTooltip() {
      this.takeButton
         .setTooltip(
            List.of(
               Component.translatable(
                  TranslationHelper.INSTANCE.translUpgradeButton("take_levels"),
                  new Object[]{Component.literal(String.valueOf(this.getContainer().getLevelsToTake())).withStyle(ChatFormatting.GREEN)}
               ),
               Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("take_levels.controls"))
                  .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY})
            )
         );
   }

   @Override
   protected void moveSlotsToTab() {
   }

   private static class LevelSelector extends WidgetBase {
      private final Supplier<String> getText;
      private final DoubleConsumer onScroll;
      private static final List<Component> TOOLTIP = List.of(
         Component.translatable(TranslationHelper.INSTANCE.translUpgradeControl("xp_level_select.tooltip")),
         Component.translatable(TranslationHelper.INSTANCE.translUpgradeControl("xp_level_select.tooltip.controls"))
            .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY})
      );

      protected LevelSelector(Position position, Supplier<String> getText, DoubleConsumer onScroll) {
         super(position, new Dimension(54, 18));
         this.getText = getText;
         this.onScroll = onScroll;
      }

      @Override
      protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
         GuiHelper.renderControlBackground(guiGraphics, this.x, this.y, 54, 18);
      }

      @Override
      protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
         String text = this.getText.get();
         Component fullText = Component.translatable(
               TranslationHelper.INSTANCE.translUpgradeControl("xp_level_select"), new Object[]{Component.literal(text).withStyle(ChatFormatting.WHITE)}
            )
            .withStyle(ChatFormatting.GRAY);
         int xOffset = (this.getWidth() - this.minecraft.font.width(fullText)) / 2;
         int yOffset = (int)Math.ceil((this.getHeight() - 9) / 2.0);
         guiGraphics.drawString(this.minecraft.font, fullText, this.x + xOffset, this.y + yOffset, DyeColor.BLACK.getTextColor(), false);
      }

      @Override
      public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
         super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
         if (this.isMouseOver(mouseX, mouseY)) {
            guiGraphics.renderTooltip(screen.font, TOOLTIP, Optional.empty(), mouseX, mouseY);
         }
      }

      public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
         this.onScroll.accept(scrollY);
         return true;
      }

      @Override
      public void updateNarration(NarrationElementOutput narrationElementOutput) {
      }
   }
}
