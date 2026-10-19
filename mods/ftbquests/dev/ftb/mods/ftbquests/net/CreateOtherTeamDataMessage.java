package dev.ftb.mods.ftbquests.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.client.FTBQuestsNetClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record CreateOtherTeamDataMessage(TeamDataUpdate dataUpdate) implements CustomPacketPayload {
   public static final Type<CreateOtherTeamDataMessage> TYPE = new Type(FTBQuestsAPI.rl("create_other_team_data_message"));
   public static final StreamCodec<FriendlyByteBuf, CreateOtherTeamDataMessage> STREAM_CODEC = StreamCodec.composite(
      TeamDataUpdate.STREAM_CODEC, CreateOtherTeamDataMessage::dataUpdate, CreateOtherTeamDataMessage::new
   );

   public Type<CreateOtherTeamDataMessage> type() {
      return TYPE;
   }

   public static void handle(CreateOtherTeamDataMessage message, PacketContext context) {
      context.queue(() -> FTBQuestsNetClient.createOtherTeamData(message.dataUpdate));
   }
}
