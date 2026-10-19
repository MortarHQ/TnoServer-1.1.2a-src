package net.pixeldreamstudios.mythika.neoforge.client;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.pixeldreamstudios.mythika.MythikaConfig;
import net.pixeldreamstudios.mythika.client.CyclopsRenderer;
import net.pixeldreamstudios.mythika.client.MinotaurRenderer;
import net.pixeldreamstudios.mythika.client.SatyrRenderer;
import net.pixeldreamstudios.mythika.registry.EntityRegistry;

public final class MythikaNeoForgeClient {
   private MythikaNeoForgeClient() {
   }

   public static void init(IEventBus modEventBus, ModContainer container) {
      modEventBus.addListener(MythikaNeoForgeClient::onRegisterRenderers);
      container.registerExtensionPoint(
         IConfigScreenFactory.class, (IConfigScreenFactory)(owner, parent) -> (Screen)AutoConfig.getConfigScreen(MythikaConfig.class, parent).get()
      );
   }

   private static void onRegisterRenderers(RegisterRenderers event) {
      event.registerEntityRenderer(EntityRegistry.CYCLOPS.get(), CyclopsRenderer::new);
      event.registerEntityRenderer(EntityRegistry.MINOTAUR.get(), MinotaurRenderer::new);
      event.registerEntityRenderer(EntityRegistry.SATYR.get(), SatyrRenderer::new);
   }
}
