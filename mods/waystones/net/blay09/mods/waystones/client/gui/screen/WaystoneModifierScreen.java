package net.blay09.mods.waystones.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.blay09.mods.waystones.block.WarpPlateBlock;
import net.blay09.mods.waystones.menu.WaystoneModifierMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WaystoneModifierScreen extends AbstractContainerScreen<WaystoneModifierMenu> {
   private static final ResourceLocation WARP_PLATE_GUI_TEXTURES = ResourceLocation.fromNamespaceAndPath(
      "waystones", "textures/gui/menu/waystone_modifiers.png"
   );

   public WaystoneModifierScreen(WaystoneModifierMenu menu, Inventory playerInventory, Component title) {
      super(menu, playerInventory, title);
      this.imageHeight = 196;
      this.inventoryLabelY = 93;
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      guiGraphics.blit(WARP_PLATE_GUI_TEXTURES, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
   }

   protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
      super.renderLabels(guiGraphics, x, y);
      Component galacticName = WarpPlateBlock.getGalacticName(((WaystoneModifierMenu)this.menu).getWaystone());
      int width = this.font.width(galacticName);
      guiGraphics.drawString(this.font, galacticName, this.imageWidth - width - 5, 5, -1);
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }
}
