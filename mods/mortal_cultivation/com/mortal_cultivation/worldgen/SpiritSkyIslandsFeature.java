package com.mortal_cultivation.worldgen;

import com.mortal_cultivation.block.SpiritLogBlock;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

public final class SpiritSkyIslandsFeature extends Feature<NoneFeatureConfiguration> {
   private final Map<ServerLevel, Map<Long, List<SpiritSkyIslandLayout.Site>>> worlds = new WeakHashMap<>();

   public SpiritSkyIslandsFeature() {
      super(NoneFeatureConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      if (!level.getLevel().dimension().equals(Level.OVERWORLD)) {
         return false;
      }

      ChunkPos chunk = new ChunkPos(context.origin());
      int minX = chunk.getMinBlockX();
      int minZ = chunk.getMinBlockZ();
      int maxX = minX + 15;
      int maxZ = minZ + 15;
      List<SpiritSkyIslandLayout.Site> sites = new ArrayList<>();

      for (int cellX = Math.floorDiv(minX - 104, 160); cellX <= Math.floorDiv(maxX + 104, 160); cellX++) {
         for (int cellZ = Math.floorDiv(minZ - 104, 160); cellZ <= Math.floorDiv(maxZ + 104, 160); cellZ++) {
            for (SpiritSkyIslandLayout.Site site : this.sites(context, cellX, cellZ)) {
               if (site.island().intersects(minX, minZ, maxX, maxZ)) {
                  sites.add(site);
               }
            }
         }
      }

      if (sites.isEmpty()) {
         return false;
      }

      boolean placed = false;
      MutableBlockPos pos = new MutableBlockPos();

      for (SpiritSkyIslandLayout.Site site : sites) {
         SpiritSkyIslandLayout.Island island = site.island();

         for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
               SpiritSkyIslandLayout.Column column = island.column(x, z);
               if (column != null) {
                  for (int y = column.bottom(); y <= column.top(); y++) {
                     pos.set(x, y, z);
                     BlockState previous = level.getBlockState(pos);
                     if (previous.isAir()
                        || previous.canBeReplaced()
                        || previous.is(BlockTags.LEAVES)
                        || previous.is(Blocks.DARK_OAK_LOG)
                        || previous.is((Block)BlockRegistry.SPIRIT_LOG.get()) && (Boolean)previous.getValue(SpiritLogBlock.NATURAL)) {
                        placed |= level.setBlock(pos, terrain(island, column, x, y, z), 2);
                     }
                  }
               }
            }
         }
      }

      for (SpiritSkyIslandLayout.Site site : sites) {
         if (site.stream() != null) {
            this.placeStream(level, site, minX, minZ, maxX, maxZ);
         }
      }

      for (SpiritSkyIslandLayout.Site site : sites) {
         if (site.stream() != null) {
            this.placeWaterfalls(level, site, minX, minZ, maxX, maxZ);
         }
      }

