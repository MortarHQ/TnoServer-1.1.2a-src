package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.unique.AuthorityOfGreedSkill;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import java.util.Optional;

public final class AuthorityOfGreedHandler {
   private AuthorityOfGreedHandler() {
   }

   public static void init() {
      SkillEvents.ACTIVATE_SKILL.register((SkillActivationEvent)(changeable, livingEntity, keyNumber, mode) -> {
         if (livingEntity.level().isClientSide()) {
            return EventResult.pass();
         }

         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(livingEntity).getSkill((ManasSkill)AllSkills.AUTHORITY_OF_GREED.get());
         if (optional.isEmpty()) {
            return EventResult.pass();
         }

         ManasSkillInstance instance = optional.get();
         if (!AuthorityOfGreedSkill.hasLockedState(instance)) {
            return EventResult.pass();
         }

         AuthorityOfGreedSkill.restoreLockedState(instance, livingEntity);
         return EventResult.pass();
      });
   }
}
