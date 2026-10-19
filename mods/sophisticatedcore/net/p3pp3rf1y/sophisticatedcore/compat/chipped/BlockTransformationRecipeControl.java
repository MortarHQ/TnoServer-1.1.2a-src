package net.p3pp3rf1y.sophisticatedcore.compat.chipped;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.FontContext;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.WidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;

public class BlockTransformationRecipeControl extends WidgetBase {
   private static final TextureBlitData SLIDER = new TextureBlitData(GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(29, 131), Dimension.RECTANGLE_12_15);
   private static final TextureBlitData DISABLED_SLIDER = new TextureBlitData(
      GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(41, 131), Dimension.RECTANGLE_12_15
   );
   private static final TextureBlitData RECIPE_BACKGROUND = new TextureBlitData(
      GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(110, 148), Dimension.RECTANGLE_16_18
   );
   private static final TextureBlitData SELECTED_RECIPE_BACKGROUND = new TextureBlitData(
      GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(110, 166), Dimension.RECTANGLE_16_18
   );
   private static final TextureBlitData RECIPE_BACKGROUND_HOVERED = new TextureBlitData(
      GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(110, 184), Dimension.RECTANGLE_16_18
   );
   private static final TextureBlitData LIST_BACKGROUND = new TextureBlitData(
      GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(29, 146), new Dimension(81, 56)
   );
   private static final int LIST_Y_OFFSET = 22;
   private static final int INPUT_SLOT_HEIGHT = 18;
   private static final int SPACING = 4;
   private boolean clickedOnScroll;
   private final StorageScreenBase<?> screen;
   private final BlockTransformationRecipeContainer container;
   private boolean hasItemsInInputSlot;
   private int recipeIndexOffset;
   private float sliderProgress;

   protected BlockTransformationRecipeControl(StorageScreenBase<?> screen, BlockTransformationRecipeContainer container, Position position) {
      super(position, new Dimension(81, 108));
      this.screen = screen;
      this.container = container;
      container.setInventoryUpdateListener(this::onInventoryUpdate);
      this.onInventoryUpdate();
   }

   public void moveSlotsToView() {
      Slot inputSlot = this.container.getInputSlot();
      inputSlot.x = this.x + this.getCenteredX(16) - this.screen.getGuiLeft();
      inputSlot.y = this.y - this.screen.getGuiTop() + 1;
      Slot outputSlot = this.container.getOutputSlot();
      outputSlot.x = this.x + this.getCenteredX(16) - this.screen.getGuiLeft();
      outputSlot.y = inputSlot.y + 18 + 4 + LIST_BACKGROUND.getHeight() + 4 + 4;
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      GuiHelper.renderSlotsBackground(guiGraphics, this.x + this.getCenteredX(18), this.y, 1, 1);
      GuiHelper.blit(guiGraphics, this.x, this.y + 22, LIST_BACKGROUND);
      GuiHelper.blit(guiGraphics, this.x + this.getCenteredX(26), this.y + 18 + 4 + LIST_BACKGROUND.getHeight() + 4, GuiHelper.CRAFTING_RESULT_SLOT);
      int sliderYOffset = (int)(39.0F * this.sliderProgress) + 1;
      GuiHelper.blit(guiGraphics, this.x + 68, this.y + 22 + sliderYOffset, this.canScroll() ? SLIDER : DISABLED_SLIDER);
      int listInnerLeftX = this.x + 1;
      int listTopY = this.getListTopY();
      int recipeIndexOffsetMax = this.recipeIndexOffset + 12;
      this.renderRecipeBackgrounds(guiGraphics, mouseX, mouseY, listInnerLeftX, listTopY, recipeIndexOffsetMax);
      this.drawRecipesItems(guiGraphics, listInnerLeftX, listTopY, recipeIndexOffsetMax);
   }

   private void drawRecipesItems(GuiGraphics guiGraphics, int listInnerLeftX, int top, int recipeIndexOffsetMax) {
      List<ItemStack> results = this.container.getResults();

      for (int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < results.size(); i++) {
         int j = i - this.recipeIndexOffset;
         int k = listInnerLeftX + j % 4 * 16;
         int l = j / 4;
         int i1 = top + l * 18 + 2;
         GuiHelper.renderItemInGUI(guiGraphics, this.minecraft, results.get(i), k, i1);
      }
   }

   private int getListTopY() {
      return this.y + 22;
   }

