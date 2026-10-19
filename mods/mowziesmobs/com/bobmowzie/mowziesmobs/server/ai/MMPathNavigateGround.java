package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class MMPathNavigateGround extends GroundPathNavigation {
   static final float EPSILON = 1.0E-8F;

   public MMPathNavigateGround(MowzieEntity entity, Level world) {
      super(entity, world);
   }

   protected PathFinder createPathFinder(int maxVisitedNodes) {
      this.nodeEvaluator = new MMWalkNodeProcessor();
      this.nodeEvaluator.setCanPassDoors(true);
      return new MMPathFinder(this.nodeEvaluator, maxVisitedNodes);
   }

   protected void followThePath() {
      Path path = Objects.requireNonNull(this.path);
      Vec3 entityPos = this.getTempMobPos();
      int pathLength = path.getNodeCount();

      for (int i = path.getNextNodeIndex(); i < path.getNodeCount(); i++) {
         if (path.getNode(i).y != Math.floor(entityPos.y)) {
            pathLength = i;
            break;
         }
      }

      Vec3 base = entityPos.add(-this.mob.getBbWidth() * 0.5F, 0.0, -this.mob.getBbWidth() * 0.5F);
      Vec3 max = base.add(this.mob.getBbWidth(), this.mob.getBbHeight(), this.mob.getBbWidth());
      if (this.tryShortcut(path, new Vec3(this.mob.getX(), this.mob.getY(), this.mob.getZ()), pathLength, base, max)
         && (this.isAt(path, 0.5F) || this.atElevationChange(path) && this.isAt(path, this.mob.getBbWidth() * 0.5F))) {
         path.setNextNodeIndex(path.getNextNodeIndex() + 1);
      }

      this.doStuckDetection(entityPos);
   }

   private boolean isAt(Path path, float threshold) {
      Vec3 pathPos = path.getNextEntityPos(this.mob);
      return Mth.abs((float)(this.mob.getX() - pathPos.x)) < threshold
         && Mth.abs((float)(this.mob.getZ() - pathPos.z)) < threshold
         && Math.abs(this.mob.getY() - pathPos.y) < 1.0;
   }

   private boolean atElevationChange(Path path) {
      int curr = path.getNextNodeIndex();
      int end = Math.min(path.getNodeCount(), curr + Mth.ceil(this.mob.getBbWidth() * 0.5F) + 1);
      int currY = path.getNode(curr).y;

      for (int i = curr + 1; i < end; i++) {
         if (path.getNode(i).y != currY) {
            return true;
         }
      }

      return false;
   }

   private boolean tryShortcut(Path path, Vec3 entityPos, int pathLength, Vec3 base, Vec3 max) {
      int i = pathLength;

      while (--i > path.getNextNodeIndex()) {
         Vec3 vec = path.getEntityPosAtNode(this.mob, i).subtract(entityPos);
         if (this.sweep(vec, base, max)) {
            path.setNextNodeIndex(i);
            return false;
         }
      }

      return true;
   }

   private boolean sweep(Vec3 vec, Vec3 base, Vec3 max) {
      float t = 0.0F;
      float max_t = (float)vec.length();
      if (max_t < 1.0E-8F) {
         return true;
      }

      float[] tr = new float[3];
      int[] ldi = new int[3];
      int[] tri = new int[3];
      int[] step = new int[3];
      float[] tDelta = new float[3];
      float[] tNext = new float[3];
      float[] normed = new float[3];

      for (int i = 0; i < 3; i++) {
         float value = element(vec, i);
         boolean dir = value >= 0.0F;
         step[i] = dir ? 1 : -1;
         float lead = element(dir ? max : base, i);
         tr[i] = element(dir ? base : max, i);
         ldi[i] = leadEdgeToInt(lead, step[i]);
         tri[i] = trailEdgeToInt(tr[i], step[i]);
         normed[i] = value / max_t;
         tDelta[i] = Mth.abs(max_t / value);
         float dist = dir ? ldi[i] + 1 - lead : lead - ldi[i];
         tNext[i] = tDelta[i] < Float.POSITIVE_INFINITY ? tDelta[i] * dist : Float.POSITIVE_INFINITY;
      }

      MutableBlockPos pos = new MutableBlockPos();

      do {
         int axis = tNext[0] < tNext[1] ? (tNext[0] < tNext[2] ? 0 : 2) : (tNext[1] < tNext[2] ? 1 : 2);
         float dt = tNext[axis] - t;
         t = tNext[axis];
         ldi[axis] += step[axis];
         tNext[axis] += tDelta[axis];

         for (int i = 0; i < 3; i++) {
            tr[i] += dt * normed[i];
            tri[i] = trailEdgeToInt(tr[i], step[i]);
         }

         int stepx = step[0];
         int x0 = axis == 0 ? ldi[0] : tri[0];
         int x1 = ldi[0] + stepx;
         int stepy = step[1];
         int y0 = axis == 1 ? ldi[1] : tri[1];
         int y1 = ldi[1] + stepy;
         int stepz = step[2];
         int z0 = axis == 2 ? ldi[2] : tri[2];
         int z1 = ldi[2] + stepz;

         for (int x = x0; x != x1; x += stepx) {
            int z = z0;

            while (z != z1) {
               for (int y = y0; y != y1; y += stepy) {
                  BlockState block = this.level.getBlockState(pos.set(x, y, z));
                  if (!block.isPathfindable(PathComputationType.LAND)) {
                     return false;
                  }
               }

               PathType below = this.nodeEvaluator.getPathType(this.mob, BlockPos.containing(x, y0 - 1, z));
               if (below != PathType.WATER && below != PathType.LAVA && below != PathType.OPEN) {
                  PathType in = this.nodeEvaluator.getPathType(this.mob, BlockPos.containing(x, y0, z));
                  float priority = this.mob.getPathfindingMalus(in);
                  if (!(priority < 0.0F) && !(priority >= 8.0F)) {
                     if (in != PathType.DAMAGE_FIRE && in != PathType.DANGER_FIRE && in != PathType.DAMAGE_OTHER) {
                        z += stepz;
                        continue;
                     }

                     return false;
                  }

                  return false;
               }

               return false;
            }
         }
      } while (t <= max_t);

      return true;
   }

   static int leadEdgeToInt(float coord, int step) {
      return Mth.floor(coord - step * 1.0E-8F);
   }

   static int trailEdgeToInt(float coord, int step) {
      return Mth.floor(coord + step * 1.0E-8F);
   }

   static float element(Vec3 v, int i) {
      switch (i) {
         case 0:
            return (float)v.x;
         case 1:
            return (float)v.y;
         case 2:
            return (float)v.z;
         default:
            return 0.0F;
      }
   }
}
