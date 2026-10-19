package net.p3pp3rf1y.sophisticatedcore.upgrades;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Locale;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public enum EntityMatch implements StringRepresentable {
   PLAYERS,
   PLAYERS_AND_ENTITIES,
   ENTITIES;

   public static final Codec<EntityMatch> CODEC = StringRepresentable.fromEnum(EntityMatch::values);
   public static final StreamCodec<FriendlyByteBuf, EntityMatch> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(EntityMatch.class);
   private static final Map<String, EntityMatch> NAME_VALUES;
   private static final EntityMatch[] VALUES;

   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   public EntityMatch next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static EntityMatch fromName(String name) {
      return NAME_VALUES.getOrDefault(name, PLAYERS_AND_ENTITIES);
   }

   static {
      Builder<String, EntityMatch> builder = new Builder();

      for (EntityMatch value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
