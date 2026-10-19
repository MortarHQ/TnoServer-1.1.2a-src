package io.github.Memoires.mysticism.ability.skill.extra;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.config.ability.skill.ExtraMysticismSkillConfig;
import io.github.Memoires.mysticism.data.MysticismSkillTags;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.magic.beam.BeamProjectile;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class LightManipulationSkill extends Skill {
   public static final ExtraMysticismSkillConfig.LightManipulation CONFIG = ((ExtraMysticismSkillConfig)ConfigRegistry.getConfig(
         ExtraMysticismSkillConfig.class
      ))
      .LightManipulation;

   public LightManipulationSkill() {
      super(SkillType.EXTRA);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/extra/light_manipulation.png");
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return TensuraStorages.getSpiritFrom(entity).getSpiritLevelId(Element.LIGHT) >= 1;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      if (!(EnergyHelper.getBaseMaxEP(entity) < CONFIG.dominationEpAcquirement)) {
         SkillHelper.learnSkill(entity, ((LightDominationSkill)ExtraMysticismSkills.LIGHT_DOMINATION.get()).createLearningInstance(entity));
      }
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.LIGHT_BOOST, CONFIG.manipulationBoost);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.LIGHT_BOOST, CONFIG.manipulationBoost);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      instance.getOrCreateTag().putInt("BeamID", 0);
      instance.markDirty();
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
         instance.addMasteryPoint(entity);
      }

      double auraCost = this.getAuraCost(entity, instance, mode);
      double magiculeCost = this.getMagiculeCost(entity, instance, mode);
      Pair<Double, Double> cost = Pair.of(auraCost, magiculeCost);
      BeamProjectile.spawnLastingBeam(
         (EntityType)MiscEntityTypes.SOLAR_BEAM.get(),
         CONFIG.damage,
         1.0F,
         21,
         CONFIG.range,
         0.0F,
         entity.getEyePosition(),
         entity,
         instance,
         mode,
         cost,
         cost,
         heldTicks
      );
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHT.get(), TensuraSkill.ABILITY_SOUND, 0.8F, 0.5F);
      return true;
   }

   public static void learnLightManipulation(ManasSkillInstance instance, LivingEntity entity) {
      int skills = instance.is(MysticismSkillTags.LIGHT_SKILLS) ? 1 : 0;

      for (ManasSkillInstance skill : SkillAPI.getSkillsFrom(entity).getLearnedSkills()) {
         if (!skill.isTemporarySkill() && skill.isMastered(entity) && skill.is(MysticismSkillTags.LIGHT_SKILLS)) {
            skills++;
         }
      }

      if (!(skills < CONFIG.lightSkillAcquirement)) {
         SkillHelper.learnSkill(entity, ((LightManipulationSkill)ExtraMysticismSkills.LIGHT_MANIPULATION.get()).createLearningInstance(entity));
      }
   }
}
