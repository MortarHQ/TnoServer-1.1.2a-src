package com.benji.netherman.common.entity;

import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModSounds;
import java.util.EnumSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class AzazelHumanLongRangeGoal extends Goal {
   private final AzazelHumanEntity boss;
   private int attackState = 0;
   private int currentAnimTick = 0;
   private int maxAnimTick = 0;
   private int cooldown = 0;
   private int spikePattern = 0;
   private double spikeTargetX = 0.0;
   private double spikeTargetZ = 0.0;

   public AzazelHumanLongRangeGoal(AzazelHumanEntity boss) {
      this.boss = boss;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      if (this.cooldown > 0) {
         this.cooldown--;
      }

      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      if (state != 5) {
         return false;
      }

      if (this.boss.forcedAttackGoal == 3) {
         return true;
      }

      LivingEntity target = this.boss.getTarget();
      if (target == null) {
         return false;
      }

      double longRadiusMin = (Double)AzazelConfig.HUMAN_LONG_ATTACK_RADIUS_MIN.get();
      return this.boss.distanceToSqr(target) > longRadiusMin * longRadiusMin && this.cooldown <= 0 ? this.boss.getRandom().nextBoolean() : false;
   }

   public void start() {
      this.boss.getNavigation().stop();
      this.currentAnimTick = 0;
      this.boss.forcedAttackGoal = 0;
      LivingEntity target = this.boss.getTarget();
      if (target != null) {
         double dX = target.getX() - this.boss.getX();
         double dZ = target.getZ() - this.boss.getZ();
         float yaw = (float)(Math.atan2(dZ, dX) * (180.0 / Math.PI)) - 90.0F;
         this.boss.setYRot(yaw);
         this.boss.yBodyRot = yaw;
         this.boss.yHeadRot = yaw;
      }

      int rand = this.boss.getRandom().nextInt(100);
      if (rand < 25) {
         this.attackState = 50;
         this.maxAnimTick = 60;
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 50);
      } else if (rand < 50) {
         this.attackState = 51;
         this.maxAnimTick = 50;
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 51);
         this.spikePattern = this.boss.getRandom().nextInt(4) + 1;
         if (this.spikePattern == 1 || this.spikePattern == 2) {
            this.spikeTargetX = this.boss.getX();
            this.spikeTargetZ = this.boss.getZ();
         } else if (target != null) {
            this.spikeTargetX = target.getX();
            this.spikeTargetZ = target.getZ();
         }
      } else if (rand < 75) {
         this.attackState = 52;
         this.maxAnimTick = 50;
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 52);
      } else {
         this.attackState = 53;
         this.maxAnimTick = 50;
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 53);
      }

      this.cooldown = 100;
   }

   public void tick() {
      this.currentAnimTick++;
      LivingEntity target = this.boss.getTarget();
      if (target != null && this.currentAnimTick < this.maxAnimTick / 2) {
         this.boss.getLookControl().setLookAt(target, 30.0F, 30.0F);
      }

      if (this.attackState == 50) {
         if (this.currentAnimTick == 10 || this.currentAnimTick == 20 || this.currentAnimTick == 30) {
            SoundEvent swingSound = this.currentAnimTick == 20 ? (SoundEvent)ModSounds.SWING_2.get() : (SoundEvent)ModSounds.SWING_1.get();
            this.boss
               .level()
               .playSound(null, this.boss.blockPosition(), swingSound, SoundSource.HOSTILE, 2.0F, 0.8F + this.boss.getRandom().nextFloat() * 0.4F);
            if (!this.boss.level().isClientSide() && target != null) {
               AzazelSplashEntity splash = (AzazelSplashEntity)((EntityType)ModEntities.SPLASH_ENTITY.get()).create(this.boss.level());
               if (splash != null) {
                  Vec3 look = this.boss.getLookAngle();
                  splash.setPos(this.boss.getX() + look.x * 2.0, this.boss.getY() + 3.0, this.boss.getZ() + look.z * 2.0);
                  splash.setOwner(this.boss);
                  double dX = target.getX() - splash.getX();
                  double dY = target.getY(0.5) - splash.getY();
                  double dZ = target.getZ() - splash.getZ();
                  splash.shoot(dX, dY, dZ, 1.5F, 0.0F);
                  this.boss.level().addFreshEntity(splash);
               }
            }
         }
      } else if (this.attackState == 51) {
         if (this.currentAnimTick == 10) {
            this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.DODGE.get(), SoundSource.HOSTILE, 2.0F, 0.8F);
            if (!this.boss.level().isClientSide()) {
               this.spawnSpikePattern();
            }
         }

         if (this.spikePattern == 4 && !this.boss.level().isClientSide()) {
            if (this.currentAnimTick == 30) {
               this.spawnSquareBorder(8);
            }

            if (this.currentAnimTick == 40) {
               this.spawnSquareBorder(5);
            }

            if (this.currentAnimTick == 50) {
               this.spawnSquareBorder(3);
            }
         }
      } else if (this.attackState == 52) {
         if (this.currentAnimTick == 10) {
            this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.DODGE.get(), SoundSource.HOSTILE, 2.0F, 0.8F);
            if (!this.boss.level().isClientSide()) {
               for (int angle = 0; angle < 360; angle += 90) {
                  for (int r = 1; r <= 15; r++) {
                     this.spawnProjectileSpike(1, r, angle, 0.0, 0.0);
                  }
               }
            }
         }
      } else if (this.attackState == 53 && (this.currentAnimTick == 10 || this.currentAnimTick == 15 || this.currentAnimTick == 20)) {
         this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.SWING_1.get(), SoundSource.HOSTILE, 2.0F, 1.2F);
         if (!this.boss.level().isClientSide()) {
            for (int angle = 0; angle < 360; angle += 45) {
               double rad = Math.toRadians(angle);
               double dX = Math.cos(rad);
               double dZ = Math.sin(rad);
               this.spawnProjectileSpike(0, 0.0F, 0.0F, dX, dZ);
            }
         }
      }

      if (this.currentAnimTick >= this.maxAnimTick) {
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 5);
         this.attackState = 0;
      }
   }

   private void spawnProjectileSpike(int mode, float radius, float angle, double dX, double dZ) {
      AzazelSpikesProjectileEntity proj = (AzazelSpikesProjectileEntity)((EntityType)ModEntities.SPIKE_PROJECTILE_ENTITY.get()).create(this.boss.level());
      if (proj != null) {
         proj.setOwner(this.boss);
         proj.setMode(mode);
         if (mode == 1) {
            proj.setPivotId(this.boss.getId());
            proj.setOrbitRadius(radius);
            proj.setOrbitAngle(angle);
            proj.setPos(this.boss.getX(), this.boss.getY() + 0.5, this.boss.getZ());
         } else {
            proj.setPos(this.boss.getX() + dX * 2.0, this.boss.getY() + 1.0, this.boss.getZ() + dZ * 2.0);
            proj.shoot(dX, 0.0, dZ, 0.8F, 0.0F);
         }

         this.boss.level().addFreshEntity(proj);
      }
   }

   private void spawnSpikePattern() {
      if (this.spikePattern == 1) {
         for (int i = 1; i <= 15; i++) {
            this.spawnSingleSpike(this.spikeTargetX + i, this.spikeTargetZ);
            this.spawnSingleSpike(this.spikeTargetX - i, this.spikeTargetZ);
            this.spawnSingleSpike(this.spikeTargetX, this.spikeTargetZ + i);
            this.spawnSingleSpike(this.spikeTargetX, this.spikeTargetZ - i);
         }
      } else if (this.spikePattern == 2) {
         for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
               this.spawnSingleSpike(this.spikeTargetX + x, this.spikeTargetZ + z);
            }
         }
      } else if (this.spikePattern == 3) {
         for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
               this.spawnSingleSpike(this.spikeTargetX + x, this.spikeTargetZ + z);
            }
         }
      } else if (this.spikePattern == 4) {
         this.spawnSquareBorder(10);
      }
   }

   private void spawnSquareBorder(int size) {
      int half = size / 2;

      for (int x = -half; x <= half; x++) {
         for (int z = -half; z <= half; z++) {
            if (Math.abs(x) == half || Math.abs(z) == half) {
               this.spawnSingleSpike(this.spikeTargetX + x, this.spikeTargetZ + z);
            }
         }
      }
   }

   private void spawnSingleSpike(double x, double z) {
      AzazelSpikeEntity spike = (AzazelSpikeEntity)((EntityType)ModEntities.SPIKE_ENTITY.get()).create(this.boss.level());
      if (spike != null) {
         spike.setPos(x, this.boss.getY(), z);
         spike.setOwner(this.boss);
         this.boss.level().addFreshEntity(spike);
      }
   }

   public boolean canContinueToUse() {
      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      return this.currentAnimTick < this.maxAnimTick && state != 40 && state < 100;
   }
}
