package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.unique.VoidArchiveForgeData;
import com.github.wal_bos.moreskills.ability.skill.unique.VoidArchiveSkill;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import dev.architectury.event.events.common.TickEvent.ServerLevelTick;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;

public final class VoidArchiveSkillHandler {
   private static final float BASE_RECORD_KILL_CHANCE = 0.05F;
   private static final float MAX_RECORD_KILL_CHANCE = 0.12F;

   private VoidArchiveSkillHandler() {
   }

   public static void init() {
      EntityEvents.DEATH_EVENT_LAST.register((LivingDeath)(entity, source) -> {
         if (source.getEntity() instanceof LivingEntity attacker && entity != attacker && !entity.level().isClientSide() && !entity.isAlive()) {
            Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(attacker).getSkill(VoidArchiveSkill.SKILL_ID);
            if (optional.isPresent() && optional.get().getSkill() instanceof VoidArchiveSkill skill) {
               ManasSkillInstance instance = optional.get();
               float masteryBonus = (float)Math.min(0.07, instance.getMastery() / 20000.0);
               float chance = Math.min(0.12F, 0.05F + masteryBonus);
               if (attacker.getRandom().nextFloat() < chance) {
                  skill.recordKilledEntity(instance, entity);
                  instance.markDirty();
               }
            }

            return EventResult.pass();
         } else {
            return EventResult.pass();
         }
      });
      TickEvent.SERVER_LEVEL_POST.register((ServerLevelTick)level -> VoidArchiveForgeData.tickDeployedTraps(level));
   }
}
