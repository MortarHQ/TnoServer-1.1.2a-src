package dev.ftb.mods.ftbchunks.util;

import net.minecraft.world.level.ChunkPos;

public record ChunkPosWithMinY(int chunkX, int chunkZ, int minY) {
   public ChunkPos asChunkPos() {
      return new ChunkPos(this.chunkX, this.chunkZ);
   }

   public long chunkPosAsLong() {
      return this.asChunkPos().toLong();
   }
}
