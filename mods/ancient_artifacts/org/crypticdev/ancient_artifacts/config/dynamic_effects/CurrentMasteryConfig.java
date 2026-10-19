package org.crypticdev.ancient_artifacts.config.dynamic_effects;

import io.github.manasmods.manascore.config.api.Comment;
import org.crypticdev.ancient_artifacts.config.SpecialEffectConfig;

public class CurrentMasteryConfig extends SpecialEffectConfig {
   public double base_value = 0.5;
   public double value_per_refinement = 0.05;
   @Comment("Max Refinement -1 is no max")
   public int max_refinements = -1;

   public String getFileName() {
      return "ancient_artifacts/effect/current_mastery";
   }

   @Override
   public int max_refinements() {
      return this.max_refinements;
   }
}
