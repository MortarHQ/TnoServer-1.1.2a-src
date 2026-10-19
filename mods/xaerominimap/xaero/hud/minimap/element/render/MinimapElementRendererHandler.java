package xaero.hud.minimap.element.render;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xaero.common.HudMod;
import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.common.misc.OptimizedMath;
import xaero.hud.minimap.BuiltInHudModules;
import xaero.hud.minimap.module.MinimapSession;

public abstract class MinimapElementRendererHandler {
   private final HudMod modMain;
   private final List<MinimapElementRenderer<?, ?>> renderers;
   protected final MinimapElementRenderLocation location;
   private final int indexLimit;

   protected MinimapElementRendererHandler(HudMod modMain, List<MinimapElementRenderer<?, ?>> renderers, MinimapElementRenderLocation location, int indexLimit) {
      this.modMain = modMain;
      this.renderers = renderers;
      this.location = location;
      this.indexLimit = indexLimit;
   }

   public void add(MinimapElementRenderer<?, ?> renderer) {
      this.renderers.add(renderer);
      Collections.sort(this.renderers);
   }

   public void render(
      GuiGraphics guiGraphics, Vec3 renderPos, float partialTicks, RenderTarget framebuffer, double backgroundCoordinateScale, ResourceKey<Level> mapDimension
   ) {
      Minecraft mc = Minecraft.getInstance();
      Entity renderEntity = mc.getCameraEntity();
      Player player = mc.player;
      MinimapSession session = BuiltInHudModules.MINIMAP.getCurrentSession();
      MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers = session.getMultiTextureRenderTypeRenderers();
      BufferSource vanillaBufferSource = guiGraphics.bufferSource();
      boolean cave = session.getProcessor().isCaveModeDisplayed();
      MinimapElementRenderInfo renderInfo = new MinimapElementRenderInfo(
         this.location, renderEntity, player, renderPos, cave, partialTicks, framebuffer, backgroundCoordinateScale, mapDimension
      );
      PoseStack matrixStack = guiGraphics.pose();
      this.beforeRender(guiGraphics, renderInfo, vanillaBufferSource);
      int indexLimit = this.getIndexLimit();

      for (int i = 0; i < this.renderers.size(); i++) {
         MinimapElementRenderer<?, ?> renderer = this.renderers.get(i);
         int elementIndex = 0;
         elementIndex = this.renderForRenderer(renderer, guiGraphics, elementIndex, multiTextureRenderTypeRenderers, indexLimit, renderInfo);
         matrixStack.translate(0.0, 0.0, this.getElementIndexDepth(elementIndex, indexLimit));
         indexLimit -= elementIndex;
         if (indexLimit < 0) {
            indexLimit = 0;
         }
      }

      this.afterRender(guiGraphics, renderInfo, vanillaBufferSource);
   }

   protected <E, RRC, RR extends MinimapElementRenderer<E, RRC>> int renderForRenderer(
      RR renderer,
      GuiGraphics guiGraphics,
      int elementIndex,
      MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers,
      int indexLimit,
      MinimapElementRenderInfo renderInfo
   ) {
      MinimapElementRenderLocation location = this.location;
      if (!renderer.shouldRender(location)) {
         return elementIndex;
      }

      BufferSource vanillaBufferSource = guiGraphics.bufferSource();
      MinimapElementReader<E, RRC> elementReader = renderer.elementReader;
      MinimapElementRenderProvider<E, RRC> provider = renderer.provider;
      RRC context = (RRC)renderer.context;
      renderer.preRender(renderInfo, vanillaBufferSource, multiTextureRenderTypeRenderers);
      provider.begin(location, context);

      while (provider.hasNext(location, context)) {
         E element = provider.setupContextAndGetNext(location, context);
         if (element != null && !elementReader.isHidden(element, context)) {
            double optionalDepth = this.getElementIndexDepth(elementIndex, indexLimit);
            if (this.transformAndRenderForRenderer(element, renderer, context, elementIndex, optionalDepth, renderInfo, guiGraphics, vanillaBufferSource)) {
               elementIndex++;
            }
         }
      }

      provider.end(location, context);
      renderer.postRender(renderInfo, vanillaBufferSource, multiTextureRenderTypeRenderers);
      return elementIndex;
   }

   protected <E, RRC, RR extends MinimapElementRenderer<E, RRC>> boolean transformAndRenderForRenderer(
      E element,
      RR renderer,
      RRC context,
      int elementIndex,
      double optionalDepth,
      MinimapElementRenderInfo renderInfo,
      GuiGraphics guiGraphics,
      BufferSource vanillaBufferSource
   ) {
      MinimapElementReader<E, RRC> elementReader = renderer.elementReader;
      double elementX = elementReader.getRenderX(element, context, renderInfo.partialTicks);
      double elementY = elementReader.getRenderY(element, context, renderInfo.partialTicks);
      double elementZ = elementReader.getRenderZ(element, context, renderInfo.partialTicks);
      double elementCoordinateScale = elementReader.getCoordinateScale(element, context, renderInfo);
      double coordinateMultiplier = elementCoordinateScale / renderInfo.backgroundCoordinateScale;
      if (coordinateMultiplier == 1.0) {
         return this.transformAndRenderForRenderer(
            element, elementX, elementY, elementZ, renderer, context, elementIndex, optionalDepth, renderInfo, guiGraphics, vanillaBufferSource
         );
      }

      if (elementReader.shouldScalePartialCoordinates(element, context, renderInfo)) {
         elementX *= coordinateMultiplier;
         elementZ *= coordinateMultiplier;
      } else {
         int flooredRenderX = OptimizedMath.myFloor(elementX);
         int flooredRenderZ = OptimizedMath.myFloor(elementZ);
         elementX = OptimizedMath.myFloor(flooredRenderX * coordinateMultiplier) + (elementX - flooredRenderX);
         elementZ = OptimizedMath.myFloor(flooredRenderZ * coordinateMultiplier) + (elementZ - flooredRenderZ);
      }

      return this.transformAndRenderForRenderer(
         element, elementX, elementY, elementZ, renderer, context, elementIndex, optionalDepth, renderInfo, guiGraphics, vanillaBufferSource
      );
   }

   protected double getElementIndexDepth(int elementIndex, int indexLimit) {
      return (elementIndex >= indexLimit ? indexLimit : elementIndex) * 0.1;
   }

   protected int getIndexLimit() {
      return this.indexLimit;
   }

   protected abstract <E, RRC, RR extends MinimapElementRenderer<E, RRC>> boolean transformAndRenderForRenderer(
      E var1,
      double var2,
      double var4,
      double var6,
      RR var8,
      RRC var9,
      int var10,
      double var11,
      MinimapElementRenderInfo var13,
      GuiGraphics var14,
      BufferSource var15
   );

   protected abstract void beforeRender(GuiGraphics var1, MinimapElementRenderInfo var2, BufferSource var3);

   protected abstract void afterRender(GuiGraphics var1, MinimapElementRenderInfo var2, BufferSource var3);

   public abstract static class Builder {
      protected Builder() {
      }

      protected MinimapElementRendererHandler.Builder setDefault() {
         return this;
      }

      public MinimapElementRendererHandler build() {
         return this.buildInternally(new ArrayList<>());
      }

      protected abstract MinimapElementRendererHandler buildInternally(List<MinimapElementRenderer<?, ?>> var1);
   }
}
