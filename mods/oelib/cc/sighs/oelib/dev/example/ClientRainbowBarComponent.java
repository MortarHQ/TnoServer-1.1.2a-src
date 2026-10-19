package cc.sighs.oelib.dev.example;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientRainbowBarComponent implements ClientTooltipComponent {
   private static final int BAR_WIDTH = 100;
   private static final int BAR_HEIGHT = 2;

   public ClientRainbowBarComponent(RainbowBarComponent ignored) {
   }

   public int getHeight() {
      return 4;
   }

   public int getWidth(Font font) {
      return 104;
   }

   public void renderImage(Font font, int tooltipX, int tooltipY, GuiGraphics guiGraphics) {
      int x0 = tooltipX + 2;
      int y0 = tooltipY + 1;
      float time = (float)(System.currentTimeMillis() % 10000L) / 10000.0F;
      float speed = 0.5F;

      for (int x = 0; x < 100; x++) {
         float hue = (x / 100.0F + time * speed) % 1.0F;
         int rgb = Mth.hsvToRgb(hue, 1.0F, 1.0F);
         guiGraphics.fill(x0 + x, y0, x0 + x + 1, y0 + 2, 0xFF000000 | rgb);
      }
   }
}
