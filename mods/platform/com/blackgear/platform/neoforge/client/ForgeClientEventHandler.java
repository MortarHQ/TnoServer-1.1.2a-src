package com.blackgear.platform.neoforge.client;

import com.blackgear.platform.core.events.ResourceReloadManager;
import com.blackgear.platform.core.util.EventBus;
import java.util.function.Consumer;

public class ForgeClientEventHandler {
   public static void registerClientResourceListeners(Consumer<ResourceReloadManager.ListenerEvent> exporter) {
      EventBus.get(EventBus.MOD).addListener(event -> exporter.accept((id, reloadListener) -> event.registerReloadListener(reloadListener)));
   }
}
