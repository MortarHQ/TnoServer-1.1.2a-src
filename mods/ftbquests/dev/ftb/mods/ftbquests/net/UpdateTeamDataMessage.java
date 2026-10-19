package dev.ftb.mods.ftbquests.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.client.FTBQuestsNetClient;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record UpdateTeamDataMessage(UUID teamId, String name) implements CustomPacketPayload {
   public static final Type<UpdateTeamDataMessage> TYPE = new Type(FTBQuestsAPI.rl("update_team_data_message"));
   public static final StreamCodec<FriendlyByteBuf, UpdateTeamDataMessage> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC, UpdateTeamDataMessage::teamId, ByteBufCodecs.STRING_UTF8, UpdateTeamDataMessage::name, UpdateTeamDataMessage::new
   );

   public Type<UpdateTeamDataMessage> type() {
      return TYPE;
   }

   public static void handle(UpdateTeamDataMessage message, PacketContext context) {
      context.queue(() -> FTBQuestsNetClient.updateTeamData(message.teamId, message.name));
   }
}
