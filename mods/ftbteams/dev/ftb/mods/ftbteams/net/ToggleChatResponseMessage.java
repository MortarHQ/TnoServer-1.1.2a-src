package dev.ftb.mods.ftbteams.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.client.FTBTeamsClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record ToggleChatResponseMessage(boolean chatRedirected) implements CustomPacketPayload {
   public static final StreamCodec<FriendlyByteBuf, ToggleChatResponseMessage> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL, ToggleChatResponseMessage::chatRedirected, ToggleChatResponseMessage::new
   );
   public static final Type<ToggleChatResponseMessage> TYPE = new Type(FTBTeamsAPI.rl("toggle_chat_response"));

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(ToggleChatResponseMessage message, PacketContext packetContext) {
      packetContext.queue(() -> FTBTeamsClient.setChatRedirected(message.chatRedirected));
   }
}
