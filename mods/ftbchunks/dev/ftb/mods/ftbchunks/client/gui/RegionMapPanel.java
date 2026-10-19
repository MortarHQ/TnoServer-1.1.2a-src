package dev.ftb.mods.ftbchunks.client.gui;

import dev.ftb.mods.ftbchunks.api.client.event.MapIconEvent;
import dev.ftb.mods.ftbchunks.api.client.icon.MapIcon;
import dev.ftb.mods.ftbchunks.api.client.icon.MapType;
import dev.ftb.mods.ftbchunks.client.map.MapChunk;
import dev.ftb.mods.ftbchunks.client.map.MapRegion;
import dev.ftb.mods.ftbchunks.client.map.MapRegionData;
import dev.ftb.mods.ftbchunks.client.mapicon.MapIconComparator;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.math.XZ;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbteams.api.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

public class RegionMapPanel extends Panel {
   final LargeMapScreen largeMap;
   double regionX = 0.0;
   double regionZ = 0.0;
   int regionMinX;
   int regionMinZ;
   int regionMaxX;
   int regionMaxZ;
   int blockX = 0;
   int blockY = -32767;
   int blockZ = 0;
   int blockIndex = 0;
   private final List<MapIcon> mapIcons;

   public RegionMapPanel(LargeMapScreen panel) {
      super(panel);
      this.largeMap = panel;
      this.mapIcons = new ArrayList<>();
   }

   public void updateMinMax() {
      this.regionMinX = Integer.MAX_VALUE;
      this.regionMinZ = Integer.MAX_VALUE;
      this.regionMaxX = Integer.MIN_VALUE;
      this.regionMaxZ = Integer.MIN_VALUE;

      for (Widget w : this.widgets) {
         if (w instanceof MapTileWidget tileWidget) {
            int qx = tileWidget.region.pos.x();
            int qy = tileWidget.region.pos.z();
            this.regionMinX = Math.min(this.regionMinX, qx);
            this.regionMinZ = Math.min(this.regionMinZ, qy);
            this.regionMaxX = Math.max(this.regionMaxX, qx);
            this.regionMaxZ = Math.max(this.regionMaxZ, qy);
         }
      }

      if (this.regionMinX == Integer.MAX_VALUE) {
         this.regionMinX = this.regionMinZ = this.regionMaxX = this.regionMaxZ = 0;
      }

      this.regionMinX -= 100;
      this.regionMinZ -= 100;
      this.regionMaxX += 101;
      this.regionMaxZ += 101;
   }

   public BlockPos blockPos() {
      return new BlockPos(this.blockX, this.blockY, this.blockZ);
   }

   public void scrollTo(double x, double y) {
      this.updateMinMax();
      double dx = this.regionMaxX - this.regionMinX;
      double dy = this.regionMaxZ - this.regionMinZ;
      this.setScrollX((x - this.regionMinX) / dx * this.largeMap.scrollWidth - this.width / 2.0);
      this.setScrollY((y - this.regionMinZ) / dy * this.largeMap.scrollHeight - this.height / 2.0);
   }

   public void resetScroll() {
      this.alignWidgets();
      this.setScrollX((this.largeMap.scrollWidth - this.width) / 2.0);
      this.setScrollY((this.largeMap.scrollHeight - this.height) / 2.0);
   }

   public void addWidgets() {
      for (MapRegion region : this.largeMap.dimension.getRegions().values()) {
         this.add(new MapTileWidget(this, region));
      }

      Minecraft mc = Minecraft.getInstance();
      this.mapIcons.clear();
      ((Consumer)MapIconEvent.LARGE_MAP.invoker()).accept(new MapIconEvent(this.largeMap.dimension.dimension, this.mapIcons, MapType.LARGE_MAP));
      if (this.mapIcons.size() >= 2) {
         this.mapIcons.sort(new MapIconComparator(mc.player.position(), 1.0F));
      }

      for (MapIcon icon : this.mapIcons) {
         if (icon.isVisible(MapType.LARGE_MAP, MathUtils.dist(mc.player.getX(), mc.player.getZ(), icon.getPos(1.0F).x, icon.getPos(1.0F).z), false)) {
            this.add(new MapIconWidget(this, icon));
         }
      }

      this.alignWidgets();
   }

