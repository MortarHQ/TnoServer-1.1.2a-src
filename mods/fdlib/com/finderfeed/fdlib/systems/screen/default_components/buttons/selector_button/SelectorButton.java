package com.finderfeed.fdlib.systems.screen.default_components.buttons.selector_button;

import com.finderfeed.fdlib.systems.screen.FDScreen;
import com.finderfeed.fdlib.systems.screen.default_components.buttons.ButtonComponent;
import com.finderfeed.fdlib.systems.screen.default_components.buttons.OnFDButtonPress;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class SelectorButton<T> extends ButtonComponent {
   protected T value;
   protected String name;
   protected float r = 0.25F;
   protected float g = 0.25F;
   protected float b = 0.25F;
   protected float a = 1.0F;

   public SelectorButton(T value, String name, FDScreen screen, String uniqueId, float x, float y, float width, float height, OnFDButtonPress onPress) {
      super(screen, uniqueId, x, y, width, height, onPress);
      this.name = name;
      this.value = value;
   }

   @Override
   public void renderComponent(GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
      if (this.isHovered()) {
         FDRenderUtil.fill(graphics.pose(), x, y, this.getWidth(), this.getHeight(), this.r + 0.05F, this.g + 0.05F, this.b + 0.05F, this.a);
      } else {
         FDRenderUtil.fill(graphics.pose(), x, y, this.getWidth(), this.getHeight(), this.r, this.g, this.b, this.a);
      }

      Font font = Minecraft.getInstance().font;
      float w = font.width(this.name);
      float mod = Mth.clamp((this.getWidth() - 2.0F) / w, 0.0F, 1.0F);
      FDRenderUtil.renderCenteredText(graphics, x + this.getWidth() / 2.0F, y + 2.0F, mod, false, this.name, 16777215);
   }
}
