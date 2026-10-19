package net.blay09.mods.waystones;

import java.lang.reflect.InvocationTargetException;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("waystones")
public class NeoForgeWaystones {
   private static final Logger logger = LoggerFactory.getLogger(NeoForgeWaystones.class);

   public NeoForgeWaystones(IEventBus modEventBus) {
      NeoForgeLoadContext context = new NeoForgeLoadContext(modEventBus);
      Balm.initialize("waystones", context, Waystones::initialize);
      Balm.initializeIfLoaded("theoneprobe", "net.blay09.mods.waystones.compat.TheOneProbeIntegration");
      if (Balm.isModLoaded("repurposed_structures")) {
         try {
            Class.forName("net.blay09.mods.waystones.compat.RepurposedStructuresIntegration").getConstructor().newInstance();
         } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException e) {
            logger.error("Failed to load Repurposed Structures integration", e);
         }
      }
   }
}
