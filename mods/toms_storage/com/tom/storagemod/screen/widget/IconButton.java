package com.tom.storagemod.screen.widget;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IconButton extends Button {
   protected static final WidgetSprites SPRITES = new WidgetSprites(
      ResourceLocation.tryBuild("toms_storage", "widget/slot_button"),
      ResourceLocation.tryBuild("toms_storage", "widget/slot_button_disabled"),
      ResourceLocation.tryBuild("toms_storage", "widget/slot_button_hovered")
   );
   protected Component name;
   protected ResourceLocation icon;

   public IconButton(int x, int y, Component name, ResourceLocation icon, OnPress pressable) {
      super(x, y, 16, 16, Component.empty(), pressable, Button.DEFAULT_NARRATION);
      this.name = name;
      this.icon = icon;
   }

   public void renderWidget(GuiGraphics st, int mouseX, int mouseY, float pt) {
      if (this.visible) {
         int x = this.getX();
         int y = this.getY();
         st.setColor(1.0F, 1.0F, 1.0F, this.alpha);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         this.isHovered = mouseX >= x && mouseY >= y && mouseX < x + this.width && mouseY < y + this.height;
         st.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
         this.drawIcon(st, mouseX, mouseY, pt);
         st.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   protected void drawIcon(GuiGraphics st, int mouseX, int mouseY, float pt) {
      st.blitSprite(this.getIcon(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
   }

   public ResourceLocation getIcon() {
      return this.icon;
   }
}
