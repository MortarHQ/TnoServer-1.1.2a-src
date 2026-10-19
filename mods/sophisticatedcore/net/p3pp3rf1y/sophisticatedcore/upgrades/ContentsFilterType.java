package net.p3pp3rf1y.sophisticatedcore.upgrades;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.util.StringRepresentable;

public enum ContentsFilterType implements StringRepresentable {
   ALLOW("allow"),
   BLOCK("block"),
   STORAGE("storage");

   private final String name;
   private static final Map<String, ContentsFilterType> NAME_VALUES;
   private static final ContentsFilterType[] VALUES;

   ContentsFilterType(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }

   public ContentsFilterType next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static ContentsFilterType fromName(String name) {
      return NAME_VALUES.getOrDefault(name, BLOCK);
   }

   static {
      Builder<String, ContentsFilterType> builder = new Builder();

      for (ContentsFilterType value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
