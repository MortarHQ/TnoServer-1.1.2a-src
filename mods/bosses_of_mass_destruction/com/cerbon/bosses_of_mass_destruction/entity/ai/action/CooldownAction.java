package com.cerbon.bosses_of_mass_destruction.entity.ai.action;

public class CooldownAction implements IAction, IActionStop {
   private final IActionWithCooldown action;
   private final int initialCooldown;
   private int currentTime;

   public CooldownAction(IActionWithCooldown action, int initialCooldown) {
      this.action = action;
      this.initialCooldown = initialCooldown;
      this.currentTime = initialCooldown;
   }

   @Override
   public void perform() {
      this.currentTime--;
      if (this.currentTime <= 0) {
         this.currentTime = this.action.perform();
      }
   }

   @Override
   public void stop() {
      this.currentTime = Math.max(this.currentTime, this.initialCooldown);
   }
}
