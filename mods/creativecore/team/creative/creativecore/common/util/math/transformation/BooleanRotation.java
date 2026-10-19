package team.creative.creativecore.common.util.math.transformation;

import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.base.Facing;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public enum BooleanRotation {
   X_PP(Axis.X, 0, true, true),
   X_NP(Axis.X, 1, false, true),
   X_NN(Axis.X, 2, false, false),
   X_PN(Axis.X, 3, true, false),
   Y_PP(Axis.Y, 0, true, true),
   Y_PN(Axis.Y, 1, true, false),
   Y_NN(Axis.Y, 2, false, false),
   Y_NP(Axis.Y, 3, false, true),
   Z_PP(Axis.Z, 0, true, true),
   Z_NP(Axis.Z, 1, false, true),
   Z_NN(Axis.Z, 2, false, false),
   Z_PN(Axis.Z, 3, true, false);

   private static final BooleanRotation[][] ROTATIONS = new BooleanRotation[3][4];
   public final Axis axis;
   private final int index;
   private final boolean positiveOne;
   private final boolean positiveTwo;

   BooleanRotation(Axis axis, int index, boolean positiveOne, boolean positiveTwo) {
      this.axis = axis;
      this.index = index;
      this.positiveOne = positiveOne;
      this.positiveTwo = positiveTwo;
   }

   private static Axis one(Axis axis) {
      return switch (axis) {
         case X -> Axis.Y;
         case Y, Z -> Axis.X;
         default -> null;
      };
   }

   private static Axis two(Axis axis) {
      return switch (axis) {
         case X, Y -> Axis.Z;
         case Z -> Axis.Y;
         default -> null;
      };
   }

   public BooleanRotation clockwise() {
      return this.index == 3 ? ROTATIONS[this.axis.ordinal()][0] : ROTATIONS[this.axis.ordinal()][this.index + 1];
   }

   public BooleanRotation counterClockwise() {
      return this.index == 0 ? ROTATIONS[this.axis.ordinal()][3] : ROTATIONS[this.axis.ordinal()][this.index - 1];
   }

   public Facing clockwiseMaxFacing() {
      return this.getFacingInBetween(this.clockwise());
   }

   public Facing counterMaxClockwiseFacing() {
      return this.getFacingInBetween(this.counterClockwise());
   }

   private Facing getFacingInBetween(BooleanRotation other) {
      if (this.positiveOne != other.positiveOne) {
         return this.axis.facing(this.positiveTwo);
      } else if (this.positiveTwo != other.positiveTwo) {
         return this.axis.facing(this.positiveOne);
      } else {
         throw new RuntimeException("Impossible to happen!");
      }
   }

   public boolean is(Vec3d vec) {
      return this.positiveOne == vec.get(one(this.axis)) >= 0.0 && this.positiveTwo == vec.get(two(this.axis)) >= 0.0;
   }

   public static BooleanRotation get(Axis axis, Vec3d vec) {
      boolean positiveOne = vec.get(one(axis)) >= 0.0;
      boolean positiveTwo = vec.get(two(axis)) >= 0.0;

      for (int i = 0; i < ROTATIONS[axis.ordinal()].length; i++) {
         BooleanRotation rotation = ROTATIONS[axis.ordinal()][i];
         if (rotation.positiveOne == positiveOne && rotation.positiveTwo == positiveTwo) {
            return rotation;
         }
      }

      return null;
   }

   static {
      for (BooleanRotation rot : values()) {
         ROTATIONS[rot.axis.ordinal()][rot.index] = rot;
      }
   }
}
