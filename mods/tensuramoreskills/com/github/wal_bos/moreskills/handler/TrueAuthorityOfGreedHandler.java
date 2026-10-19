package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.ultimate.TrueAuthorityOfGreedSkill;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import java.util.Optional;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public final class TrueAuthorityOfGreedHandler {
   private TrueAuthorityOfGreedHandler() {
   }

   public static void init() {
      SkillEvents.ACTIVATE_SKILL.register((SkillActivationEvent)(changeable, livingEntity, keyNumber, mode) -> {
         if (livingEntity.level().isClientSide()) {
            return EventResult.pass();
         }

         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(livingEntity).getSkill((ManasSkill)AllSkills.TRUE_AUTHORITY_OF_GREED.get());
         if (optional.isPresent() && TrueAuthorityOfGreedSkill.hasLockedState(optional.get())) {
            TrueAuthorityOfGreedSkill.restoreLockedState(optional.get(), livingEntity);
         }

         return EventResult.pass();
      });
      EntityEvents.LIVING_PRE_DAMAGED.register(TrueAuthorityOfGreedHandler::cancelIncomingHit);
      EntityEvents.LIVING_ON_BEING_DAMAGED.register(TrueAuthorityOfGreedHandler::cancelIncomingHit);
      TensuraEntityEvents.SPIRITUAL_HURT_EVENT.register(TrueAuthorityOfGreedHandler::reduceSpiritualDamage);
      EntityEvents.DEATH_EVENT_FIRST.register(TrueAuthorityOfGreedHandler::onDeath);
   }

   private static EventResult cancelIncomingHit(LivingEntity entity, DamageSource source, float amount) {
      if (entity.level().isClientSide()) {
         return EventResult.pass();
      }

      if (source.getEntity() != entity && source.getDirectEntity() != entity) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)AllSkills.TRUE_AUTHORITY_OF_GREED.get());
         if (optional.isEmpty()) {
            return EventResult.pass();
         }

         ManasSkillInstance instance = optional.get();
         if (!TrueAuthorityOfGreedSkill.shouldCancelIncomingHit(instance)) {
            return EventResult.pass();
         }

         TrueAuthorityOfGreedSkill.restoreLockedState(instance, entity);
         entity.invulnerableTime = 0;
         return EventResult.interruptFalse();
      } else {
         return EventResult.pass();
      }
   }

   private static EventResult reduceSpiritualDamage(
      LivingEntity target, Entity attacker, float originalAmount, Changeable<Float> resistPercentage, Changeable<Float> amount, Changeable<DamageSource> source
   ) {
      if (target.level().isClientSide()) {
         return EventResult.pass();
      }

      if (attacker == target) {
         return EventResult.pass();
      }

      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(target).getSkill((ManasSkill)AllSkills.TRUE_AUTHORITY_OF_GREED.get());
      if (optional.isEmpty()) {
         return EventResult.pass();
      }

      ManasSkillInstance instance = optional.get();
      float multiplier = TrueAuthorityOfGreedSkill.getSpiritualDamageMultiplier(instance);
      if (multiplier >= 1.0F) {
         return EventResult.pass();
      }

      amount.set((Float)amount.get() * multiplier);
      TrueAuthorityOfGreedSkill.restoreLockedState(instance, target);
      return EventResult.pass();
   }

   private static EventResult onDeath(LivingEntity entity, DamageSource source) {
      if (entity.level().isClientSide()) {
         return EventResult.pass();
      }

      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)AllSkills.TRUE_AUTHORITY_OF_GREED.get());
      if (optional.isPresent() && optional.get().getSkill() instanceof TrueAuthorityOfGreedSkill skill) {
         skill.cleanupOnDeath(optional.get(), entity, source);
      }

      return EventResult.pass();
   }
}
