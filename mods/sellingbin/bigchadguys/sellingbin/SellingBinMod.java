package bigchadguys.sellingbin;

import bigchadguys.sellingbin.init.ModRegistries;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent.ServerState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SellingBinMod {
   public static MinecraftServer SERVER;
   public static final String ID = "sellingbin";
   public static final Logger LOGGER = LogManager.getLogger("sellingbin");

   public static void onInitialize() {
      LifecycleEvent.SERVER_BEFORE_START.register((ServerState)instance -> SERVER = instance);
      LifecycleEvent.SERVER_STOPPED.register((ServerState)instance -> SERVER = null);
      ModRegistries.register();
   }

   public static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("sellingbin", path);
   }
}
