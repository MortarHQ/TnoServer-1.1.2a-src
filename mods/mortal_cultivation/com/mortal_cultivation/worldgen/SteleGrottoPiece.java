package com.mortal_cultivation.worldgen;

import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public final class SteleGrottoPiece extends StructurePiece {
   public static final int HORIZONTAL_RADIUS = 21;
   public static final int VERTICAL_RADIUS = 10;
   private static final String LAYOUT_SEED_TAG = "LayoutSeed";
   private static final double CAVE_RADIUS = 17.5;
   private static final Direction[] HORIZONTAL_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   private static final long RADIUS_PHASE_SALT = 7046029254386353131L;
   private static final long SECOND_RADIUS_PHASE_SALT = 3257665815644502181L;
   private static final long FLOOR_PHASE_SALT = -8378864009470890807L;
   private static final long CEILING_PHASE_SALT = 5700357409661599745L;
   private static final long BLOCK_SALT = -3335678366873096957L;
   private static final long ISLAND_SALT = -6068174398545744893L;
   private static final long VINE_SALT = -8306560040656073257L;
   private static final long FLORA_SALT = -2643881736870682267L;
   private static final long WATER_PLANT_SALT = -4908828816729415329L;
   private final long layoutSeed;

   public SteleGrottoPiece(BlockPos center, long layoutSeed) {
      super(
         (StructurePieceType)WorldgenRegistry.STELE_GROTTO_PIECE.get(),
         0,
         new BoundingBox(center.getX() - 21, center.getY() - 10, center.getZ() - 21, center.getX() + 21, center.getY() + 10, center.getZ() + 21)
      );
      this.layoutSeed = layoutSeed;
   }

   public SteleGrottoPiece(CompoundTag tag) {
      super((StructurePieceType)WorldgenRegistry.STELE_GROTTO_PIECE.get(), tag);
      this.layoutSeed = tag.getLong("LayoutSeed");
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putLong("LayoutSeed", this.layoutSeed);
   }

   public void postProcess(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator generator,
      RandomSource random,
      BoundingBox chunkBox,
      ChunkPos chunkPos,
      BlockPos pivot
   ) {
      BlockPos center = this.boundingBox.getCenter();
      this.generateCavern(level, chunkBox, center);
      this.decorateCeiling(level, chunkBox, center);
      this.decorateWater(level, chunkBox, center);
      this.decorateIsland(level, chunkBox, center);
      this.placePedestalAndStele(level, chunkBox, center);
   }

   private void generateCavern(WorldGenLevel level, BoundingBox chunkBox, BlockPos center) {
      int minX = Math.max(chunkBox.minX(), this.boundingBox.minX());
      int maxX = Math.min(chunkBox.maxX(), this.boundingBox.maxX());
      int minZ = Math.max(chunkBox.minZ(), this.boundingBox.minZ());
      int maxZ = Math.min(chunkBox.maxZ(), this.boundingBox.maxZ());
      int waterY = this.waterLevel(center);
      MutableBlockPos mutable = new MutableBlockPos();

      for (int x = minX; x <= maxX; x++) {
         for (int z = minZ; z <= maxZ; z++) {
            SteleGrottoPiece.ColumnShape column = this.columnAt(center, x, z);
            if (column != null) {
               int islandTop = this.islandTopY(center, x, z);
               int minY = Math.max(chunkBox.minY(), column.floorY() - 1);
               int maxY = Math.min(chunkBox.maxY(), column.ceilingY() + 1);

               for (int y = minY; y <= maxY; y++) {
                  BlockState state;
                  if (y <= column.floorY() || y >= column.ceilingY()) {
                     state = this.shellState(center, x, y, z, y >= column.ceilingY());
                  } else if (islandTop != Integer.MIN_VALUE && y <= islandTop) {
                     state = y == islandTop ? this.islandSurfaceState(x, y, z) : this.islandFillState(x, y, z);
                  } else {
                     state = y <= waterY ? Blocks.WATER.defaultBlockState() : Blocks.CAVE_AIR.defaultBlockState();
                  }

                  place(level, chunkBox, mutable.set(x, y, z), state);
               }
            }
         }
      }
   }

   private void decorateCeiling(WorldGenLevel level, BoundingBox chunkBox, BlockPos center) {
      int minX = Math.max(chunkBox.minX(), this.boundingBox.minX());
      int maxX = Math.min(chunkBox.maxX(), this.boundingBox.maxX());
      int minZ = Math.max(chunkBox.minZ(), this.boundingBox.minZ());
      int maxZ = Math.min(chunkBox.maxZ(), this.boundingBox.maxZ());
      int gridOffsetX = (int)(this.layoutSeed & 15L);
      int gridOffsetZ = (int)(this.layoutSeed >>> 8 & 15L);
      MutableBlockPos mutable = new MutableBlockPos();

      for (int x = minX; x <= maxX; x++) {
         for (int z = minZ; z <= maxZ; z++) {
            SteleGrottoPiece.ColumnShape column = this.columnAt(center, x, z);
            if (column != null && !(column.normalizedRadius() < 0.22) && !(column.normalizedRadius() > 0.9)) {
               boolean vineGrid = Math.floorMod(x + gridOffsetX, 5) == 0 && Math.floorMod(z + gridOffsetZ, 5) == 0;
               if (vineGrid && this.random01(-8306560040656073257L, x, column.ceilingY(), z) < 0.7) {
                  int requestedLength = 2 + (int)(this.random01(-8306560040656053128L, x, 0, z) * 4.0);
                  int lowestAllowedY = Math.max(column.floorY() + 2, this.waterLevel(center) + 2);
                  int length = Math.min(requestedLength, column.ceilingY() - lowestAllowedY);
                  if (length >= 2) {
                     place(level, chunkBox, mutable.set(x, column.ceilingY(), z), Blocks.MOSS_BLOCK.defaultBlockState());

                     for (int segment = 1; segment <= length; segment++) {
                        boolean tip = segment == length;
                        boolean berries = tip || this.random01(-8306560040656097536L, x, segment, z) < 0.45;
                        BlockState vine = (BlockState)(tip ? Blocks.CAVE_VINES.defaultBlockState() : Blocks.CAVE_VINES_PLANT.defaultBlockState())
                           .setValue(CaveVines.BERRIES, berries);
                        place(level, chunkBox, mutable.set(x, column.ceilingY() - segment, z), vine);
                     }
                     continue;
                  }
               }

               double ceilingDecoration = this.random01(-8306560040656044338L, x, column.ceilingY(), z);
               if (Math.floorMod(x + gridOffsetX, 8) == 0 && Math.floorMod(z + gridOffsetZ, 8) == 0 && ceilingDecoration < 0.35) {
                  place(level, chunkBox, mutable.set(x, column.ceilingY() - 1, z), Blocks.SPORE_BLOSSOM.defaultBlockState());
               } else if (ceilingDecoration < 0.08) {
                  place(level, chunkBox, mutable.set(x, column.ceilingY() - 1, z), Blocks.HANGING_ROOTS.defaultBlockState());
               }
            }
         }
      }
   }

   private void decorateWater(WorldGenLevel level, BoundingBox chunkBox, BlockPos center) {
      int minX = Math.max(chunkBox.minX(), this.boundingBox.minX());
      int maxX = Math.min(chunkBox.maxX(), this.boundingBox.maxX());
      int minZ = Math.max(chunkBox.minZ(), this.boundingBox.minZ());
      int maxZ = Math.min(chunkBox.maxZ(), this.boundingBox.maxZ());
      int waterY = this.waterLevel(center);
      MutableBlockPos mutable = new MutableBlockPos();

      for (int x = minX; x <= maxX; x++) {
         for (int z = minZ; z <= maxZ; z++) {
            SteleGrottoPiece.ColumnShape column = this.columnAt(center, x, z);
            if (column != null && column.floorY() < waterY && this.islandTopY(center, x, z) == Integer.MIN_VALUE) {
               double value = this.random01(-4908828816729415329L, x, column.floorY(), z);
               if (value < 0.055 && column.floorY() + 1 <= waterY) {
                  place(level, chunkBox, mutable.set(x, column.floorY() + 1, z), Blocks.SEAGRASS.defaultBlockState());
               }

               if (value > 0.975 && waterY + 1 < column.ceilingY()) {
                  place(level, chunkBox, mutable.set(x, waterY + 1, z), Blocks.LILY_PAD.defaultBlockState());
               }
            }
         }
      }
   }

   private void decorateIsland(WorldGenLevel level, BoundingBox chunkBox, BlockPos center) {
      int minX = Math.max(chunkBox.minX(), center.getX() - 7);
      int maxX = Math.min(chunkBox.maxX(), center.getX() + 7);
      int minZ = Math.max(chunkBox.minZ(), center.getZ() - 7);
      int maxZ = Math.min(chunkBox.maxZ(), center.getZ() + 7);
      MutableBlockPos mutable = new MutableBlockPos();

      for (int x = minX; x <= maxX; x++) {
         for (int z = minZ; z <= maxZ; z++) {
            int islandTop = this.islandTopY(center, x, z);
            SteleGrottoPiece.ColumnShape column = this.columnAt(center, x, z);
            if (islandTop != Integer.MIN_VALUE
               && column != null
               && islandTop + 1 < column.ceilingY()
               && Math.max(Math.abs(x - center.getX()), Math.abs(z - center.getZ())) > 2) {
               double value = this.random01(-2643881736870682267L, x, islandTop, z);
               if (!(value >= 0.22)) {
                  double choice = this.random01(-2643881736870676298L, x, islandTop, z);
                  BlockState flora;
                  if (choice < 0.32) {
                     flora = Blocks.MOSS_CARPET.defaultBlockState();
                  } else if (choice < 0.58) {
                     flora = Blocks.SHORT_GRASS.defaultBlockState();
                  } else if (choice < 0.76) {
                     flora = Blocks.FERN.defaultBlockState();
                  } else if (choice < 0.9) {
                     flora = Blocks.BROWN_MUSHROOM.defaultBlockState();
                  } else {
                     flora = Blocks.FLOWERING_AZALEA.defaultBlockState();
                  }

                  place(level, chunkBox, mutable.set(x, islandTop + 1, z), flora);
               }
            }
         }
      }
   }

   private void placePedestalAndStele(WorldGenLevel level, BoundingBox chunkBox, BlockPos center) {
      int platformY = this.waterLevel(center) + 2;
      MutableBlockPos mutable = new MutableBlockPos();

      for (int dx = -2; dx <= 2; dx++) {
         for (int dz = -2; dz <= 2; dz++) {
            int x = center.getX() + dx;
            int z = center.getZ() + dz;
            place(level, chunkBox, mutable.set(x, platformY - 1, z), Blocks.MOSSY_COBBLESTONE.defaultBlockState());
            BlockState platform;
            if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1) {
               platform = dx == 0 && dz == 0 ? Blocks.CHISELED_STONE_BRICKS.defaultBlockState() : Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
            } else {
               platform = Blocks.MOSS_BLOCK.defaultBlockState();
            }

            place(level, chunkBox, mutable.set(x, platformY, z), platform);
            place(level, chunkBox, mutable.set(x, platformY + 1, z), Blocks.CAVE_AIR.defaultBlockState());
         }
      }

      BlockState stele = (BlockState)((Block)BlockRegistry.ANCIENT_STELE.get())
         .defaultBlockState()
         .setValue(HorizontalDirectionalBlock.FACING, this.steleFacing());
      place(level, chunkBox, mutable.set(center.getX(), platformY + 1, center.getZ()), stele);
   }

   private SteleGrottoPiece.ColumnShape columnAt(BlockPos center, int x, int z) {
      double dx = x - center.getX();
      double dz = z - center.getZ();
      double distance = Math.sqrt(dx * dx + dz * dz);
      double angle = Math.atan2(dz, dx);
      double effectiveRadius = 17.5
         + 1.25 * Math.sin(angle * 3.0 + this.phase(7046029254386353131L))
         + 0.75 * Math.sin(angle * 5.0 + this.phase(3257665815644502181L));
      double normalizedRadius = distance / effectiveRadius;
      if (normalizedRadius > 1.0) {
         return null;
      }

      double dome = Math.sqrt(Math.max(0.0, 1.0 - normalizedRadius * normalizedRadius));
      double floorNoise = 0.65 * Math.sin(x * 0.31 + this.phase(-8378864009470890807L)) + 0.45 * Math.cos(z * 0.27 + this.phase(-8378864009470871842L));
      double ceilingNoise = 0.55 * Math.sin(x * 0.23 + this.phase(5700357409661599745L)) + 0.4 * Math.cos(z * 0.29 + this.phase(5700357409661614944L));
      int floorY = center.getY() - 3 - Mth.floor(dome * 4.7) + Mth.floor(floorNoise);
      int ceilingY = center.getY() + 1 + Mth.floor(dome * 8.1) + Mth.floor(ceilingNoise);
      floorY = Mth.clamp(floorY, center.getY() - 8, center.getY() - 2);
      ceilingY = Mth.clamp(ceilingY, center.getY() + 1, center.getY() + 9);
      ceilingY = Math.max(ceilingY, floorY + 4);
      return new SteleGrottoPiece.ColumnShape(floorY, ceilingY, normalizedRadius);
   }

   private int islandTopY(BlockPos center, int x, int z) {
      double dx = x - center.getX();
      double dz = z - center.getZ();
      double distance = Math.sqrt(dx * dx + dz * dz);
      double angle = Math.atan2(dz, dx);
      double radius = 5.35 + 0.45 * Math.sin(angle * 4.0 + this.phase(-6068174398545744893L));
      return distance > radius ? Integer.MIN_VALUE : this.waterLevel(center) + (distance < radius * 0.58 ? 2 : 1);
   }

   private BlockState shellState(BlockPos center, int x, int y, int z, boolean ceiling) {
      double value = this.random01(-3335678366873096957L ^ (ceiling ? 25387L : 6629L), x, y, z);
      if (y <= this.waterLevel(center) + 1) {
         if (value < 0.2) {
            return Blocks.MUD.defaultBlockState();
         } else if (value < 0.38) {
            return Blocks.PACKED_MUD.defaultBlockState();
         } else if (value < 0.54) {
            return Blocks.ROOTED_DIRT.defaultBlockState();
         } else if (value < 0.68) {
            return Blocks.CLAY.defaultBlockState();
         } else {
            return value < 0.84 ? Blocks.MOSS_BLOCK.defaultBlockState() : Blocks.TUFF.defaultBlockState();
         }
      } else if (value < 0.26) {
         return Blocks.TUFF.defaultBlockState();
      } else if (value < 0.49) {
         return Blocks.DEEPSLATE.defaultBlockState();
      } else if (value < 0.66) {
         return Blocks.STONE.defaultBlockState();
      } else if (value < 0.78) {
         return Blocks.ANDESITE.defaultBlockState();
      } else {
         return value < 0.91 ? Blocks.MOSS_BLOCK.defaultBlockState() : Blocks.ROOTED_DIRT.defaultBlockState();
      }
   }

   private BlockState islandSurfaceState(int x, int y, int z) {
      double value = this.random01(-6068174398545731292L, x, y, z);
      if (value < 0.58) {
         return Blocks.MOSS_BLOCK.defaultBlockState();
      } else if (value < 0.78) {
         return Blocks.ROOTED_DIRT.defaultBlockState();
      } else {
         return value < 0.9 ? Blocks.DIRT.defaultBlockState() : Blocks.MUD.defaultBlockState();
      }
   }

   private BlockState islandFillState(int x, int y, int z) {
      double value = this.random01(-6068174398545731854L, x, y, z);
      if (value < 0.38) {
         return Blocks.DIRT.defaultBlockState();
      } else {
         return value < 0.7 ? Blocks.MUD.defaultBlockState() : Blocks.PACKED_MUD.defaultBlockState();
      }
   }

   private Direction steleFacing() {
      int index = (int)(mix64(this.layoutSeed ^ 1851936439L) & 3L);
      return HORIZONTAL_DIRECTIONS[index];
   }

   private int waterLevel(BlockPos center) {
      return center.getY() - 3;
   }

   private double phase(long salt) {
      return this.random01(salt, 0, 0, 0) * Math.PI * 2.0;
   }

   private double random01(long salt, int x, int y, int z) {
      long value = this.layoutSeed ^ salt;
      value ^= x * 341873128712L;
      value ^= y * 132897987541L;
      value ^= z * 42317861L;
      return (mix64(value) >>> 11) * 1.110223E-16F;
   }

   private static long mix64(long value) {
      value = (value ^ value >>> 30) * -4658895280553007687L;
      value = (value ^ value >>> 27) * -7723592293110705685L;
      return value ^ value >>> 31;
   }

   private static void place(WorldGenLevel level, BoundingBox chunkBox, BlockPos pos, BlockState state) {
      if (chunkBox.isInside(pos)) {
         level.setBlock(pos, state, 2);
      }
   }

   private record ColumnShape(int floorY, int ceilingY, double normalizedRadius) {
   }
}
