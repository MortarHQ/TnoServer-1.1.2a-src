package net.pixeldreamstudios.mythika.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.pixeldreamstudios.mythika.registry.StructureRegistry;

public class LabyrinthEntrancePiece extends StructurePiece {
   static final int HALL_HALF_WIDTH = 5;
   static final int HALL_DEPTH = 17;
   private static final int HALL_HEIGHT = 9;
   private static final int STAIR_HALF_WIDTH = 3;
   private static final int STAIR_HEADROOM = 5;
   private static final int PORTAL_HEIGHT = 6;
   static final int STAIR_BASE_D = 19;
   private static final int FOOTING_DEPTH = 12;
   private static final int ARCH_SPACING = 16;
   private final int floorY;
   private final int mouthX;
   private final int mouthZ;
   private final Direction outward;
   private final int steps;
   private final int[] profile;

   public LabyrinthEntrancePiece(BoundingBox box, int floorY, int mouthX, int mouthZ, Direction outward, int steps, int[] profile) {
      super(StructureRegistry.LABYRINTH_ENTRANCE.get(), 0, box);
      this.setOrientation(null);
      this.floorY = floorY;
      this.mouthX = mouthX;
      this.mouthZ = mouthZ;
      this.outward = outward;
      this.steps = steps;
      this.profile = profile;
   }

   public LabyrinthEntrancePiece(CompoundTag tag) {
      super(StructureRegistry.LABYRINTH_ENTRANCE.get(), tag);
      this.floorY = tag.getInt("FloorY");
      this.mouthX = tag.getInt("MouthX");
      this.mouthZ = tag.getInt("MouthZ");
      this.outward = Direction.from2DDataValue(tag.getInt("Outward"));
      this.steps = tag.getInt("Steps");
      this.profile = tag.getIntArray("Profile");
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putInt("FloorY", this.floorY);
      tag.putInt("MouthX", this.mouthX);
      tag.putInt("MouthZ", this.mouthZ);
      tag.putInt("Outward", this.outward.get2DDataValue());
      tag.putInt("Steps", this.steps);
      tag.putIntArray("Profile", this.profile);
   }

   public void postProcess(
      WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos
   ) {
      this.openMazeWall(level, box);
      this.buildHall(level, box);
      this.buildStairway(level, box);
   }

   private void openMazeWall(WorldGenLevel level, BoundingBox box) {
      int half = 2;

      for (int d = 0; d < 2; d++) {
         for (int p = -6; p <= 6; p++) {
            for (int y = this.floorY + 7; y <= this.floorY + 9; y++) {
               this.place(level, box, d, p, y, LabyrinthBlocks.wall(this.x(d, p), y, this.z(d, p)));
            }

            if (Math.abs(p) <= half) {
               this.placeBlock(level, LabyrinthBlocks.paved(this.x(d, p), this.z(d, p)), this.x(d, p), this.floorY - 1, this.z(d, p), box);

               for (int y = this.floorY; y < this.floorY + 7; y++) {
                  this.place(level, box, d, p, y, CAVE_AIR);
               }
            }
         }
      }
   }

   private void buildHall(WorldGenLevel level, BoundingBox box) {
      int outerD = 19;

      for (int d = 2; d <= outerD; d++) {
         for (int p = -6; p <= 6; p++) {
            boolean side = Math.abs(p) > 5;
            boolean endWall = d == outerD;
            boolean doorway = endWall && Math.abs(p) <= 3;
            this.place(
               level,
               box,
               d,
               p,
               this.floorY - 1,
               !side && !endWall ? LabyrinthBlocks.paved(this.x(d, p), this.z(d, p)) : LabyrinthBlocks.wall(this.x(d, p), this.floorY - 1, this.z(d, p))
            );

            for (int y = this.floorY; y < this.floorY + 9; y++) {
               boolean solid = side || endWall && (!doorway || y >= this.floorY + 5);
               this.place(level, box, d, p, y, solid ? LabyrinthBlocks.wall(this.x(d, p), y, this.z(d, p)) : CAVE_AIR);
            }

            this.place(level, box, d, p, this.floorY + 9, LabyrinthBlocks.ceiling(this.x(d, p), this.z(d, p)));
         }
      }

      for (int d = 5; d < outerD; d += 4) {
         for (int side = -1; side <= 1; side += 2) {
            int p = side * 5;

            for (int y = this.floorY; y < this.floorY + 9; y++) {
               this.place(level, box, d, p, y, LabyrinthBlocks.CHISELED);
            }

            this.place(level, box, d, side * 4, this.floorY + 9 - 1, LabyrinthBlocks.HANGING_LANTERN);
         }
      }
   }

