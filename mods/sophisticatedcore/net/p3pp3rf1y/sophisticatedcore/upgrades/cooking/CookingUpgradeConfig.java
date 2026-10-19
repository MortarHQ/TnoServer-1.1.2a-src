package net.p3pp3rf1y.sophisticatedcore.upgrades.cooking;

import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;

public class CookingUpgradeConfig {
   public final DoubleValue cookingSpeedMultiplier;
   public final DoubleValue fuelEfficiencyMultiplier;

   public CookingUpgradeConfig(Builder builder, String upgradeName, String path) {
      builder.comment(upgradeName + " Settings").push(path);
      this.cookingSpeedMultiplier = builder.comment("Smelting speed multiplier (1.0 equals speed at which vanilla furnace smelts items)")
         .defineInRange("smeltingSpeedMultiplier", 1.0, 0.25, 4.0);
      this.fuelEfficiencyMultiplier = builder.comment("Fuel efficiency multiplier (1.0 equals speed at which it's used in vanilla furnace)")
         .defineInRange("fuelEfficiencyMultiplier", 1.0, 0.25, 4.0);
   }

   public static CookingUpgradeConfig getInstance(Builder builder, String upgradeName, String path) {
      CookingUpgradeConfig instance = new CookingUpgradeConfig(builder, upgradeName, path);
      builder.pop();
      return instance;
   }
}
