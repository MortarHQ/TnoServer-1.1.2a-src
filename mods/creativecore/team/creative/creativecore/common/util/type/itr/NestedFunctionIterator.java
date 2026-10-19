package team.creative.creativecore.common.util.type.itr;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

public class NestedFunctionIterator<T> implements IterableIterator<T> {
   public final Iterator itr;
   public final Function function;
   protected Iterator<T> current;

   public <V> NestedFunctionIterator(Iterable<V> itrs, Function<V, Iterable<T>> func) {
      Objects.requireNonNull(itrs);
      Objects.requireNonNull(func);
      this.itr = itrs.iterator();
      this.function = func;
   }

   public <V> NestedFunctionIterator(Iterator<V> itrs, Function<V, Iterable<T>> func) {
      Objects.requireNonNull(itrs);
      Objects.requireNonNull(func);
      this.itr = itrs;
      this.function = func;
   }

   @Override
   public boolean hasNext() {
      while ((this.current == null || !this.current.hasNext()) && this.itr.hasNext()) {
         this.current = ((Iterable)this.function.apply(this.itr.next())).iterator();
      }

      return this.current != null && this.current.hasNext();
   }

   @Override
   public T next() {
      return this.current.next();
   }
}
