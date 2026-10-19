package com.mortal_cultivation.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mortal_cultivation.gui.menu.RuneTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class RuneTableScreen extends AbstractContainerScreen<RuneTableMenu> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/gui/rune_table.png");

   public RuneTableScreen(RuneTableMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 186;
      this.imageHeight = 210;
      this.titleLabelY = -10000;
      this.inventoryLabelY = -10000;
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.render(guiGraphics, mouseX, mouseY, partialTick);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
   }

   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
   }
}
