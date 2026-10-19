package com.bobmowzie.mowziesmobs.client.gui;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.Trade;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerUmvuthanaTrade;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class GuiUmvuthanaTrade extends AbstractContainerScreen<ContainerUmvuthanaTrade> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/gui/container/umvuthana.png");
   private final EntityUmvuthanaMinion umvuthana;

   public GuiUmvuthanaTrade(ContainerUmvuthanaTrade screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);
      this.umvuthana = screenContainer.getUmvuthana();
   }

   public boolean mouseReleased(double mouseX, double mouseY, int state) {
      return super.mouseReleased(mouseX, mouseY, state);
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      if (this.umvuthana != null) {
         this.umvuthana.renderingInGUI = true;
         InventoryScreen.renderEntityInInventoryFollowsMouse(
            guiGraphics, this.leftPos + 8, this.topPos + 8, this.leftPos + 59, this.topPos + 69, 20, 0.25F, x, y, this.umvuthana
         );
         this.umvuthana.renderingInGUI = false;
      }
   }

   protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
      String title = I18n.get("entity.mowziesmobs.umvuthana.trade", new Object[0]);
      guiGraphics.drawString(this.font, title, (int)(this.imageWidth / 2.0F - this.font.width(title) / 2.0F + 26.0F), 6, 4210752, false);
      guiGraphics.drawString(this.font, I18n.get("container.inventory", new Object[0]), 8, this.imageHeight - 96 + 2, 4210752, false);
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
      if (this.umvuthana != null && this.umvuthana.isOfferingTrade()) {
         Trade trade = this.umvuthana.getOfferingTrade();
         ItemStack input = trade.getInput();
         ItemStack output = trade.getOutput();
         guiGraphics.pose().pushPose();
         guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
         guiGraphics.renderItem(input, this.leftPos + 80, this.topPos + 24);
         guiGraphics.renderItemDecorations(this.font, input, this.leftPos + 80, this.topPos + 24);
         guiGraphics.renderItem(output, this.leftPos + 134, this.topPos + 24);
         guiGraphics.renderItemDecorations(this.font, output, this.leftPos + 134, this.topPos + 24);
         if (this.isHovering(80, 24, 16, 16, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, input, mouseX, mouseY);
         } else if (this.isHovering(134, 24, 16, 16, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, output, mouseX, mouseY);
         }

         guiGraphics.pose().popPose();
      }
   }
}
