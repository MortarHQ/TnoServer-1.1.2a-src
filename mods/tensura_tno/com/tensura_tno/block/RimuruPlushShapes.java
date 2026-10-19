package com.tensura_tno.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.ArrayVoxelShape;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes.DoubleLineConsumer;

final class RimuruPlushShapes {
   private static final int HORIZONTAL_RESOLUTION = 64;
   private static final VoxelShape[] STANDING = rotations(
      Shapes.or(
         box(4.875, 7.625, 5.125, 11.125, 13.75, 11.0),
         new VoxelShape[]{
            box(5.75, 4.125, 6.375, 10.25, 8.625, 9.625),
            box(4.0, 3.5, 7.0, 6.375, 8.25, 9.0),
            box(9.625, 3.5, 7.0, 12.0, 8.25, 9.0),
            box(6.125, 0.0, 6.625, 9.875, 4.5, 9.0)
         }
      )
   );
   private static final VoxelShape[] SITTING = rotations(
      Shapes.or(
         box(4.875, 3.625, 5.75, 11.125, 9.625, 11.5),
         new VoxelShape[]{
            box(5.75, 0.0, 6.875, 10.25, 4.625, 10.25),
            box(3.75, 0.0, 7.5, 6.5, 4.875, 9.875),
            box(9.5, 0.0, 7.5, 12.25, 4.875, 9.875),
            box(5.875, 0.0, 4.0, 10.125, 2.375, 8.625)
         }
      )
   );

   private RimuruPlushShapes() {
   }

   static VoxelShape get(Direction facing, boolean sitting) {
      return get(rotationStep(facing, 0), sitting);
   }

   static VoxelShape get(int step, boolean sitting) {
      return (sitting ? SITTING : STANDING)[step & 15];
   }

   static int rotationStep(Direction facing, int offset) {
      int cardinalStep = switch (facing) {
         case EAST -> 4;
         case SOUTH -> 8;
         case WEST -> 12;
         default -> 0;
      };
      return cardinalStep + offset & 15;
   }

   static Direction facingForStep(int step) {
      return switch ((step & 15) / 4) {
         case 1 -> Direction.EAST;
         case 2 -> Direction.SOUTH;
         case 3 -> Direction.WEST;
         default -> Direction.NORTH;
      };
   }

   static int placementStep(float playerYaw) {
      return Mth.floor(playerYaw / 22.5F + 0.5F) & 15;
   }

   private static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      double ratio = 1.163636337627064;
      return Shapes.box(
         (8.0 + (minX - 8.0) * ratio) / 16.0,
         minY * ratio / 16.0,
         (8.0 + (minZ - 8.0) * ratio) / 16.0,
         (8.0 + (maxX - 8.0) * ratio) / 16.0,
         maxY * ratio / 16.0,
         (8.0 + (maxZ - 8.0) * ratio) / 16.0
      );
   }

   static VoxelShape[] rotations(VoxelShape north) {
      VoxelShape[] shapes = new VoxelShape[16];
      VoxelShape shape = north.optimize();

      for (int turn = 0; turn < 4; turn++) {
         shapes[turn * 4] = shape;

         for (int offset = 1; offset < 4; offset++) {
            shapes[turn * 4 + offset] = rotateFine(shape, offset * Math.PI / 8.0);
         }

         VoxelShape[] rotated = new VoxelShape[]{Shapes.empty()};
         shape.forAllBoxes(
            (minX, minY, minZ, maxX, maxY, maxZ) -> rotated[0] = Shapes.or(rotated[0], Shapes.box(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX))
         );
         shape = rotated[0].optimize();
      }

      return shapes;
   }

   private static VoxelShape rotateFine(VoxelShape shape, double radians) {
      List<AABB> boxes = shape.toAabbs();
      double[] heights = boxes.stream().flatMapToDouble(boxx -> DoubleStream.of(boxx.minY, boxx.maxY)).distinct().sorted().toArray();
      double[] horizontal = new double[65];
      Arrays.setAll(horizontal, index -> index / 64.0);
      BitSetDiscreteVoxelShape voxels = new BitSetDiscreteVoxelShape(64, heights.length - 1, 64);
      double cos = Math.cos(radians);
      double sin = Math.sin(radians);
      double halfCell = 0.0078125;

      for (AABB box : boxes) {
         double originalX = (box.minX + box.maxX) / 2.0 - 0.5;
         double originalZ = (box.minZ + box.maxZ) / 2.0 - 0.5;
         double centreX = 0.5 + originalX * cos - originalZ * sin;
         double centreZ = 0.5 + originalX * sin + originalZ * cos;
         double halfX = box.getXsize() / 2.0;
         double halfZ = box.getZsize() / 2.0;
         double extentX = Math.abs(cos) * halfX + Math.abs(sin) * halfZ;
         double extentZ = Math.abs(sin) * halfX + Math.abs(cos) * halfZ;
         int minY = Arrays.binarySearch(heights, box.minY);
         int maxY = Arrays.binarySearch(heights, box.maxY);

         for (int x = 0; x < 64; x++) {
            double dx = horizontal[x] + halfCell - centreX;
            if (!(Math.abs(dx) >= extentX + halfCell)) {
               for (int z = 0; z < 64; z++) {
                  double dz = horizontal[z] + halfCell - centreZ;
                  if (!(Math.abs(dz) >= extentZ + halfCell)) {
                     double cellProjection = halfCell * (Math.abs(cos) + Math.abs(sin));
                     if (!(Math.abs(dx * cos + dz * sin) >= halfX + cellProjection) && !(Math.abs(-dx * sin + dz * cos) >= halfZ + cellProjection)) {
                        for (int y = minY; y < maxY; y++) {
                           voxels.fill(x, y, z);
                        }
                     }
                  }
               }
            }
         }
      }

      return new RimuruPlushShapes.RotatedVoxelShape(voxels, horizontal, heights, shape, cos, sin);
   }

   private record OutlineEdge(double x1, double y1, double z1, double x2, double y2, double z2) {
   }

   private static final class RotatedVoxelShape extends ArrayVoxelShape {
      private final List<RimuruPlushShapes.OutlineEdge> outline;

      private RotatedVoxelShape(BitSetDiscreteVoxelShape voxels, double[] horizontal, double[] heights, VoxelShape source, double cos, double sin) {
         super(voxels, horizontal, heights, horizontal);
         List<RimuruPlushShapes.OutlineEdge> edges = new ArrayList<>();
         source.forAllEdges(
            (x1, y1, z1, x2, y2, z2) -> edges.add(
               new RimuruPlushShapes.OutlineEdge(
                  0.5 + (x1 - 0.5) * cos - (z1 - 0.5) * sin,
                  y1,
                  0.5 + (x1 - 0.5) * sin + (z1 - 0.5) * cos,
                  0.5 + (x2 - 0.5) * cos - (z2 - 0.5) * sin,
                  y2,
                  0.5 + (x2 - 0.5) * sin + (z2 - 0.5) * cos
               )
            )
         );
         this.outline = List.copyOf(edges);
      }

      public void forAllEdges(DoubleLineConsumer consumer) {
         for (RimuruPlushShapes.OutlineEdge edge : this.outline) {
            consumer.consume(edge.x1, edge.y1, edge.z1, edge.x2, edge.y2, edge.z2);
         }
      }
   }
}
