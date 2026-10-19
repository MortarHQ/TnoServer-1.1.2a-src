package com.chronodawn.entities.mobs;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ForgottenMinuteEntity extends Monster {
   public ForgottenMinuteEntity(EntityType<? extends ForgottenMinuteEntity> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 5;
      this.moveControl = new FlyingMoveControl(this, 20, true);
   }

   protected PathNavigation createNavigation(Level level) {
      FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
      navigation.setCanOpenDoors(false);
      navigation.setCanFloat(true);
      navigation.setCanPassDoors(true);
      return navigation;
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      Vec3 movement = this.getDeltaMovement();
      if (!this.onGround() && movement.y < 0.0) {
         this.setDeltaMovement(movement.multiply(1.0, 0.6, 1.0));
      }
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new ForgottenMinuteEntity.ForgottenMinuteChargeAttackGoal());
      this.goalSelector.addGoal(2, new ForgottenMinuteEntity.ForgottenMinuteRandomMoveGoal());
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 14.0)
         .add(Attributes.ATTACK_DAMAGE, 4.0)
         .add(Attributes.MOVEMENT_SPEED, 0.4)
         .add(Attributes.FOLLOW_RANGE, 30.0)
         .add(Attributes.FLYING_SPEED, 0.4);
   }

   public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
      return false;
   }

   public static boolean checkForgottenMinuteSpawnRules(
      EntityType<ForgottenMinuteEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return level.getDifficulty() == Difficulty.PEACEFUL ? false : Mob.checkMobSpawnRules(entityType, level, spawnType, pos, random);
   }

   class ForgottenMinuteChargeAttackGoal extends Goal {
      public ForgottenMinuteChargeAttackGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return ForgottenMinuteEntity.this.getTarget() != null
               && !ForgottenMinuteEntity.this.getMoveControl().hasWanted()
               && ForgottenMinuteEntity.this.random.nextInt(reducedTickDelay(2)) == 0
            ? ForgottenMinuteEntity.this.distanceToSqr(ForgottenMinuteEntity.this.getTarget()) > 4.0
            : false;
      }

      public boolean canContinueToUse() {
         return ForgottenMinuteEntity.this.getMoveControl().hasWanted()
            && ForgottenMinuteEntity.this.getTarget() != null
            && ForgottenMinuteEntity.this.getTarget().isAlive();
      }

      public void start() {
         LivingEntity target = ForgottenMinuteEntity.this.getTarget();
         if (target != null) {
            Vec3 targetPos = target.getEyePosition();
            ForgottenMinuteEntity.this.moveControl.setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 2.0);
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity target = ForgottenMinuteEntity.this.getTarget();
         if (target != null) {
            double distanceSq = ForgottenMinuteEntity.this.distanceToSqr(target);
            if (distanceSq < 4.0) {
               ForgottenMinuteEntity.this.doHurtTarget(target);
               ForgottenMinuteEntity.this.getMoveControl()
                  .setWantedPosition(ForgottenMinuteEntity.this.getX(), ForgottenMinuteEntity.this.getY(), ForgottenMinuteEntity.this.getZ(), 1.0);
            } else if (distanceSq < 256.0) {
               Vec3 targetPos = target.getEyePosition();
               ForgottenMinuteEntity.this.moveControl.setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 2.0);
            }
         }
      }
   }

   class ForgottenMinuteRandomMoveGoal extends Goal {
      public ForgottenMinuteRandomMoveGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return !ForgottenMinuteEntity.this.getMoveControl().hasWanted() && ForgottenMinuteEntity.this.random.nextInt(reducedTickDelay(2)) == 0;
      }

      public boolean canContinueToUse() {
         return false;
      }

      public void tick() {
         BlockPos blockPos = ForgottenMinuteEntity.this.blockPosition();

         for (int i = 0; i < 3; i++) {
            BlockPos randomPos = blockPos.offset(
               ForgottenMinuteEntity.this.random.nextInt(15) - 7,
               ForgottenMinuteEntity.this.random.nextInt(11) - 5,
               ForgottenMinuteEntity.this.random.nextInt(15) - 7
            );
            if (ForgottenMinuteEntity.this.level().isEmptyBlock(randomPos)) {
               ForgottenMinuteEntity.this.moveControl.setWantedPosition(randomPos.getX() + 0.5, randomPos.getY() + 0.5, randomPos.getZ() + 0.5, 1.5);
               if (ForgottenMinuteEntity.this.getTarget() == null) {
                  ForgottenMinuteEntity.this.getLookControl().setLookAt(randomPos.getX() + 0.5, randomPos.getY() + 0.5, randomPos.getZ() + 0.5, 180.0F, 20.0F);
               }
               break;
            }
         }
      }
   }
}
