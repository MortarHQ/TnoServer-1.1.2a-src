package twilightforest.world.components.structures.stronghold;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;

public class StrongholdSmallStairsComponent extends KnightStrongholdComponent {
   private boolean enterBottom;
   public boolean hasTreasure;
   public boolean chestTrapped;

   public StrongholdSmallStairsComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFSSS.get(), nbt);
      this.enterBottom = nbt.getBoolean("enterBottom");
      this.hasTreasure = nbt.getBoolean("hasTreasure");
      this.chestTrapped = nbt.getBoolean("chestTrapped");
   }

   public StrongholdSmallStairsComponent(int i, Direction facing, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFSSS.get(), i, facing, x, y, z);
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putBoolean("enterBottom", this.enterBottom);
      tagCompound.putBoolean("hasTreasure", this.hasTreasure);
      tagCompound.putBoolean("chestTrapped", this.chestTrapped);
   }

   @Override
   public BoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
      if (y > -15) {
         this.enterBottom = false;
      } else if (y < -21) {
         this.enterBottom = true;
      } else {
         this.enterBottom = (z & 1) == 0;
      }

      return this.enterBottom ? BoundingBox.orientBox(x, y, z, -4, -1, 0, 9, 14, 9, facing) : BoundingBox.orientBox(x, y, z, -4, -8, 0, 9, 14, 9, facing);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(parent, list, random);
      if (this.enterBottom) {
         this.addDoor(4, 1, 0);
         this.addNewComponent(parent, list, random, Rotation.NONE, 4, 8, 9);
      } else {
         this.addDoor(4, 8, 0);
         this.addNewComponent(parent, list, random, Rotation.NONE, 4, 1, 9);
      }

      this.hasTreasure = random.nextBoolean();
      this.chestTrapped = random.nextInt(3) == 0;
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.placeStrongholdWalls(world, sbb, 0, 0, 0, 8, 13, 8, rand, this.deco.randomBlocks);
      this.generateBox(world, sbb, 1, 7, 1, 7, 7, 7, this.deco.platformState, Blocks.AIR.defaultBlockState(), false);
      this.generateAirBox(world, sbb, 2, 7, 2, 6, 7, 6);
      Rotation rotation = this.enterBottom ? Rotation.NONE : Rotation.CLOCKWISE_180;

      for (int y = 1; y < 8; y++) {
         for (int x = 3; x < 6; x++) {
            this.setBlockStateRotated(world, Blocks.AIR.defaultBlockState(), x, y + 1, y, rotation, sbb);
            this.setBlockStateRotated(world, (BlockState)this.deco.stairState.setValue(StairBlock.FACING, Direction.NORTH), x, y, y, rotation, sbb);
            this.setBlockStateRotated(world, this.deco.blockState, x, y - 1, y, rotation, sbb);
         }
      }

      if (this.hasTreasure) {
         this.placeTreasureRotated(world, 4, 1, 6, this.getOrientation().getOpposite(), rotation, TFLootTables.STRONGHOLD_CACHE, this.chestTrapped, sbb);
         if (this.chestTrapped) {
            this.setBlockStateRotated(world, Blocks.TNT.defaultBlockState(), 4, 0, 6, rotation, sbb);
         }

         for (int z = 5; z < 8; z++) {
            this.setBlockStateRotated(world, (BlockState)this.deco.stairState.setValue(StairBlock.FACING, Direction.WEST), 3, 1, z, rotation, sbb);
            this.setBlockStateRotated(world, (BlockState)this.deco.stairState.setValue(StairBlock.FACING, Direction.EAST), 5, 1, z, rotation, sbb);
         }

         this.setBlockStateRotated(world, (BlockState)this.deco.stairState.setValue(StairBlock.FACING, Direction.NORTH), 4, 1, 5, rotation, sbb);
         this.setBlockStateRotated(world, (BlockState)this.deco.stairState.setValue(StairBlock.FACING, Direction.SOUTH), 4, 1, 7, rotation, sbb);
         this.setBlockStateRotated(world, (BlockState)this.deco.stairState.setValue(StairBlock.FACING, Direction.NORTH), 4, 2, 6, rotation, sbb);
      }

      if (this.enterBottom) {
         this.placeWallStatue(world, 4, 8, 1, Rotation.CLOCKWISE_180, sbb);
      } else {
         this.placeWallStatue(world, 4, 8, 7, Rotation.NONE, sbb);
      }

      this.placeDoors(world, sbb);
   }
}
