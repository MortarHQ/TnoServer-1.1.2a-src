package com.tensura_tno.world.spawn;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.FluidState;
import org.slf4j.Logger;

public final class SafeSpawnFinder {
   public static final long SEARCH_TIMEOUT_WARN_MS = 200L;
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final String LOG_PREFIX = "[TensuraTNO][SafeSpawnFinder]";

   private SafeSpawnFinder() {
   }

   public static Optional<BlockPos> findSafeSpawn(ServerLevel level, BlockPos origin, int radius, int step, Set<ResourceLocation> blacklist) {
      return findSafeSpawn(toBiomeLookup(level), toSurfaceLookup(level), origin, radius, step, blacklist);
   }

   public static Optional<BlockPos> findSafeSpawnFast(ServerLevel level, BlockPos origin, int radius, int step, Set<ResourceLocation> blacklist) {
      long t0 = System.currentTimeMillis();

      try {
         Pair<BlockPos, Holder<Biome>> hit = level.findClosestBiome3d(
            holder -> holder.unwrapKey().<ResourceLocation>map(ResourceKey::location).map(id -> !blacklist.contains(id)).orElse(false),
            origin,
            Math.max(64, radius),
            Math.max(16, step),
            64
         );
         if (hit != null) {
            BlockPos biomePos = (BlockPos)hit.getFirst();
            int x = biomePos.getX();
            int z = biomePos.getZ();
            int y = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            BlockPos surface = new BlockPos(x, y, z);
            if (isSafePosition(level, surface, blacklist)) {
               warnIfSlow(t0, radius, step);
               return Optional.of(surface);
            }

            Optional<BlockPos> fallback = findSafeSpawn(level, surface, radius, step, blacklist);
            if (fallback.isPresent()) {
               return fallback;
            }
         }
      } catch (RuntimeException e) {
         LOGGER.warn("{} findClosestBiome3d threw, falling back to spiral search", "[TensuraTNO][SafeSpawnFinder]", e);
      }

      return findSafeSpawn(level, origin, radius, step, blacklist);
   }

   static Optional<BlockPos> findSafeSpawn(
      BiomeLookup biomeLookup, SurfaceLookup surfaceLookup, BlockPos origin, int radius, int step, Set<ResourceLocation> blacklist
   ) {
      long t0 = System.currentTimeMillis();
      Iterator<int[]> it = spiralIterator(origin.getX(), origin.getZ(), radius, step);

      while (it.hasNext()) {
         int[] xz = it.next();
         BlockPos surface = surfacePos(surfaceLookup, xz[0], xz[1]);
         if (isSafePosition(biomeLookup, surfaceLookup, surface, blacklist)) {
            warnIfSlow(t0, radius, step);
            return Optional.of(surface);
         }
      }

      warnIfSlow(t0, radius, step);
      return Optional.empty();
   }

   private static void warnIfSlow(long t0, int radius, int step) {
      long elapsed = System.currentTimeMillis() - t0;
      if (elapsed > 200L) {
         LOGGER.warn(
            "{} safe-spawn search took {}ms, consider lowering spawnSafeSearchRadius ({}) or raising spawnSafeSearchStep ({})",
            new Object[]{"[TensuraTNO][SafeSpawnFinder]", elapsed, radius, step}
         );
      }
   }

   static Iterator<int[]> spiralIterator(int cx, int cz, int radius, int step) {
      return new SafeSpawnFinder.SquareSpiralIterator(cx, cz, radius, step);
   }

   static BlockPos surfacePos(ServerLevel level, int x, int z) {
      return surfacePos(toSurfaceLookup(level), x, z);
   }

   static BlockPos surfacePos(SurfaceLookup lookup, int x, int z) {
      return lookup.surfaceAt(x, z);
   }

   static boolean isSafePosition(ServerLevel level, BlockPos surface, Set<ResourceLocation> blacklist) {
      return isSafePosition(toBiomeLookup(level), toSurfaceLookup(level), surface, blacklist);
   }

