package xaero.map.element;

import xaero.map.element.render.ElementRenderLocation;
import xaero.map.element.render.ElementRenderProvider;

public abstract class MapElementRenderProvider<E, C> extends ElementRenderProvider<E, C> {
   @Deprecated
   public abstract void begin(int var1, C var2);

   @Deprecated
   public abstract boolean hasNext(int var1, C var2);

   @Deprecated
   public abstract E getNext(int var1, C var2);

   @Deprecated
   public E setupContextAndGetNext(int location, C context) {
      return this.getNext(location, context);
   }

   @Deprecated
   public abstract void end(int var1, C var2);

   @Override
   public void begin(ElementRenderLocation location, C context) {
      this.begin(location.getIndex(), context);
   }

   @Override
   public boolean hasNext(ElementRenderLocation location, C context) {
      return this.hasNext(location.getIndex(), context);
   }

   @Override
   public E getNext(ElementRenderLocation location, C context) {
      return this.getNext(location.getIndex(), context);
   }

   @Override
   public E setupContextAndGetNext(ElementRenderLocation location, C context) {
      return this.setupContextAndGetNext(location.getIndex(), context);
   }

   @Override
   public void end(ElementRenderLocation location, C context) {
      this.end(location.getIndex(), context);
   }
}
