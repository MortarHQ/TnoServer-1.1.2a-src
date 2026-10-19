package cn.leolezury.eternalstarlight.common.entity.living.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class MoveToTargetGoal extends Goal {
   private final Mob mob;
   private final double speedModifier;
   private final int recalculateInterval;
   private Vec3 lastTargetPos = Vec3.ZERO;
   private int lastRecalculate = 0;

   public MoveToTargetGoal(Mob mob, double speedModifier) {
      this(mob, speedModifier, 60);
   }

   public MoveToTargetGoal(Mob mob, double speedModifier, int recalculateInterval) {
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      this.mob = mob;
      this.speedModifier = speedModifier;
      this.recalculateInterval = recalculateInterval;
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public boolean canUse() {
      return this.mob.getTarget() != null;
   }

   public boolean canContinueToUse() {
      return this.mob.getTarget() != null;
   }

   public void tick() {
      if (this.mob.getTarget() != null) {
         this.mob.getLookControl().setLookAt(this.mob.getTarget(), 360.0F, 360.0F);
         if (this.lastTargetPos != this.mob.getTarget().position() && this.mob.tickCount - this.lastRecalculate > this.recalculateInterval) {
            this.lastTargetPos = this.mob.getTarget().position();
            this.lastRecalculate = this.mob.tickCount;
            this.mob.getNavigation().moveTo(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ(), this.speedModifier);
         }
      }
   }
}
