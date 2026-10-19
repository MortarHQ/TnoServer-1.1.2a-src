package dev.ftb.mods.ftbquests.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.quest.Chapter;
import dev.ftb.mods.ftbquests.quest.ChapterImage;
import dev.ftb.mods.ftbquests.util.NetUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record CopyChapterImageMessage(ChapterImage img) implements CustomPacketPayload {
   public static final Type<CopyChapterImageMessage> TYPE = new Type(FTBQuestsAPI.rl("copy_chapter_image_message"));
   public static final StreamCodec<FriendlyByteBuf, CopyChapterImageMessage> STREAM_CODEC = StreamCodec.composite(
      ChapterImage.STREAM_CODEC, CopyChapterImageMessage::img, CopyChapterImageMessage::new
   );

   public CopyChapterImageMessage(ChapterImage toCopy, Chapter chapter, double newX, double newY) {
      this(toCopy.copy(chapter, newX, newY));
   }

   public Type<CopyChapterImageMessage> type() {
      return TYPE;
   }

   public static void handle(CopyChapterImageMessage message, PacketContext context) {
      context.queue(() -> {
         if (NetUtils.canEdit(context)) {
            Chapter chapter = message.img.getChapter();
            chapter.addImage(message.img);
            chapter.file.markDirty();
            NetworkHelper.sendToAll(context.getPlayer().getServer(), new EditObjectResponseMessage(chapter));
         }
      });
   }
}
