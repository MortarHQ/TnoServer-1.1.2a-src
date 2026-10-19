package com.teamresourceful.resourcefullib.common.network.base;

import com.teamresourceful.resourcefullib.common.network.Packet;
import net.minecraft.server.level.ServerPlayer;

public interface Networking {
   <T extends Packet<T>> void register(ClientboundPacketType<T> var1);

   <T extends Packet<T>> void register(ServerboundPacketType<T> var1);

   <T extends Packet<T>> void sendToServer(T var1);

   <T extends Packet<T>> void sendToPlayer(T var1, ServerPlayer var2);

   boolean canSendToPlayer(ServerPlayer var1, PacketType<?> var2);
}
