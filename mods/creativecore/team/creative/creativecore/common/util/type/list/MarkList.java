package team.creative.creativecore.common.util.type.list;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class MarkList<T> implements Iterable<T> {
   public static final MarkList EMPTY = new MarkList(Collections.EMPTY_LIST);
   private final List<T> content;
   private final BitSet set;
   private int remaining;

   public MarkList(List<T> content) {
      this.content = content;
      this.set = new BitSet(content.size());
      this.remaining = content.size();
   }

   public void mark(int index) {
      if (!this.set.get(index)) {
         this.remaining--;
      }

      this.set.set(index, true);
   }

   public void reset(int index) {
      if (this.set.get(index)) {
         this.remaining++;
      }

      this.set.set(index, false);
   }

   public boolean is(int index) {
      return this.set.get(index);
   }

   public int remaing() {
      return this.remaining;
   }

   public boolean isEmpty() {
      return this.remaining <= 0;
   }

   public MarkIterator<T> iterator() {
      return new MarkIterator<T>() {
         int next = 0;
         int last = 0;
         boolean found = false;

         @Override
         public boolean hasNext() {
            while (!this.found && this.next < MarkList.this.content.size()) {
               if (!MarkList.this.set.get(this.next)) {
                  this.found = true;
               } else {
                  this.next++;
               }
            }

            return this.found;
         }

         @Override
         public T next() {
            this.last = this.next;
            this.found = false;
            this.next++;
            return MarkList.this.content.get(this.last);
         }

         @Override
         public void mark() {
            if (!MarkList.this.set.get(this.last)) {
               MarkList.this.remaining--;
            }

            MarkList.this.set.set(this.last);
         }
      };
   }

   public void clear() {
      this.set.clear();
      this.remaining = this.content.size();
   }

   public Iterable<T> allIgnoreMark() {
      return this.content;
   }

   public T getIgnoreMark(int index) {
      return this.content.get(index);
   }

   public int sizeIgnoreMark() {
      return this.content.size();
   }
}
