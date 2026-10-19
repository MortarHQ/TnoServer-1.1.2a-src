package twilightforest.util.iterators;

import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public class ZippedIterator<E> implements Iterator<E>, Iterable<E> {
   private final Iterator<E> first;
   private final Iterator<E> second;
   private boolean firstNext;

   public static <E> ZippedIterator<E> fromIterables(Iterable<E> first, Iterable<E> second) {
      return new ZippedIterator<>(first.iterator(), second.iterator());
   }

   public ZippedIterator(Iterator<E> first, Iterator<E> second) {
      this.first = first;
      this.second = second;
      this.firstNext = first.hasNext();
   }

   @Override
   public boolean hasNext() {
      return this.first.hasNext() || this.second.hasNext();
   }

   @Override
   public E next() {
      Iterator<E> current = this.firstNext ? this.first : this.second;
      Iterator<E> other = this.firstNext ? this.second : this.first;
      if (other.hasNext()) {
         this.firstNext = !this.firstNext;
      }

      return current.next();
   }

   @NotNull
   @Override
   public Iterator<E> iterator() {
      return this;
   }
}
