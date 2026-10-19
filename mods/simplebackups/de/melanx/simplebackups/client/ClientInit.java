package de.melanx.simplebackups.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

public class ClientInit {
   public static void init(IEventBus modEventBus, ModContainer modContainer) {
      NeoForge.EVENT_BUS.register(new ClientEventHandler());
      modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
   }
}
