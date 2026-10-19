package net.crypticmc.tr_unique_monsters;

import net.crypticmc.tr_unique_monsters.registry.TRUniqueMobsRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TensuraUniqueMonsters {
   public static final String MOD_ID = "tr_unique_monsters";
   public static final Logger LOG = LoggerFactory.getLogger("tr_unique_monsters");

   public static void init() {
      TRUniqueMobsRegistry.init();
   }
}
