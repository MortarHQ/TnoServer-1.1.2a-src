package dev.shadowako.trlagbegon;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public final class ModLifecycle {
   private ModLifecycle() {
   }

   public static void onCommonSetup(FMLCommonSetupEvent event) {
      TrLagBegonMod.LOGGER.info("[trlagbegon] Common setup complete.");
   }
}
