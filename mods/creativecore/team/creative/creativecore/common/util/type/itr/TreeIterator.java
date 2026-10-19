package team.creative.creativecore.common.util.type.itr;

import java.util.Iterator;
import java.util.function.Function;

public class TreeIterator<T> implements IterableIterator<T> {
   private final Function<T, Iterator<T>> function;
   private T start;
   private final Iterator<T> itr;
   private TreeIterator<T> current;

   public TreeIterator(T start, Function<T, Iterator<T>> function) {
      this.start = start;
      this.function = function;
      this.itr = function.apply(start);
   }

   @Override
   public boolean hasNext() {
      if (this.start != null) {
         return true;
      } else {
         return this.current != null && this.current.hasNext() ? true : this.itr.hasNext();
      }
   }

   @Override
   public T next() {
      if (this.start != null) {
         T next = this.start;
         this.start = null;
         return next;
      }

      if (this.current != null && this.current.hasNext()) {
         return this.current.next();
      }

      this.current = new TreeIterator<>(this.itr.next(), this.function);
      return this.current.next();
   }
}
