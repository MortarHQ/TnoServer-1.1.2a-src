package fuzs.puzzleslib.neoforge.impl.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.impl.PuzzlesLib;
import fuzs.puzzleslib.impl.client.PuzzlesLibClient;
import fuzs.puzzleslib.neoforge.impl.client.commands.NeoForgeConfigCommand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = "puzzleslib", dist = Dist.CLIENT)
public class PuzzlesLibNeoForgeClient {
   public PuzzlesLibNeoForgeClient() {
      ClientModConstructor.construct("puzzleslib", PuzzlesLibClient::new);
      setupDevelopmentEnvironment();
   }

   private static void setupDevelopmentEnvironment() {
      if (PuzzlesLib.isDevelopmentEnvironmentWithoutDataGeneration()) {
         registerEventHandlers(NeoForge.EVENT_BUS);
      }
   }

   private static void registerEventHandlers(IEventBus eventBus) {
      eventBus.addListener(
         evt -> NeoForgeConfigCommand.register(evt.getDispatcher(), (commandSourceStack, component) -> commandSourceStack.sendSuccess(() -> component, true))
      );
   }
}
