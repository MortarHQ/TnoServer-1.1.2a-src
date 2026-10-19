package xaero.common.minimap.render;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import xaero.common.HudMod;
import xaero.common.graphics.CustomRenderTypes;
import xaero.common.graphics.CustomVertexConsumers;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.misc.OptimizedMath;
import xaero.common.settings.ModSettings;
import xaero.hud.entity.EntityUtils;
import xaero.hud.gui.util.GuiUtils;
import xaero.hud.minimap.BuiltInHudModules;
import xaero.hud.minimap.Minimap;
import xaero.hud.minimap.common.config.MinimapConfigConstants;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.compass.render.CompassRenderer;
import xaero.hud.minimap.config.util.MinimapConfigClientUtils;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.radar.RadarSession;
import xaero.hud.minimap.radar.category.EntityRadarCategory;
import xaero.hud.minimap.radar.category.EntityRadarCategoryManager;
import xaero.hud.minimap.radar.category.setting.EntityRadarCategorySettings;
import xaero.hud.minimap.radar.color.RadarColor;
import xaero.hud.minimap.waypoint.render.WaypointMapRenderer;
import xaero.hud.render.TextureLocations;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.client.gui.util.graphics.GuiGraphicsUtils;

public abstract class MinimapRenderer {
   public static final int black = -16777216;
   public static final int slime = -2142047936;
   protected HudMod modMain;
   protected Minecraft mc;
   protected Minimap minimap;
   protected MinimapRendererHelper helper;
   protected WaypointMapRenderer waypointMapRenderer;
   private int lastMinimapSize;
   protected double zoom = 1.0;
   private MutableBlockPos mutableBlockPos;
   protected final CompassRenderer compassRenderer;

   public MinimapRenderer(HudMod modMain, Minecraft mc, WaypointMapRenderer waypointMapRenderer, Minimap minimap, CompassRenderer compassRenderer) {
      this.modMain = modMain;
      this.mc = mc;
      this.waypointMapRenderer = waypointMapRenderer;
      this.minimap = minimap;
      this.helper = new MinimapRendererHelper();
      this.mutableBlockPos = new MutableBlockPos();
      this.compassRenderer = compassRenderer;
   }

   public double getRenderAngle(boolean lockedNorth) {
      return lockedNorth ? 90.0 : this.getActualAngle();
   }

   private double getActualAngle() {
      double rotation = this.mc.gameRenderer.getMainCamera().getYRot();
      return -90.0 - rotation;
   }

   protected abstract void renderChunks(
      MinimapSession var1,
      GuiGraphics var2,
      MinimapProcessor var3,
      Vec3 var4,
      ResourceKey<Level> var5,
      double var6,
      int var8,
      int var9,
      float var10,
      float var11,
      int var12,
      boolean var13,
      boolean var14,
      int var15,
      double var16,
      double var18,
      boolean var20,
      boolean var21,
      ModSettings var22,
      CustomVertexConsumers var23
   );

