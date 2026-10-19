package com.benji.netherman.common.entity;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class GuardianBehaviorGoal extends Goal {
   private final GuardianEntity guardian;
   private int tickCounter = 0;
   private int specCooldown = 0;
   private int punchCooldown = 0;
   private int comboPunchesLeft = 0;
   private int pathUpdateDelay = 0;

   public GuardianBehaviorGoal(GuardianEntity guardian) {
      this.guardian = guardian;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      if (this.guardian.getEntityState() == 6) {
         return false;
      } else {
         return this.guardian.getEntityState() == 7 ? false : this.guardian.getTarget() != null;
      }
   }

   public boolean canContinueToUse() {
      int state = this.guardian.getEntityState();
      return state != 1 && state != 3 && state != 5 && state != 4 ? super.canContinueToUse() : true;
   }

   public void start() {
      this.guardian.setEntityState(1);
      this.tickCounter = 15;
      this.pathUpdateDelay = 0;
   }

   public void tick() {
      LivingEntity target = this.guardian.getTarget();
      int currentState = this.guardian.getEntityState();
      if (target != null) {
         this.guardian.getLookControl().setLookAt(target, 30.0F, 30.0F);
      }

      if (this.specCooldown > 0) {
         this.specCooldown--;
      }

      if (currentState == 1 || currentState == 5 || currentState == 4) {
         this.guardian.getNavigation().stop();
         if (this.tickCounter > 0) {
            this.tickCounter--;
            if (currentState == 5 && this.tickCounter == 30) {
               this.guardian.performMegaPunch();
            }

            if (this.tickCounter == 0) {
               this.guardian.setEntityState(2);
               this.pathUpdateDelay = 0;
            }
         }
      } else if (currentState == 3) {
         this.guardian.getNavigation().stop();
         if (this.comboPunchesLeft > 0) {
            if (this.punchCooldown > 0) {
               this.punchCooldown--;
               if (this.punchCooldown == 10 || this.punchCooldown == 6 || this.punchCooldown == 2) {
                  this.guardian.performMeleeAttack();
               }
            }

            if (this.punchCooldown == 0) {
               this.comboPunchesLeft--;
               if (this.comboPunchesLeft > 0) {
                  this.punchCooldown = 11;
               } else {
                  this.guardian.setEntityState(4);
                  this.tickCounter = 15;
               }
            }
         }
      } else if (target != null) {
         double distSq = this.guardian.distanceToSqr(target);
         if (currentState == 2) {
            if (this.pathUpdateDelay <= 0) {
               this.guardian.getNavigation().moveTo(target, 1.0);
               this.pathUpdateDelay = 10;
            } else {
               this.pathUpdateDelay--;
            }

            if (distSq <= 16.0) {
               this.guardian.setEntityState(3);
               this.comboPunchesLeft = this.guardian.getRandom().nextInt(3) + 3;
               this.punchCooldown = 11;
               this.guardian.getNavigation().stop();
            } else if (distSq <= 100.0 && this.specCooldown == 0 && this.guardian.getRandom().nextInt(20) == 0) {
               this.guardian.setEntityState(5);
               this.tickCounter = 40;
               this.specCooldown = 160;
               this.guardian.getNavigation().stop();
            }
         }
      }
   }

   public void stop() {
      int state = this.guardian.getEntityState();
      if (state != 1 && state != 3 && state != 5 && state != 4) {
         this.guardian.setEntityState(0);
      }

      this.guardian.getNavigation().stop();
   }
}
