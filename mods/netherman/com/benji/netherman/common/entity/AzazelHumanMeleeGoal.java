package com.benji.netherman.common.entity;

import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModSounds;
import java.util.EnumSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class AzazelHumanMeleeGoal extends Goal {
   private final AzazelHumanEntity boss;
   private int attackState = 0;
   private int currentAnimTick = 0;
   private int maxAnimTick = 0;

   public AzazelHumanMeleeGoal(AzazelHumanEntity boss) {
      this.boss = boss;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      if (state != 5) {
         return false;
      }

      if (this.boss.forcedAttackGoal == 1) {
         return true;
      }

      LivingEntity target = this.boss.getTarget();
      if (target == null) {
         return false;
      }

      double meleeRadius = (Double)AzazelConfig.HUMAN_MELEE_ATTACK_RADIUS.get();
      double meleeRadiusSq = meleeRadius * meleeRadius;
      return this.boss.distanceToSqr(target) <= meleeRadiusSq || this.boss.getDamageTakenRecently() >= 25.0F && this.boss.getDefendCooldown() <= 0;
   }

   public void start() {
      this.boss.getNavigation().stop();
      this.currentAnimTick = 0;
      this.boss.forcedAttackGoal = 0;
      LivingEntity target = this.boss.getTarget();
      if (target != null) {
         this.boss.getLookControl().setLookAt(target, 30.0F, 30.0F);
      }

      if (this.boss.getDamageTakenRecently() >= 25.0F && this.boss.getDefendCooldown() <= 0) {
         this.attackState = 22;
         this.maxAnimTick = 100;
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 22);
         this.boss.heal(100.0F);
         this.boss.setDefendCooldown(2400);
         this.boss.resetDamageTaken();
      } else {
         int rand = this.boss.getRandom().nextInt(100);
         if (rand < 40) {
            this.attackState = 20;
            this.maxAnimTick = 38;
            this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 20);
         } else if (rand < 80) {
            this.attackState = 21;
            this.maxAnimTick = 60;
            this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 21);
            this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.GRUNT_2.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
         } else if (rand < 90) {
            this.attackState = 30;
            this.maxAnimTick = 24;
            this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 30);
            this.playRandomGrunt();
         }
      }
   }

   public void tick() {
      this.currentAnimTick++;
      LivingEntity target = this.boss.getTarget();
      if (target != null && this.currentAnimTick < this.maxAnimTick / 2) {
         this.boss.getLookControl().setLookAt(target, 30.0F, 30.0F);
      }

      if (this.attackState == 20) {
         if (this.currentAnimTick == 15) {
            this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.STOMP.get(), SoundSource.HOSTILE, 3.0F, 0.8F);
            this.boss.triggerScreenShake(5.0F, 30);
            if (this.boss.level() instanceof ServerLevel sl) {
               sl.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.boss.getX(), this.boss.getY() + 0.5, this.boss.getZ(), 50, 2.0, 0.2, 2.0, 0.1);
            }

            float stompDamage = ((Double)AzazelConfig.HUMAN_STOMP_DAMAGE.get()).floatValue();
            double stompKb = (Double)AzazelConfig.HUMAN_STOMP_KNOCKBACK.get();

            for (Player p : this.boss.level().getEntitiesOfClass(Player.class, this.boss.getBoundingBox().inflate(15.0))) {
               Vec3 push = p.position().subtract(this.boss.position()).normalize();
               p.setDeltaMovement(push.x * stompKb, stompKb * 1.5, push.z * stompKb);
               p.hurt(this.boss.damageSources().mobAttack(this.boss), stompDamage);
               p.hurtMarked = true;
            }
         }
      } else if (this.attackState == 21) {
         if (target != null && this.currentAnimTick <= 10) {
            double dX = target.getX() - this.boss.getX();
            double dZ = target.getZ() - this.boss.getZ();
            float yaw = (float)(Mth.atan2(dZ, dX) * (180.0 / Math.PI)) - 90.0F;
            this.boss.setYRot(yaw);
            this.boss.yBodyRot = yaw;
            this.boss.yHeadRot = yaw;
         }

         if (this.currentAnimTick == 5) {
            this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.SWING_1.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
            this.hitWithScythe(((Double)AzazelConfig.HUMAN_SCYTHE_1_DAMAGE.get()).floatValue(), (Double)AzazelConfig.HUMAN_SCYTHE_1_KNOCKBACK.get());
         } else if (this.currentAnimTick == 15) {
            this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.SWING_2.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
            this.hitWithScythe(((Double)AzazelConfig.HUMAN_SCYTHE_2_DAMAGE.get()).floatValue(), (Double)AzazelConfig.HUMAN_SCYTHE_2_KNOCKBACK.get());
         }
      } else if (this.attackState == 30) {
         if (this.currentAnimTick == 8) {
            this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.SWING_1.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
            this.executeSpearAttack(
               ((Double)AzazelConfig.HUMAN_SPEAR_MELEE_DAMAGE.get()).floatValue(), (Double)AzazelConfig.HUMAN_SPEAR_MELEE_KNOCKBACK.get(), false
            );
         }
      } else if (this.attackState == 22 && this.currentAnimTick % 10 == 0 && this.boss.level() instanceof ServerLevel sl) {
         sl.sendParticles(ParticleTypes.ENCHANT, this.boss.getX(), this.boss.getY() + 2.0, this.boss.getZ(), 10, 1.5, 1.5, 1.5, 0.1);
      }

      if (this.currentAnimTick >= this.maxAnimTick) {
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 5);
         this.attackState = 0;
      }
   }

   private void hitWithScythe(float damage, double knockbackMultiplier) {
      Vec3 look = this.boss.getLookAngle();
      Vec3 look2D = new Vec3(look.x, 0.0, look.z).normalize();

      for (Player p : this.boss.level().getEntitiesOfClass(Player.class, this.boss.getBoundingBox().inflate(12.0))) {
         Vec3 toPlayer = p.position().subtract(this.boss.position());
         Vec3 toPlayer2D = new Vec3(toPlayer.x, 0.0, toPlayer.z).normalize();
         if (toPlayer2D.lengthSqr() < 0.01) {
            p.hurt(this.boss.damageSources().mobAttack(this.boss), damage);
            p.setDeltaMovement(0.0, 0.4, 0.0);
            p.hurtMarked = true;
         } else if (look2D.dot(toPlayer2D) > -0.2) {
            p.hurt(this.boss.damageSources().mobAttack(this.boss), damage);
            p.setDeltaMovement(p.getDeltaMovement().add(toPlayer2D.x * knockbackMultiplier, 0.4, toPlayer2D.z * knockbackMultiplier));
            p.hurtMarked = true;
         }
      }
   }

   private void executeSpearAttack(float damage, double force, boolean pull) {
      Vec3 look = this.boss.getLookAngle();
      Vec3 look2D = new Vec3(look.x, 0.0, look.z).normalize();

      for (Player p : this.boss.level().getEntitiesOfClass(Player.class, this.boss.getBoundingBox().inflate(12.0))) {
         Vec3 toPlayer = p.position().subtract(this.boss.position());
         Vec3 toPlayer2D = new Vec3(toPlayer.x, 0.0, toPlayer.z).normalize();
         if (look2D.dot(toPlayer2D) > 0.0) {
            p.hurt(this.boss.damageSources().mobAttack(this.boss), damage);
            p.hurtMarked = true;
            if (pull) {
               p.setDeltaMovement(p.getDeltaMovement().add(-toPlayer2D.x * force, 0.3, -toPlayer2D.z * force));
            } else {
               p.setDeltaMovement(p.getDeltaMovement().add(toPlayer2D.x * force, 0.5, toPlayer2D.z * force));
            }
         }
      }
   }

   private void playRandomGrunt() {
      SoundEvent[] grunts = new SoundEvent[]{
         (SoundEvent)ModSounds.GRUNT_1.get(),
         (SoundEvent)ModSounds.GRUNT_3.get(),
         (SoundEvent)ModSounds.GRUNT_4.get(),
         (SoundEvent)ModSounds.GRUNT_5.get(),
         (SoundEvent)ModSounds.GRUNT_6.get(),
         (SoundEvent)ModSounds.GRUNT_7.get()
      };
      this.boss
         .level()
         .playSound(
            null,
            this.boss.blockPosition(),
            grunts[this.boss.getRandom().nextInt(grunts.length)],
            SoundSource.HOSTILE,
            2.0F,
            0.9F + this.boss.getRandom().nextFloat() * 0.2F
         );
   }

   public boolean canContinueToUse() {
      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      return this.currentAnimTick < this.maxAnimTick && state != 40 && state < 100;
   }
}
