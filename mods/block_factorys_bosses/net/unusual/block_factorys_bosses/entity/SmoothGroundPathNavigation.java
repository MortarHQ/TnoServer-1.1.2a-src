package net.unusual.block_factorys_bosses.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SmoothGroundPathNavigation extends GroundPathNavigation {
   public SmoothGroundPathNavigation(Mob mob, Level level) {
      super(mob, level);
   }

   protected void followThePath() {
      Vec3 currentPos = this.getTempMobPos();
      if (this.path != null) {
         int nextIndex = this.path.getNextNodeIndex();
         int maxIndex = this.path.getNodeCount();
         if (nextIndex < maxIndex) {
            Node nextNode = this.path.getNode(nextIndex);
            Vec3 nextNodePos = new Vec3(nextNode.x + 0.5, nextNode.y, nextNode.z + 0.5);
            double dx = nextNodePos.x - currentPos.x;
            double dz = nextNodePos.z - currentPos.z;
            double distance = dx * dx + dz * dz;
            boolean advanced = false;
            if (distance < 0.25) {
               this.path.advance();
               advanced = true;
            }

            double targetAngle = Math.atan2(dz, dx) * 180.0F / (float)Math.PI - 90.0;
            double currentAngle = this.mob.getYRot();
            double angleDiff = Mth.wrapDegrees(targetAngle - currentAngle);
            double maxTurn = 6.0;
            double clampedTurn = Mth.clamp(angleDiff, -maxTurn, maxTurn);
            this.mob.setYRot((float)(currentAngle + clampedTurn));
            this.mob.yBodyRot = this.mob.getYRot();
            this.mob.yHeadRot = this.mob.getYRot();
            this.maxDistanceToWaypoint = this.mob.getBbWidth() > 0.75F ? this.mob.getBbWidth() / 2.0F : 0.75F - this.mob.getBbWidth() / 2.0F;
            if (!advanced && (this.isFlag(this.path) || this.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(currentPos))) {
               this.path.advance();
            }

            this.doStuckDetection(currentPos);
         }
      }
   }

   private boolean isFlag(Path path) {
      Vec3i nextPos = path.getNextNodePos();
      double x = Math.abs(this.mob.getX() - (nextPos.getX() + (int)(this.mob.getBbWidth() + 1.0F) / 2.0));
      double y = Math.abs(this.mob.getY() - nextPos.getY());
      double z = Math.abs(this.mob.getZ() - (nextPos.getZ() + (int)(this.mob.getBbWidth() + 1.0F) / 2.0));
      return x <= this.maxDistanceToWaypoint && z <= this.maxDistanceToWaypoint && y < 1.0;
   }
}
