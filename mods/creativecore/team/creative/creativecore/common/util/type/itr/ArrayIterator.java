package team.creative.creativecore.common.util.type.itr;

public class ArrayIterator<T> implements IterableIterator<T> {
   public final T[] content;
   private int index;

   public ArrayIterator(T... content) {
      this.content = content;
      this.index = 0;
   }

   @Override
   public boolean hasNext() {
      return this.index < this.content.length;
   }

   @Override
   public T next() {
      T result = this.content[this.index];
      this.index++;
      return result;
   }
}
