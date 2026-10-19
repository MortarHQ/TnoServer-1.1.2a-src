package dev.ftb.mods.ftbquests.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.client.FTBQuestsNetClient;
import dev.ftb.mods.ftbquests.quest.TeamData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record SyncTeamDataMessage(TeamData teamData) implements CustomPacketPayload {
   public static final Type<SyncTeamDataMessage> TYPE = new Type(FTBQuestsAPI.rl("sync_team_data_message"));
   public static final StreamCodec<FriendlyByteBuf, SyncTeamDataMessage> STREAM_CODEC = StreamCodec.composite(
      TeamData.STREAM_CODEC, SyncTeamDataMessage::teamData, SyncTeamDataMessage::new
   );

   public Type<SyncTeamDataMessage> type() {
      return TYPE;
   }

   public static void handle(SyncTeamDataMessage message, PacketContext context) {
      context.queue(() -> FTBQuestsNetClient.syncTeamData(message.teamData));
   }
}
