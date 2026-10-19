package com.shin.tensura_kumodesu.effects;

import com.shin.tensura_kumodesu.ability.skill.unique.DeterminationSkill;
import com.shin.tensura_kumodesu.config.skill.KumodesuUniqueSkillConfig;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class DeterminationEffect extends TensuraMobEffect {
   protected static final ResourceLocation DETERMINATION = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "determination");

   public DeterminationEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(44, 148, 99, 255).getRGB());
      KumodesuUniqueSkillConfig.Determination CONFIG = DeterminationSkill.CONFIG;
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, DETERMINATION, CONFIG.bonusAttack, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, DETERMINATION, CONFIG.bonusAttackSpeed, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ARMOR, DETERMINATION, CONFIG.bonusArmor, Operation.ADD_VALUE);
   }
}