      return placed;
   }

   private List<SpiritSkyIslandLayout.Site> sites(FeaturePlaceContext<NoneFeatureConfiguration> context, int cellX, int cellZ) {
      ServerLevel level = context.level().getLevel();
      Map<Long, List<SpiritSkyIslandLayout.Site>> cache;
      synchronized (this.worlds) {
         cache = this.worlds.computeIfAbsent(level, ignored -> new LinkedHashMap<Long, List<SpiritSkyIslandLayout.Site>>(256, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Entry<Long, List<SpiritSkyIslandLayout.Site>> entry) {
               return this.size() > 2048;
            }
         });
      }

      long key = (long)cellX << 32 ^ cellZ & 4294967295L;
      synchronized (cache) {
         return cache.computeIfAbsent(key, ignored -> {
            List<SpiritSkyIslandLayout.Site> candidates = SpiritSkyIslandLayout.create(context.level().getSeed(), cellX, cellZ);
            if (!eligible(context, candidates.getFirst().island(), true)) {
               return List.of();
            }

            List<SpiritSkyIslandLayout.Site> accepted = new ArrayList<>();
            accepted.add(candidates.getFirst());

            for (int i = 1; i < candidates.size(); i++) {
               if (eligible(context, candidates.get(i).island(), false)) {
                  accepted.add(candidates.get(i));
               }
            }

            return List.copyOf(accepted);
         });
      }
   }

   private static boolean eligible(FeaturePlaceContext<NoneFeatureConfiguration> context, SpiritSkyIslandLayout.Island island, boolean mainIsland) {
      WorldGenLevel level = context.level();
      if (island.y() + 40 >= level.getMaxBuildHeight()) {
         return false;
      }

      RandomState randomState = level.getLevel().getChunkSource().randomState();
      int lowestRock = island.y() - island.depth() - 13;

      for (int sx = -1; sx <= 1; sx++) {
         for (int sz = -1; sz <= 1; sz++) {
            int x = island.x() + sx * island.radiusX() * 3 / 4;
            int z = island.z() + sz * island.radiusZ() * 3 / 4;
            Holder<Biome> biome = context.chunkGenerator()
               .getBiomeSource()
               .getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(island.y()), QuartPos.fromBlock(z), randomState.sampler());
            if (mainIsland ? !biome.is(WorldgenRegistry.SKY_SPIRIT_FOREST) : !biome.is(WorldgenRegistry.SPIRIT_FORESTS)) {
               return false;
            }

            int ground = context.chunkGenerator().getBaseHeight(x, z, Types.WORLD_SURFACE_WG, level, randomState);
            if (ground + 20 >= lowestRock) {
               return false;
            }
         }
      }

      return true;
   }

   private static BlockState terrain(SpiritSkyIslandLayout.Island island, SpiritSkyIslandLayout.Column column, int x, int y, int z) {
      if (y == column.top()) {
         double moss = Math.sin(x * 0.15 + (island.seed() & 15L)) * Math.cos(z * 0.17);
         return moss > 0.55 ? Blocks.MOSS_BLOCK.defaultBlockState() : Blocks.GRASS_BLOCK.defaultBlockState();
      }

      if (y >= column.top() - 3) {
         return Blocks.DIRT.defaultBlockState();
      }

      long grain = SpiritSkyIslandLayout.mix(island.seed() ^ x * 73428767L ^ y * 912931L ^ z * 913967L);
      return (grain & 7L) == 0L ? Blocks.ANDESITE.defaultBlockState() : Blocks.STONE.defaultBlockState();
   }

   private void placeStream(WorldGenLevel level, SpiritSkyIslandLayout.Site site, int minX, int minZ, int maxX, int maxZ) {
      SpiritSkyIslandLayout.Stream stream = site.stream();
      MutableBlockPos pos = new MutableBlockPos();

      for (int x = minX; x <= maxX; x++) {
         for (int z = minZ; z <= maxZ; z++) {
            SpiritSkyIslandLayout.Column column = site.island().column(x, z);
            if (column != null) {
               if (!stream.contains(x, z)) {
                  boolean bank = stream.contains(x + 1, z) || stream.contains(x - 1, z) || stream.contains(x, z + 1) || stream.contains(x, z - 1);
                  if (bank && column.top() < stream.waterY()) {
                     for (int y = column.top() + 1; y <= stream.waterY(); y++) {
                        level.setBlock(pos.set(x, y, z), y == stream.waterY() ? Blocks.MOSS_BLOCK.defaultBlockState() : Blocks.STONE.defaultBlockState(), 2);
                     }
                  }
               } else {
                  for (int y = stream.waterY() + 1; y <= column.top(); y++) {
                     level.setBlock(pos.set(x, y, z), Blocks.AIR.defaultBlockState(), 2);
                  }

                  level.setBlock(pos.set(x, stream.waterY() - 1, z), Blocks.STONE.defaultBlockState(), 2);
                  BlockPos water = new BlockPos(x, stream.waterY(), z);
                  level.setBlock(water, Blocks.WATER.defaultBlockState(), 2);
                  level.scheduleTick(water, Fluids.WATER, Fluids.WATER.getTickDelay(level));
               }
            }
         }
      }
   }

   private void placeWaterfalls(WorldGenLevel level, SpiritSkyIslandLayout.Site site, int minX, int minZ, int maxX, int maxZ) {
      MutableBlockPos pos = new MutableBlockPos();

      for (SpiritSkyIslandLayout.Outlet outlet : site.stream().outlets()) {
         if (outlet.x() >= minX && outlet.x() <= maxX && outlet.z() >= minZ && outlet.z() <= maxZ) {
            BlockPos head = new BlockPos(outlet.x(), outlet.y(), outlet.z());
            if (level.isEmptyBlock(head)) {
               level.setBlock(head, (BlockState)Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 1), 2);
               BlockPos tail = head;

               for (int y = outlet.y() - 1; y >= level.getMinBuildHeight(); y--) {
                  pos.set(outlet.x(), y, outlet.z());
                  BlockState previous = level.getBlockState(pos);
                  if (!previous.getFluidState().isEmpty() || !previous.isAir() && !previous.canBeReplaced()) {
                     break;
                  }

                  level.setBlock(pos, (BlockState)Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 8), 2);
                  tail = pos.immutable();
               }

               level.scheduleTick(head, Fluids.WATER, Fluids.WATER.getTickDelay(level));
               level.scheduleTick(tail, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
         }
      }
   }
}
