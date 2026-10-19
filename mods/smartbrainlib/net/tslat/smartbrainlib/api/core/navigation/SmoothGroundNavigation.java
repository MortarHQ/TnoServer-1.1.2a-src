package net.tslat.smartbrainlib.api.core.navigation;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SmoothGroundNavigation extends GroundPathNavigation implements ExtendedNavigator {
   public SmoothGroundNavigation(Mob mob, Level level) {
      super(mob, level);
   }

   @Override
   public Mob getMob() {
      return this.mob;
   }

   @Nullable
   @Override
   public Path getPath() {
      return super.getPath();
   }

   protected PathFinder createPathFinder(int maxVisitedNodes) {
      this.nodeEvaluator = new WalkNodeEvaluator();
      this.nodeEvaluator.setCanPassDoors(true);
      return this.createSmoothPathFinder(this.nodeEvaluator, maxVisitedNodes);
   }

   protected void followThePath() {
      Vec3 safeSurfacePos = this.getTempMobPos();
      int shortcutNode = this.getClosestVerticalTraversal(Mth.floor(safeSurfacePos.y));
      this.maxDistanceToWaypoint = this.mob.getBbWidth() > 0.75F ? this.mob.getBbWidth() / 2.0F : 0.75F - this.mob.getBbWidth() / 2.0F;
      if (!this.attemptShortcut(shortcutNode, safeSurfacePos)
         && (this.isCloseToNextNode(0.5F) || this.isAboutToTraverseVertically() && this.isCloseToNextNode(this.getMaxDistanceToWaypoint()))) {
         this.path.advance();
      }

      this.doStuckDetection(safeSurfacePos);
   }

   public int getSurfaceY() {
      return super.getSurfaceY();
   }

   protected int getClosestVerticalTraversal(int safeSurfaceHeight) {
      int nodesLength = this.path.getNodeCount();

      for (int nodeIndex = this.path.getNextNodeIndex(); nodeIndex < nodesLength; nodeIndex++) {
         if (this.path.getNode(nodeIndex).y != safeSurfaceHeight) {
            return nodeIndex;
         }
      }

      return nodesLength;
   }
}
