package xaero.map.mods.pac.gui.claim.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.TextureManager;
import xaero.map.WorldMap;
import xaero.map.element.MapElementRenderer;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.graphics.CustomRenderTypes;
import xaero.map.graphics.MapRenderHelper;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.map.mods.pac.gui.claim.ClaimResultElement;
import xaero.map.mods.pac.gui.claim.ClaimResultElementManager;

public class ClaimResultElementRenderer extends MapElementRenderer<ClaimResultElement, ClaimResultElementRenderContext, ClaimResultElementRenderer> {
   private final ClaimResultElementManager manager;

   private ClaimResultElementRenderer(
      ClaimResultElementManager manager,
      ClaimResultElementRenderContext context,
      ClaimResultElementRenderProvider provider,
      ClaimResultElementRenderReader reader
   ) {
      super(context, provider, reader);
      this.manager = manager;
   }

   @Override
   public void preRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
      BufferSource renderTypeBuffers = WorldMap.worldMapClientOnly.customVertexConsumers.getRenderTypeBuffers();
      this.context.guiIconBuffer = renderTypeBuffers.getBuffer(CustomRenderTypes.GUI_BILINEAR);
      this.context.toDelete.clear();
   }

   @Override
   public void postRender(
      ElementRenderInfo renderInfo, BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow
   ) {
      BufferSource renderTypeBuffers = WorldMap.worldMapClientOnly.customVertexConsumers.getRenderTypeBuffers();
      renderTypeBuffers.endBatch();

      for (ClaimResultElement element : this.context.toDelete) {
         this.manager.remove(element);
      }
   }

   public void renderElementShadow(
      ClaimResultElement element,
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
      ClaimResultElement element,
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
      long time = System.currentTimeMillis();
      int iconScale = (int)Math.ceil(optionalScale);
      matrixStack.translate(partialX, partialY, 0.0);
      matrixStack.scale(iconScale, iconScale, 1.0F);
      int iconU = element.hasPositive() ? 0 : 32;
      int iconV = 78;
      float r;
      float g;
      float b;
      if (element.hasPositive() == element.hasNegative()) {
         r = 1.0F;
         g = 0.6666667F;
         b = 0.0F;
      } else if (element.hasPositive()) {
         r = 0.0F;
         g = 0.6666667F;
         b = 0.0F;
      } else {
         r = 0.8F;
         g = 0.1F;
         b = 0.1F;
      }

      MapRenderHelper.blitIntoExistingBuffer(
         matrixStack.last().pose(), this.context.guiIconBuffer, -16.0F, -16.0F, iconU, iconV, 32, 32, 32, 32, r, g, b, 1.0F, 256, 256
      );
      if (hovered) {
         element.setFadeOutStartTime(time);
      }

      if (time - element.getFadeOutStartTime() > 3000L) {
         this.context.toDelete.add(element);
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
      ClaimResultElement element,
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
      ClaimResultElement element,
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
      return true;
   }

   @Override
   public int getOrder() {
      return 150;
   }

   public static final class Builder {
      private ClaimResultElementManager manager;

      private Builder() {
      }

      private ClaimResultElementRenderer.Builder setDefault() {
         this.setManager(null);
         return this;
      }

      public ClaimResultElementRenderer.Builder setManager(ClaimResultElementManager manager) {
         this.manager = manager;
         return this;
      }

      public ClaimResultElementRenderer build() {
         if (this.manager == null) {
            throw new IllegalStateException();
         } else {
            return new ClaimResultElementRenderer(
               this.manager, new ClaimResultElementRenderContext(), new ClaimResultElementRenderProvider(this.manager), new ClaimResultElementRenderReader()
            );
         }
      }

      public static ClaimResultElementRenderer.Builder begin() {
         return new ClaimResultElementRenderer.Builder().setDefault();
      }
   }
}
