package twilightforest.enums;

import java.util.Locale;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public enum Diagonals implements StringRepresentable {
   TOP_RIGHT((x, rX) -> rX - x, (y, rY) -> y, true, false),
   BOTTOM_RIGHT((x, rX) -> rX - x, (y, rY) -> rY - y, false, false),
   BOTTOM_LEFT((x, rX) -> x, (y, rY) -> rY - y, false, true),
   TOP_LEFT((x, rX) -> x, (y, rY) -> y, true, true);

   public final Diagonals.Inversion operationX;
   public final Diagonals.Inversion operationY;
   private final boolean isTop;
   private final boolean isLeft;

   Diagonals(Diagonals.Inversion operationX, Diagonals.Inversion operationY, boolean isTop, boolean isLeft) {
      this.operationX = operationX;
      this.operationY = operationY;
      this.isTop = isTop;
      this.isLeft = isLeft;
   }

   public static Diagonals getDiagonalFromUpDownLeftRight(boolean isLeft, boolean isTop) {
      if (isLeft) {
         return isTop ? TOP_LEFT : BOTTOM_LEFT;
      } else {
         return isTop ? TOP_RIGHT : BOTTOM_RIGHT;
      }
   }

   public static Diagonals rotate(Diagonals diagonal, Rotation rotation) {
      return values()[(diagonal.ordinal() + rotation.ordinal()) % 4];
   }

   public static Diagonals mirrorOn(Axis axis, Diagonals diagonal, Mirror mirror) {
      return switch (axis) {
         case X -> mirrorOnX(diagonal, mirror);
         case Z -> mirrorOnZ(diagonal, mirror);
         default -> mirror(diagonal, mirror);
      };
   }

   public static Diagonals mirrorOnX(Diagonals diagonal, Mirror mirror) {
      if (mirror == Mirror.FRONT_BACK) {
         return switch (diagonal) {
            case TOP_RIGHT -> BOTTOM_RIGHT;
            case BOTTOM_RIGHT -> TOP_RIGHT;
            case BOTTOM_LEFT -> TOP_LEFT;
            case TOP_LEFT -> BOTTOM_LEFT;
         };
      } else {
         return diagonal;
      }
   }

   public static Diagonals mirrorLeftRight(Diagonals diagonal) {
      return switch (diagonal) {
         case TOP_RIGHT -> TOP_LEFT;
         case BOTTOM_RIGHT -> BOTTOM_LEFT;
         case BOTTOM_LEFT -> BOTTOM_RIGHT;
         case TOP_LEFT -> TOP_RIGHT;
      };
   }

   public static Diagonals mirrorUpDown(Diagonals diagonal) {
      return switch (diagonal) {
         case TOP_RIGHT -> BOTTOM_RIGHT;
         case BOTTOM_RIGHT -> TOP_RIGHT;
         case BOTTOM_LEFT -> TOP_LEFT;
         case TOP_LEFT -> BOTTOM_LEFT;
      };
   }

   public static Diagonals mirror(Diagonals diagonal, Mirror mirror) {
      return switch (mirror) {
         case LEFT_RIGHT -> mirrorLeftRight(diagonal);
         case FRONT_BACK -> mirrorUpDown(diagonal);
         default -> diagonal;
      };
   }

   public static Diagonals mirrorOnZ(Diagonals diagonal, Mirror mirror) {
      if (mirror == Mirror.LEFT_RIGHT) {
         return switch (diagonal) {
            case TOP_RIGHT -> TOP_LEFT;
            case BOTTOM_RIGHT -> BOTTOM_LEFT;
            case BOTTOM_LEFT -> BOTTOM_RIGHT;
            case TOP_LEFT -> TOP_RIGHT;
         };
      } else {
         return diagonal;
      }
   }

   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   public boolean isTop() {
      return this.isTop;
   }

   public boolean isLeft() {
      return this.isLeft;
   }

   public interface Inversion {
      int convert(int var1, int var2);
   }
}
