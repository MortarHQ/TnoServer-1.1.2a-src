package net.unusual.block_factorys_bosses.state;

import net.minecraft.world.entity.ai.goal.Goal;
import net.unusual.block_factorys_bosses.entity.boss.AbstractStateBossEntity;

public class StateGoal<T extends AbstractStateBossEntity> extends Goal {
   private final T boss;

   public StateGoal(T boss) {
      this.boss = boss;
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void tick() {
      this.boss.getStateController().tick();
   }

   public void stop() {
      this.boss.getStateController().endAll();
   }

   public boolean isInterruptable() {
      return false;
   }

   public boolean canUse() {
      return !this.boss.getStateController().getRegistry().isEmpty();
   }
}
