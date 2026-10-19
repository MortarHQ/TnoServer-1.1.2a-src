package net.p3pp3rf1y.sophisticatedcore.upgrades;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public enum PrimaryMatch implements StringRepresentable {
   ITEM("item"),
   MOD("mod"),
   TAGS("tags");

   private final String name;
   public static final Codec<PrimaryMatch> CODEC = StringRepresentable.fromEnum(PrimaryMatch::values);
   public static final StreamCodec<FriendlyByteBuf, PrimaryMatch> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(PrimaryMatch.class);
   private static final Map<String, PrimaryMatch> NAME_VALUES;
   private static final PrimaryMatch[] VALUES;

   PrimaryMatch(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }

   public PrimaryMatch next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static PrimaryMatch fromName(String name) {
      return NAME_VALUES.getOrDefault(name, ITEM);
   }

   static {
      Builder<String, PrimaryMatch> builder = new Builder();

      for (PrimaryMatch value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
