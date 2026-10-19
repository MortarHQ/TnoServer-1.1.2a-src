package com.iafenvoy.uranus.object.entity.pathfinding.raycoms.pathjobs;

import com.iafenvoy.uranus.object.entity.pathfinding.raycoms.MNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class PathJobMoveAwayFromLocation extends AbstractPathJob {
   protected final BlockPos avoid;
   protected final int avoidDistance;

   public PathJobMoveAwayFromLocation(Level world, BlockPos start, BlockPos avoid, int avoidDistance, int range, LivingEntity entity) {
      super(world, start, avoid, range, entity);
      this.avoid = new BlockPos(avoid);
      this.avoidDistance = avoidDistance;
   }

   @Override
   protected double computeHeuristic(BlockPos pos) {
      return -this.avoid.distSqr(pos);
   }

   @Override
   protected boolean isAtDestination(MNode n) {
      return Math.sqrt(this.avoid.distSqr(n.pos)) > this.avoidDistance;
   }

   @Override
   protected double getNodeResultScore(MNode n) {
      return -this.avoid.distSqr(n.pos);
   }
}
