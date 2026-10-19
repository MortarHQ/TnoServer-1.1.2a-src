package com.cerbon.bosses_of_mass_destruction.entity.ai.goals;

import java.util.Arrays;
import java.util.List;
import net.minecraft.world.entity.ai.goal.Goal;

public class CompositeGoal extends Goal {
   private final List<Goal> goals;

   public CompositeGoal(Goal... goals) {
      this.goals = Arrays.asList(goals);

      for (Goal goal : goals) {
         this.getFlags().addAll(goal.getFlags());
      }
   }

   public boolean canUse() {
      return this.goals.stream().allMatch(Goal::canUse);
   }

   public boolean isInterruptable() {
      return this.goals.stream().allMatch(Goal::isInterruptable);
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void tick() {
      this.goals.forEach(Goal::tick);
   }

   public void stop() {
      this.goals.forEach(Goal::stop);
   }

   public void start() {
      this.goals.forEach(Goal::start);
   }
}
