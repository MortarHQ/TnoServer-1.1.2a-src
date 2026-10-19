package team.creative.creativecore.common.util.math.box;

import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.base.Facing;
import team.creative.creativecore.common.util.math.vec.Vec3d;
import team.creative.creativecore.common.util.math.vec.Vec3f;

public enum BoxFace {
   EAST(Facing.EAST, new BoxCorner[]{BoxCorner.EUS, BoxCorner.EDS, BoxCorner.EDN, BoxCorner.EUN}, Facing.NORTH, Facing.DOWN),
   WEST(Facing.WEST, new BoxCorner[]{BoxCorner.WUN, BoxCorner.WDN, BoxCorner.WDS, BoxCorner.WUS}, Facing.SOUTH, Facing.DOWN),
   UP(Facing.UP, new BoxCorner[]{BoxCorner.WUN, BoxCorner.WUS, BoxCorner.EUS, BoxCorner.EUN}, Facing.EAST, Facing.SOUTH),
   DOWN(Facing.DOWN, new BoxCorner[]{BoxCorner.WDS, BoxCorner.WDN, BoxCorner.EDN, BoxCorner.EDS}, Facing.EAST, Facing.NORTH),
   SOUTH(Facing.SOUTH, new BoxCorner[]{BoxCorner.WUS, BoxCorner.WDS, BoxCorner.EDS, BoxCorner.EUS}, Facing.EAST, Facing.DOWN),
   NORTH(Facing.NORTH, new BoxCorner[]{BoxCorner.EUN, BoxCorner.EDN, BoxCorner.WDN, BoxCorner.WUN}, Facing.WEST, Facing.DOWN);

   public final Facing facing;
   public final BoxCorner[] corners;
   private final Axis one;
   private final Axis two;
   private final Facing texU;
   private final Facing texV;
   private final BoxCorner[] triangleFirst;
   private final BoxCorner[] triangleFirstInv;
   private final BoxCorner[] triangleSecond;
   private final BoxCorner[] triangleSecondInv;

   BoxFace(Facing facing, BoxCorner[] corners, Facing texU, Facing texV) {
      this.facing = facing;
      this.corners = corners;
      this.one = facing.axis.one();
      this.two = facing.axis.two();
      this.texU = texU;
      this.texV = texV;
      this.triangleFirst = new BoxCorner[]{corners[0], corners[1], corners[2]};
      this.triangleFirstInv = new BoxCorner[]{corners[0], corners[1], corners[3]};
      this.triangleSecond = new BoxCorner[]{corners[0], corners[2], corners[3]};
      this.triangleSecondInv = new BoxCorner[]{corners[1], corners[2], corners[3]};
   }

   public BoxCorner getCornerInQuestion(boolean first, boolean inverted) {
      if (first) {
         return inverted ? this.corners[1] : this.corners[0];
      } else {
         return inverted ? this.corners[3] : this.corners[2];
      }
   }

   private BoxCorner[] getTriangle(boolean first, boolean inverted) {
      if (first) {
         return inverted ? this.triangleFirstInv : this.triangleFirst;
      } else {
         return inverted ? this.triangleSecondInv : this.triangleSecond;
      }
   }

   public Axis getTexUAxis() {
      return this.texU.axis;
   }

   public Axis getTexVAxis() {
      return this.texV.axis;
   }

   public Facing getTexU() {
      return this.texU;
   }

   public Facing getTexV() {
      return this.texV;
   }

   public BoxCorner[] getTriangleFirst(boolean inverted) {
      return this.getTriangle(true, inverted);
   }

   public BoxCorner[] getTriangleSecond(boolean inverted) {
      return this.getTriangle(false, inverted);
   }

   public BoxCorner[] getCorners() {
      return this.corners;
   }

   public Facing getFacing() {
      return this.facing;
   }

   public Axis getOne() {
      return this.one;
   }

   public Axis getTwo() {
      return this.two;
   }

   public Vec3d first(Vec3d[] corners) {
      return corners[this.corners[0].ordinal()];
   }

   public Vec3d normal(Vec3d[] corners) {
      Vec3d origin = this.first(corners);
      Vec3d first = new Vec3d(corners[this.corners[1].ordinal()]);
      Vec3d second = new Vec3d(corners[this.corners[2].ordinal()]);
      first.sub(origin);
      second.sub(origin);
      return new Vec3d(first.y * second.z - first.z * second.y, first.z * second.x - first.x * second.z, first.x * second.y - first.y * second.x);
   }

   public Boolean isFacingOutwards(boolean first, boolean inverted, Vec3f normal) {
      float valueOne = normal.get(this.one);
      float valueTwo = normal.get(this.two);
      Boolean outwardOne = valueOne == 0.0F ? null : valueOne > 0.0F;
      Boolean outwardTwo = valueTwo == 0.0F ? null : valueTwo > 0.0F;
      if (outwardOne == outwardTwo) {
         return outwardOne;
      } else if (outwardOne != null && outwardTwo == null) {
         return outwardOne;
      } else if (outwardOne == null) {
         return outwardTwo;
      } else if (valueOne == valueTwo) {
         return null;
      } else {
         return valueOne > valueTwo ? outwardOne : outwardTwo;
      }
   }

   public static Vec3f getTraingleNormal(BoxCorner[] triangle, Vec3f[] corners) {
      Vec3f a = new Vec3f(corners[triangle[1].ordinal()]);
      a.sub(corners[triangle[0].ordinal()]);
      Vec3f b = new Vec3f(corners[triangle[2].ordinal()]);
      b.sub(corners[triangle[0].ordinal()]);
      Vec3f normal = new Vec3f();
      normal.cross(a, b);
      return normal;
   }

   public static void ensureSameLength(Vec3f one, Vec3f two) {
      float normVecOne = one.x * one.x + one.y * one.y + one.z * one.z;
      float normVecTwo = two.x * two.x + two.y * two.y + two.z * two.z;
      one.scale(normVecTwo);
      two.scale(normVecOne);
   }

   public static BoxFace get(Facing facing) {
      return switch (facing) {
         case EAST -> EAST;
         case WEST -> WEST;
         case UP -> UP;
         case DOWN -> DOWN;
         case SOUTH -> SOUTH;
         case NORTH -> NORTH;
      };
   }

   public static BoxFace get(Axis axis, boolean direction) {
      return switch (axis) {
         case X -> direction ? EAST : WEST;
         case Y -> direction ? UP : DOWN;
         case Z -> direction ? SOUTH : NORTH;
      };
   }

   public static Vec3f[] getVecArray(BoxCorner[] corners, Vec3f[] vecs) {
      Vec3f[] result = new Vec3f[corners.length];

      for (int i = 0; i < result.length; i++) {
         result[i] = vecs[corners[i].ordinal()];
      }

      return result;
   }
}
