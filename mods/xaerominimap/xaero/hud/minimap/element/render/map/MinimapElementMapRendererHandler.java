package xaero.hud.minimap.element.render.map;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.world.phys.Vec3;
import xaero.common.HudMod;
import xaero.common.minimap.element.render.MinimapElementRendererHandler;
import xaero.hud.minimap.element.render.MinimapElementRenderInfo;
import xaero.hud.minimap.element.render.MinimapElementRenderLocation;
import xaero.hud.minimap.element.render.MinimapElementRenderer;

public class MinimapElementMapRendererHandler extends MinimapElementRendererHandler {
   private double ps;
   private double pc;
   private double zoom;
   private float halfWView;

   protected MinimapElementMapRendererHandler(HudMod modMain, List<MinimapElementRenderer<?, ?>> renderers) {
      super(modMain, renderers, MinimapElementRenderLocation.IN_MINIMAP, 19490);
   }

   public void prepareRender(double ps, double pc, double zoom, float halfWView) {
      this.ps = ps;
      this.pc = pc;
      this.zoom = zoom;
      this.halfWView = halfWView;
   }

   @Override
   protected <E, RRC, RR extends MinimapElementRenderer<E, RRC>> boolean transformAndRenderForRenderer(
      E element,
      double elementX,
      double elementY,
      double elementZ,
      RR renderer,
      RRC context,
      int elementIndex,
      double optionalDepth,
      MinimapElementRenderInfo renderInfo,
      GuiGraphics guiGraphics,
      BufferSource vanillaBufferSource
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      Vec3 renderPos = renderInfo.renderPos;
      double offx = elementX - renderPos.x;
      double offz = elementZ - renderPos.z;
      matrixStack.pushPose();
      double zoomedOffX = offx * this.zoom;
      double zoomedOffZ = offz * this.zoom;
      double translateX = this.ps * zoomedOffX - this.pc * zoomedOffZ;
      double translateY = this.pc * zoomedOffX + this.ps * zoomedOffZ;
      int roundedX = (int)Math.round(translateX);
      int roundedY = (int)Math.round(translateY);
      boolean outOfBounds = Math.abs(roundedX) > this.halfWView || Math.abs(roundedY) > this.halfWView;
      double partialX = translateX - roundedX;
      double partialY = translateY - roundedY;
      matrixStack.translate(roundedX, roundedY, 0.0F);
      boolean result = renderer.renderElement(
         element, false, outOfBounds, optionalDepth, 1.0F, partialX, partialY, renderInfo, guiGraphics, vanillaBufferSource
      );
      matrixStack.popPose();
      return result;
   }

   @Override
   protected void beforeRender(GuiGraphics guiGraphics, MinimapElementRenderInfo renderInfo, BufferSource vanillaBufferSource) {
   }

   @Override
   protected void afterRender(GuiGraphics guiGraphics, MinimapElementRenderInfo renderInfo, BufferSource vanillaBufferSource) {
   }

   public static final class Builder extends xaero.hud.minimap.element.render.MinimapElementRendererHandler.Builder {
      public MinimapElementMapRendererHandler build() {
         return (MinimapElementMapRendererHandler)super.build();
      }

      protected MinimapElementMapRendererHandler buildInternally(List<MinimapElementRenderer<?, ?>> renderers) {
         return new xaero.common.minimap.element.render.map.MinimapElementMapRendererHandler(HudMod.INSTANCE, renderers);
      }

      protected MinimapElementMapRendererHandler.Builder setDefault() {
         super.setDefault();
         return this;
      }

      public static MinimapElementMapRendererHandler.Builder begin() {
         return new MinimapElementMapRendererHandler.Builder().setDefault();
      }
   }
}
