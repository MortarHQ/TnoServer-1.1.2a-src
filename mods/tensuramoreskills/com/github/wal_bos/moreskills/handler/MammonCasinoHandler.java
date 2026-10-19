package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.ultimate.MammonCasinoUltimateSkill;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public final class MammonCasinoHandler {
   private MammonCasinoHandler() {
   }

   public static void init() {
      EntityEvents.DEATH_EVENT_LAST.register((LivingDeath)(entity, source) -> {
         if (entity.level().isClientSide()) {
            return EventResult.pass();
         }

         if (entity instanceof LivingEntity living) {
            if (source.getEntity() instanceof ServerPlayer player) {
               if (!entity.isDeadOrDying()) {
                  return EventResult.pass();
               }

               if (!MammonCasinoUltimateSkill.hasSkill(player)) {
                  return EventResult.pass();
               }

               if (living == player) {
                  return EventResult.pass();
               }

               MammonCasinoUltimateSkill.addTrackedKill(player, living);
               return EventResult.pass();
            } else {
               return EventResult.pass();
            }
         } else {
            return EventResult.pass();
         }
      });
   }
}
