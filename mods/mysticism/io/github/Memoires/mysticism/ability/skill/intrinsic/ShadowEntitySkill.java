package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.world.entity.LivingEntity;

public class ShadowEntitySkill extends Skill {
   public ShadowEntitySkill() {
      super(SkillType.INTRINSIC);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity living) {
      super.onTick(instance, living);
      long time = living.level().getDayTime() % 24000L;
      if (time >= 13000L && time <= 23000L) {
         float maxHealth = living.getMaxHealth();
         float healAmount = maxHealth * 0.02F;
         living.heal(healAmount);
      }
   }
}
