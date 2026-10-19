package com.shin.tensura_kumodesu.effects;

import com.shin.tensura_kumodesu.ability.skill.unique.PerseveranceSkill;
import com.shin.tensura_kumodesu.config.skill.KumodesuUniqueSkillConfig;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class PerseveranceEffect extends TensuraMobEffect {
   protected static final ResourceLocation PERSEVERANCE = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "perseverance");

   public PerseveranceEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(44, 148, 99, 255).getRGB());
      KumodesuUniqueSkillConfig.Perseverance CONFIG = PerseveranceSkill.CONFIG;
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, PERSEVERANCE, CONFIG.bonusAttack, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, PERSEVERANCE, CONFIG.bonusAttackSpeed, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ARMOR, PERSEVERANCE, CONFIG.bonusArmor, Operation.ADD_VALUE);
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
      return pDuration % 2 == 0;
   }
}
