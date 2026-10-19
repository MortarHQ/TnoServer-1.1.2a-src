package com.shin.tensura_kumodesu;

import com.shin.tensura_kumodesu.client.renderer.KumodesuEntityRenderer;
import com.shin.tensura_kumodesu.config.KumodesuConfig;
import com.shin.tensura_kumodesu.registry.KumodesuRegistry;
import com.shin.tensura_kumodesu.registry.blocks.KumodesuBlocks;
import com.shin.tensura_kumodesu.registry.entities.KumodesuEntities;
import com.shin.tensura_kumodesu.registry.items.KumodesuItems;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent.ClientState;
import io.github.manasmods.manascore.config.ConfigRegistry;

public final class TensuraKumodesu {
   public static final String MOD_ID = "tensura_kumodesu";

   public static void init() {
      KumodesuConfig.init();
      KumodesuRegistry.init();
      KumodesuEntities.register();
      KumodesuBlocks.init();
      KumodesuItems.init();
   }

   public static void initClient() {
      ClientLifecycleEvent.CLIENT_STARTED.register((ClientState)listener -> KumodesuEntityRenderer.initEntityRendering());
   }

   public static void postInit() {
      KumodesuConfig.addToConfig();
      ConfigRegistry.saveAllConfigs();
   }
}
