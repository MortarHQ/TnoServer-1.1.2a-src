package com.mortal_cultivation.worldgen;

import com.mortal_cultivation.entity.AzureMossQilinEntity;
import com.mortal_cultivation.registry.CultivationEntities;
import com.mortal_cultivation.registry.WorldgenRegistry;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public final class QilinBambooGroveFeature extends Feature<NoneFeatureConfiguration> {
   private static final int CELL_CHUNKS = 6;
   private static final int RADIUS = 14;
   private static final int ARENA_RADIUS = 8;
   private static final int TREE_CLEARANCE = 22;
   private static final int MAX_VEGETATION_HEIGHT = 48;

   public QilinBambooGroveFeature() {
      super(NoneFeatureConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      ChunkPos chunk = new ChunkPos(context.origin());
      QilinBambooGroveFeature.Site site = site(level.getSeed(), Math.floorDiv(chunk.x, 6), Math.floorDiv(chunk.z, 6));
      if (site.x() >> 4 == chunk.x && site.z() >> 4 == chunk.z && !overlapsLandmark(level.getSeed(), site)) {
         int groundY = findGround(level, site.x(), site.z());
         if (groundY == Integer.MIN_VALUE) {
            return false;
         }

         BlockPos center = new BlockPos(site.x(), groundY + 1, site.z());
         if (level.getBiome(center).is(WorldgenRegistry.SPIRIT_FORESTS) && !intersectsStructure(level, center)) {
            List<QilinBambooGroveFeature.Column> columns = new ArrayList<>();

            for (int dx = -14; dx <= 14; dx++) {
               for (int dz = -14; dz <= 14; dz++) {
                  if (dx * dx + dz * dz <= 196) {
                     int x = site.x() + dx;
                     int z = site.z() + dz;
                     int oldGround = findGround(level, x, z);
                     if (oldGround == Integer.MIN_VALUE || Math.abs(oldGround - groundY) > 4) {
                        return false;
                     }

                     int distance = Mth.ceil(Math.sqrt(dx * dx + dz * dz));
                     int newGround = groundY + Mth.clamp(oldGround - groundY, -Math.max(0, distance - 8), Math.max(0, distance - 8));
                     int top = Math.max(level.getHeight(Types.WORLD_SURFACE_WG, x, z), newGround + 14);
                     if (!validColumn(level, x, z, oldGround, newGround, top)) {
                        return false;
                     }

                     columns.add(new QilinBambooGroveFeature.Column(x, z, oldGround, newGround, top, distance));
                  }
               }
            }

            AzureMossQilinEntity qilin = (AzureMossQilinEntity)((EntityType)CultivationEntities.AZURE_MOSS_QILIN.get()).create(level.getLevel());
            if (qilin == null) {
               return false;
            }

            UUID id = new UUID(SpiritSkyIslandLayout.mix(site.seed()), SpiritSkyIslandLayout.mix(~site.seed()));
            if (!(level instanceof WorldGenRegion) && level.getLevel().getEntity(id) != null) {
               return false;
            }

            qilin.setUUID(id);
            qilin.moveTo(center.getX() + 0.5, center.getY(), center.getZ() + 0.5, (float)Math.floorMod(site.seed(), 360L), 0.0F);
            qilin.setPersistenceRequired();
            qilin.restrictTo(center, 22);
            qilin.finalizeSpawn(level, level.getCurrentDifficultyAt(center), MobSpawnType.STRUCTURE, null);
            if (qilin.isSpawnCancelled()) {
               return false;
            }

            RandomSource random = RandomSource.create(site.seed());

            for (QilinBambooGroveFeature.Column column : columns) {
               prepareGround(level, column, random);
            }

            for (QilinBambooGroveFeature.Column column : columns) {
               int dx = column.x() - site.x();
               int dz = column.z() - site.z();
               boolean approach = Math.abs((site.seed() & 1L) == 0L ? dx : dz) <= 1;
               if (column.distance() >= 10 && !approach && random.nextFloat() < 0.58F) {
                  bamboo(level, new BlockPos(column.x(), column.newGround() + 1, column.z()), 7 + random.nextInt(7));
               }
            }

            return level.addFreshEntity(qilin);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean inClearing(long seed, BlockPos pos) {
      int cellX = Math.floorDiv(pos.getX() >> 4, 6);
      int cellZ = Math.floorDiv(pos.getZ() >> 4, 6);

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            QilinBambooGroveFeature.Site site = site(seed, cellX + dx, cellZ + dz);
            long sx = (long)site.x() - pos.getX();
            long sz = (long)site.z() - pos.getZ();
            if (sx * sx + sz * sz <= 484L && !overlapsLandmark(seed, site)) {
               return true;
            }
         }
      }

      return false;
   }

   private static QilinBambooGroveFeature.Site site(long worldSeed, int cellX, int cellZ) {
      long seed = SpiritSkyIslandLayout.mix(worldSeed ^ 47916389148785L ^ cellX * 341873128712L ^ cellZ * 132897987541L);
      RandomSource random = RandomSource.create(seed);
      int chunkX = cellX * 6 + 1 + random.nextInt(4);
      int chunkZ = cellZ * 6 + 1 + random.nextInt(4);
      return new QilinBambooGroveFeature.Site(chunkX * 16 + 8, chunkZ * 16 + 8, seed);
   }

   private static boolean overlapsLandmark(long seed, QilinBambooGroveFeature.Site site) {
      int cellX = Math.floorDiv(site.x(), 64);
      int cellZ = Math.floorDiv(site.z(), 64);

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            SpiritForestLandscapeLayout.Site other = SpiritForestLandscapeLayout.site(seed, cellX + dx, cellZ + dz);
            long x = (long)other.x() - site.x();
            long z = (long)other.z() - site.z();
            int clearance = 14 + (other.kind() == SpiritForestLandscapeLayout.Kind.RUIN ? 11 : 16);
            if (x * x + z * z < clearance * clearance) {
               return true;
            }
         }
      }

      return false;
   }

   private static int findGround(WorldGenLevel level, int x, int z) {
      int top = level.getHeight(Types.WORLD_SURFACE_WG, x, z) - 1;
      MutableBlockPos cursor = new MutableBlockPos(x, top, z);
      int bottom = Math.max(level.getMinBuildHeight() + 4, top - 48);

      for (int y = top; y >= bottom; y--) {
         BlockState state = level.getBlockState(cursor.setY(y));
         if (soil(state)) {
            return y;
         }

         if (!vegetation(state)) {
            return Integer.MIN_VALUE;
         }
      }

      return Integer.MIN_VALUE;
   }

   private static boolean validColumn(WorldGenLevel level, int x, int z, int oldGround, int newGround, int top) {
      MutableBlockPos pos = new MutableBlockPos(x, newGround, z);
      if (top < level.getMaxBuildHeight()
         && Math.min(oldGround, newGround) - 2 > level.getMinBuildHeight()
         && level.getBiome(pos).is(WorldgenRegistry.SPIRIT_FORESTS)
         && level.ensureCanWrite(pos)
         && level.ensureCanWrite(pos.setY(top))) {
         for (int y = Math.min(oldGround, newGround) - 2; y <= top; y++) {
            BlockState state = level.getBlockState(pos.setY(y));
            if (state.hasBlockEntity() || !state.getFluidState().isEmpty() || !soil(state) && !state.is(BlockTags.BASE_STONE_OVERWORLD) && !vegetation(state)) {
               return false;
            }

            if (y <= oldGround && !soil(state) && !state.is(BlockTags.BASE_STONE_OVERWORLD)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean intersectsStructure(WorldGenLevel level, BlockPos center) {
      if (!(level instanceof WorldGenRegion region)) {
         return false;
      } else {
         BoundingBox grove = new BoundingBox(
            center.getX() - 14, center.getY() - 6, center.getZ() - 14, center.getX() + 14, center.getY() + 48, center.getZ() + 14
         );
         ChunkPos chunk = new ChunkPos(center);

         for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
               Map<Structure, LongSet> references = region.getChunk(chunk.x + dx, chunk.z + dz, ChunkStatus.STRUCTURE_REFERENCES).getAllReferences();

               for (Entry<Structure, LongSet> entry : references.entrySet()) {
                  LongIterator var10 = entry.getValue().iterator();

                  while (var10.hasNext()) {
                     long packed = (Long)var10.next();
                     ChunkPos startChunk = new ChunkPos(packed);
                     if (!region.hasChunk(startChunk.x, startChunk.z)) {
                        return true;
                     }

                     StructureStart start = region.getChunk(startChunk.x, startChunk.z, ChunkStatus.STRUCTURE_STARTS).getStartForStructure(entry.getKey());
                     if (start != null && start.isValid() && start.getBoundingBox().intersects(grove)) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   private static boolean soil(BlockState state) {
      return !state.hasBlockEntity() && state.getFluidState().isEmpty() && (state.is(BlockTags.DIRT) || state.is(Blocks.MOSS_BLOCK));
   }

   private static boolean vegetation(BlockState state) {
      return !state.hasBlockEntity()
         && state.getFluidState().isEmpty()
         && (
            state.isAir()
               || state.is(BlockTags.REPLACEABLE_BY_TREES)
               || state.is(BlockTags.LOGS)
               || state.is(BlockTags.LEAVES)
               || state.getBlock() instanceof BushBlock
               || state.is(Blocks.BAMBOO)
               || state.is(Blocks.BAMBOO_SAPLING)
         );
   }

   private static void prepareGround(WorldGenLevel level, QilinBambooGroveFeature.Column column, RandomSource random) {
      MutableBlockPos pos = new MutableBlockPos(column.x(), column.newGround(), column.z());

      for (int y = Math.min(column.oldGround(), column.newGround()); y < column.newGround(); y++) {
         level.setBlock(pos.setY(y), Blocks.DIRT.defaultBlockState(), 2);
      }

      BlockState surface = random.nextInt(5) == 0
         ? Blocks.MOSS_BLOCK.defaultBlockState()
         : (column.distance() > 8 && random.nextInt(4) == 0 ? Blocks.PODZOL.defaultBlockState() : Blocks.GRASS_BLOCK.defaultBlockState());
      level.setBlock(pos.setY(column.newGround()), surface, 2);

      for (int y = column.newGround() + 1; y <= column.top(); y++) {
         pos.setY(y);
         if (!level.isEmptyBlock(pos)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
         }
      }
   }

   private static void bamboo(WorldGenLevel level, BlockPos pos, int height) {
      for (int y = 0; y < height; y++) {
         BambooLeaves leaves = y == height - 1 ? BambooLeaves.LARGE : (y >= height - 3 ? BambooLeaves.SMALL : BambooLeaves.NONE);
         level.setBlock(
            pos.above(y),
            (BlockState)((BlockState)((BlockState)Blocks.BAMBOO.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1))
                  .setValue(BlockStateProperties.BAMBOO_LEAVES, leaves))
               .setValue(BlockStateProperties.STAGE, y == height - 1 ? 1 : 0),
            2
         );
      }
   }

   private record Column(int x, int z, int oldGround, int newGround, int top, int distance) {
   }

   private record Site(int x, int z, long seed) {
   }
}
