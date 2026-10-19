package io.github.manasmods.tensura_neb;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;

public class NebConfig extends ManasConfig {
   @Comment("One of how many chances for an otherworlder to spawn as Rimuru.")
   public int rimuruChance = 0;
   @Comment("One of how many chances for an otherworlder to spawn as Carrion.")
   public int carrionChance = 0;
   @Comment("One of how many chances for an otherworlder to spawn as Luminous.")
   public int luminousChance = 0;

   public String getFileName() {
      return "tensura/neb_config";
   }
}
