package dev.ftbqlang.network;

import io.netty.handler.codec.DecoderException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record LanguageStatePayload(List<String> languages, String selected, boolean prompt) implements CustomPacketPayload {
   public static final Type<LanguageStatePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("ftbqlang", "language_state"));
   public static final StreamCodec<FriendlyByteBuf, LanguageStatePayload> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, LanguageStatePayload>() {
      public LanguageStatePayload decode(FriendlyByteBuf buffer) {
         int count = buffer.readVarInt();
         if (count >= 0 && count <= 512) {
            List<String> languages = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
               languages.add(buffer.readUtf(64));
            }

            return new LanguageStatePayload(languages, buffer.readUtf(64), buffer.readBoolean());
         } else {
            throw new DecoderException("Invalid quest language count: " + count);
         }
      }

      public void encode(FriendlyByteBuf buffer, LanguageStatePayload payload) {
         buffer.writeVarInt(payload.languages().size());

         for (String language : payload.languages()) {
            buffer.writeUtf(language, 64);
         }

         buffer.writeUtf(payload.selected(), 64);
         buffer.writeBoolean(payload.prompt());
      }
   };

   public LanguageStatePayload {
      languages = List.copyOf(languages);
      if (languages.size() > 512) {
         throw new IllegalArgumentException("Too many quest languages");
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
