package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.handler.IWantToBeJustLikeYouHandler;
import dev.architectury.networking.NetworkManager.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record IWantLimboRerollPayload() implements CustomPacketPayload {
   public static final Type<IWantLimboRerollPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "i_want_limbo_reroll"));
   public static final StreamCodec<FriendlyByteBuf, IWantLimboRerollPayload> STREAM_CODEC = StreamCodec.of(
      IWantLimboRerollPayload::write, IWantLimboRerollPayload::read
   );

   private static void write(FriendlyByteBuf buffer, IWantLimboRerollPayload payload) {
   }

   private static IWantLimboRerollPayload read(FriendlyByteBuf buffer) {
      return new IWantLimboRerollPayload();
   }

   public static void handle(IWantLimboRerollPayload payload, PacketContext context) {
      context.queue(() -> {
         if (context.getPlayer() instanceof ServerPlayer player) {
            IWantToBeJustLikeYouHandler.rerollCards(player);
         }
      });
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
