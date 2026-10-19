package org.crypticdev.ancient_artifacts.config.dynamic_effects;

import io.github.manasmods.manascore.config.api.Comment;
import org.crypticdev.ancient_artifacts.config.SpecialEffectConfig;

public class OgresWrathConfig extends SpecialEffectConfig {
   public double health_percent_for_activate = 0.25;
   public double base_value = 0.15;
   public double value_per_refinement = 0.025;
   @Comment("Max Refinement -1 is no max")
   public int max_refinements = 2;

   public String getFileName() {
      return "ancient_artifacts/effect/ogres_wrath";
   }

   @Override
   public int max_refinements() {
      return this.max_refinements;
   }
}
