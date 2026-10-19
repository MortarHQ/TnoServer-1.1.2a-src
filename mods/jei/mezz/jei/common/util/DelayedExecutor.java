package mezz.jei.common.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.time.Duration;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.Nullable;

final class DelayedExecutor implements IDelayedExecutor {
   @Nullable
   private static DelayedExecutor INSTANCE;
   private final ScheduledThreadPoolExecutor service;

   public static DelayedExecutor getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new DelayedExecutor();
      }

      return INSTANCE;
   }

   private DelayedExecutor() {
      ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("JEI Deduplicating Run Executor %d").build();
      ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(1, threadFactory);
      service.setRemoveOnCancelPolicy(true);
      this.service = service;
   }

   @Override
   public Future<?> schedule(Runnable command, Duration delay) {
      return this.service.schedule(command, delay.toMillis(), TimeUnit.MILLISECONDS);
   }
}
