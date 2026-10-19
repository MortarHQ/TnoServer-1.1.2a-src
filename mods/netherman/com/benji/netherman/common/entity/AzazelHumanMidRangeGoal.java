package com.benji.netherman.common.entity;

import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModSounds;
import java.util.EnumSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class AzazelHumanMidRangeGoal extends Goal {
   private final AzazelHumanEntity boss;
   private int attackState = 0;
   private int currentAnimTick = 0;
   private int maxAnimTick = 0;

   public AzazelHumanMidRangeGoal(AzazelHumanEntity boss) {
      this.boss = boss;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      if (state != 5) {
         return false;
      }

      if (this.boss.forcedAttackGoal == 2) {
         return true;
      }

      LivingEntity target = this.boss.getTarget();
      if (target == null) {
         return false;
      }

      double distSq = this.boss.distanceToSqr(target);
      double minRadius = (Double)AzazelConfig.HUMAN_MELEE_ATTACK_RADIUS.get();
      double maxRadius = (Double)AzazelConfig.HUMAN_MID_ATTACK_RADIUS_MAX.get();
      return distSq > minRadius * minRadius && distSq <= maxRadius * maxRadius;
   }

   public void start() {
      this.boss.getNavigation().stop();
      this.currentAnimTick = 0;
      this.boss.forcedAttackGoal = 0;
      LivingEntity target = this.boss.getTarget();
      if (target != null) {
         this.boss.getLookControl().setLookAt(target, 30.0F, 30.0F);
      }

      int rand = this.boss.getRandom().nextInt(100);
      if (rand < 40) {
         this.attackState = 30;
         this.maxAnimTick = 24;
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 30);
         this.playRandomGrunt();
      } else if (rand < 80) {
         this.attackState = 31;
         this.maxAnimTick = 40;
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 31);
         this.playRandomGrunt();
      } else {
         this.attackState = 32;
         this.maxAnimTick = 120;
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 32);
      }
   }

   public void tick() {
      this.currentAnimTick++;
      LivingEntity target = this.boss.getTarget();
      if (target != null && this.currentAnimTick < this.maxAnimTick / 3) {
         this.boss.getLookControl().setLookAt(target, 30.0F, 30.0F);
      }

      if (this.attackState == 30) {
         if (this.currentAnimTick == 8) {
            this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.SWING_1.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
            this.executeSpearAttack(
               ((Double)AzazelConfig.HUMAN_SPEAR_MID_PUSH_DAMAGE.get()).floatValue(), (Double)AzazelConfig.HUMAN_SPEAR_MID_PUSH_KNOCKBACK.get(), false
            );
         }
      } else if (this.attackState == 31) {
         if (this.currentAnimTick == 10) {
            this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.SWING_2.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
            this.executeSpearAttack(
               ((Double)AzazelConfig.HUMAN_SPEAR_MID_PULL_DAMAGE.get()).floatValue(), (Double)AzazelConfig.HUMAN_SPEAR_MID_PULL_KNOCKBACK.get(), true
            );
         }
      } else if (this.attackState == 32) {
         if (this.currentAnimTick == 15) {
            this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.SMOKE_BREATH.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
         }

         if (this.currentAnimTick >= 20 && this.currentAnimTick <= 80 && this.currentAnimTick % 2 == 0 && this.boss.level() instanceof ServerLevel sl) {
            Vec3 look = this.boss.getLookAngle();
            double spawnX = this.boss.getX() + look.x * 3.0;
            double spawnZ = this.boss.getZ() + look.z * 3.0;
            double spawnY = this.boss.getY() + 7.5;

            for (int i = 0; i < 15; i++) {
               double offsetX = (this.boss.getRandom().nextDouble() - 0.5) * 6.0;
               double offsetZ = (this.boss.getRandom().nextDouble() - 0.5) * 6.0;
               sl.sendParticles(ParticleTypes.LARGE_SMOKE, spawnX + offsetX, spawnY, spawnZ + offsetZ, 0, 0.0, -0.5, 0.0, 0.5);
            }
         }

         if (this.currentAnimTick == 40) {
            for (Player p : this.boss.level().getEntitiesOfClass(Player.class, this.boss.getBoundingBox().inflate(20.0))) {
               p.addEffect(new MobEffectInstance(MobEffects.WITHER, 600, 1));
               p.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 600, 0));
               p.addEffect(new MobEffectInstance(ModEffects.MANIPULATION, 400, 0));
            }
         }
      }

      if (this.currentAnimTick >= this.maxAnimTick) {
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 5);
         this.attackState = 0;
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
