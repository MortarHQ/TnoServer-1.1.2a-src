package dev.ftb.mods.ftbteams.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.client.FTBTeamsClient;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record SendMessageResponseMessage(UUID senderId, Component text) implements CustomPacketPayload {
   public static final Type<SendMessageResponseMessage> TYPE = new Type(FTBTeamsAPI.rl("send_message_response"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SendMessageResponseMessage> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC,
      SendMessageResponseMessage::senderId,
      ComponentSerialization.STREAM_CODEC,
      SendMessageResponseMessage::text,
      SendMessageResponseMessage::new
   );

   public static void handle(SendMessageResponseMessage message, PacketContext context) {
      FTBTeamsClient.sendMessage(message.senderId, message.text);
   }

   public Type<SendMessageResponseMessage> type() {
      return TYPE;
   }
}
