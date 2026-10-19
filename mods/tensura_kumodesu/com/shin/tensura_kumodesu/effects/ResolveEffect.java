package com.shin.tensura_kumodesu.effects;

import com.shin.tensura_kumodesu.ability.skill.unique.ResolveSkill;
import com.shin.tensura_kumodesu.config.skill.KumodesuUniqueSkillConfig;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class ResolveEffect extends TensuraMobEffect {
   protected static final ResourceLocation RESOLVE = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "resolve");

   public ResolveEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(44, 148, 99, 255).getRGB());
      KumodesuUniqueSkillConfig.Resolve CONFIG = ResolveSkill.CONFIG;
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, RESOLVE, CONFIG.bonusAttack, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, RESOLVE, CONFIG.bonusAttackSpeed, Operation.ADD_VALUE);
   }
}
