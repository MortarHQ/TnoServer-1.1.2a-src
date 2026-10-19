package team.creative.creativecore.common.util.type.itr;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class SingleIterator<E> implements IterableIterator<E> {
   private E element;

   public SingleIterator(E element) {
      this.element = element;
   }

   @Override
   public boolean hasNext() {
      return this.element != null;
   }

   @Override
   public E next() {
      if (this.element != null) {
         E result = this.element;
         this.element = null;
         return result;
      } else {
         throw new NoSuchElementException();
      }
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException();
   }

   @Override
   public void forEachRemaining(Consumer<? super E> action) {
      Objects.requireNonNull(action);
      if (this.element != null) {
         action.accept(this.element);
         this.element = null;
      }
   }
}
