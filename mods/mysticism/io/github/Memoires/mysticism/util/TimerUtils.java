package io.github.Memoires.mysticism.util;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.LifecycleEvent.ServerState;
import dev.architectury.event.events.common.TickEvent.Server;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TimerUtils {
   private static final Map<String, TimerUtils.Timer> TIMERS = new ConcurrentHashMap<>();
   private static final Map<String, TimerUtils.RepeatingTimer> REPEATING = new ConcurrentHashMap<>();

   private TimerUtils() {
   }

   public static void register() {
      TickEvent.SERVER_PRE.register((Server)server -> {
         TIMERS.values().forEach(TimerUtils.Timer::update);
         REPEATING.values().forEach(TimerUtils.RepeatingTimer::update);
      });
      LifecycleEvent.SERVER_STOPPING.register((ServerState)server -> {
         TIMERS.clear();
         REPEATING.clear();
      });
   }

   public static void runLater(String key, int delayTicks, Runnable action) {
      TimerUtils.Timer t = getOrCreateTimer(key);
      t.start(delayTicks, () -> {
         try {
            action.run();
         } finally {
            t.stop();
            removeTimer(key);
         }
      });
   }

   public static boolean cancel(String key) {
      boolean cancelled = false;
      TimerUtils.Timer t = TIMERS.remove(key);
      if (t != null) {
         t.stop();
         cancelled = true;
      }

      TimerUtils.RepeatingTimer r = REPEATING.remove(key);
      if (r != null) {
         r.stop();
         cancelled = true;
      }

      return cancelled;
   }

   private static void removeTimer(String key) {
      TIMERS.remove(key);
   }

   public static TimerUtils.Timer getOrCreateTimer(String name) {
      return TIMERS.computeIfAbsent(name, TimerUtils.Timer::new);
   }

   public static TimerUtils.RepeatingTimer getOrCreateRepeating(String name) {
      return REPEATING.computeIfAbsent(name, TimerUtils.RepeatingTimer::new);
   }

   public static class RepeatingTimer {
      private final String name;
      private int ticksElapsed;
      private int maxTicks;
      private Runnable everyTickAction;
      private Runnable onComplete;
      private boolean active;

      public RepeatingTimer(String name) {
         this.name = name;
      }

      public void start(int durationTicks, Runnable everyTickAction, Runnable onComplete) {
         this.maxTicks = Math.max(1, durationTicks);
         this.everyTickAction = everyTickAction;
         this.onComplete = onComplete;
         this.ticksElapsed = 0;
         this.active = true;
      }

      public void stop() {
         this.active = false;
      }

      public void reset() {
         this.ticksElapsed = 0;
      }

      public boolean isActive() {
         return this.active;
      }

      public String getName() {
         return this.name;
      }

      private void update() {
         if (this.active) {
            if (this.everyTickAction != null) {
               this.everyTickAction.run();
            }

            if (++this.ticksElapsed >= this.maxTicks) {
               if (this.onComplete != null) {
                  this.onComplete.run();
               }

               this.active = false;
            }
         }
      }
   }

   public static class Timer {
      private final String name;
      private int ticksElapsed;
      private int intervalTicks = 60;
      private boolean active;
      private Runnable action;

      public Timer(String name) {
         this.name = name;
      }

      public void start(int intervalTicks, Runnable action) {
         this.intervalTicks = Math.max(1, intervalTicks);
         this.ticksElapsed = 0;
         this.active = true;
         this.action = action;
      }

      public void stop() {
         this.active = false;
      }

      public void reset() {
         this.ticksElapsed = 0;
      }

      public boolean isActive() {
         return this.active;
      }

      public String getName() {
         return this.name;
      }

      private void update() {
         if (this.active) {
            if (++this.ticksElapsed >= this.intervalTicks) {
               if (this.action != null) {
                  this.action.run();
               }

               this.ticksElapsed = 0;
            }
         }
      }
   }
}
