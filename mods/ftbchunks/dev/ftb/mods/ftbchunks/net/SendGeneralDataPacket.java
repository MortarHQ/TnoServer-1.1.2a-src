package dev.ftb.mods.ftbchunks.net;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbchunks.api.ChunkTeamData;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import java.util.Collection;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerPlayer;

public record SendGeneralDataPacket(SendGeneralDataPacket.GeneralChunkData data) implements CustomPacketPayload {
   public static final Type<SendGeneralDataPacket> TYPE = new Type(FTBChunksAPI.rl("send_general_data_packet"));
   public static final StreamCodec<FriendlyByteBuf, SendGeneralDataPacket> STREAM_CODEC = StreamCodec.composite(
      SendGeneralDataPacket.GeneralChunkData.STREAM_CODEC, SendGeneralDataPacket::data, SendGeneralDataPacket::new
   );

   public Type<SendGeneralDataPacket> type() {
      return TYPE;
   }

   public static void handle(SendGeneralDataPacket message, PacketContext context) {
      context.queue(() -> FTBChunksClient.INSTANCE.updateGeneralData(message.data));
   }

   public static void send(ChunkTeamData teamData, ServerPlayer player) {
      send(teamData, List.of(player));
   }

   public static void send(ChunkTeamData teamData, Collection<ServerPlayer> players) {
      Collection<? extends ClaimedChunk> cc = teamData.getClaimedChunks();
      int loaded = (int)cc.stream().filter(ClaimedChunk::isForceLoaded).count();
      SendGeneralDataPacket data = new SendGeneralDataPacket(
         new SendGeneralDataPacket.GeneralChunkData(cc.size(), loaded, teamData.getMaxClaimChunks(), teamData.getMaxForceLoadChunks())
      );
      players.forEach(player -> teamData.getTeamManager().getTeamForPlayer(player).ifPresent(team -> {
         if (team.getId().equals(teamData.getTeam().getId())) {
            NetworkManager.sendToPlayer(player, data);
         }
      }));
   }

   public record GeneralChunkData(int claimed, int loaded, int maxClaimChunks, int maxForceLoadChunks) {
      public static StreamCodec<FriendlyByteBuf, SendGeneralDataPacket.GeneralChunkData> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.VAR_INT,
         SendGeneralDataPacket.GeneralChunkData::claimed,
         ByteBufCodecs.VAR_INT,
         SendGeneralDataPacket.GeneralChunkData::loaded,
         ByteBufCodecs.VAR_INT,
         SendGeneralDataPacket.GeneralChunkData::maxClaimChunks,
         ByteBufCodecs.VAR_INT,
         SendGeneralDataPacket.GeneralChunkData::maxForceLoadChunks,
         SendGeneralDataPacket.GeneralChunkData::new
      );
   }
}
