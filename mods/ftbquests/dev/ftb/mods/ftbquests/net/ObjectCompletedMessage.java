package dev.ftb.mods.ftbquests.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.client.FTBQuestsNetClient;
import java.util.Date;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record ObjectCompletedMessage(UUID teamId, long id) implements CustomPacketPayload {
   public static final Type<ObjectCompletedMessage> TYPE = new Type(FTBQuestsAPI.rl("object_completed_message"));
   public static final StreamCodec<FriendlyByteBuf, ObjectCompletedMessage> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC, ObjectCompletedMessage::teamId, ByteBufCodecs.VAR_LONG, ObjectCompletedMessage::id, ObjectCompletedMessage::new
   );

   public Type<ObjectCompletedMessage> type() {
      return TYPE;
   }

   public static void handle(ObjectCompletedMessage message, PacketContext context) {
      context.queue(() -> FTBQuestsNetClient.objectCompleted(message.teamId, message.id, new Date()));
   }
}
