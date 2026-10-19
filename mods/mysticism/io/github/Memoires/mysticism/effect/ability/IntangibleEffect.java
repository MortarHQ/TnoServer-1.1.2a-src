package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class IntangibleEffect extends TensuraMobEffect {
   protected static final UniqueMysticismSkillConfig.Phaser CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Phaser;

   public IntangibleEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(0, 0, 0, 10).getRGB());
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)UniqueMysticismSkills.PHASER.get());
      if (skill.isPresent()) {
         ManasSkillInstance skillInstance = skill.get();
         if (!skillInstance.getOrCreateTag().getBoolean("IntangibilityActive")) {
            skillInstance.addMasteryPoint(entity);
            skillInstance.getOrCreateTag().remove("PassiveRunning");
            skillInstance.setCoolDown(CONFIG.intangibilityPassiveCooldown, 2);
         } else {
            skillInstance.getOrCreateTag().remove("IntangibilityActive");
            skillInstance.setCoolDown(CONFIG.intangibilityActiveCooldown, 2);
         }
      }
   }
}
