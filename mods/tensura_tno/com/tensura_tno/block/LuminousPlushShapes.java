package com.tensura_tno.block;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

final class LuminousPlushShapes {
   private static final VoxelShape[] STANDING = RimuruPlushShapes.rotations(
      Shapes.or(
         box(-6.46, 10.74, -1.97, -2.81, 14.98, 1.97),
         new VoxelShape[]{
            box(2.81, 10.74, -1.97, 6.46, 14.98, 1.97),
            box(-3.0, 8.45, -2.26, 3.0, 15.73, 2.26),
            box(-2.78, 0.0, -2.08, -0.31, 5.06, 1.42),
            box(0.31, 0.0, -2.08, 2.78, 5.06, 1.42),
            box(-6.46, 5.89, -1.37, -3.64, 11.84, 1.37),
            box(3.64, 5.89, -1.37, 6.46, 11.84, 1.37),
            box(-5.34, 14.4, -4.96, 5.34, 25.0, 5.07),
            box(-2.6, 4.69, -1.15, -0.5, 8.7, 1.15),
            box(0.5, 4.69, -1.15, 2.6, 8.7, 1.15),
            box(-4.13, 8.41, -4.16, 4.13, 11.08, 4.16),
            box(-4.83, 6.44, -4.9, 4.83, 8.89, 4.9),
            box(-5.78, 4.38, -5.89, 5.78, 6.91, 5.89)
         }
      )
   );
   private static final VoxelShape[] SITTING = RimuruPlushShapes.rotations(
      Shapes.or(
         box(-6.62, 5.53, -0.81, -2.75, 10.26, 3.19),
         new VoxelShape[]{
            box(2.75, 5.53, -0.81, 6.62, 10.26, 3.19),
            box(-3.0, 2.5, -1.16, 3.0, 9.82, 3.16),
            box(-3.72, 0.0, -6.08, -0.84, 3.5, -0.81),
            box(0.84, 0.0, -6.08, 3.72, 3.5, -0.81),
            box(-6.83, 0.84, 0.07, -3.74, 7.08, 2.93),
            box(3.74, 0.84, 0.07, 6.83, 7.08, 2.93),
            box(-5.36, 8.5, -3.77, 5.48, 19.12, 6.02),
            box(-3.05, 0.27, -1.39, -0.55, 2.57, 2.81),
            box(0.55, 0.27, -1.39, 3.05, 2.57, 2.81),
            box(-4.12, 1.18, -3.47, 4.12, 5.88, 4.44),
            box(-4.82, 0.01, -4.39, 4.82, 5.6, 4.66)
         }
      )
   );

   private LuminousPlushShapes() {
   }

   static VoxelShape get(int step, boolean sitting) {
      return (sitting ? SITTING : STANDING)[step & 15];
   }

   private static VoxelShape box(double x1, double y1, double z1, double x2, double y2, double z2) {
      double scale = 0.035F;
      return Shapes.box(0.5 + x1 * scale, y1 * scale, 0.5 + z1 * scale, 0.5 + x2 * scale, y2 * scale, 0.5 + z2 * scale);
   }
}
