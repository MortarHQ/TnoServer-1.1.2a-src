package io.github.Memoires.mysticism.util;

import io.github.Memoires.mysticism.ability.skill.unique.KyuremSkill;
import io.github.Memoires.mysticism.ability.skill.unique.ReducerSkill;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.world.entity.LivingEntity;

public class MysticismEnergyCostHelper {
   public static double modifyAllEnergyCost(LivingEntity entity, double original) {
      return modifyMpEnergyCost(entity, modifyApEnergyCost(entity, original));
   }

   public static double modifyMpEnergyCost(LivingEntity entity, double original) {
      if (!entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.STILLNESS))
         && !entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_ENERGY))
         && !entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.REVENANT_HORROR))) {
         double multiplier = 1.0;
         if (TensuraStorages.getAbilityFrom(entity).isAbilityInActivePreset((ManasSkill)UniqueMysticismSkills.SPIRITUALIST.get())) {
            multiplier *= 0.5;
         }

         if (SkillUtils.isSkillToggled(entity, (ManasSkill)UniqueMysticismSkills.REDUCER.get())) {
            multiplier *= SkillUtils.isSkillMastered(entity, (ManasSkill)UniqueMysticismSkills.REDUCER.get())
               ? ReducerSkill.CONFIG.purificationReductionMastered
               : ReducerSkill.CONFIG.purificationReduction;
         }

         if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.PRESSURE))) {
            multiplier *= KyuremSkill.CONFIG.pressureCostMultiplier;
         }

         return original * multiplier;
      } else {
         return 0.0;
      }
   }

   public static double modifyApEnergyCost(LivingEntity entity, double original) {
      if (!entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.STILLNESS))
         && !entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_ENERGY))
         && !entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.AWAKENED_FORESIGHT))) {
         double multiplier = 1.0;
         if (TensuraStorages.getAbilityFrom(entity).isAbilityInActivePreset((ManasSkill)UniqueMysticismSkills.SPIRITUALIST.get())) {
            multiplier *= 0.5;
         }

         if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.PRESSURE))) {
            multiplier *= KyuremSkill.CONFIG.pressureCostMultiplier;
         }

         return original * multiplier;
      } else {
         return 0.0;
      }
   }
}
