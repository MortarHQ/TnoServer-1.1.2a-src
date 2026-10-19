package net.p3pp3rf1y.sophisticatedcore.upgrades.feeding;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public enum HungerLevel implements StringRepresentable {
   ANY("any"),
   HALF("half"),
   FULL("full");

   public static final Codec<HungerLevel> CODEC = StringRepresentable.fromEnum(HungerLevel::values);
   public static final StreamCodec<FriendlyByteBuf, HungerLevel> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(HungerLevel.class);
   private final String name;
   private static final Map<String, HungerLevel> NAME_VALUES;
   private static final HungerLevel[] VALUES;

   HungerLevel(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }

   public HungerLevel next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static HungerLevel fromName(String name) {
      return NAME_VALUES.getOrDefault(name, HALF);
   }

   static {
      Builder<String, HungerLevel> builder = new Builder();

      for (HungerLevel value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
