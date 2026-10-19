package net.pixeldreamstudios.mythika.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.entity.MinotaurEntity;
import net.pixeldreamstudios.mythika.registry.EntityRegistry;
import net.pixeldreamstudios.mythika.registry.StructureRegistry;

public class LabyrinthPiece extends StructurePiece {
   private static final int GUARDIAN_STEPS = 10;
   private static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   private static final ResourceKey<LootTable> LOOT = ResourceKey.create(Registries.LOOT_TABLE, Mythika.id("chests/labyrinth"));
   private final LabyrinthLayout layout;
   private final int floorY;
   private final int entranceCell;
   private boolean spawnedGuardian;

   public LabyrinthPiece(BoundingBox box, int floorY, LabyrinthLayout layout, int entranceCell) {
      super(StructureRegistry.LABYRINTH_PIECE.get(), 0, box);
      this.setOrientation(null);
      this.layout = layout;
      this.floorY = floorY;
      this.entranceCell = entranceCell;
   }

   public LabyrinthPiece(CompoundTag tag) {
      super(StructureRegistry.LABYRINTH_PIECE.get(), tag);
      this.layout = LabyrinthLayout.fromBytes(tag.getInt("Cells"), tag.getByteArray("Open"));
      this.floorY = tag.getInt("FloorY");
      this.entranceCell = tag.getInt("EntranceCell");
      this.spawnedGuardian = tag.getBoolean("Guardian");
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putInt("Cells", this.layout.cells());
      tag.putByteArray("Open", this.layout.toBytes());
      tag.putInt("FloorY", this.floorY);
      tag.putInt("EntranceCell", this.entranceCell);
      tag.putBoolean("Guardian", this.spawnedGuardian);
   }

   public void postProcess(
      WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos
   ) {
      int span = this.layout.span();
      int originX = this.boundingBox.minX();
      int originZ = this.boundingBox.minZ();
      boolean[] mask = this.layout.carveMask();
      int coreLow = LabyrinthLayout.cellOrigin(this.layout.coreMin());
      int coreHigh = LabyrinthLayout.cellOrigin(this.layout.coreMax()) + 5 - 1;
      int xFrom = Math.max(originX, box.minX());
      int xTo = Math.min(originX + span - 1, box.maxX());
      int zFrom = Math.max(originZ, box.minZ());
      int zTo = Math.min(originZ + span - 1, box.maxZ());

      for (int x = xFrom; x <= xTo; x++) {
         for (int z = zFrom; z <= zTo; z++) {
            int lx = x - originX;
            int lz = z - originZ;
            boolean core = lx >= coreLow && lx <= coreHigh && lz >= coreLow && lz <= coreHigh;
            int air = this.floorY + (core ? 12 : 7) - 1;
            this.placeBlock(level, core ? LabyrinthBlocks.paved(x, z) : LabyrinthBlocks.floor(x, z), x, this.floorY - 1, z, box);
            boolean open = mask[lx + lz * span];

            for (int y = this.floorY; y <= air; y++) {
               this.placeBlock(level, open ? CAVE_AIR : LabyrinthBlocks.wall(x, y, z), x, y, z, box);
            }

            this.placeBlock(level, LabyrinthBlocks.ceiling(x, z), x, air + 1, z, box);
            boolean lip = !core && lx >= coreLow - 1 && lx <= coreHigh + 1 && lz >= coreLow - 1 && lz <= coreHigh + 1;
            if (lip) {
               for (int y = air + 2; y <= this.floorY + 12; y++) {
                  this.placeBlock(level, LabyrinthBlocks.wall(x, y, z), x, y, z, box);
               }
            }
         }
      }

      this.dressDeadEnds(level, box, originX, originZ);
      this.lightCorridors(level, box, originX, originZ);
      this.dressCore(level, box, random, originX, originZ, mask);
      this.spawnGuardian(level, box, random, originX, originZ);
   }

