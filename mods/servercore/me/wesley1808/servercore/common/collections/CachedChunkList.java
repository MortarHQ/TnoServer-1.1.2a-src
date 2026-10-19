package me.wesley1808.servercore.common.collections;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import me.wesley1808.servercore.common.services.platform.PlatformHelper;
import me.wesley1808.servercore.common.utils.ChunkManager;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache.ChunkAndHolder;
import net.minecraft.world.level.chunk.LevelChunk;

public class CachedChunkList extends ObjectArrayList<ChunkAndHolder> {
   private static final AtomicInteger COUNTER = new AtomicInteger();
   private boolean trim;
   private int tickCount = COUNTER.incrementAndGet();

   public CachedChunkList() {
      super(1024);
   }

   public void shouldTrim() {
      this.trim = true;
   }

   public void update(ChunkMap chunkMap, Iterable<ChunkHolder> holders) {
      if (this.tickCount++ % 20 == 0) {
         this.clear();

         for (ChunkHolder holder : holders) {
            LevelChunk chunk = ChunkManager.getChunkFromFuture(holder.getEntityTickingChunkFuture());
            if (chunk != null && PlatformHelper.shouldTickChunk(chunkMap, holder.getPos())) {
               this.add(new ChunkAndHolder(chunk, holder));
            }
         }

         if (this.trim) {
            this.trim(1024);
            this.trim = false;
         }
      }
   }
}
