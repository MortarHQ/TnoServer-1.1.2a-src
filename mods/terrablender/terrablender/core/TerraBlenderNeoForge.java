package terrablender.core;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import terrablender.config.TerraBlenderConfig;
import terrablender.handler.InitializationHandler;

@Mod("terrablender")
public class TerraBlenderNeoForge {
   private static final TerraBlenderConfig CONFIG = new TerraBlenderConfig(FMLPaths.CONFIGDIR.get().resolve("terrablender.toml"));

   public TerraBlenderNeoForge() {
      NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, InitializationHandler::onServerAboutToStart);
      TerraBlender.setConfig(CONFIG);
   }
}
