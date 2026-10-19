package io.github.Memoires.mysticism.ability.skill.extra;

import io.github.Memoires.mysticism.config.ability.skill.ExtraMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.projectile.LightArrowProjectile;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class LightDominationSkill extends Skill {
   public static final ExtraMysticismSkillConfig.LightManipulation CONFIG = ((ExtraMysticismSkillConfig)ConfigRegistry.getConfig(
         ExtraMysticismSkillConfig.class
      ))
      .LightManipulation;

   public LightDominationSkill() {
      super(SkillType.EXTRA);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/extra/light_domination.png");
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return !SkillUtils.isSkillMastered(entity, (ManasSkill)ExtraMysticismSkills.LIGHT_MANIPULATION.get())
         ? false
         : newEP > LightManipulationSkill.CONFIG.dominationEpAcquirement;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.LIGHT_BOOST, CONFIG.dominationBoost);
      if (instance.isMastered(entity)) {
         AttributeHelper.applyDominationDegradation(
            entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, LightManipulationSkill.CONFIG.resistDegradationAcquirement
         );
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.LIGHT_BOOST, CONFIG.dominationBoost);
      AttributeHelper.removeDominationDegradation(entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0 && !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
         double distance = CONFIG.range;
         Entity target = ObjectSelectionHelper.getTargetingEntity(entity, distance, false, true);
         Vec3 pos;
         if (target != null) {
            pos = target.getEyePosition();
         } else {
            BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(entity.level(), entity, Fluid.NONE, distance);
            pos = result.getLocation().add(0.0, 0.5, 0.0);
         }

         if (instance.isMastered(entity)) {
            this.spawnLightArrows(instance, entity, pos, CONFIG.arrowNumber, 2.0, mode);
            this.spawnLightArrows(instance, entity, pos, CONFIG.arrowNumberMastered - CONFIG.arrowNumber, 3.0, mode);
         } else {
            this.spawnLightArrows(instance, entity, pos, CONFIG.arrowNumber, 2.5, mode);
         }

         instance.setCoolDown(3, 0);
      }
   }

   private void spawnLightArrows(ManasSkillInstance instance, LivingEntity entity, Vec3 pos, int arrowAmount, double distance, int mode) {
      int arrowRot = 360 / arrowAmount;

      for (int i = 0; i < arrowAmount; i++) {
         Vec3 arrowPos = entity.getEyePosition()
            .add(
               new Vec3(0.0, distance, 0.0)
                  .zRot((arrowRot * i - arrowRot / 2.0F) * (float) (Math.PI / 180.0))
                  .xRot(-entity.getXRot() * (float) (Math.PI / 180.0))
                  .yRot(-entity.getYRot() * (float) (Math.PI / 180.0))
            );
         LightArrowProjectile arrow = new LightArrowProjectile(entity.level(), entity);
         arrow.setSpeed(2.0F);
         arrow.setPos(arrowPos);
         arrow.shootFromRot(pos.subtract(arrowPos).normalize());
         arrow.setLife(50);
         arrow.setDamage(CONFIG.damage);
         arrow.setElementalAttack(true);
         arrow.setSkill(entity, instance, this, mode);
         entity.level().addFreshEntity(arrow);
         entity.level()
            .playSound(null, arrow.getX(), arrow.getY(), arrow.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHT.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      }
   }
}
