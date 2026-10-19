package twilightforest.entity.ai.goal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import twilightforest.entity.boss.UrGhast;

public class UrGhastFlightGoal extends Goal {
   private static final int HOVER_ALTITUDE = 20;
   private final UrGhast ghast;
   private List<BlockPos> pointsToVisit = new ArrayList<>();
   private int currentPoint = 0;

   public UrGhastFlightGoal(UrGhast ghast) {
      this.ghast = ghast;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      MoveControl entitymovehelper = this.ghast.getMoveControl();
      this.pointsToVisit = this.createPath();
      if (this.pointsToVisit.isEmpty()) {
         return false;
      }

      if (!entitymovehelper.hasWanted()) {
         return true;
      }

      double d0 = entitymovehelper.getWantedX() - this.ghast.getX();
      double d1 = entitymovehelper.getWantedY() - this.ghast.getY();
      double d2 = entitymovehelper.getWantedZ() - this.ghast.getZ();
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return d3 < 1.0 || d3 > 3600.0;
   }

   public boolean canContinueToUse() {
      return false;
   }

   public void start() {
      if (this.pointsToVisit.isEmpty()) {
         this.pointsToVisit.addAll(this.createPath());
      } else {
         if (this.currentPoint >= this.pointsToVisit.size()) {
            this.currentPoint = 0;
            if (!this.ghast.checkGhastsAtTraps()) {
               this.ghast.spawnGhastsAtTraps((ServerLevel)this.ghast.level());
            }
         }

         double x = this.pointsToVisit.get(this.currentPoint).getX();
         double y = this.pointsToVisit.get(this.currentPoint).getY() + (this.ghast.getRestrictionPoint() != null ? 20 : 0);
         double z = this.pointsToVisit.get(this.currentPoint).getZ();
         this.ghast.getMoveControl().setWantedPosition(x, y, z, 1.0);
         this.currentPoint++;
         this.ghast.noPhysics = false;
      }
   }

   private List<BlockPos> createPath() {
      List<BlockPos> potentialPoints = new ArrayList<>();
      BlockPos pos = new BlockPos(this.ghast.getLogicalScanPoint());
      if (!this.ghast.getTrapLocations().isEmpty()) {
         potentialPoints.addAll(this.ghast.getTrapLocations());
      } else {
         potentialPoints.add(pos.offset(20, 0, 0));
         potentialPoints.add(pos.offset(0, 0, -20));
         potentialPoints.add(pos.offset(-20, 0, 0));
         potentialPoints.add(pos.offset(0, 0, 20));
      }

      Collections.shuffle(potentialPoints);
      if (this.ghast.getTrapLocations().isEmpty()) {
         potentialPoints.add(pos);
      }

      return potentialPoints;
   }
}
