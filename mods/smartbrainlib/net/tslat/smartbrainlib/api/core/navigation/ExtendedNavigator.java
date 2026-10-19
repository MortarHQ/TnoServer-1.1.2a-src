package net.tslat.smartbrainlib.api.core.navigation;

import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface ExtendedNavigator {
   float EPSILON = 1.0E-8F;

   Mob getMob();

   Path getPath();

   default boolean canPathOnto(PathType pathType) {
      return switch (pathType) {
         case WATER, LAVA, OPEN -> false;
         default -> true;
      };
   }

   default boolean canPathInto(PathType pathType) {
      return switch (pathType) {
         case DAMAGE_FIRE, DANGER_FIRE, DAMAGE_OTHER -> true;
         default -> false;
      };
   }

   default boolean isCloseToNextNode(float distance) {
      Mob mob = this.getMob();
      Path path = this.getPath();
      Vec3 nextNodePos = this.getEntityPosAtNode(path.getNextNodeIndex());
      return Math.abs(mob.getX() - nextNodePos.x) < distance && Math.abs(mob.getZ() - nextNodePos.z) < distance && Math.abs(mob.getY() - nextNodePos.y) < 1.0;
   }

   default boolean isAboutToTraverseVertically() {
      Mob mob = this.getMob();
      Path path = this.getPath();
      int fromNode = path.getNextNodeIndex();
      int fromNodeHeight = path.getNode(fromNode).y;
      int toNode = Math.min(path.getNodeCount(), fromNode + Mth.ceil(mob.getBbWidth() * 0.5) + 1);

      for (int i = fromNode + 1; i < toNode; i++) {
         if (path.getNode(i).y != fromNodeHeight) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   default Path patchPath(@Nullable Path path) {
      return path == null ? null : new Path(path.nodes, path.getTarget(), path.canReach()) {
         public Vec3 getEntityPosAtNode(Entity entity, int nodeIndex) {
            return ExtendedNavigator.this.getEntityPosAtNode(nodeIndex);
         }
      };
   }

   default PathFinder createSmoothPathFinder(NodeEvaluator nodeEvaluator, int maxVisitedNodes) {
      return new PathFinder(nodeEvaluator, maxVisitedNodes) {
         @Nullable
         public Path findPath(
            PathNavigationRegion navigationRegion, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier
         ) {
            return ExtendedNavigator.this.patchPath(super.findPath(navigationRegion, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier));
         }
      };
   }

   default boolean attemptShortcut(int targetNode, Vec3 safeSurfacePos) {
      Mob mob = this.getMob();
      Path path = this.getPath();
      Vec3 position = mob.position();
      Vec3 minBounds = safeSurfacePos.add(-mob.getBbWidth() * 0.5, 0.0, -mob.getBbWidth() * 0.5);
      Vec3 maxBounds = minBounds.add(mob.getBbWidth(), mob.getBbHeight(), mob.getBbWidth());

      for (int nodeIndex = targetNode - 1; nodeIndex > path.getNextNodeIndex(); nodeIndex--) {
         Vec3 nodeDelta = this.getEntityPosAtNode(nodeIndex).subtract(position);
         if (this.isCollisionFreeTraversal(nodeDelta, minBounds, maxBounds)) {
            path.setNextNodeIndex(nodeIndex);
            return true;
         }
      }

      return false;
   }

   default Vec3 getEntityPosAtNode(int nodeIndex) {
      Mob mob = this.getMob();
      Path path = this.getPath();
      double lateralOffset = Mth.floor(mob.getBbWidth() + 1.0) / 2.0;
      return Vec3.atLowerCornerOf(path.getNodePos(nodeIndex)).add(lateralOffset, 0.0, lateralOffset);
   }

   default boolean isCollisionFreeTraversal(Vec3 traversalVector, Vec3 minBoundsPos, Vec3 leadingEdgePos) {
      float traversalDistance = (float)traversalVector.length();
      if (traversalDistance < 1.0E-8F) {
         return true;
      }

      ExtendedNavigator.VoxelRayDetails ray = new ExtendedNavigator.VoxelRayDetails();

      for (Axis axis : Axis.values()) {
         int index = axis.ordinal();
         float axisLength = this.lengthForAxis(traversalVector, axis);
         boolean isPositive = axisLength >= 0.0F;
         float maxPos = this.lengthForAxis(isPositive ? leadingEdgePos : minBoundsPos, axis);
         ray.absStep[index] = isPositive ? 1 : -1;
         ray.minPos[index] = this.lengthForAxis(isPositive ? minBoundsPos : leadingEdgePos, axis);
         ray.leadingEdgeBound[index] = Mth.floor(maxPos - ray.absStep[index] * 1.0E-8F);
         ray.trailingEdgeBound[index] = Mth.floor(ray.minPos[index] + ray.absStep[index] * 1.0E-8F);
         ray.axisLengthNormalised[index] = axisLength / traversalDistance;
         ray.axisSteps[index] = Mth.abs(traversalDistance / axisLength);
         float dist = isPositive ? ray.leadingEdgeBound[index] + 1 - maxPos : maxPos - ray.leadingEdgeBound[index];
         ray.rayTargetLength[index] = ray.axisSteps[index] < Float.POSITIVE_INFINITY ? ray.axisSteps[index] * dist : Float.POSITIVE_INFINITY;
      }

      return this.collidesWhileTraversing(ray, traversalDistance);
   }

   default boolean collidesWhileTraversing(ExtendedNavigator.VoxelRayDetails ray, float traversalDistance) {
      Mob mob = this.getMob();
      Level level = mob.level();
      BulkSectionAccess sectionAccess = new BulkSectionAccess(level);

      boolean pathMalus;
      label123: {
         boolean var34;
         label122: {
            boolean var36;
            label121: {
               label120: {
                  try {
                     NodeEvaluator nodeEvaluator = mob.getNavigation().getNodeEvaluator();
                     MutableBlockPos pos = new MutableBlockPos();
                     float target = 0.0F;

                     do {
                        Axis longestEdge = ray.rayTargetLength[0] < ray.rayTargetLength[1]
                           ? (ray.rayTargetLength[0] < ray.rayTargetLength[2] ? Axis.X : Axis.Z)
                           : (ray.rayTargetLength[1] < ray.rayTargetLength[2] ? Axis.Y : Axis.Z);
                        int index = longestEdge.ordinal();
                        float rayDelta = ray.rayTargetLength[index] - target;
                        target = ray.rayTargetLength[index];
                        ray.leadingEdgeBound[index] = ray.leadingEdgeBound[index] + ray.absStep[index];
                        ray.rayTargetLength[index] = ray.rayTargetLength[index] + ray.axisSteps[index];

                        for (Axis axis : Axis.values()) {
                           int index2 = axis.ordinal();
                           ray.minPos[index2] = ray.minPos[index2] + rayDelta * ray.axisLengthNormalised[index2];
                           ray.trailingEdgeBound[index2] = Mth.floor(ray.minPos[index2] + ray.absStep[index2] * 1.0E-8F);
                        }

                        int xStep = ray.absStep[0];
                        int yStep = ray.absStep[1];
                        int zStep = ray.absStep[2];
                        int xBound = longestEdge == Axis.X ? ray.leadingEdgeBound[0] : ray.trailingEdgeBound[0];
                        int yBound = longestEdge == Axis.Y ? ray.leadingEdgeBound[1] : ray.trailingEdgeBound[1];
                        int zBound = longestEdge == Axis.Z ? ray.leadingEdgeBound[2] : ray.trailingEdgeBound[2];
                        int xStepBound = ray.leadingEdgeBound[0] + xStep;
                        int yStepBound = ray.leadingEdgeBound[1] + yStep;
                        int zStepBound = ray.leadingEdgeBound[2] + zStep;

                        for (int x = xBound; x != xStepBound; x += xStep) {
                           for (int z = zBound; z != zStepBound; z += zStep) {
                              for (int y = yBound; y != yStepBound; y += yStep) {
                                 if (!sectionAccess.getBlockState(pos.set(x, y, z)).isPathfindable(PathComputationType.LAND)) {
                                    pathMalus = false;
                                    break label123;
                                 }
                              }

                              if (!this.canPathOnto(nodeEvaluator.getPathType(new PathfindingContext(level, mob), x, yBound - 1, z))) {
                                 var34 = false;
                                 break label122;
                              }

                              PathType insidePathType = nodeEvaluator.getPathType(new PathfindingContext(level, mob), x, yBound, z);
                              float pathMalusx = mob.getPathfindingMalus(insidePathType);
                              if (pathMalusx < 0.0F || pathMalusx >= 8.0F) {
                                 var36 = false;
                                 break label121;
                              }

                              if (this.canPathInto(insidePathType)) {
                                 var36 = false;
                                 break label120;
                              }
                           }
                        }
                     } while (target <= traversalDistance);
                  } catch (Throwable var27) {
                     try {
                        sectionAccess.close();
                     } catch (Throwable var26) {
                        var27.addSuppressed(var26);
                     }

                     throw var27;
                  }

                  sectionAccess.close();
                  return true;
               }

               sectionAccess.close();
               return var36;
            }

            sectionAccess.close();
            return var36;
         }

         sectionAccess.close();
         return var34;
      }

      sectionAccess.close();
      return pathMalus;
   }

   default float lengthForAxis(Vec3 vector, Axis axis) {
      return (float)axis.choose(vector.x, vector.y, vector.z);
   }

   record VoxelRayDetails(
      float[] minPos, int[] leadingEdgeBound, int[] trailingEdgeBound, int[] absStep, float[] axisSteps, float[] rayTargetLength, float[] axisLengthNormalised
   ) {
      public VoxelRayDetails() {
         this(new float[3], new int[3], new int[3], new int[3], new float[3], new float[3], new float[3]);
      }
   }
}
