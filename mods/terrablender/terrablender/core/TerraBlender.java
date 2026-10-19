package terrablender.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.config.TerraBlenderConfig;

public class TerraBlender {
   public static final String MOD_ID = "terrablender";
   public static final Logger LOGGER = LogManager.getLogger("terrablender");
   public static TerraBlenderConfig CONFIG;

   public static void setConfig(TerraBlenderConfig config) {
      CONFIG = config;
   }
}
