package io.github.Memoires.mysticism.config.ability.magic;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;

@SyncToClient
public class SpiritualMysticismMagicConfig extends ManasConfig {
   public SpiritualMysticismMagicConfig.GravitationalVoid GravitationalVoid = new SpiritualMysticismMagicConfig.GravitationalVoid();

   public String getFileName() {
      return "mysticism/ability/magic/spiritual_config";
   }

   public static class GravitationalVoid extends ManasSubConfig {
      @Comment("Cast time in tick.")
      public int castTime = 400;
      @Comment("Cast time in tick when mastered.")
      public int castTimeMastered = 120;
      @Comment("Magicule Cost to cast.")
      public double magiculeCost = 500000.0;
      @Comment("The base gravity damage of the black hole.")
      public float baseGravityDamage = 50.0F;
      @Comment("The base magic damage of the black hole.")
      public float baseMagicDamage = 50.0F;
      @Comment("The max gravity damage of the black hole.")
      public float maxGravityDamage = 250.0F;
      @Comment("The max magic damage of the black hole.")
      public float maxMagicDamage = 250.0F;
      @Comment("The maximum size of the black hole.")
      public int maxSize = 10;
      @Comment("The lifespan of the black hole in ticks.")
      public int lifespan = 1200;
      @Comment("The lifespan of the black hole in ticks when the magic is mastered.")
      public int lifespanMastered = 3600;
      @Comment("The cooldown in second of the magic.")
      public int cooldown = 420;
      @Comment("The cooldown in second of the magic when mastered.")
      public int cooldownMastered = 300;
   }
}
