package com.chunksending;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;

public interface IBatchedUpdateSender {
   boolean attachToPending(ChunkPos var1, Packet<?> var2);

   void reset();

   void tick(ServerPlayer var1);
}
