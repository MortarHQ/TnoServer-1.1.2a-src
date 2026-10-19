package dev.ftbqlang;

import dev.ftbqlang.network.LanguageNetwork;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("ftbqlang")
public final class FTBQLang {
   public static final String MOD_ID = "ftbqlang";
   public static final Logger LOGGER = LoggerFactory.getLogger("ftbqlang");

   public FTBQLang(IEventBus modBus) {
      modBus.addListener(LanguageNetwork::register);
   }
}
