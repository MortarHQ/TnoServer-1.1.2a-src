package dev.ftb.mods.ftbchunks.client.gui;

import dev.ftb.mods.ftbchunks.api.client.icon.MapIcon;
import dev.ftb.mods.ftbchunks.api.client.icon.MapType;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MapIconWidget extends Widget {
   private final MapIcon mapIcon;
   private final RegionMapPanel regionMapPanel;

   public MapIconWidget(RegionMapPanel panel, MapIcon m) {
      super(panel);
      this.mapIcon = m;
      this.regionMapPanel = panel;
   }

   public MapIcon getMapIcon() {
      return this.mapIcon;
   }

   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      if (this.width > 0 && this.height > 0) {
         this.updatePosition(this.regionMapPanel.getPartialTicks());
         this.mapIcon.draw(MapType.LARGE_MAP, graphics, x, y, w, h, false, 255);
      }
   }

   public void addMouseOverText(TooltipList list) {
      if (this.width > 0 && this.height > 0 && !list.shouldRender()) {
         this.mapIcon.addTooltip(list);
      }
   }

   public boolean mousePressed(MouseButton button) {
      return this.isMouseOver() && this.width > 0 && this.height > 0 && this.mapIcon.onMousePressed(this.regionMapPanel.largeMap, button)
         ? true
         : super.mousePressed(button);
   }

   public boolean keyPressed(Key key) {
      return this.isMouseOver() && this.width > 0 && this.height > 0 && this.mapIcon.onKeyPressed(this.regionMapPanel.largeMap, key)
         ? true
         : super.keyPressed(key);
   }

   public void updatePosition(float delta) {
      int size = this.regionMapPanel.largeMap.getRegionTileSize();
      Vec3 pos = this.mapIcon.getPos(delta);
      double qx = pos.x / 512.0;
      double qy = pos.z / 512.0;
      double x = (qx - this.regionMapPanel.regionMinX) * size - this.width / 2.0;
      double y = (qy - this.regionMapPanel.regionMinZ) * size - (this.mapIcon.isIconOnEdge(MapType.LARGE_MAP, false) ? this.height : this.height / 2.0);
      this.setPos(Mth.floor(x), Mth.floor(y));
   }
}
