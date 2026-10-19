package com.foxablazeaqzl.wiki.client;

import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

final class FoxWikiButton extends SimpleButton {
   FoxWikiButton(int x, int y, int width, int height, ResourceLocation texture, Component text, Component tooltip, OnPress onPress) {
      super(x, y, width, height, texture, text, tooltip, onPress);
   }

   protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      super.renderWidget(graphics, mouseX, mouseY, partialTick);
      if ((Boolean)this.renderCheck.get() && FoxWikiUnreadState.hasUnreadWiki()) {
         FoxWikiMarker.render(graphics, this.getX() + this.getWidth() - 8 - 1, this.getY() + 1, 8);
      }
   }
}