   private void renderRecipeBackgrounds(GuiGraphics guiGraphics, int mouseX, int mouseY, int listInnerLeftX, int listTopY, int recipeIndexOffsetMax) {
      for (int recipeIndex = this.recipeIndexOffset; recipeIndex < recipeIndexOffsetMax && recipeIndex < this.container.getResults().size(); recipeIndex++) {
         int j = recipeIndex - this.recipeIndexOffset;
         int recipeX = listInnerLeftX + j % 4 * 16;
         int row = j / 4;
         int recipeY = listTopY + row * 18 + 2;
         TextureBlitData background = RECIPE_BACKGROUND;
         if (recipeIndex == this.container.getSelectedRecipe()) {
            background = SELECTED_RECIPE_BACKGROUND;
         } else if (mouseX >= recipeX && mouseY >= recipeY && mouseX < recipeX + 16 && mouseY < recipeY + 18) {
            background = RECIPE_BACKGROUND_HOVERED;
         }

         GuiHelper.blit(guiGraphics, recipeX, recipeY - 1, background);
      }
   }

   private boolean canScroll() {
      return this.hasItemsInInputSlot && this.container.getResults().size() > 12;
   }

   @Override
   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
      this.renderHoveredTooltip(guiGraphics, mouseX, mouseY);
   }

   private void renderHoveredTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      if (this.hasItemsInInputSlot) {
         int listTopY = this.getListTopY();
         int k = this.recipeIndexOffset + 12;
         List<ItemStack> results = this.container.getResults();

         for (int recipeIndex = this.recipeIndexOffset; recipeIndex < k && recipeIndex < results.size(); recipeIndex++) {
            int inviewRecipeIndex = recipeIndex - this.recipeIndexOffset;
            int recipeLeftX = this.x + inviewRecipeIndex % 4 * 16;
            int k1 = listTopY + inviewRecipeIndex / 4 * 18 + 2;
            if (mouseX >= recipeLeftX && mouseX < recipeLeftX + 16 && mouseY >= k1 && mouseY < k1 + 18) {
               this.renderTooltip(guiGraphics, results.get(recipeIndex), mouseX, mouseY);
            }
         }
      }
   }

   private void renderTooltip(GuiGraphics guiGraphics, ItemStack itemStack, int mouseX, int mouseY) {
      Font font = IClientItemExtensions.of(itemStack).getFont(itemStack, FontContext.TOOLTIP);
      guiGraphics.renderComponentTooltip(font == null ? this.font : font, Screen.getTooltipFromItem(this.minecraft, itemStack), mouseX, mouseY);
   }

   private void onInventoryUpdate() {
      this.hasItemsInInputSlot = this.container.hasItemsInInputSlot();
      if (!this.hasItemsInInputSlot) {
         this.sliderProgress = 0.0F;
         this.recipeIndexOffset = 0;
      } else if (this.container.getSelectedRecipe() - this.recipeIndexOffset >= 12) {
         int rowsToScroll = (this.container.getSelectedRecipe() - this.recipeIndexOffset - 12) / 4 + 1;
         this.scrollRecipesByDelta(-rowsToScroll);
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.clickedOnScroll = false;
      if (this.hasItemsInInputSlot) {
         int listInnerLeftX = this.x + 1;
         int listInnerTopY = this.y + 22 + 1;
         int maxRecipeIndexOffset = this.recipeIndexOffset + 12;

         for (int recipeIndex = this.recipeIndexOffset; recipeIndex < maxRecipeIndexOffset; recipeIndex++) {
            int visibleRecipeIndex = recipeIndex - this.recipeIndexOffset;
            double relativeX = mouseX - (listInnerLeftX + visibleRecipeIndex % 4 * 16);
            double relativeY = mouseY - (listInnerTopY + Math.floorDiv(visibleRecipeIndex, 4) * 18);
            if (relativeX >= 0.0 && relativeY >= 0.0 && relativeX < 16.0 && relativeY < 18.0 && this.container.selectRecipeIndex(recipeIndex)) {
               Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
               return true;
            }
         }

         int sliderLeftX = listInnerLeftX + 67;
         if (mouseX >= sliderLeftX && mouseX < sliderLeftX + 12 && mouseY >= listInnerTopY && mouseY < listInnerTopY + 54) {
            this.clickedOnScroll = true;
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.clickedOnScroll && this.canScroll()) {
         int listTopY = this.y + 22;
         int listBottomY = listTopY + 54;
         this.sliderProgress = ((float)mouseY - listTopY - 7.5F) / (listBottomY - listTopY - 15.0F);
         this.sliderProgress = Mth.clamp(this.sliderProgress, 0.0F, 1.0F);
         this.recipeIndexOffset = (int)(this.sliderProgress * this.getHiddenRows() + 0.5) * 4;
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      if (this.canScroll()) {
         this.scrollRecipesByDelta(scrollY);
      }

      return true;
   }

   private void scrollRecipesByDelta(double delta) {
      int i = this.getHiddenRows();
      this.sliderProgress = (float)(this.sliderProgress - delta / i);
      this.sliderProgress = Mth.clamp(this.sliderProgress, 0.0F, 1.0F);
      this.recipeIndexOffset = (int)(this.sliderProgress * i + 0.5) * 4;
   }

   protected int getHiddenRows() {
      return (this.container.getResults().size() + 4 - 1) / 4 - 3;
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }
}
