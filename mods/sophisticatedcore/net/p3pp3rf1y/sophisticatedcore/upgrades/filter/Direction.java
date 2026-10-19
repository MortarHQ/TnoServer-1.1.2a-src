package net.p3pp3rf1y.sophisticatedcore.upgrades.filter;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public enum Direction implements StringRepresentable {
   BOTH("both"),
   INPUT("input"),
   OUTPUT("output");

   public static final Codec<Direction> CODEC = StringRepresentable.fromEnum(Direction::values);
   public static final StreamCodec<FriendlyByteBuf, Direction> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(Direction.class);
   private final String name;
   private static final Map<String, Direction> NAME_VALUES;
   private static final Direction[] VALUES;

   Direction(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }

   public Direction next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static Direction fromName(String name) {
      return NAME_VALUES.getOrDefault(name, BOTH);
   }

   static {
      Builder<String, Direction> builder = new Builder();

      for (Direction value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
