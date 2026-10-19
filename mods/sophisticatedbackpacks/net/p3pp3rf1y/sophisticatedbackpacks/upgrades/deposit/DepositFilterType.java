package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.deposit;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.util.StringRepresentable;

public enum DepositFilterType implements StringRepresentable {
   ALLOW("allow"),
   BLOCK("block"),
   INVENTORY("inventory");

   private final String name;
   private static final Map<String, DepositFilterType> NAME_VALUES;
   private static final DepositFilterType[] VALUES;

   DepositFilterType(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }

   public DepositFilterType next() {
      return VALUES[(this.ordinal() + 1) % VALUES.length];
   }

   public static DepositFilterType fromName(String name) {
      return NAME_VALUES.getOrDefault(name, BLOCK);
   }

   static {
      Builder<String, DepositFilterType> builder = new Builder();

      for (DepositFilterType value : values()) {
         builder.put(value.getSerializedName(), value);
      }

      NAME_VALUES = builder.build();
      VALUES = values();
   }
}
