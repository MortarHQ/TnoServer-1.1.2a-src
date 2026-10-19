package twilightforest.world.components.structures.minotaurmaze;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;

public class MazeDeadEndTripwireChestComponent extends MazeDeadEndChestComponent {
   public MazeDeadEndTripwireChestComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMDETC.get(), nbt);
   }

   public MazeDeadEndTripwireChestComponent(int i, int x, int y, int z, Direction rotation) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMDETC.get(), i, x, y, z, rotation);
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);
      this.placeTripwire(world, 1, 1, 2, 3, Direction.EAST, sbb);
      BlockState tnt = (BlockState)Blocks.TNT.defaultBlockState().setValue(TntBlock.UNSTABLE, true);
      this.placeBlock(world, tnt, 0, 0, 2, sbb);
      this.placeBlock(world, AIR, 0, -1, 2, sbb);
      this.placeBlock(world, AIR, 1, -1, 2, sbb);
      this.placeBlock(world, tnt, 2, 0, 4, sbb);
      this.placeBlock(world, tnt, 3, 0, 4, sbb);
      this.placeBlock(world, tnt, 2, 0, 3, sbb);
      this.placeBlock(world, tnt, 3, 0, 3, sbb);
   }
}
