package net.jadenxgamer.elysium_api.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public final class ElysiumTimeTracker {
   private ElysiumTimeTracker() {
   }

   private static long getTicksForSource(ElysiumTimeTracker.TimeSource source) {
      return switch (source) {
         case SERVER -> {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            yield server != null ? server.getTickCount() : -1L;
         }
         case CLIENT -> {
            if (FMLEnvironment.dist != Dist.CLIENT) {
               yield -1L;
            } else {
               Minecraft client = Minecraft.getInstance();
               yield client.level != null ? client.level.getGameTime() : -1L;
            }
         }
         case CURRENT -> {
            long clientTicks = getTicksForSource(ElysiumTimeTracker.TimeSource.CLIENT);
            yield clientTicks != -1L ? clientTicks : getTicksForSource(ElysiumTimeTracker.TimeSource.SERVER);
         }
      };
   }

   public static ElysiumTimeTracker.TimeTracker getServer() {
      return new ElysiumTimeTracker.SourceTimeTracker(ElysiumTimeTracker.TimeSource.SERVER);
   }

   public static ElysiumTimeTracker.TimeTracker getClient() {
      return new ElysiumTimeTracker.SourceTimeTracker(ElysiumTimeTracker.TimeSource.CLIENT);
   }

   public static ElysiumTimeTracker.TimeTracker getCurrent() {
      return new ElysiumTimeTracker.SourceTimeTracker(ElysiumTimeTracker.TimeSource.CURRENT);
   }

   public static ElysiumTimeTracker.Timer createTimer(ElysiumTimeTracker.TimeSource source) {
      return new ElysiumTimeTracker.Timer(source);
   }

   public static ElysiumTimeTracker.IntervalTimer createIntervalTimer(long intervalTicks, ElysiumTimeTracker.TimeSource source) {
      return new ElysiumTimeTracker.IntervalTimer(intervalTicks, source);
   }

   public static final class IntervalTimer {
      private final ElysiumTimeTracker.TimeSource source;
      private long intervalTicks;
      private long lastTriggerTick;

      private IntervalTimer(long intervalTicks, ElysiumTimeTracker.TimeSource source) {
         this.source = source;
         this.intervalTicks = intervalTicks;
         this.lastTriggerTick = ElysiumTimeTracker.getTicksForSource(source);
      }

      public boolean check() {
         long current = ElysiumTimeTracker.getTicksForSource(this.source);
         if (current < 0L) {
            return false;
         } else if (current - this.lastTriggerTick >= this.intervalTicks) {
            this.lastTriggerTick = current;
            return true;
         } else {
            return false;
         }
      }

      public void reset() {
         long now = ElysiumTimeTracker.getTicksForSource(this.source);
         if (now >= 0L) {
            this.lastTriggerTick = now;
         }
      }

      public void setInterval(long intervalTicks) {
         this.intervalTicks = intervalTicks;
      }

      public long getElapsedTicksSinceLastTrigger() {
         long current = ElysiumTimeTracker.getTicksForSource(this.source);
         return current < 0L ? -1L : current - this.lastTriggerTick;
      }

      public double getTimeSinceLastTrigger(ElysiumTimeTracker.TimeUnit unit) {
         return unit.convert(this.getElapsedTicksSinceLastTrigger());
      }

      public long getIntervalTicks() {
         return this.intervalTicks;
      }

      public ElysiumTimeTracker.TimeSource getSource() {
         return this.source;
      }
   }

   private record SourceTimeTracker(ElysiumTimeTracker.TimeSource source) implements ElysiumTimeTracker.TimeTracker {
      @Override
      public long getTicks() {
         return ElysiumTimeTracker.getTicksForSource(this.source);
      }
   }

   public enum TimeSource {
      SERVER,
      CLIENT,
      CURRENT;
   }

   public interface TimeTracker {
      long getTicks();

      default double getTime(ElysiumTimeTracker.TimeUnit unit) {
         return unit.convert(this.getTicks());
      }
   }

   public enum TimeUnit {
      MILLISECONDS(50.0),
      SECONDS(0.05),
      MINUTES(8.333333333333334E-4),
      HOURS(1.388888888888889E-5),
      DAYS(5.787037037037037E-7),
      GAME_DAYS(4.1666666666666665E-5),
      GAME_HOURS(0.001);

      private final double multiplier;

      TimeUnit(double multiplier) {
         this.multiplier = multiplier;
      }

      public double convert(long ticks) {
         return ticks < 0L ? -1.0 : ticks * this.multiplier;
      }
   }

   public static final class Timer {
      private final ElysiumTimeTracker.TimeSource source;
      private volatile boolean running;
      private long startTime;
      private long accumulated;

      private Timer(ElysiumTimeTracker.TimeSource source) {
         this.source = source;
         this.reset();
      }

      public synchronized void start() {
         if (!this.running) {
            long now = ElysiumTimeTracker.getTicksForSource(this.source);
            if (now >= 0L) {
               this.startTime = now;
               this.running = true;
            }
         }
      }

      public synchronized void pause() {
         if (this.running) {
            long now = ElysiumTimeTracker.getTicksForSource(this.source);
            if (now >= 0L) {
               this.accumulated = this.accumulated + (now - this.startTime);
               this.running = false;
            }
         }
      }

      public synchronized void resume() {
         if (!this.running) {
            long now = ElysiumTimeTracker.getTicksForSource(this.source);
            if (now >= 0L) {
               this.startTime = now;
               this.running = true;
            }
         }
      }

      public synchronized void reset() {
         this.running = false;
         this.startTime = ElysiumTimeTracker.getTicksForSource(this.source);
         this.accumulated = 0L;
      }

      public synchronized void stop() {
         this.running = false;
         this.startTime = -1L;
         this.accumulated = 0L;
      }

      public synchronized long getElapsedTicks() {
         long now = ElysiumTimeTracker.getTicksForSource(this.source);
         if (now < 0L) {
            return -1L;
         }

         long base = this.running ? now - this.startTime : 0L;
         return this.accumulated + base;
      }

      public double getElapsedTime(ElysiumTimeTracker.TimeUnit unit) {
         return unit.convert(this.getElapsedTicks());
      }

      public boolean isRunning() {
         return this.running;
      }
   }
}
