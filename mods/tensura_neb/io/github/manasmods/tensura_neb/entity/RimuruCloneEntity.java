package io.github.manasmods.tensura_neb.entity;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.CustomRangeAttack;
import io.github.manasmods.tensura.entity.ai.behaviour.attack.InvalidateNeutralAttackTarget;
import io.github.manasmods.tensura.entity.ai.behaviour.movement.TeleportToEntity;
import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.magic.AspectualMagics;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;

public class RimuruCloneEntity extends RimuruOgreFightEntity {
   public RimuruCloneEntity(EntityType<? extends RimuruCloneEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.xpReward = 0;
   }

   public static Builder setAttributes() {
      return TensuraTamableEntity.setAttributes()
         .add(Attributes.ARMOR, 10.0)
         .add(Attributes.ATTACK_DAMAGE, 50.0)
         .add(Attributes.ATTACK_KNOCKBACK, 3.0)
         .add(Attributes.MAX_HEALTH, 500.0)
         .add(Attributes.MOVEMENT_SPEED, 0.3F)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
         .add(Attributes.STEP_HEIGHT, 2.0)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 2.0)
         .add(TensuraAttributes.PRESENCE_SENSE, 3.0)
         .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 10.0)
         .add(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, 5.0)
         .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 5.0);
   }

   public void copySkills(LivingEntity owner) {
      this.setCustomName(owner.getName());

      for (MobEffectInstance instance : owner.getActiveEffects()) {
         this.addEffect(instance);
      }

      for (ManasSkillInstance instance : List.copyOf(SkillAPI.getSkillsFrom(owner).getLearnedSkills())) {
         ManasSkillInstance skillInstance = TensuraSkillInstance.fromNBT(instance.toNBT());
         skillInstance.setToggled(true);
         skillInstance.onToggleOn(this);
         SkillHelper.learnSkill(this, skillInstance);
      }
   }

   @Override
   public BrainActivityGroup<RimuruOgreFightEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{
            new InvalidateNeutralAttackTarget().invalidateIf(this::shouldStopTarget),
            new TeleportToEntity()
               .following(Mob::getTarget)
               .canTeleportOffGroundWhen(entity -> true)
               .teleportRadius((entity, target) -> 10)
               .teleportToTargetAfter((entity, target) -> {
                  if (entity.tickCount % 200 == 0) {
                     return 10.0;
                  } else {
                     return entity.tickCount % 20 == 0 ? 20.0 : 32.0;
                  }
               })
               .onSuccessTeleport((entity, target) -> {
                  TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.SQUID_INK, 1.0);
                  entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
               }),
            new StrafeTarget().stopStrafingWhen(entity -> !entity.usingRangedWeapon()).startCondition(PlayerLikeEntity::usingRangedWeapon),
            new SetWalkTargetToAttackTarget().speedMod((owner, target) -> 2.0F).startCondition(entity -> !entity.usingRangedWeapon()),
            new FirstApplicableBehaviour(
                  new ExtendedBehaviour[]{
                     PlayerLikeEntity.getSpearAttack(20).attackInterval(entity -> 5).attackRadius(25.0F),
                     PlayerLikeEntity.getCrossbowAttack().attackInterval(entity -> 10).attackRadius(30.0F),
                     PlayerLikeEntity.getBowAttack().attackInterval(entity -> 10).attackRadius(25.0F),
                     new CustomRangeAttack(5)
                        .maxAttackRadius(32.0F)
                        .attackInterval(entity -> 20)
                        .performAttack((entity, target) -> entity.shootIcicle(target, entity.getRandom().nextInt(7, 10)))
                        .startCondition(
                           entity -> entity.getRandom().nextFloat() <= 0.2 && entity.getSkill((ManasSkill)AspectualMagics.ICICLE_LANCE.get()) != null
                        ),
                     new CustomRangeAttack(5)
                        .maxAttackRadius(32.0F)
                        .attackInterval(entity -> 10)
                        .performAttack(RimuruOgreFightEntity::shootWaterBlade)
                        .startCondition(entity -> entity.getRandom().nextFloat() <= 0.4 && entity.getSkill((ManasSkill)CommonSkills.WATER_BLADE.get()) != null),
                     new CustomRangeAttack(5)
                        .maxAttackRadius(64.0F)
                        .attackInterval(entity -> 10)
                        .performAttack((entity, target) -> entity.coercion(entity.getEyePosition().add(entity.getLookAngle().scale(15.0))))
                        .startCondition(entity -> entity.getRandom().nextInt(10) == 1 && entity.getSkill((ManasSkill)CommonSkills.COERCION.get()) != null),
                     new CustomRangeAttack(0)
                        .maxAttackRadius(12.0F)
                        .attackInterval(entity -> 160)
                        .performAttack((entity, target) -> entity.setBreathing(80))
                        .startCondition(entity -> entity.getRandom().nextInt(15) == 1),
                     new AnimatableMeleeAttack(1)
                        .attackInterval(entity -> 5)
                        .whenStarting(entity -> entity.swing(InteractionHand.MAIN_HAND, true))
                        .startCondition(entity -> !entity.usingRangedWeapon())
                  }
               )
               .startCondition(entity -> entity.getBreathing() <= 0)
         }
      );
   }
}
