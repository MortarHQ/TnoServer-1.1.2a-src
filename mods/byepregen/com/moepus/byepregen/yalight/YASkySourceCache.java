package com.moepus.byepregen.yalight;

import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.LightChunk;
import net.minecraft.world.level.chunk.LightChunkGetter;

final class YASkySourceCache extends YAChunkRunCache {
   YAChunkSkyLightSources enabledSources(YALightStorage storage, int chunkX, int chunkZ) {
      LightChunk chunk = this.enabledChunk(storage, chunkX, chunkZ);
      return chunk == null ? null : (YAChunkSkyLightSources)chunk.getSkyLightSources();
   }

   YAChunkSkyLightSources sources(LightChunkGetter chunkGetter, int chunkX, int chunkZ) {
      LightChunk chunk = this.chunk(chunkGetter, chunkX, chunkZ);
      return chunk == null ? null : (YAChunkSkyLightSources)chunk.getSkyLightSources();
   }

   int enabledSourceCode(YALightStorage storage, int x, int z) {
      YAChunkSkyLightSources sources = this.enabledSources(storage, SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
      return sources == null ? Integer.MAX_VALUE : sources.sourceCode(x, z);
   }

   int sourceCode(LightChunkGetter chunkGetter, int x, int z) {
      YAChunkSkyLightSources sources = this.sources(chunkGetter, SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
      return sources == null ? 65535 : sources.sourceCode(x, z);
   }
}
