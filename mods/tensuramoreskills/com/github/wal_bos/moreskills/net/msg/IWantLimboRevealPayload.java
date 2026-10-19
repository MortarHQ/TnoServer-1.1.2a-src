package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.screen.IWantLimboScreen;
import dev.architectury.networking.NetworkManager.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record IWantLimboRevealPayload(int cardId, ResourceLocation skill) implements CustomPacketPayload {
   public static final Type<IWantLimboRevealPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "i_want_limbo_reveal"));
   public static final StreamCodec<FriendlyByteBuf, IWantLimboRevealPayload> STREAM_CODEC = StreamCodec.of(
      IWantLimboRevealPayload::write, IWantLimboRevealPayload::read
   );

   private static void write(FriendlyByteBuf buffer, IWantLimboRevealPayload payload) {
      buffer.writeInt(payload.cardId);
      buffer.writeResourceLocation(payload.skill);
   }

   private static IWantLimboRevealPayload read(FriendlyByteBuf buffer) {
      return new IWantLimboRevealPayload(buffer.readInt(), buffer.readResourceLocation());
   }

   public static void handle(IWantLimboRevealPayload payload, PacketContext context) {
      context.queue(() -> IWantLimboScreen.reveal(payload.cardId(), payload.skill()));
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
