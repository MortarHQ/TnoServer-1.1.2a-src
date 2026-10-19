package xaero.map.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.Supplier;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xaero.lib.client.gui.widget.Tooltip;

public class GuiTexturedButton extends TooltipButton {
   protected int textureX;
   protected int textureY;
   protected int textureW;
   protected int textureH;
   protected ResourceLocation texture;

   public GuiTexturedButton(
      int x, int y, int w, int h, int textureX, int textureY, int textureW, int textureH, ResourceLocation texture, OnPress onPress, Supplier<Tooltip> tooltip
   ) {
      super(x, y, w, h, Component.literal(""), onPress, tooltip);
      this.textureX = textureX;
      this.textureY = textureY;
      this.textureW = textureW;
      this.textureH = textureH;
      this.texture = texture;
   }

   public Component getMessage() {
      return (Component)(this.tooltipSupplier != null ? Component.literal(this.tooltipSupplier.get().getPlainText()) : super.getMessage());
   }

   public void renderWidget(GuiGraphics guiGraphics, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
      int iconX = this.getX() + this.width / 2 - this.textureW / 2;
      int iconY = this.getY() + this.height / 2 - this.textureH / 2;
      if (this.active) {
         if (this.isHovered) {
            iconY--;
            RenderSystem.setShaderColor(0.9F, 0.9F, 0.9F, 1.0F);
         } else {
            RenderSystem.setShaderColor(0.9882F, 0.9882F, 0.9882F, 1.0F);
         }
      } else {
         RenderSystem.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
      }

      if (this.isFocused()) {
         guiGraphics.fill(iconX, iconY, iconX + this.textureW, iconY + this.textureH, 1442840575);
      }

      guiGraphics.blit(this.texture, iconX, iconY, this.textureX, this.textureY, this.textureW, this.textureH);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
