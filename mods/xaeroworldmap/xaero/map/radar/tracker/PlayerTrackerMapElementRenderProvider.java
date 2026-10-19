package xaero.map.radar.tracker;

import java.util.Iterator;
import xaero.map.element.MapElementRenderProvider;
import xaero.map.element.render.ElementRenderLocation;

public class PlayerTrackerMapElementRenderProvider<C> extends MapElementRenderProvider<PlayerTrackerMapElement<?>, C> {
   private PlayerTrackerMapElementCollector collector;
   private Iterator<PlayerTrackerMapElement<?>> iterator;

   public PlayerTrackerMapElementRenderProvider(PlayerTrackerMapElementCollector collector) {
      this.collector = collector;
   }

   @Override
   public void begin(ElementRenderLocation location, C context) {
      this.iterator = this.collector.getElements().iterator();
   }

   @Override
   public boolean hasNext(ElementRenderLocation location, C context) {
      return this.iterator != null && this.iterator.hasNext();
   }

   public PlayerTrackerMapElement<?> getNext(ElementRenderLocation location, C context) {
      return this.iterator.next();
   }

   @Override
   public void end(ElementRenderLocation location, C context) {
      this.iterator = null;
   }

   @Deprecated
   @Override
   public void begin(int location, C context) {
      this.begin(ElementRenderLocation.fromIndex(location), context);
   }

   @Deprecated
   @Override
   public boolean hasNext(int location, C context) {
      return this.hasNext(ElementRenderLocation.fromIndex(location), context);
   }

   @Deprecated
   public PlayerTrackerMapElement<?> getNext(int location, C context) {
      return this.getNext(ElementRenderLocation.fromIndex(location), context);
   }

   @Deprecated
   public PlayerTrackerMapElement<?> setupContextAndGetNext(ElementRenderLocation location, C context) {
      return this.getNext(location, context);
   }

   @Deprecated
   public PlayerTrackerMapElement<?> setupContextAndGetNext(int location, C context) {
      return this.setupContextAndGetNext(ElementRenderLocation.fromIndex(location), context);
   }

   @Deprecated
   @Override
   public void end(int location, C context) {
      this.end(ElementRenderLocation.fromIndex(location), context);
   }
}
