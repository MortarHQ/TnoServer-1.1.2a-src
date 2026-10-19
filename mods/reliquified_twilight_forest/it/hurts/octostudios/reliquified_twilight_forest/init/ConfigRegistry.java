package it.hurts.octostudios.reliquified_twilight_forest.init;

import it.hurts.octostudios.octolib.module.config.ConfigManager;
import it.hurts.octostudios.reliquified_twilight_forest.config.RTFConfig;

public class ConfigRegistry {
   public static RTFConfig GENERAL = new RTFConfig();

   public static void register() {
      ConfigManager.registerConfig("reliquified_twilight_forest/general", GENERAL);
   }
}
