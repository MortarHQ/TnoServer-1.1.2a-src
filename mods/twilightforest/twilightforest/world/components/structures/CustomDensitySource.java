package twilightforest.world.components.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import twilightforest.world.components.chunkgenerators.AbsoluteDifferenceFunction;

public interface CustomDensitySource {
   DensityFunction getStructureTerraformer(ChunkPos var1, StructureStart var2);

   static DensityFunction getInvertedPyramidTerraformer(StructureStart structurePieceSource, int yOffset, int horizontalPadding) {
      int vanillaBoxInflationFactor = 12;
      BoundingBox structureBox = structurePieceSource.getBoundingBox();
      int squareHorizontalSpan = Math.max(structureBox.getXSpan(), structureBox.getZSpan()) - 2 * vanillaBoxInflationFactor;
      BlockPos centerPos = structureBox.getCenter();
      yOffset += structureBox.minY() + vanillaBoxInflationFactor;
      DensityFunction invertedPyramid = DensityFunctions.add(
         DensityFunctions.yClampedGradient(yOffset - 1, yOffset + squareHorizontalSpan, 1.0, -1 - squareHorizontalSpan),
         new AbsoluteDifferenceFunction.Max(squareHorizontalSpan, centerPos.getX() + 0.5F, centerPos.getZ() + 0.5F)
      );
      return DensityFunctions.min(
         DensityFunctions.constant(0.0),
         DensityFunctions.max(
            DensityFunctions.yClampedGradient(yOffset - 1, yOffset, 1.0, -1.0),
            DensityFunctions.add(DensityFunctions.constant(-(squareHorizontalSpan >> 1) - horizontalPadding), invertedPyramid)
         )
      );
   }
}
