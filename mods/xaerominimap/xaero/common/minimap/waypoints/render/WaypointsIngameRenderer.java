package xaero.common.minimap.waypoints.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import xaero.common.HudMod;
import xaero.common.graphics.CustomRenderTypes;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.settings.ModSettings;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.config.util.MinimapConfigClientUtils;
import xaero.hud.minimap.element.render.MinimapElementReader;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.waypoint.render.world.WaypointWorldRenderContext;
import xaero.hud.minimap.waypoint.render.world.WaypointWorldRenderProvider;
import xaero.hud.minimap.waypoint.render.world.WaypointWorldRenderer;
import xaero.lib.client.config.ClientConfigManager;

@Deprecated
public class WaypointsIngameRenderer extends WaypointWorldRenderer {
   private GuiGraphics guiGraphics;
   private Vector4f origin4f = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);

   public WaypointsIngameRenderer(
      MinimapElementReader<Waypoint, WaypointWorldRenderContext> elementReader,
      WaypointWorldRenderProvider provider,
      WaypointWorldRenderContext context,
      Vector4f origin4f
   ) {
      super(elementReader, provider, context);
   }

   @Deprecated
   public void render(MinimapSession session, float partial, MinimapProcessor minimap, Matrix4f waypointsProjection, Matrix4f worldModelView) {
      Vec3 renderPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
      if (this.guiGraphics == null) {
         this.guiGraphics = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
      }

      HudMod.INSTANCE.getMinimap().getWorldRendererHandler().prepareRender(waypointsProjection, worldModelView);
      Minecraft mc = Minecraft.getInstance();
      HudMod.INSTANCE
         .getMinimap()
         .getWorldRendererHandler()
         .render(this.guiGraphics, renderPos, partial, null, mc.level.dimensionType().coordinateScale(), mc.level.dimension());
   }

   @Deprecated
   public void drawAsOverlay(
      PoseStack matrixStack,
      PoseStack matrixStackOverlay,
      MinimapRendererHelper helper,
      Waypoint w,
      ModSettings settings,
      Tesselator tessellator,
      Font fontrenderer,
      String name,
      String distance,
      float textSize,
      boolean showDistance,
      BufferSource renderTypeBuffer,
      VertexConsumer waypointBackgroundConsumer,
      Matrix4f waypointsProjection,
      int screenWidth,
      int screenHeight,
      double depthClamp,
      double depth,
      boolean isTheMain,
      String subworldName
   ) {
      this.origin4f.mul(matrixStack.last().pose());
      this.origin4f.mul(waypointsProjection);
      int overlayPosX = (int)((1.0F + this.origin4f.x() / this.origin4f.w()) / 2.0F * screenWidth);
      int overlayPosY = (int)((1.0F - this.origin4f.y() / this.origin4f.w()) / 2.0F * screenHeight);
      this.origin4f.set(0.0F, 0.0F, 0.0F, 1.0F);
      matrixStackOverlay.translate(overlayPosX, overlayPosY, 0.0F);
      if (depth < depthClamp) {
         float scale = (float)(depthClamp / depth);
         matrixStackOverlay.scale(scale, scale, scale);
      }

      this.drawIconInWorld(
         matrixStackOverlay,
         helper,
         w,
         settings,
         tessellator,
         fontrenderer,
         name,
         distance,
         textSize,
         showDistance,
         renderTypeBuffer,
         waypointBackgroundConsumer,
         isTheMain,
         subworldName
      );
   }

   @Deprecated
   public void drawIconInWorld(
      PoseStack matrixStack,
      MinimapRendererHelper helper,
      Waypoint w,
      ModSettings settings,
      Tesselator tessellator,
      Font fontRenderer,
      String name,
      String distanceText,
      float textSize,
      boolean displayingDistance,
      BufferSource bufferSource,
      VertexConsumer waypointBackgroundConsumer,
      boolean isTheMain,
      String subWorldName
   ) {
      if (!displayingDistance) {
         distanceText = null;
      }

      ClientConfigManager configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
      this.texturedIconConsumer = bufferSource.getBuffer(CustomRenderTypes.GUI_NEAREST);
      this.waypointBackgroundConsumer = waypointBackgroundConsumer;
      this.opacity = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.WAYPOINT_OPACITY_IN_WORLD);
      float iconScale = MinimapConfigClientUtils.getUIScale(configManager, MinimapProfiledConfigOptions.WAYPOINT_ICON_SCALE_IN_WORLD);
      int distanceTextScale = (int)Math.ceil(MinimapConfigClientUtils.getUIScale(configManager, MinimapProfiledConfigOptions.WAYPOINT_DISTANCE_SCALE_IN_WORLD));
      int nameScale = (int)MinimapConfigClientUtils.getUIScale(configManager, MinimapProfiledConfigOptions.WAYPOINT_NAME_SCALE_IN_WORLD, 0.5);
      if (Minecraft.getInstance().isEnforceUnicode()) {
         iconScale = (float)(Math.ceil(iconScale / 2.0F) * 2.0);
         distanceTextScale = (distanceTextScale + 1) / 2 * 2;
         nameScale = (nameScale + 1) / 2 * 2;
      }

      int halfIconPixel = (int)iconScale / 2;
      matrixStack.translate(halfIconPixel, 0.0F, 0.0F);
      this.renderIconWithLabels(
         w, isTheMain, name, distanceText, subWorldName, iconScale, nameScale, distanceTextScale, fontRenderer, halfIconPixel, matrixStack, bufferSource
      );
   }
}
