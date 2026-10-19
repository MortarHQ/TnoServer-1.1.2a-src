package xaero.map.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.TextureManager;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;

public final class MenuOnlyElementRenderer<E> extends MapElementRenderer<E, Object, MenuOnlyElementRenderer<E>> {
   protected MenuOnlyElementRenderer(MenuOnlyElementReader<E> reader) {
      super(null, null, reader);
   }

   @Override
   public boolean shouldRender(int location, boolean shadow) {
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
      return false;
   }

   @Override
   public void preRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
   }

   @Override
   public void postRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
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
      return false;
   }
}
