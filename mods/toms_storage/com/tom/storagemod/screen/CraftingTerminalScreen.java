package com.tom.storagemod.screen;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tom.storagemod.menu.CraftingTerminalMenu;
import com.tom.storagemod.screen.widget.ToggleButton;
import com.tom.storagemod.util.IAutoFillTerminal;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.gui.screens.recipebook.GhostRecipe.GhostIngredient;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CraftingTerminalScreen extends AbstractStorageTerminalScreen<CraftingTerminalMenu> implements RecipeUpdateListener {
   private static final ResourceLocation gui = ResourceLocation.tryBuild("toms_storage", "textures/gui/crafting_terminal.png");
   private final RecipeBookComponent recipeBookGui = new RecipeBookComponent();
   private boolean widthTooNarrow;
   private ToggleButton buttonPullFromInv;
   private CraftingTerminalScreen.ButtonClear btnClr;

   public CraftingTerminalScreen(CraftingTerminalMenu screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn, 5, 256, 7, 17);
   }

   @Override
   public ResourceLocation getGui() {
      return gui;
   }

   @Override
   protected void onUpdateSearch(String text) {
      if (IAutoFillTerminal.hasSync() || (this.buttonSearchType.getSearchType() & 4) > 0) {
         if (this.recipeBookGui.searchBox != null) {
            this.recipeBookGui.searchBox.setValue(text);
         }

         this.recipeBookGui.recipesUpdated();
      }
   }

   @Override
   protected void init() {
      this.imageWidth = 194;
      this.imageHeight = 256;
      super.init();
      this.widthTooNarrow = this.width < 379;
      this.recipeBookGui.init(this.width, this.height, this.minecraft, this.widthTooNarrow, (RecipeBookMenu)this.menu);
      this.leftPos = this.recipeBookGui.updateScreenPosition(this.width, this.imageWidth - 16);
      this.addWidget(this.recipeBookGui);
      this.setInitialFocus(this.recipeBookGui);
      this.btnClr = new CraftingTerminalScreen.ButtonClear(this.leftPos + 80, this.topPos + 20 + this.rowCount * 18, b -> this.clearGrid());
      this.addRenderableWidget(this.btnClr);
      this.buttonPullFromInv = (ToggleButton)this.addRenderableWidget(
         ToggleButton.builder(this.leftPos - 18, this.topPos + 5 + 108)
            .iconOff(ResourceLocation.tryBuild("toms_storage", "icons/refill_off"))
            .iconOn(ResourceLocation.tryBuild("toms_storage", "icons/refill_on"))
            .build(s -> {
               this.buttonPullFromInv.setState(s);
               this.sendUpdate();
            })
      );
      this.buttonPullFromInv
         .setTooltip(
            Tooltip.create(Component.translatable("tooltip.toms_storage.crafting_pull_off")),
            Tooltip.create(Component.translatable("tooltip.toms_storage.crafting_pull_on"))
         );
      this.addRenderableWidget(new RecipeBookButton(this.leftPos + 4, this.topPos + 38 + this.rowCount * 18, p_214076_1_ -> {
         this.recipeBookGui.initVisuals();
         this.recipeBookGui.toggleVisibility();
         this.leftPos = this.recipeBookGui.updateScreenPosition(this.width, this.imageWidth - 16);
         ((ImageButton)p_214076_1_).setPosition(this.leftPos + 4, this.topPos + 38 + this.rowCount * 18);
         this.setButtonsPos();
      }));
      this.setButtonsPos();
      this.onPacket();
   }

   private void setButtonsPos() {
      this.searchField.setX(this.leftPos + 82);
      this.btnClr.setX(this.leftPos + 80);
      int space = this.recipeBookGui.isVisible() ? this.recipeBookGui.searchBox.getY() - 16 : this.imageHeight;
      List<Button> buttons = Arrays.asList(
         this.buttonSortingType,
         this.buttonDirection,
         this.buttonSearchType,
         this.buttonCtrlMode,
         this.buttonGhostMode,
         this.buttonPullFromInv,
         this.buttonTallMode
      );
      int y = this.topPos + 5;
      int x = this.leftPos - 18;

      for (int i = 0; i < buttons.size(); i++) {
         Button b = buttons.get(i);
         if (y + 18 > space) {
            y = this.topPos + 5;
            x -= 18;
         }

         b.setX(x);
         b.setY(y);
         y += 18;
      }
   }

   @Override
   protected void onPacket() {
      super.onPacket();
      boolean pullFromInv = (((CraftingTerminalMenu)this.menu).modes & 32) != 0;
      this.buttonPullFromInv.setState(pullFromInv);
   }

   @Override
   protected int writeModes() {
      int d = super.writeModes();
      return d | (this.buttonPullFromInv.getState() ? 32 : 0);
   }

   @Override
   public void containerTick() {
      super.containerTick();
      this.recipeBookGui.tick();
   }

   @Override
   public void render(GuiGraphics st, int mouseX, int mouseY, float partialTicks) {
      if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
         st.pose().pushPose();
         st.pose().translate(0.0F, 0.0F, -1000.0F);
         super.render(st, -1, -1, partialTicks);
         st.pose().popPose();
         this.recipeBookGui.render(st, mouseX, mouseY, partialTicks);
      } else {
         super.render(st, mouseX, mouseY, partialTicks);
         this.recipeBookGui.render(st, mouseX, mouseY, partialTicks);
         this.recipeBookGui.renderGhostRecipe(st, this.leftPos, this.topPos, true, partialTicks);
      }

      this.renderTooltip(st, mouseX, mouseY);
      this.recipeBookGui.renderTooltip(st, this.leftPos, this.topPos, mouseX, mouseY);
      this.setInitialFocus(this.recipeBookGui);
   }

   protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
      return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super.isHovering(x, y, width, height, mouseX, mouseY);
   }

   @Override
   public boolean mouseClicked(double x, double y, int b) {
      if (this.recipeBookGui.mouseClicked(x, y, b)) {
         this.setFocused(this.recipeBookGui);
         return true;
      }

      if (this.widthTooNarrow && this.recipeBookGui.isVisible()) {
         if (this.recipeBookGui.hasClickedOutside(x, y, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, b)) {
            this.recipeBookGui.toggleVisibility();
         }

         return false;
      } else {
         return super.mouseClicked(x, y, b);
      }
   }

   protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
      boolean flag = mouseX < guiLeftIn || mouseY < guiTopIn || mouseX >= guiLeftIn + this.imageWidth || mouseY >= guiTopIn + this.imageHeight;
      return this.recipeBookGui.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag;
   }

   protected void slotClicked(Slot slotIn, int slotId, int mouseButton, ClickType type) {
      super.slotClicked(slotIn, slotId, mouseButton, type);
      this.recipeBookGui.slotClicked(slotIn);
   }

   public void recipesUpdated() {
      this.recipeBookGui.recipesUpdated();
   }

   public RecipeBookComponent getRecipeBookComponent() {
      return this.recipeBookGui;
   }

   private void clearGrid() {
      this.minecraft.gameMode.handleInventoryButtonClick(((CraftingTerminalMenu)this.menu).containerId, 0);
   }

   @Override
   public boolean keyPressed(int code, int p_231046_2_, int p_231046_3_) {
      if (code == 83 && this.hoveredSlot != null) {
         ItemStack itemstack = null;

         for (int i = 0; i < this.recipeBookGui.ghostRecipe.size(); i++) {
            GhostIngredient ghostrecipe$ghostingredient = this.recipeBookGui.ghostRecipe.get(i);
            int j = ghostrecipe$ghostingredient.getX();
            int k = ghostrecipe$ghostingredient.getY();
            if (j == this.hoveredSlot.x && k == this.hoveredSlot.y) {
               itemstack = ghostrecipe$ghostingredient.getItem();
            }
         }

         if (itemstack != null) {
            this.searchField.setValue(itemstack.getHoverName().getString());
            this.searchField.setFocused(false);
            return true;
         }
      }

      if (code == 256 && this.recipeBookGui.isVisible() && this.widthTooNarrow) {
         this.recipeBookGui.toggleVisibility();
         return true;
      } else {
         return super.keyPressed(code, p_231046_2_, p_231046_3_);
      }
   }

   public static class ButtonClear extends Button {
      private static final WidgetSprites SPRITES = new WidgetSprites(
         ResourceLocation.tryBuild("toms_storage", "widget/clear_button"),
         ResourceLocation.tryBuild("toms_storage", "widget/clear_button_disabled"),
         ResourceLocation.tryBuild("toms_storage", "widget/clear_button_hovered")
      );

      public ButtonClear(int x, int y, OnPress pressable) {
         super(x, y, 11, 11, Component.empty(), pressable, DEFAULT_NARRATION);
      }

      public void renderWidget(GuiGraphics st, int mouseX, int mouseY, float pt) {
         if (this.visible) {
            int x = this.getX();
            int y = this.getY();
            this.isHovered = mouseX >= x && mouseY >= y && mouseX < x + this.width && mouseY < y + this.height;
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            st.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), x, y, this.width, this.height);
         }
      }
   }
}
