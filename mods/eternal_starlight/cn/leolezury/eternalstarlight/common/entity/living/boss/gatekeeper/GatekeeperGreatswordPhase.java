package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GatekeeperGreatswordPhase extends BehaviorPhase<TheGatekeeper> {
   public static final int ID = 5;

   public GatekeeperGreatswordPhase() {
      super(5, 1, 47, 100);
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
      if ((ticks == 15 || ticks == 33) && target != null) {
         this.performDefaultMeleeAttack(entity, 3.0, true, 120.0F, e -> {
            e.hurtMarked = true;
            e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(0.3, 0.2, 0.3));
         });
         entity.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
         entity.hurtMarked = true;
         entity.addDeltaMovement(target.position().subtract(entity.position()).normalize().scale(0.6));
      }
   }

   public boolean canContinue(TheGatekeeper entity) {
      return true;
   }
}
