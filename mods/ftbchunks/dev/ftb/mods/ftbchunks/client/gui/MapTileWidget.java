package dev.ftb.mods.ftbchunks.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.ftb.mods.ftbchunks.client.map.MapRegion;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class MapTileWidget extends Widget {
   public final MapRegion region;

   public MapTileWidget(RegionMapPanel panel, MapRegion region) {
      super(panel);
      this.region = region;
   }

   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      int id = this.region.getRenderedMapImageTextureId();
      if (this.region.isMapImageLoaded()) {
         RenderSystem.bindTextureForSetup(id);
         int filter = w * Minecraft.getInstance().getWindow().getGuiScale() < 512.0 ? 9729 : 9728;
         RenderSystem.texParameter(3553, 10241, filter);
         RenderSystem.texParameter(3553, 10240, filter);
         RenderSystem.setShaderTexture(0, id);
         GuiHelper.drawTexturedRect(graphics, x, y, w, h, Color4I.WHITE, 0.0F, 0.0F, 1.0F, 1.0F);
      }
   }
}
