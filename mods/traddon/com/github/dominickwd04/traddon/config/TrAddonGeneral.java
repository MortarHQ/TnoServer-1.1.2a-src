package com.github.dominickwd04.traddon.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class TrAddonGeneral extends ManasConfig {
   public TrAddonGeneral.General General = new TrAddonGeneral.General();

   public String getFileName() {
      return "traddon/general";
   }

   public static class General extends ManasSubConfig {
      @Comment("Should the TR Addon skills and races be added to the starting lists on the next launch?")
      public boolean refresh = true;
   }
}
