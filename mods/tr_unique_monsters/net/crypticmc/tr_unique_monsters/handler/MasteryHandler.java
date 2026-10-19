package net.crypticmc.tr_unique_monsters.handler;

import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillMasteryEvent;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.skill.common.FarsightSkill;
import io.github.manasmods.tensura.ability.skill.extra.AnalyticalAppraisalSkill;
import net.crypticmc.tr_unique_monsters.ability.skill.extra.AppraisalEyeSkill;
import net.crypticmc.tr_unique_monsters.registry.skill.ExtraSkills;

public class MasteryHandler {
   public static void init() {
      masteryListeners();
   }

   private static void masteryListeners() {
      SkillEvents.SKILL_MASTERY.register((SkillMasteryEvent)(instance, owner, newMastery) -> {
         ManasSkill skill = instance.getSkill();
         if (!owner.level().isClientSide && (skill instanceof AnalyticalAppraisalSkill || skill instanceof FarsightSkill)) {
            SkillStorage skills = SkillAPI.getSkillsFrom(owner);
            boolean hasAnalytical = skills.getLearnedSkills().stream().anyMatch(s -> s.getSkill() instanceof AnalyticalAppraisalSkill);
            boolean hasFarsight = skills.getLearnedSkills().stream().anyMatch(s -> s.getSkill() instanceof FarsightSkill);
            boolean hasAppraisalEye = skills.getLearnedSkills().stream().anyMatch(s -> s.getSkill() instanceof AppraisalEyeSkill);
            if (hasAnalytical && hasFarsight && !hasAppraisalEye) {
               SkillHelper.learnSkill(owner, (ManasSkill)ExtraSkills.APPRAISAL_EYE.get());
            }
         }

         return EventResult.pass();
      });
   }
}
