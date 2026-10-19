package org.crypticdev.ancient_artifacts.config.dynamic_effects;

import io.github.manasmods.manascore.config.api.Comment;
import org.crypticdev.ancient_artifacts.config.SpecialEffectConfig;

public class RisingInfernoConfig extends SpecialEffectConfig {
   public double max_stack = 0.2;
   public double increase_per_stack = 0.01;
   public double time_till_next_stack = 3.0;
   public double time_reduction_per_refine = 0.25;
   @Comment("Max Refinement -1 is no max")
   public int max_refinements = 2;

   public String getFileName() {
      return "ancient_artifacts/effect/rising_inferno";
   }

   @Override
   public int max_refinements() {
      return this.max_refinements;
   }
}
