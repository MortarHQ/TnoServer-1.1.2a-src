package team.creative.creativecore.common.gui.style.display;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import team.creative.creativecore.client.render.GuiRenderHelper;

public class DisplayTextureRepeat extends DisplayTexture {
   public int w;
   public int h;

   public DisplayTextureRepeat() {
   }

   public DisplayTextureRepeat(ResourceLocation location, int u, int v, int width, int height) {
      super(location, u, v);
      this.w = width;
      this.h = height;
   }

   @Override
   public void render(GuiGraphics graphics, double x, double y, double width, double height) {
      RenderSystem.setShaderTexture(0, this.location);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int renderedX = 0;

      while (renderedX < (int)width) {
         int renderedY = 0;
         int renderedWidth = Math.min(this.w, (int)width - renderedX);

         while (renderedY < (int)height) {
            int renderedHeight = Math.min(this.h, (int)height - renderedY);
            GuiRenderHelper.textureRect(
               graphics, (int)x + renderedX, renderedY, (int)y + renderedWidth, renderedHeight, this.u, this.v, this.u + renderedWidth, this.v + renderedHeight
            );
            renderedY += renderedHeight;
         }

         renderedX += renderedWidth;
      }
   }
}
