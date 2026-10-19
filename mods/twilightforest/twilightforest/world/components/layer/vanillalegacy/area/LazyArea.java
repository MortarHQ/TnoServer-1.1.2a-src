package twilightforest.world.components.layer.vanillalegacy.area;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import twilightforest.world.components.layer.vanillalegacy.Area;

public class LazyArea implements Area {
   private final Area transformer;
   private final ConcurrentHashMap<Long, ResourceKey<Biome>> cachedSamples;
   private final LinkedBlockingQueue<Long> evictionQueue;
   private final int maxCache;

   public LazyArea(ConcurrentHashMap<Long, ResourceKey<Biome>> cache, LinkedBlockingQueue<Long> evictionQueue, int maxCache, Area transformer) {
      this.cachedSamples = cache;
      this.evictionQueue = evictionQueue;
      this.maxCache = maxCache;
      this.transformer = transformer;
   }

   @Override
   public ResourceKey<Biome> getBiome(int biomeX, int biomeZ) {
      long key = ChunkPos.asLong(biomeX, biomeZ);
      ResourceKey<Biome> cached = this.cachedSamples.get(key);
      if (cached != null && cached != Biomes.THE_VOID) {
         return cached;
      }

      ResourceKey<Biome> computed = this.transformer.getBiome(biomeX, biomeZ);
      ResourceKey<Biome> existing = this.cachedSamples.putIfAbsent(key, computed);
      if (existing != null && existing != Biomes.THE_VOID) {
         return existing;
      }

      this.evictionQueue.offer(key);
      if (this.evictionQueue.size() > this.maxCache) {
         int i = 0;

         for (int limit = this.maxCache / 16; i < limit; i++) {
            Long oldest = this.evictionQueue.poll();
            if (oldest != null) {
               this.cachedSamples.remove(oldest);
            }
         }
      }

      return computed;
   }

   public int getMaxCache() {
      return this.maxCache;
   }
}
