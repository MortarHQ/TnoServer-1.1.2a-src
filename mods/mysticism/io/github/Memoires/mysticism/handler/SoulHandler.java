package io.github.Memoires.mysticism.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.config.race.RaceConfig;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SoulHandler {
   public static void init() {
      EntityEvents.DEATH_EVENT_NORMAL.register((LivingDeath)(entity, source) -> {
         if (entity.isAlive()) {
            return EventResult.pass();
         }

         if (entity.getType().is(TensuraEntityTags.EP_DROP_EXCLUDED)) {
            return EventResult.pass();
         }

         if (entity.getType().is(TensuraEntityTags.SOUL_DROP_EXCLUDED)) {
            return EventResult.pass();
         }

         Entity rawAttacker = source.getEntity();
         if (rawAttacker instanceof CloneEntity clone) {
            rawAttacker = clone.getOwner();
         }

         if (rawAttacker instanceof LivingEntity attacker) {
            if (rawAttacker == entity) {
               return EventResult.pass();
            }

            double epBase = EnergyHelper.getEPGain(entity, attacker, false);
            if (epBase <= 0.0) {
               return EventResult.pass();
            }

            if (TensuraStorages.getExistenceFrom(entity).isSkippingEPDrop()) {
               return EventResult.pass();
            }

            LivingEntity recipient = SubordinateHelper.getSubordinateOwner(attacker);
            if (recipient == null) {
               recipient = attacker;
            }

            IExistence recExistence = TensuraStorages.getExistenceFrom(recipient);
            if (recExistence.isDemonLordSeed()) {
               return EventResult.pass();
            }

            boolean hasSpiritualist = SkillAPI.getSkillsFrom(recipient).getSkill((ManasSkill)UniqueMysticismSkills.SPIRITUALIST.get()).isPresent();
            if (!hasSpiritualist) {
               return EventResult.pass();
            }

            Level lvl = entity.level();
            double maxMP = lvl.getGameRules().getInt(TensuraGameRules.MAX_MP_GAIN);
            double maxAP = lvl.getGameRules().getInt(TensuraGameRules.MAX_AP_GAIN);
            double souls = EnergyHelper.getEPGain(entity, recipient, false) * ((RaceConfig)ConfigRegistry.getConfig(RaceConfig.class)).epToSoulRate / 100.0;
            souls = Math.min(souls, maxMP + maxAP);
            int newSouls = (int)Math.min(recExistence.getSoulPoints() + (long)souls, 2147483647L);
            recExistence.setSoulPoints(newSouls);
            if (lvl.getGameRules().getBoolean(TensuraGameRules.RIMURU_MODE) && newSouls >= 20000000) {
               SkillHelper.learnSkill(recipient, (ManasSkill)UniqueSkills.MERCILESS.get());
            }

            recExistence.markDirty();
            return EventResult.pass();
         } else {
            return EventResult.pass();
         }
      });
   }
}
