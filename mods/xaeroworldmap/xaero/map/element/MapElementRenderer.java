package xaero.map.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.TextureManager;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.element.render.ElementRenderer;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;

public abstract class MapElementRenderer<E, C, R extends MapElementRenderer<E, C, R>> extends ElementRenderer<E, C, R> {
   protected MapElementRenderer(C context, MapElementRenderProvider<E, C> provider, MapElementReader<E, C, R> reader) {
      super(context, provider, reader);
   }

   @Override
   public void preRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
      this.beforeRender(
         renderInfo.location.getIndex(),
         Minecraft.getInstance(),
         null,
         renderInfo.renderPos.x,
         renderInfo.renderPos.z,
         renderInfo.mouseX,
         renderInfo.mouseZ,
         renderInfo.brightness,
         renderInfo.scale,
         renderInfo.screenSizeBasedScale,
         Minecraft.getInstance().getTextureManager(),
         Minecraft.getInstance().font,
         vanillaBufferSource,
         rendererProvider,
         shadow
      );
   }

   @Override
   public void postRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
      this.afterRender(
         renderInfo.location.getIndex(),
         Minecraft.getInstance(),
         null,
         renderInfo.renderPos.x,
         renderInfo.renderPos.z,
         renderInfo.mouseX,
         renderInfo.mouseZ,
         renderInfo.brightness,
         renderInfo.scale,
         renderInfo.screenSizeBasedScale,
         Minecraft.getInstance().getTextureManager(),
         Minecraft.getInstance().font,
         vanillaBufferSource,
         rendererProvider,
         shadow
      );
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
      this.renderElementPre(
         renderInfo.location.getIndex(),
         element,
         hovered,
         Minecraft.getInstance(),
         guiGraphics,
         renderInfo.renderPos.x,
         renderInfo.renderPos.z,
         renderInfo.mouseX,
         renderInfo.mouseZ,
         renderInfo.brightness,
         renderInfo.scale,
         renderInfo.screenSizeBasedScale,
         Minecraft.getInstance().getTextureManager(),
         Minecraft.getInstance().font,
         vanillaBufferSource,
         rendererProvider,
         optionalScale,
         partialX,
         partialY,
         renderInfo.cave,
         renderInfo.partialTicks
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
      return this.renderElement(
         renderInfo.location.getIndex(),
         element,
         hovered,
         Minecraft.getInstance(),
         guiGraphics,
         renderInfo.renderPos.x,
         renderInfo.renderPos.z,
         renderInfo.mouseX,
         renderInfo.mouseZ,
         renderInfo.brightness,
         renderInfo.scale,
         renderInfo.screenSizeBasedScale,
         Minecraft.getInstance().getTextureManager(),
         Minecraft.getInstance().font,
         vanillaBufferSource,
         rendererProvider,
         0,
         optionalDepth,
         optionalScale,
         partialX,
         partialY,
         renderInfo.cave,
         renderInfo.partialTicks
      );
   }

   @Override
   public boolean shouldRender(ElementRenderLocation location, boolean shadow) {
      return this.shouldRender(location.getIndex(), shadow);
   }

   @Deprecated
   public abstract void beforeRender(
      int var1,
      Minecraft var2,
      GuiGraphics var3,
      double var4,
      double var6,
      double var8,
      double var10,
      float var12,
      double var13,
      double var15,
      TextureManager var17,
      Font var18,
      BufferSource var19,
      MultiTextureRenderTypeRendererProvider var20,
      boolean var21
   );

   @Deprecated
   public abstract void afterRender(
      int var1,
      Minecraft var2,
      GuiGraphics var3,
      double var4,
      double var6,
      double var8,
      double var10,
      float var12,
      double var13,
      double var15,
      TextureManager var17,
      Font var18,
      BufferSource var19,
      MultiTextureRenderTypeRendererProvider var20,
      boolean var21
   );

   @Deprecated
   public abstract void renderElementPre(
      int var1,
      E var2,
      boolean var3,
      Minecraft var4,
      GuiGraphics var5,
      double var6,
      double var8,
      double var10,
      double var12,
      float var14,
      double var15,
      double var17,
      TextureManager var19,
      Font var20,
      BufferSource var21,
      MultiTextureRenderTypeRendererProvider var22,
      float var23,
      double var24,
      double var26,
      boolean var28,
      float var29
   );

   @Deprecated
   public abstract boolean renderElement(
      int var1,
      E var2,
      boolean var3,
      Minecraft var4,
      GuiGraphics var5,
      double var6,
      double var8,
      double var10,
      double var12,
      float var14,
      double var15,
      double var17,
      TextureManager var19,
      Font var20,
      BufferSource var21,
      MultiTextureRenderTypeRendererProvider var22,
      int var23,
      double var24,
      float var26,
      double var27,
      double var29,
      boolean var31,
      float var32
   );

   @Deprecated
   public abstract boolean shouldRender(int var1, boolean var2);

   @Deprecated
   public MapElementReader<E, C, R> getReader() {
      return (MapElementReader<E, C, R>)super.getReader();
   }

   @Deprecated
   public MapElementRenderProvider<E, C> getProvider() {
      return (MapElementRenderProvider<E, C>)super.getProvider();
   }
}
