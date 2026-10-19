package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;

public class RelapseHandler {
   public static void init() {
      EntityEvents.DEATH_EVENT_NORMAL.register((LivingDeath)(entity, source) -> {
         if (entity.isAlive()) {
            return EventResult.pass();
         }

         if (source.getEntity() instanceof Player player && !entity.isAlliedTo(player)) {
            Optional<ManasSkillInstance> manasSkillInstance = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)IntrinsicMysticismSkills.RELAPSE.get());
            manasSkillInstance.ifPresent(instance -> instance.getOrCreateTag().putInt("Kills", instance.getOrCreateTag().getInt("Kills") + 1));
         }

         return EventResult.pass();
      });
   }
}
