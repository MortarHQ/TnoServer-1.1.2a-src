package cc.sighs.oelib.network.spi;

import cc.sighs.oelib.network.api.INetworkPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;

public interface INetworkManager {
   <T extends INetworkPacket<T> & CustomPacketPayload> void sendToPlayer(T var1, ServerPlayer var2);

   <T extends INetworkPacket<T> & CustomPacketPayload> void sendToAll(T var1);

   <T extends INetworkPacket<T> & CustomPacketPayload> void sendToServer(T var1);

   <T extends INetworkPacket<T> & CustomPacketPayload> void sendToWorld(T var1, ServerLevel var2);

   <T extends INetworkPacket<T> & CustomPacketPayload> void sendToNear(T var1, ServerLevel var2, Vec3 var3, double var4);

   <T extends INetworkPacket<T> & CustomPacketPayload> void sendToNearExcept(T var1, ServerLevel var2, Vec3 var3, double var4, ServerPlayer var6);

   <T extends INetworkPacket<T> & CustomPacketPayload> void sendToTrackingEntity(T var1, Entity var2);

   <T extends INetworkPacket<T> & CustomPacketPayload> void sendToTrackingEntityAndSelf(T var1, Entity var2);

   <T extends INetworkPacket<T> & CustomPacketPayload> void sendToTrackingChunk(T var1, ServerLevel var2, ChunkPos var3);
}
