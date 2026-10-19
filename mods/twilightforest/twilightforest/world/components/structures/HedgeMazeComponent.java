package twilightforest.world.components.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.LootTable;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;
import twilightforest.util.BoundingBoxUtils;

public class HedgeMazeComponent extends TFStructureComponentOld {
   private static final int MSIZE = 16;
   private static final int RADIUS = 25;
   private static final int DIAMETER = 50;
   private static final int FLOOR_LEVEL = 0;

   public HedgeMazeComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFHedge.get(), nbt);
      this.boundingBox = BoundingBoxUtils.NBTToBoundingBox(nbt);
   }

   public HedgeMazeComponent(int i, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFHedge.get(), i, x, y, z);
      this.setOrientation(Direction.SOUTH);
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, -25, -3, -25, 50, 10, 50, Direction.SOUTH, false);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      rand.setSeed(this.getMazeSeed(world));
      TFMaze maze = new TFMaze(16, 16, rand);
      maze.oddBias = 2;
      maze.torchBlockState = ((Block)TFBlocks.FIREFLY.get()).defaultBlockState();
      maze.wallBlockState = ((Block)TFBlocks.HEDGE.get()).defaultBlockState();
      maze.type = 4;
      maze.tall = 3;
      maze.roots = 3;

      for (int fx = 0; fx <= 50; fx++) {
         for (int fz = 0; fz <= 50; fz++) {
            this.placeBlock(world, Blocks.GRASS_BLOCK.defaultBlockState(), fx, -1, fz, sbb);
         }
      }

      BlockState northJacko = (BlockState)Blocks.JACK_O_LANTERN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.SOUTH);
      BlockState southJacko = (BlockState)Blocks.JACK_O_LANTERN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.NORTH);
      BlockState westJacko = (BlockState)Blocks.JACK_O_LANTERN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.EAST);
      BlockState eastJacko = (BlockState)Blocks.JACK_O_LANTERN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.WEST);
      this.placeBlock(world, westJacko, 0, 0, 24, sbb);
      this.placeBlock(world, westJacko, 0, 0, 29, sbb);
      this.placeBlock(world, eastJacko, 50, 0, 24, sbb);
      this.placeBlock(world, eastJacko, 50, 0, 29, sbb);
      this.placeBlock(world, northJacko, 24, 0, 0, sbb);
      this.placeBlock(world, northJacko, 29, 0, 0, sbb);
      this.placeBlock(world, southJacko, 24, 0, 50, sbb);
      this.placeBlock(world, southJacko, 29, 0, 50, sbb);
      int nrooms = 5;
      int[] rcoords = new int[nrooms * 2];

      while (!maze.allCellsNonZero()) {
         maze.resetCells();
         rcoords = new int[nrooms * 2];

         for (int i = 0; i < nrooms; i++) {
            int rx;
            int rz;
            do {
               rx = rand.nextInt(14) + 1;
               rz = rand.nextInt(14) + 1;
            } while (this.isNearRoom(rx, rz, rcoords));

            maze.carveRoom1(rx, rz);
            rcoords[i * 2] = rx;
            rcoords[i * 2 + 1] = rz;
         }

         maze.generateRecursiveBacktracker(0, 0, rand);
      }

      maze.add4Exits();
      maze.copyToStructure(world, manager, generator, 1, 0, 1, this, sbb, rand);
      this.decorate3x3Rooms(world, rcoords, sbb);
   }

   private long getMazeSeed(WorldGenLevel world) {
      return world.getSeed() + (long)this.boundingBox.minX() * this.boundingBox.minZ();
   }

   private boolean isNearRoom(int dx, int dz, int[] rcoords) {
      if (dx == 1 && dz == 1) {
         return true;
      }

      for (int i = 0; i < rcoords.length / 2; i++) {
         int rx = rcoords[i * 2];
         int rz = rcoords[i * 2 + 1];
         if ((rx != 0 || rz != 0) && Math.abs(dx - rx) < 3 && Math.abs(dz - rz) < 3) {
            return true;
         }
      }

      return false;
   }

   private void decorate3x3Rooms(WorldGenLevel world, int[] rcoords, BoundingBox sbb) {
      for (int i = 0; i < rcoords.length / 2; i++) {
         int dx = rcoords[i * 2];
         int dz = rcoords[i * 2 + 1];
         dx = dx * 3 + 3;
         dz = dz * 3 + 3;
         this.decorate3x3Room(world, dx, dz, sbb);
      }
   }

   private void decorate3x3Room(WorldGenLevel world, int x, int z, BoundingBox sbb) {
      RandomSource roomRNG = RandomSource.create(world.getSeed() ^ x + z);
      this.roomJackO(world, roomRNG, x, z, 8, sbb);
      if (roomRNG.nextInt(4) == 0) {
         this.roomJackO(world, roomRNG, x, z, 8, sbb);
      }

      this.roomSpawner(world, roomRNG, x, z, 8, sbb);
      this.roomTreasure(world, roomRNG, x, z, 8, sbb, TFLootTables.HEDGE_MAZE);
      if (roomRNG.nextInt(4) == 0) {
         this.roomTreasure(world, roomRNG, x, z, 8, sbb, TFLootTables.HEDGE_CLOTH);
      }
   }

   private void roomSpawner(WorldGenLevel world, RandomSource rand, int x, int z, int diameter, BoundingBox sbb) {
      int rx = x + rand.nextInt(diameter) - diameter / 2;
      int rz = z + rand.nextInt(diameter) - diameter / 2;

      EntityType<?> mobID = switch (rand.nextInt(3)) {
         case 1 -> (EntityType)TFEntities.SWARM_SPIDER.get();
         case 2 -> (EntityType)TFEntities.HOSTILE_WOLF.get();
         default -> (EntityType)TFEntities.HEDGE_SPIDER.get();
      };
      this.setSpawner(world, rx, 0, rz, sbb, mobID);
   }

   private void roomTreasure(WorldGenLevel world, RandomSource rand, int x, int z, int diameter, BoundingBox sbb, ResourceKey<LootTable> table) {
      int rx = x + rand.nextInt(diameter) - diameter / 2;
      int rz = z + rand.nextInt(diameter) - diameter / 2;
      int xDiff = x - rx;
      int zDiff = z - rz;
      BlockPos pos = new BlockPos(this.getWorldX(rx, rz), this.getWorldY(0), this.getWorldZ(rx, rz));
      if (sbb.isInside(pos) && world.getBlockState(pos).getBlock() != Blocks.CHEST) {
         Direction facing;
         if (Math.abs(xDiff) > Math.abs(zDiff)) {
            facing = xDiff < 0 ? Direction.WEST : Direction.EAST;
         } else {
            facing = zDiff < 0 ? Direction.NORTH : Direction.SOUTH;
         }

         TFLootTables.generateChest(world, pos, facing, false, table);
      }
   }

   private void roomJackO(WorldGenLevel world, RandomSource rand, int x, int z, int diameter, BoundingBox sbb) {
      int rx = x + rand.nextInt(diameter) - diameter / 2;
      int rz = z + rand.nextInt(diameter) - diameter / 2;
      this.placeBlock(
         world,
         (BlockState)Blocks.JACK_O_LANTERN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.from2DDataValue(rand.nextInt(4))),
         rx,
         0,
         rz,
         sbb
      );
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      BoundingBoxUtils.boundingBoxToExistingNBT(this.boundingBox, tagCompound);
   }

   @Override
   public TerrainAdjustment getTerrainAdjustment() {
      return TerrainAdjustment.BEARD_BOX;
   }
}
