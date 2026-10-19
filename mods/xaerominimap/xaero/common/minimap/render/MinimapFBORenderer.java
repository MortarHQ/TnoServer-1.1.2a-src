package xaero.common.minimap.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL11;
import xaero.common.HudMod;
import xaero.common.graphics.CustomRenderTypes;
import xaero.common.graphics.CustomVertexConsumers;
import xaero.common.graphics.ImprovedFramebuffer;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.region.MinimapChunk;
import xaero.common.misc.OptimizedMath;
import xaero.common.settings.ModSettings;
import xaero.hud.compat.mods.ImmediatelyFastHelper;
import xaero.hud.minimap.Minimap;
import xaero.hud.minimap.MinimapLogs;
import xaero.hud.minimap.common.config.MinimapConfigConstants;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.compass.render.CompassRenderer;
import xaero.hud.minimap.config.util.MinimapConfigClientUtils;
import xaero.hud.minimap.element.render.MinimapElementRenderLocation;
import xaero.hud.minimap.element.render.map.MinimapElementMapRendererHandler;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.radar.icon.RadarIconManager;
import xaero.hud.minimap.radar.icon.creator.RadarIconCreator;
import xaero.hud.minimap.radar.render.element.RadarRenderer;
import xaero.hud.minimap.waypoint.render.WaypointMapRenderer;
import xaero.hud.render.util.RenderBufferUtil;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.client.graphics.XaeroRenderType;
import xaero.lib.client.graphics.shader.LibShaders;

public class MinimapFBORenderer extends MinimapRenderer {
   private ImprovedFramebuffer scalingFramebuffer;
   private ImprovedFramebuffer rotationFramebuffer;
   private MinimapElementMapRendererHandler minimapElementMapRendererHandler;
   private RadarRenderer entityRadarRenderer;
   private RadarIconManager radarIconManager;
   private boolean triedFBO;
   private boolean loadedFBO;

   public MinimapFBORenderer(HudMod modMain, Minecraft mc, WaypointMapRenderer waypointMapRenderer, Minimap minimap, CompassRenderer compassRenderer) {
      super(modMain, mc, waypointMapRenderer, minimap, compassRenderer);
   }

   public void loadFrameBuffer(MinimapProcessor minimapProcessor) {
      if (!minimapProcessor.canUseFrameBuffer()) {
         MinimapLogs.LOGGER.info("FBO mode not supported! Using minimap safe mode.");
      } else {
         this.scalingFramebuffer = new ImprovedFramebuffer(512, 512, false);
         this.rotationFramebuffer = new ImprovedFramebuffer(512, 512, true);
         this.rotationFramebuffer.setFilterMode(9729);
         this.radarIconManager = new RadarIconManager(new RadarIconCreator());
         this.loadedFBO = this.scalingFramebuffer.frameBufferId != -1 && this.rotationFramebuffer.frameBufferId != -1;
         this.minimapElementMapRendererHandler = MinimapElementMapRendererHandler.Builder.begin().build();
         this.entityRadarRenderer = RadarRenderer.Builder.begin().setRadarIconManager(this.radarIconManager).setMinimap(this.minimap).build();
         this.minimapElementMapRendererHandler.add(this.entityRadarRenderer);
         this.minimap.getOverMapRendererHandler().add(this.entityRadarRenderer);
         if (this.modMain.getSupportMods().worldmap()) {
            this.modMain.getSupportMods().worldmapSupport.createRadarRenderWrapper(this.entityRadarRenderer);
         }
      }

      this.triedFBO = true;
   }

