package com.chronodawn.events;

import com.chronodawn.ChronoDawn;
import com.chronodawn.registry.ModDimensions;
import dev.architectury.event.events.common.TickEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class TimeDistortionEventHandler {
   private static final Map<ResourceKey<Level>, Float> timeSpeedMap = new ConcurrentHashMap<>();
   private static final Map<ResourceKey<Level>, Integer> timeUntilChangeMap = new ConcurrentHashMap<>();
   private static final Map<ResourceKey<Level>, Long> sleepSkipTargetTimeMap = new ConcurrentHashMap<>();
   private static final Map<ResourceKey<Level>, Float> accumulatedTicksMap = new ConcurrentHashMap<>();
   private static final long SLEEP_SKIP_TICKS_PER_TICK = 500L;
   private static final int TIME_SYNC_INTERVAL = 20;
   private static final float MIN_SPEED = 0.67F;
   private static final float MAX_SPEED = 5.0F;
   private static final int MIN_DURATION = 1200;
   private static final int MAX_DURATION = 6000;

   public static void register() {
      TickEvent.SERVER_LEVEL_POST.register(TimeDistortionEventHandler::onServerLevelTick);
      ChronoDawn.LOGGER.debug("Registered TimeDistortionEventHandler");
   }

   private static void onServerLevelTick(ServerLevel level) {
      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         ResourceKey<Level> dimensionKey = level.dimension();
         if (!timeSpeedMap.containsKey(dimensionKey)) {
            timeSpeedMap.put(dimensionKey, 1.0F);
            timeUntilChangeMap.put(dimensionKey, getRandomDuration(level.getRandom()));
            ChronoDawn.LOGGER.debug("TimeDistortionEventHandler: Initialized for ChronoDawn with speed 1.0x");
         }

         float currentSpeed = timeSpeedMap.get(dimensionKey);
         int timeUntilChange = timeUntilChangeMap.get(dimensionKey);
         Long targetTime = sleepSkipTargetTimeMap.get(dimensionKey);
         if (targetTime != null) {
            long currentTime = level.getDayTime();
            long remainingTicks = targetTime - currentTime;
            if (remainingTicks > 0L) {
               long ticksThisTick = Math.min(remainingTicks, 500L);
               long newDayTime = currentTime + ticksThisTick;
               level.setDayTime(newDayTime);
               ChronoDawn.LOGGER.debug("TimeDistortionEventHandler: Sleep skip advancing... {} / {} remaining", remainingTicks - ticksThisTick, remainingTicks);
            } else {
               sleepSkipTargetTimeMap.remove(dimensionKey);
               ChronoDawn.LOGGER.debug("TimeDistortionEventHandler: Sleep skip complete! Final time: {}", currentTime);
            }
         } else {
            long currentDayTime = level.getDayTime();
            float accumulatedTicks = accumulatedTicksMap.getOrDefault(dimensionKey, 0.0F);
            accumulatedTicks += currentSpeed;
            long ticksToAdvance = (long)accumulatedTicks;
            accumulatedTicks -= (float)ticksToAdvance;
            accumulatedTicksMap.put(dimensionKey, accumulatedTicks);
            if (ticksToAdvance > 0L) {
               long newDayTime = currentDayTime + ticksToAdvance;
               level.setDayTime(newDayTime);
            }

            syncTimeToClients(level);
            timeUntilChangeMap.put(dimensionKey, --timeUntilChange);
            if (timeUntilChange <= 0) {
               float newSpeed = getRandomSpeed(level.getRandom());
               int newDuration = getRandomDuration(level.getRandom());
               ChronoDawn.LOGGER
                  .debug("TimeDistortionEventHandler: Speed changed from {}x to {}x (duration: {} ticks)", new Object[]{currentSpeed, newSpeed, newDuration});
               timeSpeedMap.put(dimensionKey, newSpeed);
               timeUntilChangeMap.put(dimensionKey, newDuration);
            }
         }
      }
   }

   private static float getRandomSpeed(RandomSource random) {
      return 0.67F + random.nextFloat() * 4.33F;
   }

   private static int getRandomDuration(RandomSource random) {
      return 1200 + random.nextInt(4800);
   }

   private static void syncTimeToClients(ServerLevel level) {
      if (level.getServer().getTickCount() % 20 == 0) {
         ClientboundSetTimePacket packet = new ClientboundSetTimePacket(level.getGameTime(), level.getDayTime(), true);

         for (ServerPlayer player : level.players()) {
            player.connection.send(packet);
         }
      }
   }

   public static float getCurrentSpeed(ServerLevel level) {
      ResourceKey<Level> dimensionKey = level.dimension();
      return timeSpeedMap.getOrDefault(dimensionKey, 1.0F);
   }

   public static void requestSleepSkip(ServerLevel level, long targetTime) {
      ResourceKey<Level> dimensionKey = level.dimension();
      sleepSkipTargetTimeMap.put(dimensionKey, targetTime);
      ChronoDawn.LOGGER.debug("Sleep skip requested: target time = {}", targetTime);
   }
}
