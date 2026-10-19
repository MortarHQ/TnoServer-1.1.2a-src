package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GatekeeperHammerPhase extends BehaviorPhase<TheGatekeeper> {
   public static final int ID = 6;

   public GatekeeperHammerPhase() {
      super(6, 1, 32, 100);
   }

   public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
      return cooldownOver && this.canReachTarget(entity, 2.0);
   }

   public void onStart(TheGatekeeper entity) {
      entity.setItemInHand(InteractionHand.MAIN_HAND, entity.getGatekeeperHammer());
      entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
   }

   public void tick(TheGatekeeper entity) {
      LivingEntity target = entity.getTarget();
      int ticks = entity.getBehaviorTicks();
      if (ticks == 18 && target != null) {
         this.performDefaultMeleeAttack(entity, 2.0, true, 90.0F, e -> {
            e.hurtMarked = true;
            e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(0.6, 0.5, 0.6));
         });
         entity.playSound(SoundEvents.MACE_SMASH_GROUND_HEAVY);
         entity.hurtMarked = true;
         entity.addDeltaMovement(target.position().subtract(entity.position()).normalize().scale(0.7));
      }
   }

   public boolean canContinue(TheGatekeeper entity) {
      return true;
   }
}
