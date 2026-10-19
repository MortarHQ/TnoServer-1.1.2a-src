package net.darkhax.bookshelf.common.api.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkHandler {
   <T extends CustomPacketPayload> void register(IPacket<T> var1);

   <T extends CustomPacketPayload> void sendToServer(T var1);

   <T extends CustomPacketPayload> void sendToPlayer(ServerPlayer var1, T var2);

   default boolean canSendPacket(ServerPlayer recipient, CustomPacketPayload payload) {
      return this.canSendPacket(recipient, payload.type().id());
   }

   default boolean canSendPacket(ServerPlayer recipient, IPacket<?> packet) {
      return this.canSendPacket(recipient, packet.type().id());
   }

   boolean canSendPacket(ServerPlayer var1, ResourceLocation var2);
}
