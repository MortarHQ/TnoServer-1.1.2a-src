package net.pixeldreamstudios.mythika.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.entity.CyclopsEntity;
import net.pixeldreamstudios.mythika.registry.EntityRegistry;
import net.pixeldreamstudios.mythika.registry.StructureRegistry;

public class CyclopsCavePiece extends StructurePiece {
   private static final ResourceKey<LootTable> LOOT = ResourceKey.create(Registries.LOOT_TABLE, Mythika.id("chests/cyclops_cave"));
   private static final int SHAFT_REACH = 64;
   private static final int SHAFT_HALF = 3;
   private static final int HEARTH_HALF = 4;
   private static final int FIRE_REACH = 3;
   private static final int DRIP_HEADROOM = 10;
   private static final int DRIP_LENGTH = 5;
   private static final int DRIP_CHANCE = 18;
   private static final int FOLD_LIGHT_HEIGHT = 4;
   private static final int FOOTING_DEPTH = 12;
   private static final int SHEEP = 5;
   private final int floorY;
   private final int mouthX;
   private final int mouthZ;
   private final int skyY;
   private final Direction inward;
   private final CyclopsCaveLayout layout;
   private boolean spawnedCyclops;

   public CyclopsCavePiece(BoundingBox box, int floorY, int mouthX, int mouthZ, int skyY, Direction inward, CyclopsCaveLayout layout) {
      super(StructureRegistry.CYCLOPS_CAVE_PIECE.get(), 0, box);
      this.setOrientation(null);
      this.floorY = floorY;
      this.mouthX = mouthX;
      this.mouthZ = mouthZ;
      this.skyY = skyY;
      this.inward = inward;
      this.layout = layout;
   }

   public CyclopsCavePiece(CompoundTag tag) {
      super(StructureRegistry.CYCLOPS_CAVE_PIECE.get(), tag);
      this.floorY = tag.getInt("FloorY");
      this.mouthX = tag.getInt("MouthX");
      this.mouthZ = tag.getInt("MouthZ");
      this.skyY = tag.getInt("SkyY");
      this.inward = Direction.from2DDataValue(tag.getInt("Inward"));
      this.layout = CyclopsCaveLayout.of(tag.getInt("Seed"), tag.getInt("Side"), tag.getInt("Reach"));
      this.spawnedCyclops = tag.getBoolean("Cyclops");
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putInt("FloorY", this.floorY);
      tag.putInt("MouthX", this.mouthX);
      tag.putInt("MouthZ", this.mouthZ);
      tag.putInt("SkyY", this.skyY);
      tag.putInt("Inward", this.inward.get2DDataValue());
      tag.putInt("Seed", this.layout.seed());
      tag.putInt("Side", this.layout.side());
      tag.putInt("Reach", this.layout.reach());
      tag.putBoolean("Cyclops", this.spawnedCyclops);
   }

   public void postProcess(
      WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos
   ) {
      this.hollowOut(level, box);
      this.rollDoorStone(level, box);
      this.hangDripstone(level, box);
      this.sinkPool(level, box);
      this.layHearth(level, box);
      this.openSmokeHole(level, box);
      this.penTheFlock(level, box);
      this.stockTheLarder(level, box);
      this.dressShelf(level, box, random);
      this.spawnCyclops(level, box, random);
   }

   private void hollowOut(WorldGenLevel level, BoundingBox box) {
      for (int x = box.minX(); x <= box.maxX(); x++) {
         for (int z = box.minZ(); z <= box.maxZ(); z++) {
            int d = this.d(x, z);
            int p = this.p(x, z);
            if (d >= -9 && d <= this.layout.maxD() && Math.abs(p) <= 24) {
               if (this.layout.hasRind(d, p)) {
                  int from = this.floorY + this.layout.rindLo(d, p);
                  int to = this.floorY + this.layout.rindHi(d, p);

                  for (int y = from; y <= to; y++) {
                     if (this.isLoose(this.getBlock(level, x, y, z, box))) {
                        this.placeBlock(level, CaveBlocks.rock(x, y, z), x, y, z, box);
                     }
                  }
               }

               if (this.layout.carved(d, p)) {
                  int floorTop = this.floorY + this.layout.floorTop(d, p);
                  this.placeBlock(level, CaveBlocks.floor(x, z), x, floorTop, z, box);
                  if (d < 0) {
                     this.foot(level, box, d, p, floorTop - 1);
                  }

                  if (this.layout.treadSlab(d, p)) {
                     this.placeBlock(level, CaveBlocks.TREAD, x, floorTop + 1, z, box);
                  }

                  for (int y = this.floorY + this.layout.air(d, p); y <= this.floorY + this.layout.ceiling(d, p); y++) {
                     this.placeBlock(level, CAVE_AIR, x, y, z, box);
                  }
               }
            }
         }
      }
   }

