package com.faboslav.variantsandventures.neoforge;

import com.faboslav.variantsandventures.common.VariantsAndVenturesClient;
import com.faboslav.variantsandventures.common.events.client.RegisterEntityLayersEvent;
import com.faboslav.variantsandventures.common.events.client.RegisterEntityRenderersEvent;
import com.faboslav.variantsandventures.common.events.item.RegisterItemColorEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public final class VariantsAndVenturesClientNeoForge {
   public static void init(IEventBus modEventBus, IEventBus eventBus) {
      VariantsAndVenturesClient.init();
      modEventBus.addListener(VariantsAndVenturesClientNeoForge::onClientSetup);
      modEventBus.addListener(VariantsAndVenturesClientNeoForge::onRegisterEntityRenderers);
      modEventBus.addListener(VariantsAndVenturesClientNeoForge::onRegisterEntityLayers);
      modEventBus.addListener(VariantsAndVenturesClientNeoForge::onRegisterItemColors);
   }

   private static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(
         () -> {
            if (ModList.get().isLoaded("yet_another_config_lib_v3")) {
               ModLoadingContext.get()
                  .registerExtensionPoint(IConfigScreenFactory.class, () -> (client, screen) -> VariantsAndVenturesClient.getConfigScreen(screen));
            }
         }
      );
   }

   private static void onRegisterEntityRenderers(RegisterRenderers event) {
      RegisterEntityRenderersEvent.EVENT.invoke(new RegisterEntityRenderersEvent(event::registerEntityRenderer));
   }

   private static void onRegisterEntityLayers(RegisterLayerDefinitions event) {
      RegisterEntityLayersEvent.EVENT.invoke(new RegisterEntityLayersEvent(event::registerLayerDefinition));
   }

   private static void onRegisterItemColors(Item event) {
      RegisterItemColorEvent.EVENT.invoke(new RegisterItemColorEvent(event::register));
   }
}