   @Override
   protected void renderChunks(
      MinimapSession minimapSession,
      GuiGraphics guiGraphics,
      MinimapProcessor minimap,
      Vec3 renderPos,
      ResourceKey<Level> mapDimension,
      double mapDimensionScale,
      int mapSize,
      int bufferSize,
      float sizeFix,
      float partial,
      int lightLevel,
      boolean useWorldMap,
      boolean lockedNorth,
      int shape,
      double ps,
      double pc,
      boolean cave,
      boolean circle,
      ModSettings settings,
      CustomVertexConsumers cvc
   ) {
      synchronized (minimap.getMinimapWriter()) {
         this.renderChunksToFBO(
            minimapSession,
            guiGraphics,
            minimap,
            renderPos,
            mapDimension,
            mapDimensionScale,
            mapSize,
            partial,
            lightLevel,
            useWorldMap,
            lockedNorth,
            shape,
            ps,
            pc,
            cave,
            cvc
         );
      }

      this.scalingFramebuffer.bindDefaultFramebuffer(Minecraft.getInstance());
      GlStateManager._viewport(0, 0, Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
      this.rotationFramebuffer.bindRead();
   }

   public void renderChunksToFBO(
      MinimapSession minimapSession,
      GuiGraphics guiGraphics,
      MinimapProcessor minimap,
      Vec3 renderPos,
      ResourceKey<Level> mapDimension,
      double mapDimensionScale,
      int viewW,
      float partial,
      int level,
      boolean useWorldMap,
      boolean lockedNorth,
      int shape,
      double ps,
      double pc,
      boolean cave,
      CustomVertexConsumers cvc
   ) {
      ClientConfigManager configManager = this.modMain.getHudConfigs().getClientConfigManager();
      Matrix4f projectionMatrixBackup = RenderSystem.getProjectionMatrix();
      VertexSorting vertexSortingBackup = RenderSystem.getVertexSorting();
      PoseStack matrixStack = guiGraphics.pose();
      matrixStack.pushPose();
      matrixStack.setIdentity();
      MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers = minimapSession.getMultiTextureRenderTypeRenderers();
      double maxVisibleLength = !lockedNorth && shape != 1 ? viewW * Math.sqrt(2.0) : viewW;
      double halfMaxVisibleLength = maxVisibleLength / 2.0;
      double radiusBlocks = maxVisibleLength / 2.0 / this.zoom;
      int xFloored = OptimizedMath.myFloor(renderPos.x);
      int zFloored = OptimizedMath.myFloor(renderPos.z);
      int playerChunkX = xFloored >> 6;
      int playerChunkZ = zFloored >> 6;
      int offsetX = xFloored & 63;
      int offsetZ = zFloored & 63;
      boolean zooming = (int)this.zoom != this.zoom;
      guiGraphics.flush();
      ImmediatelyFastHelper.triggerBatchingBuffersFlush(guiGraphics);
      this.scalingFramebuffer.bindAsMainTarget(true);
      GL11.glClear(16640);
      Lighting.setupForFlatItems();
      long before = System.currentTimeMillis();
      GlStateManager._clear(256, Minecraft.ON_OSX);
      this.helper.defaultOrtho(this.scalingFramebuffer);
      Matrix4fStack shaderMatrixStack = RenderSystem.getModelViewStack();
      shaderMatrixStack.pushMatrix();
      shaderMatrixStack.identity();
      before = System.currentTimeMillis();
      double xInsidePixel = renderPos.x - xFloored;
      if (xInsidePixel < 0.0) {
         xInsidePixel++;
      }

      double zInsidePixel = renderPos.z - zFloored;
      if (zInsidePixel < 0.0) {
         zInsidePixel++;
      }

      float halfWView = viewW / 2.0F;
      float angle = (float)(90.0 - this.getRenderAngle(lockedNorth));
      RenderSystem.enableBlend();
      shaderMatrixStack.translate(256.0F, 256.0F, -2000.0F);
      shaderMatrixStack.scale((float)this.zoom, (float)this.zoom, 1.0F);
      RenderSystem.applyModelViewMatrix();
      guiGraphics.fill(-256, -256, 256, 256, -16777216);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      BufferSource renderTypeBuffers = cvc.getBetterPVPRenderTypeBuffers();
      VertexConsumer overlayBufferBuilder = renderTypeBuffers.getBuffer(CustomRenderTypes.MAP_CHUNK_OVERLAY);
      float chunkGridAlphaMultiplier = 1.0F;
      int minX = playerChunkX + (int)Math.floor((offsetX - radiusBlocks) / 64.0);
      int minZ = playerChunkZ + (int)Math.floor((offsetZ - radiusBlocks) / 64.0);
      int maxX = playerChunkX + (int)Math.floor((offsetX + 1 + radiusBlocks) / 64.0);
      int maxZ = playerChunkZ + (int)Math.floor((offsetZ + 1 + radiusBlocks) / 64.0);
      if (!cave || MinimapConfigClientUtils.getEffectiveCaveModeAllowed()) {
         if (useWorldMap) {
            chunkGridAlphaMultiplier = this.modMain.getSupportMods().worldmapSupport.getMinimapBrightness();
            this.modMain
               .getSupportMods()
               .worldmapSupport
               .drawMinimap(
                  minimapSession,
                  matrixStack,
                  this.getHelper(),
                  xFloored,
                  zFloored,
                  minX,
                  minZ,
                  maxX,
                  maxZ,
                  zooming,
                  this.zoom,
                  mapDimensionScale,
                  overlayBufferBuilder,
                  multiTextureRenderTypeRenderers
               );
         } else if (minimap.getMinimapWriter().getLoadedBlocks() != null && level >= 0) {
            int loadedLevels = minimap.getMinimapWriter().getLoadedLevels();
            chunkGridAlphaMultiplier = loadedLevels <= 1 ? 1.0F : 0.375F + 0.625F * (1.0F - (float)level / (loadedLevels - 1));
            int loadedMapChunkX = minimap.getMinimapWriter().getLoadedMapChunkX();
            int loadedMapChunkZ = minimap.getMinimapWriter().getLoadedMapChunkZ();
            int loadedWidth = minimap.getMinimapWriter().getLoadedBlocks().length;
            boolean slimeChunks = MinimapConfigClientUtils.getEffectiveSlimeChunks(minimapSession);
            minX = Math.max(minX, loadedMapChunkX);
            minZ = Math.max(minZ, loadedMapChunkZ);
            maxX = Math.min(maxX, loadedMapChunkX + loadedWidth - 1);
            maxZ = Math.min(maxZ, loadedMapChunkZ + loadedWidth - 1);
            MultiTextureRenderTypeRenderer multiTextureRenderTypeRenderer = multiTextureRenderTypeRenderers.getRenderer(
               t -> RenderSystem.setShaderTexture(0, t), MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.GUI_BILINEAR
            );
            MinimapRendererHelper helper = this.getHelper();

            for (int X = minX; X <= maxX; X++) {
               int canvasX = X - minimap.getMinimapWriter().getLoadedMapChunkX();

               for (int Z = minZ; Z <= maxZ; Z++) {
                  int canvasZ = Z - minimap.getMinimapWriter().getLoadedMapChunkZ();
                  MinimapChunk mchunk = minimap.getMinimapWriter().getLoadedBlocks()[canvasX][canvasZ];
                  if (mchunk != null) {
                     int texture = mchunk.bindTexture(level);
                     if (mchunk.isHasSomething() && level < mchunk.getLevelsBuffered() && texture != 0) {
                        if (!zooming) {
                           GL11.glTexParameteri(3553, 10240, 9728);
                        } else {
                           GL11.glTexParameteri(3553, 10240, 9729);
                        }

                        int drawX = (X - playerChunkX) * 64 - offsetX;
                        int drawZ = (Z - playerChunkZ) * 64 - offsetZ;
                        helper.prepareMyTexturedColoredModalRect(
                           matrixStack.last().pose(),
                           drawX,
                           drawZ,
                           0,
                           64,
                           64.0F,
                           64.0F,
                           -64.0F,
                           64.0F,
                           texture,
                           1.0F,
                           1.0F,
                           1.0F,
                           1.0F,
                           multiTextureRenderTypeRenderer
                        );
                        if (slimeChunks) {
                           for (int t = 0; t < 16; t++) {
                              if (mchunk.getTile(t % 4, t / 4) != null && mchunk.getTile(t % 4, t / 4).isSlimeChunk()) {
                                 int slimeDrawX = drawX + 16 * (t % 4);
                                 int slimeDrawZ = drawZ + 16 * (t / 4);
                                 RenderBufferUtil.addColoredRect(matrixStack.last().pose(), overlayBufferBuilder, slimeDrawX, slimeDrawZ, 16, 16, -2142047936);
                              }
                           }
                        }
                     }
                  }
               }
            }

            multiTextureRenderTypeRenderers.draw(multiTextureRenderTypeRenderer);
         }
      }

      int chunkGridConfig = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.CHUNK_GRID);
      if (chunkGridConfig > -1) {
         VertexConsumer lineBufferBuilder = renderTypeBuffers.getBuffer(CustomRenderTypes.MAP_LINES);
         int grid = MinimapConfigConstants.COLORS[chunkGridConfig];
         int r = grid >> 16 & 0xFF;
         int g = grid >> 8 & 0xFF;
         int b = grid & 0xFF;
         LibShaders.FRAMEBUFFER_LINES.setFrameSize(this.scalingFramebuffer.viewWidth, this.scalingFramebuffer.viewHeight);
         float red = r / 255.0F;
         float green = g / 255.0F;
         float blue = b / 255.0F;
         float alpha = 0.8F;
         float colorMultiplier = chunkGridAlphaMultiplier;
         red *= colorMultiplier;
         green *= colorMultiplier;
         blue *= colorMultiplier;
         int chunkGridLineWidthConfig = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.CHUNK_GRID_LINE_WIDTH);
         RenderSystem.lineWidth(chunkGridLineWidthConfig);
         int bias = 1;
         Pose matrices = matrixStack.last();

         for (int X = minX; X <= maxX; X++) {
            int drawX = (X - playerChunkX + 1) * 64 - offsetX;

            for (int i = 0; i < 4; i++) {
               float lineX = (float)drawX + -16 * i;
               this.helper
                  .addColoredLineToExistingBuffer(
                     matrices, lineBufferBuilder, lineX, -((float)halfMaxVisibleLength), lineX, (float)halfMaxVisibleLength + bias, red, green, blue, alpha
                  );
            }
         }

         for (int Z = minZ; Z <= maxZ; Z++) {
            int drawZ = (Z - playerChunkZ + 1) * 64 - offsetZ;

            for (int i = 0; i < 4; i++) {
               float lineZ = drawZ + (float)(-16 * i - 1.0 / this.zoom);
               this.helper
                  .addColoredLineToExistingBuffer(
                     matrices, lineBufferBuilder, -((float)halfMaxVisibleLength), lineZ, (float)halfMaxVisibleLength + bias, lineZ, red, green, blue, alpha
                  );
            }
         }
      }

