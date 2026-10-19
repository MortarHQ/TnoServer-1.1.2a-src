package it.hurts.octostudios.octolib.module;

import it.hurts.octostudios.octolib.module.config.annotation.registration.ObjectConfig;
import it.hurts.octostudios.octolib.module.config.impl.ConfigSide;

public class ConfigTest {
   @ObjectConfig(value = "clientc", side = ConfigSide.CLIENT)
   static ConfigTest.ConfigA configA = new ConfigTest.ConfigA();
   @ObjectConfig(value = "serverc", side = ConfigSide.SERVER)
   static ConfigTest.ConfigA configB = new ConfigTest.ConfigA();

   public static class ConfigA {
      String a = "It's a config";
      int lol = 109;
   }
}
