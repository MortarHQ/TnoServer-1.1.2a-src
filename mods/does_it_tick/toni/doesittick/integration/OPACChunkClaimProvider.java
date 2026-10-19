package toni.doesittick.integration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import xaero.pac.client.api.OpenPACClientAPI;
import xaero.pac.common.server.api.OpenPACServerAPI;

public final class OPACChunkClaimProvider implements IChunkClaimProvider {
   @Override
   public boolean isInClaimedChunk(Level level, BlockPos pos) {
      return level.isClientSide
         ? OpenPACClientAPI.get().getClaimsManager().get(level.dimension().location(), new ChunkPos(pos)) != null
         : OpenPACServerAPI.get(level.getServer()).getServerClaimsManager().get(level.dimension().location(), new ChunkPos(pos)) != null;
   }
}
