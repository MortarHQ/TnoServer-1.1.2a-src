package io.github.Memoires.mysticism.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class MysticismGeneralConfig extends ManasConfig {
   public MysticismGeneralConfig.General General = new MysticismGeneralConfig.General();

   public String getFileName() {
      return "mysticism/general";
   }

   public static class General extends ManasSubConfig {
      @Comment("Should the Tensura configurations add Mysticism's skills and races on next launch?")
      public boolean refresh = true;
      @Comment("The default flying speed of all Mysticism races.")
      public double flightSpeed = 0.05;
      @Comment("SE cost multiplier when acquiring a Unique Skill. Default: 4, which means the skill's magicule cost * 4")
      public int seCostMultiplier = 4;
   }
}
