package com.mortal_cultivation.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mortal_cultivation.gui.menu.AlchemyFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlchemyFurnaceScreen extends AbstractContainerScreen<AlchemyFurnaceMenu> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/gui/alchemy_furnace_table.png");
   private final int textureWidth = 176;
   private final int textureHeight = 166;
   private static final int BURN_INDICATOR_X = 43;
   private static final int BURN_INDICATOR_Y = 43;
   private static final int BURN_INDICATOR_WIDTH = 14;
   private static final int BURN_INDICATOR_HEIGHT = 14;
   private static final int PROGRESS_ARROW_X = 82;
   private static final int PROGRESS_ARROW_Y = 43;
   private static final int PROGRESS_ARROW_WIDTH = 24;
   private static final int PROGRESS_ARROW_HEIGHT = 17;

   public AlchemyFurnaceScreen(AlchemyFurnaceMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.titleLabelY = -999;
   }

   protected void init() {
      super.init();
      this.titleLabelY = -999;
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, TEXTURE);
      int x = (this.width - this.imageWidth) / 2;
      int y = (this.height - this.imageHeight) / 2;
      guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
      if (((AlchemyFurnaceMenu)this.menu).isBurning()) {
         int burnProgress = ((AlchemyFurnaceMenu)this.menu).getBurnProgress();
         int burnHeight = 14 * burnProgress / 100;
         if (burnHeight > 0) {
            guiGraphics.blit(TEXTURE, x + 43, y + 43 + 14 - burnHeight, 176, 14 - burnHeight, 14, burnHeight);
         }
      }

      int cookProgress = ((AlchemyFurnaceMenu)this.menu).getCookProgress();
      if (cookProgress > 0) {
         int progressWidth = 24 * cookProgress / 100;
         guiGraphics.blit(TEXTURE, x + 82, y + 43, 176, 14, progressWidth, 17);
      }
   }

   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
   }
}