   public void renderMinimap(
      MinimapSession minimapSession,
      GuiGraphics guiGraphics,
      MinimapProcessor minimap,
      int x,
      int y,
      int width,
      int height,
      double scale,
      int size,
      float partial,
      CustomVertexConsumers cvc
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      ModSettings settings = this.modMain.getSettings();
      int minimapSizeConfig = MinimapConfigClientUtils.getEffectiveMinimapSize();
      if (minimapSizeConfig != this.lastMinimapSize) {
         this.lastMinimapSize = minimapSizeConfig;
         minimap.setToResetImage(true);
      }

      minimap.getRadarSession().getStateUpdater().setLastRenderViewEntity(this.mc.getCameraEntity());
      int mapSize = minimapSession.getProcessor().getMinimapSize();
      int bufferSize = minimapSession.getProcessor().getMinimapBufferSize(mapSize);
      if (this.minimap.usingFBO()) {
         bufferSize = minimap.getFBOBufferSize();
      }

      ClientConfigManager configManager = this.modMain.getHudConfigs().getClientConfigManager();
      float minimapScale = GuiUtils.getMinimapScale(configManager);
      float mapScale = (float)(scale / minimapScale);
      minimap.updateZoom();
      this.zoom = minimap.getMinimapZoom();
      Lighting.setupForFlatItems();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pixelStore(3317, 4);
      RenderSystem.pixelStore(3316, 0);
      RenderSystem.pixelStore(3315, 0);
      RenderSystem.pixelStore(3314, 0);
      float sizeFix = bufferSize / 512.0F;
      int shape = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.SHAPE);
      boolean lockedNorth = MinimapConfigClientUtils.getEffectiveNorthLocked(mapSize / 2, shape);
      double angle = Math.toRadians(this.getRenderAngle(lockedNorth));
      double ps = Math.sin(Math.PI - angle);
      double pc = Math.cos(Math.PI - angle);
      boolean useWorldMap = this.modMain.getSupportMods().shouldUseWorldMapChunks() && !minimap.getMinimapWriter().isLoadedNonWorldMap();
      boolean lightingConfig = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.LIGHTING);
      int lightLevel = (int)((1.0F - Math.min(1.0F, this.getSunBrightness(minimap, lightingConfig))) * (minimap.getMinimapWriter().getLoadedLevels() - 1));
      boolean cave = minimap.isCaveModeDisplayed();
      boolean circleShape = shape == 1;
      double playerX = EntityUtils.getEntityX(this.mc.getCameraEntity(), partial);
      double playerY = EntityUtils.getEntityY(this.mc.getCameraEntity(), partial);
      double playerZ = EntityUtils.getEntityZ(this.mc.getCameraEntity(), partial);
      double renderX = playerX;
      double renderZ = playerZ;
      double mapDimensionScale = this.mc.level.dimensionType().coordinateScale();
      ResourceKey<Level> mapDimension = this.mc.level.dimension();
      double playerDimDiv = 1.0;
      if (useWorldMap) {
         double playerCoordinateScale = mapDimensionScale;
         mapDimensionScale = this.modMain.getSupportMods().worldmapSupport.getMapDimensionScale();
         mapDimension = this.modMain.getSupportMods().worldmapSupport.getMapDimension();
         if (mapDimensionScale == 0.0) {
            mapDimensionScale = minimap.getLastMapDimensionScale();
            mapDimension = minimap.getLastMapDimension();
         }

         playerDimDiv = mapDimensionScale / playerCoordinateScale;
         renderX /= playerDimDiv;
         renderZ /= playerDimDiv;
      }

      minimap.setLastMapDimensionScale(mapDimensionScale);
      minimap.setLastMapDimension(mapDimension);
      minimap.setLastPlayerDimDiv(playerDimDiv);
      Vec3 renderPos = new Vec3(renderX, playerY, renderZ);
      matrixStack.pushPose();
      this.renderChunks(
         minimapSession,
         guiGraphics,
         minimap,
         renderPos,
         mapDimension,
         mapDimensionScale,
         mapSize,
         bufferSize,
         sizeFix,
         partial,
         lightLevel,
         useWorldMap,
         lockedNorth,
         shape,
         ps,
         pc,
         cave,
         circleShape,
         settings,
         cvc
      );
      if (this.minimap.usingFBO()) {
         sizeFix = 1.0F;
      }

      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ZERO, DestFactor.ONE);
      matrixStack.scale(1.0F / mapScale, 1.0F / mapScale, 1.0F);
      int scaledX = (int)(x * mapScale);
      int scaledY = (int)(y * mapScale);
      int minimapFrameSize = (int)(mapSize / 2 / sizeFix);
      int circleSides = Math.max(32, (int)Math.ceil(Math.PI * (minimapFrameSize + 8) / 8.0 / 4.0) * 4);
      double circleSeamAngle = -Math.PI / 4;
      int circleSeamWidth = 32;
      int circleFrameThickness = 4;
      double circleStartAngle = 0.0;
      if (!circleShape) {
         this.getHelper()
            .drawMyTexturedModalRect(
               matrixStack,
               (int)((scaledX + 9) / sizeFix),
               (int)((scaledY + 9) / sizeFix),
               0,
               256 - minimapFrameSize,
               minimapFrameSize,
               minimapFrameSize,
               minimapFrameSize,
               256.0F
            );
      } else {
         float outerRadius = mapSize / 4 + circleFrameThickness;
         circleStartAngle = circleSeamAngle - circleSeamWidth / 2 / outerRadius;
         this.getHelper()
            .drawTexturedElipseInsideRectangle(
               matrixStack,
               circleStartAngle,
               circleSides,
               (int)((scaledX + 9) / sizeFix),
               (int)((scaledY + 9) / sizeFix),
               0,
               256 - minimapFrameSize,
               minimapFrameSize,
               256.0F
            );
      }

      RenderSystem.defaultBlendFunc();
      if (!this.minimap.usingFBO()) {
         matrixStack.scale(1.0F / sizeFix, 1.0F / sizeFix, 1.0F);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }

      int frameType = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.FRAME);
      boolean renderFrame = frameType < MinimapConfigConstants.FRAME_NAMES.length - 1;
      if (frameType > 0) {
         int frameColorConfig = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.FRAME_COLOR);
         int frameColor = MinimapConfigConstants.COLORS[frameColorConfig];
         RenderSystem.setShaderColor((frameColor >> 16 & 0xFF) / 255.0F, (frameColor >> 8 & 0xFF) / 255.0F, (frameColor & 0xFF) / 255.0F, 1.0F);
      }

      MinimapRendererHelper helper = this.getHelper();
      if (renderFrame) {
         RenderSystem.setShaderTexture(0, TextureLocations.MINIMAP_FRAME_TEXTURES);
      }

      if (renderFrame && !circleShape) {
         int rightCornerStartX = scaledX + 9 + mapSize / 2 + 4 - 16;
         int bottomCornerStartY = scaledY + 9 + mapSize / 2 + 4 - 16;
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         Tesselator tessellator = Tesselator.getInstance();
         BufferBuilder vertexBuffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
         Matrix4f matrix = matrixStack.last().pose();
         int cornerTextureX = frameType == 0 ? 192 : (frameType == 1 ? 208 : 224);
         helper.addTexturedRectToExistingBuffer(matrix, vertexBuffer, scaledX + 9 - 4, scaledY + 9 - 4, cornerTextureX, 97, 16, 16);
         helper.addTexturedRectToExistingBuffer(matrix, vertexBuffer, rightCornerStartX, scaledY + 9 - 4, cornerTextureX, 113, 16, 16);
         helper.addTexturedRectToExistingBuffer(matrix, vertexBuffer, scaledX + 9 - 4, bottomCornerStartY, cornerTextureX, 129, 16, 16);
         helper.addTexturedRectToExistingBuffer(matrix, vertexBuffer, rightCornerStartX, bottomCornerStartY, cornerTextureX, 145, 16, 16);
         int horLineStartX = scaledX + 9 - 4 + 16;
         int horLineWidth = rightCornerStartX - horLineStartX;
         int horPieceTextureY = frameType == 0 ? 0 : (frameType == 1 ? 32 : 64);
         int horPieceWidth = 226;
         int horLineLength = (int)Math.ceil((double)horLineWidth / horPieceWidth);

         for (int i = 0; i < horLineLength; i++) {
            int pieceX = scaledX + 9 - 4 + 16 + i * horPieceWidth;
            int pieceW = horPieceWidth;
            if (i == horLineLength - 1 && pieceX + pieceW > rightCornerStartX) {
               pieceW = rightCornerStartX - pieceX;
            }

            helper.addTexturedRectToExistingBuffer(matrix, vertexBuffer, pieceX, scaledY + 9 - 4, 0, horPieceTextureY, pieceW, 16);
            helper.addTexturedRectToExistingBuffer(matrix, vertexBuffer, pieceX, scaledY + 9 + mapSize / 2 - 12, 0, horPieceTextureY + 16, pieceW, 16);
         }

         int verLineStartY = scaledY + 9 - 4 + 16;
         int verLineHeight = bottomCornerStartY - verLineStartY;
         int verPieceTextureX = frameType == 0 ? 0 : (frameType == 1 ? 64 : 128);
         int verPieceHeight = 113;
         int vertLineLength = (int)Math.ceil((double)verLineHeight / verPieceHeight);

         for (int i = 0; i < vertLineLength; i++) {
            int pieceY = scaledY + 9 - 4 + 16 + i * verPieceHeight;
            int pieceU = verPieceTextureX + 32 * (i & 1);
            int pieceH = verPieceHeight;
            if (i == vertLineLength - 1 && pieceY + pieceH > bottomCornerStartY) {
               pieceH = bottomCornerStartY - pieceY;
            }

            helper.addTexturedRectToExistingBuffer(matrix, vertexBuffer, scaledX + 9 - 4, pieceY, pieceU, 97, 16, pieceH);
            helper.addTexturedRectToExistingBuffer(matrix, vertexBuffer, scaledX + 9 + mapSize / 2 - 12, pieceY, pieceU + 16, 97, 16, pieceH);
         }

         BufferUploader.drawWithShader(vertexBuffer.build());
      } else if (renderFrame) {
         int frameTextureY = frameType == 0 ? 210 : (frameType == 1 ? 214 : 218);
         double shadeStartAngle = (Math.PI / 4) - circleStartAngle;
         int shadeStartIndex = (int)(shadeStartAngle / 2.0 / Math.PI * circleSides);
         int circleLeftX = scaledX + 9;
         int circleTopY = scaledY + 9;
         int innerCircleDiameter = mapSize / 2;
         helper.drawTexturedElipseInsideRectangleFrame(
            matrixStack,
            false,
            false,
            circleStartAngle,
            0,
            shadeStartIndex,
            circleSides,
            circleFrameThickness,
            circleLeftX,
            circleTopY,
            0,
            frameTextureY,
            innerCircleDiameter,
            73.0F,
            circleFrameThickness,
            circleSeamWidth,
            256.0F
         );
         helper.drawTexturedElipseInsideRectangleFrame(
            matrixStack,
            true,
            false,
            circleStartAngle,
            shadeStartIndex,
            shadeStartIndex + circleSides / 4,
            circleSides,
            circleFrameThickness,
            circleLeftX,
            circleTopY,
            138,
            frameTextureY,
            innerCircleDiameter,
            68.0F,
            circleFrameThickness,
            20,
            256.0F
         );
         helper.drawTexturedElipseInsideRectangleFrame(
            matrixStack,
            true,
            true,
            circleStartAngle,
            shadeStartIndex + circleSides / 4,
            shadeStartIndex + circleSides / 2,
            circleSides,
            circleFrameThickness,
            circleLeftX,
            circleTopY,
            138,
            frameTextureY,
            innerCircleDiameter,
            68.0F,
            circleFrameThickness,
            20,
            256.0F
         );
         helper.drawTexturedElipseInsideRectangleFrame(
            matrixStack,
            false,
            false,
            circleStartAngle,
            shadeStartIndex + circleSides / 2,
            circleSides,
            circleSides,
            circleFrameThickness,
            circleLeftX,
            circleTopY,
            0,
            frameTextureY,
            innerCircleDiameter,
            73.0F,
            circleFrameThickness,
            circleSeamWidth,
            256.0F
         );
      }

      RenderSystem.setShaderTexture(0, TextureLocations.GUI_TEXTURES);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.pushPose();
      matrixStack.translate(scaledX + 9, scaledY + 9, 0.0F);
      matrixStack.scale(1.0F / minimapScale, 1.0F / minimapScale, 1.0F);
      int halfFrame = (int)(mapSize * minimapScale / 2.0F / 2.0F);
      matrixStack.translate(halfFrame, halfFrame, 0.5);
      int specW = halfFrame + (int)(3.0F * minimapScale);
      int specH = specW;
      boolean safeMode = this instanceof MinimapSafeModeRenderer;
      BufferSource renderTypeBuffers = this.modMain.getHudRenderer().getCustomVertexConsumers().getBetterPVPRenderTypeBuffers();
      MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers = minimapSession.getMultiTextureRenderTypeRenderers();
      double scaledZoom = this.zoom * minimapScale / 2.0;
      boolean compassOverEverythingConfig = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.COMPASS_OVER_EVERYTHING);
      if (!compassOverEverythingConfig) {
         this.renderCompass(matrixStack, settings, configManager, renderTypeBuffers, specW, specH, halfFrame, ps, pc, circleShape, minimapScale);
      }

      this.minimap.getOverMapRendererHandler().prepareRender(ps, pc, scaledZoom, specW, specH, halfFrame, halfFrame, circleShape, minimapScale);
      this.minimap.getOverMapRendererHandler().render(guiGraphics, renderPos, partial, null, mapDimensionScale, mapDimension);
      if (compassOverEverythingConfig) {
         this.renderCompass(matrixStack, settings, configManager, renderTypeBuffers, specW, specH, halfFrame, ps, pc, circleShape, minimapScale);
      }

      renderTypeBuffers.endBatch();
      matrixStack.popPose();
      int depthClearerX = scaledX - 25;
      int depthClearerY = scaledY - 25;
      int depthClearerW = 18 + mapSize / 2 + 50;
      guiGraphics.fill(CustomRenderTypes.DEPTH_CLEAR, depthClearerX, depthClearerY, depthClearerX + depthClearerW, depthClearerY + depthClearerW, -16777216);
      RenderSystem.enableBlend();
      int mainEntityAs = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.RADAR_MAIN_ENTITY);
      boolean crosshairDisplayed = mainEntityAs == 0 && !lockedNorth;
      if (crosshairDisplayed) {
         matrixStack.pushPose();
         matrixStack.translate(scaledX + 9, scaledY + 9, 0.0F);
         matrixStack.scale(0.5F, 0.5F, 1.0F);
         matrixStack.translate(mapSize / 2, mapSize / 2, 0.0F);
         RenderSystem.blendFuncSeparate(775, 0, 1, 0);
         this.getHelper().drawMyColoredRect(matrixStack, -5.0F, -1.0F, 5.0F, 1.0F);
         this.getHelper().drawMyColoredRect(matrixStack, -1.0F, 3.0F, 1.0F, 5.0F);
         this.getHelper().drawMyColoredRect(matrixStack, -1.0F, -5.0F, 1.0F, -3.0F);
         RenderSystem.blendFunc(770, 771);
         RadarSession radarSession = minimap.getRadarSession();
         EntityRadarCategoryManager categoryManager = radarSession.getCategoryManager();
         EntityRadarCategory mainEntityCategory = categoryManager.getRuleResolver()
            .resolve(categoryManager.getRootCategory(), this.mc.getCameraEntity(), this.mc.player);
         if (mainEntityCategory == null) {
            mainEntityCategory = categoryManager.getRootCategory();
         }

         RadarColor crosshairRadarColor = RadarColor.fromIndex(mainEntityCategory.getSettingValue(EntityRadarCategorySettings.COLOR).intValue());
         RadarColor crosshairFallbackColor = radarSession.getColorHelper().getFallbackColor(mainEntityCategory, null);
         int crosshairColor = radarSession.getColorHelper()
            .getEntityColor(this.mc.getCameraEntity(), 0.0F, false, 100, 100, false, crosshairRadarColor, crosshairFallbackColor);
         RenderSystem.setShaderColor((crosshairColor >> 16 & 0xFF) / 255.0F, (crosshairColor >> 8 & 0xFF) / 255.0F, (crosshairColor & 0xFF) / 255.0F, 1.0F);
         this.getHelper().drawMyColoredRect(matrixStack, 1.0F, -1.0F, 3.0F, 1.0F);
         this.getHelper().drawMyColoredRect(matrixStack, -3.0F, -1.0F, -1.0F, 1.0F);
         this.getHelper().drawMyColoredRect(matrixStack, -1.0F, 1.0F, 1.0F, 3.0F);
         this.getHelper().drawMyColoredRect(matrixStack, -1.0F, -3.0F, 1.0F, -1.0F);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableBlend();
         matrixStack.popPose();
      }

      double centerX = 2 * scaledX + 18 + mapSize / 2;
      double centerY = 2 * scaledY + 18 + mapSize / 2;
      matrixStack.pushPose();
      matrixStack.scale(0.5F, 0.5F, 1.0F);
      matrixStack.translate(centerX, centerY, 0.0);
      this.mc.getTextureManager().bindForSetup(TextureLocations.GUI_TEXTURES);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexParameteri(3553, 10241, 9729);
      Entity mainEntity = this.mc.getCameraEntity();
      if (!safeMode && !crosshairDisplayed) {
         this.minimap.getMinimapFBORenderer().renderMainEntityDot(guiGraphics, mainEntity, cave, cvc.getBetterPVPRenderTypeBuffers());
      }

      RenderSystem.setShaderTexture(0, TextureLocations.GUI_TEXTURES);
      RenderSystem.enableBlend();
      if (lockedNorth || mainEntityAs == 2) {
         float arrowAngle = lockedNorth ? mainEntity.getViewYRot(partial) : 180.0F;
         int arrowOpacityInt = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.ARROW_OPACITY);
         float arrowOpacity = arrowOpacityInt / 100.0F;
         if (arrowOpacity == 1.0F) {
            this.drawArrow(matrixStack, arrowAngle, 0.0, 1.0, 0.0F, 0.0F, 0.0F, 0.5F, configManager);
         }

         int arrowColour = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.ARROW_COLOR);
         float r;
         float g;
         float b;
         float a;
         if (arrowColour != -1) {
            float[] c = MinimapConfigConstants.ARROW_COLORS[arrowColour];
            r = c[0];
            g = c[1];
            b = c[2];
            a = c[3];
         } else {
            int rgb = minimap.getRadarSession().getColorHelper().getTeamColor((Entity)(this.mc.player == null ? mainEntity : this.mc.player));
            if (rgb != -1) {
               r = (rgb >> 16 & 0xFF) / 255.0F;
               g = (rgb >> 8 & 0xFF) / 255.0F;
               b = (rgb & 0xFF) / 255.0F;
               a = 1.0F;
            } else {
               float[] c = MinimapConfigConstants.ARROW_COLORS[0];
               r = c[0];
               g = c[1];
               b = c[2];
               a = c[3];
            }
         }

         a *= arrowOpacity;
         this.drawArrow(matrixStack, arrowAngle, 0.0, 0.0, r, g, b, a, configManager);
      }

      matrixStack.popPose();
      this.mc.getTextureManager().bindForSetup(TextureLocations.GUI_TEXTURES);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10241, 9728);
      int playerBlockX = OptimizedMath.myFloor(mainEntity.getX());
      int playerBlockY = OptimizedMath.myFloor(mainEntity.getY());
      int playerBlockZ = OptimizedMath.myFloor(mainEntity.getZ());
      BlockPos pos = this.mutableBlockPos.set(playerBlockX, playerBlockY, playerBlockZ);
      this.minimap
         .getInfoDisplays()
         .getRenderer()
         .render(guiGraphics, minimapSession, this.minimap, height, size, pos, scaledX, scaledY, mapScale, renderTypeBuffers);
      matrixStack.popPose();
      Lighting.setupFor3DItems();
   }

   private void renderCompass(
      PoseStack matrixStack,
      ModSettings settings,
      ClientConfigManager configManager,
      BufferSource renderTypeBuffers,
      int specW,
      int specH,
      int halfFrame,
      double ps,
      double pc,
      boolean circleShape,
      float minimapScale
   ) {
      VertexConsumer nameBgBuilder = renderTypeBuffers.getBuffer(CustomRenderTypes.RADAR_NAME_BGS);
      int compassScale = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.COMPASS_SCALE);
      int compassLocation = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.COMPASS_LOCATION);
      if (compassScale <= 0) {
         compassScale = compassLocation == 1 ? (int)Math.ceil(minimapScale / 2.0F) : (int)minimapScale;
      } else {
         compassScale = (int)MinimapConfigClientUtils.getUIScale(configManager, MinimapProfiledConfigOptions.COMPASS_SCALE);
      }

      if (compassLocation == 1) {
         if (Minecraft.getInstance().isEnforceUnicode()) {
            compassScale *= 2;
         }

         halfFrame = (int)(halfFrame - 7.0F * minimapScale / 2.0F);
         this.compassRenderer
            .drawCompass(
               matrixStack,
               halfFrame - 3 * compassScale,
               halfFrame - 3 * compassScale,
               ps,
               pc,
               1.0,
               circleShape,
               compassScale,
               true,
               renderTypeBuffers,
               nameBgBuilder
            );
      } else if (compassLocation == 2) {
         this.compassRenderer.drawCompass(matrixStack, specW, specH, ps, pc, this.zoom, circleShape, compassScale, false, renderTypeBuffers, null);
      }
   }

   private void drawArrow(
      PoseStack matrixStack, float angle, double arrowX, double arrowY, float r, float g, float b, float a, ClientConfigManager configManager
   ) {
      matrixStack.pushPose();
      matrixStack.translate(arrowX, arrowY, 0.0);
      OptimizedMath.rotatePose(matrixStack, angle, OptimizedMath.ZP);
      double arrowScale = (Double)configManager.getEffective(MinimapProfiledConfigOptions.ARROW_SCALE);
      matrixStack.scale((float)(0.5 * arrowScale), (float)(0.5 * arrowScale), 1.0F);
      int offsetY = -6;
      int h = 28;
      int ty = 0;
      matrixStack.translate(-13.0F, offsetY, 0.0F);
      RenderSystem.setShaderColor(r, g, b, a);
      RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
      GuiGraphicsUtils.blit(matrixStack, 0, 0, 49.0F, ty, 26, h);
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.popPose();
   }

   public double getZoom() {
      return this.zoom;
   }

   public void setZoom(double zoom) {
      this.zoom = zoom;
   }

   public float getSunBrightness(MinimapProcessor minimap, boolean lighting) {
      ClientLevel world = this.mc.level;
      float sunBrightness = (world.getSkyDarken(1.0F) - 0.2F) / 0.8F;
      float ambient = world.dimensionType().ambientLight() * 24.0F / 15.0F;
      if (ambient > 1.0F) {
         ambient = 1.0F;
      }

      return ambient + (1.0F - ambient) * Mth.clamp(sunBrightness, 0.0F, 1.0F);
   }

   public MinimapRendererHelper getHelper() {
      return this.helper;
   }

   @Deprecated
   public double getLastPlayerDimDiv() {
      MinimapSession session = BuiltInHudModules.MINIMAP.getCurrentSession();
      return session == null ? 1.0 : session.getProcessor().getLastPlayerDimDiv();
   }
}
