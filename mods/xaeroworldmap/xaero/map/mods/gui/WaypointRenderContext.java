package xaero.map.mods.gui;

import com.mojang.blaze3d.vertex.VertexConsumer;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;

public class WaypointRenderContext {
   public VertexConsumer regularUIObjectConsumer;
   public VertexConsumer textBGConsumer;
   public MultiTextureRenderTypeRenderer uniqueTextureUIObjectRenderer;
   public float worldmapWaypointsScale;
   public boolean deathpoints;
   public double userScale;
   public boolean waypointBackgrounds;
   public double minZoomForLocalWaypoints;
   public boolean showDisabledWaypoints;
}
