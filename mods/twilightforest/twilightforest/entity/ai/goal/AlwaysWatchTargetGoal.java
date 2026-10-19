package twilightforest.entity.ai.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class AlwaysWatchTargetGoal extends Goal {
   private final Mob mob;

   public AlwaysWatchTargetGoal(Mob mob) {
      this.mob = mob;
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public boolean canUse() {
      return this.mob.getTarget() != null;
   }

   public void tick() {
      super.tick();
      if (this.mob.getTarget() != null) {
         this.mob.getLookControl().setLookAt(this.mob.getTarget(), 100.0F, 100.0F);
      }
   }
}
