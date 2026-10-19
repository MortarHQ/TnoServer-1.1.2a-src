package xaero.map.mods.minimap.element;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.phys.Vec3;
import xaero.common.IXaeroMinimap;
import xaero.common.XaeroMinimapSession;
import xaero.common.minimap.element.render.MinimapElementRenderLocation;
import xaero.common.minimap.element.render.MinimapElementRenderer;
import xaero.common.minimap.render.MinimapRendererHelper;
import xaero.map.MapProcessor;
import xaero.map.WorldMapSession;
import xaero.map.element.MapElementRenderer;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.map.world.MapDimension;

public final class MinimapElementRendererWrapper<E, C> extends MapElementRenderer<E, C, MinimapElementRendererWrapper<E, C>> {
   private final int order;
   private final IXaeroMinimap modMain;
   private final MinimapElementRenderer<E, C> renderer;
   private final Supplier<Boolean> shouldRenderSupplier;
   private ElementRenderInfo compatibleRenderInfo;

   private MinimapElementRendererWrapper(
      IXaeroMinimap modMain,
      C context,
      MinimapElementRenderProviderWrapper<E, C> provider,
      MinimapElementReaderWrapper<E, C> reader,
      MinimapElementRenderer<E, C> renderer,
      Supplier<Boolean> shouldRenderSupplier,
      int order
   ) {
      super(context, provider, reader);
      this.order = order;
      this.renderer = renderer;
      this.modMain = modMain;
      this.shouldRenderSupplier = shouldRenderSupplier;
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
      ElementRenderInfo renderInfo = this.getFakeRenderInfo(
         location,
         mc,
         guiGraphics,
         cameraX,
         cameraZ,
         mouseX,
         mouseZ,
         brightness,
         scale,
         screenSizeBasedScale,
         textureManager,
         fontRenderer,
         vanillaBufferSource,
         rendererProvider,
         pre
      );
      this.preRender(renderInfo, vanillaBufferSource, rendererProvider, pre);
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
      if (this.compatibleRenderInfo == null) {
         this.compatibleRenderInfo = this.getFakeRenderInfo(
            location,
            mc,
            guiGraphics,
            cameraX,
            cameraZ,
            mouseX,
            mouseZ,
            brightness,
            scale,
            screenSizeBasedScale,
            textureManager,
            fontRenderer,
            vanillaBufferSource,
            rendererProvider,
            pre
         );
      }

      this.postRender(this.compatibleRenderInfo, vanillaBufferSource, rendererProvider, pre);
      this.compatibleRenderInfo = null;
   }

   private ElementRenderInfo getFakeRenderInfo(
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
      MapProcessor mapProcessor = WorldMapSession.getCurrentSession().getMapProcessor();
      MapDimension mapDimension = mapProcessor.getMapWorld().getCurrentDimension();
      double mapDimScale = mapDimension.calculateDimScale(mapProcessor.getWorldDimensionTypeRegistry());
      return new ElementRenderInfo(
         ElementRenderLocation.fromIndex(location),
         mc.getCameraEntity(),
         mc.player,
         new Vec3(cameraX, -1.0, cameraZ),
         mouseX,
         mouseZ,
         scale,
         false,
         1.0F,
         brightness,
         screenSizeBasedScale,
         null,
         mapDimScale,
         mapDimension.getDimId()
      );
   }

   @Deprecated
   @Override
   public boolean renderElement(
      int location,
      E element,
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

      return this.renderElement(
         element, hovered, optionalDepth, optionalScale, partialX, partialY, this.compatibleRenderInfo, guiGraphics, vanillaBufferSource, rendererProvider
      );
   }

