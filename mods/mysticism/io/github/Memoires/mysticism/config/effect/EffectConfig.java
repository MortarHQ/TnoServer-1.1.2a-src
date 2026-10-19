package io.github.Memoires.mysticism.config.effect;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class EffectConfig extends ManasConfig {
   public EffectConfig.BrimstoneFlames BrimstoneFlames = new EffectConfig.BrimstoneFlames();

   public String getFileName() {
      return "mysticism/effect/effects";
   }

   public static class BrimstoneFlames extends ManasSubConfig {
      @Comment("How much damage that the effect Brimstone Flames does each second per level.")
      public float brimstoneFlamesDamage = 15.0F;
      @Comment("Should Brimstone Flames bypass Resistances and degrade Nullifications?")
      public boolean brimstoneFlamesBypassResistances = true;
      @Comment("Should Brimstone Flames cancel Ultraspeed Regeneration?")
      public boolean brimstoneFlamesBypassUSR = true;
      @Comment("Should Brimstone Flames cancel Infinite Regeneration?")
      public boolean brimstoneFlamesBypassIR = true;
   }
}
