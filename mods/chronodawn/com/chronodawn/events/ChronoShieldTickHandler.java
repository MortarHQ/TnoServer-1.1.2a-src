package com.chronodawn.events;

import com.chronodawn.ChronoDawn;
import com.chronodawn.data.PlayerProgressData;
import com.chronodawn.items.shield.ChronoShieldEffectHandler;
import com.chronodawn.registry.ModSounds;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public final class ChronoShieldTickHandler {
   private static final int DRIFT_INTERVAL_TICKS = 10;
   private static final int PULSE_SOUND_INTERVAL_TICKS = 20;

   private ChronoShieldTickHandler() {
   }

   public static void register() {
      TickEvent.SERVER_LEVEL_POST.register(ChronoShieldTickHandler::onServerLevelTick);
      ChronoDawn.LOGGER.debug("Registered ChronoShieldTickHandler");
   }

   private static void onServerLevelTick(ServerLevel level) {
      long now = level.getGameTime();
      if (now % 10L == 0L) {
         if (!level.players().isEmpty()) {
            boolean pulseTick = now % 20L == 0L;
            PlayerProgressData data = PlayerProgressData.get(level);

            for (ServerPlayer sp : level.players()) {
               long activeUntil = data.getShieldEchoActiveUntil(sp.getUUID());
               if (activeUntil > now) {
                  ChronoShieldEffectHandler.emitEchoActiveDriftFx(sp);
                  if (pulseTick) {
                     level.playSound(null, sp.blockPosition(), (SoundEvent)ModSounds.SHIELD_ECHO_ACTIVE_PULSE.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
                  }
               }
            }
         }
      }
   }
}
