package twilightforest.util;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;

public final class AxisUtil {
   public static Direction getAxisDirectionPositive(Axis axis) {
      return switch (axis) {
         case X -> Direction.EAST;
         case Y -> Direction.UP;
         case Z -> Direction.SOUTH;
         default -> throw new MatchException(null, null);
      };
   }

   public static Direction getAxisDirectionNegative(Axis axis) {
      return switch (axis) {
         case X -> Direction.WEST;
         case Y -> Direction.DOWN;
         case Z -> Direction.NORTH;
         default -> throw new MatchException(null, null);
      };
   }

   private AxisUtil() {
   }
}
