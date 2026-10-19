package xaero.hud.minimap.radar.render.element;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import xaero.common.minimap.element.render.MinimapElementReader;
import xaero.hud.entity.EntityUtils;
import xaero.hud.minimap.element.render.MinimapElementRenderLocation;

public abstract class RadarElementReader extends MinimapElementReader<Entity, RadarRenderContext> {
   public double getRenderX(Entity element, RadarRenderContext context, float partialTicks) {
      return EntityUtils.getEntityX(element, partialTicks);
   }

   public double getRenderY(Entity element, RadarRenderContext context, float partialTicks) {
      return EntityUtils.getEntityY(element, partialTicks);
   }

   public double getRenderZ(Entity element, RadarRenderContext context, float partialTicks) {
      return EntityUtils.getEntityZ(element, partialTicks);
   }

   public boolean isHidden(Entity element, RadarRenderContext context) {
      return false;
   }

   public int getInteractionBoxLeft(Entity element, RadarRenderContext context, float partialTicks) {
      return context.icon ? -16 : -6;
   }

   public int getInteractionBoxRight(Entity element, RadarRenderContext context, float partialTicks) {
      return context.icon ? 16 : 6;
   }

   public int getInteractionBoxTop(Entity element, RadarRenderContext context, float partialTicks) {
      return context.icon ? -16 : -6;
   }

   public int getInteractionBoxBottom(Entity element, RadarRenderContext context, float partialTicks) {
      return context.icon ? 16 : 6;
   }

   public int getRenderBoxLeft(Entity element, RadarRenderContext context, float partialTicks) {
      return -64;
   }

   public int getRenderBoxRight(Entity element, RadarRenderContext context, float partialTicks) {
      return 64;
   }

   public int getRenderBoxTop(Entity element, RadarRenderContext context, float partialTicks) {
      return -32;
   }

   public int getRenderBoxBottom(Entity element, RadarRenderContext context, float partialTicks) {
      return 32;
   }

   public int getLeftSideLength(Entity element, Minecraft mc) {
      return 0;
   }

   public String getMenuName(Entity element) {
      return "n/a";
   }

   public String getFilterName(Entity element) {
      return this.getMenuName(element);
   }

   public int getMenuTextFillLeftPadding(Entity element) {
      return 0;
   }

   public int getRightClickTitleBackgroundColor(Entity element) {
      return 0;
   }

   public float getBoxScale(MinimapElementRenderLocation location, Entity element, RadarRenderContext context) {
      return (location == MinimapElementRenderLocation.OVER_MINIMAP ? 0.5F : 1.0F) * (float)(context.icon ? context.iconScale : context.dotScale);
   }

   public boolean isInteractable(MinimapElementRenderLocation location, Entity element) {
      return true;
   }

   @Override
   public boolean shouldScaleBoxWithOptionalScale() {
      return true;
   }
}
