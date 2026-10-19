package net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy;

import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class AlchemyUpgradeConfig {
   public final IntValue filterSlots;

   public AlchemyUpgradeConfig(Builder builder, String name, String path, int defaultFilterSlots) {
      builder.comment(name + " Settings").push(path);
      this.filterSlots = builder.comment("Number of " + name + "'s filter slots").defineInRange("filterSlots", defaultFilterSlots, 1, 20);
      builder.pop();
   }
}