   static boolean isSafePosition(BiomeLookup biomeLookup, SurfaceLookup surfaceLookup, BlockPos surface, Set<ResourceLocation> blacklist) {
      int y = surface.getY();
      if (y >= surfaceLookup.minBuildHeight() && y < surfaceLookup.maxBuildHeight()) {
         ResourceLocation biome = biomeLookup.biomeAt(surface);
         return biome != null && !blacklist.contains(biome) ? surfaceLookup.isStandable(surface) : false;
      } else {
         return false;
      }
   }

   private static BiomeLookup toBiomeLookup(ServerLevel level) {
      return pos -> {
         Holder<Biome> holder = level.getBiome(pos);
         return holder.unwrapKey().map(key -> key.location()).orElse(null);
      };
   }

   private static SurfaceLookup toSurfaceLookup(final ServerLevel level) {
      return new SurfaceLookup() {
         @Override
         public BlockPos surfaceAt(int x, int z) {
            int y = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            return new BlockPos(x, y, z);
         }

         @Override
         public int minBuildHeight() {
            return level.getMinBuildHeight();
         }

         @Override
         public int maxBuildHeight() {
            return level.getMaxBuildHeight();
         }

         @Override
         public boolean isStandable(BlockPos pos) {
            BlockPos below = pos.below();
            BlockState belowState = level.getBlockState(below);
            if (belowState.isAir()) {
               return false;
            }

            FluidState belowFluid = belowState.getFluidState();
            if (!belowFluid.isEmpty()) {
               return false;
            }

            if (!belowState.isFaceSturdy(level, below, Direction.UP)) {
               return false;
            }

            BlockState feetState = level.getBlockState(pos);
            if (!feetState.isAir()) {
               return false;
            }

            BlockState headState = level.getBlockState(pos.above());
            return headState.isAir();
         }
      };
   }

   private static final class SquareSpiralIterator implements Iterator<int[]> {
      private final int cx;
      private final int cz;
      private final int radius;
      private final int step;
      private final boolean ringsDisabled;
      private boolean emittedCenter;
      private int layer;
      private int edge;
      private int idx;

      SquareSpiralIterator(int cx, int cz, int radius, int step) {
         this.cx = cx;
         this.cz = cz;
         this.radius = radius;
         this.step = step;
         this.ringsDisabled = step <= 0;
         this.emittedCenter = false;
         this.layer = 1;
         this.edge = 0;
         this.idx = 0;
      }

      @Override
      public boolean hasNext() {
         if (!this.emittedCenter) {
            return true;
         } else {
            return this.ringsDisabled ? false : (long)this.layer * this.step <= this.radius;
         }
      }

      public int[] next() {
         if (!this.emittedCenter) {
            this.emittedCenter = true;
            return new int[]{this.cx, this.cz};
         }

         if (!this.ringsDisabled && (long)this.layer * this.step <= this.radius) {
            int r = this.layer;
            int dx;
            int dz;
            switch (this.edge) {
               case 0:
                  dx = r;
                  dz = -r + 1 + this.idx;
                  break;
               case 1:
                  dx = r - 1 - this.idx;
                  dz = r;
                  break;
               case 2:
                  dx = -r;
                  dz = r - 1 - this.idx;
                  break;
               case 3:
                  dx = -r + 1 + this.idx;
                  dz = -r;
                  break;
               default:
                  throw new IllegalStateException("unexpected edge index: " + this.edge);
            }

            int x = this.cx + dx * this.step;
            int z = this.cz + dz * this.step;
            this.idx++;
            if (this.idx >= 2 * r) {
               this.idx = 0;
               this.edge++;
               if (this.edge >= 4) {
                  this.edge = 0;
                  this.layer++;
               }
            }

            return new int[]{x, z};
         } else {
            throw new NoSuchElementException();
         }
      }
   }
}
