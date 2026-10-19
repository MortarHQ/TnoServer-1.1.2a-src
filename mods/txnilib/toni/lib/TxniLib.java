package toni.lib;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("txnilib")
public class TxniLib {
   public static final String MODNAME = "TxniLib";
   public static final String ID = "txnilib";
   public static final Logger LOGGER = LogManager.getLogger("TxniLib");

   public TxniLib(IEventBus modEventBus, ModContainer modContainer) {
      modEventBus.addListener(this::commonSetup);
      modEventBus.addListener(this::clientSetup);
   }

   public void onInitialize() {
      System.out.println("TxniLib onInitialize!");
   }

   public void onInitializeClient() {
   }

   public void commonSetup(FMLCommonSetupEvent event) {
      this.onInitialize();
   }

   public void clientSetup(FMLClientSetupEvent event) {
      this.onInitializeClient();
   }
}
