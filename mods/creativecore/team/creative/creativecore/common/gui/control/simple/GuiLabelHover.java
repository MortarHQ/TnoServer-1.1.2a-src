package team.creative.creativecore.common.gui.control.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.mc.ColorUtils;

public class GuiLabelHover extends GuiLabel {
   public int darken = ColorUtils.rgb(0, 0, 40);

   public GuiLabelHover(String name) {
      super(name);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY) {
      int color = this.text.getDefaultColor();
      if (realRect.inside(mouseX, mouseY)) {
         this.text.setDefaultColor(ColorUtils.substract(color, this.darken));
      }

      this.text.render(graphics);
      this.text.setDefaultColor(color);
   }
}
