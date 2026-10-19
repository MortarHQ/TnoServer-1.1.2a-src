package cc.sighs.oelib.event;

public interface CancellableEvent extends Event {
   boolean isCanceled();

   void setCanceled(boolean var1);

   default void cancel() {
      this.setCanceled(true);
   }
}
