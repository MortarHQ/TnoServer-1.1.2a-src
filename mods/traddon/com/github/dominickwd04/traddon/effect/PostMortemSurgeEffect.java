package com.github.dominickwd04.traddon.effect;

import com.github.dominickwd04.traddon.ability.skill.unique.DevastatorSkill;
import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class PostMortemSurgeEffect extends TensuraMobEffect {
   protected static final ResourceLocation POST_MORTEM_SURGE = ResourceLocation.fromNamespaceAndPath("traddon", "post_mortem_surge");

   public PostMortemSurgeEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 0, 0).getRGB());
      TrAddonSkillConfig.Devastator CONFIG = DevastatorSkill.CONFIG;
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, POST_MORTEM_SURGE, CONFIG.surgeAttack, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, POST_MORTEM_SURGE, CONFIG.surgeAttackSpeed, Operation.ADD_MULTIPLIED_TOTAL);
   }
}
