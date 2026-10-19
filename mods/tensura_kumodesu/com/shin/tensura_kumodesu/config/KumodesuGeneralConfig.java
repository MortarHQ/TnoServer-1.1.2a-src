package com.shin.tensura_kumodesu.config;

import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class KumodesuGeneralConfig extends ManasConfig {
   public KumodesuGeneralConfig.General General = new KumodesuGeneralConfig.General();

   public String getFileName() {
      return "tensura_kumodesu/general";
   }

   public static class General extends ManasSubConfig {
      public boolean refresh = true;
   }
}
