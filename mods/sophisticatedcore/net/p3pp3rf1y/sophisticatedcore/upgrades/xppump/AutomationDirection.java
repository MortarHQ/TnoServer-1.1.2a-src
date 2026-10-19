package net.p3pp3rf1y.sophisticatedcore.upgrades.xppump;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public enum AutomationDirection implements StringRepresentable {
   INPUT("input"),
   OUTPUT("output"),
   KEEP("keep"),
   OFF("off");

   public static final Codec<AutomationDirection> CODEC = StringRepresentable.fromEnum(AutomationDirection::values);
   public static final StreamCodec<FriendlyByteBuf, AutomationDirection> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(AutomationDirection.class);
   private final String name;
   private static final Map<String, AutomationDirection> NAME_VALUES;
   private static final AutomationDirection[] VALUES;

   AutomationDirection(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }

   public AutomationDirection next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static AutomationDirection fromName(String name) {
      return NAME_VALUES.getOrDefault(name, INPUT);
   }

   static {
      Builder<String, AutomationDirection> builder = new Builder();

      for (AutomationDirection value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
