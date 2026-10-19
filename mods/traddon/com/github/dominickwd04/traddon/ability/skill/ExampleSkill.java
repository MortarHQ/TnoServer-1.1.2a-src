package com.github.dominickwd04.traddon.ability.skill;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.world.entity.LivingEntity;

public class ExampleSkill extends Skill {
   public ExampleSkill() {
      super(SkillType.UNIQUE);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 0.0;
   }

   public int getModes(ManasSkillInstance instance) {
      return 1;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canBeSlotted(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return true;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.isToggled()) {
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
   }
}
