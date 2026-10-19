package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RotatedShapes {
   private final VoxelShape[] rotatedShapes;
   private final boolean horizontal;

   public RotatedShapes(VoxelShape... shapes) {
      this(true, shapes);
   }

   public RotatedShapes(boolean horizontal, VoxelShape... shapes) {
      this.rotatedShapes = new VoxelShape[horizontal ? 4 : 6];
      this.horizontal = horizontal;
      this.rotatedShapes[0] = this.or(Stream.of(shapes));
   }

   public VoxelShape getRotatedShape(Direction to) {
      int index = this.horizontal ? (to.get2DDataValue() + 4) % 4 : to.get3DDataValue();
      if (this.rotatedShapes[index] == null) {
         if (this.horizontal) {
            this.rotatedShapes[index] = this.rotateHorizontal(to);
         } else {
            this.rotatedShapes[index] = this.rotate(to);
         }
      }

      return this.rotatedShapes[index];
   }

   private VoxelShape rotateHorizontal(Direction dir) {
      return switch (dir) {
         case NORTH -> this.rotateShape(
            this.rotatedShapes[0], (minX, minY, minZ, maxX, maxY, maxZ) -> this.box(1.0 - minX, minY, 1.0 - minZ, 1.0 - maxX, maxY, 1.0 - maxZ)
         );
         case SOUTH -> this.rotatedShapes[0];
         case WEST -> this.rotateShape(this.rotatedShapes[0], (minX, minY, minZ, maxX, maxY, maxZ) -> this.box(1.0 - minZ, minY, minX, 1.0 - maxZ, maxY, maxX));
         case EAST -> this.rotateShape(this.rotatedShapes[0], (minX, minY, minZ, maxX, maxY, maxZ) -> this.box(minZ, minY, 1.0 - minX, maxZ, maxY, 1.0 - maxX));
         default -> this.rotatedShapes[0];
      };
   }

   private VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      return Shapes.box(Math.min(minX, maxX), Math.min(minY, maxY), Math.min(minZ, maxZ), Math.max(minX, maxX), Math.max(minY, maxY), Math.max(minZ, maxZ));
   }

   private VoxelShape rotate(Direction dir) {
      return switch (dir) {
         case NORTH -> this.rotateShape(this.rotatedShapes[0], (minX, minY, minZ, maxX, maxY, maxZ) -> this.box(minX, 1.0 - minZ, minY, maxX, 1.0 - maxZ, maxY));
         case SOUTH -> this.rotateShape(
            this.rotatedShapes[0], (minX, minY, minZ, maxX, maxY, maxZ) -> this.box(1.0 - minX, 1.0 - minZ, 1.0 - minY, 1.0 - maxX, 1.0 - maxZ, 1.0 - maxY)
         );
         case WEST -> this.rotateShape(
            this.rotatedShapes[0], (minX, minY, minZ, maxX, maxY, maxZ) -> this.box(minY, 1.0 - minZ, 1.0 - minX, maxY, 1.0 - maxZ, 1.0 - maxX)
         );
         case EAST -> this.rotateShape(
            this.rotatedShapes[0], (minX, minY, minZ, maxX, maxY, maxZ) -> this.box(1.0 - minY, 1.0 - minZ, minX, 1.0 - maxY, 1.0 - maxZ, maxX)
         );
         case DOWN -> this.rotatedShapes[0];
         case UP -> this.rotateShape(
            this.rotatedShapes[0], (minX, minY, minZ, maxX, maxY, maxZ) -> this.box(minX, 1.0 - minY, 1.0 - minZ, maxX, 1.0 - maxY, 1.0 - maxZ)
         );
         default -> throw new MatchException(null, null);
      };
   }

   private VoxelShape rotateShape(VoxelShape shape, RotatedShapes.DoubleLineFunction rotate) {
      List<VoxelShape> shapes = new ArrayList<>();
      shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> shapes.add(rotate.apply(minX, minY, minZ, maxX, maxY, maxZ)));
      return this.or(shapes.stream());
   }

   private VoxelShape or(Stream<VoxelShape> shapes) {
      return shapes.reduce((v1, v2) -> Shapes.joinUnoptimized(v1, v2, BooleanOp.OR)).<VoxelShape>map(VoxelShape::optimize).orElse(Shapes.empty());
   }

   public interface DoubleLineFunction {
      VoxelShape apply(double var1, double var3, double var5, double var7, double var9, double var11);
   }
}
