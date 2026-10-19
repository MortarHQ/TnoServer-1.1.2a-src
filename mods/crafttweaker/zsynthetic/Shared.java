package zsynthetic;

public final class Shared<T extends AutoCloseable> {
   private final T value;
   private int refcount = 1;

   public Shared(T value) {
      this.value = value;
   }

   public synchronized void addRef() {
      this.refcount++;
   }

   public synchronized void release() {
      this.refcount--;
      if (this.refcount == 0) {
         try {
            this.value.close();
         } catch (Exception var2) {
         }
      }
   }
}
