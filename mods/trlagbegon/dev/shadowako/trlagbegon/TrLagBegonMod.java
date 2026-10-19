package dev.shadowako.trlagbegon;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod("trlagbegon")
public final class TrLagBegonMod {
   public static final String MOD_ID = "trlagbegon";
   public static final Logger LOGGER = LogUtils.getLogger();

   public TrLagBegonMod(IEventBus modEventBus) {
      NeoForge.EVENT_BUS.addListener(TrLagBegonCommands::register);
      NeoForge.EVENT_BUS.addListener(StressTestRunner::onServerTick);
      modEventBus.addListener(ModLifecycle::onCommonSetup);
   }
}
