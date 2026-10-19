package team.creative.creativecore.common.util.math.box;

import net.minecraft.world.phys.AABB;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.base.Facing;
import team.creative.creativecore.common.util.math.matrix.IntMatrix3c;
import team.creative.creativecore.common.util.math.transformation.Rotation;
import team.creative.creativecore.common.util.math.utils.BooleanUtils;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public enum BoxCorner {
   EUN(Facing.EAST, Facing.UP, Facing.NORTH),
   EUS(Facing.EAST, Facing.UP, Facing.SOUTH),
   EDN(Facing.EAST, Facing.DOWN, Facing.NORTH),
   EDS(Facing.EAST, Facing.DOWN, Facing.SOUTH),
   WUN(Facing.WEST, Facing.UP, Facing.NORTH),
   WUS(Facing.WEST, Facing.UP, Facing.SOUTH),
   WDN(Facing.WEST, Facing.DOWN, Facing.NORTH),
   WDS(Facing.WEST, Facing.DOWN, Facing.SOUTH);

   private static final BoxCorner[] LOOKUP = new BoxCorner[8];
   public static final BoxCorner[][] FACING_CORNERS = new BoxCorner[][]{
      {EDN, EDS, WDN, WDS}, {EUN, EUS, WUN, WUS}, {EUN, EDN, WUN, WDN}, {EUS, EDS, WUS, WDS}, {WUN, WUS, WDN, WDS}, {EUN, EUS, EDN, EDS}
   };
   public final Facing x;
   public final Facing y;
   public final Facing z;
   public BoxCorner neighborOne;
   public BoxCorner neighborTwo;
   public BoxCorner neighborThree;

   BoxCorner(Facing x, Facing y, Facing z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   private static BoxCorner lookup(Facing x, Facing y, Facing z) {
      int mask = (x.offset() > 0 ? 1 : 0) << 2 | (y.offset() > 0 ? 1 : 0) << 1 | (z.offset() > 0 ? 1 : 0);
      return LOOKUP[mask];
   }

   public static BoxCorner getCornerUnsorted(Facing a, Facing b, Facing c) {
      Facing x = a.axis == Axis.X ? a : (b.axis == Axis.X ? b : c);
      Facing y = a.axis == Axis.Y ? a : (b.axis == Axis.Y ? b : c);
      Facing z = a.axis == Axis.Z ? a : (b.axis == Axis.Z ? b : c);
      return getCorner(x, y, z);
   }

   public static BoxCorner getCorner(Facing x, Facing y, Facing z) {
      int mask = (x.offset() > 0 ? 1 : 0) << 2 | (y.offset() > 0 ? 1 : 0) << 1 | (z.offset() > 0 ? 1 : 0);
      return LOOKUP[mask];
   }

   public static BoxCorner[] faceCorners(Facing facing) {
      return FACING_CORNERS[facing.ordinal()];
   }

   public boolean isFacing(Facing facing) {
      return this.getFacing(facing.axis) == facing;
   }

   public boolean isFacingPositive(Axis axis) {
      return this.getFacing(axis).positive;
   }

   public Facing getFacing(Axis axis) {
      return switch (axis) {
         case X -> this.x;
         case Y -> this.y;
         case Z -> this.z;
      };
   }

   public BoxCorner transform(IntMatrix3c matrix) {
      int ox = this.x.offset();
      int oy = this.y.offset();
      int oz = this.z.offset();
      return getCorner(
         Facing.get(Axis.X, matrix.getX(ox, oy, oz) > 0), Facing.get(Axis.Y, matrix.getY(ox, oy, oz) > 0), Facing.get(Axis.Z, matrix.getZ(ox, oy, oz) > 0)
      );
   }

   public BoxCorner mirror(Axis axis) {
      return switch (axis) {
         case X -> lookup(this.x.opposite(), this.y, this.z);
         case Y -> lookup(this.x, this.y.opposite(), this.z);
         case Z -> lookup(this.x, this.y, this.z.opposite());
      };
   }

   public BoxCorner rotate(Rotation rotation) {
      int ox = this.x.offset();
      int oy = this.y.offset();
      int oz = this.z.offset();
      IntMatrix3c m = rotation.getMatrix();
      return getCorner(Facing.get(Axis.X, m.getX(ox, oy, oz) > 0), Facing.get(Axis.Y, m.getY(ox, oy, oz) > 0), Facing.get(Axis.Z, m.getZ(ox, oy, oz) > 0));
   }

   public Vec3d get(ABB bb) {
      return new Vec3d(bb.get(this.x), bb.get(this.y), bb.get(this.z));
   }

   public void set(ABB bb, Vec3d vec) {
      vec.x = bb.get(this.x);
      vec.y = bb.get(this.y);
      vec.z = bb.get(this.z);
   }

   public Vec3d get(AABB bb) {
      return new Vec3d(BoxUtils.get(bb, this.x), BoxUtils.get(bb, this.y), BoxUtils.get(bb, this.z));
   }

   public void set(AABB bb, Vec3d vec) {
      vec.x = BoxUtils.get(bb, this.x);
      vec.y = BoxUtils.get(bb, this.y);
      vec.z = BoxUtils.get(bb, this.z);
   }

   public Facing facingTo(BoxCorner corner) {
      boolean sx = this.x == corner.x;
      boolean sy = this.y == corner.y;
      boolean sz = this.z == corner.z;
      if (!BooleanUtils.explicitOneFalse(sx, sy, sz)) {
         return null;
      } else if (!sx) {
         return corner.x;
      } else {
         return !sy ? corner.y : corner.z;
      }
   }

   static {
      for (BoxCorner c : values()) {
         int mask = (c.x.offset() > 0 ? 1 : 0) << 2 | (c.y.offset() > 0 ? 1 : 0) << 1 | (c.z.offset() > 0 ? 1 : 0);
         LOOKUP[mask] = c;
      }

      for (BoxCorner c : values()) {
         c.neighborOne = lookup(c.x.opposite(), c.y, c.z);
         c.neighborTwo = lookup(c.x, c.y.opposite(), c.z);
         c.neighborThree = lookup(c.x, c.y, c.z.opposite());
      }
   }
}
