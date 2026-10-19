package com.cerbon.bosses_of_mass_destruction.capability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;

public class ChunkBlockCache implements IChunkBlockCache {
   private final HashMap<ChunkPos, HashMap<Block, HashSet<BlockPos>>> map = new HashMap<>();

   @Override
   public void addToChunk(ChunkPos chunkPos, Block block, BlockPos pos) {
      HashMap<Block, HashSet<BlockPos>> chunk = this.map.getOrDefault(chunkPos, new HashMap<>());
      HashSet<BlockPos> blocks = chunk.getOrDefault(block, new HashSet<>());
      blocks.add(pos);
      chunk.put(block, blocks);
      this.map.put(chunkPos, chunk);
   }

   @Override
   public List<BlockPos> getBlocksFromChunk(ChunkPos chunkPos, Block block) {
      List<BlockPos> positions = new ArrayList<>();
      if (this.map.containsKey(chunkPos) && this.map.get(chunkPos).containsKey(block)) {
         positions = this.map.get(chunkPos).get(block).stream().toList();
      }

      return positions;
   }

   @Override
   public void removeFromChunk(ChunkPos chunkPos, Block block, BlockPos pos) {
      if (this.map.containsKey(chunkPos) && this.map.get(chunkPos).containsKey(block)) {
         this.map.get(chunkPos).get(block).remove(pos);
      }
   }
}
