package xaero.map.mods.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.phys.Vec3;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.single.SingleConfigManager;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.animation.SlowingAnimation;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.config.primary.option.WorldMapPrimaryClientConfigOptions;
import xaero.map.element.MapElementRenderer;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.graphics.CustomRenderTypes;
import xaero.map.graphics.MapRenderHelper;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.map.gui.GuiMap;
import xaero.map.icon.XaeroIcon;
import xaero.map.misc.Misc;
import xaero.map.mods.SupportXaeroMinimap;
import xaero.map.world.MapDimension;

public final class WaypointRenderer extends MapElementRenderer<Waypoint, WaypointRenderContext, WaypointRenderer> {
   private final SupportXaeroMinimap minimap;
   private final WaypointSymbolCreator symbolCreator;
   private ElementRenderInfo compatibleRenderInfo;

   private WaypointRenderer(
      WaypointRenderContext context, WaypointRenderProvider provider, WaypointReader reader, SupportXaeroMinimap minimap, WaypointSymbolCreator symbolCreator
   ) {
      super(context, provider, reader);
      this.minimap = minimap;
      this.symbolCreator = symbolCreator;
   }

   public WaypointSymbolCreator getSymbolCreator() {
      return this.symbolCreator;
   }

   public void renderElementShadow(
      Waypoint w,
      boolean hovered,
      float optionalScale,
      double partialX,
      double partialY,
      ElementRenderInfo renderInfo,
      GuiGraphics guiGraphics,
      BufferSource vanillaBufferSource,
      MultiTextureRenderTypeRendererProvider rendererProvider
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      matrixStack.translate(partialX, partialY, 0.0);
      matrixStack.scale(optionalScale * this.context.worldmapWaypointsScale, optionalScale * this.context.worldmapWaypointsScale, 1.0F);
      float visibilityAlpha = w.isDisabled() ? 0.3F : 1.0F;
      matrixStack.translate(-14.0F, -41.0F, 0.0F);
      MapRenderHelper.blitIntoExistingBuffer(
         matrixStack.last().pose(),
         this.context.regularUIObjectConsumer,
         0,
         19,
         0,
         117,
         41,
         22,
         0.0F,
         0.0F,
         0.0F,
         renderInfo.brightness * visibilityAlpha / this.context.worldmapWaypointsScale
      );
   }

