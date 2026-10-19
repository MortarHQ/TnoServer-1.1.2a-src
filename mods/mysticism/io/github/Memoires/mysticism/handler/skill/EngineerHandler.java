package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.Memoires.mysticism.entity.skill.SentryEntity;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.handler.DeathHandler;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.world.entity.LivingEntity;

public class EngineerHandler {
   public static void init() {
      EntityEvents.DEATH_EVENT_NORMAL.register((LivingDeath)(entity, source) -> {
         if (entity.isAlive()) {
            return EventResult.pass();
         }

         if (source.getEntity() instanceof SentryEntity sentry && sentry.getOwner() != null) {
            LivingEntity owner = sentry.getOwner();
            if (entity.getType().is(TensuraEntityTags.EP_DROP_EXCLUDED)) {
               return EventResult.pass();
            }

            double EP = EnergyHelper.getEPGain(entity, owner, false);
            if (EP <= 0.0) {
               return EventResult.pass();
            }

            if (TensuraStorages.getExistenceFrom(entity).isSkippingEPDrop()) {
               return EventResult.pass();
            }

            float multiplier = entity.level().getGameRules().getInt(TensuraGameRules.EP_GAIN_MULTIPLIER);
            double epGain = EnergyHelper.getEPGain(entity, owner, true);
            DeathHandler.gainEPEntity(owner, entity, epGain * multiplier);
            return EventResult.pass();
         } else {
            return EventResult.pass();
         }
      });
   }
}
