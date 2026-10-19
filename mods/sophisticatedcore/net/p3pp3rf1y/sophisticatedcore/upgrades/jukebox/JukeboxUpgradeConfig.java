package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class JukeboxUpgradeConfig {
   public final IntValue numberOfSlots;
   public final IntValue slotsInRow;

   public JukeboxUpgradeConfig(Builder builder, String upgradeName, String path, int defaultNumberOfSlots) {
      builder.comment(upgradeName + " Settings").push(path);
      this.numberOfSlots = builder.comment("Number of slots for discs in jukebox upgrade").defineInRange("numberOfSlots", defaultNumberOfSlots, 1, 16);
      this.slotsInRow = builder.comment("Number of lots displayed in a row").defineInRange("slotsInRow", 4, 1, 6);
      builder.pop();
   }
}