   @Override
   public boolean shouldRender(int location, boolean shadow) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      boolean waypointBackgroundsConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.WAYPOINT_BACKGROUNDS);
      boolean renderWaypoints = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.RENDER_WAYPOINTS);
      return renderWaypoints && (!shadow || waypointBackgroundsConfig);
   }

   public boolean renderElement(
      Waypoint w,
      boolean hovered,
      double optionalDepth,
      float optionalScale,
      double partialX,
      double partialY,
      ElementRenderInfo renderInfo,
      GuiGraphics guiGraphics,
      BufferSource vanillaBufferSource,
      MultiTextureRenderTypeRendererProvider rendererProvider
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      boolean renderBackground = hovered || this.context.waypointBackgrounds;
      matrixStack.translate(partialX, partialY, 0.0);
      matrixStack.scale(optionalScale * this.context.worldmapWaypointsScale, optionalScale * this.context.worldmapWaypointsScale, 1.0F);
      matrixStack.pushPose();
      float visibilityAlpha = w.isDisabled() ? 0.3F : 1.0F;
      int color = w.getColor();
      String symbol = w.getSymbol();
      int type = w.getType();
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      int flagU = 35;
      int flagV = 34;
      int flagW = 30;
      int flagH = 43;
      if (symbol.length() > 1) {
         flagU += 35;
         flagW += 13;
      }

      if (w.isTemporary()) {
         flagU += 83;
      }

      matrixStack.translate(-flagW / 2.0F, -flagH + 1, 0.0F);
      if (renderBackground) {
         TextureManager textureManager = Minecraft.getInstance().getTextureManager();
         MapRenderHelper.blitIntoMultiTextureRenderer(
            matrixStack.last().pose(),
            this.context.uniqueTextureUIObjectRenderer,
            0.0F,
            0.0F,
            flagU,
            flagV,
            flagW,
            flagH,
            red * visibilityAlpha,
            green * visibilityAlpha,
            blue * visibilityAlpha,
            visibilityAlpha,
            textureManager.getTexture(WorldMap.guiTextures).getId()
         );
      }

      matrixStack.popPose();
      float oldDestAlpha = w.getDestAlpha();
      if (hovered) {
         w.setDestAlpha(255.0F);
      } else {
         w.setDestAlpha(0.0F);
      }

      if (oldDestAlpha != w.getDestAlpha()) {
         w.setAlphaAnim(new SlowingAnimation(w.getAlpha(), w.getDestAlpha(), 0.8, 1.0));
      }

      if (w.getAlphaAnim() != null) {
         w.setAlpha((float)w.getAlphaAnim().getCurrent());
      }

      float alpha = w.getAlpha();
      XaeroIcon symbolIcon = null;
      int symbolVerticalOffset = 0;
      int symbolWidth = 0;
      Font fontRenderer = Minecraft.getInstance().font;
      int stringWidth = fontRenderer.width(symbol);
      int symbolFrameWidth = stringWidth / 2 > 4 ? 62 : 32;
      if (type != 1 && alpha < 200.0F) {
         symbolVerticalOffset = 5;
         symbolWidth = (stringWidth - 1) * 3;
         symbolIcon = this.symbolCreator.getSymbolTexture(guiGraphics, symbol);
      } else if (type == 1) {
         symbolVerticalOffset = 3;
         symbolWidth = 27;
         symbolIcon = this.symbolCreator.getDeathSymbolTexture(guiGraphics);
      }

      if (symbolIcon != null) {
         matrixStack.pushPose();
         matrixStack.translate(-1.0F - symbolWidth / 2.0F, 62 + (renderBackground ? -43 + symbolVerticalOffset - 1 : -12), 0.0F);
         matrixStack.scale(1.0F, -1.0F, 1.0F);
         MapRenderHelper.blitIntoMultiTextureRenderer(
            matrixStack.last().pose(),
            this.context.uniqueTextureUIObjectRenderer,
            0.0F,
            0.0F,
            symbolIcon.getOffsetX() + 1,
            symbolIcon.getOffsetY() + 1,
            symbolFrameWidth,
            62,
            visibilityAlpha,
            visibilityAlpha,
            visibilityAlpha,
            visibilityAlpha,
            symbolIcon.getTextureAtlas().getWidth(),
            symbolIcon.getTextureAtlas().getWidth(),
            symbolIcon.getTextureAtlas().getTextureId()
         );
         matrixStack.popPose();
      }

      if ((int)alpha > 0) {
         int tc = (int)alpha << 24 | 16777215;
         String name = w.getName();
         int len = fontRenderer.width(name);
         matrixStack.translate(0.0F, renderBackground ? -38 : -11, 0.0F);
         matrixStack.scale(3.0F, 3.0F, 1.0F);
         int bgLen = Math.max(len + 2, 10);
         MapRenderHelper.fillIntoExistingBuffer(
            matrixStack.last().pose(), this.context.textBGConsumer, -bgLen / 2, -1, bgLen / 2, 9, red, green, blue, alpha / 255.0F
         );
         MapRenderHelper.fillIntoExistingBuffer(
            matrixStack.last().pose(), this.context.textBGConsumer, -bgLen / 2, -1, bgLen / 2, 8, 0.0F, 0.0F, 0.0F, alpha / 255.0F * 200.0F / 255.0F
         );
         if ((int)alpha > 3) {
            matrixStack.translate(0.0F, 0.0F, 1.0F);
            Misc.drawNormalText(matrixStack, name, -(len - 1) / 2.0F, 0.0F, tc, false, vanillaBufferSource);
         }
      }

      return false;
   }

   @Override
   public void preRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
      BufferSource renderTypeBuffers = WorldMap.worldMapClientOnly.customVertexConsumers.getRenderTypeBuffers();
      this.context.regularUIObjectConsumer = renderTypeBuffers.getBuffer(CustomRenderTypes.GUI_BILINEAR);
      this.context.textBGConsumer = renderTypeBuffers.getBuffer(CustomRenderTypes.MAP_ELEMENT_TEXT_BG);
      this.context.uniqueTextureUIObjectRenderer = rendererProvider.getRenderer(
         t -> RenderSystem.setShaderTexture(0, t), MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.GUI_BILINEAR_PREMULTIPLIED
      );
      this.context.deathpoints = this.minimap.getDeathpoints();
      Minecraft mc = Minecraft.getInstance();
      this.context.userScale = mc.screen != null && mc.screen instanceof GuiMap ? ((GuiMap)mc.screen).getUserScale() : 1.0;
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      this.context.waypointBackgrounds = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.WAYPOINT_BACKGROUNDS);
      this.context.minZoomForLocalWaypoints = (Double)configManager.getEffective(WorldMapProfiledConfigOptions.MIN_ZOOM_LOCAL_WAYPOINTS);
      SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
      this.context.showDisabledWaypoints = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.DISPLAY_DISABLED_WAYPOINTS);
   }

   @Override
   public void postRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
      BufferSource renderTypeBuffers = WorldMap.worldMapClientOnly.customVertexConsumers.getRenderTypeBuffers();
      rendererProvider.draw(this.context.uniqueTextureUIObjectRenderer);
      renderTypeBuffers.endBatch();
   }

   @Deprecated
   @Override
   public void beforeRender(
      int location,
      Minecraft mc,
      GuiGraphics guiGraphics,
      double cameraX,
      double cameraZ,
      double mouseX,
      double mouseZ,
      float brightness,
      double scale,
      double screenSizeBasedScale,
      TextureManager textureManager,
      Font fontRenderer,
      BufferSource vanillaBufferSource,
      MultiTextureRenderTypeRendererProvider rendererProvider,
      boolean pre
   ) {
      this.preRender(null, vanillaBufferSource, rendererProvider, pre);
   }

   @Deprecated
   @Override
   public void afterRender(
      int location,
      Minecraft mc,
      GuiGraphics guiGraphics,
      double cameraX,
      double cameraZ,
      double mouseX,
      double mouseZ,
      float brightness,
      double scale,
      double screenSizeBasedScale,
      TextureManager textureManager,
      Font fontRenderer,
      BufferSource vanillaBufferSource,
      MultiTextureRenderTypeRendererProvider rendererProvider,
      boolean pre
   ) {
      this.postRender(null, vanillaBufferSource, rendererProvider, pre);
      this.compatibleRenderInfo = null;
   }

   @Deprecated
   public void renderElementPre(
      int location,
      Waypoint element,
      boolean hovered,
      Minecraft mc,
      GuiGraphics guiGraphics,
      double cameraX,
      double cameraZ,
      double mouseX,
      double mouseZ,
      float brightness,
      double scale,
      double screenSizeBasedScale,
      TextureManager textureManager,
      Font fontRenderer,
      BufferSource vanillaBufferSource,
      MultiTextureRenderTypeRendererProvider rendererProvider,
      float optionalScale,
      double partialX,
      double partialY,
      boolean cave,
      float partialTicks
   ) {
      if (this.compatibleRenderInfo == null) {
         MapProcessor mapProcessor = WorldMapSession.getCurrentSession().getMapProcessor();
         MapDimension mapDimension = mapProcessor.getMapWorld().getCurrentDimension();
         double mapDimScale = mapDimension.calculateDimScale(mapProcessor.getWorldDimensionTypeRegistry());
         this.compatibleRenderInfo = new ElementRenderInfo(
            ElementRenderLocation.fromIndex(location),
            mc.getCameraEntity(),
            mc.player,
            new Vec3(cameraX, -1.0, cameraZ),
            mouseX,
            mouseZ,
            scale,
            cave,
            partialTicks,
            brightness,
            screenSizeBasedScale,
            null,
            mapDimScale,
            mapDimension.getDimId()
         );
      }

      this.renderElementShadow(
         element, hovered, optionalScale, partialX, partialY, this.compatibleRenderInfo, guiGraphics, vanillaBufferSource, rendererProvider
      );
   }

   @Deprecated
   public boolean renderElement(
      int location,
      Waypoint element,
      boolean hovered,
      Minecraft mc,
      GuiGraphics guiGraphics,
      double cameraX,
      double cameraZ,
      double mouseX,
      double mouseZ,
      float brightness,
      double scale,
      double screenSizeBasedScale,
      TextureManager textureManager,
      Font fontRenderer,
      BufferSource vanillaBufferSource,
      MultiTextureRenderTypeRendererProvider rendererProvider,
      int elementIndex,
      double optionalDepth,
      float optionalScale,
      double partialX,
      double partialY,
      boolean cave,
      float partialTicks
   ) {
      return this.renderElement(element, hovered, optionalDepth, optionalScale, partialX, partialY, null, guiGraphics, vanillaBufferSource, rendererProvider);
   }

   @Override
   public int getOrder() {
      return 200;
   }

   @Override
   public boolean shouldBeDimScaled() {
      return false;
   }

   public static final class Builder {
      private SupportXaeroMinimap minimap;
      private WaypointSymbolCreator symbolCreator;

      private Builder() {
      }

      private WaypointRenderer.Builder setDefault() {
         this.setMinimap(null);
         this.setSymbolCreator(null);
         return this;
      }

      public WaypointRenderer.Builder setMinimap(SupportXaeroMinimap minimap) {
         this.minimap = minimap;
         return this;
      }

      public WaypointRenderer.Builder setSymbolCreator(WaypointSymbolCreator symbolCreator) {
         this.symbolCreator = symbolCreator;
         return this;
      }

      public WaypointRenderer build() {
         if (this.minimap != null && this.symbolCreator != null) {
            return new WaypointRenderer(
               new WaypointRenderContext(), new WaypointRenderProvider(this.minimap), new WaypointReader(), this.minimap, this.symbolCreator
            );
         } else {
            throw new IllegalStateException();
         }
      }

      public static WaypointRenderer.Builder begin() {
         return new WaypointRenderer.Builder().setDefault();
      }
   }
}
