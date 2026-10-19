package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class BackpackShapeHelper {
   private BackpackShapeHelper() {
   }

   public static VoxelShape composeAndRotate(BackpackShapeHelper.ShapeKey key, Function<BackpackShapeHelper.Part, VoxelShape> partLookup) {
      VoxelShape composed = or(
         Stream.of(
            partLookup.apply(BackpackShapeHelper.Part.BASE),
            partLookup.apply(key.leftTank ? BackpackShapeHelper.Part.LEFT_TANK : BackpackShapeHelper.Part.LEFT_POUCH),
            partLookup.apply(key.rightTank ? BackpackShapeHelper.Part.RIGHT_TANK : BackpackShapeHelper.Part.RIGHT_POUCH),
            partLookup.apply(key.battery ? BackpackShapeHelper.Part.BATTERY : BackpackShapeHelper.Part.FRONT_POUCH)
         )
      );
      return rotateHorizontalFromNorth(composed, key.direction).optimize();
   }

   public static VoxelShape rotateHorizontalFromNorth(VoxelShape shape, Direction dir) {
      return switch (dir) {
         case NORTH -> shape;
         case EAST -> rotateShape(shape, (minX, minY, minZ, maxX, maxY, maxZ) -> box(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX));
         case SOUTH -> rotateShape(shape, (minX, minY, minZ, maxX, maxY, maxZ) -> box(1.0 - maxX, minY, 1.0 - maxZ, 1.0 - minX, maxY, 1.0 - minZ));
         case WEST -> rotateShape(shape, (minX, minY, minZ, maxX, maxY, maxZ) -> box(minZ, minY, 1.0 - maxX, maxZ, maxY, 1.0 - minX));
         default -> shape;
      };
   }

   public static VoxelShape or(Stream<VoxelShape> shapes) {
      return shapes.reduce((v1, v2) -> Shapes.joinUnoptimized(v1, v2, BooleanOp.OR)).orElse(Shapes.empty());
   }

   private static VoxelShape rotateShape(VoxelShape shape, BackpackShapeHelper.DoubleLineFunction rotate) {
      VoxelShape ret = Shapes.empty();

      for (AABB aabb : shape.toAabbs()) {
         ret = Shapes.joinUnoptimized(ret, rotate.apply(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ), BooleanOp.OR);
      }

      return ret;
   }

   private static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      return Shapes.box(Math.min(minX, maxX), Math.min(minY, maxY), Math.min(minZ, maxZ), Math.max(minX, maxX), Math.max(minY, maxY), Math.max(minZ, maxZ));
   }

   private interface DoubleLineFunction {
      VoxelShape apply(double var1, double var3, double var5, double var7, double var9, double var11);
   }

   public enum Part {
      BASE,
      BATTERY,
      FRONT_POUCH,
      LEFT_POUCH,
      LEFT_TANK,
      RIGHT_POUCH,
      RIGHT_TANK;
   }

   public record ShapeKey(Direction direction, boolean leftTank, boolean rightTank, boolean battery) {
      public static BackpackShapeHelper.ShapeKey of(Direction direction, boolean leftTank, boolean rightTank, boolean battery) {
         return new BackpackShapeHelper.ShapeKey(direction, leftTank, rightTank, battery);
      }
   }
}
