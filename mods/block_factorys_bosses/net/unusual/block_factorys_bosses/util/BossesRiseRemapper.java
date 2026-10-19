package net.unusual.block_factorys_bosses.util;

import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

public class BossesRiseRemapper {
   public static void addRegistryAliases() {
      BossesRiseItems.REGISTRY.addAlias(BossesRise.prefix("frozen_fist"), BossesRise.prefix("ice_gauntlet"));
      BossesRiseItems.REGISTRY.addAlias(BossesRise.prefix("sandworm_dart"), BossesRise.prefix("sandworm_gauntlet"));
   }
}
