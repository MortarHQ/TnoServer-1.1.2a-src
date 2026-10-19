package com.chronodawn.entities.mobs;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class FloqEntity extends Monster {
   public float targetSquish;
   public float squish;
   public float oSquish;
   private boolean wasOnGround = false;

   public FloqEntity(EntityType<? extends FloqEntity> entityType, Level level) {
      super(entityType, level);
      this.moveControl = new FloqEntity.FloqMoveControl(this);
      this.xpReward = 5;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(2, new FloqEntity.FloqRandomDirectionGoal(this));
      this.goalSelector.addGoal(3, new FloqEntity.FloqFloatGoal(this));
      this.goalSelector.addGoal(4, new FloqEntity.FloqKeepOnJumpingGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 16.0).add(Attributes.MOVEMENT_SPEED, 0.4).add(Attributes.ATTACK_DAMAGE, 3.0);
   }

   public void jumpFromGround() {
      Vec3 vec3 = this.getDeltaMovement();
      this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
      this.hasImpulse = true;
   }

   public static boolean checkFloqSpawnRules(
      EntityType<FloqEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return Monster.checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
   }

   protected SoundEvent getJumpSound() {
      return SoundEvents.SLIME_JUMP;
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.SLIME_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.SLIME_DEATH;
   }

   protected boolean canJump() {
      return true;
   }

   public void tick() {
      this.oSquish = this.squish;
      if (this.onGround() && !this.wasOnGround) {
         this.targetSquish = -0.5F;
      } else if (!this.onGround() && this.wasOnGround) {
         this.targetSquish = 1.0F;
      } else {
         this.targetSquish = this.targetSquish + (0.0F - this.targetSquish) * 0.1F;
      }

      this.squish = this.squish + (this.targetSquish - this.squish) * 0.5F;
      this.squish = Mth.clamp(this.squish, -0.5F, 1.0F);
      super.tick();
   }

   public void aiStep() {
      this.wasOnGround = this.onGround();
      super.aiStep();
   }

   protected int getJumpDelay() {
      return this.random.nextInt(5) + 3;
   }

   static class FloqFloatGoal extends Goal {
      private final FloqEntity floq;

      public FloqFloatGoal(FloqEntity floq) {
         this.floq = floq;
         this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
         floq.getNavigation().setCanFloat(true);
      }

      public boolean canUse() {
         return (this.floq.isInWater() || this.floq.isInLava()) && this.floq.getMoveControl() instanceof FloqEntity.FloqMoveControl;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         if (this.floq.getRandom().nextFloat() < 0.8F) {
            this.floq.getJumpControl().jump();
         }

         ((FloqEntity.FloqMoveControl)this.floq.getMoveControl()).setWantedMovement(1.2);
      }
   }

   static class FloqKeepOnJumpingGoal extends Goal {
      private final FloqEntity floq;

      public FloqKeepOnJumpingGoal(FloqEntity floq) {
         this.floq = floq;
         this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
      }

      public boolean canUse() {
         return !this.floq.isPassenger();
      }

      public void tick() {
         ((FloqEntity.FloqMoveControl)this.floq.getMoveControl()).setWantedMovement(1.0);
      }
   }

   static class FloqMoveControl extends MoveControl {
      private float yRot;
      private int jumpDelay;
      private final FloqEntity floq;
      private boolean isAggressive;

      public FloqMoveControl(FloqEntity floq) {
         super(floq);
         this.floq = floq;
         this.yRot = 180.0F * floq.getYRot() / (float) Math.PI;
      }

      public void setDirection(float yRot, boolean aggressive) {
         this.yRot = yRot;
         this.isAggressive = aggressive;
      }

      public void setWantedMovement(double speed) {
         this.speedModifier = speed;
         this.operation = Operation.MOVE_TO;
      }

      public void tick() {
         this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
         this.mob.yHeadRot = this.mob.getYRot();
         this.mob.yBodyRot = this.mob.getYRot();
         if (this.operation != Operation.MOVE_TO) {
            this.mob.setZza(0.0F);
         } else {
            this.operation = Operation.WAIT;
            if (this.mob.onGround()) {
               this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
               if (this.jumpDelay-- <= 0) {
                  this.jumpDelay = this.floq.getJumpDelay();
                  if (this.isAggressive) {
                     this.jumpDelay /= 3;
                  }

                  this.floq.getJumpControl().jump();
                  if (this.floq.canJump()) {
                     this.floq.playSound(this.floq.getJumpSound(), this.floq.getSoundVolume(), this.floq.getVoicePitch());
                  }
               } else {
                  this.floq.xxa = 0.0F;
                  this.floq.zza = 0.0F;
                  this.mob.setSpeed(0.0F);
               }
            } else {
               this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
         }
      }
   }

   static class FloqRandomDirectionGoal extends Goal {
      private final FloqEntity floq;
      private float chosenDegrees;
      private int nextRandomizeTime;

      public FloqRandomDirectionGoal(FloqEntity floq) {
         this.floq = floq;
         this.setFlags(EnumSet.of(Flag.LOOK));
      }

      public boolean canUse() {
         return this.floq.getTarget() == null
            && (this.floq.onGround() || this.floq.isInWater() || this.floq.isInLava() || this.floq.hasEffect(MobEffects.LEVITATION))
            && this.floq.getMoveControl() instanceof FloqEntity.FloqMoveControl;
      }

      public void tick() {
         if (--this.nextRandomizeTime <= 0) {
            this.nextRandomizeTime = this.adjustedTickDelay(40 + this.floq.getRandom().nextInt(60));
            this.chosenDegrees = this.floq.getRandom().nextInt(360);
         }

         ((FloqEntity.FloqMoveControl)this.floq.getMoveControl()).setDirection(this.chosenDegrees, false);
      }
   }
}
