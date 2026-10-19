package com.cupboard;

import com.cupboard.config.CommonConfiguration;
import com.cupboard.config.CupboardConfig;
import com.cupboard.event.EventHandler;
import com.sun.management.HotSpotDiagnosticMXBean;
import java.lang.management.ManagementFactory;
import java.util.Random;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("cupboard")
public class Cupboard {
   public static final String MOD_ID = "cupboard";
   public static final Logger LOGGER = LogManager.getLogger();
   public static CupboardConfig<CommonConfiguration> config = new CupboardConfig<>("cupboard", new CommonConfiguration());
   public static Random rand = new Random();

   public Cupboard(IEventBus modEventBus, ModContainer modContainer) {
      if (config.getCommonConfig().forceHeapDumpOnOOM) {
         try {
            HotSpotDiagnosticMXBean bean = ManagementFactory.newPlatformMXBeanProxy(
               ManagementFactory.getPlatformMBeanServer(), "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class
            );
            bean.setVMOption("HeapDumpOnOutOfMemoryError", "true");
            bean.setVMOption("HeapDumpPath", FMLPaths.GAMEDIR.get().toString());
         } catch (Exception e) {
            LOGGER.error("Failed to enable heapdump option: ", e);
         }
      }

      NeoForge.EVENT_BUS.register(EventHandler.class);
      modEventBus.addListener(this::clientSetup);
   }

   public void clientSetup(FMLClientSetupEvent event) {
      CupboardClient.onInitializeClient(event);
   }
}
