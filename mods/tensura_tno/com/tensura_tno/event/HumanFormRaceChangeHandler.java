package com.tensura_tno.event;

import com.tensura_tno.race.SlimeHumanFormState;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.race.api.RaceEvents;
import io.github.manasmods.manascore.race.api.RaceEvents.SetRaceEvent;
import net.minecraft.server.level.ServerPlayer;

public final class HumanFormRaceChangeHandler {
   private static volatile boolean registered = false;

   private HumanFormRaceChangeHandler() {
   }

   public static void register() {
      if (!registered) {
         synchronized (HumanFormRaceChangeHandler.class) {
            if (!registered) {
               RaceEvents.SET_RACE.register((SetRaceEvent)(oldInstance, owner, newInstance, evolution, teleport, message) -> {
                  if (owner instanceof ServerPlayer player) {
                     SlimeHumanFormState.onRaceChanged(player, newInstance);
                  }

                  return EventResult.pass();
               });
               registered = true;
            }
         }
      }
   }
}
