package cn.star.structblend.terrain;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.Heightmap.Types;

final class SurfaceHeightCache {
   private static final int CAPACITY = 65536;
   private static final Map<RandomState, Map<NoiseBasedChunkGenerator, SurfaceHeightCache>> CACHES = new WeakHashMap<>();
   private final AtomicReferenceArray<SurfaceHeightCache.Entry> surfaceEntries = new AtomicReferenceArray<>(65536);
   private volatile AtomicReferenceArray<SurfaceHeightCache.Entry> oceanFloorEntries;
   private final HeightQueryDiagnostics.Tracker surfaceDiagnostics = HeightQueryDiagnostics.tracker(false);
   private final HeightQueryDiagnostics.Tracker floorDiagnostics = HeightQueryDiagnostics.tracker(true);
   final ChunkHeightBatch.Guard batchGuard = new ChunkHeightBatch.Guard();

   private SurfaceHeightCache() {
   }

   long cachedPair(int x, int z, ChunkAccess chunk) {
      long position = (long)x << 32 | z & 4294967295L;
      int slot = slot(x, z);
      int minY = chunk.getMinBuildHeight();
      int maxY = chunk.getMaxBuildHeight();
      SurfaceHeightCache.Entry surface = this.surfaceEntries.get(slot);
      AtomicReferenceArray<SurfaceHeightCache.Entry> floors = this.oceanFloorEntries;
      if (matches(surface, position, minY, maxY) && floors != null) {
         SurfaceHeightCache.Entry floor = floors.get(slot);
         return matches(floor, position, minY, maxY) ? pack(surface.height, floor.height) : Long.MIN_VALUE;
      } else {
         return Long.MIN_VALUE;
      }
   }

   static SurfaceHeightCache forContext(NoiseBasedChunkGenerator generator, RandomState randomState) {
      synchronized (CACHES) {
         Map<NoiseBasedChunkGenerator, SurfaceHeightCache> generators = CACHES.computeIfAbsent(randomState, ignored -> new WeakHashMap<>());
         return generators.computeIfAbsent(generator, ignored -> new SurfaceHeightCache());
      }
   }

   int get(NoiseBasedChunkGenerator generator, RandomState randomState, ChunkAccess chunk, int x, int z) {
      return get(generator, randomState, chunk, x, z, Types.WORLD_SURFACE_WG, this.surfaceEntries, this.surfaceDiagnostics);
   }

   long getPair(NoiseBasedChunkGenerator generator, RandomState randomState, ChunkAccess chunk, int x, int z) {
      if (PairedHeightQuery.ENABLED) {
         long position = (long)x << 32 | z & 4294967295L;
         int slot = slot(x, z);
         int minY = chunk.getMinBuildHeight();
         int maxY = chunk.getMaxBuildHeight();
         AtomicReferenceArray<SurfaceHeightCache.Entry> floors = this.oceanFloorEntries;
         if (!matches(this.surfaceEntries.get(slot), position, minY, maxY) && (floors == null || !matches(floors.get(slot), position, minY, maxY))) {
            try (PairedHeightQuery.Request ignored = PairedHeightQuery.open(generator, chunk, randomState, x, z)) {
               return pack(this.get(generator, randomState, chunk, x, z), this.getOceanFloor(generator, randomState, chunk, x, z));
            }
         }
      }

      return pack(this.get(generator, randomState, chunk, x, z), this.getOceanFloor(generator, randomState, chunk, x, z));
   }

   static int surface(long pair) {
      return (int)(pair >> 32);
   }

   static int floor(long pair) {
      return (int)pair;
   }

   static long pack(int surface, int floor) {
      return (long)surface << 32 | floor & 4294967295L;
   }

   private static int slot(int x, int z) {
      int hash = Integer.rotateLeft(x * -1640531527, 16) ^ z * -2048144789;
      return (hash ^ hash >>> 16) & 65535;
   }

   private static boolean matches(SurfaceHeightCache.Entry entry, long position, int minY, int maxY) {
      return entry != null && entry.position == position && entry.minY == minY && entry.maxY == maxY;
   }

   int getOceanFloor(NoiseBasedChunkGenerator generator, RandomState randomState, ChunkAccess chunk, int x, int z) {
      AtomicReferenceArray<SurfaceHeightCache.Entry> floorEntries = this.oceanFloorEntries;
      if (floorEntries == null) {
         synchronized (this) {
            floorEntries = this.oceanFloorEntries;
            if (floorEntries == null) {
               floorEntries = new AtomicReferenceArray<>(65536);
               this.oceanFloorEntries = floorEntries;
            }
         }
      }

      return get(generator, randomState, chunk, x, z, Types.OCEAN_FLOOR_WG, floorEntries, this.floorDiagnostics);
   }

   private static int get(
      NoiseBasedChunkGenerator generator,
      RandomState randomState,
      ChunkAccess chunk,
      int x,
      int z,
      Types heightmap,
      AtomicReferenceArray<SurfaceHeightCache.Entry> entries,
      HeightQueryDiagnostics.Tracker diagnostics
   ) {
      long position = (long)x << 32 | z & 4294967295L;
      int slot = slot(x, z);
      int minY = chunk.getMinBuildHeight();
      int maxY = chunk.getMaxBuildHeight();
      SurfaceHeightCache.Entry entry = entries.get(slot);
      if (matches(entry, position, minY, maxY)) {
         if (HeightQueryDiagnostics.ENABLED) {
            diagnostics.hit();
         }

         return entry.height;
      } else if (HeightQueryDiagnostics.ENABLED) {
         HeightQueryDiagnostics.Key key = diagnostics.begin(position, minY, maxY, entry != null);
         long started = System.nanoTime();
         boolean success = false;

         try {
            int height = generator.getBaseHeight(x, z, heightmap, chunk, randomState) - 1;
            entries.lazySet(slot, new SurfaceHeightCache.Entry(position, minY, maxY, height));
            success = true;
            return height;
         } finally {
            diagnostics.finish(key, System.nanoTime() - started, success);
         }
      } else {
         int height = generator.getBaseHeight(x, z, heightmap, chunk, randomState) - 1;
         entries.lazySet(slot, new SurfaceHeightCache.Entry(position, minY, maxY, height));
         return height;
      }
   }

   private record Entry(long position, int minY, int maxY, int height) {
   }
}
