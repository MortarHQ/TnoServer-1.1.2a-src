package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModSounds;
import java.util.EnumSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class AzazelHumanPhase2Goal extends Goal {
   private final AzazelHumanEntity boss;
   private int tick = 0;
   private final int MAX_TICKS = 180;

   public AzazelHumanPhase2Goal(AzazelHumanEntity boss) {
      this.boss = boss;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      return this.boss.forcedAttackGoal == 5 && (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE) == 5;
   }

   public void start() {
      this.boss.forcedAttackGoal = 0;
      this.boss.getNavigation().stop();
      this.tick = 0;
      this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 70);
      this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.ROAR.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
   }

   public void tick() {
      this.tick++;
      if (!this.boss.level().isClientSide() && this.tick < 150) {
         ServerLevel sl = (ServerLevel)this.boss.level();
         double radius = 8.0;

         for (int i = 0; i < 15; i++) {
            double angle = this.boss.getRandom().nextDouble() * 2.0 * Math.PI;
            double x = this.boss.getX() + Math.cos(angle) * radius;
            double z = this.boss.getZ() + Math.sin(angle) * radius;
            double speedY = 0.5 + this.boss.getRandom().nextDouble() * 0.3;
            sl.sendParticles(ParticleTypes.LARGE_SMOKE, x, this.boss.getY() + 0.1, z, 0, 0.0, speedY, 0.0, 1.0);
         }
      }

      if (this.tick == 60) {
         this.boss.getEntityData().set(AzazelHumanEntity.IS_PHASE_2, true);
      }

      if (this.tick >= 180) {
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 5);
      }
   }

   public boolean canContinueToUse() {
      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      return this.tick < 180 && state < 100;
   }
}
