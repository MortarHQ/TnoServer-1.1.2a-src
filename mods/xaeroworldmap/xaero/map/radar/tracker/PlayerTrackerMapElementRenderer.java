package xaero.map.radar.tracker;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.entity.player.Player;
import xaero.lib.client.config.ClientConfigManager;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.animation.SlowingAnimation;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.element.MapElementRenderer;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.graphics.CustomRenderTypes;
import xaero.map.graphics.MapRenderHelper;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.map.icon.XaeroIcon;
import xaero.map.icon.XaeroIconAtlas;

public final class PlayerTrackerMapElementRenderer
   extends MapElementRenderer<PlayerTrackerMapElement<?>, PlayerTrackerMapElementRenderContext, PlayerTrackerMapElementRenderer> {
   private final PlayerTrackerMapElementCollector elementCollector;
   private TrackedPlayerIconManager trackedPlayerIconManager;

   private PlayerTrackerMapElementRenderer(
      PlayerTrackerMapElementCollector elementCollector,
      PlayerTrackerMapElementRenderContext context,
      PlayerTrackerMapElementRenderProvider<PlayerTrackerMapElementRenderContext> provider,
      PlayerTrackerMapElementReader reader
   ) {
      super(context, provider, reader);
      this.elementCollector = elementCollector;
   }

   public TrackedPlayerIconManager getTrackedPlayerIconManager() {
      return this.trackedPlayerIconManager;
   }

   @Override
   public void preRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
      Minecraft mc = Minecraft.getInstance();
      WorldMapSession mapSession = WorldMapSession.getCurrentSession();
      MapProcessor mapProcessor = mapSession.getMapProcessor();
      BufferSource renderTypeBuffers = WorldMap.worldMapClientOnly.customVertexConsumers.getRenderTypeBuffers();
      this.context.textBGConsumer = renderTypeBuffers.getBuffer(CustomRenderTypes.MAP_ELEMENT_TEXT_BG);
      this.context.uniqueTextureUIObjectRenderer = rendererProvider.getRenderer(
         t -> RenderSystem.setShaderTexture(0, t), MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.GUI_BILINEAR_PREMULTIPLIED
      );
      this.context.mapDimId = mapProcessor.getMapWorld().getCurrentDimensionId();
      this.context.mapDimDiv = mapProcessor.getMapWorld()
         .getCurrentDimension()
         .calculateDimDiv(mapProcessor.getWorldDimensionTypeRegistry(), mc.level.dimensionType());
   }

   @Override
   public void postRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
      BufferSource renderTypeBuffers = WorldMap.worldMapClientOnly.customVertexConsumers.getRenderTypeBuffers();
      rendererProvider.draw(this.context.uniqueTextureUIObjectRenderer);
      renderTypeBuffers.endBatch();
      if (!shadow) {
         this.elementCollector.resetRenderedOnRadarFlags();
      }
   }

   public void renderElementShadow(
      PlayerTrackerMapElement<?> element,
      boolean hovered,
      float optionalScale,
      double partialX,
      double partialY,
      ElementRenderInfo renderInfo,
      GuiGraphics guiGraphics,
      BufferSource vanillaBufferSource,
      MultiTextureRenderTypeRendererProvider rendererProvider
   ) {
   }

   public boolean renderElement(
      PlayerTrackerMapElement<?> e,
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
      PlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(e.getPlayerId());
      if (info != null) {
         Minecraft mc = Minecraft.getInstance();
         Font fontRenderer = mc.font;
         Player clientPlayer = mc.level.getPlayerByUUID(e.getPlayerId());
         matrixStack.pushPose();
         double fadeDest = hovered ? 1.0 : 0.0;
         boolean firstTime = e.getFadeAnim() == null;
         if (firstTime || e.getFadeAnim().getDestination() != fadeDest) {
            e.setFadeAnim(new SlowingAnimation(e.getFadeAnim() == null ? 0.0 : e.getFadeAnim().getCurrent(), fadeDest, 0.8, 0.001));
         }

         float alpha = (float)e.getFadeAnim().getCurrent();
         if (!e.wasRenderedOnRadar() || alpha > 0.0F) {
            if (alpha > 0.0F) {
               matrixStack.pushPose();
               matrixStack.scale(2.0F, 2.0F, 1.0F);
               String name = info.getProfile().getName();
               int nameWidth = fontRenderer.width(name);
               MapRenderHelper.fillIntoExistingBuffer(
                  matrixStack.last().pose(), this.context.textBGConsumer, -8 - nameWidth - 2, -6, -7, 6, 0.0F, 0.0F, 0.0F, alpha * 119.0F / 255.0F
               );
               int textAlphaComponent = (int)(alpha * 255.0F);
               if (textAlphaComponent > 3) {
                  int tc = 16777215 | textAlphaComponent << 24;
                  guiGraphics.drawString(fontRenderer, name, -8 - nameWidth, -4, tc);
               }

               matrixStack.popPose();
            }

            matrixStack.translate(partialX, partialY, 0.0);
            matrixStack.scale((2.0F + alpha) / 3.0F, (2.0F + alpha) / 3.0F, 1.0F);
            XaeroIcon icon = this.getTrackedPlayerIconManager().getIcon(guiGraphics, clientPlayer, info, e);
            XaeroIconAtlas atlas = icon.getTextureAtlas();
            MapRenderHelper.blitIntoMultiTextureRenderer(
               matrixStack.last().pose(),
               this.context.uniqueTextureUIObjectRenderer,
               -15.0F,
               -15.0F,
               icon.getOffsetX() + 1,
               icon.getOffsetY() + 31,
               30,
               30,
               30,
               -30,
               1.0F,
               1.0F,
               1.0F,
               1.0F,
               atlas.getWidth(),
               atlas.getWidth(),
               atlas.getTextureId()
            );
         }

         matrixStack.popPose();
      }

      return false;
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
   }

   @Deprecated
   public void renderElementPre(
      int location,
      PlayerTrackerMapElement<?> element,
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
      this.renderElementShadow(element, hovered, optionalScale, partialX, partialY, null, guiGraphics, vanillaBufferSource, rendererProvider);
   }

   @Deprecated
   public boolean renderElement(
      int location,
      PlayerTrackerMapElement<?> element,
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
   public boolean shouldRender(int location, boolean shadow) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      return (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.DISPLAY_TRACKED_PLAYERS);
   }

   @Override
   public int getOrder() {
      return 200;
   }

   public PlayerTrackerMapElementCollector getCollector() {
      return this.elementCollector;
   }

   public void update(Minecraft mc) {
      if (this.trackedPlayerIconManager == null) {
         this.trackedPlayerIconManager = TrackedPlayerIconManager.Builder.begin().build();
      }

      this.elementCollector.update(mc);
   }

   public static final class Builder {
      private Builder() {
      }

      private PlayerTrackerMapElementRenderer.Builder setDefault() {
         return this;
      }

      public PlayerTrackerMapElementRenderer build() {
         PlayerTrackerMapElementCollector collector = new PlayerTrackerMapElementCollector(
            WorldMap.playerTrackerSystemManager, () -> WorldMap.trackedPlayerMenuRenderer.updateFilteredList()
         );
         return new PlayerTrackerMapElementRenderer(
            collector, new PlayerTrackerMapElementRenderContext(), new PlayerTrackerMapElementRenderProvider<>(collector), new PlayerTrackerMapElementReader()
         );
      }

      public static PlayerTrackerMapElementRenderer.Builder begin() {
         return new PlayerTrackerMapElementRenderer.Builder().setDefault();
      }
   }
}
