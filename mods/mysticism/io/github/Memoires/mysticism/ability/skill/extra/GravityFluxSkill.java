package io.github.Memoires.mysticism.ability.skill.extra;

import io.github.Memoires.mysticism.config.ability.skill.ExtraMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class GravityFluxSkill extends Skill {
   protected static final ExtraMysticismSkillConfig.GravityFlux CONFIG = ((ExtraMysticismSkillConfig)ConfigRegistry.getConfig(ExtraMysticismSkillConfig.class)).GravityFlux;

   public GravityFluxSkill() {
      super(SkillType.EXTRA);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/extra/gravity_flux.png");
   }

   public int getModes() {
      return 2;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "gravity_flux.fluctuate";
         case 1 -> "gravity_flux.hamper";
         default -> super.getModeId(instance, mode);
      };
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         boolean mastered = instance.isMastered(entity);
         double range = mastered ? CONFIG.rangeMastered : CONFIG.range;
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, range, false, true);
         if (target != null) {
            if (mode == 0) {
               int gravDur = (int)(mastered ? CONFIG.gravityDurationMastered : CONFIG.gravityDuration);
               int gravAmp = Math.max(0, (mastered ? CONFIG.gravityLevelMastered : CONFIG.gravityLevel) - 1);
               int nauseaDur = (int)(mastered ? CONFIG.nauseaDurationMastered : CONFIG.nauseaDuration);
               int nauseaAmp = Math.max(0, (mastered ? CONFIG.nauseaLevelMastered : CONFIG.nauseaLevel) - 1);
               TensuraMobEffect.addEffect(
                  target,
                  new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.GRAVITY_FLUCTUATION), gravDur, gravAmp, false, true, false),
                  entity,
                  this,
                  mode
               );
               TensuraMobEffect.addEffect(target, new MobEffectInstance(MobEffects.CONFUSION, nauseaDur, nauseaAmp), entity, this, mode);
            } else {
               int burdenDur = (int)(mastered ? CONFIG.burdenDurationMastered : CONFIG.burdenDuration);
               int burdenAmp = Math.max(0, (mastered ? CONFIG.burdenLevelMastered : CONFIG.burdenLevel) - 1);
               TensuraMobEffect.addEffect(target, new MobEffectInstance(TensuraMobEffects.BURDEN, burdenDur, burdenAmp), entity, this, mode);
            }
         }
      }
   }
}
