package io.github.Memoires.mysticism.ability.skill.extra;

import io.github.Memoires.mysticism.config.ability.skill.ExtraMysticismSkillConfig;
import io.github.Memoires.mysticism.data.MysticismSkillTags;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.magic.breath.BreathEntity;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class IceManipulationSkill extends Skill {
   public static final ExtraMysticismSkillConfig.IceManipulation CONFIG = ((ExtraMysticismSkillConfig)ConfigRegistry.getConfig(ExtraMysticismSkillConfig.class))
      .IceManipulation;

   public IceManipulationSkill() {
      super(SkillType.EXTRA);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/extra/ice_manipulation.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      if (!(EnergyHelper.getBaseMaxEP(entity) < CONFIG.dominationEpAcquirement)) {
         SkillHelper.learnSkill(entity, ((IceDominationSkill)ExtraMysticismSkills.ICE_DOMINATION.get()).createLearningInstance(entity));
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.multiplyElementalBoost(entity, MysticismAttributes.COLD_BOOST, CONFIG.manipulationBoost);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeElementalMultiplier(entity, MysticismAttributes.COLD_BOOST, CONFIG.manipulationBoost);
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
         instance.addMasteryPoint(entity);
      }

      float damage = instance.isMastered(entity) ? CONFIG.damageMastered : CONFIG.damage;
      BreathEntity.spawnBreathEntity((EntityType)MiscEntityTypes.ICE_BREATH.get(), entity, instance, damage, this, mode);
      if (heldTicks % 2 == 0) {
         entity.level()
            .playSound(
               null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_ICE.get(), TensuraSkill.ABILITY_SOUND, 0.75F, 1.0F
            );
      }

      return true;
   }

   public static void learnIceManipulation(ManasSkillInstance instance, LivingEntity entity) {
      int skills = instance.is(MysticismSkillTags.ICE_SKILLS) ? 1 : 0;
      if (!SkillUtils.hasSkillPermanently(entity, (ManasSkill)ExtraMysticismSkills.ICE_MANIPULATION.get())) {
         for (ManasSkillInstance skill : SkillAPI.getSkillsFrom(entity).getLearnedSkills()) {
            if (!skill.isTemporarySkill() && skill.isMastered(entity) && skill.is(MysticismSkillTags.ICE_SKILLS)) {
               skills++;
            }
         }

         if (!(skills < CONFIG.iceSkillAcquirement)) {
            SkillHelper.learnSkill(entity, ((IceManipulationSkill)ExtraMysticismSkills.ICE_MANIPULATION.get()).createLearningInstance(entity));
         }
      }
   }
}
