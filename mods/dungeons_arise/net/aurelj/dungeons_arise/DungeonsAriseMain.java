package net.aurelj.dungeons_arise;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("dungeons_arise")
public class DungeonsAriseMain {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final String MODID = "dungeons_arise";

   public DungeonsAriseMain(IEventBus modEventBus) {
      WDAStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
   }
}
