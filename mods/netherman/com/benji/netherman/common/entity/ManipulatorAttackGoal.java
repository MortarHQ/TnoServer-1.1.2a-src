package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModEffects;
import java.util.EnumSet;
import net.minecraft.server.TickTask;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;

public class ManipulatorAttackGoal extends Goal {
   private final ManipulatorEntity mob;
   private int attackCooldown = 40;

   public ManipulatorAttackGoal(ManipulatorEntity mob) {
      this.mob = mob;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      return this.mob.getTarget() != null && this.mob.getTarget().isAlive();
   }

   public void tick() {
      LivingEntity target = this.mob.getTarget();
      if (target != null) {
         double distSq = this.mob.distanceToSqr(target);
         this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
         if (this.mob.fleeTicks > 0) {
            this.mob
               .getNavigation()
               .moveTo(this.mob.getX() + (this.mob.getX() - target.getX()), this.mob.getY(), this.mob.getZ() + (this.mob.getZ() - target.getZ()), 2.0);
         } else if (this.mob.castTicks > 0) {
            this.mob.getNavigation().stop();
         } else {
            if (this.attackCooldown > 0) {
               this.attackCooldown--;
            }

            if (distSq < 64.0) {
               this.mob
                  .getNavigation()
                  .moveTo(this.mob.getX() + (this.mob.getX() - target.getX()), this.mob.getY(), this.mob.getZ() + (this.mob.getZ() - target.getZ()), 1.0);
            } else if (distSq > 100.0) {
               this.mob.getNavigation().moveTo(target, 1.0);
            } else {
               this.mob.getNavigation().stop();
            }

            if (this.mob.getNavigation().isInProgress()) {
               if (this.mob.getEntityState() != 2) {
                  this.mob.setEntityState(1);
               }
            } else if (this.mob.getEntityState() != 2) {
               this.mob.setEntityState(0);
            }

            if (distSq <= 225.0 && this.attackCooldown == 0) {
               this.mob.getNavigation().stop();
               if (this.mob.manipulationCooldown <= 0 && target instanceof Player player) {
                  this.mob.setEntityState(3);
                  this.mob.castTicks = 75;
                  this.mob.manipulationCooldown = 1200;
                  player.addEffect(new MobEffectInstance(ModEffects.MANIPULATION, 200, 0));
                  this.mob.level().getServer().tell(new TickTask(this.mob.level().getServer().getTickCount() + 35, () -> {
                     if (this.mob.isAlive() && this.mob.castTicks > 0) {
                        this.mob.setEntityState(4);
                     }
                  }));
               } else {
                  this.mob.setEntityState(3);
                  this.mob.castTicks = 35;
                  this.mob.spawnWitherSkeletons();
               }

               this.attackCooldown = 160;
            }
         }
      }
   }
}
