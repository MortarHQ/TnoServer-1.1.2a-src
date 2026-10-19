package com.github.ysbbbbbb.kaleidoscopecookery.init.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;

@EventBusSubscriber(bus = Bus.MOD, modid = "kaleidoscope_cookery")
public class CapabilitiesRegistry {
   @SubscribeEvent
   public static void registerGenericItemHandlers(RegisterCapabilitiesEvent event) {
      event.registerBlockEntity(ItemHandler.BLOCK, ModBlocks.OIL_POT_BE.get(), (b, v) -> b.createHandler());
   }
}
