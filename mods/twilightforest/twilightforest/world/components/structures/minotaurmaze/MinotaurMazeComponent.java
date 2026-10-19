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
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.TFMaze;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.selectors.MazestoneRandomBlockSelectoryFactory;

public class MinotaurMazeComponent extends TFStructureComponentOld {
   @Autowired
   private static MazestoneRandomBlockSelectoryFactory mazestone;
   final TFMaze maze;
   final int[] rcoords;
   private final int level;

   public MinotaurMazeComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMaze.get(), nbt);
      this.level = nbt.getInt("mazeLevel");
      this.rcoords = nbt.getIntArray("roomCoords");
      RandomSource random = RandomSource.create(this.getSeed());
      this.maze = new TFMaze(this.getMazeSize(), this.getMazeSize(), random);
      this.configureMaze();

      for (int i = 2; i < this.rcoords.length; i++) {
         this.rcoords[i] = 0;
      }

      this.addRoomsToMaze(this.rcoords[0], this.rcoords[1], (this.rcoords.length + 1) / 2, random);
      this.maze.generateRecursiveBacktracker(0, 0, random);
   }

   public MinotaurMazeComponent(int index, int x, int y, int z, int entranceX, int entranceZ, int level, RandomSource random) {
      super((StructurePieceType)TFStructurePieceTypes.TFMMaze.get(), index, x, y, z);
      this.setOrientation(Direction.SOUTH);
      this.level = level;
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(
         x, y, z, -this.getRadius(), 0, -this.getRadius(), this.getRadius() * 2 + 2, 5, this.getRadius() * 2 + 2, Direction.SOUTH, false
      );
      random.setSeed(this.getSeed());
      this.maze = new TFMaze(this.getMazeSize(), this.getMazeSize(), random);
      this.configureMaze();
      int nrooms = 7;
      this.rcoords = new int[nrooms * 2];
      this.addRoomsToMaze(entranceX, entranceZ, nrooms, random);
      this.maze.generateRecursiveBacktracker(0, 0, random);
   }

   private void addRoomsToMaze(int entranceX, int entranceZ, int nrooms, RandomSource rand) {
      this.rcoords[0] = entranceX;
      this.rcoords[1] = entranceZ;
      this.maze.carveRoom1(entranceX, entranceZ);

      for (int i = 1; i < nrooms; i++) {
         int rx;
         int rz;
         do {
            rx = rand.nextInt(this.getMazeSize() - 2) + 1;
            rz = rand.nextInt(this.getMazeSize() - 2) + 1;
         } while (this.isNearRoom(rx, rz, this.rcoords, i == 1 ? 7 : 4));

         this.maze.carveRoom1(rx, rz);
         this.rcoords[i * 2] = rx;
         this.rcoords[i * 2 + 1] = rz;
      }
   }

   private long getSeed() {
      return this.boundingBox.minX() * 90342903L + this.boundingBox.minY() * 90342903L ^ this.boundingBox.minZ();
   }

   public MinotaurMazeComponent(int index, int x, int y, int z, int level, RandomSource random) {
      this(index, x, y, z, 11, 11, level, random);
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putInt("mazeLevel", this.level);
      tagCompound.putIntArray("roomCoords", this.rcoords);
   }

   protected MazeRoomComponent makeRoom(RandomSource random, int i, int dx, int dz) {
      int worldX = this.boundingBox.minX() + dx * 5 - 4;
      int worldY = this.boundingBox.minY();
      int worldZ = this.boundingBox.minZ() + dz * 5 - 4;
      MazeRoomComponent room;
      if (i == 0) {
         room = new MazeRoomComponent((StructurePieceType)TFStructurePieceTypes.TFMMR.get(), 3 + i, random, worldX, worldY, worldZ);
      } else if (i == 1) {
         if (this.level == 1) {
            room = new MazeRoomExitComponent(3 + i, random, worldX, worldY, worldZ);
         } else {
            room = new MazeRoomBossComponent(3 + i, random, worldX, worldY, worldZ);
         }
      } else if (i != 2 && i != 3) {
         if (i == 4) {
            if (this.level == 1) {
               room = new MazeRoomFountainComponent(3 + i, random, worldX, worldY, worldZ);
            } else {
               room = new MazeRoomVaultComponent(3 + i, random, worldX, worldY, worldZ);
            }
         } else {
            room = new MazeRoomSpawnerChestsComponent(3 + i, random, worldX, worldY, worldZ);
         }
      } else if (this.level == 1) {
         room = new MazeRoomCollapseComponent(3 + i, random, worldX, worldY, worldZ);
      } else {
         room = new MazeMushRoomComponent(3 + i, random, worldX, worldY, worldZ);
      }

      return room;
   }

   protected void decorateDeadEndsCorridors(RandomSource random, StructurePieceAccessor list) {
      for (int x = 0; x < this.maze.width; x++) {
         for (int z = 0; z < this.maze.depth; z++) {
            TFStructureComponentOld component = null;
            if (!this.maze.isWall(x, z, x - 1, z) && this.maze.isWall(x, z, x + 1, z) && this.maze.isWall(x, z, x, z - 1) && this.maze.isWall(x, z, x, z + 1)) {
               component = this.makeDeadEnd(random, x, z, Direction.EAST);
            }

            if (this.maze.isWall(x, z, x - 1, z) && !this.maze.isWall(x, z, x + 1, z) && this.maze.isWall(x, z, x, z - 1) && this.maze.isWall(x, z, x, z + 1)) {
               component = this.makeDeadEnd(random, x, z, Direction.WEST);
            }

            if (this.maze.isWall(x, z, x - 1, z) && this.maze.isWall(x, z, x + 1, z) && !this.maze.isWall(x, z, x, z - 1) && this.maze.isWall(x, z, x, z + 1)) {
               component = this.makeDeadEnd(random, x, z, Direction.SOUTH);
            }

            if (this.maze.isWall(x, z, x - 1, z) && this.maze.isWall(x, z, x + 1, z) && this.maze.isWall(x, z, x, z - 1) && !this.maze.isWall(x, z, x, z + 1)) {
               component = this.makeDeadEnd(random, x, z, Direction.NORTH);
            }

            if (!this.maze.isWall(x, z, x - 1, z)
               && !this.maze.isWall(x, z, x + 1, z)
               && this.maze.isWall(x, z, x, z - 1)
               && this.maze.isWall(x, z, x, z + 1)
               && this.maze.isWall(x - 1, z, x - 1, z - 1)
               && this.maze.isWall(x - 1, z, x - 1, z + 1)
               && this.maze.isWall(x + 1, z, x + 1, z - 1)
               && this.maze.isWall(x + 1, z, x + 1, z + 1)) {
               component = this.makeCorridor(random, x, z, Direction.WEST);
            }

            if (!this.maze.isWall(x, z, x, z - 1)
               && !this.maze.isWall(x, z, x, z + 1)
               && this.maze.isWall(x, z, x - 1, z)
               && this.maze.isWall(x, z, x + 1, z)
               && this.maze.isWall(x, z - 1, x - 1, z - 1)
               && this.maze.isWall(x, z - 1, x + 1, z - 1)
               && this.maze.isWall(x, z + 1, x - 1, z + 1)
               && this.maze.isWall(x, z + 1, x + 1, z + 1)) {
               component = this.makeCorridor(random, x, z, Direction.SOUTH);
            }

            if (component != null) {
               list.addPiece(component);
               component.addChildren(this, list, random);
            }
         }
      }
   }

   protected MazeDeadEndComponent makeDeadEnd(RandomSource random, int dx, int dz, Direction rotation) {
      int worldX = this.boundingBox.minX() + dx * 5 + 1;
      int worldY = this.boundingBox.minY();
      int worldZ = this.boundingBox.minZ() + dz * 5 + 1;
      int decorationType = random.nextInt(8);

      return switch (decorationType) {
         case 1 -> new MazeDeadEndChestComponent((StructurePieceType)TFStructurePieceTypes.TFMMDEC.get(), 4, worldX, worldY, worldZ, rotation);
         case 2 -> random.nextBoolean()
            ? new MazeDeadEndTripwireChestComponent(4, worldX, worldY, worldZ, rotation)
            : new MazeDeadEndTrappedChestComponent(4, worldX, worldY, worldZ, rotation);
         case 3 -> new MazeDeadEndTorchesComponent(4, worldX, worldY, worldZ, rotation);
         case 4 -> new MazeDeadEndFountainComponent((StructurePieceType)TFStructurePieceTypes.TFMMDEF.get(), 4, worldX, worldY, worldZ, rotation);
         case 5 -> new MazeDeadEndFountainLavaComponent(4, worldX, worldY, worldZ, rotation);
         case 6 -> new MazeDeadEndPaintingComponent(4, worldX, worldY, worldZ, rotation);
         case 7 -> this.level == 1
            ? new MazeDeadEndRootsComponent((StructurePieceType)TFStructurePieceTypes.TFMMDER.get(), 4, worldX, worldY, worldZ, rotation)
            : new MazeDeadEndShroomsComponent(4, worldX, worldY, worldZ, rotation);
         default -> new MazeDeadEndComponent((StructurePieceType)TFStructurePieceTypes.TFMMDE.get(), 4, worldX, worldY, worldZ, rotation);
      };
   }

   protected MazeCorridorComponent makeCorridor(RandomSource random, int dx, int dz, Direction rotation) {
      int worldX = this.boundingBox.minX() + dx * 5 + 1;
      int worldY = this.boundingBox.minY();
      int worldZ = this.boundingBox.minZ() + dz * 5 + 1;
      int decorationType = random.nextInt(5);

      return switch (decorationType) {
         case 1 -> new MazeCorridorComponent((StructurePieceType)TFStructurePieceTypes.TFMMC.get(), 4, worldX, worldY, worldZ, rotation);
         case 2 -> new MazeCorridorIronFenceComponent(4, worldX, worldY, worldZ, rotation);
         case 3 -> null;
         case 4 -> this.level == 1
            ? new MazeCorridorRootsComponent(4, worldX, worldY, worldZ, rotation)
            : new MazeCorridorShroomsComponent(4, worldX, worldY, worldZ, rotation);
         default -> null;
      };
   }

   public void addChildren(StructurePiece structurecomponent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(structurecomponent, list, random);
      if (this.level == 1) {
         int centerX = this.boundingBox.minX() + (this.boundingBox.maxX() - this.boundingBox.minX()) / 2;
         int centerZ = this.boundingBox.minZ() + (this.boundingBox.maxZ() - this.boundingBox.minZ()) / 2;
         MinotaurMazeComponent maze = new MinotaurMazeComponent(1, centerX, this.boundingBox.minY() - 10, centerZ, this.rcoords[2], this.rcoords[3], 2, random);
         list.addPiece(maze);
         maze.addChildren(this, list, random);
      }

      for (int i = 0; i < this.rcoords.length / 2; i++) {
         int dx = this.rcoords[i * 2];
         int dz = this.rcoords[i * 2 + 1];
         MazeRoomComponent room = this.makeRoom(random, i, dx, dz);
         list.addPiece(room);
         room.addChildren(this, list, random);
      }

      this.decorateDeadEndsCorridors(random, list);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
      BlockState stone = Blocks.STONE.defaultBlockState();
      if (this.level == 2) {
         this.generateBox(world, sbb, 0, -1, 0, this.getDiameter() + 2, 6, this.getDiameter() + 2, bedrock, AIR, false);
      }

      this.generateAirBox(world, sbb, 1, 1, 1, this.getDiameter(), 4, this.getDiameter());
      boolean onlyReplaceCeiling = this.level == 1;
      this.generateBox(
         world,
         sbb,
         1,
         5,
         1,
         this.getDiameter() + 1,
         5,
         this.getDiameter() + 1,
         ((Block)TFBlocks.MAZESTONE.get()).defaultBlockState(),
         stone,
         onlyReplaceCeiling
      );
      this.generateBox(
         world, sbb, 1, 0, 1, this.getDiameter() + 1, 0, this.getDiameter() + 1, ((Block)TFBlocks.MAZESTONE_MOSAIC.get()).defaultBlockState(), stone, false
      );
      this.maze.copyToStructure(world, manager, generator, 1, 2, 1, this, sbb, rand);
   }

   private void configureMaze() {
      this.maze.headBlockState = ((Block)TFBlocks.DECORATIVE_MAZESTONE.get()).defaultBlockState();
      this.maze.wallBlockState = ((Block)TFBlocks.MAZESTONE_BRICK.get()).defaultBlockState();
      this.maze.rootBlockState = ((Block)TFBlocks.DECORATIVE_MAZESTONE.get()).defaultBlockState();
      this.maze.pillarBlockState = ((Block)TFBlocks.CUT_MAZESTONE.get()).defaultBlockState();
      this.maze.wallBlocks = mazestone.make();
      this.maze.torchRarity = 0.05F;
      this.maze.tall = 2;
      this.maze.head = 1;
      this.maze.roots = 1;
      this.maze.oddBias = 4;
   }

   public int getMazeSize() {
      return 22;
   }

   public int getRadius() {
      return (int)(this.getMazeSize() * 2.5);
   }

   public int getDiameter() {
      return this.getMazeSize() * 5;
   }

   protected boolean isNearRoom(int dx, int dz, int[] rcoords, int range) {
      if (dx == 1 && dz == 1) {
         return true;
      }

      for (int i = 0; i < rcoords.length / 2; i++) {
         int rx = rcoords[i * 2];
         int rz = rcoords[i * 2 + 1];
         if ((rx != 0 || rz != 0) && Math.abs(dx - rx) < range && Math.abs(dz - rz) < range) {
            return true;
         }
      }

      return false;
   }
}
