package net.p3pp3rf1y.sophisticatedcore.upgrades.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public class CraftingUpgradeTab extends UpgradeSettingsTab<CraftingUpgradeContainer> {
   public static final int RESULT_SELECTION_BORDER_WIDTH = 3;
   private static final TextureBlitData ARROW = new TextureBlitData(GuiHelper.GUI_CONTROLS, new UV(97, 216), new Dimension(15, 8));
   private static final Dimension DIMENSION_8_12 = new Dimension(8, 12);
   private static final Dimension DIMENSION_16_12 = new Dimension(16, 12);
   private static final TextureBlitData SMALL_BUTTON_BACKGROUND = new TextureBlitData(GuiHelper.GUI_CONTROLS, new UV(53, 18), DIMENSION_8_12);
   private static final TextureBlitData SMALL_BUTTON_HOVERED_BACKGROUND = new TextureBlitData(GuiHelper.GUI_CONTROLS, new UV(61, 18), DIMENSION_8_12);
   private static final TextureBlitData PREVIOS_RESULT_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(0, 0), Dimension.SQUARE_256, new UV(48, 144), DIMENSION_8_12
   );
   private static final ButtonDefinition PREVIOUS_RESULT = new ButtonDefinition(
      DIMENSION_8_12,
      SMALL_BUTTON_BACKGROUND,
      SMALL_BUTTON_HOVERED_BACKGROUND,
      PREVIOS_RESULT_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("previous_result"))
   );
   private static final TextureBlitData NEXT_RESULT_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(0, 0), Dimension.SQUARE_256, new UV(56, 144), DIMENSION_8_12
   );
   private static final ButtonDefinition NEXT_RESULT = new ButtonDefinition(
      DIMENSION_8_12,
      SMALL_BUTTON_BACKGROUND,
      SMALL_BUTTON_HOVERED_BACKGROUND,
      NEXT_RESULT_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("next_result"))
   );
   private static final TextureBlitData BIG_BUTTON_BACKGROUND = new TextureBlitData(GuiHelper.GUI_CONTROLS, new UV(69, 18), DIMENSION_16_12);
   private static final TextureBlitData BIG_BUTTON_HOVERED_BACKGROUND = new TextureBlitData(GuiHelper.GUI_CONTROLS, new UV(63, 30), DIMENSION_16_12);
   private static final ButtonDefinition SELECT_RESULT = new ButtonDefinition(
      DIMENSION_16_12,
      BIG_BUTTON_BACKGROUND,
      BIG_BUTTON_HOVERED_BACKGROUND,
      null,
      Component.translatable(TranslationHelper.INSTANCE.translUpgradeButton("select_result"))
   );
   private static final ClientTooltipPositioner LEFT_SIDE_TOOLTIP_POSITIONER = new ClientTooltipPositioner() {
      public Vector2ic positionTooltip(int guiWidth, int guiHeight, int mouseX, int mouseY, int tooltipWidth, int tooltipHeight) {
         Vector2i tooltipTopLeft = new Vector2i(mouseX, mouseY).add(12, -12);
         this.positionTooltip(guiHeight, tooltipTopLeft, tooltipWidth, tooltipHeight);
         return tooltipTopLeft;
      }

      private void positionTooltip(int guiHeight, Vector2i tooltipTopLeft, int tooltipWidth, int tooltipHeight) {
         tooltipTopLeft.x = Math.max(tooltipTopLeft.x - 24 - tooltipWidth, 4);
         int i = tooltipHeight + 3;
         if (tooltipTopLeft.y + i > guiHeight) {
            tooltipTopLeft.y = guiHeight - i;
         }
      }
   };
   private final ICraftingUIPart craftingUIAddition;
   private final Button previousResultButton;
   private final Button nextResultButton;
   private final Button selectResultButton;
   private boolean resultSelectionShown = false;
   private Tuple<Position, Dimension> resultListPositionDimensions;
   private final List<Position> resultChoicePositions = new ArrayList<>();

   public CraftingUpgradeTab(
      CraftingUpgradeContainer upgradeContainer,
      Position position,
      StorageScreenBase<?> screen,
      ButtonDefinition.Toggle<Boolean> shiftClickTargetButton,
      ButtonDefinition.Toggle<Boolean> refillCraftingGridButton
   ) {
      super(
         upgradeContainer, position, screen, TranslationHelper.INSTANCE.translUpgrade("crafting"), TranslationHelper.INSTANCE.translUpgradeTooltip("crafting")
      );
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 3, this.y + 24),
            shiftClickTargetButton,
            button -> this.getContainer().setShiftClickIntoStorage(!this.getContainer().shouldShiftClickIntoStorage()),
            this.getContainer()::shouldShiftClickIntoStorage
         )
      );
      this.addHideableChild(
         new ToggleButton<>(
            new Position(this.x + 21, this.y + 24),
            refillCraftingGridButton,
            button -> this.getContainer().setRefillCraftingGrid(!this.getContainer().shouldRefillCraftingGrid()),
            this.getContainer()::shouldRefillCraftingGrid
         )
      );
      this.craftingUIAddition = screen.getCraftingUIAddition();
      this.openTabDimension = new Dimension(63 + this.craftingUIAddition.getWidth(), 142);
      this.previousResultButton = new Button(new Position(this.x + 3 + 6 + this.craftingUIAddition.getWidth(), this.y + 118), PREVIOUS_RESULT, button -> {
         if (button == 0) {
            this.getContainer().selectPreviousCraftingResult();
         }
      }) {
         @Override
         public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
            if (this.visible && this.isMouseOver(mouseX, mouseY)) {
               guiGraphics.renderTooltip(
                  this.minecraft.font,
                  this.getTooltip().stream().map(Component::getVisualOrderText).toList(),
                  CraftingUpgradeTab.LEFT_SIDE_TOOLTIP_POSITIONER,
                  mouseX,
                  mouseY
               );
            }
         }
      };
      this.addHideableChild(this.previousResultButton);
      this.nextResultButton = new Button(new Position(this.x + 3 + 6 + this.craftingUIAddition.getWidth() + 8 + 26, this.y + 118), NEXT_RESULT, button -> {
         if (button == 0) {
            this.getContainer().selectNextCraftingResult();
         }
      });
      this.addHideableChild(this.nextResultButton);
      this.selectResultButton = new Button(new Position(this.x + 3 + 6 + this.craftingUIAddition.getWidth() + 13, this.y + 99), SELECT_RESULT, button -> {
         if (button == 0) {
            this.resultSelectionShown = !this.resultSelectionShown;
         }
      }) {
         @Override
         protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
            super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
            guiGraphics.drawString(
               this.font, String.valueOf(CraftingUpgradeTab.this.getContainer().getMatchedCraftingResults().size()), this.x + 5, this.y + 2, 16777215, true
            );
         }
      };
      this.addHideableChild(this.selectResultButton);
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
      if (this.getContainer().isOpen()) {
         GuiHelper.renderSlotsBackground(guiGraphics, this.x + 3 + this.craftingUIAddition.getWidth(), this.y + 44, 3, 3);
         GuiHelper.blit(guiGraphics, this.x + 3 + this.craftingUIAddition.getWidth() + 19, this.y + 101, ARROW);
         GuiHelper.blit(guiGraphics, this.x + 3 + this.craftingUIAddition.getWidth() + 14, this.y + 111, GuiHelper.CRAFTING_RESULT_SLOT);
      }
   }

   @Override
   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      List<ItemStack> matchedCraftingResults = this.getContainer().getMatchedCraftingResults();
      this.previousResultButton.setVisible(this.shouldShowResultSelection());
      this.nextResultButton.setVisible(this.shouldShowResultSelection());
      this.selectResultButton.setVisible(this.shouldShowResultSelection());
      if (!this.shouldShowResultSelection()) {
         this.resultSelectionShown = false;
         this.resultListPositionDimensions = null;
         this.resultChoicePositions.clear();
      } else if (this.resultSelectionShown) {
         if (this.resultListPositionDimensions == null || this.resultChoicePositions.size() != matchedCraftingResults.size()) {
            this.initResultSelectionPositionDimension(matchedCraftingResults);
            this.resultChoicePositions.clear();

            for (int i = 0; i < matchedCraftingResults.size(); i++) {
               int xOffset = i % 3 * 18;
               int yOffset = i / 3 * 18;
               this.resultChoicePositions
                  .add(
                     new Position(
                        ((Position)this.resultListPositionDimensions.getA()).x() + 3 + xOffset,
                        ((Position)this.resultListPositionDimensions.getA()).y() + 3 + yOffset
                     )
                  );
            }
         }

         guiGraphics.pose().pushPose();
         guiGraphics.pose().translate(0.0F, 0.0F, 410.0F);
         renderResultSelectionBackground(
            guiGraphics,
            matchedCraftingResults,
            ((Dimension)this.resultListPositionDimensions.getB()).width(),
            ((Dimension)this.resultListPositionDimensions.getB()).height(),
            ((Position)this.resultListPositionDimensions.getA()).x(),
            ((Position)this.resultListPositionDimensions.getA()).y()
         );
         this.renderResultChoices(
            guiGraphics,
            matchedCraftingResults,
            ((Position)this.resultListPositionDimensions.getA()).x(),
            ((Position)this.resultListPositionDimensions.getA()).y()
         );
         this.renderSelectionSlotHover(guiGraphics, mouseX, mouseY);
         guiGraphics.pose().popPose();
      }
   }

   private void renderSelectionSlotHover(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      this.getResultChoiceHovered(mouseX, mouseY).ifPresent(i -> {
         Position position = this.resultChoicePositions.get(i);
         AbstractContainerScreen.renderSlotHighlight(guiGraphics, position.x() + 1, position.y() + 1, 0, -2130706433);
      });
   }

   private Optional<Integer> getResultChoiceHovered(int mouseX, int mouseY) {
      if (this.resultSelectionShown && this.resultListPositionDimensions != null) {
         Position pos = (Position)this.resultListPositionDimensions.getA();
         Dimension dim = (Dimension)this.resultListPositionDimensions.getB();
         int slotsLeftX = pos.x() + 3;
         int slotsTopY = pos.y() + 3;
         if (mouseX >= slotsLeftX && mouseX < pos.x() + dim.width() - 3 && mouseY >= slotsTopY && mouseY < pos.y() + dim.height() - 3) {
            for (int i = 0; i < this.resultChoicePositions.size(); i++) {
               Position position = this.resultChoicePositions.get(i);
               if (mouseX >= position.x() && mouseX < position.x() + 18 && mouseY >= position.y() && mouseY < position.y() + 18) {
                  return Optional.of(i);
               }
            }
         }

         return Optional.empty();
      } else {
         return Optional.empty();
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      boolean ret = super.mouseClicked(mouseX, mouseY, button);
      return ret ? true : this.getResultChoiceHovered((int)mouseX, (int)mouseY).map(i -> {
         this.getContainer().selectCraftingResult(i);
         this.resultSelectionShown = false;
         return true;
      }).orElse(false);
   }

   private void initResultSelectionPositionDimension(List<ItemStack> matchedCraftingResults) {
      int height = 3 + ((matchedCraftingResults.size() - 1) / 3 + 1) * 18 + 3;
      int width = 3 + Math.min(matchedCraftingResults.size(), 3) * 18 + 3;
      int resultListLeftX = this.selectResultButton.getX() + 8 - 3 - (int)(Math.min(matchedCraftingResults.size(), 3) / 2.0F * 18.0F);
      int resultListTopY = this.selectResultButton.getY() - height;
      this.resultListPositionDimensions = new Tuple(new Position(resultListLeftX, resultListTopY), new Dimension(width, height));
   }

   private static void renderResultSelectionBackground(
      GuiGraphics guiGraphics, List<ItemStack> matchedCraftingResults, int width, int height, int resultListLeftX, int resultListTopY
   ) {
      int halfWidth = width / 2;
      int halfHeight = height / 2;
      guiGraphics.blit(GuiHelper.GUI_CONTROLS, resultListLeftX, resultListTopY, 85.0F, 24.0F, halfWidth, halfHeight, 256, 256);
      guiGraphics.blit(GuiHelper.GUI_CONTROLS, resultListLeftX + halfWidth, resultListTopY, 117.0F - halfWidth, 24.0F, halfWidth, halfHeight, 256, 256);
      guiGraphics.blit(GuiHelper.GUI_CONTROLS, resultListLeftX, resultListTopY + halfHeight, 85.0F, 56.0F - halfHeight, halfWidth, halfHeight, 256, 256);
      guiGraphics.blit(
         GuiHelper.GUI_CONTROLS,
         resultListLeftX + halfWidth,
         resultListTopY + halfHeight,
         117.0F - halfWidth,
         56.0F - halfHeight,
         halfWidth,
         halfHeight,
         256,
         256
      );
      GuiHelper.renderSlotsBackground(
         guiGraphics, resultListLeftX + 3, resultListTopY + 3, 3, matchedCraftingResults.size() / 3, matchedCraftingResults.size() % 3
      );
   }

   private void renderResultChoices(GuiGraphics guiGraphics, List<ItemStack> matchedCraftingResults, int resultListLeftX, int resultListTopY) {
      for (int i = 0; i < matchedCraftingResults.size(); i++) {
         ItemStack resultStack = matchedCraftingResults.get(i);
         int xOffset = i % 3 * 18;
         int yOffset = i / 3 * 18;
         int x = resultListLeftX + 3 + 1 + xOffset;
         int y = resultListTopY + 3 + 1 + yOffset;
         guiGraphics.renderItem(resultStack, x, y);
         guiGraphics.renderItemDecorations(this.font, resultStack, x, y, null);
      }
   }

   @Override
   protected void onTabClose() {
      super.onTabClose();
      this.craftingUIAddition.onCraftingSlotsHidden();
   }

   @Override
   protected void moveSlotsToTab() {
      int slotNumber = 0;

      for (Slot slot : this.getContainer().getSlots()) {
         slot.x = this.x + 3 + this.craftingUIAddition.getWidth() - this.screen.getGuiLeft() + 1 + slotNumber % 3 * 18;
         slot.y = this.y + 44 - this.screen.getGuiTop() + 1 + slotNumber / 3 * 18;
         if (++slotNumber >= 9) {
            break;
         }
      }

      Slot craftingSlot = this.getContainer().getSlots().get(9);
      craftingSlot.x = this.x + 3 + this.craftingUIAddition.getWidth() - this.screen.getGuiLeft() + 19;
      craftingSlot.y = this.y + 44 - this.screen.getGuiTop() + 72;
      this.craftingUIAddition.onCraftingSlotsDisplayed(this.getContainer().getSlots());
   }

   private boolean shouldShowResultSelection() {
      return this.getContainer().getMatchedCraftingResults().size() > 1;
   }

   @Override
   public boolean slotIsNotCoveredAt(Slot slot, double mouseX, double mouseY) {
      if (this.isOpen && this.shouldShowResultSelection() && this.resultSelectionShown) {
         Position pos = (Position)this.resultListPositionDimensions.getA();
         Dimension dim = (Dimension)this.resultListPositionDimensions.getB();
         return mouseX < pos.x() || mouseX > pos.x() + dim.width() || mouseY < pos.y() || mouseY > pos.y() + dim.height();
      } else {
         return true;
      }
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(0.0F, 0.0F, 410.0F);
      this.getResultChoiceHovered(mouseX, mouseY).ifPresent(i -> {
         List<ItemStack> matchedCraftingResults = this.getContainer().getMatchedCraftingResults();
         if (i < matchedCraftingResults.size()) {
            ItemStack stack = matchedCraftingResults.get(i);
            guiGraphics.renderTooltip(this.minecraft.font, stack, mouseX, mouseY);
         }
      });
      guiGraphics.pose().popPose();
   }
}
