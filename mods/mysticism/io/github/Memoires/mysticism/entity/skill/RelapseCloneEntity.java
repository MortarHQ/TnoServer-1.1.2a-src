package io.github.Memoires.mysticism.entity.skill;

import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RelapseCloneEntity extends CloneEntity {
   private RelapseCloneEntity.RelapseBehavior behavior = RelapseCloneEntity.RelapseBehavior.STATIC;

   public RelapseCloneEntity(EntityType<? extends RelapseCloneEntity> type, Level level) {
      super(type, level);
   }

   public static Builder setAttributes() {
      return Player.createAttributes().add(Attributes.STEP_HEIGHT, 1.0).add(Attributes.FOLLOW_RANGE, 64.0);
   }

   public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
      return !mobEffectInstance.getEffect().equals(TensuraMobEffects.getReference(TensuraMobEffects.MAGICULE_POISON));
   }

   public void setRelapseBehavior(RelapseCloneEntity.RelapseBehavior behavior) {
      this.behavior = behavior;
      switch (behavior) {
         case STATIC:
         case STARE:
            this.setStatic(true);
            this.setNoAi(false);
            this.setTarget(null);
            this.getNavigation().stop();
            this.setDeltaMovement(0.0, 0.0, 0.0);
            break;
         case CHASE_NO_DAMAGE:
            this.setStatic(false);
            this.setNoAi(false);
      }
   }

   public boolean doHurtTarget(Entity target, float multiplier) {
      return this.behavior == RelapseCloneEntity.RelapseBehavior.CHASE_NO_DAMAGE ? true : super.doHurtTarget(target, multiplier);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.behavior == RelapseCloneEntity.RelapseBehavior.STARE) {
            this.getNavigation().stop();
            this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
            LivingEntity target = this.level()
               .getNearestEntity(
                  LivingEntity.class,
                  TargetingConditions.forNonCombat().ignoreLineOfSight(),
                  this,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  this.getBoundingBox().inflate(16.0)
               );
            if (target != null && target != this.getOwner() && target != this) {
               this.getLookControl().setLookAt(target, 30.0F, 30.0F);
               this.setYHeadRot(this.getYRot());
            }
         }

         if (this.behavior == RelapseCloneEntity.RelapseBehavior.CHASE_NO_DAMAGE) {
            LivingEntity target = this.level()
               .getNearestEntity(
                  LivingEntity.class,
                  TargetingConditions.forCombat().ignoreLineOfSight(),
                  this,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  this.getBoundingBox().inflate(32.0)
               );
            if (target != null && target != this.getOwner() && target != this) {
               this.setTarget(target);
               this.getNavigation().moveTo(target, 1.4);
            }
         }
      }
   }

   public enum RelapseBehavior {
      STATIC,
      STARE,
      CHASE_NO_DAMAGE;
   }
}
