package com.tensura_tno.compat.stextras;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.resources.ResourceLocation;

public final class SkillLockEligibility {
   private SkillLockEligibility() {
   }

   public static boolean isResistanceSkill(ResourceLocation skillId) {
      return getRegisteredSkill(skillId) instanceof Skill tensuraSkill && tensuraSkill.getType() == SkillType.RESISTANCE;
   }

   public static boolean isUltimateSkill(ResourceLocation skillId) {
      return getRegisteredSkill(skillId) instanceof Skill tensuraSkill && tensuraSkill.getType() == SkillType.ULTIMATE;
   }

   private static ManasSkill getRegisteredSkill(ResourceLocation skillId) {
      if (skillId == null) {
         return null;
      }

      try {
         return (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
      } catch (Throwable ignored) {
         return null;
      }
   }
}
