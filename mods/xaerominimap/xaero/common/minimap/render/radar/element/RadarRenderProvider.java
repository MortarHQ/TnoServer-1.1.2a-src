package xaero.common.minimap.render.radar.element;

import net.minecraft.world.entity.Entity;
import xaero.hud.minimap.element.render.MinimapElementRenderLocation;

@Deprecated
public final class RadarRenderProvider extends xaero.hud.minimap.radar.render.element.RadarRenderProvider {
   @Deprecated
   public void begin(int location, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      this.begin(MinimapElementRenderLocation.fromIndex(location), context);
   }

   @Deprecated
   public boolean hasNext(int location, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      return this.hasNext(MinimapElementRenderLocation.fromIndex(location), context);
   }

   @Deprecated
   public Entity setupContextAndGetNext(int location, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      return this.setupContextAndGetNext(MinimapElementRenderLocation.fromIndex(location), context);
   }

   @Deprecated
   public Entity getNext(int location, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      return this.getNext(MinimapElementRenderLocation.fromIndex(location), context);
   }

   @Deprecated
   public void end(int location, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      this.end(MinimapElementRenderLocation.fromIndex(location), context);
   }

   @Deprecated
   @Override
   public void begin(MinimapElementRenderLocation location, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      super.begin(location, context);
   }

   @Deprecated
   @Override
   public boolean hasNext(MinimapElementRenderLocation location, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      return super.hasNext(location, context);
   }

   @Deprecated
   @Override
   public Entity setupContextAndGetNext(MinimapElementRenderLocation location, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      return super.setupContextAndGetNext(location, context);
   }

   @Deprecated
   @Override
   public Entity getNext(MinimapElementRenderLocation location, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      return super.getNext(location, context);
   }

   @Deprecated
   @Override
   public void end(MinimapElementRenderLocation location, xaero.hud.minimap.radar.render.element.RadarRenderContext context) {
      super.end(location, context);
   }
}
