package com.iafenvoy.uranus.object.entity.pathfinding.raycoms.pathjobs;

import com.iafenvoy.uranus.object.entity.pathfinding.raycoms.MNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;

public class PathJobMoveToLocation extends AbstractPathJob {
   private static final float DESTINATION_SLACK_NONE = 0.1F;
   private static final float DESTINATION_SLACK_ADJACENT = (float)Math.sqrt(2.0);
   private final BlockPos destination;
   private float destinationSlack = 0.1F;

   public PathJobMoveToLocation(Level world, BlockPos start, BlockPos end, int range, LivingEntity entity) {
      super(world, start, end, range, entity);
      this.destination = new BlockPos(end);
   }

   @Override
   protected Path search() {
      if (this.getGroundHeight(null, this.destination) != this.destination.getY()) {
         this.destinationSlack = DESTINATION_SLACK_ADJACENT;
      }

      return super.search();
   }

   @Override
   protected BlockPos getPathTargetPos(MNode finalMNode) {
      return this.destination;
   }

   @Override
   protected double computeHeuristic(BlockPos pos) {
      return Math.sqrt(this.destination.distSqr(pos));
   }

   @Override
   protected boolean isAtDestination(MNode n) {
      if (!(this.destinationSlack <= 0.1F)) {
         return n.pos.getY() == this.destination.getY() - 1
            ? this.destination.closerThan(new Vec3i(n.pos.getX(), this.destination.getY(), n.pos.getZ()), DESTINATION_SLACK_ADJACENT)
            : this.destination.closerThan(n.pos, DESTINATION_SLACK_ADJACENT);
      } else {
         return n.pos.getX() == this.destination.getX() && n.pos.getY() == this.destination.getY() && n.pos.getZ() == this.destination.getZ();
      }
   }

   @Override
   protected double getNodeResultScore(MNode n) {
      return this.destination.distSqr(n.pos);
   }
}
