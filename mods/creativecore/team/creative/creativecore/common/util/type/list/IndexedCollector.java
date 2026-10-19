package team.creative.creativecore.common.util.type.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import team.creative.creativecore.common.util.type.itr.ComputeNextIterator;
import team.creative.creativecore.common.util.type.itr.FilterIterator;

public class IndexedCollector<T> implements Iterable<T> {
   private static final Predicate PREDICATE = x -> x.getClass() != Integer.class;
   private final List content = new ArrayList();
   private int count;
   private int lastIndex;
   private boolean sectionStarted = false;

   public void add(int index, T element) {
      if (this.sectionStarted) {
         throw new UnsupportedOperationException("Section started so use add(T) instead");
      }

      if (this.count == 0 || this.lastIndex != index) {
         this.content.add(index);
         this.lastIndex = index;
      }

      this.content.add(element);
      this.count++;
   }

   public void add(T element) {
      if (!this.sectionStarted) {
         throw new UnsupportedOperationException("No section started so use add(int, T) instead or start a section first");
      }

      this.content.add(element);
      this.count++;
   }

   public void startSection(int index) {
      if (this.count == 0 || this.lastIndex != index) {
         this.content.add(index);
         this.lastIndex = index;
      }

      this.sectionStarted = true;
   }

   public void endSection() {
      while (!this.content.isEmpty() && this.content.get(this.content.size() - 1).getClass() == Integer.class) {
         this.content.remove(this.content.size() - 1);
      }

      this.sectionStarted = false;
   }

   public boolean isEmpty() {
      return this.count == 0;
   }

   @Override
   public Iterator<T> iterator() {
      if (this.sectionStarted) {
         throw new UnsupportedOperationException("Cannot iterate while section has not ended");
      } else {
         return new FilterIterator<>(this.content.iterator(), PREDICATE);
      }
   }

   public void clear() {
      this.sectionStarted = false;
      this.count = 0;
      this.content.clear();
   }

   public Iterator<T> sectionIterator(final IntConsumer consumer) {
      if (this.sectionStarted) {
         throw new UnsupportedOperationException("Cannot iterate while section has not ended");
      } else {
         return new ComputeNextIterator<T>() {
            Iterator<T> itr = IndexedCollector.this.content.iterator();

            @Override
            protected T computeNext() {
               if (!this.itr.hasNext()) {
                  return (T)this.end();
               }

               T next = this.itr.next();
               if (next.getClass() == Integer.class) {
                  consumer.accept((Integer)next);
                  next = this.itr.next();
               }

               return next;
            }
         };
      }
   }
}
