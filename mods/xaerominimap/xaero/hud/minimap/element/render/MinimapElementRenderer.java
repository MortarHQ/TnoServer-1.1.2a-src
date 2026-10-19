package xaero.hud.minimap.element.render;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;

public abstract class MinimapElementRenderer<E, RC> implements Comparable<MinimapElementRenderer<?, ?>> {
   protected final MinimapElementReader<E, RC> elementReader;
   protected final RC context;
   protected final MinimapElementRenderProvider<E, RC> provider;

   public MinimapElementRenderer(MinimapElementReader<E, RC> elementReader, MinimapElementRenderProvider<E, RC> provider, RC context) {
      this.elementReader = elementReader;
      this.context = context;
      this.provider = provider;
   }

   public int getOrder() {
      return 0;
   }

   public int compareTo(MinimapElementRenderer<?, ?> o) {
      return Integer.compare(this.getOrder(), o.getOrder());
   }

   public RC getContext() {
      return this.context;
   }

   public MinimapElementRenderProvider<E, RC> getProvider() {
      return this.provider;
   }

   public MinimapElementReader<E, RC> getElementReader() {
      return this.elementReader;
   }

   public abstract boolean renderElement(
      E var1,
      boolean var2,
      boolean var3,
      double var4,
      float var6,
      double var7,
      double var9,
      MinimapElementRenderInfo var11,
      GuiGraphics var12,
      BufferSource var13
   );

   public abstract void preRender(MinimapElementRenderInfo var1, BufferSource var2, MultiTextureRenderTypeRendererProvider var3);

   public abstract void postRender(MinimapElementRenderInfo var1, BufferSource var2, MultiTextureRenderTypeRendererProvider var3);

   public abstract boolean shouldRender(MinimapElementRenderLocation var1);
}
