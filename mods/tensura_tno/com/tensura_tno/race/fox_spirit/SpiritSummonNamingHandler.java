package com.tensura_tno.race.fox_spirit;

import dev.architectury.event.EventResult;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.NamingEvent;
import io.github.manasmods.tensura.network.c2s.RequestNamingMenuPacket.NamingType;

public final class SpiritSummonNamingHandler {
   private static boolean registered;

   private SpiritSummonNamingHandler() {
   }

   public static synchronized void register() {
      if (!registered) {
         TensuraEntityEvents.NAMING_EVENT.register((NamingEvent)(entity, namer, epGain, cost, namingType, name) -> {
            if (namer != null && SpiritSummonEntityHelper.isSpiritSummonOf(entity, namer)) {
               epGain.set(0.0);
               cost.set(0.0);
               namingType.set(NamingType.LOW);
               return EventResult.pass();
            } else {
               return EventResult.pass();
            }
         });
         registered = true;
      }
   }
}
