package io.github.manasmods.tensura_neb.handler;

import dev.architectury.event.EventResult;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.EnergyDrainEvent;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import io.github.manasmods.tensura_neb.registry.NebEntityTypes;

public class NebHandler {
   public static void init() {
      TensuraEntityEvents.ENERGY_DRAIN_EVENT.register((EnergyDrainEvent)(target, drainer, drainType, gainType, amount, percentage) -> {
         if (gainType.get() == GainType.NORMAL_EXCEED_MAX && drainer != null && drainer.getType().equals(NebEntityTypes.LUMINOUS_VALENTINE.get())) {
            gainType.set(GainType.NORMAL);
         }

         return EventResult.pass();
      });
   }
}