   private boolean isLoose(BlockState state) {
      return this.isReplaceableByStructures(state) || state.getBlock() instanceof FallingBlock;
   }

   private void rollDoorStone(WorldGenLevel level, BoundingBox box) {
      int centreD = -5;
      int centreP = this.layout.doorStoneP();
      int radius = 3;
      int centreY = this.floorY + radius - 1;

      for (int dd = -radius; dd <= radius; dd++) {
         for (int dp = -radius; dp <= radius; dp++) {
            int lowest = Integer.MAX_VALUE;

            for (int dy = -radius; dy <= radius; dy++) {
               double r = Math.sqrt(dd * dd + dp * dp + dy * dy) + 0.5 * (Variance.at(dd, dy, dp, 24) % 3 - 1);
               if (!(r > radius)) {
                  int y = centreY + dy;
                  lowest = Math.min(lowest, y);
                  this.place(
                     level, box, centreD + dd, centreP + dp, y, CaveBlocks.rock(this.x(centreD + dd, centreP + dp), y, this.z(centreD + dd, centreP + dp))
                  );
               }
            }

            if (lowest != Integer.MAX_VALUE) {
               this.foot(level, box, centreD + dd, centreP + dp, lowest - 1);
            }
         }
      }
   }

   private void hangDripstone(WorldGenLevel level, BoundingBox box) {
      int centreD = this.layout.chamberD();
      int radius = 13;

      for (int dd = -radius; dd <= radius; dd++) {
         for (int dp = -radius; dp <= radius; dp++) {
            int d = centreD + dd;
            if (this.layout.air(d, dp) == 0 && Math.max(Math.abs(dd), Math.abs(dp)) > 4) {
               int x = this.x(d, dp);
               int z = this.z(d, dp);
               int headroom = this.layout.headroom(d, dp);
               int roll = Variance.at(x, 0, z, 28) % 100;
               if (headroom >= 10 && roll < 18) {
                  int ceiling = this.floorY + this.layout.ceiling(d, dp);
                  int length = 2 + roll % 4;
                  this.place(level, box, d, dp, ceiling + 1, Blocks.DRIPSTONE_BLOCK.defaultBlockState());

                  for (int i = 0; i < length; i++) {
                     this.place(level, box, d, dp, ceiling - i, CaveBlocks.dripstone(Direction.DOWN, i, length));
                  }
               }
            }
         }
      }
   }

   private void sinkPool(WorldGenLevel level, BoundingBox box) {
      int centreD = this.layout.chamberD();
      int centreP = -this.layout.side() * 9;

      for (int dd = -1; dd <= 1; dd++) {
         for (int dp = -1; dp <= 1; dp++) {
            this.trough(level, box, centreD + dd, centreP + dp);
         }
      }
   }

   private void layHearth(WorldGenLevel level, BoundingBox box) {
      int centreD = this.layout.chamberD();

      for (int dd = -4; dd <= 4; dd++) {
         for (int dp = -4; dp <= 4; dp++) {
            int d = centreD + dd;
            if (this.layout.carved(d, dp) && this.layout.air(d, dp) == 0) {
               int x = this.x(d, dp);
               int z = this.z(d, dp);
               this.place(level, box, d, dp, this.floorY - 1, CaveBlocks.scorched(x, z));
               if (Math.max(Math.abs(dd), Math.abs(dp)) == 4) {
                  if (Variance.at(x, 0, z, 25) % 100 < 70) {
                     this.place(level, box, d, dp, this.floorY, Blocks.COBBLESTONE.defaultBlockState());
                  }
               } else if (Math.abs(dd) + Math.abs(dp) <= 3) {
                  this.place(level, box, d, dp, this.floorY, CaveBlocks.HEARTH_FIRE);
               } else {
                  this.place(level, box, d, dp, this.floorY, CaveBlocks.scorched(x, z));
               }
            }
         }
      }
   }

