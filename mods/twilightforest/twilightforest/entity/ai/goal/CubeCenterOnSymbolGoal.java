package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import twilightforest.entity.RovingCube;

public class CubeCenterOnSymbolGoal extends Goal {
   private final RovingCube myCube;
   private final double speed;
   private double xPosition;
   private double yPosition;
   private double zPosition;

   public CubeCenterOnSymbolGoal(RovingCube cube, double d) {
      this.myCube = cube;
      this.xPosition = this.myCube.symbolX;
      this.yPosition = this.myCube.symbolY;
      this.zPosition = this.myCube.symbolZ;
      this.speed = d;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      this.xPosition = this.myCube.symbolX;
      this.yPosition = this.myCube.symbolY;
      this.zPosition = this.myCube.symbolZ;
      return !this.myCube.getNavigation().isDone() ? false : this.isCloseToSymbol();
   }

   public boolean canContinueToUse() {
      this.myCube.getMoveControl().setWantedPosition(this.xPosition + 0.5, this.yPosition, this.zPosition + 0.5, this.speed);
      return this.distanceFromSymbol() > 0.1F && this.isCourseTraversable();
   }

   private boolean isCourseTraversable() {
      return this.distanceFromSymbol() < 100.0;
   }

   private boolean isCloseToSymbol() {
      double dist = this.distanceFromSymbol();
      return dist > 0.25 && dist < 10.0;
   }

   private double distanceFromSymbol() {
      double dx = this.xPosition - this.myCube.getX() + 0.5;
      double dy = this.yPosition - this.myCube.getY();
      double dz = this.zPosition - this.myCube.getZ() + 0.5;
      return Math.sqrt(dx * dx + dy * dy + dz * dz);
   }
}
