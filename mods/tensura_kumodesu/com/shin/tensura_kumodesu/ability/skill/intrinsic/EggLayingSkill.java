package com.shin.tensura_kumodesu.ability.skill.intrinsic;

import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.resources.ResourceLocation;

public class EggLayingSkill extends Skill {
   public EggLayingSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/skill/intrinsic/egg_laying.png");
   }
}