   private void openSmokeHole(WorldGenLevel level, BoundingBox box) {
      int centreD = this.layout.chamberD();
      int from = this.floorY + this.layout.ceiling(centreD, 0) + 1;
      if (this.skyY > from && this.skyY - from <= 64) {
         for (int dd = -3; dd <= 3; dd++) {
            for (int dp = -3; dp <= 3; dp++) {
               if (dd * dd + dp * dp <= 9 + Variance.at(dd, 0, dp, 26) % 3) {
                  int x = this.x(centreD + dd, dp);
                  int z = this.z(centreD + dd, dp);
                  if (this.inChunk(box, x, z)) {
                     for (int y = from; y <= this.skyY + 2; y++) {
                        if (!this.getBlock(level, x, y, z, box).isAir()) {
                           this.place(level, box, centreD + dd, dp, y, CAVE_AIR);
                        }
                     }
                  }
               }
            }
         }

         for (int dd = -4; dd <= 4; dd++) {
            for (int dp = -4; dp <= 4; dp++) {
               if (Math.max(Math.abs(dd), Math.abs(dp)) == 4) {
                  int x = this.x(centreD + dd, dp);
                  int z = this.z(centreD + dd, dp);
                  if (this.inChunk(box, x, z)) {
                     for (int y = from; y <= this.skyY + 2; y++) {
                        if (this.isReplaceableByStructures(this.getBlock(level, x, y, z, box))) {
                           this.place(level, box, centreD + dd, dp, y - 1, CaveBlocks.scorched(x, z));
                           break;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean inChunk(BoundingBox box, int x, int z) {
      return x >= box.minX() && x <= box.maxX() && z >= box.minZ() && z <= box.maxZ();
   }

   private void penTheFlock(WorldGenLevel level, BoundingBox box) {
      BlockState wall = CaveBlocks.dryStone(this.inward.getAxis());
      int gate = this.layout.gateP();

      for (int d = -9; d <= this.layout.maxD(); d++) {
         if (this.layout.isGate(d, gate)) {
            int base = this.floorY + this.layout.air(d, gate);
            this.place(level, box, d, gate, base, Blocks.COBBLESTONE.defaultBlockState());
            this.place(level, box, d, gate, base + 1, wall);
         }
      }

      int foldD = this.layout.chamberD();
      int foldP = this.layout.foldP();

      for (int dd = -4; dd <= 4; dd += 8) {
         this.stand(level, box, foldD + dd, foldP, Blocks.HAY_BLOCK.defaultBlockState());
      }

      for (int dp = -1; dp <= 1; dp++) {
         this.trough(level, box, foldD, foldP + dp + 3);
      }

      this.hangLantern(level, box, foldD, foldP);
      this.hangLantern(level, box, foldD - 4, foldP + 2);
      this.hangLantern(level, box, foldD + 4, foldP - 2);
      int[][] pens = new int[][]{{2, 2}, {-2, 2}, {2, -2}, {-2, -2}, {0, -1}};

      for (int i = 0; i < 5; i++) {
         int d = foldD + pens[i][0];
         int p = foldP + pens[i][1];
         if (this.layout.headroom(d, p) >= 2) {
            BlockPos at = new BlockPos(this.x(d, p), this.floorY, this.z(d, p));
            if (box.isInside(at)) {
               Sheep sheep = (Sheep)EntityType.SHEEP.create(level.getLevel());
               if (sheep != null) {
                  sheep.moveTo(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, 0.0F, 0.0F);
                  sheep.finalizeSpawn(level, level.getCurrentDifficultyAt(at), MobSpawnType.STRUCTURE, null);
                  sheep.setPersistenceRequired();
                  level.addFreshEntity(sheep);
               }
            }
         }
      }
   }

   private void hangLantern(WorldGenLevel level, BoundingBox box, int d, int p) {
      int ceiling = this.layout.ceiling(d, p);
      if (this.layout.headroom(d, p) >= 6 && this.layout.air(d, p) == 0) {
         for (int y = 5; y <= ceiling; y++) {
            this.place(level, box, d, p, this.floorY + y, CaveBlocks.CHAIN);
         }

         this.place(level, box, d, p, this.floorY + 4, CaveBlocks.HANGING_LANTERN);
      }
   }

   private void trough(WorldGenLevel level, BoundingBox box, int d, int p) {
      if (this.layout.headroom(d, p) >= 2 && this.layout.air(d, p) == 0) {
         this.place(level, box, d, p, this.floorY - 2, Blocks.STONE.defaultBlockState());
         this.place(level, box, d, p, this.floorY - 1, Blocks.WATER.defaultBlockState());
      }
   }

   private void stockTheLarder(WorldGenLevel level, BoundingBox box) {
      int d = this.layout.chamberD();
      int p = this.layout.larderP();
      this.stand(level, box, d - 2, p, (BlockState)Blocks.STRIPPED_OAK_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, this.inward.getAxis()));
      this.stand(level, box, d - 1, p, (BlockState)Blocks.OAK_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, this.inward.getAxis()));
      this.stand(level, box, d + 2, p, Blocks.CAULDRON.defaultBlockState());
      this.stand(level, box, d + 1, p - 2, Blocks.DECORATED_POT.defaultBlockState());
      this.stand(level, box, d + 2, p + 2, Blocks.DECORATED_POT.defaultBlockState());
      this.stand(level, box, d - 1, p + 3, Blocks.DECORATED_POT.defaultBlockState());
      this.stand(level, box, d + 3, p - 1, Blocks.HAY_BLOCK.defaultBlockState());
      this.stand(level, box, d - 3, p + 1, CaveBlocks.HEARTH_FIRE);
   }

   private void dressShelf(WorldGenLevel level, BoundingBox box, RandomSource random) {
      int shelfD = this.layout.shelfD();
      int shelfAir = CyclopsCaveLayout.shelfAir();
      int top = this.floorY + shelfAir;

      for (int d = shelfD; d <= this.layout.maxD(); d++) {
         for (int p = -7; p <= 7; p++) {
            if (this.layout.headroom(d, p) >= 2 && this.layout.air(d, p) == shelfAir) {
               int x = this.x(d, p);
               int z = this.z(d, p);
               int roll = Variance.at(x, 0, z, 27) % 100;
               if (roll < 18) {
                  BlockState bone = (BlockState)Blocks.BONE_BLOCK
                     .defaultBlockState()
                     .setValue(RotatedPillarBlock.AXIS, roll < 7 ? Axis.Y : this.inward.getAxis());
                  this.place(level, box, d, p, top, bone);
                  if (roll < 5) {
                     this.place(
                        level,
                        box,
                        d,
                        p,
                        top + 1,
                        (BlockState)Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, RotationSegment.convertToSegment(this.inward))
                     );
                  }
               } else if (roll < 30) {
                  this.place(level, box, d, p, top, Blocks.HAY_BLOCK.defaultBlockState());
               }
            }
         }
      }

      int chestD = shelfD + 3;
      if (this.layout.headroom(chestD, 0) >= 2) {
         this.createChest(level, box, random, this.x(chestD, 0), top, this.z(chestD, 0), LOOT);
      }

      for (int side = -1; side <= 1; side += 2) {
         int p = side * 3;
         if (this.layout.headroom(chestD, p) >= 3) {
            this.place(level, box, chestD, p, top, CaveBlocks.HEARTH_FIRE);
         }
      }
   }

   private void stand(WorldGenLevel level, BoundingBox box, int d, int p, BlockState state) {
      if (this.layout.headroom(d, p) >= 3 && this.layout.air(d, p) == 0) {
         this.place(level, box, d, p, this.floorY, state);
      }
   }

   private void spawnCyclops(WorldGenLevel level, BoundingBox box, RandomSource random) {
      if (!this.spawnedCyclops) {
         int centreD = this.layout.chamberD();
         int stand = -1;
         int standP = 0;

         for (int dd = -4; dd <= 4 && stand < 0; dd++) {
            for (int dp = 6; dp <= 8; dp++) {
               int p = -this.layout.side() * dp;
               if (this.layout.headroom(centreD + dd, p) >= 5 && this.layout.air(centreD + dd, p) == 0) {
                  stand = centreD + dd;
                  standP = p;
                  break;
               }
            }
         }

         if (stand >= 0) {
            BlockPos at = new BlockPos(this.x(stand, standP), this.floorY, this.z(stand, standP));
            if (box.isInside(at)) {
               this.spawnedCyclops = true;
               CyclopsEntity cyclops = (CyclopsEntity)EntityRegistry.CYCLOPS.get().create(level.getLevel());
               if (cyclops != null) {
                  cyclops.bindToLair(new BlockPos(this.x(centreD, 0), this.floorY, this.z(centreD, 0)), centreD + 32);
                  cyclops.moveTo(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, random.nextFloat() * 360.0F, 0.0F);
                  cyclops.finalizeSpawn(level, level.getCurrentDifficultyAt(at), MobSpawnType.STRUCTURE, null);
                  cyclops.setPersistenceRequired();
                  level.addFreshEntity(cyclops);
               }
            }
         }
      }
   }

   private void foot(WorldGenLevel level, BoundingBox box, int d, int p, int y) {
      int x = this.x(d, p);
      int z = this.z(d, p);

      for (int depth = 0; depth < 12; depth++) {
         int at = y - depth;
         if (!this.isLoose(this.getBlock(level, x, at, z, box))) {
            return;
         }

         this.place(level, box, d, p, at, CaveBlocks.rock(x, at, z));
      }
   }

   private void place(WorldGenLevel level, BoundingBox box, int d, int p, int y, BlockState state) {
      this.placeBlock(level, state, this.x(d, p), y, this.z(d, p), box);
   }

   private int x(int d, int p) {
      return this.mouthX + this.inward.getStepX() * d + this.inward.getClockWise().getStepX() * p;
   }

   private int z(int d, int p) {
      return this.mouthZ + this.inward.getStepZ() * d + this.inward.getClockWise().getStepZ() * p;
   }

   private int d(int x, int z) {
      return (x - this.mouthX) * this.inward.getStepX() + (z - this.mouthZ) * this.inward.getStepZ();
   }

   private int p(int x, int z) {
      return (x - this.mouthX) * this.inward.getClockWise().getStepX() + (z - this.mouthZ) * this.inward.getClockWise().getStepZ();
   }
}
