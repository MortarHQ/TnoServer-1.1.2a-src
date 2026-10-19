package net.pixeldreamstudios.mythika.entity;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mythika.Mythika;
import org.jetbrains.annotations.Nullable;

public class ReturnToCampfireGoal extends Goal {
   private static final double ARRIVED = 6.0;
   private static final double SPEED = 0.9;
   private static final int REPATH_TICKS = 20;
   private static final int GIVE_UP_TICKS = 400;
   private final SatyrEntity satyr;
   @Nullable
   private BlockPos fire;
   private int repathTicks;
   private int giveUpTicks;

   public ReturnToCampfireGoal(SatyrEntity satyr) {
      this.satyr = satyr;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      if (!Mythika.config.satyrPlaysMusic || this.satyr.getTarget() != null) {
         return false;
      }

      if (this.satyr.nearestCampfire() != null) {
         return false;
      }

      this.fire = this.satyr.campfireToReturnTo();
      return this.fire != null && !this.arrived();
   }

   public boolean canContinueToUse() {
      if (this.satyr.getTarget() == null && this.giveUpTicks > 0) {
         this.fire = this.satyr.campfireToReturnTo();
         return this.fire != null && !this.arrived();
      } else {
         return false;
      }
   }

   public void start() {
      this.giveUpTicks = 400;
      this.repathTicks = 0;
   }

   public void stop() {
      this.satyr.forgetCampfire();
      this.satyr.getNavigation().stop();
      this.fire = null;
   }

   public void tick() {
      this.satyr.getLookControl().setLookAt(this.fire.getX() + 0.5, this.fire.getY() + 0.5, this.fire.getZ() + 0.5);
      if (--this.giveUpTicks <= 0) {
         this.satyr.forgetHomeCampfire();
      } else {
         if (--this.repathTicks <= 0 || this.satyr.getNavigation().isDone()) {
            this.repathTicks = 20;
            this.satyr.getNavigation().moveTo(this.fire.getX() + 0.5, this.fire.getY(), this.fire.getZ() + 0.5, 0.9);
         }
      }
   }

   private boolean arrived() {
      return this.satyr.distanceToSqr(Vec3.atCenterOf(this.fire)) < 36.0;
   }
}
