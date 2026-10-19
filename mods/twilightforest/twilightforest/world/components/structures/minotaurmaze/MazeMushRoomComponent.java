package twilightforest.world.components.structures.minotaurmaze;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.HugeMushroomUtil;

public class MazeMushRoomComponent extends MazeRoomComponent {
   public MazeMushRoomComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMMR.get(), nbt);
   }

   public MazeMushRoomComponent(int i, RandomSource rand, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMMR.get(), i, rand, x, y, z);
      this.setOrientation(Direction.SOUTH);
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);

      for (int x = 1; x < 14; x++) {
         for (int z = 1; z < 14; z++) {
            int dist = (int)Math.round(7.0 / Math.sqrt((7.5 - x) * (7.5 - x) + (7.5 - z) * (7.5 - z)));
            if (rand.nextInt(dist + 1) > 0) {
               this.placeBlock(world, Blocks.MYCELIUM.defaultBlockState(), x, 0, z, sbb);
            }

            if (rand.nextInt(dist) > 0) {
               this.placeBlock(world, (rand.nextBoolean() ? Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM).defaultBlockState(), x, 1, z, sbb);
            }
         }
      }

      BlockState redMushroomBlock = Blocks.RED_MUSHROOM_BLOCK.defaultBlockState();
      BlockState brownMushroomBlock = Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState();
      BlockState stemMushroomBlock = Blocks.MUSHROOM_STEM.defaultBlockState();
      this.makeMediumMushroom(world, sbb, 5, 2, 9, redMushroomBlock);
      this.makeMediumMushroom(world, sbb, 5, 3, 9, redMushroomBlock);
      this.makeMediumMushroom(world, sbb, 9, 2, 5, redMushroomBlock);
      this.makeMediumMushroom(world, sbb, 6, 3, 4, brownMushroomBlock);
      this.makeMediumMushroom(world, sbb, 10, 1, 9, brownMushroomBlock);
      this.placeBlock(world, stemMushroomBlock, 1, 2, 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.CENTER, redMushroomBlock), 1, 3, 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.NORTH_WEST, redMushroomBlock), 2, 3, 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.NORTH_WEST, redMushroomBlock), 1, 3, 2, sbb);
      this.placeBlock(world, stemMushroomBlock, 14, 3, 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.CENTER, brownMushroomBlock), 14, 4, 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.NORTH_EAST, brownMushroomBlock), 13, 4, 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.NORTH_EAST, brownMushroomBlock), 14, 4, 2, sbb);
      this.placeBlock(world, stemMushroomBlock, 1, 1, 14, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.CENTER, brownMushroomBlock), 1, 2, 14, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.SOUTH_WEST, brownMushroomBlock), 2, 2, 14, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.SOUTH_WEST, brownMushroomBlock), 1, 2, 13, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.CENTER, brownMushroomBlock), 14, 1, 14, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.SOUTH_EAST, brownMushroomBlock), 13, 1, 14, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.SOUTH_EAST, brownMushroomBlock), 14, 1, 13, sbb);
   }

   private void makeMediumMushroom(WorldGenLevel world, BoundingBox sbb, int mx, int my, int mz, BlockState redMushroomBlock) {
      BlockState mushroomStem = (BlockState)((BlockState)Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.DOWN, false))
         .setValue(HugeMushroomBlock.UP, false);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.CENTER, redMushroomBlock), mx, my, mz, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.WEST, redMushroomBlock), mx + 1, my, mz, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.NORTH_WEST, redMushroomBlock), mx + 1, my, mz + 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.NORTH, redMushroomBlock), mx, my, mz + 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.NORTH_EAST, redMushroomBlock), mx - 1, my, mz + 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.EAST, redMushroomBlock), mx - 1, my, mz, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.SOUTH_EAST, redMushroomBlock), mx - 1, my, mz - 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.SOUTH, redMushroomBlock), mx, my, mz - 1, sbb);
      this.placeBlock(world, HugeMushroomUtil.getState(HugeMushroomUtil.HugeMushroomType.SOUTH_WEST, redMushroomBlock), mx + 1, my, mz - 1, sbb);

      for (int y = 1; y < my; y++) {
         this.placeBlock(world, mushroomStem, mx, y, mz, sbb);
      }
   }
}
