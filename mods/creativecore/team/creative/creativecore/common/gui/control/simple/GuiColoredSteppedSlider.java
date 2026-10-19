package team.creative.creativecore.common.gui.control.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.client.render.GuiRenderHelper;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.mc.ColorUtils;
import team.creative.creativecore.common.util.type.Color;

public class GuiColoredSteppedSlider extends GuiSteppedSlider {
   public GuiColorPicker picker;
   public ColorUtils.ColorPart part;

   public GuiColoredSteppedSlider(String name, GuiColorPicker picker, ColorUtils.ColorPart part) {
      super(name, part.get(picker.color), 0, 255);
      this.picker = picker;
      this.part = part;
   }

   @Override
   public void setValue(double value) {
      super.setValue((int)value);
      if (this.part != null) {
         this.part.set(this.picker.color, this.getIntValue());
         this.picker.onColorChanged();
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY) {
      if (this.part == ColorUtils.ColorPart.ALPHA) {
         Color startColor = new Color(this.picker.color);
         startColor.setAlpha(0);
         Color endColor = new Color(this.picker.color);
         endColor.setAlpha(255);
         GuiRenderHelper.horizontalGradientRect(graphics, 0, 0, this.rect.getContentWidth(), this.rect.getContentHeight(), startColor.toInt(), endColor.toInt());
      } else {
         GuiRenderHelper.horizontalGradientMaskRect(
            graphics, 0, 0, this.rect.getContentWidth(), this.rect.getContentHeight(), this.picker.color.toInt(), this.part.code
         );
      }

      super.renderContent(graphics, controlRect, realRect, scale, mouseX, mouseY);
   }
}
