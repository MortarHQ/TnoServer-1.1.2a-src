package com.github.dominickwd04.traddon;

import com.github.dominickwd04.traddon.client.TrAddonClient;
import com.github.dominickwd04.traddon.config.TrAddonConfigs;
import com.github.dominickwd04.traddon.handler.TrAddonHandlers;
import com.github.dominickwd04.traddon.registry.TrAddonRegistry;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;

public final class TrAddon {
   public static final String MOD_ID = "traddon";

   public static void init() {
      TrAddonConfigs.init();
      TrAddonRegistry.init();
      TrAddonHandlers.init();
      TrAddonConfigs.addToConfig();
      if (Platform.getEnvironment() == Env.CLIENT) {
         TrAddonClient.init();
      }
   }
}
