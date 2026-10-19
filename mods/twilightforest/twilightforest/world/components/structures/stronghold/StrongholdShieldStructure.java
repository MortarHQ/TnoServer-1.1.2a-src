package twilightforest.world.components.structures.stronghold;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;

public class StrongholdShieldStructure extends KnightStrongholdComponent {
   public StrongholdShieldStructure(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFSShield.get(), nbt);
      this.spawnListIndex = -1;
   }

   @Override
   public BoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
      return null;
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource randomIn, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      BlockState down = (BlockState)((Block)TFBlocks.STRONGHOLD_SHIELD.get()).defaultBlockState().setValue(DirectionalBlock.FACING, Direction.DOWN);
      BlockState up = (BlockState)((Block)TFBlocks.STRONGHOLD_SHIELD.get()).defaultBlockState().setValue(DirectionalBlock.FACING, Direction.UP);
      BlockState north = (BlockState)((Block)TFBlocks.STRONGHOLD_SHIELD.get()).defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH);
      BlockState south = (BlockState)((Block)TFBlocks.STRONGHOLD_SHIELD.get()).defaultBlockState().setValue(DirectionalBlock.FACING, Direction.SOUTH);
      BlockState west = (BlockState)((Block)TFBlocks.STRONGHOLD_SHIELD.get()).defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST);
      BlockState east = (BlockState)((Block)TFBlocks.STRONGHOLD_SHIELD.get()).defaultBlockState().setValue(DirectionalBlock.FACING, Direction.EAST);
      this.generateBox(
         world,
         sbb,
         this.boundingBox.getXSpan(),
         0,
         0,
         this.boundingBox.getXSpan(),
         this.boundingBox.getYSpan(),
         this.boundingBox.getZSpan(),
         west,
         west,
         false
      );
      this.generateBox(world, sbb, 0, 0, 0, 0, this.boundingBox.getYSpan(), this.boundingBox.getZSpan(), east, east, false);
      this.generateBox(
         world,
         sbb,
         0,
         0,
         this.boundingBox.getZSpan(),
         this.boundingBox.getXSpan(),
         this.boundingBox.getYSpan(),
         this.boundingBox.getZSpan(),
         north,
         north,
         false
      );
      this.generateBox(world, sbb, 0, 0, 0, this.boundingBox.getXSpan(), this.boundingBox.getYSpan(), 0, south, south, false);
      this.generateBox(world, sbb, 0, 0, 0, this.boundingBox.getXSpan(), 0, this.boundingBox.getZSpan(), up, up, false);
      this.generateBox(
         world,
         sbb,
         0,
         this.boundingBox.getYSpan(),
         0,
         this.boundingBox.getXSpan(),
         this.boundingBox.getYSpan(),
         this.boundingBox.getZSpan(),
         down,
         down,
         false
      );
   }
}
