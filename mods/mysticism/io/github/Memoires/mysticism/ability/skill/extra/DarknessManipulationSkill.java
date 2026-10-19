package io.github.Memoires.mysticism.ability.skill.extra;

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
import io.github.manasmods.tensura.entity.magic.barrier.BarrierPart;
import io.github.manasmods.tensura.entity.magic.barrier.DarkCubeEntity;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
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

public class DarknessManipulationSkill extends Skill {
   public static final ExtraMysticismSkillConfig.DarknessManipulation CONFIG = ((ExtraMysticismSkillConfig)ConfigRegistry.getConfig(
         ExtraMysticismSkillConfig.class
      ))
      .DarknessManipulation;

   public DarknessManipulationSkill() {
      super(SkillType.EXTRA);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/extra/darkness_manipulation.png");
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return TensuraStorages.getSpiritFrom(entity).getSpiritLevelId(Element.DARKNESS) >= 1;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      if (!(EnergyHelper.getBaseMaxEP(entity) < CONFIG.dominationEpAcquirement)) {
         SkillHelper.learnSkill(entity, ((DarknessDominationSkill)ExtraMysticismSkills.DARKNESS_DOMINATION.get()).createLearningInstance(entity));
      }
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.DARKNESS_BOOST, CONFIG.manipulationBoost);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.DARKNESS_BOOST, CONFIG.manipulationBoost);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.isShiftKeyDown()) {
         double distance = CONFIG.range;
         BarrierPart part = (BarrierPart)ObjectSelectionHelper.getTargetingEntity(BarrierPart.class, entity, distance, 0.5, false, false, false);
         if (part != null && part.getBarrier() instanceof DarkCubeEntity darkCube && darkCube.getOwner() == entity) {
            darkCube.setLife(20);
            entity.level()
               .playSound(
                  null, part.getX(), part.getY(), part.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_UNCAST.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
               );
         }
      } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
         double distance = CONFIG.range;
         Entity target = ObjectSelectionHelper.getTargetingEntity(entity, distance, false, true);
         Vec3 pos;
         if (target != null) {
            pos = target.position();
         } else {
            BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(entity.level(), entity, Fluid.NONE, distance);
            pos = result.getLocation().add(0.0, 0.25, 0.0);
         }

         DarkCubeEntity cube = new DarkCubeEntity(entity.level(), entity);
         cube.setPos(pos);
         cube.setDamage(instance.isMastered(entity) ? CONFIG.cubeDamageMastered : CONFIG.cubeDamage);
         cube.setSkill(entity, instance, this, mode);
         cube.setLife(CONFIG.cubeDuration);
         cube.setSize(CONFIG.cubeRadius);
         cube.setVisualSize(cube.getSize());
         entity.level().addFreshEntity(cube);
         entity.level()
            .playSound(
               null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_DARK.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
            );
         instance.setCoolDown(instance.isMastered(entity) ? CONFIG.cooldownMastered : CONFIG.cooldown, mode);
         instance.getOrCreateTag().putInt("HeldTicks", 0);
         instance.markDirty();
      }
   }

   public static void learnDarknessManipulation(ManasSkillInstance instance, LivingEntity entity) {
      int skills = instance.is(MysticismSkillTags.DARKNESS_SKILLS) ? 1 : 0;

      for (ManasSkillInstance skill : SkillAPI.getSkillsFrom(entity).getLearnedSkills()) {
         if (!skill.isTemporarySkill() && skill.isMastered(entity) && skill.is(MysticismSkillTags.DARKNESS_SKILLS)) {
            skills++;
         }
      }

      if (!(skills < CONFIG.darknessSkillAcquirement)) {
         SkillHelper.learnSkill(entity, ((DarknessManipulationSkill)ExtraMysticismSkills.DARKNESS_MANIPULATION.get()).createLearningInstance(entity));
      }
   }
}
