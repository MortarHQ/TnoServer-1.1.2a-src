package twilightforest.world.components.structures.finalcastle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class FinalCastleBridgeComponent extends TFStructureComponentOld {
   public FinalCastleBridgeComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFFCBri.get(), nbt);
   }

   public FinalCastleBridgeComponent(int i, int x, int y, int z, int length, Direction direction) {
      super((StructurePieceType)TFStructurePieceTypes.TFFCBri.get(), i, x, y, z);
      this.setOrientation(direction);
      this.boundingBox = TFStructureComponentOld.getComponentToAddBoundingBox2(x, y, z, 0, -1, -3, length - 1, 5, 6, direction);
   }

   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      int length = this.getOrientation() != Direction.SOUTH && this.getOrientation() != Direction.NORTH
         ? this.boundingBox.maxZ() - this.boundingBox.minZ()
         : this.boundingBox.maxX() - this.boundingBox.minX();
      this.generateBox(world, sbb, 0, 0, 0, length, 1, 6, false, rand, this.deco.randomBlocks);
      BlockState castlePillar = (BlockState)((Block)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get()).defaultBlockState().setValue(RotatedPillarBlock.AXIS, Axis.X);
      this.generateBox(world, sbb, 0, 2, 0, length, 2, 0, castlePillar, castlePillar, false);
      this.generateBox(world, sbb, 0, 2, 6, length, 2, 6, castlePillar, castlePillar, false);
      int l3 = length / 3;

      for (int i = 0; i < l3; i++) {
         int sl = l3 - (int)(Mth.cos((float)(l3 - i) / l3 * 1.6F) * l3);
         this.generateBox(world, sbb, i, -sl, 0, i, 0, 0, false, rand, this.deco.randomBlocks);
         this.generateBox(world, sbb, i, -sl, 6, i, 0, 6, false, rand, this.deco.randomBlocks);
         this.generateBox(world, sbb, length - i, -sl, 0, length - i, 0, 0, false, rand, this.deco.randomBlocks);
         this.generateBox(world, sbb, length - i, -sl, 6, length - i, 0, 6, false, rand, this.deco.randomBlocks);
      }

      this.generateBox(world, sbb, 0, 2, 1, 0, 7, 1, this.deco.pillarState, this.deco.pillarState, false);
      this.generateBox(world, sbb, 0, 2, 5, 0, 7, 5, this.deco.pillarState, this.deco.pillarState, false);
      this.generateBox(world, sbb, 0, 6, 2, 0, 6, 4, this.deco.accentState, this.deco.accentState, false);
      this.placeBlock(world, this.deco.pillarState, 0, 7, 3, sbb);
      this.generateBox(world, sbb, length, 2, 1, length, 7, 1, this.deco.pillarState, this.deco.pillarState, false);
      this.generateBox(world, sbb, length, 2, 5, length, 7, 5, this.deco.pillarState, this.deco.pillarState, false);
      this.generateBox(world, sbb, length, 6, 2, length, 6, 4, this.deco.accentState, this.deco.accentState, false);
      this.placeBlock(world, this.deco.pillarState, length, 7, 3, sbb);
   }
}
