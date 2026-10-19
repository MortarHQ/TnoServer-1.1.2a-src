package team.creative.creativecore.common.util.type.itr;

public abstract class ComputeNextIterator<T> implements IterableIterator<T> {
   private T next;
   private boolean searched;
   private boolean end;

   protected abstract T computeNext();

   protected T end() {
      this.end = true;
      return null;
   }

   @Override
   public boolean hasNext() {
      if (!this.searched) {
         this.next = this.computeNext();
         this.searched = true;
      }

      return !this.end;
   }

   @Override
   public T next() {
      this.searched = false;
      return this.next;
   }
}
