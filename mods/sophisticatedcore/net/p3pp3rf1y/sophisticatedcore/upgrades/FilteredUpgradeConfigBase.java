package net.p3pp3rf1y.sophisticatedcore.upgrades;

import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class FilteredUpgradeConfigBase {
   public final IntValue filterSlots;
   public final IntValue slotsInRow;

   protected FilteredUpgradeConfigBase(Builder builder, String name, String path, int defaultFilterSlots, int defaultSlotsInRow) {
      builder.comment(name + " Settings").push(path);
      this.filterSlots = builder.comment("Number of " + name + "'s filter slots").defineInRange("filterSlots", defaultFilterSlots, 1, 20);
      this.slotsInRow = builder.comment("Number of filter slots displayed in a row").defineInRange("slotsInRow", defaultSlotsInRow, 1, 6);
   }
}
