package dev.ftb.mods.ftbquests.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record OpenQuestBookMessage(long id) implements CustomPacketPayload {
   public static final Type<OpenQuestBookMessage> TYPE = new Type(FTBQuestsAPI.rl("open_quest_book_message"));
   public static final StreamCodec<FriendlyByteBuf, OpenQuestBookMessage> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_LONG, OpenQuestBookMessage::id, OpenQuestBookMessage::new
   );

   public static OpenQuestBookMessage lastOpenedQuest() {
      return new OpenQuestBookMessage(0L);
   }

   public Type<OpenQuestBookMessage> type() {
      return TYPE;
   }

   public static void handle(OpenQuestBookMessage message, PacketContext context) {
      context.queue(() -> ClientQuestFile.openBookToQuestObject(message.id));
   }
}
