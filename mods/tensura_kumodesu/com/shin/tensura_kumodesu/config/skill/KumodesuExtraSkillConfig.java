package com.shin.tensura_kumodesu.config.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class KumodesuExtraSkillConfig extends ManasConfig {
   public KumodesuExtraSkillConfig.DeadlyPoison DeadlyPoison = new KumodesuExtraSkillConfig.DeadlyPoison();

   public String getFileName() {
      return "tensura_kumodesu/ability/extra_config";
   }

   public static class DeadlyPoison extends ManasSubConfig {
      @Comment("The duration in tick of the Poison effect.")
      public int poisonDuration = 160;
      @Comment("The level of the Poison effect.")
      public int poisonLevel = 1;
   }
}
