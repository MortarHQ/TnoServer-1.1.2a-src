package com.foxablazeultimate.ability.skill.shubniggurath;

import com.foxablazeultimate.event.UltimateSkillProtector;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.RemoveSkillEvent;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public final class ShubNiggurathArchiveHandler {
   private ShubNiggurathArchiveHandler() {
   }

   public static void init() {
      SkillEvents.REMOVE_SKILL
         .register(
            (RemoveSkillEvent)(removed, owner, forgetMessage) -> {
               if (removed != null
                  && owner != null
                  && removed.getMastery() >= 0.0
                  && !removed.isTemporarySkill()
                  && !FoxAblazeUltimateSkills.CIEL.getId().equals(removed.getSkillId())
                  && !UltimateSkillProtector.isEvolutionRemoval(owner, removed.getSkillId())
                  && !UltimateSkillProtector.isInactivityRemoval(owner, removed.getSkillId())) {
                  archive(owner, removed);
               }

               return EventResult.pass();
            }
         );
   }

   private static void archive(LivingEntity owner, ManasSkillInstance information) {
      Optional<ManasSkillInstance> shub = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get());
      ResourceLocation id = information.getSkillId();
      if (shub.isPresent() && id != null && shub.get() != information) {
         ShubNiggurathWarehouse.archiveSkills(shub.get(), List.of(id));
      }
   }
}
