package com.tensura_guild.data;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record GuildTitleData(List<String> titles) {
   public static final int MAX_TITLE_LENGTH = 64;
   public static final int MAX_TITLE_COUNT = 64;
   public static final GuildTitleData EMPTY = new GuildTitleData(List.of());
   public static final Codec<GuildTitleData> CODEC = Codec.STRING.listOf().xmap(GuildTitleData::new, GuildTitleData::titles);
   public static final StreamCodec<ByteBuf, GuildTitleData> STREAM_CODEC = new StreamCodec<ByteBuf, GuildTitleData>() {
      public GuildTitleData decode(ByteBuf buffer) {
         FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
         int size = Math.max(0, buf.readVarInt());
         List<String> values = new ArrayList<>();

         for (int i = 0; i < size; i++) {
            String value = buf.readUtf(64);
            if (values.size() < 64) {
               values.add(value);
            }
         }

         return new GuildTitleData(values);
      }

      public void encode(ByteBuf buffer, GuildTitleData value) {
         FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
         List<String> values = value == null ? List.of() : value.titles();
         int size = Math.min(values.size(), 64);
         buf.writeVarInt(size);

         for (int i = 0; i < size; i++) {
            buf.writeUtf(values.get(i), 64);
         }
      }
   };

   public GuildTitleData {
      titles = normalizeList(titles);
   }

   public GuildTitleData withTitle(String title) {
      Optional<String> normalized = normalizeTitle(title);
      if (!normalized.isEmpty() && !this.titles.contains(normalized.get())) {
         List<String> values = new ArrayList<>(this.titles);
         values.add(normalized.get());
         return new GuildTitleData(values);
      } else {
         return this;
      }
   }

   public boolean contains(String title) {
      return normalizeTitle(title).filter(this.titles::contains).isPresent();
   }

   public static Optional<String> normalizeTitle(String title) {
      if (title == null) {
         return Optional.empty();
      }

      String value = title.trim();
      if (value.isEmpty()) {
         return Optional.empty();
      }

      if (value.length() > 64) {
         value = value.substring(0, 64);
      }

      return Optional.of(value);
   }

   private static List<String> normalizeList(List<String> values) {
      if (values != null && !values.isEmpty()) {
         Set<String> normalized = new LinkedHashSet<>();

         for (String value : values) {
            normalizeTitle(value).ifPresent(normalized::add);
            if (normalized.size() >= 64) {
               break;
            }
         }

         return List.copyOf(normalized);
      } else {
         return List.of();
      }
   }
}
