package noobanidus.mods.lootr.common.chunk;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;

public class LoadedChunks {
   private static final Map<ResourceKey<Level>, Set<ChunkPos>> LOADED_CHUNKS = new ConcurrentHashMap<>();

   public static void onChunkLoad(LevelAccessor level, LevelChunk chunk) {
      if (!level.isClientSide() && chunk.getFullStatus().isOrAfter(FullChunkStatus.FULL)) {
         ResourceKey<Level> dimension = chunk.getLevel().dimension();
         Set<ChunkPos> chunkSet = LOADED_CHUNKS.computeIfAbsent(dimension, k -> ConcurrentHashMap.newKeySet());
         chunkSet.add(chunk.getPos());
      }
   }

   public static void onChunkUnload(LevelAccessor level, LevelChunk chunk) {
      if (!level.isClientSide()) {
         ResourceKey<Level> dimension = chunk.getLevel().dimension();
         Set<ChunkPos> chunkSet = LOADED_CHUNKS.get(dimension);
         if (chunkSet != null) {
            chunkSet.remove(chunk.getPos());
         }
      }
   }

   public static Set<ChunkPos> getLoadedChunks(ResourceKey<Level> dimension) {
      Set<ChunkPos> set = LOADED_CHUNKS.computeIfAbsent(dimension, k -> ConcurrentHashMap.newKeySet());
      return Collections.unmodifiableSet(set);
   }

   public static void clear() {
      LOADED_CHUNKS.clear();
   }
}
