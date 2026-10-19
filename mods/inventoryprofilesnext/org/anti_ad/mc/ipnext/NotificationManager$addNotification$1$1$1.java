package org.anti_ad.mc.ipnext;

import java.util.Timer;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.TellPlayer;

@SourceDebugExtension(
   "SMAP\nNotificationManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NotificationManager.kt\norg/anti_ad/mc/ipnext/NotificationManager$addNotification$1$1$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,79:1\n1869#2,2:80\n*S KotlinDebug\n*F\n+ 1 NotificationManager.kt\norg/anti_ad/mc/ipnext/NotificationManager$addNotification$1$1$1\n*L\n46#1:80,2\n*E\n"
)
final class NotificationManager$addNotification$1$1$1 implements Runnable {
   public static final NotificationManager$addNotification$1$1$1 INSTANCE = new NotificationManager$addNotification$1$1$1();

   @Override
   public final void run() {
      NotificationManager.access$getReadWriteLock$p().readLock().lock();

      try {
         for (String var2 : NotificationManager.access$getNotifications$p()) {
            TellPlayer.INSTANCE.chat(var2);
         }

         NotificationManager.access$getNotifications$p().clear();
      } finally {
         NotificationManager.access$getReadWriteLock$p().readLock().unlock();
      }

      NotificationManager.access$getReadWriteLock$p().writeLock().lock();

      try {
         if (NotificationManager.access$getNotifications$p().isEmpty()) {
            Timer var10000 = NotificationManager.access$getTimer$p();
            if (var10000 != null) {
               var10000.cancel();
            }

            NotificationManager.access$setTimer$p(null);
         }
      } finally {
         NotificationManager.access$getReadWriteLock$p().writeLock().unlock();
      }
   }
}
