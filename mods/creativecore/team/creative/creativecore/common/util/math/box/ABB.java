package team.creative.creativecore.common.util.math.box;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.creative.creativecore.common.util.math.base.Facing;
import team.creative.creativecore.common.util.math.collision.CollisionCoordinator;
import team.creative.creativecore.common.util.math.matrix.IVecOrigin;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public class ABB {
   public double minX;
   public double minY;
   public double minZ;
   public double maxX;
   public double maxY;
   public double maxZ;

   public static double min(AABB bb, Axis axis) {
      return switch (axis) {
         case X -> bb.minX;
         case Y -> bb.minY;
         case Z -> bb.minZ;
         default -> throw new MatchException(null, null);
      };
   }

   public static double max(AABB bb, Axis axis) {
      return switch (axis) {
         case X -> bb.maxX;
         case Y -> bb.maxY;
         case Z -> bb.maxZ;
         default -> throw new MatchException(null, null);
      };
   }

   public static double min(AABB bb, team.creative.creativecore.common.util.math.base.Axis axis) {
      return switch (axis) {
         case X -> bb.minX;
         case Y -> bb.minY;
         case Z -> bb.minZ;
      };
   }

   public static double max(AABB bb, team.creative.creativecore.common.util.math.base.Axis axis) {
      return switch (axis) {
         case X -> bb.maxX;
         case Y -> bb.maxY;
         case Z -> bb.maxZ;
      };
   }

   public double get(AABB bb, Facing facing) {
      return switch (facing) {
         case EAST -> bb.maxX;
         case WEST -> bb.minX;
         case UP -> bb.maxY;
         case DOWN -> bb.minY;
         case SOUTH -> bb.maxZ;
         case NORTH -> bb.minZ;
      };
   }

   public static ABB createEmptyBox() {
      return new ABB(
         Double.POSITIVE_INFINITY,
         Double.POSITIVE_INFINITY,
         Double.POSITIVE_INFINITY,
         Double.NEGATIVE_INFINITY,
         Double.NEGATIVE_INFINITY,
         Double.NEGATIVE_INFINITY
      );
   }

   @Nullable
   public static BlockHitResult clip(Iterable<ABB> boxes, Vec3 pos, Vec3 look, BlockPos blockPos) {
      BlockHitResult hit = null;
      double distance = Double.POSITIVE_INFINITY;

      for (ABB box : boxes) {
         BlockHitResult temp = box.rayTrace(pos, look, blockPos);
         if (temp != null) {
            double tempDistance = pos.distanceToSqr(temp.getLocation());
            if (tempDistance < distance) {
               hit = temp;
               distance = tempDistance;
            }
         }
      }

      return hit;
   }

   public ABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      this.minX = minX;
      this.minY = minY;
      this.minZ = minZ;
      this.maxX = maxX;
      this.maxY = maxY;
      this.maxZ = maxZ;
   }

   public ABB(AABB bb) {
      this(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
   }

   public ABB(ABB bb) {
      this(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
   }

   public double get(Facing facing) {
      return switch (facing) {
         case EAST -> this.maxX;
         case WEST -> this.minX;
         case UP -> this.maxY;
         case DOWN -> this.minY;
         case SOUTH -> this.maxZ;
         case NORTH -> this.minZ;
      };
   }

   public double min(Axis axis) {
      return switch (axis) {
         case X -> this.minX;
         case Y -> this.minY;
         case Z -> this.minZ;
         default -> throw new MatchException(null, null);
      };
   }

   public double max(Axis axis) {
      return switch (axis) {
         case X -> this.maxX;
         case Y -> this.maxY;
         case Z -> this.maxZ;
         default -> throw new MatchException(null, null);
      };
   }

   public double min(team.creative.creativecore.common.util.math.base.Axis axis) {
      return switch (axis) {
         case X -> this.minX;
         case Y -> this.minY;
         case Z -> this.minZ;
      };
   }

   public double max(team.creative.creativecore.common.util.math.base.Axis axis) {
      return switch (axis) {
         case X -> this.maxX;
         case Y -> this.maxY;
         case Z -> this.maxZ;
      };
   }

   public Vec3d corner(BoxCorner corner) {
      return new Vec3d(this.cornerX(corner), this.cornerY(corner), this.cornerZ(corner));
   }

   public double cornerValue(AABB bb, BoxCorner corner, team.creative.creativecore.common.util.math.base.Axis axis) {
      return this.get(corner.getFacing(axis));
   }

   public double cornerX(BoxCorner corner) {
      return this.get(corner.x);
   }

   public double cornerY(BoxCorner corner) {
      return this.get(corner.y);
   }

   public double cornerZ(BoxCorner corner) {
      return this.get(corner.z);
   }

   public boolean intersectsWithAxis(team.creative.creativecore.common.util.math.base.Axis axis, AABB bb2) {
      return switch (axis) {
         case X -> this.minY < bb2.maxY && this.maxY > bb2.minY && this.minZ < bb2.maxZ && this.maxZ > bb2.minZ;
         case Y -> this.minX < bb2.maxX && this.maxX > bb2.minX && this.minZ < bb2.maxZ && this.maxZ > bb2.minZ;
         case Z -> this.minX < bb2.maxX && this.maxX > bb2.minX && this.minY < bb2.maxY && this.maxY > bb2.minY;
      };
   }

   public boolean intersectsWithAxis(team.creative.creativecore.common.util.math.base.Axis axis, ABB bb2) {
      return switch (axis) {
         case X -> this.minY < bb2.maxY && this.maxY > bb2.minY && this.minZ < bb2.maxZ && this.maxZ > bb2.minZ;
         case Y -> this.minX < bb2.maxX && this.maxX > bb2.minX && this.minZ < bb2.maxZ && this.maxZ > bb2.minZ;
         case Z -> this.minX < bb2.maxX && this.maxX > bb2.minX && this.minY < bb2.maxY && this.maxY > bb2.minY;
      };
   }

   public boolean intersectsWithAxis(
      team.creative.creativecore.common.util.math.base.Axis one, team.creative.creativecore.common.util.math.base.Axis two, double valueOne, double valueTwo
   ) {
      return this.min(one) < valueOne && this.max(one) > valueOne && this.min(two) < valueTwo && this.max(two) > valueTwo;
   }

   public double calculateAxisOffset(
      team.creative.creativecore.common.util.math.base.Axis axis,
      team.creative.creativecore.common.util.math.base.Axis one,
      team.creative.creativecore.common.util.math.base.Axis two,
      AABB other,
      double offset
   ) {
      if (this.intersectsWithAxis(axis, other)) {
         if (offset > 0.0 && max(other, axis) <= this.min(axis)) {
            double newDistance = this.min(axis) - max(other, axis);
            if (newDistance < offset) {
               return newDistance;
            }
         } else if (offset < 0.0 && min(other, axis) >= this.max(axis)) {
            double newDistance = this.max(axis) - min(other, axis);
            if (newDistance > offset) {
               return newDistance;
            }
         }
      }

      return offset;
   }

   public ABB copy() {
      return new ABB(this);
   }

   public void move(double x, double y, double z) {
      this.minX += x;
      this.minY += y;
      this.minZ += z;
      this.maxX += x;
      this.maxY += y;
      this.maxZ += z;
   }

   public ABB moveCopy(double x, double y, double z) {
      ABB bb = this.copy();
      bb.move(x, y, z);
      return bb;
   }

   public Vec3d[] getCorners() {
      Vec3d[] corners = new Vec3d[BoxCorner.values().length];

      for (int i = 0; i < corners.length; i++) {
         corners[i] = this.corner(BoxCorner.values()[i]);
      }

      return corners;
   }

   public Vec3d[] getRotatedCorners(IVecOrigin origin) {
      Vec3d[] corners = this.getCorners();

      for (int i = 0; i < corners.length; i++) {
         origin.transformPointToWorld(corners[i]);
      }

      return corners;
   }

   public Vec3d[] getOuterCorner(Facing facing, IVecOrigin origin, double minOne, double minTwo, double maxOne, double maxTwo) {
      Vec3d[] corners = this.getCorners();
      double value = 0.0;
      BoxCorner selected = null;
      team.creative.creativecore.common.util.math.base.Axis axis = facing.axis;

      for (int i = 0; i < corners.length; i++) {
         Vec3d vec = corners[i];
         origin.transformPointToWorld(vec);
         double vectorValue = vec.get(axis);
         if (selected == null || (facing.positive ? vectorValue > value : vectorValue < value)) {
            selected = BoxCorner.values()[i];
            value = vectorValue;
         }
      }

      return new Vec3d[]{
         corners[selected.ordinal()],
         corners[selected.neighborOne.ordinal()],
         corners[selected.neighborTwo.ordinal()],
         corners[selected.neighborThree.ordinal()]
      };
   }

   public void include(Vec3d vec) {
      this.minX = Math.min(this.minX, vec.x);
      this.minY = Math.min(this.minY, vec.y);
      this.minZ = Math.min(this.minZ, vec.z);
      this.maxX = Math.max(this.maxX, vec.x);
      this.maxY = Math.max(this.maxY, vec.y);
      this.maxZ = Math.max(this.maxZ, vec.z);
   }

   public void include(Facing facing, double value) {
      switch (facing) {
         case EAST:
            this.maxX = Math.max(this.maxX, value);
            break;
         case WEST:
            this.minX = Math.min(this.minX, value);
            break;
         case UP:
            this.maxY = Math.max(this.maxY, value);
            break;
         case DOWN:
            this.minY = Math.min(this.minY, value);
            break;
         case SOUTH:
            this.maxZ = Math.max(this.maxZ, value);
            break;
         case NORTH:
            this.minZ = Math.min(this.minZ, value);
      }
   }

   public AABB toVanilla() {
      return new AABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
   }

   public BlockHitResult rayTrace(Vec3 pos, Vec3 look, BlockPos blockPos) {
      double[] time = new double[]{1.0};
      double x = look.x - pos.x;
      double y = look.y - pos.y;
      double z = look.z - pos.z;
      Facing facing = this.clipFacing(pos, time, null, x, y, z, blockPos);
      return facing == null ? null : new BlockHitResult(pos.add(x * time[0], y * time[0], z * time[0]), facing.toVanilla(), blockPos, false);
   }

   private Facing clipFacing(Vec3 pos, double[] time, Facing facing, double x, double y, double z, BlockPos blockPos) {
      for (int i = 0; i < Facing.VALUES.length; i++) {
         Facing toClip = Facing.VALUES[i];
         if (toClip.positive ? toClip.axis.get(x, y, z) < 1.0E-7 : toClip.axis.get(x, y, z) > 1.0E-7) {
            facing = this.clipPoint(time, facing, toClip, x, y, z, pos, blockPos);
         }
      }

      return facing;
   }

   private Facing clipPoint(double[] time, Facing original, Facing toClip, double x, double y, double z, Vec3 pos, BlockPos blockPos) {
      double d0 = (this.get(toClip) + blockPos.get(toClip.axis.toVanilla()) - pos.get(toClip.axis.toVanilla())) / toClip.axis.get(x, y, z);
      double d1 = pos.get(toClip.one().toVanilla()) + d0 * toClip.one().get(x, y, z);
      double d2 = pos.get(toClip.two().toVanilla()) + d0 * toClip.two().get(x, y, z);
      if (0.0 < d0
         && d0 < time[0]
         && this.min(toClip.one()) + blockPos.get(toClip.one().toVanilla()) - 1.0E-7 < d1
         && d1 < this.max(toClip.one()) + blockPos.get(toClip.one().toVanilla()) + 1.0E-7
         && this.min(toClip.two()) + blockPos.get(toClip.two().toVanilla()) - 1.0E-7 < d2
         && d2 < this.max(toClip.two()) + blockPos.get(toClip.two().toVanilla()) + 1.0E-7) {
         time[0] = d0;
         return toClip;
      } else {
         return original;
      }
   }

   public ABB createRotatedSurrounding(CollisionCoordinator coordinator) {
      Vec3d[] corners = this.getRotatedCorners(coordinator.original());
      ABB bb = createEmptyBox();

      for (int i = 0; i < corners.length; i++) {
         Vec3d vec = corners[i];
         bb.include(vec);
         if (coordinator.hasOnlyTranslation()) {
            vec.add(coordinator.translation);
            bb.include(vec);
         } else {
            BoxUtils.includeMaxRotationInBox(bb, new Vec3d(vec), team.creative.creativecore.common.util.math.base.Axis.X, coordinator);
            BoxUtils.includeMaxRotationInBox(bb, new Vec3d(vec), team.creative.creativecore.common.util.math.base.Axis.Y, coordinator);
            BoxUtils.includeMaxRotationInBox(bb, new Vec3d(vec), team.creative.creativecore.common.util.math.base.Axis.Z, coordinator);
            coordinator.transform(vec, 1.0);
            bb.include(vec);
         }
      }

      return bb;
   }

   public ABB createRotatedSurroundingInverseInternal(CollisionCoordinator coordinator) {
      Vec3d[] corners = this.getCorners();
      ABB bb = createEmptyBox();

      for (int i = 0; i < corners.length; i++) {
         Vec3d vec = corners[i];
         bb.include(vec);
         if (coordinator.hasOnlyTranslation()) {
            vec.sub(coordinator.translation);
            bb.include(vec);
         } else {
            BoxUtils.includeMaxRotationInBoxInverse(bb, new Vec3d(vec), team.creative.creativecore.common.util.math.base.Axis.X, coordinator);
            BoxUtils.includeMaxRotationInBoxInverse(bb, new Vec3d(vec), team.creative.creativecore.common.util.math.base.Axis.Y, coordinator);
            BoxUtils.includeMaxRotationInBoxInverse(bb, new Vec3d(vec), team.creative.creativecore.common.util.math.base.Axis.Z, coordinator);
            coordinator.transformInverted(vec, 1.0);
            bb.include(vec);
         }
      }

      return bb;
   }

   public boolean contains(Vec3d vec) {
      return this.contains(vec.x, vec.y, vec.z);
   }

   public boolean contains(Vec3 vec) {
      return this.contains(vec.x, vec.y, vec.z);
   }

   public boolean intersectsPrecise(AABB bb) {
      return this.intersects(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
   }

   public boolean intersects(AABB bb) {
      return this.intersects(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
   }

   public boolean intersects(ABB bb) {
      return this.intersects(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
   }

   public boolean intersects(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      return this.minX < maxX && this.maxX > minX && this.minY < maxY && this.maxY > minY && this.minZ < maxZ && this.maxZ > minZ;
   }

   public boolean intersects(Vec3 vec1, Vec3 vec2) {
      return this.intersects(
         Math.min(vec1.x, vec2.x),
         Math.min(vec1.y, vec2.y),
         Math.min(vec1.z, vec2.z),
         Math.max(vec1.x, vec2.x),
         Math.max(vec1.y, vec2.y),
         Math.max(vec1.z, vec2.z)
      );
   }

   public boolean contains(double x, double y, double z) {
      return x >= this.minX && x < this.maxX && y >= this.minY && y < this.maxY && z >= this.minZ && z < this.maxZ;
   }

   public Vec3 getCenter() {
      return new Vec3(Mth.lerp(0.5, this.minX, this.maxX), Mth.lerp(0.5, this.minY, this.maxY), Mth.lerp(0.5, this.minZ, this.maxZ));
   }

   @Override
   public String toString() {
      return "ABB[" + this.minX + ", " + this.minY + ", " + this.minZ + "] -> [" + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
   }

   public boolean equals(AABB bb) {
      return this.minX == bb.minX && this.minY == bb.minY && this.minZ == bb.minZ && this.maxX == bb.maxX && this.maxY == bb.maxY && this.maxZ == bb.maxZ;
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof ABB bb)
         ? false
         : this.minX == bb.minX && this.minY == bb.minY && this.minZ == bb.minZ && this.maxX == bb.maxX && this.maxY == bb.maxY && this.maxZ == bb.maxZ;
   }

   protected ABB combine(ABB box) {
      boolean x = this.minX == box.minX && this.maxX == box.maxX;
      boolean y = this.minY == box.minY && this.maxY == box.maxY;
      boolean z = this.minZ == box.minZ && this.maxZ == box.maxZ;
      if (x && y && z) {
         return this;
      }

      if (x && y) {
         if (this.minZ == box.maxZ) {
            return new ABB(this.minX, this.minY, box.minZ, this.maxX, this.maxY, this.maxZ);
         }

         if (this.maxZ == box.minZ) {
            return new ABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, box.maxZ);
         }
      }

      if (x && z) {
         if (this.minY == box.maxY) {
            return new ABB(this.minX, box.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
         }

         if (this.maxY == box.minY) {
            return new ABB(this.minX, this.minY, this.minZ, this.maxX, box.maxY, this.maxZ);
         }
      }

      if (y && z) {
         if (this.minX == box.maxX) {
            return new ABB(box.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
         }

         if (this.maxX == box.minX) {
            return new ABB(this.minX, this.minY, this.minZ, box.maxX, this.maxY, this.maxZ);
         }
      }

      return null;
   }
}
