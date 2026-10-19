package toni.doesittick.integration;

import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import toni.doesittick.mixin.ClaimedChunkManagerAccessor;

public final class FTBChunkClaimProvider implements IChunkClaimProvider {
   @Override
   public boolean isInClaimedChunk(Level level, BlockPos pos) {
      return !FTBChunksAPI.api().isManagerLoaded()
         ? true
         : ((ClaimedChunkManagerAccessor)FTBChunksAPI.api().getManager()).getClaimedChunks().containsKey(new ChunkDimPos(level, pos));
   }
}
