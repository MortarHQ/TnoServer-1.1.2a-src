package team.creative.creativecore.common.util.type.itr;

import java.util.Iterator;
import java.util.Objects;

public class NestedIterator<T> implements IterableIterator<T> {
   public final Iterator<? extends Iterable<T>> itr;
   protected Iterator<T> current;

   public NestedIterator(Iterator<? extends Iterable<T>> itrs) {
      Objects.requireNonNull(itrs);
      this.itr = itrs;
   }

   public NestedIterator(Iterable<? extends Iterable<T>> itrs) {
      Objects.requireNonNull(itrs);
      this.itr = itrs.iterator();
   }

   @Override
   public boolean hasNext() {
      while ((this.current == null || !this.current.hasNext()) && this.itr.hasNext()) {
         this.current = this.itr.next().iterator();
      }

      return this.current != null && this.current.hasNext();
   }

   @Override
   public T next() {
      return this.current.next();
   }
}
