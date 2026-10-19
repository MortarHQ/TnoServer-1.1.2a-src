package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.screen.NirvanaArchiveScreen;
import dev.architectury.networking.NetworkManager.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record NirvanaToastPayload(boolean success, String title, String body) implements CustomPacketPayload {
   public static final Type<NirvanaToastPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "nirvana_toast"));
   public static final StreamCodec<FriendlyByteBuf, NirvanaToastPayload> STREAM_CODEC = StreamCodec.of(NirvanaToastPayload::write, NirvanaToastPayload::read);

   private static void write(FriendlyByteBuf buffer, NirvanaToastPayload payload) {
      buffer.writeBoolean(payload.success);
      buffer.writeUtf(payload.title == null ? "" : payload.title);
      buffer.writeUtf(payload.body == null ? "" : payload.body);
   }

   private static NirvanaToastPayload read(FriendlyByteBuf buffer) {
      return new NirvanaToastPayload(buffer.readBoolean(), buffer.readUtf(32767), buffer.readUtf(32767));
   }

   public static void handle(NirvanaToastPayload payload, PacketContext context) {
      context.queue(() -> NirvanaArchiveScreen.showMigrationPopup(payload.success(), payload.title(), payload.body()));
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
