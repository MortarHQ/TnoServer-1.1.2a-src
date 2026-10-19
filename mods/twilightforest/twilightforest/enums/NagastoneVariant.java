package twilightforest.enums;

import java.util.Locale;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.StringRepresentable;

public enum NagastoneVariant implements StringRepresentable {
   NORTH_DOWN,
   SOUTH_DOWN,
   WEST_DOWN,
   EAST_DOWN,
   NORTH_UP,
   SOUTH_UP,
   EAST_UP,
   WEST_UP,
   AXIS_X,
   AXIS_Y,
   AXIS_Z,
   SOLID;

   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   public static NagastoneVariant getVariantFromAxis(Axis axis) {
      return switch (axis) {
         case X -> AXIS_X;
         case Y -> AXIS_Y;
         case Z -> AXIS_Z;
         default -> throw new MatchException(null, null);
      };
   }

   public static NagastoneVariant getVariantFromDoubleFacing(Direction facing1, Direction facing2) {
      if (facing1.getAxis() == facing2.getAxis()) {
         return getVariantFromAxis(facing1.getAxis());
      }

      if (facing1.getAxis() != Axis.Y && facing2.getAxis() != Axis.Y) {
         return SOLID;
      }

      Direction facingYAxis = facing1.getAxis() == Axis.Y ? facing1 : facing2;
      Direction otherFace = facing1.getAxis() != Axis.Y ? facing1 : facing2;
      if (facingYAxis == Direction.UP) {
         return switch (otherFace) {
            case NORTH -> NORTH_UP;
            case SOUTH -> SOUTH_UP;
            case WEST -> WEST_UP;
            case EAST -> EAST_UP;
            default -> SOLID;
         };
      } else {
         return switch (otherFace) {
            case NORTH -> NORTH_DOWN;
            case SOUTH -> SOUTH_DOWN;
            case WEST -> WEST_DOWN;
            case EAST -> EAST_DOWN;
            default -> SOLID;
         };
      }
   }
}