   private void buildStairway(WorldGenLevel level, BoundingBox box) {
      int topRef = LabyrinthLayout.stairTop(this.floorY, this.steps);

      for (int k = 0; k <= this.steps; k++) {
         int d = 19 + k;
         int base = LabyrinthLayout.treadBase(topRef, this.steps - k);
         boolean slab = LabyrinthLayout.treadHasSlab(this.steps - k);
         int roof = base + 5 + 1;
         boolean open = this.profile[k] <= roof;

         for (int p = -4; p <= 4; p++) {
            if (Math.abs(p) > 3) {
               for (int y = base - 1; y <= roof; y++) {
                  this.place(level, box, d, p, y, LabyrinthBlocks.wall(this.x(d, p), y, this.z(d, p)));
               }

               if (open) {
                  this.place(level, box, d, p, roof + 1, LabyrinthBlocks.wall(this.x(d, p), roof + 1, this.z(d, p)));
                  this.underpin(level, box, d, p, base - 2);
               }
            } else {
               this.place(level, box, d, p, base, LabyrinthBlocks.wall(this.x(d, p), base, this.z(d, p)));
               if (slab) {
                  this.place(level, box, d, p, base + 1, LabyrinthBlocks.TREAD_SLAB);
               }

               for (int y = base + (slab ? 2 : 1); y <= base + 5; y++) {
                  this.place(level, box, d, p, y, CAVE_AIR);
               }

               this.place(level, box, d, p, roof, open ? CAVE_AIR : LabyrinthBlocks.ceiling(this.x(d, p), this.z(d, p)));
            }
         }

         if (!open && k > 0 && k % 16 == 0) {
            for (int p = -3; p <= 3; p++) {
               this.place(level, box, d, p, roof, LabyrinthBlocks.CHISELED);
            }
         }

         if (!open && k % 8 == 4) {
            int p = k % 16 == 4 ? 2 : -2;
            this.place(level, box, d, p, base + 5, LabyrinthBlocks.HANGING_LANTERN);
         }
      }

      this.buildPortal(level, box, topRef);
   }

   private void buildPortal(WorldGenLevel level, BoundingBox box, int topRef) {
      int d = 19 + this.steps;
      int ground = topRef + 1;

      for (int side = -1; side <= 1; side += 2) {
         int p = side * 4;

         for (int y = ground; y < ground + 6; y++) {
            this.place(level, box, d, p, y, LabyrinthBlocks.CHISELED);
         }

         this.place(level, box, d, p, ground + 6 + 1, LabyrinthBlocks.STANDING_LANTERN);
      }

      for (int p = -4; p <= 4; p++) {
         this.place(level, box, d, p, ground + 6, LabyrinthBlocks.wall(this.x(d, p), ground + 6, this.z(d, p)));
      }

      for (int ahead = 1; ahead <= 3; ahead++) {
         this.pave(level, box, d + ahead, 2 * ground);
      }

      int surface = 2 * ground;

      for (int ahead = 4; ahead <= 11 && 2 * this.profile[this.steps + ahead] > surface; ahead++) {
         this.pave(level, box, d + ahead, ++surface);
      }
   }

   private void pave(WorldGenLevel level, BoundingBox box, int d, int surface) {
      int course = surface / 2;
      boolean slab = surface % 2 == 1;

      for (int p = -4; p <= 4; p++) {
         int px = this.x(d, p);
         int pz = this.z(d, p);
         boolean border = Math.abs(p) > 3;
         this.place(level, box, d, p, course - 1, border ? LabyrinthBlocks.CHISELED : LabyrinthBlocks.paved(px, pz));
         if (slab) {
            this.place(level, box, d, p, course, border ? LabyrinthBlocks.CHISELED : LabyrinthBlocks.TREAD_SLAB);
         }

         this.underpin(level, box, d, p, course - 2);

         for (int y = course + (slab ? 1 : 0); y <= course + 6; y++) {
            this.place(level, box, d, p, y, CAVE_AIR);
         }
      }
   }

   private void underpin(WorldGenLevel level, BoundingBox box, int d, int p, int y) {
      int x = this.x(d, p);
      int z = this.z(d, p);

      for (int depth = 0; depth < 12; depth++) {
         int at = y - depth;
         if (!this.isReplaceableByStructures(this.getBlock(level, x, at, z, box))) {
            return;
         }

         this.place(level, box, d, p, at, LabyrinthBlocks.wall(x, at, z));
      }
   }

   private void place(WorldGenLevel level, BoundingBox box, int d, int p, int y, BlockState state) {
      this.placeBlock(level, state, this.x(d, p), y, this.z(d, p), box);
   }

   private int x(int d, int p) {
      return this.mouthX + this.outward.getStepX() * d + this.outward.getClockWise().getStepX() * p;
   }

   private int z(int d, int p) {
      return this.mouthZ + this.outward.getStepZ() * d + this.outward.getClockWise().getStepZ() * p;
   }

   static int outwardExtent(int steps) {
      return 19 + steps + 3 + 8;
   }
}
