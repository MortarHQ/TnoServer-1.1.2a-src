package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.item.ItemStack;

public class GatekeeperEatFailPhase extends BehaviorPhase<TheGatekeeper> {
   public static final int ID = 14;

   public GatekeeperEatFailPhase() {
      super(14, 1, 22, 0);
   }

   public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
      return false;
   }

   public void onStart(TheGatekeeper entity) {
      entity.healInterruptedCount++;
   }

   public void tick(TheGatekeeper entity) {
      if (entity.getBehaviorTicks() == 2) {
         BehaviorUtils.throwItem(
            entity,
            entity.getOffhandItem().copy(),
            entity.getEyePosition().add(entity.getRandom().nextDouble() - 0.5, entity.getRandom().nextDouble(), entity.getRandom().nextDouble() - 0.5)
         );
         entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
      }
   }

   public boolean canContinue(TheGatekeeper entity) {
      return true;
   }

   public void stop(TheGatekeeper entity, BehaviorManager<TheGatekeeper> manager) {
      entity.setBehaviorState(0);
      entity.setBehaviorTicks(0);
      int newId = this.canReachTarget(entity, 3.0) ? 5 : (this.canReachTarget(entity, 18.0) ? 9 : 10);
      if (manager.getCooldowns().getOrDefault(newId, 0) <= 0) {
         manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
      }

      this.onStop(entity);
   }
}
