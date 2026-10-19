package com.cerbon.bosses_of_mass_destruction.entity.ai.goals;

import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IAction;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionStop;
import java.util.function.Supplier;
import net.minecraft.world.entity.ai.goal.Goal;

public class ActionGoal extends Goal {
   private final Supplier<Boolean> hasTarget;
   private final Supplier<Boolean> canContinue;
   private IAction tickAction = () -> {};
   private IAction startAction = () -> {};
   private IActionStop endAction = () -> {};

   public ActionGoal(Supplier<Boolean> hasTarget, Supplier<Boolean> canContinue, IAction tickAction, IAction startAction, IActionStop endAction) {
      this.hasTarget = hasTarget;
      this.canContinue = canContinue != null ? canContinue : hasTarget;
      this.tickAction = tickAction != null ? tickAction : this.tickAction;
      this.startAction = startAction != null ? startAction : this.startAction;
      this.endAction = endAction != null ? endAction : this.endAction;
   }

   public boolean canUse() {
      return this.hasTarget.get();
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public boolean canContinueToUse() {
      return this.canContinue.get();
   }

   public void start() {
      this.startAction.perform();
   }

   public void tick() {
      this.tickAction.perform();
   }

   public void stop() {
      this.endAction.stop();
   }
}
