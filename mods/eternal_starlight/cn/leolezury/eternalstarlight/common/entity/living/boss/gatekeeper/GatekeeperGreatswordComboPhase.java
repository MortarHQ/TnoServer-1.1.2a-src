package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GatekeeperGreatswordComboPhase extends BehaviorPhase<TheGatekeeper> {
   public static final int ID = 8;

   public GatekeeperGreatswordComboPhase() {
      super(8, 1, 105, 150);
   }

   public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
      return cooldownOver && this.canReachTarget(entity, 3.0);
   }

   public void onStart(TheGatekeeper entity) {
      entity.setItemInHand(InteractionHand.MAIN_HAND, ESItems.GLISTERING_GREATSWORD.get().getDefaultInstance());
      entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
   }

   public void tick(TheGatekeeper entity) {
      LivingEntity target = entity.getTarget();
      int ticks = entity.getBehaviorTicks();
      if (target != null) {
         ESEntityUtil.instantLook(entity, target.getEyePosition());
         if (ticks == 22 || ticks == 41 || ticks == 62 || ticks == 81) {
            this.performDefaultMeleeAttack(entity, 3.0, true, 120.0F, e -> {
               e.hurtMarked = true;
               e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(0.3, 0.2, 0.3));
            });
            entity.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
            entity.hurtMarked = true;
            entity.addDeltaMovement(target.position().subtract(entity.position()).normalize().scale(0.6));
         }
      }
   }

   public boolean canContinue(TheGatekeeper entity) {
      return true;
   }

   public void stop(TheGatekeeper entity, BehaviorManager<TheGatekeeper> manager) {
      entity.setBehaviorState(0);
      entity.setBehaviorTicks(0);
      int newId = this.canReachTarget(entity, 5.0) ? 1 : 0;
      if (manager.getCooldowns().getOrDefault(newId, 0) <= 0) {
         manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
      }

      this.onStop(entity);
   }
}
