package io.github.Memoires.mysticism.ability.skill.extra;

import io.github.Memoires.mysticism.config.ability.skill.ExtraMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
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
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class IceDominationSkill extends Skill {
   public static final ExtraMysticismSkillConfig.IceManipulation CONFIG = ((ExtraMysticismSkillConfig)ConfigRegistry.getConfig(ExtraMysticismSkillConfig.class))
      .IceManipulation;

   public IceDominationSkill() {
      super(SkillType.EXTRA);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/extra/ice_domination.png");
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return !SkillUtils.isSkillMastered(entity, (ManasSkill)ExtraMysticismSkills.ICE_MANIPULATION.get())
         ? false
         : newEP > IceManipulationSkill.CONFIG.dominationEpAcquirement;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.multiplyElementalBoost(entity, MysticismAttributes.COLD_BOOST, CONFIG.dominationBoost);
      if (instance.isMastered(entity)) {
         AttributeHelper.applyDominationDegradation(
            entity, MysticismAttributes.COLD_RESIST_DEGRADATION, IceManipulationSkill.CONFIG.resistDegradationAcquirement
         );
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeElementalMultiplier(entity, MysticismAttributes.COLD_BOOST, CONFIG.dominationBoost);
      AttributeHelper.removeDominationDegradation(entity, MysticismAttributes.COLD_RESIST_DEGRADATION);
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
}
