package io.github.Memoires.mysticism.ability.skill.extra;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.config.ability.skill.ExtraMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.magic.beam.BeamProjectile;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class DarknessDominationSkill extends Skill {
   public static final ExtraMysticismSkillConfig.DarknessManipulation CONFIG = ((ExtraMysticismSkillConfig)ConfigRegistry.getConfig(
         ExtraMysticismSkillConfig.class
      ))
      .DarknessManipulation;

   public DarknessDominationSkill() {
      super(SkillType.EXTRA);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/extra/darkness_domination.png");
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return !SkillUtils.isSkillMastered(entity, (ManasSkill)ExtraMysticismSkills.DARKNESS_MANIPULATION.get())
         ? false
         : newEP > DarknessManipulationSkill.CONFIG.dominationEpAcquirement;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.DARKNESS_BOOST, CONFIG.dominationBoost);
      if (instance.isMastered(entity)) {
         AttributeHelper.applyDominationDegradation(
            entity, TensuraAttributes.DARKNESS_RESIST_DEGRADATION, DarknessManipulationSkill.CONFIG.resistDegradationAcquirement
         );
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.DARKNESS_BOOST, CONFIG.dominationBoost);
      AttributeHelper.removeDominationDegradation(entity, TensuraAttributes.DARKNESS_RESIST_DEGRADATION);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      instance.getOrCreateTag().putInt("BeamID", 0);
      instance.markDirty();
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0) {
         instance.addMasteryPoint(entity);
      }

      Pair<Double, Double> cost = Pair.of(this.getAuraCost(entity, instance, mode), this.getMagiculeCost(entity, instance, mode));
      BeamProjectile.spawnLastingBeam(
         (EntityType)MiscEntityTypes.DARKNESS_CANNON.get(),
         CONFIG.damage,
         1.0F,
         21,
         (float)CONFIG.range,
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
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_DARK.get(), TensuraSkill.ABILITY_SOUND, 0.8F, 0.5F);
      return true;
   }
}
