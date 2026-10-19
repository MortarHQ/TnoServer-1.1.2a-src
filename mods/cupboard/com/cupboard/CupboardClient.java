package com.cupboard;

import com.cupboard.event.ClientEventHandler;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

public class CupboardClient {
   public static void onInitializeClient(FMLClientSetupEvent event) {
      NeoForge.EVENT_BUS.register(ClientEventHandler.class);
   }
}
