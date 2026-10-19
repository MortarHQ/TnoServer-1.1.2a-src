package com.cerbon.bosses_of_mass_destruction.capability;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;

public interface IChunkBlockCache {
   void addToChunk(ChunkPos var1, Block var2, BlockPos var3);

   List<BlockPos> getBlocksFromChunk(ChunkPos var1, Block var2);

   void removeFromChunk(ChunkPos var1, Block var2, BlockPos var3);
}
