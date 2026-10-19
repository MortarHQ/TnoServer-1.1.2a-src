package org.crypticdev.stextras;

import org.crypticdev.stextras.client.StextrasClient;
import org.crypticdev.stextras.config.STExtarsConfigs;
import org.crypticdev.stextras.registary.STExtraRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Stextras {
   public static final String MOD_ID = "stextras";
   public static final Logger LOGGER = LoggerFactory.getLogger("stextras");
   private static boolean initialized;

   public static void init() {
      if (!initialized) {
         initialized = true;
         STExtarsConfigs.init();
         STExtraRegistry.init();
      }
   }

   public static void initClient() {
      StextrasClient.init();
   }

   private Stextras() {
   }
}
