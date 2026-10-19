package com.github.wal_bos.moreskills.handler.Istaroth;

import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;

public final class IstarothDeathHandler {
   private static boolean initialized;

   private IstarothDeathHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         EntityEvents.DEATH_EVENT_FIRST.register((LivingDeath)(entity, source) -> {
            if (entity instanceof LivingEntity living) {
               Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(living).getSkill((ManasSkill)ShadeSkills.ISTAROTH.get());
               if (optional.isEmpty()) {
                  return EventResult.pass();
               }

               ManasSkillInstance instance = optional.get();
               if (instance.isToggled() && instance.isMastered(living)) {
                  boolean denied = IstarothTimeHandler.shouldChronologyDeny(living, source, Float.MAX_VALUE);
                  if (denied) {
                     living.setHealth(Math.max(1.0F, living.getHealth()));
                     return EventResult.interruptFalse();
                  }
               }

               IstarothTimeHandler.clearOwner(living);
               return EventResult.pass();
            } else {
               return EventResult.pass();
            }
         });
      }
   }
}
