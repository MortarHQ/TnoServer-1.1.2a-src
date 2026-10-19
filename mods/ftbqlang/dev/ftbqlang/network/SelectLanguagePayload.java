package dev.ftbqlang.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record SelectLanguagePayload(String locale) implements CustomPacketPayload {
   public static final Type<SelectLanguagePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("ftbqlang", "select_language"));
   public static final StreamCodec<FriendlyByteBuf, SelectLanguagePayload> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, SelectLanguagePayload>() {
      public SelectLanguagePayload decode(FriendlyByteBuf buffer) {
         return new SelectLanguagePayload(buffer.readUtf(64));
      }

      public void encode(FriendlyByteBuf buffer, SelectLanguagePayload payload) {
         buffer.writeUtf(payload.locale(), 64);
      }
   };

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
