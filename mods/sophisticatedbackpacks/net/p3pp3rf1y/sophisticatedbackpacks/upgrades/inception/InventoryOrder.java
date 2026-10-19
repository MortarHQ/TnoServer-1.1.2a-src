package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public enum InventoryOrder implements StringRepresentable {
   MAIN_FIRST("main_first"),
   INCEPTED_FIRST("incepted_first");

   public static final Codec<InventoryOrder> CODEC = StringRepresentable.fromEnum(InventoryOrder::values);
   public static final StreamCodec<FriendlyByteBuf, InventoryOrder> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(InventoryOrder.class);
   private final String name;
   private static final Map<String, InventoryOrder> NAME_VALUES;
   private static final InventoryOrder[] VALUES;

   InventoryOrder(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }

   public InventoryOrder next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static InventoryOrder fromName(String name) {
      return NAME_VALUES.getOrDefault(name, MAIN_FIRST);
   }

   static {
      Builder<String, InventoryOrder> builder = new Builder();

      for (InventoryOrder value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
