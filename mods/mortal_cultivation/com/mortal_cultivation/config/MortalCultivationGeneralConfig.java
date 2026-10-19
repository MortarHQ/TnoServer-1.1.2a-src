package com.mortal_cultivation.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class MortalCultivationGeneralConfig extends ManasConfig {
   public MortalCultivationGeneralConfig.General General = new MortalCultivationGeneralConfig.General();

   public String getFileName() {
      return "mortal_cultivation/general";
   }

   public static class General extends ManasSubConfig {
      @Comment("Should the Tensura configurations add Mortal Cultivation's skills and races on next launch?")
      public boolean refresh = true;
   }
}
