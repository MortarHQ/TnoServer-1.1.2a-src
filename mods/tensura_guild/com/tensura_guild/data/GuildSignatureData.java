package com.tensura_guild.data;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record GuildSignatureData(List<Integer> points) {
   public static final int SEPARATOR = -1;
   public static final GuildSignatureData EMPTY = new GuildSignatureData(List.of());
   public static final Codec<GuildSignatureData> CODEC = Codec.INT.listOf().xmap(GuildSignatureData::new, GuildSignatureData::points);
   public static final StreamCodec<ByteBuf, GuildSignatureData> STREAM_CODEC = ByteBufCodecs.VAR_INT
      .apply(ByteBufCodecs.list())
      .map(GuildSignatureData::new, GuildSignatureData::points);
   private static final int MAX_VALUES = 1024;
   private static final int MAX_COORDINATE = 255;

   public GuildSignatureData {
      points = sanitize(points);
   }

   public boolean hasSignature() {
      int pairCount = 0;

      for (int i = 0; i + 1 < this.points.size(); i += 2) {
         if (!isSeparator(this.points.get(i), this.points.get(i + 1))) {
            if (++pairCount >= 2) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean isSeparator(int x, int y) {
      return x == -1 && y == -1;
   }

   private static List<Integer> sanitize(List<Integer> input) {
      if (input != null && !input.isEmpty()) {
         int limit = Math.min(input.size(), 1024);
         if ((limit & 1) == 1) {
            limit--;
         }

         List<Integer> sanitized = new ArrayList<>(limit);
         boolean lastWasSeparator = true;

         for (int i = 0; i + 1 < limit; i += 2) {
            int x = input.get(i);
            int y = input.get(i + 1);
            if (isSeparator(x, y)) {
               if (!lastWasSeparator && !sanitized.isEmpty()) {
                  sanitized.add(-1);
                  sanitized.add(-1);
                  lastWasSeparator = true;
               }
            } else if (x >= 0 && y >= 0 && x <= 255 && y <= 255) {
               sanitized.add(x);
               sanitized.add(y);
               lastWasSeparator = false;
            }
         }

         int size = sanitized.size();
         if (size >= 2 && isSeparator(sanitized.get(size - 2), sanitized.get(size - 1))) {
            sanitized.remove(size - 1);
            sanitized.remove(size - 2);
         }

         return List.copyOf(sanitized);
      } else {
         return List.of();
      }
   }
}
