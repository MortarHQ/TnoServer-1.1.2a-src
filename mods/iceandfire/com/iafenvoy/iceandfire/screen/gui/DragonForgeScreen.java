package com.iafenvoy.iceandfire.screen.gui;

import com.iafenvoy.iceandfire.screen.handler.DragonForgeScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Locale;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DragonForgeScreen extends AbstractContainerScreen<DragonForgeScreenHandler> {
   public DragonForgeScreen(DragonForgeScreenHandler container, Inventory inv, Component name) {
      super(container, inv, name);
   }

   protected void renderLabels(GuiGraphics pGuiGraphics, int mouseX, int mouseY) {
      assert this.minecraft != null;
      Font textRenderer = this.minecraft.font;
      String s = I18n.get("block.iceandfire.dragonforge_" + ((DragonForgeScreenHandler)this.menu).getDragonType().name() + "_core", new Object[0]);
      pGuiGraphics.drawString(this.font, s, this.imageWidth / 2 - textRenderer.width(s) / 2, 6, 4210752, false);
      pGuiGraphics.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752, false);
   }

   protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
         "iceandfire", String.format(Locale.ROOT, "textures/gui/dragonforge_%s.png", ((DragonForgeScreenHandler)this.menu).getDragonType().name())
      );
      int k = (this.width - this.imageWidth) / 2;
      int l = (this.height - this.imageHeight) / 2;
      pGuiGraphics.blit(texture, k, l, 0, 0, this.imageWidth, this.imageHeight);
      if (((DragonForgeScreenHandler)this.menu).getMaxCookTime() > 0) {
         pGuiGraphics.blit(
            texture,
            k + 12,
            l + 23,
            0,
            166,
            125 * ((DragonForgeScreenHandler)this.menu).getCookTime() / ((DragonForgeScreenHandler)this.menu).getMaxCookTime(),
            38
         );
      }
   }

   public void render(GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(context, mouseX, mouseY, partialTicks);
      super.render(context, mouseX, mouseY, partialTicks);
      this.renderTooltip(context, mouseX, mouseY);
   }
}
