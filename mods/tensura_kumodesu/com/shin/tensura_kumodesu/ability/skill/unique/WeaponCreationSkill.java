package com.shin.tensura_kumodesu.ability.skill.unique;

import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.resources.ResourceLocation;

public class WeaponCreationSkill extends Skill {
   public WeaponCreationSkill() {
      super(SkillType.UNIQUE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/skill/unique/example_unique.png");
   }
}
