package team.creative.creativecore.common.util.type.itr;

import java.util.ListIterator;

public abstract class ComputeNextListIterator<T> implements ListIterator<T> {
   private T next;
   private boolean searchedNext;
   private boolean endNext;
   private T previous;
   private boolean searchedPrevious;
   private boolean endPrevious;

   protected abstract T computeNext();

   protected T endNext() {
      this.endNext = true;
      return null;
   }

   @Override
   public boolean hasNext() {
      if (!this.searchedNext) {
         this.next = this.computeNext();
         this.searchedNext = true;
      }

      return !this.endNext;
   }

   @Override
   public T next() {
      this.searchedNext = this.searchedPrevious = false;
      return this.next;
   }

   protected abstract T computePrevious();

   protected T endPrevious() {
      this.endPrevious = true;
      return null;
   }

   @Override
   public boolean hasPrevious() {
      if (!this.searchedPrevious) {
         this.previous = this.computePrevious();
         this.searchedPrevious = true;
      }

      return !this.endPrevious;
   }

   @Override
   public T previous() {
      this.searchedNext = this.searchedPrevious = false;
      return this.previous;
   }
}
