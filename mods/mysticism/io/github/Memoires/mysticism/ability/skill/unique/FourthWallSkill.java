package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class FourthWallSkill extends Skill {
   protected static final ResourceLocation FOURTH_WALL = ResourceLocation.fromNamespaceAndPath("mysticism", "fourth_wall");

   public FourthWallSkill() {
      super(SkillType.UNIQUE);
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance learningSpeed = entity.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN);
      if (learningSpeed != null) {
         learningSpeed.addOrReplacePermanentModifier(new AttributeModifier(FOURTH_WALL, 4.0, Operation.ADD_VALUE));
      }

      AttributeInstance masterySpeed = entity.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN);
      if (masterySpeed != null) {
         masterySpeed.addOrReplacePermanentModifier(new AttributeModifier(FOURTH_WALL, 4.0, Operation.ADD_VALUE));
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance learningSpeed = entity.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN);
      if (learningSpeed != null) {
         learningSpeed.removeModifier(FOURTH_WALL);
      }

      AttributeInstance masterySpeed = entity.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN);
      if (masterySpeed != null) {
         masterySpeed.removeModifier(FOURTH_WALL);
      }
   }
}
