package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.handler.IWantToBeJustLikeYouHandler;
import dev.architectury.networking.NetworkManager.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record IWantLimboSelectPayload(int cardId) implements CustomPacketPayload {
   public static final Type<IWantLimboSelectPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "i_want_limbo_select"));
   public static final StreamCodec<FriendlyByteBuf, IWantLimboSelectPayload> STREAM_CODEC = StreamCodec.of(
      IWantLimboSelectPayload::write, IWantLimboSelectPayload::read
   );

   private static void write(FriendlyByteBuf buffer, IWantLimboSelectPayload payload) {
      buffer.writeInt(payload.cardId);
   }

   private static IWantLimboSelectPayload read(FriendlyByteBuf buffer) {
      return new IWantLimboSelectPayload(buffer.readInt());
   }

   public static void handle(IWantLimboSelectPayload payload, PacketContext context) {
      context.queue(() -> {
         if (context.getPlayer() instanceof ServerPlayer player) {
            IWantToBeJustLikeYouHandler.selectCard(player, payload.cardId());
         }
      });
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
