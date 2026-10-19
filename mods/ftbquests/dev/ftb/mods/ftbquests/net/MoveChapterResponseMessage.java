package dev.ftb.mods.ftbquests.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.client.FTBQuestsNetClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record MoveChapterResponseMessage(long id, boolean movingUp) implements CustomPacketPayload {
   public static final Type<MoveChapterResponseMessage> TYPE = new Type(FTBQuestsAPI.rl("move_chapter_response_message"));
   public static final StreamCodec<FriendlyByteBuf, MoveChapterResponseMessage> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_LONG, MoveChapterResponseMessage::id, ByteBufCodecs.BOOL, MoveChapterResponseMessage::movingUp, MoveChapterResponseMessage::new
   );

   public Type<MoveChapterResponseMessage> type() {
      return TYPE;
   }

   public static void handle(MoveChapterResponseMessage message, PacketContext context) {
      context.queue(() -> FTBQuestsNetClient.moveChapter(message.id, message.movingUp));
   }
}
