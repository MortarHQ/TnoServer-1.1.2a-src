package team.creative.creativecore.common.util.type.itr;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Predicate;

public class FilterListIterator<T> extends ComputeNextListIterator<T> {
   private final Predicate<T> predicate;
   private final ListIterator<T> itr;

   public static <T> FilterListIterator<T> skipNull(ListIterator<T> itr) {
      return new FilterListIterator<>(itr, Objects::nonNull);
   }

   public FilterListIterator(List iterable, Class clazz) {
      this(iterable.listIterator(), clazz);
   }

   public FilterListIterator(ListIterator iterable, Class clazz) {
      this(iterable, clazz::isInstance);
   }

   public FilterListIterator(List<T> iterable, Predicate<T> predicate) {
      this(iterable.listIterator(), predicate);
   }

   public FilterListIterator(ListIterator<T> iterator, Predicate<T> predicate) {
      this.itr = iterator;
      this.predicate = predicate;
   }

   @Override
   protected T computeNext() {
      while (this.itr.hasNext()) {
         T object = this.itr.next();
         if (this.predicate.test(object)) {
            return object;
         }
      }

      return this.endNext();
   }

   @Override
   protected T computePrevious() {
      while (this.itr.hasPrevious()) {
         T object = this.itr.previous();
         if (this.predicate.test(object)) {
            return object;
         }
      }

      return this.endPrevious();
   }

   @Override
   public int nextIndex() {
      return this.itr.nextIndex();
   }

   @Override
   public int previousIndex() {
      return this.itr.previousIndex();
   }

   @Override
   public void remove() {
      this.itr.remove();
   }

   @Override
   public void set(T e) {
      this.itr.set(e);
   }

   @Override
   public void add(T e) {
      this.itr.add(e);
   }
}
