package cn.leolezury.eternalstarlight.common.entity.living.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class BoarwarfDoSleepGoal extends Goal {
   private final PathfinderMob creature;

   public BoarwarfDoSleepGoal(PathfinderMob creature) {
      this.creature = creature;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      return this.creature.isSleeping();
   }
}
