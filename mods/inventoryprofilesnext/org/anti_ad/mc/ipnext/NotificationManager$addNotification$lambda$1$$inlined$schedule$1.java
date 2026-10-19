package org.anti_ad.mc.ipnext;

import java.util.Timer;
import java.util.TimerTask;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.vanilla.Vanilla;

@SourceDebugExtension(
   "SMAP\nTimer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Timer.kt\nkotlin/concurrent/TimersKt$timerTask$1\n+ 2 NotificationManager.kt\norg/anti_ad/mc/ipnext/NotificationManager\n*L\n1#1,147:1\n43#2:148\n63#2,8:149\n*E\n"
)
public final class NotificationManager$addNotification$lambda$1$$inlined$schedule$1 extends TimerTask {
   @Override
   public final void run() {
      Vanilla.INSTANCE.mc().execute(NotificationManager$addNotification$1$1$1.INSTANCE);
      NotificationManager.access$getReadWriteLock$p().writeLock().lock();

      try {
         Timer var10000 = NotificationManager.access$getTimer$p();
         if (var10000 != null) {
            var10000.cancel();
         }

         NotificationManager.access$setTimer$p(null);
      } finally {
         NotificationManager.access$getReadWriteLock$p().writeLock().unlock();
      }
   }
}
