package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import twilightforest.entity.monster.CarminiteGhastguard;

public class GhastguardRandomFlyGoal extends Goal {
   private final CarminiteGhastguard parentEntity;

   public GhastguardRandomFlyGoal(CarminiteGhastguard ghast) {
      this.parentEntity = ghast;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      MoveControl entitymovehelper = this.parentEntity.getMoveControl();
      if (!entitymovehelper.hasWanted()) {
         return this.parentEntity.getTarget() == null;
      }

      double d0 = entitymovehelper.getWantedX() - this.parentEntity.getX();
      double d1 = entitymovehelper.getWantedY() - this.parentEntity.getY();
      double d2 = entitymovehelper.getWantedZ() - this.parentEntity.getZ();
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return this.parentEntity.getTarget() == null && (d3 < 1.0 || d3 > 3600.0);
   }

   public boolean canContinueToUse() {
      return false;
   }

   public void start() {
      RandomSource random = this.parentEntity.getRandom();
      double d0 = this.parentEntity.getX() + (random.nextFloat() * 2.0F - 1.0F) * this.parentEntity.getWanderFactor();
      double d1 = this.parentEntity.getY() + (random.nextFloat() * 2.0F - 1.0F) * this.parentEntity.getWanderFactor();
      double d2 = this.parentEntity.getZ() + (random.nextFloat() * 2.0F - 1.0F) * this.parentEntity.getWanderFactor();
      this.parentEntity.getMoveControl().setWantedPosition(d0, d1, d2, 1.0);
   }
}
