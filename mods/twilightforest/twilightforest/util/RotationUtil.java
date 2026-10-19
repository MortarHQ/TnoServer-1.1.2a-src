package twilightforest.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public final class RotationUtil {
   public static final Rotation[] ROTATIONS = Rotation.values();
   public static final Direction[] CARDINALS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

   private RotationUtil() {
   }

   public static Rotation getRandomRotation(RandomSource random) {
      return ROTATIONS[random.nextInt(ROTATIONS.length)];
   }

   public static Rotation add(Rotation original, int rotations) {
      return original.getRotated(ROTATIONS[rotations + 4 & 3]);
   }

   public static Rotation subtract(Rotation original, Rotation rotation) {
      return switch (rotation) {
         case CLOCKWISE_90 -> {
            switch (original) {
               case NONE:
                  yield Rotation.COUNTERCLOCKWISE_90;
               case CLOCKWISE_90:
                  yield Rotation.NONE;
               case CLOCKWISE_180:
                  yield Rotation.CLOCKWISE_90;
               case COUNTERCLOCKWISE_90:
                  yield Rotation.CLOCKWISE_180;
               default:
                  throw new MatchException(null, null);
            }
         }
         case CLOCKWISE_180 -> {
            switch (original) {
               case NONE:
                  yield Rotation.CLOCKWISE_180;
               case CLOCKWISE_90:
                  yield Rotation.COUNTERCLOCKWISE_90;
               case CLOCKWISE_180:
                  yield Rotation.NONE;
               case COUNTERCLOCKWISE_90:
                  yield Rotation.CLOCKWISE_90;
               default:
                  throw new MatchException(null, null);
            }
         }
         case COUNTERCLOCKWISE_90 -> {
            switch (original) {
               case NONE:
                  yield Rotation.CLOCKWISE_90;
               case CLOCKWISE_90:
                  yield Rotation.CLOCKWISE_180;
               case CLOCKWISE_180:
                  yield Rotation.COUNTERCLOCKWISE_90;
               case COUNTERCLOCKWISE_90:
                  yield Rotation.NONE;
               default:
                  throw new MatchException(null, null);
            }
         }
         default -> original;
      };
   }

   public static Rotation getRelativeRotation(Direction original, Direction destination) {
      return switch (original) {
         case WEST -> {
            switch (destination) {
               case NORTH:
                  yield Rotation.CLOCKWISE_90;
               case WEST:
               default:
                  yield Rotation.NONE;
               case EAST:
                  yield Rotation.CLOCKWISE_180;
               case SOUTH:
                  yield Rotation.COUNTERCLOCKWISE_90;
            }
         }
         case EAST -> {
            switch (destination) {
               case NORTH:
                  yield Rotation.COUNTERCLOCKWISE_90;
               case WEST:
                  yield Rotation.CLOCKWISE_180;
               case EAST:
               default:
                  yield Rotation.NONE;
               case SOUTH:
                  yield Rotation.CLOCKWISE_90;
            }
         }
         case SOUTH -> {
            switch (destination) {
               case NORTH:
                  yield Rotation.CLOCKWISE_180;
               case WEST:
                  yield Rotation.CLOCKWISE_90;
               case EAST:
                  yield Rotation.COUNTERCLOCKWISE_90;
               default:
                  yield Rotation.NONE;
            }
         }
         default -> {
            switch (destination) {
               case WEST:
                  yield Rotation.COUNTERCLOCKWISE_90;
                  break;
               case EAST:
                  yield Rotation.CLOCKWISE_90;
                  break;
               case SOUTH:
                  yield Rotation.CLOCKWISE_180;
                  break;
               default:
                  yield Rotation.NONE;
            }
         }
      };
   }

   public static Direction getRandomFacing(RandomSource random) {
      return CARDINALS[random.nextInt(CARDINALS.length)];
   }

   public static BlockPos mirrorOffset(Mirror mirror, BlockPos offset) {
      return mirror == Mirror.NONE
         ? offset
         : new BlockPos(
            mirror == Mirror.FRONT_BACK ? -offset.getX() : offset.getX(), offset.getY(), mirror == Mirror.LEFT_RIGHT ? -offset.getZ() : offset.getZ()
         );
   }

   public static Mirror mirrorOverAxis(boolean shouldFlip, Axis axis) {
      return shouldFlip ? (axis == Axis.X ? Mirror.LEFT_RIGHT : Mirror.FRONT_BACK) : Mirror.NONE;
   }
}
