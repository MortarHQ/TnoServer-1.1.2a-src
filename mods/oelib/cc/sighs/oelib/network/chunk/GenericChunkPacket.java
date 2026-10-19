package cc.sighs.oelib.network.chunk;

import cc.sighs.oelib.network.api.INetworkContext;
import cc.sighs.oelib.network.api.INetworkPacket;
import cc.sighs.oelib.network.api.NetworkPacket;
import cc.sighs.oelib.network.api.Side;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

@NetworkPacket(modId = "oelib", id = "generic_chunk", side = Side.BOTH)
public record GenericChunkPacket(UUID sessionId, int totalSize, short chunkIndex, short totalChunks, ResourceLocation originalTypeId, byte[] chunkData)
   implements INetworkPacket<GenericChunkPacket> {
   @Override
   public void handle(INetworkContext context) {
      GenericChunkAssembler.receiveChunk(this.sessionId, this.totalSize, this.chunkIndex, this.totalChunks, this.originalTypeId, this.chunkData, context);
   }
}