   public void alignWidgets() {
      this.largeMap.scrollWidth = 0;
      this.largeMap.scrollHeight = 0;
      this.updateMinMax();
      int buttonSize = this.largeMap.getRegionTileSize();
      this.largeMap.scrollWidth = (this.regionMaxX - this.regionMinX) * buttonSize;
      this.largeMap.scrollHeight = (this.regionMaxZ - this.regionMinZ) * buttonSize;

      for (Widget w : this.widgets) {
         if (w instanceof MapTileWidget tileWidget) {
            double qx = tileWidget.region.pos.x();
            double qy = tileWidget.region.pos.z();
            double qw = 1.0;
            double qh = 1.0;
            double x = (qx - this.regionMinX) * buttonSize;
            double y = (qy - this.regionMinZ) * buttonSize;
            w.setPosAndSize((int)x, (int)y, (int)(buttonSize * qw), (int)(buttonSize * qh));
         } else if (w instanceof MapIconWidget iconWidget) {
            MapIcon mapIcon = iconWidget.getMapIcon();
            double s = Math.max(mapIcon.isZoomDependant(MapType.LARGE_MAP) ? 0.0 : 6.0, buttonSize / 128.0 * mapIcon.getIconScale(MapType.LARGE_MAP));
            if (s <= 1.0) {
               w.setSize(0, 0);
            } else {
               w.setSize(Mth.ceil(s), Mth.ceil(s));
               iconWidget.updatePosition(1.0F);
            }
         }
      }

      this.setPosAndSize(0, 0, this.parent.width, this.parent.height);
   }

   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      super.draw(graphics, theme, x, y, w, h);
      int dx = this.regionMaxX - this.regionMinX;
      int dy = this.regionMaxZ - this.regionMinZ;
      double px = this.getScrollX() - this.getX();
      double py = this.getScrollY() - this.getY();
      this.regionX = (this.parent.getMouseX() + px) / this.largeMap.scrollWidth * dx + this.regionMinX;
      this.regionZ = (this.parent.getMouseY() + py) / this.largeMap.scrollHeight * dy + this.regionMinZ;
      this.blockX = Mth.floor(this.regionX * 512.0);
      this.blockZ = Mth.floor(this.regionZ * 512.0);
      this.blockIndex = (this.blockX & 511) + (this.blockZ & 511) * 512;
      this.blockY = -32767;
      MapRegion region = this.largeMap.dimension.getRegions().get(XZ.regionFromBlock(this.blockX, this.blockZ));
      if (region != null) {
         MapRegionData data = region.getData();
         if (data != null) {
            this.blockY = data.height[this.blockIndex];
         }
      }
   }

   public void addMouseOverText(TooltipList list) {
      super.addMouseOverText(list);
      MapRegion mapRegion = this.largeMap.dimension.getRegions().get(XZ.regionFromBlock(this.blockX, this.blockZ));
      if (mapRegion != null) {
         MapRegionData data = mapRegion.getData();
         if (data != null) {
            MapChunk mapChunk = mapRegion.getMapChunk(XZ.of(this.blockX >> 4 & 31, this.blockZ >> 4 & 31));
            Team team = mapChunk == null ? null : mapChunk.getTeam().orElse(null);
            if (team != null) {
               list.add(team.getName());
            }
         }
      }
   }

   public boolean mousePressed(MouseButton button) {
      if (super.mousePressed(button)) {
         return true;
      } else if (button.isLeft() && this.isMouseOver()) {
         this.largeMap.prevMouseX = this.getMouseX();
         this.largeMap.prevMouseY = this.getMouseY();
         this.largeMap.grabbed = 1;
         return true;
      } else {
         return false;
      }
   }

   public void mouseReleased(MouseButton button) {
      super.mouseReleased(button);
      this.largeMap.grabbed = 0;
   }

   public boolean scrollPanel(double scroll) {
      if (this.isMouseOver()) {
         this.largeMap.addZoom(scroll);
         return true;
      } else {
         return false;
      }
   }
}
