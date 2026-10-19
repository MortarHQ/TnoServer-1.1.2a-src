package com.github.b4ndithelps.tennogamenolife.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class TenreincarnationGeneralConfig extends ManasConfig {
   public TenreincarnationGeneralConfig.General General = new TenreincarnationGeneralConfig.General();

   public String getFileName() {
      return "tennogamenolife/general";
   }

   public static class General extends ManasSubConfig {
      @Comment("Should the Tensura configurations add TenNoGameNoLife's skills and races on next launch?")
      public boolean refresh = true;
   }
}
