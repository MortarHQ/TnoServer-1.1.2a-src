package io.github.Memoires.mysticism.effect.ability.constant;

import io.github.Memoires.mysticism.ability.skill.unique.ConstantSkill;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ConstantEnergyEffect extends TensuraMobEffect {
   public ConstantEnergyEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(0, 56, 56).getRGB());
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      super.onEffectRemoved(entity, instance);
      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      if (SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.CONSTANT.get())) {
         ManasSkillInstance constant = (ManasSkillInstance)SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)UniqueMysticismSkills.CONSTANT.get()).get();
         double constantEnergyMPCost = constant.getOrCreateTag().getDouble("ConstantEnergyMPCost");
         double constantEnergyAPCost = constant.getOrCreateTag().getDouble("ConstantEnergyAPCost");
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         Holder<MobEffect> effect = TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_ENERGY_DEBUFF);
         if (existence.getMagicule() - constantEnergyMPCost <= 0.0) {
            double maxMagicule = entity.getAttributeValue(TensuraAttributes.MAX_MAGICULE);
            constantEnergyMPCost = maxMagicule * 0.25;
            TensuraMobEffect.addEffect(
               entity,
               new MobEffectInstance(effect, ConstantSkill.CONFIG.constantEnergyDebuffDuration * 20, 0, false, false, false),
               entity,
               constant.getSkill()
            );
         } else {
            constantEnergyMPCost = existence.getMagicule() - constantEnergyMPCost;
         }

         if (existence.getAura() - constantEnergyAPCost <= 0.0) {
            double maxAura = entity.getAttributeValue(TensuraAttributes.MAX_AURA);
            constantEnergyAPCost = maxAura * 0.25;
            TensuraMobEffect.addEffect(
               entity,
               new MobEffectInstance(effect, ConstantSkill.CONFIG.constantEnergyDebuffDuration * 20, 0, false, false, false),
               entity,
               constant.getSkill()
            );
         } else {
            constantEnergyAPCost = existence.getAura() - constantEnergyAPCost;
         }

         existence.setMagicule(constantEnergyMPCost);
         existence.setAura(constantEnergyAPCost);
         constant.getOrCreateTag().putDouble("ConstantEnergyMPCost", 0.0);
         constant.getOrCreateTag().putDouble("ConstantEnergyAPCost", 0.0);
         constant.markDirty();
         existence.markDirty();
      }
   }
}
