package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class GatekeeperStepBackPhase extends BehaviorPhase<TheGatekeeper> {
   public static final int ID = 1;

   public GatekeeperStepBackPhase() {
      super(1, 1, 17, 200);
   }

   public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
      return cooldownOver && this.canReachTarget(entity, 5.0);
   }

   public void onStart(TheGatekeeper entity) {
      LivingEntity target = entity.getTarget();
      if (target != null) {
         entity.hurtMarked = true;
         Vec3 delta = entity.position().subtract(target.position());
         entity.addDeltaMovement(new Vec3(delta.x, 0.0, delta.z).normalize().add(0.0, delta.normalize().y, 0.0).normalize().scale(1.8));
      }

      entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
   }

   public void tick(TheGatekeeper entity) {
      LivingEntity target = entity.getTarget();
      if (target != null) {
         ESEntityUtil.instantLook(entity, target.getEyePosition());
      }
   }

   public boolean canContinue(TheGatekeeper entity) {
      return true;
   }

   public void stop(TheGatekeeper entity, BehaviorManager<TheGatekeeper> manager) {
      entity.setBehaviorState(0);
      entity.setBehaviorTicks(0);
      int newId = this.canReachTarget(entity, 8.0) ? 2 : (this.canReachTarget(entity, 18.0) ? 9 : 10);
      manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
      this.onStop(entity);
   }
}
