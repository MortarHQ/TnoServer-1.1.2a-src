package de.melanx.simplebackups;

import de.melanx.simplebackups.client.ClientInit;
import de.melanx.simplebackups.config.CommonConfig;
import de.melanx.simplebackups.config.ExperimentalConfig;
import de.melanx.simplebackups.config.ServerConfig;
import de.melanx.simplebackups.network.Pause;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("simplebackups")
public class SimpleBackups {
   public static final Logger LOGGER = LoggerFactory.getLogger(SimpleBackups.class);
   public static final String MODID = "simplebackups";

   public SimpleBackups(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
      modContainer.registerConfig(Type.COMMON, CommonConfig.CONFIG);
      modContainer.registerConfig(Type.COMMON, ExperimentalConfig.CONFIG, "simplebackups-common-experimental.toml");
      modContainer.registerConfig(Type.SERVER, ServerConfig.CONFIG);
      NeoForge.EVENT_BUS.register(new EventListener());
      modEventBus.addListener(this::setup);
      modEventBus.addListener(this::onRegisterPayloadHandler);
      if (CommonConfig.backupsDisabledByJvmArg()) {
         LOGGER.info("##########################################");
         LOGGER.info("#  Backups are disabled by JVM argument  #");
         LOGGER.info("##########################################");
      }

      if (dist.isClient()) {
         ClientInit.init(modEventBus, modContainer);
      }
   }

   private void onRegisterPayloadHandler(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("simplebackups").versioned("1.0").optional();
      registrar.playToClient(Pause.TYPE, Pause.CODEC, Pause::handle);
   }

   private void setup(FMLCommonSetupEvent event) {
   }
}
