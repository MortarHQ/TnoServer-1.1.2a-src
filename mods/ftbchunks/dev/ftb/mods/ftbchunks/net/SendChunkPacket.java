package dev.ftb.mods.ftbchunks.net;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbchunks.api.ChunkTeamData;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.data.ChunkSyncInfo;
import java.util.List;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public record SendChunkPacket(ResourceKey<Level> dimension, UUID teamId, ChunkSyncInfo chunk) implements CustomPacketPayload {
   public static final Type<SendChunkPacket> TYPE = new Type(FTBChunksAPI.rl("send_chunk_packet"));
   public static final StreamCodec<FriendlyByteBuf, SendChunkPacket> STREAM_CODEC = StreamCodec.composite(
      ResourceKey.streamCodec(Registries.DIMENSION),
      SendChunkPacket::dimension,
      UUIDUtil.STREAM_CODEC,
      SendChunkPacket::teamId,
      ChunkSyncInfo.STREAM_CODEC,
      SendChunkPacket::chunk,
      SendChunkPacket::new
   );

   public void sendToAll(MinecraftServer server, ChunkTeamData teamData) {
      if (teamData.shouldHideClaims()) {
         SendChunkPacket hiddenPacket = new SendChunkPacket(this.dimension(), Util.NIL_UUID, this.chunk().hidden());

         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            NetworkManager.sendToPlayer(player, teamData.isAlly(player.getUUID()) ? this : hiddenPacket);
         }
      } else {
         NetworkManager.sendToPlayers(server.getPlayerList().getPlayers(), this);
      }
   }

   public Type<SendChunkPacket> type() {
      return TYPE;
   }

   public static void handle(SendChunkPacket message, PacketContext context) {
      context.queue(() -> FTBChunksClient.INSTANCE.updateChunksFromServer(message.dimension, message.teamId, List.of(message.chunk)));
   }
}
