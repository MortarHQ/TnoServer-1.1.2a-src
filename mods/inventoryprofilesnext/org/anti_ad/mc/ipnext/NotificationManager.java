package org.anti_ad.mc.ipnext;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NotificationManager {
   @NotNull
   public static final NotificationManager INSTANCE = new NotificationManager();
   @NotNull
   private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
   @NotNull
   private static final List notifications = new ArrayList();
   @Nullable
   private static Timer timer;

   private NotificationManager() {
   }

   public final void addNotification(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      readWriteLock.writeLock().lock();

      try {
         notifications.add(var1);
         if (timer == null) {
            Timer var6;
            Timer var2 = var6 = new Timer("IPN Notifications timer", true);
            TimerTask var3 = new NotificationManager$addNotification$lambda$1$$inlined$schedule$1();
            var2.schedule(var3, 10000L);
            timer = var6;
         }
      } finally {
         readWriteLock.writeLock().unlock();
      }
   }
}
