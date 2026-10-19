package com.benji.netherman.common.entity;

import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModSounds;
import java.util.EnumSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AzazelHumanMovementGoal extends Goal {
   private final AzazelHumanEntity boss;
   private Vec3 chargeDirection = Vec3.ZERO;
   private int moveMode = 0;
   private int actionTimer = 0;
   private int pathUpdateTimer = 0;

   public AzazelHumanMovementGoal(AzazelHumanEntity boss) {
      this.boss = boss;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      if (state >= 5 && state != 14 && state < 100) {
         LivingEntity target = this.boss.getTarget();
         if (target == null) {
            return false;
         }

         double stopRadius = (Double)AzazelConfig.HUMAN_MOVEMENT_STOP_RADIUS.get();
         return this.boss.distanceToSqr(target) > stopRadius * stopRadius;
      } else {
         return false;
      }
   }

   public void start() {
      this.pathUpdateTimer = 0;
      boolean isPhase2 = (Boolean)this.boss.getEntityData().get(AzazelHumanEntity.IS_PHASE_2);
      int rand = this.boss.getRandom().nextInt(100);
      if (isPhase2) {
         if (rand < 10) {
            this.moveMode = 0;
            this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 10);
         } else if (rand < 15) {
            this.moveMode = 1;
            this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 11);
         } else if (rand < 55) {
            this.moveMode = 2;
            this.startCharge();
         } else {
            this.moveMode = 3;
            this.startJump();
         }
      } else if (rand < 40) {
         this.moveMode = 0;
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 10);
      } else if (rand < 70) {
         this.moveMode = 1;
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 11);
      } else if (rand < 90) {
         this.moveMode = 2;
         this.startCharge();
      } else {
         this.moveMode = 3;
         this.startJump();
      }
   }

   private void startCharge() {
      this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 12);
      this.actionTimer = 60;
      LivingEntity target = this.boss.getTarget();
      if (target != null) {
         this.chargeDirection = target.position().subtract(this.boss.position()).normalize();
         this.boss.getLookControl().setLookAt(target.getX(), this.boss.getEyeY(), target.getZ());
      }
   }

   private void startJump() {
      this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 13);
      this.actionTimer = 43;
      LivingEntity target = this.boss.getTarget();
      if (target != null) {
         Vec3 jumpVec = target.position().subtract(this.boss.position()).normalize().scale(1.5);
         this.boss.setDeltaMovement(jumpVec.x, 1.2, jumpVec.z);
      }
   }

   public void tick() {
      LivingEntity target = this.boss.getTarget();
      if (target != null) {
         if (this.moveMode == 0 || this.moveMode == 1) {
            this.boss.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.pathUpdateTimer > 0 && !this.boss.getNavigation().isDone()) {
               this.pathUpdateTimer--;
            } else {
               this.boss.getNavigation().moveTo(target, 1.0);
               this.pathUpdateTimer = 20;
            }

            double stopRadius = (Double)AzazelConfig.HUMAN_MOVEMENT_STOP_RADIUS.get();
            if (this.boss.distanceToSqr(target) <= stopRadius * stopRadius) {
               this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 5);
            }
         } else if (this.moveMode == 2) {
            this.actionTimer--;
            this.boss.setDeltaMovement(this.chargeDirection.x * 0.8, this.boss.getDeltaMovement().y, this.chargeDirection.z * 0.8);
            float targetYaw = (float)(Math.atan2(this.chargeDirection.z, this.chargeDirection.x) * (180.0 / Math.PI)) - 90.0F;
            this.boss.setYRot(targetYaw);
            this.boss.yBodyRot = targetYaw;
            this.boss.yHeadRot = targetYaw;
            float chargeDamage = ((Double)AzazelConfig.HUMAN_CHARGE_DAMAGE.get()).floatValue();
            double chargeKb = (Double)AzazelConfig.HUMAN_CHARGE_KNOCKBACK.get();

            for (Player p : this.boss.level().getEntitiesOfClass(Player.class, this.boss.getBoundingBox().inflate(1.5))) {
               p.setDeltaMovement(this.chargeDirection.x * chargeKb, 0.2, this.chargeDirection.z * chargeKb);
               p.hurtMarked = true;
               if (this.actionTimer % 5 == 0) {
                  p.hurt(this.boss.damageSources().mobAttack(this.boss), 30.0F);
               }
            }

            AABB crashBox = this.boss.getBoundingBox().move(this.chargeDirection.x * 1.5, 0.0, this.chargeDirection.z * 1.5);
            boolean hitWall = this.boss.horizontalCollision || !this.boss.level().noCollision(this.boss, crashBox);
            if (hitWall) {
               this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.STOMP.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
               this.boss.triggerScreenShake(2.0F, 30);
               this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 14);
               this.boss.getEntityData().set(AzazelHumanEntity.ATTACK_TIMER, 90);
               this.boss.setDeltaMovement(Vec3.ZERO);
            } else if (this.actionTimer <= 0) {
               this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 5);
            }
         } else if (this.moveMode == 3) {
            this.actionTimer--;
            this.boss.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.actionTimer <= 0 || this.boss.onGround() && this.actionTimer < 30) {
               this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.STOMP.get(), SoundSource.HOSTILE, 3.0F, 0.8F);
               this.boss.triggerScreenShake(2.5F, 40);

               for (Player p : this.boss.level().getEntitiesOfClass(Player.class, this.boss.getBoundingBox().inflate(20.0))) {
                  p.setDeltaMovement(p.getDeltaMovement().x, 1.5, p.getDeltaMovement().z);
                  p.hurtMarked = true;
               }

               this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 5);
            }
         }
      }
   }

   public boolean canContinueToUse() {
      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      return state >= 10 && state <= 13;
   }

   public void stop() {
      this.boss.getNavigation().stop();
      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      if (state != 14 && state != 40) {
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 5);
      }
   }
}
