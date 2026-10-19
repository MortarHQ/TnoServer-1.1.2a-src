package com.chronodawn.mixin;

import com.chronodawn.ChronoDawn;
import com.chronodawn.events.TimeDistortionEventHandler;
import com.chronodawn.registry.ModDimensions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class SleepMixin {
   private int tickCounter = 0;
   private boolean hasLoggedSleepStatus = false;
   private final Map<UUID, Integer> sleepTimeMap = new HashMap<>();
   private static final int REQUIRED_SLEEP_TIME = 100;

   @Inject(method = "tick(Ljava/util/function/BooleanSupplier;)V", at = @At("TAIL"))
   private void onTick(CallbackInfo ci) {
      ServerLevel level = (ServerLevel)this;
      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         List<ServerPlayer> chronodawnPlayers = level.getServer()
            .getPlayerList()
            .getPlayers()
            .stream()
            .filter(p -> p.level().dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION))
            .collect(Collectors.toList());
         if (!chronodawnPlayers.isEmpty()) {
            for (ServerPlayer player : chronodawnPlayers) {
               UUID playerId = player.getUUID();
               if (player.isSleeping()) {
                  int currentSleepTime = this.sleepTimeMap.getOrDefault(playerId, 0);
                  this.sleepTimeMap.put(playerId, currentSleepTime + 1);
               } else {
                  this.sleepTimeMap.put(playerId, 0);
               }
            }

            this.tickCounter++;
            boolean anyoneSleeping = chronodawnPlayers.stream().anyMatch(LivingEntity::isSleeping);
            if (anyoneSleeping && this.tickCounter % 20 == 0) {
               long currentTime = level.getDayTime();
               long dayOfCycle = currentTime % 24000L;

               for (ServerPlayer player : chronodawnPlayers) {
                  UUID playerId = player.getUUID();
                  int sleepTime = this.sleepTimeMap.getOrDefault(playerId, 0);
                  ChronoDawn.LOGGER
                     .debug(
                        "DEBUG Sleep Check - Player: {}, isSleeping: {}, sleepTime: {}/{}, dayTime: {}, dayOfCycle: {}",
                        new Object[]{player.getName().getString(), player.isSleeping(), sleepTime, 100, currentTime, dayOfCycle}
                     );
               }

               this.hasLoggedSleepStatus = true;
            }

            if (!anyoneSleeping && this.hasLoggedSleepStatus) {
               this.hasLoggedSleepStatus = false;
            }

            boolean allSleptLongEnough = chronodawnPlayers.stream().allMatch(playerx -> this.sleepTimeMap.getOrDefault(playerx.getUUID(), 0) >= 100);
            if (allSleptLongEnough) {
               long currentDayTime = level.getDayTime();
               long dayOfCycle = currentDayTime % 24000L;
               long ticksToMorning;
               if (dayOfCycle >= 0L && dayOfCycle < 1000L) {
                  ticksToMorning = 1000L - dayOfCycle + 24000L;
               } else {
                  ticksToMorning = 24000L - dayOfCycle + 1000L;
               }

               long newDayTime = currentDayTime + ticksToMorning;
               ChronoDawn.LOGGER.debug("SleepMixin: Requesting sleep skip to time {}", newDayTime);
               TimeDistortionEventHandler.requestSleepSkip(level, newDayTime);
               this.sleepTimeMap.clear();

               for (ServerPlayer player : chronodawnPlayers) {
                  player.stopSleepInBed(false, true);
               }

               ChronoDawn.LOGGER
                  .debug(
                     "ChronoDawn: All {} players were sleeping, skipped to morning (dayTime: {} -> {})",
                     new Object[]{chronodawnPlayers.size(), currentDayTime, newDayTime}
                  );
            }
         }
      }
   }
}
