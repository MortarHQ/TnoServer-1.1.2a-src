package twilightforest.world.components.structures.lichtower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;

public class TowerRoofStairsComponent extends TowerRoofComponent {
   public TowerRoofStairsComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRSt.get(), nbt);
   }

   public TowerRoofStairsComponent(int i, TowerWingComponent wing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFLTRSt.get(), i, x, y, z);
      this.setOrientation(Direction.SOUTH);
      this.size = wing.size;
      this.height = this.size / 2;
      this.makeCapBB(wing);
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      BlockState birchSlab = Blocks.BIRCH_SLAB.defaultBlockState();
      BlockState birchPlanks = Blocks.BIRCH_PLANKS.defaultBlockState();
      BlockState birchStairsNorth = (BlockState)Blocks.BIRCH_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
      BlockState birchStairsSouth = (BlockState)Blocks.BIRCH_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
      BlockState birchStairsEast = (BlockState)Blocks.BIRCH_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
      BlockState birchStairsWest = (BlockState)Blocks.BIRCH_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);

      for (int y = 0; y <= this.height; y++) {
         int min = y;
         int max = this.size - y - 1;

         for (int x = min; x <= max; x++) {
            for (int z = min; z <= max; z++) {
               if (x == min) {
                  if (z != min && z != max) {
                     this.placeBlock(world, birchStairsWest, x, y, z, sbb);
                  } else {
                     this.placeBlock(world, birchSlab, x, y, z, sbb);
                  }
               } else if (x == max) {
                  if (z != min && z != max) {
                     this.placeBlock(world, birchStairsEast, x, y, z, sbb);
                  } else {
                     this.placeBlock(world, birchSlab, x, y, z, sbb);
                  }
               } else if (z == max) {
                  this.placeBlock(world, birchStairsSouth, x, y, z, sbb);
               } else if (z == min) {
                  this.placeBlock(world, birchStairsNorth, x, y, z, sbb);
               } else {
                  this.placeBlock(world, birchPlanks, x, y, z, sbb);
               }
            }
         }
      }
   }
}
