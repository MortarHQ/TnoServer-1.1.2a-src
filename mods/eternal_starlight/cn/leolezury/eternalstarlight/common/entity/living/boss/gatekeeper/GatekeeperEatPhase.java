package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GatekeeperEatPhase extends BehaviorPhase<TheGatekeeper> {
   public static final int ID = 13;

   public GatekeeperEatPhase() {
      super(13, 1, 55, 400);
   }

   public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
      return cooldownOver
         && entity.getTarget() != null
         && !this.canReachTarget(entity, 10.0)
         && entity.getHealth() < entity.getMaxHealth() * 0.4F
         && entity.healCount < 8
         && entity.healInterruptedCount < 4;
   }

   public void onStart(TheGatekeeper entity) {
      entity.setItemInHand(InteractionHand.OFF_HAND, Items.GOLDEN_CARROT.getDefaultInstance());
      entity.healInterrupted = false;
      entity.healInterruptedIndirect = false;
      entity.healCount++;
   }

   public void tick(TheGatekeeper entity) {
      LivingEntity target = entity.getTarget();
      if (target != null) {
         ESEntityUtil.instantLook(entity, target.getEyePosition());
         entity.hurtMarked = true;
         entity.addDeltaMovement(entity.position().subtract(target.position()).normalize().scale(0.05));
      }
   }

   public boolean canContinue(TheGatekeeper entity) {
      return !entity.healInterrupted;
   }

   public void stop(TheGatekeeper entity, BehaviorManager<TheGatekeeper> manager) {
      if (!entity.healInterrupted || !entity.healInterruptedIndirect) {
         entity.heal(entity.healInterrupted ? entity.getMaxHealth() / 20.0F : entity.getMaxHealth() / 10.0F);
      }

      entity.setBehaviorState(0);
      entity.setBehaviorTicks(0);
      int newId = entity.healInterrupted && entity.healInterruptedIndirect
         ? 14
         : (this.canReachTarget(entity, 3.0) ? 5 : (this.canReachTarget(entity, 18.0) ? 9 : 10));
      if (newId != 14) {
         entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
      }

      if (manager.getCooldowns().getOrDefault(newId, 0) <= 0) {
         manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
      }

      this.onStop(entity);
   }
}