   private void dressDeadEnds(WorldGenLevel level, BoundingBox box, int originX, int originZ) {
      int offset = 2;

      for (int cz = 0; cz < this.layout.cells(); cz++) {
         for (int cx = 0; cx < this.layout.cells(); cx++) {
            if (!this.layout.isCore(cx, cz) && this.layout.isDeadEnd(cx, cz)) {
               int x = originX + LabyrinthLayout.cellOrigin(cx) + offset;
               int z = originZ + LabyrinthLayout.cellOrigin(cz) + offset;
               int exit = this.layout.deadEndExit(cx, cz);
               int back = (exit + 2) % 4;
               int bx = LabyrinthLayout.stepX(back);
               int bz = LabyrinthLayout.stepZ(back);

               for (int side = -1; side <= 1; side += 2) {
                  int wx = x + offset * (bx + side * bz);
                  int wz = z + offset * (bz - side * bx);
                  if (LabyrinthBlocks.variant(wx, 0, wz, 11) % 100 < 55) {
                     for (int y = this.floorY; y <= this.floorY + 2; y++) {
                        this.placeBlock(level, Blocks.COBWEB.defaultBlockState(), wx, y, wz, box);
                     }
                  }
               }

               if (LabyrinthBlocks.variant(x, 0, z, 12) % 100 < 30) {
                  int facing = RotationSegment.convertToSegment(DIRECTIONS[exit].getOpposite());
                  this.placeBlock(level, (BlockState)Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, facing), x, this.floorY, z, box);
               }
            }
         }
      }
   }

   private void lightCorridors(WorldGenLevel level, BoundingBox box, int originX, int originZ) {
      boolean[] lit = this.layout.lightCells();
      int offset = 2;

      for (int cz = 0; cz < this.layout.cells(); cz++) {
         for (int cx = 0; cx < this.layout.cells(); cx++) {
            if (!this.layout.isCore(cx, cz) && lit[this.layout.cellIndex(cx, cz)]) {
               this.hangLantern(level, box, originX + LabyrinthLayout.cellOrigin(cx) + offset, originZ + LabyrinthLayout.cellOrigin(cz) + offset, 4, 7);
            }
         }
      }
   }

   private void hangLantern(WorldGenLevel level, BoundingBox box, int x, int z, int height, int ceiling) {
      for (int y = height + 1; y < ceiling; y++) {
         this.placeBlock(level, LabyrinthBlocks.CHAIN, x, this.floorY + y, z, box);
      }

      this.placeBlock(level, LabyrinthBlocks.HANGING_LANTERN, x, this.floorY + height, z, box);
   }

   private void dressCore(WorldGenLevel level, BoundingBox box, RandomSource random, int originX, int originZ, boolean[] mask) {
      int span = this.layout.span();
      int offset = 2;

      for (int cz = this.layout.coreMin(); cz <= this.layout.coreMax(); cz++) {
         for (int cx = this.layout.coreMin(); cx <= this.layout.coreMax(); cx++) {
            this.hangLantern(level, box, originX + LabyrinthLayout.cellOrigin(cx) + offset, originZ + LabyrinthLayout.cellOrigin(cz) + offset, 7, 12);
         }
      }

      int inner = LabyrinthLayout.cellOrigin(this.layout.coreMin());
      int outer = LabyrinthLayout.cellOrigin(this.layout.coreMax()) + 5 - 1;
      int centreX = originX + (inner + outer) / 2;
      int centreZ = originZ + (inner + outer) / 2;
      int edge = (outer - inner) / 2;

      for (int x = originX + inner - 1; x <= originX + outer + 1; x++) {
         for (int z = originZ + inner - 1; z <= originZ + outer + 1; z++) {
            int lx = x - originX;
            int lz = z - originZ;
            if (lx >= inner && lx <= outer && lz >= inner && lz <= outer) {
               if (!mask[lx + lz * span]) {
                  this.placeBlock(level, LabyrinthBlocks.CHISELED, x, this.floorY, z, box);

                  for (int y = this.floorY + 12 - 2; y <= this.floorY + 12 - 1; y++) {
                     this.placeBlock(level, LabyrinthBlocks.CHISELED, x, y, z, box);
                  }
               } else {
                  int r = Math.max(Math.abs(x - centreX), Math.abs(z - centreZ));
                  if (r == 5 || r == edge - 1) {
                     this.placeBlock(level, LabyrinthBlocks.CHISELED, x, this.floorY - 1, z, box);
                  }

                  int half = LabyrinthLayout.daisHalfSteps(r);
                  int courses = half / 2;

                  for (int step = 0; step < courses; step++) {
                     this.placeBlock(
                        level, r == 0 && step == courses - 1 ? LabyrinthBlocks.CHISELED : LabyrinthBlocks.paved(x, z), x, this.floorY + step, z, box
                     );
                  }

                  if (half % 2 == 1) {
                     this.placeBlock(level, LabyrinthBlocks.TREAD_SLAB, x, this.floorY + courses, z, box);
                  }
               }
            } else {
               this.placeBlock(level, LabyrinthBlocks.CHISELED, x, this.floorY + 7, z, box);
               this.placeBlock(level, LabyrinthBlocks.CHISELED, x, this.floorY + 12 - 1, z, box);
            }
         }
      }

      for (int dx = -2; dx <= 2; dx += 4) {
         for (int dz = -2; dz <= 2; dz += 4) {
            this.placeBlock(level, LabyrinthBlocks.STANDING_LANTERN, centreX + dx, this.floorY + 1, centreZ + dz, box);
         }
      }

      this.createChest(level, box, random, centreX, this.floorY + LabyrinthLayout.daisHalfSteps(0) / 2, centreZ, LOOT);
   }

   private void spawnGuardian(WorldGenLevel level, BoundingBox box, RandomSource random, int originX, int originZ) {
      if (!this.spawnedGuardian) {
         int cell = this.layout.cellAtDistance(this.entranceCell, 10);
         int offset = 2;
         BlockPos pos = new BlockPos(
            originX + LabyrinthLayout.cellOrigin(cell % this.layout.cells()) + offset,
            this.floorY,
            originZ + LabyrinthLayout.cellOrigin(cell / this.layout.cells()) + offset
         );
         if (box.isInside(pos)) {
            this.spawnedGuardian = true;
            MinotaurEntity minotaur = (MinotaurEntity)EntityRegistry.MINOTAUR.get().create(level.getLevel());
            if (minotaur != null) {
               minotaur.bindToLabyrinth(
                  new BoundingBox(
                     this.boundingBox.minX(), this.floorY, this.boundingBox.minZ(), this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ()
                  )
               );
               minotaur.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, random.nextFloat() * 360.0F, 0.0F);
               minotaur.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null);
               minotaur.setPersistenceRequired();
               level.addFreshEntity(minotaur);
            }
         }
      }
   }
}
