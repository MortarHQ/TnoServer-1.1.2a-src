package team.creative.creativecore.common.util.type.itr;

public class ArrayOffsetIterator<T> implements IterableIterator<T> {
   public final T[] content;
   private int offset;
   private int count;

   public ArrayOffsetIterator(int offset, T... content) {
      this.content = content;
      this.count = 0;
      this.offset = offset;
   }

   @Override
   public boolean hasNext() {
      return this.count < this.content.length;
   }

   @Override
   public T next() {
      T result = this.content[this.offset];
      this.offset++;
      if (this.offset >= this.content.length) {
         this.offset = 0;
      }

      this.count++;
      return result;
   }
}
