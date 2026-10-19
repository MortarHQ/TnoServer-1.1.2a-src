package com.iafenvoy.uranus.object.entity.pathfinding.raycoms;

import com.iafenvoy.uranus.Uranus;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public final class Pathfinding {
   private static final BlockingQueue<Runnable> jobQueue = new LinkedBlockingDeque<>();
   private static ThreadPoolExecutor executor;

   private Pathfinding() {
   }

   public static ThreadPoolExecutor getExecutor() {
      if (executor == null) {
         executor = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.SECONDS, jobQueue, new Pathfinding.UranusThreadFactory());
      }

      return executor;
   }

   public static class UranusThreadFactory implements ThreadFactory {
      public static int id;

      @Override
      public Thread newThread(@NotNull Runnable runnable) throws RuntimeException {
         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
         Thread thread = new Thread(runnable, "Uranus Pathfinding Worker #" + id++);
         thread.setDaemon(true);
         thread.setPriority(10);
         if (thread.getContextClassLoader() != classLoader) {
            Uranus.LOGGER.info("Corrected CCL of new Uranus Pathfinding Thread, was: {}", thread.getContextClassLoader().toString());
            thread.setContextClassLoader(classLoader);
         }

         thread.setUncaughtExceptionHandler((thread1, throwable) -> Uranus.LOGGER.error("Uranus Pathfinding Thread errored! ", throwable));
         return thread;
      }
   }
}
