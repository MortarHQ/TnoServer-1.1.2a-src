package com.github.ysbbbbbb.kaleidoscopecookery.init.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.datamap.resources.MillstoneBindableDataReloadListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@EventBusSubscriber(modid = "kaleidoscope_cookery")
public class DatapackReloadListenerEvent {
   @SubscribeEvent
   public static void onAddReloadListenerEvent(AddReloadListenerEvent event) {
      event.addListener(new MillstoneBindableDataReloadListener());
   }
}
