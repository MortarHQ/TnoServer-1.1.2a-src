package com.tensura_tno.compat;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

public final class TensuraMultipartQuerySafety {
   public static final long MAX_INDEXED_CHUNK_COLUMNS = 4096L;

   private TensuraMultipartQuerySafety() {
   }

   public static boolean canUseChunkBuckets(AABB bounds) {
      long columns = chunkColumnCount(bounds);
      return columns > 0L && columns <= 4096L;
   }

   public static long chunkColumnCount(AABB bounds) {
      if (bounds != null
         && Double.isFinite(bounds.minX)
         && Double.isFinite(bounds.maxX)
         && Double.isFinite(bounds.minZ)
         && Double.isFinite(bounds.maxZ)
         && !(bounds.minX < -2.1474836E9F)
         && !(bounds.maxX > 2.147483647E9)
         && !(bounds.minZ < -2.1474836E9F)
         && !(bounds.maxZ > 2.147483647E9)) {
         int minChunkX = Mth.floor(bounds.minX) >> 4;
         int maxChunkX = Mth.floor(bounds.maxX) >> 4;
         int minChunkZ = Mth.floor(bounds.minZ) >> 4;
         int maxChunkZ = Mth.floor(bounds.maxZ) >> 4;
         long width = (long)maxChunkX - minChunkX + 1L;
         long depth = (long)maxChunkZ - minChunkZ + 1L;
         return width > 0L && depth > 0L ? width * depth : Long.MAX_VALUE;
      } else {
         return Long.MAX_VALUE;
      }
   }
}
