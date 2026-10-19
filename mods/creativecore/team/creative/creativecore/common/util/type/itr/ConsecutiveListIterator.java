package team.creative.creativecore.common.util.type.itr;

import java.util.List;
import java.util.ListIterator;

public class ConsecutiveListIterator<T> implements ListIterator<T> {
   public final ListIterator<T>[] itrs;
   protected int index;

   public ConsecutiveListIterator(ListIterator<T>... itrs) {
      this.itrs = itrs;
   }

   public ConsecutiveListIterator(List<List<T>> nestedList) {
      this.itrs = new ListIterator[nestedList.size()];

      for (int i = 0; i < this.itrs.length; i++) {
         this.itrs[i] = nestedList.get(i).listIterator();
      }
   }

   public ConsecutiveListIterator goEnd() {
      while (this.hasNext()) {
         this.next();
      }

      return this;
   }

   @Override
   public boolean hasNext() {
      while (this.index < this.itrs.length && !this.itrs[this.index].hasNext()) {
         if (this.index == this.itrs.length - 1) {
            return this.itrs[this.index].hasNext();
         }

         this.index++;
      }

      return this.index < this.itrs.length;
   }

   @Override
   public T next() {
      return this.itrs[this.index].next();
   }

   @Override
   public boolean hasPrevious() {
      while (this.index >= 0 && !this.itrs[this.index].hasPrevious()) {
         this.index--;
      }

      return this.index >= 0;
   }

   @Override
   public T previous() {
      return this.itrs[this.index].previous();
   }

   @Override
   public int nextIndex() {
      return this.itrs[this.index].nextIndex();
   }

   @Override
   public int previousIndex() {
      return this.itrs[this.index].previousIndex();
   }

   @Override
   public void remove() {
      this.itrs[this.index].remove();
   }

   @Override
   public void set(T e) {
      this.itrs[this.index].set(e);
   }

   @Override
   public void add(T e) {
      this.itrs[this.index].add(e);
   }
}
