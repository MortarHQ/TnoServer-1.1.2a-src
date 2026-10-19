package team.creative.creativecore.common.util.type.itr;

import java.util.Iterator;

public class InverseConsecutiveIterator<T> implements IterableIterator<T> {
   public final Iterator<T>[] itrs;
   protected int index;

   public InverseConsecutiveIterator(Iterator<T>... itrs) {
      this.itrs = itrs;
      this.index = this.itrs.length - 1;
   }

   public InverseConsecutiveIterator(Iterable<T>... itrs) {
      this();

      for (int i = 0; i < itrs.length; i++) {
         this.itrs[i] = itrs[i].iterator();
      }
   }

   @Override
   public boolean hasNext() {
      while (this.index >= 0 && !this.itrs[this.index].hasNext()) {
         this.index--;
      }

      return this.index >= 0;
   }

   @Override
   public T next() {
      return this.itrs[this.index].next();
   }
}
