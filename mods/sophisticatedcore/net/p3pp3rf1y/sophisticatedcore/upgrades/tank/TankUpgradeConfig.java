package net.p3pp3rf1y.sophisticatedcore.upgrades.tank;

import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class TankUpgradeConfig {
   public final IntValue capacityPerSlotRow;
   public final DoubleValue stackMultiplierRatio;
   public final IntValue autoFillDrainContainerCooldown;
   public final IntValue maxInputOutput;

   public TankUpgradeConfig(Builder builder) {
      builder.comment("Tank Upgrade Settings").push("tankUpgrade");
      this.capacityPerSlotRow = builder.comment("Capacity in mB the tank upgrade will have per row of storage slots")
         .defineInRange("capacityPerSlotRow", 4000, 500, 20000);
      this.stackMultiplierRatio = builder.comment(
            "Ratio that gets applied (multiplies) to inventory stack multiplier before this is applied to tank capacity. Value lower than 1 makes stack multiplier affect the capacity less, higher makes it affect the capacity more. 0 turns off stack multiplier affecting tank capacity"
         )
         .defineInRange("stackMultiplierRatio", 1.0, 0.0, 5.0);
      this.autoFillDrainContainerCooldown = builder.comment(
            "Cooldown between fill/drain actions done on fluid containers in tank slots. Only fills/drains one bucket worth to/from container after this cooldown and then waits again."
         )
         .defineInRange("autoFillDrainContainerCooldown", 20, 1, 100);
      this.maxInputOutput = builder.comment(
            "How much mB can be transfered in / out per operation. This is a base transfer rate and same as max tank capacity gets multiplied by number of rows in storage and stack multiplier."
         )
         .defineInRange("maxInputOutput", 20, 1, 1000);
      builder.pop();
   }
}
