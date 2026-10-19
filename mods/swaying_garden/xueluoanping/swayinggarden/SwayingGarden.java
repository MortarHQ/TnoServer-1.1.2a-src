package xueluoanping.swayinggarden;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xueluoanping.swayinggarden.config.General;

@Mod(value = "swaying_garden", dist = Dist.CLIENT)
public class SwayingGarden {
   public static final String MOD_ID = "swaying_garden";
   public static final Logger LOGGER = LogManager.getLogger();
   public static final boolean useLogger = Objects.equals(System.getProperty("forgegradle.runs.dev"), "true");

   public SwayingGarden(IEventBus modEventBus, ModContainer modContainer) {
      modContainer.registerConfig(Type.CLIENT, General.CLIENT_CONFIG);
      if (FMLLoader.getDist() == Dist.CLIENT) {
         modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
      }
   }

   public static void logger(Object... x) {
      if (useLogger) {
         LOGGER.info(getStr(x));
      }
   }

   public static String getStr(Object... x) {
      StringBuilder output = new StringBuilder();

      for (Object i : x) {
         output.append(i).append(" ");
      }

      return output.toString();
   }

   public static void error(Object... x) {
      LOGGER.error(getStr(x));
   }

   public static ResourceLocation rl(String name) {
      return ResourceLocation.fromNamespaceAndPath("swaying_garden", name);
   }
}