   @Override
   public void preRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
      BufferSource renderTypeBuffers = this.modMain.getInterfaceRenderer().getCustomVertexConsumers().getBetterPVPRenderTypeBuffers();
      XaeroMinimapSession minimapSession = XaeroMinimapSession.getCurrentSession();
      xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider minimapMultiTextureRender = minimapSession.getMultiTextureRenderTypeRenderers();
      this.renderer
         .preRender(
            MinimapElementRenderLocation.fromWorldMap(renderInfo.location.getIndex()),
            renderInfo.renderEntity,
            renderInfo.player,
            renderInfo.renderPos.x,
            renderInfo.renderPos.y,
            renderInfo.renderPos.z,
            this.modMain,
            renderTypeBuffers,
            minimapMultiTextureRender
         );
   }

   @Override
   public void postRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
      BufferSource renderTypeBuffers = this.modMain.getInterfaceRenderer().getCustomVertexConsumers().getBetterPVPRenderTypeBuffers();
      XaeroMinimapSession minimapSession = XaeroMinimapSession.getCurrentSession();
      xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider minimapMultiTextureRender = minimapSession.getMultiTextureRenderTypeRenderers();
      this.renderer
         .postRender(
            MinimapElementRenderLocation.fromWorldMap(renderInfo.location.getIndex()),
            renderInfo.renderEntity,
            renderInfo.player,
            renderInfo.renderPos.x,
            renderInfo.renderPos.y,
            renderInfo.renderPos.z,
            this.modMain,
            renderTypeBuffers,
            minimapMultiTextureRender
         );
   }

   @Override
   public boolean renderElement(
      E element,
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
      BufferSource renderTypeBuffers = this.modMain.getInterfaceRenderer().getCustomVertexConsumers().getBetterPVPRenderTypeBuffers();
      Minecraft mc = Minecraft.getInstance();
      MinimapRendererHelper helper = this.modMain.getInterfaces().getMinimapInterface().getMinimapFBORenderer().getHelper();
      return this.renderer
         .renderElement(
            MinimapElementRenderLocation.fromWorldMap(renderInfo.location.getIndex()),
            hovered,
            false,
            guiGraphics,
            renderTypeBuffers,
            mc.font,
            renderInfo.framebuffer,
            helper,
            mc.getCameraEntity(),
            mc.player,
            renderInfo.renderPos.x,
            renderInfo.renderPos.y,
            renderInfo.renderPos.z,
            0,
            optionalDepth,
            optionalScale,
            element,
            partialX,
            partialY,
            renderInfo.cave,
            renderInfo.partialTicks
         );
   }

   @Deprecated
   @Override
   public void renderElementPre(
      int location,
      E element,
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
   }

   @Override
   public void renderElementShadow(
      E element,
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

   @Override
   public boolean shouldRender(int location, boolean shadow) {
      return !shadow && this.shouldRenderSupplier.get() && this.renderer.shouldRender(location);
   }

   @Override
   public int getOrder() {
      return this.order;
   }

   public static final class Builder<E, C> {
      private final MinimapElementRenderer<E, C> renderer;
      private Supplier<Boolean> shouldRenderSupplier;
      private IXaeroMinimap modMain;
      private int order;

      private Builder(MinimapElementRenderer<E, C> renderer) {
         this.renderer = renderer;
      }

      private MinimapElementRendererWrapper.Builder<E, C> setDefault() {
         this.setModMain(null);
         this.setShouldRenderSupplier(() -> true);
         this.setOrder(0);
         return this;
      }

      public MinimapElementRendererWrapper.Builder<E, C> setModMain(IXaeroMinimap modMain) {
         this.modMain = modMain;
         return this;
      }

      public MinimapElementRendererWrapper.Builder<E, C> setShouldRenderSupplier(Supplier<Boolean> shouldRenderSupplier) {
         this.shouldRenderSupplier = shouldRenderSupplier;
         return this;
      }

      public MinimapElementRendererWrapper.Builder<E, C> setOrder(int order) {
         this.order = order;
         return this;
      }

      public MinimapElementRendererWrapper<E, C> build() {
         if (this.modMain != null && this.shouldRenderSupplier != null) {
            MinimapElementRenderProviderWrapper<E, C> providerWrapper = new MinimapElementRenderProviderWrapper<>(this.renderer.getProvider());
            MinimapElementReaderWrapper<E, C> readerWrapper = new MinimapElementReaderWrapper<>(this.renderer.getElementReader());
            C context = (C)this.renderer.getContext();
            return new MinimapElementRendererWrapper<>(
               this.modMain, context, providerWrapper, readerWrapper, this.renderer, this.shouldRenderSupplier, this.order
            );
         } else {
            throw new IllegalStateException();
         }
      }

      public static <E, C> MinimapElementRendererWrapper.Builder<E, C> begin(MinimapElementRenderer<E, C> renderer) {
         return new MinimapElementRendererWrapper.Builder<E, C>(renderer).setDefault();
      }
   }
}
