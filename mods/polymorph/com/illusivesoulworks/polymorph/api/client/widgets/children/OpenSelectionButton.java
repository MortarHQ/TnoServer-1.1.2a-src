package com.illusivesoulworks.polymorph.api.client.widgets.children;

import com.illusivesoulworks.polymorph.platform.Services;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nonnull;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class OpenSelectionButton extends ImageButton {
   private final AbstractContainerScreen<?> containerScreen;
   private int xOffset;
   private int yOffset;

   public OpenSelectionButton(AbstractContainerScreen<?> containerScreen, int x, int y, WidgetSprites sprites, OnPress onPress) {
      super(0, 0, 16, 16, sprites, onPress);
      this.containerScreen = containerScreen;
      this.xOffset = x;
      this.yOffset = y;
   }

   public void setOffsets(int x, int y) {
      this.xOffset = x;
      this.yOffset = y;
   }

   public void renderWidget(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      this.setX(Services.CLIENT_PLATFORM.getScreenLeft(this.containerScreen) + this.xOffset);
      this.setY(Services.CLIENT_PLATFORM.getScreenTop(this.containerScreen) + this.yOffset);
      super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
   }
}
