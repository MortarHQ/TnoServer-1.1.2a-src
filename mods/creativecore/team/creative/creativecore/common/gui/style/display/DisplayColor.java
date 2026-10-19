package team.creative.creativecore.common.gui.style.display;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.util.mc.ColorUtils;
import team.creative.creativecore.common.util.type.Color;

@OnlyIn(Dist.CLIENT)
@Environment(EnvType.CLIENT)
public class DisplayColor extends StyleDisplay {
   public int color;

   public DisplayColor() {
      this.set(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public DisplayColor(int color) {
      this.set(ColorUtils.redF(color), ColorUtils.greenF(color), ColorUtils.blueF(color), ColorUtils.alphaF(color));
   }

   public DisplayColor(float r, float g, float b, float a) {
      this.set(r, g, b, a);
   }

   public DisplayColor(Color color) {
      this.set(color.getRedDecimal(), color.getGreenDecimal(), color.getBlueDecimal(), color.getAlphaDecimal());
   }

   public void set(int color) {
      this.set(ColorUtils.redF(color), ColorUtils.greenF(color), ColorUtils.blueF(color), ColorUtils.alphaF(color));
   }

   public DisplayColor(int red, int green, int blue, int alpha) {
      this.color = ColorUtils.rgba(red, green, blue, alpha);
   }

   public void set(float r, float g, float b, float a) {
      this.color = ColorUtils.rgba(r, g, b, a);
   }

   public void set(Color color) {
      this.set(color.getRedDecimal(), color.getGreenDecimal(), color.getBlueDecimal(), color.getAlphaDecimal());
   }

   @Override
   public void render(GuiGraphics graphics, double x, double y, double width, double height) {
      graphics.fill((int)x, (int)y, (int)(x + width), (int)(y + height), this.color);
   }
}
