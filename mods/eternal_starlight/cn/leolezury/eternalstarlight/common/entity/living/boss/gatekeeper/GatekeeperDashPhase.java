package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GatekeeperDashPhase extends BehaviorPhase<TheGatekeeper> {
   private final List<Entity> hitEntities = new ArrayList<>();
   public static final int ID = 7;

   public GatekeeperDashPhase() {
      super(7, 2, 40, 150);
   }

   public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
      return cooldownOver && this.canReachTargetXZ(entity, 9.0) && !this.canReachTargetXZ(entity, 5.0);
   }

   public void onStart(TheGatekeeper entity) {
      entity.setItemInHand(InteractionHand.MAIN_HAND, ESItems.GLISTERING_SWORD.get().getDefaultInstance());
      entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
      this.hitEntities.clear();
   }

   public void tick(TheGatekeeper entity) {
      LivingEntity target = entity.getTarget();
      if (target != null) {
         ESEntityUtil.instantLook(entity, target.getEyePosition());
         if (entity.getBehaviorTicks() == 16) {
            entity.hurtMarked = true;
            entity.addDeltaMovement(target.position().subtract(entity.position()).normalize().scale(3.0));
         }

         if (entity.getBehaviorTicks() >= 16 && entity.getBehaviorTicks() <= 35) {
            this.performMeleeAttack(entity, 1.5, true, 360.0F, e -> {
               if (!this.hitEntities.contains(e) && entity.doHurtTarget(e)) {
                  e.hurtMarked = true;
                  e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(0.2, 0.1, 0.2));
                  this.hitEntities.add(e);
               }
            });
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
