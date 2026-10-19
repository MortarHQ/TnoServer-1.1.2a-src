package net.p3pp3rf1y.sophisticatedcore.upgrades.cooking;

import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class AutoCookingUpgradeConfig extends CookingUpgradeConfig {
   public final IntValue inputFilterSlots;
   public final IntValue inputFilterSlotsInRow;
   public final IntValue fuelFilterSlots;
   public final IntValue fuelFilterSlotsInRow;

   public AutoCookingUpgradeConfig(Builder builder, String upgradeName, String path) {
      super(builder, upgradeName, path);
      this.inputFilterSlots = builder.comment("Number of input filter slots").defineInRange("inputFilterSlots", 8, 1, 20);
      this.inputFilterSlotsInRow = builder.comment("Number of input filter slots displayed in a row").defineInRange("inputFilterSlotsInRow", 4, 1, 6);
      this.fuelFilterSlots = builder.comment("Number of fuel filter slots").defineInRange("fuelFilterSlots", 4, 1, 20);
      this.fuelFilterSlotsInRow = builder.comment("Number of fuel filter slots displayed in a row").defineInRange("fuelFilterSlotsInRow", 4, 1, 6);
      builder.pop();
   }
}
