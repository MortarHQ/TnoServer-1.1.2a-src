package com.github.b4ndithelps.tensuraenigmatic.compat;

import com.github.b4ndithelps.tensuraenigmatic.handler.CursedRingEnhancementHandler;
import com.github.b4ndithelps.tensuraenigmatic.handler.TheCubeEnhancementHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;

public final class EnigmaticCompat {
   public static final String MOD_ID = "enigmaticlegacyplus";

   public static void register(IEventBus gameBus) {
      gameBus.register(CursedRingEnhancementHandler.class);
      gameBus.register(TheCubeEnhancementHandler.class);
      if (FMLEnvironment.dist == Dist.CLIENT) {
         EnigmaticClientCompat.register(gameBus);
      }
   }

   private EnigmaticCompat() {
   }
}
