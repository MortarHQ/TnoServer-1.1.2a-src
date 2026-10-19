package com.shin.tensura_kumodesu.config.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class KumodesuIntrinsicSkillConfig extends ManasConfig {
   public KumodesuIntrinsicSkillConfig.DivineMagicRelease DivineMagicRelease = new KumodesuIntrinsicSkillConfig.DivineMagicRelease();
   public KumodesuIntrinsicSkillConfig.DeadlySpiderPoison DeadlySpiderPoison = new KumodesuIntrinsicSkillConfig.DeadlySpiderPoison();
   public KumodesuIntrinsicSkillConfig.FatalSpiderPoison FatalSpiderPoison = new KumodesuIntrinsicSkillConfig.FatalSpiderPoison();

   public String getFileName() {
      return "tensura_kumodesu/ability/intrinsic_config";
   }

   public static class DeadlySpiderPoison extends ManasSubConfig {
      @Comment("The duration in tick of the Poison effect.")
      public int poisonDuration = 160;
      @Comment("The level of the Poison effect.")
      public int poisonLevel = 1;
   }

   public static class DivineMagicRelease extends ManasSubConfig {
      @Comment("The chant speed multiplier when toggled.")
      public double chantSpeed = 1.125;
      @Comment("The bonus number of learning point to gain when toggled.")
      public double learningPoint = 3.0;
      @Comment("The bonus number of mastery point to gain when toggled.")
      public double masteryPoint = 3.0;
      @Comment("Magicule regeneration rate multiplier.")
      public double magiculeregen = 19.0;
   }

   public static class FatalSpiderPoison extends ManasSubConfig {
      @Comment("The duration in tick of the Poison effect.")
      public int poisonDuration = 140;
      @Comment("The level of the Poison effect.")
      public int poisonLevel = 1;
   }
}
