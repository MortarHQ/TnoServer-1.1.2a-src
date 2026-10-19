package xaero.common.minimap.render.radar.element;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import xaero.hud.minimap.element.render.MinimapElementRenderLocation;

@Deprecated
public final class RadarElementReader extends xaero.hud.minimap.radar.render.element.RadarElementReader {
   @Deprecated
   @Override
   public double getRenderX(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getRenderX(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public double getRenderY(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getRenderY(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public double getRenderZ(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getRenderZ(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public boolean isHidden(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      return super.isHidden(element, context);
   }

   @Deprecated
   @Override
   public int getInteractionBoxLeft(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getInteractionBoxLeft(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public int getInteractionBoxRight(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getInteractionBoxRight(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public int getInteractionBoxTop(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getInteractionBoxTop(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public int getInteractionBoxBottom(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getInteractionBoxBottom(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public int getRenderBoxLeft(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getRenderBoxLeft(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public int getRenderBoxRight(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getRenderBoxRight(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public int getRenderBoxTop(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getRenderBoxTop(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public int getRenderBoxBottom(Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context, float partialTicks) {
      return super.getRenderBoxBottom(element, context, partialTicks);
   }

   @Deprecated
   @Override
   public int getLeftSideLength(Entity element, Minecraft mc) {
      return super.getLeftSideLength(element, mc);
   }

   @Deprecated
   @Override
   public String getMenuName(Entity element) {
      return super.getMenuName(element);
   }

   @Deprecated
   @Override
   public String getFilterName(Entity element) {
      return super.getFilterName(element);
   }

   @Deprecated
   @Override
   public int getMenuTextFillLeftPadding(Entity element) {
      return super.getMenuTextFillLeftPadding(element);
   }

   @Deprecated
   @Override
   public int getRightClickTitleBackgroundColor(Entity element) {
      return super.getRightClickTitleBackgroundColor(element);
   }

   @Deprecated
   public float getBoxScale(int location, Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      return this.getBoxScale(MinimapElementRenderLocation.fromIndex(location), element, context);
   }

   @Deprecated
   @Override
   public float getBoxScale(MinimapElementRenderLocation location, Entity element, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      return super.getBoxScale(location, element, context);
   }

   @Deprecated
   public boolean isInteractable(int location, Entity element) {
      return this.isInteractable(MinimapElementRenderLocation.fromIndex(location), element);
   }

   @Deprecated
   @Override
   public boolean isInteractable(MinimapElementRenderLocation location, Entity element) {
      return super.isInteractable(location, element);
   }

   @Deprecated
   @Override
   public boolean shouldScaleBoxWithOptionalScale() {
      return super.shouldScaleBoxWithOptionalScale();
   }
}
