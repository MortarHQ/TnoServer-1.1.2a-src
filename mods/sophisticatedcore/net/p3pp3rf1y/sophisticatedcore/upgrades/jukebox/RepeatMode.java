package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public enum RepeatMode implements StringRepresentable {
   ALL("all"),
   ONE("one"),
   NO("no");

   public static final Codec<RepeatMode> CODEC = StringRepresentable.fromEnum(RepeatMode::values);
   public static final StreamCodec<FriendlyByteBuf, RepeatMode> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(RepeatMode.class);
   private final String name;
   private static final Map<String, RepeatMode> NAME_VALUES;
   private static final RepeatMode[] VALUES;

   RepeatMode(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }

   public RepeatMode next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static RepeatMode fromName(String name) {
      return NAME_VALUES.getOrDefault(name, NO);
   }

   static {
      Builder<String, RepeatMode> builder = new Builder();

      for (RepeatMode value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
