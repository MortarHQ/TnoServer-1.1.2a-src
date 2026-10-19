package org.crypticdev.stextras.config.ability.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class STExtrasIntrinsicSkillConfig extends ManasConfig {
   public STExtrasIntrinsicSkillConfig.Blacksmith Blacksmith = new STExtrasIntrinsicSkillConfig.Blacksmith();

   public String getFileName() {
      return "stextras/ability/skill/intrinsic_config";
   }

   public static class Blacksmith extends ManasSubConfig {
      @Comment("The cooldown in seconds.")
      public int cooldown = 60;
      @Comment("The cooldown in seconds when the skill is mastered.")
      public int cooldownMastered = 30;
   }
}
