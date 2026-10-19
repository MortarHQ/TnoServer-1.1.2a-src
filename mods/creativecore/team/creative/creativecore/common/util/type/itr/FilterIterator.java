package team.creative.creativecore.common.util.type.itr;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public class FilterIterator<T> extends ComputeNextIterator<T> {
   private final Predicate predicate;
   private final Iterator itr;

   public static <T> FilterIterator<T> skipNull(Iterator<T> itr) {
      return new FilterIterator<>(itr, Objects::nonNull);
   }

   public FilterIterator(Iterable iterable, Class clazz) {
      this(iterable.iterator(), clazz);
   }

   public FilterIterator(Iterator iterable, Class clazz) {
      this(iterable, clazz::isInstance);
   }

   public <E> FilterIterator(Iterable<E> iterable, Predicate<E> predicate) {
      this(iterable.iterator(), predicate);
   }

   public <E> FilterIterator(Iterator<E> iterator, Predicate<E> predicate) {
      this.itr = iterator;
      this.predicate = predicate;
   }

   @Override
   protected T computeNext() {
      while (this.itr.hasNext()) {
         Object object = this.itr.next();
         if (this.predicate.test(object)) {
            return (T)object;
         }
      }

      return this.end();
   }
}
