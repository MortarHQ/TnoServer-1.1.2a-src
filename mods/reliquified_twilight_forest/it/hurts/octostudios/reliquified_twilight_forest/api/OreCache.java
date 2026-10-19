package it.hurts.octostudios.reliquified_twilight_forest.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.neoforge.common.Tags.Blocks;

public class OreCache {
   private static final Map<ChunkPos, List<BlockPos>> oreCache = new ConcurrentHashMap<>();
   private static final ExecutorService executor = Executors.newFixedThreadPool(2);

   public static void scanChunkAsync(LevelAccessor level, ChunkAccess chunk) {
      executor.submit(() -> {
         List<BlockPos> ores = scanChunk(level, chunk);
         ChunkPos chunkPos = chunk.getPos();
         Minecraft.getInstance().execute(() -> oreCache.put(chunkPos, ores));
      });
   }

   private static List<BlockPos> scanChunk(LevelAccessor level, ChunkAccess chunk) {
      List<BlockPos> orePositions = new ArrayList<>();
      int minX = chunk.getPos().getMinBlockX();
      int minZ = chunk.getPos().getMinBlockZ();
      int maxX = chunk.getPos().getMaxBlockX();
      int maxZ = chunk.getPos().getMaxBlockZ();

      for (int x = minX; x <= maxX; x++) {
         for (int z = minZ; z <= maxZ; z++) {
            for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
               BlockPos pos = new BlockPos(x, y, z);
               if (level.getBlockState(pos).is(Blocks.ORES)) {
                  orePositions.add(pos);
               }
            }
         }
      }

      return orePositions;
   }

   public static List<BlockPos> getNearbyOres(Level level, BlockPos center, float radius) {
      List<BlockPos> result = new ArrayList<>();
      int chunkRadius = Mth.ceil(radius) / 16 + 1;
      int centerChunkX = center.getX() >> 4;
      int centerChunkZ = center.getZ() >> 4;

      for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
         for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
            ChunkPos cp = new ChunkPos(centerChunkX + dx, centerChunkZ + dz);
            List<BlockPos> chunkOres = oreCache.get(cp);
            if (chunkOres != null) {
               for (BlockPos orePos : chunkOres) {
                  if (center.distSqr(orePos) <= radius * radius) {
                     result.add(orePos);
                  }
               }
            }
         }
      }

      return result;
   }

   public static boolean hasChunk(ChunkPos pos) {
      return oreCache.containsKey(pos);
   }

   public static void removeChunk(ChunkPos pos) {
      oreCache.remove(pos);
   }
}
