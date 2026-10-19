package net.p3pp3rf1y.sophisticatedcore.upgrades.voiding;

import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilteredUpgradeConfigBase;

public class VoidUpgradeConfig extends FilteredUpgradeConfigBase {
   public final BooleanValue voidAlwaysEnabled;

   public VoidUpgradeConfig(Builder builder, String name, String path, int defaultFilterSlots, int defaultSlotsInRow) {
      super(builder, name, path, defaultFilterSlots, defaultSlotsInRow);
      this.voidAlwaysEnabled = builder.comment("Determines whether void upgrade allows voiding always or it only has overflow options")
         .define("voidAlwaysEnabled", true);
      builder.pop();
   }
}
