package twilightforest.world.components.structures.minotaurmaze;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;

public class MazeDeadEndTrappedChestComponent extends MazeDeadEndComponent {
   public MazeDeadEndTrappedChestComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMDETrC.get(), nbt);
   }

   public MazeDeadEndTrappedChestComponent(int i, int x, int y, int z, Direction rotation) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMDETrC.get(), i, x, y, z, rotation);
      this.setOrientation(rotation);
      this.spawnListIndex = Integer.MAX_VALUE;
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.placeBlock(world, Blocks.OAK_PLANKS.defaultBlockState(), 2, 1, 4, sbb);
      this.placeBlock(world, Blocks.OAK_PLANKS.defaultBlockState(), 3, 1, 4, sbb);
      this.placeBlock(world, getStairState(Blocks.OAK_STAIRS.defaultBlockState(), Direction.NORTH, false), 2, 1, 3, sbb);
      this.placeBlock(world, getStairState(Blocks.OAK_STAIRS.defaultBlockState(), Direction.NORTH, false), 3, 1, 3, sbb);
      this.setDoubleLootChest(world, 2, 2, 4, 3, 2, 4, Direction.SOUTH, TFLootTables.LABYRINTH_DEAD_END, sbb, true);
      this.generateBox(world, sbb, 1, 1, 0, 4, 3, 1, ((Block)TFBlocks.CUT_MAZESTONE.get()).defaultBlockState(), AIR, false);
      this.generateBox(world, sbb, 1, 4, 0, 4, 4, 1, ((Block)TFBlocks.DECORATIVE_MAZESTONE.get()).defaultBlockState(), AIR, false);
      this.generateBox(world, sbb, 2, 1, 0, 3, 3, 1, Blocks.IRON_BARS.defaultBlockState(), AIR, false);
      BlockState tnt = Blocks.TNT.defaultBlockState();
      this.placeBlock(world, tnt, 2, 0, 3, sbb);
      this.placeBlock(world, tnt, 3, 0, 3, sbb);
      this.placeBlock(world, tnt, 2, 0, 4, sbb);
      this.placeBlock(world, tnt, 3, 0, 4, sbb);
   }
}