      renderTypeBuffers.endBatch();
      this.scalingFramebuffer.unbindWrite();
      this.rotationFramebuffer.bindAsMainTarget(false);
      GL11.glClear(16640);
      this.scalingFramebuffer.bindRead();
      shaderMatrixStack.identity();
      boolean antiAliasing = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.ANTI_ALIASING);
      if (antiAliasing) {
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexParameteri(3553, 10241, 9729);
      } else {
         GL11.glTexParameteri(3553, 10240, 9728);
         GL11.glTexParameteri(3553, 10241, 9728);
      }

      shaderMatrixStack.translate(halfWView, halfWView, -2980.0F);
      shaderMatrixStack.pushMatrix();
      if (!lockedNorth) {
         OptimizedMath.rotateMatrix(shaderMatrixStack, -angle, OptimizedMath.ZP);
      }

      shaderMatrixStack.translate((float)(-xInsidePixel * this.zoom), (float)(-zInsidePixel * this.zoom), 0.0F);
      RenderSystem.applyModelViewMatrix();
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ZERO, SourceFactor.ONE, DestFactor.ZERO);
      int opacityConfig = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.OPACITY);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacityConfig / 100.0F);
      this.helper.drawMyTexturedModalRect(matrixStack, -256.0F, -256.0F, 0, 0, 512.0F, 512.0F, 512.0F, 512.0F);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      shaderMatrixStack.popMatrix();
      RenderSystem.applyModelViewMatrix();
      before = System.currentTimeMillis();
      XaeroRenderType.resetTransparency();
      XaeroRenderType.resetDepthTest();
      RenderSystem.enableDepthTest();
      XaeroRenderType.resetWriteMask();
      GL11.glBindTexture(3553, 0);
      GlStateManager._bindTexture(0);
      this.minimapElementMapRendererHandler.prepareRender(ps, pc, this.zoom, halfWView);
      this.minimapElementMapRendererHandler.render(guiGraphics, renderPos, partial, this.rotationFramebuffer, mapDimensionScale, mapDimension);
      guiGraphics.flush();
      renderTypeBuffers.endBatch();
      ImmediatelyFastHelper.triggerBatchingBuffersFlush(guiGraphics);
      this.rotationFramebuffer.unbindWrite();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setProjectionMatrix(projectionMatrixBackup, vertexSortingBackup);
      shaderMatrixStack.popMatrix();
      RenderSystem.applyModelViewMatrix();
      matrixStack.popPose();
   }

   public void deleteFramebuffers() {
      this.scalingFramebuffer.destroyBuffers();
      this.rotationFramebuffer.destroyBuffers();
      if (this.radarIconManager != null) {
         this.radarIconManager.reset();
      }
   }

   public boolean isLoadedFBO() {
      return this.loadedFBO;
   }

   public void setLoadedFBO(boolean loadedFBO) {
      this.loadedFBO = loadedFBO;
   }

   public boolean isTriedFBO() {
      return this.triedFBO;
   }

   public boolean assumeUsingFBO() {
      boolean mapSafeMode = (Boolean)this.modMain.getHudConfigs().getClientConfigManager().getEffective(MinimapProfiledConfigOptions.SAFE_MODE);
      return !this.isTriedFBO() && !mapSafeMode || this.minimap.usingFBO();
   }

   public void resetEntityIcons() {
      if (this.radarIconManager != null) {
         this.radarIconManager.reset();
      }
   }

   public void resetEntityIconsResources() {
      if (this.radarIconManager != null) {
         this.radarIconManager.resetResources();
      }
   }

   public void onRadarIconModelRenderTrace(EntityModel<?> model, VertexConsumer vertexConsumer, int color) {
      this.radarIconManager.onModelRenderTrace(model, vertexConsumer, color);
   }

   public void onEntityIconModelPartRenderTrace(ModelPart modelRenderer, int color) {
      this.radarIconManager.onModelPartRenderTrace(modelRenderer, color);
   }

   public void renderMainEntityDot(GuiGraphics guiGraphics, Entity renderEntity, boolean cave, BufferSource renderTypeBuffers) {
      guiGraphics.pose().pushPose();
      this.entityRadarRenderer.renderSingleEntity(renderEntity, cave, false, 2.0F, false, false, MinimapElementRenderLocation.OVER_MINIMAP, null, guiGraphics);
      renderTypeBuffers.endBatch();
      guiGraphics.pose().popPose();
   }

   @Deprecated
   public xaero.common.minimap.render.radar.element.RadarRenderer getRadarRenderer() {
      return (xaero.common.minimap.render.radar.element.RadarRenderer)this.entityRadarRenderer;
   }

   public RadarRenderer getEntityRadarRenderer() {
      return this.entityRadarRenderer;
   }
}
