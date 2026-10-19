package xaero.map.executor;

import net.minecraft.util.thread.BlockableEventLoop;

public class Executor extends BlockableEventLoop<Runnable> {
   private final Thread thread;

   public Executor(String name, Thread thread) {
      super(name);
      this.thread = thread;
   }

   protected Runnable wrapRunnable(Runnable runnable) {
      return runnable;
   }

   protected boolean shouldRun(Runnable runnable) {
      return true;
   }

   protected Thread getRunningThread() {
      return this.thread;
   }

   public void runAllTasks() {
      if (!this.isSameThread()) {
         throw new RuntimeException("wrong thread!");
      }

      super.runAllTasks();
   }
}
