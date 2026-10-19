package net.p3pp3rf1y.sophisticatedcore.common.gui;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public enum SortBy implements StringRepresentable {
   NAME("name"),
   MOD("mod"),
   COUNT("count"),
   TAGS("tags");

   public static final Codec<SortBy> CODEC = StringRepresentable.fromEnum(SortBy::values);
   public static final StreamCodec<FriendlyByteBuf, SortBy> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(SortBy.class);
   private final String name;
   private static final Map<String, SortBy> NAME_VALUES;
   private static final SortBy[] VALUES;

   SortBy(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }

   public SortBy next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static SortBy fromName(String name) {
      return NAME_VALUES.getOrDefault(name, NAME);
   }

   static {
      Builder<String, SortBy> builder = new Builder();

      for (SortBy value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
