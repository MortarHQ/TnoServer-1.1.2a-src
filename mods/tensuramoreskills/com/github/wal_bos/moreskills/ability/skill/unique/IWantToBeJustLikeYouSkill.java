package com.github.wal_bos.moreskills.ability.skill.unique;

import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class IWantToBeJustLikeYouSkill extends Skill {
   public IWantToBeJustLikeYouSkill() {
      super(SkillType.UNIQUE);
   }

   public int getMaxMastery() {
      return SKILL_CONFIG.Mastery.masteryUnique;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 0.0;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/i_want_to_be_just_like_you.png");
   }
}
