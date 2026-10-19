package cn.leolezury.eternalstarlight.common.entity.living.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class LookAtTargetGoal extends Goal {
   private final Mob mob;

   public LookAtTargetGoal(Mob mob) {
      this.setFlags(EnumSet.of(Flag.LOOK));
      this.mob = mob;
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
      }
   }
}
