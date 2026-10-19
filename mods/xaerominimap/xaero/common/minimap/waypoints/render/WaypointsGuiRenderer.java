package xaero.common.minimap.waypoints.render;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import xaero.common.IXaeroMinimap;
import xaero.common.graphics.CustomRenderTypes;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.common.settings.ModSettings;
import xaero.hud.minimap.BuiltInHudModules;
import xaero.hud.minimap.element.render.MinimapElementRenderInfo;
import xaero.hud.minimap.element.render.MinimapElementRenderLocation;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.world.MinimapWorldManager;

@Deprecated
public final class WaypointsGuiRenderer extends xaero.hud.minimap.waypoint.render.WaypointsGuiRenderer {
   private MinimapElementRenderInfo compatibleRenderInfo;
   private boolean temporaryWaypointsGlobal;

   public WaypointsGuiRenderer(WaypointReader elementReader, WaypointRenderProvider provider, WaypointGuiRenderContext context) {
      super(elementReader, provider, context);
   }

   @Deprecated
   public void updateWaypointCollection(IXaeroMinimap modMain) {
      super.updateWaypointCollection();
      MinimapSession session = BuiltInHudModules.MINIMAP.getCurrentSession();
      MinimapWorldManager manager = session.getWorldManager();
      Camera activeRender = Minecraft.getInstance().gameRenderer.getMainCamera();
      Vec3 cameraPos = activeRender.getPosition();
      this.context.dimCoordinateScale = session.getDimensionHelper().getDimCoordinateScale(manager.getCurrentWorld());
      double cameraPosMultiplier = Minecraft.getInstance().level.dimensionType().coordinateScale() / this.context.dimCoordinateScale;
      Waypoint.RENDER_SORTING_POS = new Vec3(cameraPos.x * cameraPosMultiplier, cameraPos.y, cameraPos.z * cameraPosMultiplier);
   }

   @Deprecated
   public void drawIconOnGUI(
      GuiGraphics guiGraphics,
      MinimapRendererHelper rendererHelper,
      Waypoint w,
      ModSettings settings,
      int drawX,
      int drawY,
      BufferSource renderTypeBuffer,
      VertexConsumer waypointBackgroundConsumer
   ) {
      super.drawIconOnGUI(
         guiGraphics, rendererHelper, w, drawX, drawY, renderTypeBuffer, waypointBackgroundConsumer, renderTypeBuffer.getBuffer(CustomRenderTypes.GUI_NEAREST)
      );
   }

   @Deprecated
   @Override
   public void drawSetChange(WaypointsManager waypointsManager, GuiGraphics guiGraphics, Window res) {
      this.drawSetChange((MinimapSession)waypointsManager, guiGraphics, res);
   }

   @Deprecated
   @Override
   public void drawSetChange(MinimapSession session, GuiGraphics guiGraphics, Window res) {
      super.drawSetChange((MinimapSession)session, guiGraphics, res);
   }

   @Deprecated
   @Override
   public boolean shouldRender(int location) {
      return super.shouldRender(MinimapElementRenderLocation.fromIndex(location));
   }

   @Deprecated
   @Override
   public int getOrder() {
      return super.getOrder();
   }

   @Deprecated
   public boolean renderElement(
      int location,
      boolean highlit,
      boolean outOfBounds,
      GuiGraphics guiGraphics,
      BufferSource renderTypeBuffers,
      Font font,
      RenderTarget framebuffer,
      MinimapRendererHelper helper,
      Entity renderEntity,
      Player player,
      double renderX,
      double renderY,
      double renderZ,
      int elementIndex,
      double optionalDepth,
      float optionalScale,
      Waypoint element,
      double partialX,
      double partialY,
      boolean cave,
      float partialTicks
   ) {
      if (this.compatibleRenderInfo == null) {
         MinimapSession session = BuiltInHudModules.MINIMAP.getCurrentSession();
         this.compatibleRenderInfo = new MinimapElementRenderInfo(
            MinimapElementRenderLocation.fromIndex(location),
            renderEntity,
            player,
            new Vec3(renderX, renderY, renderZ),
            cave,
            partialTicks,
            framebuffer,
            session.getProcessor().getLastMapDimensionScale(),
            session.getProcessor().getLastMapDimension()
         );
      }

      return this.renderElement(
         element, highlit, outOfBounds, optionalDepth, optionalScale, partialX, partialY, this.compatibleRenderInfo, guiGraphics, renderTypeBuffers
      );
   }

   @Deprecated
   @Override
   public void preRender(
      int location,
      Entity renderEntity,
      Player player,
      double renderX,
      double renderY,
      double renderZ,
      IXaeroMinimap modMain,
      BufferSource renderTypeBuffers,
      MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers
   ) {
      this.preRender(this.getPreInfo(location, renderEntity, player, renderX, renderY, renderZ), renderTypeBuffers, multiTextureRenderTypeRenderers);
   }

   @Deprecated
   @Override
   public void postRender(
      int location,
      Entity renderEntity,
      Player player,
      double renderX,
      double renderY,
      double renderZ,
      IXaeroMinimap modMain,
      BufferSource renderTypeBuffers,
      MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers
   ) {
      if (this.compatibleRenderInfo == null) {
         this.compatibleRenderInfo = this.getPreInfo(location, renderEntity, player, renderX, renderY, renderZ);
      }

      this.postRender(this.compatibleRenderInfo, renderTypeBuffers, multiTextureRenderTypeRenderers);
      this.compatibleRenderInfo = null;
   }
}
