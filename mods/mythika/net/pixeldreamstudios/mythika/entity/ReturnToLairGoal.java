package net.pixeldreamstudios.mythika.entity;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class ReturnToLairGoal extends Goal {
   private static final double ARRIVED = 8.0;
   private static final double SPEED = 0.9;
   private static final int REPATH_TICKS = 20;
   private static final int GIVE_UP_TICKS = 800;
   private static final int RETRY_TICKS = 300;
   private final CyclopsEntity cyclops;
   private BlockPos lair = BlockPos.ZERO;
   private int giveUpTicks;
   private int repathTicks;
   private int retryTicks;

   public ReturnToLairGoal(CyclopsEntity cyclops) {
      this.cyclops = cyclops;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      if (this.retryTicks > 0) {
         this.retryTicks--;
         return false;
      }

      if (this.cyclops.getTarget() != null) {
         return false;
      }

      BlockPos home = this.cyclops.lair();
      if (home == null) {
         return false;
      }

      this.lair = home;
      double away = this.cyclops.homeRadius();
      return this.cyclops.distanceToSqr(Vec3.atCenterOf(home)) > away * away;
   }

   public boolean canContinueToUse() {
      return this.cyclops.getTarget() == null && this.giveUpTicks > 0 && !this.arrived();
   }

   public void start() {
      this.giveUpTicks = 800;
      this.repathTicks = 0;
   }

   public void stop() {
      this.cyclops.getNavigation().stop();
   }

   public void tick() {
      this.cyclops.getLookControl().setLookAt(this.lair.getX() + 0.5, this.lair.getY() + 0.5, this.lair.getZ() + 0.5);
      if (--this.giveUpTicks <= 0) {
         this.retryTicks = 300;
      } else {
         if (--this.repathTicks <= 0 || this.cyclops.getNavigation().isDone()) {
            this.repathTicks = 20;
            this.cyclops.getNavigation().moveTo(this.lair.getX() + 0.5, this.lair.getY(), this.lair.getZ() + 0.5, 0.9);
         }
      }
   }

   private boolean arrived() {
      return this.cyclops.distanceToSqr(Vec3.atCenterOf(this.lair)) < 64.0;
   }
}
